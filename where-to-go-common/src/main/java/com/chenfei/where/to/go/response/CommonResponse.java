package com.chenfei.where.to.go.response;
/*
 * Created by chenfei on 2019/3/24 14:10
 */


import lombok.Data;

import java.io.Serializable;

@Data
public  class CommonResponse implements Serializable {

    private Integer code;

    private String message;

}
