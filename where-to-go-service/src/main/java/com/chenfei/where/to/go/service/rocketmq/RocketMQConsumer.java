package com.chenfei.where.to.go.service.rocketmq;
/*
 * Created by chenfei on 2019/4/10 18:14
 */

import com.alibaba.fastjson.JSON;
import com.chenfei.where.to.go.exception.RoceketMqException;
import com.chenfei.where.to.go.model.bo.TopicAndTagInfo;
import com.chenfei.where.to.go.service.rocketmq.listen.MessageListen;
import com.chenfei.where.to.go.service.rocketmq.processor.MessageProcessor;
import com.chenfei.where.to.go.service.rocketmq.properties.TopicAndTagProperties;
import com.chenfei.where.to.go.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class RocketMQConsumer {

    @Autowired
    private SpringContextUtil springContextUtil;

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;

    @Resource
    private TopicAndTagProperties topicAndTagProperties;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() {
        List<TopicAndTagInfo> topicAndTagInfos =topicAndTagProperties.getTopicAndTagInfos();
        if(CollectionUtils.isEmpty(topicAndTagInfos)){
            throw new RoceketMqException("订阅主题信息、tag为空");
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setVipChannelEnabled(false);
        //我们自己实现的监听类
        //在监听类中增加两个消息处理类，当然可以增加更多，也就是往MessageListen类中的map集合放入处理类。
        MessageListen messageListene = new MessageListen();
        for (TopicAndTagInfo topicAndTagInfo : topicAndTagInfos) {
            messageListene.registerHandler(dealKey(topicAndTagInfo.getTopic(),topicAndTagInfo.getTag()), getMessageProcessor(topicAndTagInfo.getProcessorHandle()));
        }
        consumer.registerMessageListener(messageListene);
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

    private MessageProcessor getMessageProcessor(String processorHandle) {
        return (MessageProcessor)springContextUtil.getBean(processorHandle);
    }

    public static String dealKey(String topic,String tag) {
        StringBuilder sb = new StringBuilder();
        return (sb.append(topic)
                .append("-")
                .append(tag))
                .toString();
    }
}
