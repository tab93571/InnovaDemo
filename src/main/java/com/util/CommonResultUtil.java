package com.util;

import com.bo.CommonResult;
import com.enums.ResponseEnum;

public class CommonResultUtil {
    public static CommonResult success(Object object){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(ResponseEnum.Success.getCode());
        commonResult.setMessage(ResponseEnum.Success.getMessage());
        commonResult.setData(object);
        return commonResult;
    }

    public static CommonResult success(){
        return success(null);
    }

    public static CommonResult error(ResponseEnum responseEnum, Object object){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(responseEnum.getCode());
        commonResult.setMessage(responseEnum.getMessage());
        commonResult.setData(object);
        return commonResult;
    }

    public static CommonResult error(ResponseEnum responseEnum){

        return error(responseEnum,null);
    }
}
