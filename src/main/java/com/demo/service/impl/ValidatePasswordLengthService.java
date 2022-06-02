package com.demo.service.impl;

import static com.demo.constant.ValidationConstant.*;

import com.demo.enums.ResponseEnum;
import com.demo.request.ValidateRequest;
import com.demo.response.Response;
import com.demo.service.ValidateService;
import org.springframework.stereotype.Service;
import com.demo.util.ResponseUtil;
import java.util.Arrays;
import java.util.List;


@Service
public class ValidatePasswordLengthService implements ValidateService {

    private final String name = "validatePasswordLengthService";

    @Override
    public void validate(ValidateRequest request, List<Response> responseList) {
        int length = request.getPassword().length();
        if(length > LENGTHUPPERBOUND || length < LENGTHLOWERBOUND){
            Response response = ResponseUtil.error(ResponseEnum.ValidationPasswordLengthError,Arrays.asList(LENGTHLOWERBOUND,LENGTHUPPERBOUND));
            responseList.add(response);
        }

    }

    public String getName() {
        return name;
    }
}
