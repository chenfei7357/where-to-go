package com.chenfei.where.to.go.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixt
 * @date 2019/3/21 下午3:48
 * @comment
 */

@RestController
@RequestMapping({"test"})
@Api(description = "测试")

public class TestController {

    @ApiOperation(value = "测试", notes = "测试")
    @RequestMapping(value = {"/test"}, method = {RequestMethod.GET})
    public String getUserDetailByAccount() {
        return "hello ！";
    }
}
