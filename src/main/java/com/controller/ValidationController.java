package com.controller;

import com.bo.CommonResult;
import com.request.ValidationRequest;
import com.service.PasswordValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.constant.MappingConstant.*;


@RestController
public class ValidationController {

    @Autowired
    PasswordValidationService service;


    @PostMapping(VALIDATE)
    public CommonResult validate(@RequestBody ValidationRequest req){

      return service.validate(req);
    }
}
