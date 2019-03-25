package com.chenfei.where.to.go.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Configs implements Serializable {

    private String conName;

    private String conValue;

    private String remark;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

}