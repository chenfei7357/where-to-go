package com.chenfei.where.to.go.service.rocketmq.producerUtils;
/*
 * Created by chenfei on 2019/4/10 18:30
 */

import com.chenfei.where.to.go.exception.RoceketMqException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class RocketMQProducerUtil {

    @Resource
    private DefaultMQProducer defaultMQProducer;

    /**
     * 发送普通消息的方法
     * @param topic
     * @param tags
     * @param keys
     * @param contentText
     * @return
     * @throws RoceketMqException
     */
    public SendResult sendMessage(String topic,String tags,String keys,String contentText){
        if(StringUtils.isBlank(topic)){
            throw  new RoceketMqException("topic is blank");
        }
        if(StringUtils.isBlank(tags)){
            throw  new RoceketMqException("tags is blank");
        }
        if(StringUtils.isBlank(keys)){
            throw  new RoceketMqException("keys is blank");
        }
        if(StringUtils.isBlank(contentText)){
            throw  new RoceketMqException("contentText is blank");
        }
        Message message = new Message(topic,tags,keys,contentText.getBytes());
        try {
            SendResult sendResult = this.defaultMQProducer.send(message);
            return sendResult;
        } catch (Exception e) {
            log.error("message send error :{}",e.getMessage());
            e.printStackTrace();
            throw new RoceketMqException(e);
        }
    }
}
