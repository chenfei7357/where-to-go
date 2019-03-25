package com.chenfei.where.to.go.globalExceptionsHandler;
/*
 * Created by chenfei on 2018/12/24 13:35
 */


import com.chenfei.where.to.go.exception.CommonException;
import com.chenfei.where.to.go.response.CommonResponse;
import com.chenfei.where.to.go.response.CommonResponseUtils;
import com.chenfei.where.to.go.response.CommonResultResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(CommonException.class)
    public CommonResultResponse commonExceptionHnadler(CommonException ce){
        return CommonResponseUtils.error(ce.getCode(),ce.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse exceptionHnadler(Exception ce){
        ce.printStackTrace();
        return CommonResponseUtils.error();
    }
}
