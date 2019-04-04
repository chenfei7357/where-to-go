package com.chenfei.where.to.go.model.dto;

import com.chenfei.where.to.go.model.validatorInterface.ValidatorInterface;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "ConfigsDTO",description = "字典实体类")
public class ConfigsDTO implements Serializable {

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

    @ApiModelProperty("页码")
    @NotNull(groups = ValidatorInterface.ConfigQuery.class,message = "分页参数不能为空")
    @Min(groups = ValidatorInterface.ConfigQuery.class,value = 1,message = "分页参数不合法")
    private Integer page;

    @ApiModelProperty("每页容量")
    @NotNull(groups = ValidatorInterface.ConfigQuery.class ,message = "分页参数不能为空")
    @Min(groups = ValidatorInterface.ConfigQuery.class ,value = 1,message = "分页参数不合法")
    private Integer size;


}