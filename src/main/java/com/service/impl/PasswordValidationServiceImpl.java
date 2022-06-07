package com.service.impl;


import com.bo.PasswordValidationResult;
import com.enums.ResponseEnum;
import com.request.ValidationRequest;
import com.bo.CommonResult;
import com.service.PasswordValidationService;
import com.component.PasswordValidationComponent;
import com.util.CommonResultUtil;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PasswordValidationServiceImpl implements PasswordValidationService {


    private List<PasswordValidationComponent> passwordValidationComponents;

    public PasswordValidationServiceImpl(List<PasswordValidationComponent> passwordValidationComponents){
        this.passwordValidationComponents = passwordValidationComponents;
    }

    @Override
    public CommonResult validate(ValidationRequest req) {


        List<CommonResult> passwordValidationDetails = new ArrayList<>();

        for(PasswordValidationComponent passwordValidationComponent : passwordValidationComponents){
            PasswordValidationResult passwordValidationResult = passwordValidationComponent.validate(req);
            if(!passwordValidationResult.isSuccess() && null != passwordValidationResult.getDetails()){
                passwordValidationDetails.addAll(passwordValidationResult.getDetails());
            }
        }

        if(passwordValidationDetails.size() == 0){
            return CommonResultUtil.success();
        }else{
            return CommonResultUtil.error(ResponseEnum.Fail,passwordValidationDetails);
        }

    }
}
