package com.controller;



import com.enums.ResponseEnum;
import com.request.AddValidateServiceRequest;
import com.request.DeleteValidateServiceRequest;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.GeneralValidateService;
import com.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "delete the validation service we are using",notes =
            "change which validation logic in runtime" + "\n"+
            "but if there are multiple pods we need to use redis channel to reach to every pods(send to the service and it publishes and other pods subscribe that channel and operate"+ "\n"+
            "but when the service restart all the operation will be lost"+ "\n"+
            "or we can store a validationService list in redis and we modify that list in redis, every time we run validate method we can check the verificationList in redis and decide if we need to operate that")
    @DeleteMapping(DELETEVALIDATESERVICEINUSE)
    public Response deleteValidateServiceInUse(@RequestBody DeleteValidateServiceRequest req){
        return service.deleteValidateServiceInUse(req);
    }

    @PostMapping(ADDVALIDATESERVICE)
    public Response addValidateService(@RequestBody AddValidateServiceRequest req){
        return service.addValidateService(req);
    }
}
