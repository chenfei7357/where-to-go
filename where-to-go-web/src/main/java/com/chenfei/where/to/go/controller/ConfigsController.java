package com.chenfei.where.to.go.controller;
/*
 * Created by chenfei on 2019/3/24 14:07
 */

import com.chenfei.where.to.go.model.dto.ConfigsDTO;
import com.chenfei.where.to.go.model.validatorInterface.ValidatorInterface;
import com.chenfei.where.to.go.model.vo.ConfigsVO;
import com.chenfei.where.to.go.response.CommonPageResultResponse;
import com.chenfei.where.to.go.response.CommonResponseUtils;
import com.chenfei.where.to.go.response.CommonResultResponse;
import com.chenfei.where.to.go.service.ConfigsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/configs")
@Api(value = "字典表相关的api",tags={"字典表相关操作接口"})
@Slf4j
public class ConfigsController {

    @Resource
    private ConfigsService configsService;


    @RequestMapping(value = "/queryConfigByName/{name}",method = RequestMethod.GET)
    @ApiOperation(value = "根据字典名称查询字典信息", notes = "根据字典名称查询字典信息")
    public CommonResultResponse<ConfigsVO> queryConfigByName(
            @ApiParam(name="name",value="字典名称",required=true)
            @PathVariable("name") String name){
        ConfigsVO configsVO =configsService.queryConfigByName(name);
        return CommonResponseUtils.success(configsVO);
    }

    @RequestMapping(value = "/queryPageConfig",method = RequestMethod.POST)
    @ApiOperation(value = "分页查询字典信息", notes = "分页查询字典信息")
    public CommonPageResultResponse<List<ConfigsVO>> queryPageConfig(
            @ApiParam(name="configsDTO",value="字典名称",required=true)
            @Validated(ValidatorInterface.ConfigQuery.class) @RequestBody ConfigsDTO configsDTO){
        return configsService.queryPageConfig(configsDTO);
    }

    @RequestMapping(value = "/queryConfigByRedLock/{name}",method = RequestMethod.GET)
    @ApiOperation(value = "根据字典名称查询字典信息(分布式锁)", notes = "根据字典名称查询字典信息(分布式锁)")
    public CommonResultResponse<ConfigsVO> queryConfigByRedLock(
            @ApiParam(name="configsDTO",value="字典名称",required=true)
            @NotNull(message = "请求参数不合法") @PathVariable("name") String name) throws Exception {
        ConfigsVO configsVO =configsService.queryConfigByRedLock(name);
        return CommonResponseUtils.success(configsVO);
    }
}
