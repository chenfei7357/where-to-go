package com.chenfei.where.to.go.config;
/*
 * Created by chenfei on 2019/4/8 11:32
 */

import com.chenfei.where.to.go.task.SimpleJobDemo;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class ElasticJobConfig {

    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    @Qualifier("taskDataSource")
    private DataSource taskDataSource;
    /**
     * 配置任务详细信息
     * @param jobClass
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @return
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
            final String cron,final int shardingTotalCount,final String shardingItemParameters,
            final String description) {

        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                        .description(description)
                        .shardingItemParameters(shardingItemParameters)
                        .build()
                ,jobClass.getCanonicalName())
        ).overwrite(true).build();
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SimpleJobDemo simpleJob,
            @Value("${stockJob.cron}") final String cron,
            @Value("${stockJob.shardingTotalCount}") final int shardingTotalCount,
            @Value("${stockJob.shardingItemParameters}") final String shardingItemParameters,
            @Value("${stockJob.description}") final String description) {
        return new SpringJobScheduler(simpleJob, regCenter,
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount,
                        shardingItemParameters,description),
                new JobEventRdbConfiguration(taskDataSource));// 定义日志数据库事件溯源配置
    }
}
