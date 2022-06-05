package com.request;

import java.util.List;

public class AddValidateServiceRequest {

    public AddValidateServiceRequest(){}

    public AddValidateServiceRequest(String serviceName){
        this.serviceName = serviceName;
    }

    String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
