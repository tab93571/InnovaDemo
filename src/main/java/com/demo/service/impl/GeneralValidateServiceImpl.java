package com.demo.service.impl;

import com.demo.enums.ResponseEnum;
import com.demo.request.AddValidateServiceRequest;
import com.demo.request.DeleteValidateServiceRequest;
import com.demo.request.ValidateRequest;
import com.demo.response.Response;
import com.demo.service.GeneralValidateService;
import com.demo.service.ValidateService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.demo.util.ResponseUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



@Service
public class GeneralValidateServiceImpl implements GeneralValidateService {

    @Autowired
    Set<ValidateService> validationSet;

    @Autowired
    ApplicationContext context;

    @Override
    public Response validate(ValidateRequest req) {

        if(req == null || Strings.isEmpty(req.getPassword())){
            return ResponseUtil.error(ResponseEnum.ValidationPasswordNullOrEmptyError);
        }

        List<Response> responseList = new ArrayList();

        for(ValidateService validateService : validationSet){
            validateService.validate(req,responseList);
        }
        if(responseList.size() == 0 ){
            return ResponseUtil.success();
        }else{
            return ResponseUtil.error(ResponseEnum.Fail,responseList);
        }
    }


    @Override
    public Response getValidateServiceInUse() {
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
        return ResponseUtil.error(ResponseEnum.ValidationCanNotFindService,req);
    }

    @Override
    public synchronized Response addValidateService(AddValidateServiceRequest req) {
        String serviceName = req.getServiceName();

        Object bean;
        try{
            bean = context.getBean(serviceName);
        }catch(Exception e){
            return ResponseUtil.error(ResponseEnum.ValidationCanNotFindService,req);
        }

        if(!(bean instanceof  ValidateService)){
            return ResponseUtil.error(ResponseEnum.ValidationServiceIsNotValidationService,req);
        }

        validationSet.add((ValidateService)bean);
        return ResponseUtil.success();
    }

}
