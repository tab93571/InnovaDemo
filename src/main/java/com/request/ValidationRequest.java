package com.request;

public class ValidationRequest {

    public ValidationRequest(){}

    public ValidationRequest(String password){
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
