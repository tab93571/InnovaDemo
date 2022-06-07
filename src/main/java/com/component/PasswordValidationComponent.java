package com.component;

import com.bo.PasswordValidationResult;
import com.request.ValidationRequest;

public interface PasswordValidationComponent {

    PasswordValidationResult validate(ValidationRequest request);

}
