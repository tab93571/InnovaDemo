package com.controller;



import com.request.AddValidateServiceRequest;
import com.request.DeleteValidateServiceRequest;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.GeneralValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ValidationController {

    @Autowired
    GeneralValidateService service;


    @PostMapping("/validate")
    public Response validate(@RequestBody ValidateRequest req){
        return service.validate(req);
    }

    @GetMapping("/getValidateServicesInUse")
    public Response getValidateServicesInUse(){
        return service.getValidateServiceInUse();
    }

    @DeleteMapping("/deleteValidateServiceInUse")
    public Response deleteValidateServiceInUse(@RequestBody DeleteValidateServiceRequest req){
        return service.deleteValidateServiceInUse(req);
    }

    @PostMapping("/addValidateService")
    public Response addValidateService(@RequestBody AddValidateServiceRequest req){
        return service.addValidateService(req);
    }
}
