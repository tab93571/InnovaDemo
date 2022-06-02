package com.demo.controller;



import com.demo.request.AddValidateServiceRequest;
import com.demo.request.DeleteValidateServiceRequest;
import com.demo.request.ValidateRequest;
import com.demo.response.Response;
import com.demo.service.GeneralValidateService;
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
