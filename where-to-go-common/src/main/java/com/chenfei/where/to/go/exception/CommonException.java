package com.chenfei.where.to.go.exception;
/*
 * Created by chenfei on 2019/3/24 15:00
 */

import com.chenfei.where.to.go.enums.ResponseCodeEnum;
import lombok.Getter;
import lombok.Setter;

public class CommonException extends RuntimeException{

    @Getter
    @Setter
    private Integer code;

    public CommonException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public CommonException(String message) {
        super(message);
        this.code = ResponseCodeEnum.ERROR.getCode();
    }
}
