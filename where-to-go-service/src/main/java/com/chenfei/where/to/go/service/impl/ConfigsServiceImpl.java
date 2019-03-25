package com.chenfei.where.to.go.service.impl;
/*
 * Created by chenfei on 2019/3/24 14:55
 */

import com.chenfei.where.to.go.enums.BizEnum;
import com.chenfei.where.to.go.exception.CommonException;
import com.chenfei.where.to.go.dao.ConfigsMapper;
import com.chenfei.where.to.go.model.po.Configs;
import com.chenfei.where.to.go.model.vo.ConfigsVO;
import com.chenfei.where.to.go.service.ConfigsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfigsServiceImpl implements ConfigsService {

    @Resource
    private ConfigsMapper configsMapper;
    @Override
    public ConfigsVO queryConfigByName(String name) {
        if(StringUtils.isBlank(name)){
            throw new CommonException(BizEnum.PARAMETER_EXCEPTION.getCode(),BizEnum.PARAMETER_EXCEPTION.getDesc());
        }
        ConfigsVO vo = new ConfigsVO();
        Configs configs = configsMapper.selectByPrimaryKey(name);
        if (configs != null) {
            BeanUtils.copyProperties(configs,vo);
        }
        return vo;
    }
}
