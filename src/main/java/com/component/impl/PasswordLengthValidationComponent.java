package com.component.impl;

import com.bo.PasswordValidationResult;
import com.constant.ValidationConstant;
import com.enums.ResponseEnum;
import com.request.ValidationRequest;
import com.bo.CommonResult;
import com.component.PasswordValidationComponent;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import com.util.CommonResultUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.enums.ResponseEnum.ValidationPasswordNullOrEmptyError;


@Component
public class PasswordLengthValidationComponent implements PasswordValidationComponent {

    @Override
    public PasswordValidationResult validate(ValidationRequest request) {

        List<CommonResult> passwordValidationDetails = new ArrayList<>();
        PasswordValidationResult passwordValidationResult = new PasswordValidationResult(passwordValidationDetails);

        if(request == null || Strings.isEmpty(request.getPassword())){
            passwordValidationDetails.add(CommonResultUtil.error(ValidationPasswordNullOrEmptyError));
            return passwordValidationResult;
        }

        int length = request.getPassword().length();
        if(length > ValidationConstant.LENGTHUPPERBOUND || length < ValidationConstant.LENGTHLOWERBOUND){
            CommonResult commonResult = CommonResultUtil.error(ResponseEnum.ValidationPasswordLengthError,Arrays.asList(ValidationConstant.LENGTHLOWERBOUND, ValidationConstant.LENGTHUPPERBOUND));
            passwordValidationDetails.add(commonResult);
        }

        if(0 == passwordValidationDetails.size()){
            passwordValidationResult.setSuccess(true);
        }

        return passwordValidationResult;

    }
}
