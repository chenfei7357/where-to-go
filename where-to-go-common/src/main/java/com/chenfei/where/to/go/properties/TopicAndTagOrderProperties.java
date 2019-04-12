package com.chenfei.where.to.go.properties;
/*
 * Created by chenfei on 2019/4/11 15:10
 */

import com.chenfei.where.to.go.model.bo.TopicAndTagInfo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@ConfigurationProperties(prefix = "rocketmq.consumer")
@Component
public class TopicAndTagOrderProperties {

    private List<TopicAndTagInfo> topicAndTagOrderInfos;
}
