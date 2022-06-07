package com.service;

import com.bo.CommonResult;
import com.request.ValidationRequest;


public interface PasswordValidationService {
    CommonResult validate(ValidationRequest req);
}
