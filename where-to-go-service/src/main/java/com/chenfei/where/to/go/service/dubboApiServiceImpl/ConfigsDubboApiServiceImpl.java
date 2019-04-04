package com.chenfei.where.to.go.service.dubboApiServiceImpl;
/*
 * Created by chenfei on 2019/4/4 17:08
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.chenfei.where.to.go.dubboApiFacade.ConfigsDubboApiFacade;
import com.chenfei.where.to.go.model.vo.ConfigsVO;
import com.chenfei.where.to.go.response.CommonResultResponse;

@Service
public class ConfigsDubboApiServiceImpl implements ConfigsDubboApiFacade {


    @Override
    public CommonResultResponse<ConfigsVO> queryConfigByName(String name) {

        return null;
    }
}

