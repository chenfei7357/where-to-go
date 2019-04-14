package com.chenfei.where.to.go.service.impl;
/*
 * Created by chenfei on 2019/3/24 14:55
 */

import com.alibaba.fastjson.JSON;
import com.chenfei.where.to.go.annotation.RedLock;
import com.chenfei.where.to.go.dao.ConfigsMapper;
import com.chenfei.where.to.go.enums.BizEnum;
import com.chenfei.where.to.go.exception.CommonException;
import com.chenfei.where.to.go.model.dto.ConfigsDTO;
import com.chenfei.where.to.go.model.po.Configs;
import com.chenfei.where.to.go.model.vo.ConfigsVO;
import com.chenfei.where.to.go.response.CommonPageResponseUtils;
import com.chenfei.where.to.go.response.CommonPageResultResponse;
import com.chenfei.where.to.go.service.ConfigsService;
import com.chenfei.where.to.go.utils.RedisUtil;
import com.chenfei.where.to.go.utils.RocketMQProducerUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConfigsServiceImpl implements ConfigsService {

    @Resource
    private ConfigsMapper configsMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RocketMQProducerUtil rocketMQProducerUtil;

    @Override
    public ConfigsVO queryConfigByName(String name) {
        if(StringUtils.isBlank(name)){
            throw new CommonException(BizEnum.PARAMETER_EXCEPTION.getCode(),BizEnum.PARAMETER_EXCEPTION.getDesc());
        }
        ConfigsVO vo = new ConfigsVO();
        String key="test-configs-key";
        Configs configs =new Configs();
        if (!redisUtil.hasKey(key)){
            configs = configsMapper.selectByPrimaryKey(name);
            redisUtil.set(key, configs, 300);
        }else {
            configs=((Configs) redisUtil.get(key));
        }
        BeanUtils.copyProperties(configs,vo);

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

    @Override
    @RedLock(lockName = "config",waitTime = 20,leaseTime = 10)
    public ConfigsVO queryConfigByRedLock(String name) throws Exception {
        ConfigsVO configsVO = new ConfigsVO();
        log.info("ThreadName"+Thread.currentThread().getName()+"进入请求");
        configsVO.setConName("分布式锁测试");
        Thread.sleep(1500);
        return configsVO;
    }

    @Override
    public ConfigsVO queryConfigSendToMQ(String name){
        for(int i=0;i<5;i++){
            ConfigsVO configsVO = new ConfigsVO();
            configsVO.setConName("发送MQtest"+i);
            rocketMQProducerUtil.sendMessage("where-to-go-2","test-2","2", JSON.toJSONString(configsVO));
            rocketMQProducerUtil.sendOrderMessage("where-to-go-order-3","test-order-3","1000"+i, JSON.toJSONString(configsVO),2);
        }
        ConfigsVO vo = new ConfigsVO();
        return vo;
    }
}
