package com.chenfei.where.to.go.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "ConfigsVO",description = "字典实体类")
public class ConfigsVO implements Serializable {

    @ApiModelProperty("字典名称")
    private String conName;

    @ApiModelProperty("字典值")
    private String conValue;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}