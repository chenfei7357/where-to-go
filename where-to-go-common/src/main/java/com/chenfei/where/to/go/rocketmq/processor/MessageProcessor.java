package com.chenfei.where.to.go.rocketmq.processor;
/*
 * Created by chenfei on 2019/4/10 18:11
 */

import com.chenfei.where.to.go.utils.JsonUtil2;

public interface MessageProcessor<T>{

    boolean handleMessage(T message);

    Class<T> getClazz();

    default T transferMessage(String message) {
        return JsonUtil2.fromJson(message, getClazz());
    }
}
