package com.request;

public class ValidateRequest {

    public ValidateRequest(){}

    public ValidateRequest(String password){
        this.password = password;
    }
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
