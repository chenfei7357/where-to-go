package com.chenfei.where.to.go.utils;
/*
 * Created by chenfei on 2019/4/12 23:51
 */

public class RocketMQUtils {

    public static String dealKey(String topic,String tag) {
        StringBuilder sb = new StringBuilder();
        return (sb.append(topic)
                .append("-")
                .append(tag))
                .toString();
    }
}
