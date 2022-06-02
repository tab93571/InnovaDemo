package com.demo.service;

import com.demo.request.ValidateRequest;
import com.demo.response.Response;

import java.util.List;

public interface ValidateService {

    void validate(ValidateRequest request, List<Response> responseList);

     String getName();
}
