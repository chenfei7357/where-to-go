package com.chenfei.where.to.go.enums;
/*
 * Created by chenfei on 2019/3/24 14:29
 */

import lombok.Getter;

public enum ResponseCodeEnum {
    SUCCESS(200000,"请求成功"),
    ERROR(500000,"系统异常");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    ResponseCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
