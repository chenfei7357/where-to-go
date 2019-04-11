package com.chenfei.where.to.go.rocketmq.listen;
/*
 * Created by chenfei on 2019/4/10 18:13
 */

import com.alibaba.fastjson.JSON;
import com.chenfei.where.to.go.exception.RoceketMqException;
import com.chenfei.where.to.go.rocketmq.RocketMQConsumer;
import com.chenfei.where.to.go.rocketmq.processor.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MessageListen implements MessageListenerConcurrently {

    //MessageProcessor接口的实现类放进map集合 key：tag value：MessageProcessor实习类
    private Map<String, MessageProcessor> handleMap = new HashMap<>();

    public void registerHandler(String topicAndTag, MessageProcessor messageProcessor) {
        handleMap.put(topicAndTag, messageProcessor);
    }


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        MessageExt ext = list.get(0);
        String message = new String(ext.getBody());
        //获取到key
        String key = RocketMQConsumer.dealKey(ext.getTopic(),ext.getTags());
        //根据key从handleMap里获取到我们的处理类
        MessageProcessor messageProcessor = handleMap.get(key);
        Object obj = null;
        try {
            //将String类型的message反序列化成对应的对象。
            obj = messageProcessor.transferMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoceketMqException("对象反序列化失败了");
        }
        //处理消息
        boolean result = messageProcessor.handleMessage(obj);
        if (!result) {
            //消费大于三次，就另外处理
            if (ext.getReconsumeTimes() > 3) {
                log.info("超过三次处理未成功，【消息内容为】：{}", JSON.toJSONString(ext));
                //todo 记录日志+人工处理
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
