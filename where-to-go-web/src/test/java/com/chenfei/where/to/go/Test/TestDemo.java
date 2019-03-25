package com.chenfei.where.to.go.Test;
/*
 * Created by chenfei on 2019/3/25 18:20
 */

import com.alibaba.fastjson.JSON;
import com.chenfei.where.to.go.model.vo.ConfigsVO;
import com.chenfei.where.to.go.service.ConfigsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestDemo {


    @Resource
    private ConfigsService configsService;

    @Test
    public void test(){
        ConfigsVO configsVO = configsService.queryConfigByName("GLZX_02");
        System.out.println(JSON.toJSONString(configsVO));
    }

}
