package com.chenfei.where.to.go.service.dubboApiServiceImpl;
/*
 * Created by chenfei on 2019/4/4 17:08
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.chenfei.where.to.go.dubboApiFacade.ConfigsDubboApiFacade;
import com.chenfei.where.to.go.model.vo.ConfigsVO;
import com.chenfei.where.to.go.response.CommonResponseUtils;
import com.chenfei.where.to.go.response.CommonResultResponse;
import com.chenfei.where.to.go.service.ConfigsService;

import javax.annotation.Resource;

@Service
public class ConfigsDubboApiServiceImpl implements ConfigsDubboApiFacade {

    @Resource
    private ConfigsService configsService;

    @Override
    public CommonResultResponse<ConfigsVO> queryConfigByName(String name) {
        ConfigsVO configsVO = configsService.queryConfigByName(name);
        return CommonResponseUtils.success(configsVO);
    }
}

