package com.demo.service;

import com.demo.request.AddValidateServiceRequest;
import com.demo.request.DeleteValidateServiceRequest;
import com.demo.request.ValidateRequest;
import com.demo.response.Response;

import java.util.List;

public interface GeneralValidateService {
    Response validate(ValidateRequest req);
    Response getValidateServiceInUse();
    Response deleteValidateServiceInUse(DeleteValidateServiceRequest req);
    Response addValidateService(AddValidateServiceRequest req);
}
