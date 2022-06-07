package com.bo;

import java.util.List;

public class PasswordValidationResult {

    private boolean isSuccess;
    private List<CommonResult> details;

    public PasswordValidationResult(List<CommonResult> details){
        this.details = details;
    }

    public PasswordValidationResult(){

    }

    public PasswordValidationResult(boolean isSuccess, List<CommonResult>details){
        this.isSuccess = isSuccess;
        this.details = details;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<CommonResult> getDetails() {
        return details;
    }

    public void setDetails(List<CommonResult> details) {
        this.details = details;
    }
}
