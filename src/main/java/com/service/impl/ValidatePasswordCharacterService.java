package com.service.impl;

import com.enums.ResponseEnum;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.ValidateService;
import org.springframework.stereotype.Service;
import com.util.ResponseUtil;

import java.util.List;

@Service
public class ValidatePasswordCharacterService implements ValidateService {

    private final String name = "validatePasswordCharacterService";

    @Override
    public void validate(ValidateRequest request, List<Response> responseList) {

        String password = request.getPassword();

        int numberOfLowerDigits = 0 ;
        int numberOfNumericalDigits = 0;

        for(char ch:  password.toCharArray()){
            if(Character.isLowerCase(ch)){
                numberOfLowerDigits ++;
            }else if(Character.isDigit(ch)){
                numberOfNumericalDigits ++;
            }
        }

        if(numberOfLowerDigits == 0){
            responseList.add(ResponseUtil.error(ResponseEnum.ValidationPasswordNoLowerDigits));
        }
        if(numberOfNumericalDigits == 0){
            responseList.add(ResponseUtil.error(ResponseEnum.ValidationPasswordNoNumericalDigits));
        }
        if((numberOfLowerDigits + numberOfNumericalDigits) != password.length()){
            responseList.add(ResponseUtil.error(ResponseEnum.ValidationPasswordCharacterNotAllowed));
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
