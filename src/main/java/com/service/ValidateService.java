package com.service;

import com.request.ValidateRequest;
import com.response.Response;

import java.util.List;

public interface ValidateService {

    void validate(ValidateRequest request, List<Response> responseList);

     String getName();
}
