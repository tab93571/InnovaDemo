package com.controller;



import com.enums.ResponseEnum;
import com.request.AddValidateServiceRequest;
import com.request.DeleteValidateServiceRequest;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.GeneralValidateService;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.constant.MappingConstant.*;


@RestController
public class ValidationController {

    @Autowired
    GeneralValidateService service;


    @PostMapping(VALIDATE)
    public Response validate(@RequestBody ValidateRequest req){

        List<Response> responseList = service.validate(req);

        if(responseList.size() == 0 ){
            return ResponseUtil.success();
        }else{
            return ResponseUtil.error(ResponseEnum.Fail,responseList);
        }
    }

    @GetMapping(GETVALIDATESERVICESINUSE)
    public Response getValidateServicesInUse(){
        return service.getValidateServiceNameInUse();
    }

    @DeleteMapping(DELETEVALIDATESERVICEINUSE)
    public Response deleteValidateServiceInUse(@RequestBody DeleteValidateServiceRequest req){
        return service.deleteValidateServiceInUse(req);
    }

    @PostMapping(ADDVALIDATESERVICE)
    public Response addValidateService(@RequestBody AddValidateServiceRequest req){
        return service.addValidateService(req);
    }
}
