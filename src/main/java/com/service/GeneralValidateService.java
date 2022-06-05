package com.service;

import com.request.AddValidateServiceRequest;
import com.request.DeleteValidateServiceRequest;
import com.request.ValidateRequest;
import com.response.Response;

import java.util.List;

public interface GeneralValidateService {
    List<Response> validate(ValidateRequest req);
    Response getValidateServiceNameInUse();
    Response deleteValidateServiceInUse(DeleteValidateServiceRequest req);
    Response addValidateService(AddValidateServiceRequest req);
}
