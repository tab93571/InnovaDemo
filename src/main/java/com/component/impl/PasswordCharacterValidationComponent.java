package com.component.impl;

import com.bo.PasswordValidationResult;
import com.enums.ResponseEnum;
import com.request.ValidationRequest;
import com.component.PasswordValidationComponent;
import com.bo.CommonResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import com.util.CommonResultUtil;

import java.util.ArrayList;
import java.util.List;

import static com.enums.ResponseEnum.ValidationPasswordNullOrEmptyError;

@Component
public class PasswordCharacterValidationComponent implements PasswordValidationComponent {


    @Override
    public PasswordValidationResult validate(ValidationRequest request) {

        List<CommonResult> passwordValidationDetails = new ArrayList<>();
        PasswordValidationResult passwordValidationResult = new PasswordValidationResult(passwordValidationDetails);

        if(request == null || Strings.isEmpty(request.getPassword())){
            passwordValidationDetails.add(CommonResultUtil.error(ValidationPasswordNullOrEmptyError));
            return passwordValidationResult;
        }

        String password = request.getPassword();

        int amountOfLowerCharacters = 0 ;
        int amountOfNumericalDigits = 0;

        for(char ch:  password.toCharArray()){
            if(Character.isLowerCase(ch)){
                amountOfLowerCharacters ++;
            }else if(Character.isDigit(ch)){
                amountOfNumericalDigits ++;
            }
        }

        if(amountOfLowerCharacters == 0){
            passwordValidationDetails.add(CommonResultUtil.error(ResponseEnum.ValidationPasswordNoLowerCaseLetters));
        }
        if(amountOfNumericalDigits == 0){
            passwordValidationDetails.add(CommonResultUtil.error(ResponseEnum.ValidationPasswordNoNumericalDigits));
        }
        if((amountOfLowerCharacters + amountOfNumericalDigits) != password.length()){
            passwordValidationDetails.add(CommonResultUtil.error(ResponseEnum.ValidationPasswordCharacterNotAllowed));
        }
        if(0 == passwordValidationDetails.size()){
            passwordValidationResult.setSuccess(true);
        }

        return passwordValidationResult;
    }

}
