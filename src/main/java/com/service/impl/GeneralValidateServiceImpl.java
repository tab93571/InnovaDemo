package com.service.impl;


import static com.enums.ResponseEnum.*;
import com.request.AddValidateServiceRequest;
import com.request.DeleteValidateServiceRequest;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.GeneralValidateService;
import com.service.ValidateService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.util.ResponseUtil;

import java.util.*;


@Service
public class GeneralValidateServiceImpl implements GeneralValidateService {


    private Set<ValidateService> validationSet;
    private ApplicationContext context;

    public GeneralValidateServiceImpl(Set<ValidateService> validationSet,ApplicationContext context){
        this.context = context;
        this.validationSet = validationSet;
    }

    @Override
    public List<Response> validate(ValidateRequest req) {

        List<Response> responseList = new ArrayList();

        if(req == null || Strings.isEmpty(req.getPassword())){
            Response response = ResponseUtil.error(ValidationPasswordNullOrEmptyError);
            responseList.add(response);
            return responseList;
        }

        for(ValidateService validateService : validationSet){
            validateService.validate(req,responseList);
        }
        return responseList;
    }


    @Override
    public Response getValidateServiceNameInUse() {
        List<String> validationServiceList = new ArrayList<>();
        for (ValidateService  validateService : validationSet){
            validationServiceList.add(validateService.getName());
        }
        return ResponseUtil.success(validationServiceList);
    }

    @Override
    public synchronized Response deleteValidateServiceInUse(DeleteValidateServiceRequest req) {
        String needToRemoveService = req.getServiceName();

        for (Iterator<ValidateService> i = validationSet.iterator(); i.hasNext();) {
            ValidateService service = i.next();
            if (needToRemoveService.equals(service.getName())) {
                i.remove();
                return ResponseUtil.success();
            }
        }
        return ResponseUtil.error(ValidationCanNotFindService);
    }

    @Override
    public synchronized Response addValidateService(AddValidateServiceRequest req) {
        String serviceName = req.getServiceName();

        Object bean;
        try{
            bean = context.getBean(serviceName);
        }catch(Exception e){
            return ResponseUtil.error(ValidationCanNotFindService);
        }

        if(!(bean instanceof  ValidateService)){
            return ResponseUtil.error(ValidationServiceTypeNotMatch);
        }

        validationSet.add((ValidateService)bean);
        return ResponseUtil.success();
    }

}
