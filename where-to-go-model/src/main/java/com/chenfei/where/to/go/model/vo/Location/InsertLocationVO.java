package com.chenfei.where.to.go.model.vo.Location;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "新增地点",description = "新增地点信息")
public class InsertLocationVO implements Serializable {

    @ApiModelProperty("地点编码")
    private String code;

    @NotNull
    @ApiModelProperty(value = "地点名称" ,required = true)
    private String name;

    @ApiModelProperty("层级")
    private String level;

    @NotNull
    @ApiModelProperty(value = "经度" ,required = true)
    private String lng;

    @NotNull
    @ApiModelProperty(value = "纬度" ,required = true)
    private String lat;

    @ApiModelProperty("描述")
    private String note;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}