package com.util;

import com.enums.ResponseEnum;
import com.response.Response;

public class ResponseUtil {
    public static Response success(Object object){
        Response response = new Response();
        response.setCode(ResponseEnum.Success.getCode());
        response.setMessage(ResponseEnum.Success.getMessage());
        response.setData(object);
        return response;
    }

    public static Response success(){
        return success(null);
    }

    public static Response error(ResponseEnum responseEnum,Object object){
        Response response = new Response();
        response.setCode(responseEnum.getCode());
        response.setMessage(responseEnum.getMessage());
        response.setData(object);
        return response;
    }

    public static Response error(ResponseEnum responseEnum){

        return error(responseEnum,null);
    }
}
