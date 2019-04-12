package com.chenfei.where.to.go.rocketmq;
/*
 * Created by chenfei on 2019/4/10 18:07
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RocketOrderMQProducer {

    @Value("${rocketmq.producer.groupNameOrder}")
    private String groupName;
    @Value("${rocketmq.namesrvAddr}")
    private String nameserAddr;
    @Value("${rocketmq.producer.maxMessageSize}")
    private int maxMessageSize;
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private int sendMsgTimeout;

    private DefaultMQProducer producer;

    @Bean(name = "defaultOrderMQProducer")
    public DefaultMQProducer getRocketMQProducer() {
        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(nameserAddr);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            log.info("rocketMQ is start !!groupName : {},nameserAddr:{}", groupName, nameserAddr);
        } catch (MQClientException e) {
            log.error(String.format("rocketMQ start error,{}", e.getMessage()));
            e.printStackTrace();
        }
        return producer;
    }

}
