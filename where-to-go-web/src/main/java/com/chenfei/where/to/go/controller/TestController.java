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
    public String test() {
        return "hello ！";
    }


//    public static String getLngLat(String addrName){
//        //http://www.nmc.cn/f/rest/province全国各省份
//        JSONObject object = new JSONObject();
//        //创建客户端
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        String entityr = "";
//        //创建Get实例
//        HttpGet httpGet = new HttpGet("http://api.map.baidu.com/geocoder?address="+addrName+"&output=json&coord_type=bd09ll&src=webapp.baidu.openAPIdemo");
//        //添加请求头的信息，模拟浏览器访问
//        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3573.0 Safari/537.36");
//
//        try{
//            //获得Response
//            CloseableHttpResponse response = httpClient.execute(httpGet);
//            if(response.getStatusLine().getStatusCode() == 200){
//                //当响应状态码为200时，获得该网页源码并打印
//                String entity = EntityUtils.toString(response.getEntity(),"utf-8");
//                object = (JSONObject) JSONObject.parse(entity);
//                entityr = object.getJSONObject("result").get("location").toString();
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(entityr.toString());
//        return entityr;
//    }


//    public static void main(String[] args) throws Exception {
//        getLngLat("上海嘉里中心");
//    }

}
