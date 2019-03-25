package com.chenfei.where.to.go.config;
/*
 * Created by chenfei on 2019/3/25 9:49
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "exclude.login.validate")
@Data
public class ExcludeLoginProperties {

    private String path;

    private String key;
}
