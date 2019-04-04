package com.chenfei.where.to.go.enums;
/*
 * Created by chenfei on 2019/3/24 15:08
 */

import lombok.Getter;

public enum BizEnum {
    NO_LOGIN(99999,"please login"),
    PARAMETER_EXCEPTION(30000,"请求参数不合法"),
    PAGE_PARAMETER_NULL(30001,"分页参数为空"),
    SYSTEM_BUSY(30002,"系统繁忙，排队中")
    ;

    @Getter
    private Integer code;

    @Getter
    private String desc;

    BizEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
