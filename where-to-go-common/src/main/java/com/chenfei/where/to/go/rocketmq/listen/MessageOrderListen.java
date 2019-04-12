package com.chenfei.where.to.go.rocketmq.listen;
/*
 * Created by chenfei on 2019/4/12 14:46
 */

import com.alibaba.fastjson.JSON;
import com.chenfei.where.to.go.exception.RoceketMqException;
import com.chenfei.where.to.go.rocketmq.processor.MessageProcessor;
import com.chenfei.where.to.go.utils.RocketMQUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MessageOrderListen implements MessageListenerOrderly {

    //MessageProcessor接口的实现类放进map集合 key：tag value：MessageProcessor实习类
    private Map<String, MessageProcessor> handleMap = new HashMap<>();

    public void registerHandler(String topicAndTag, MessageProcessor messageProcessor) {
        handleMap.put(topicAndTag, messageProcessor);
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        MessageExt ext = msgs.get(0);
        String message = new String(ext.getBody());
        //获取到key
        String key = RocketMQUtils.dealKey(ext.getTopic(),ext.getTags());
        //根据key从handleMap里获取到我们的处理类
        MessageProcessor messageProcessor = handleMap.get(key);
        Object obj = null;
        try {
            // 设置自动提交
            context.setAutoCommit(true);
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
                return ConsumeOrderlyStatus.SUCCESS;
            }
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
