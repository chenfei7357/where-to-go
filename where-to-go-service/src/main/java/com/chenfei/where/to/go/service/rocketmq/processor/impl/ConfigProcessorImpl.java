package com.chenfei.where.to.go.service.rocketmq.processor.impl;
/*
 * Created by chenfei on 2019/4/11 11:27
 */

import com.chenfei.where.to.go.model.po.Configs;
import com.chenfei.where.to.go.service.rocketmq.processor.MessageProcessor;
import org.springframework.stereotype.Service;

@Service(value = "configProcessorImpl")
public class ConfigProcessorImpl implements MessageProcessor<Configs> {

    @Override
    public boolean handleMessage(Configs configs) {
        System.out.println("【收到了消息】："+ configs.toString());
        return true;
    }

    @Override
    public Class<Configs> getClazz() {
        return Configs.class;
    }
}
