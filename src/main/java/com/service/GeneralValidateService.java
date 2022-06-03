package com.service;

import com.request.AddValidateServiceRequest;
import com.request.DeleteValidateServiceRequest;
import com.request.ValidateRequest;
import com.response.Response;

public interface GeneralValidateService {
    Response validate(ValidateRequest req);
    Response getValidateServiceInUse();
    Response deleteValidateServiceInUse(DeleteValidateServiceRequest req);
    Response addValidateService(AddValidateServiceRequest req);
}
