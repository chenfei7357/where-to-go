package com.chenfei.where.to.go.properties;
/*
 * Created by chenfei on 2019/3/29 17:02
 */

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RedissonProperties {

    @Value("${redisson.cluster-addresses}")
    private String clusterAddresses;

    private int timeout = 3000;

    private int database = 0;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize=10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;
}
