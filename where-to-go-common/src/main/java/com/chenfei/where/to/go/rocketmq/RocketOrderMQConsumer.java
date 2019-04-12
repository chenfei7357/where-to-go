package com.chenfei.where.to.go.rocketmq;
/*
 * Created by chenfei on 2019/4/10 18:14
 */

import com.alibaba.fastjson.JSON;
import com.chenfei.where.to.go.exception.RoceketMqException;
import com.chenfei.where.to.go.model.bo.TopicAndTagInfo;
import com.chenfei.where.to.go.properties.TopicAndTagOrderProperties;
import com.chenfei.where.to.go.rocketmq.listen.MessageOrderListen;
import com.chenfei.where.to.go.rocketmq.processor.MessageProcessor;
import com.chenfei.where.to.go.utils.RocketMQUtils;
import com.chenfei.where.to.go.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class RocketOrderMQConsumer {

    @Autowired
    private SpringContextUtil springContextUtil;

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.groupNameOrder}")
    private String groupName;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;

    @Resource
    private TopicAndTagOrderProperties topicAndTagOrderProperties;

    @Bean
    public DefaultMQPushConsumer getRocketOrderMQConsumer() {
        List<TopicAndTagInfo> topicAndTagInfos =topicAndTagOrderProperties.getTopicAndTagOrderInfos();
        if(CollectionUtils.isEmpty(topicAndTagInfos)){
            throw new RoceketMqException("订阅主题信息、tag为空");
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setVipChannelEnabled(false);
        //我们自己实现的监听类
        //在监听类中增加两个消息处理类，当然可以增加更多，也就是往MessageListen类中的map集合放入处理类。
        MessageOrderListen messageOrderListen = new MessageOrderListen();
        for (TopicAndTagInfo topicAndTagInfo : topicAndTagInfos) {
            messageOrderListen.registerHandler(RocketMQUtils.dealKey(topicAndTagInfo.getTopic(),topicAndTagInfo.getTag()),
                    (MessageProcessor)springContextUtil.getBean(topicAndTagInfo.getProcessorHandle()));
        }
        consumer.registerMessageListener(messageOrderListen);
        try {
            for (TopicAndTagInfo topicAndTagInfo : topicAndTagInfos) {
                consumer.subscribe(topicAndTagInfo.getTopic(),topicAndTagInfo.getTag());
            }
            consumer.start();
            log.info("consumer is start !!! groupName:{},topicAndTag:{},namesrvAddr:{}",groupName, JSON.toJSONString(topicAndTagInfos),namesrvAddr);
        }catch (MQClientException e){
            log.error("consumer is start !!! groupName:{},topicAndTag:{},namesrvAddr:{}",groupName,JSON.toJSONString(topicAndTagInfos),namesrvAddr,e);
            throw new RoceketMqException(e.getMessage());
        }
        return consumer;
    }
}
