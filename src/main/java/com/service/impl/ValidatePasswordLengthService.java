package com.service.impl;

import com.constant.ValidationConstant;
import com.enums.ResponseEnum;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.ValidateService;
import org.springframework.stereotype.Service;
import com.util.ResponseUtil;
import java.util.Arrays;
import java.util.List;


@Service
public class ValidatePasswordLengthService implements ValidateService {

    private final String name = "validatePasswordLengthService";

    @Override
    public void validate(ValidateRequest request, List<Response> responseList) {
        int length = request.getPassword().length();
        if(length > ValidationConstant.LENGTHUPPERBOUND || length < ValidationConstant.LENGTHLOWERBOUND){
            Response response = ResponseUtil.error(ResponseEnum.ValidationPasswordLengthError,Arrays.asList(ValidationConstant.LENGTHLOWERBOUND, ValidationConstant.LENGTHUPPERBOUND));
            responseList.add(response);
        }

    }

    public String getName() {
        return name;
    }
}
