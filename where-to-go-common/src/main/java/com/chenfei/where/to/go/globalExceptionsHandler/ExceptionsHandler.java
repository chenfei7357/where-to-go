package com.chenfei.where.to.go.globalExceptionsHandler;
/*
 * Created by chenfei on 2018/12/24 13:35
 */


import com.chenfei.where.to.go.enums.BizEnum;
import com.chenfei.where.to.go.exception.CommonException;
import com.chenfei.where.to.go.exception.UnableToAquireLockException;
import com.chenfei.where.to.go.response.CommonResponse;
import com.chenfei.where.to.go.response.CommonResponseUtils;
import com.chenfei.where.to.go.response.CommonResultResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(CommonException.class)
    public CommonResultResponse commonExceptionHandle(CommonException ce){
        return CommonResponseUtils.error(ce.getCode(),ce.getMessage());
    }

    @ExceptionHandler(UnableToAquireLockException.class)
    public CommonResponse exceptionHandle(UnableToAquireLockException ue){
        return CommonResponseUtils.error(BizEnum.SYSTEM_BUSY.getCode(),BizEnum.SYSTEM_BUSY.getDesc());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse validationBodyExceptionHandle(MethodArgumentNotValidException ce){
        BindingResult result = ce.getBindingResult();
        StringBuilder sb = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p ->{
                sb.append("【").append(p.getDefaultMessage()).append("】");
            });
        }
        return CommonResponseUtils.error(BizEnum.PARAMETER_EXCEPTION.getCode(),sb.toString());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResponse validationConstraintViolationExceptionHandle(ConstraintViolationException ce){
        StringBuilder sb = new StringBuilder();
        ce.getConstraintViolations().forEach(p ->{
            sb.append("【").append(p.getMessage()).append("】");
        });
        return CommonResponseUtils.error(BizEnum.PARAMETER_EXCEPTION.getCode(),ce.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse exceptionHandle(Exception ce){
        ce.printStackTrace();
        return CommonResponseUtils.error();
    }
}
