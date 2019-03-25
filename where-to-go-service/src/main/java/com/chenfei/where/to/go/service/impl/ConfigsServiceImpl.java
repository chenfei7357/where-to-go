package com.chenfei.where.to.go.service.impl;
/*
 * Created by chenfei on 2019/3/24 14:55
 */

import com.chenfei.where.to.go.dao.ConfigsMapper;
import com.chenfei.where.to.go.enums.BizEnum;
import com.chenfei.where.to.go.exception.CommonException;
import com.chenfei.where.to.go.model.dto.ConfigsDTO;
import com.chenfei.where.to.go.model.po.Configs;
import com.chenfei.where.to.go.model.vo.ConfigsVO;
import com.chenfei.where.to.go.response.CommonPageResponseUtils;
import com.chenfei.where.to.go.response.CommonPageResultResponse;
import com.chenfei.where.to.go.service.ConfigsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CommonPageResultResponse<List<ConfigsVO>> queryPageConfig(ConfigsDTO configsDTO) {
        if (configsDTO == null) {
            throw new CommonException(BizEnum.PARAMETER_EXCEPTION.getCode(),BizEnum.PARAMETER_EXCEPTION.getDesc());
        }
        if (configsDTO.getPage() == null || configsDTO.getSize()==null) {
            throw new CommonException(BizEnum.PAGE_PARAMETER_NULL.getCode(),BizEnum.PAGE_PARAMETER_NULL.getDesc());
        }
        PageHelper.startPage(configsDTO.getPage(),configsDTO.getSize());
        PageInfo<Configs> pageInfo = new PageInfo(configsMapper.queryPage(configsDTO));
        List<ConfigsVO> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            result= pageInfo.getList().stream().map(Configs -> {
                ConfigsVO configsVO = new ConfigsVO();
                BeanUtils.copyProperties(Configs, configsVO);
                return configsVO;
            }).collect(Collectors.toList());
        }
        return CommonPageResponseUtils.success(result,pageInfo.getTotal());
    }
}
