package com.chenfei.where.to.go.config;

/**
 * @author lixt
 * @date 2019/3/21 下午3:42
 * @comment
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Bean
    public Docket createRestApi() {
        return (new Docket(DocumentationType.SWAGGER_2))
                .apiInfo(this.apiInfo())
                .enable(swaggerEnable)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chenfei.where.to.go.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(apiKeys());
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder())
                .title("where2goAPI接口说明")
                .description("")
                .termsOfServiceUrl("")
                .contact(new Contact("where2go", "http://www.where2go.com", ""))
                .version("2.0")
                .build();
    }

    private List<ApiKey> apiKeys(){
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "x-token","header"));
        return apiKeyList;
//        return Collections.singletonList(new ApiKey("", "x-token","header"));
    }
}
