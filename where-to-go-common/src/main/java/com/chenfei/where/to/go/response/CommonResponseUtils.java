package com.chenfei.where.to.go.response;
/*
 * Created by chenfei on 2019/3/24 14:22
 */

import com.chenfei.where.to.go.enums.ResponseCodeEnum;

public class CommonResponseUtils {


    public static <T> CommonResultResponse success(){
        return CommonResponseUtils.success(null);
    }

    public static <T> CommonResultResponse success(T t){
        CommonResultResponse<T> response = new CommonResultResponse();
        response.setCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setMessage(ResponseCodeEnum.SUCCESS.getDesc());
        response.setResult(t);
        return response;
    }

    public static <T> CommonResultResponse error(){
        return CommonResponseUtils.error(null);
    }

    public static <T> CommonResultResponse error(T t){
        CommonResultResponse<T> response = new CommonResultResponse();
        response.setCode(ResponseCodeEnum.ERROR.getCode());
        response.setMessage(ResponseCodeEnum.ERROR.getDesc());
        response.setResult(t);
        return response;
    }

    public static <T> CommonResultResponse error(int code,String message){
        return CommonResponseUtils.commonResponse(code,message);
    }

    public static CommonResultResponse commonResponse(int code,String message){
        return CommonResponseUtils.commonResponse(code, message, null);
    }

    public static <T> CommonResultResponse commonResponse(int code,String message,T t){
        CommonResultResponse<T> response = new CommonResultResponse();
        response.setCode(code);
        response.setMessage(message);
        response.setResult(t);
        return response;
    }
}
