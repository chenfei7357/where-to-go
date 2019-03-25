package com.chenfei.where.to.go.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixt
 * @date 2019/3/22 下午3:38
 * @comment
 */

@RestController
@RequestMapping({"JWD"})
@Api(description = "经纬度")
public class LngLatController {

    String lng = "121.456798";//经度
    String lat = "31.230647";//纬度

    @ApiOperation(value = "根据输入地址获取经纬度", notes = "根据输入地址获取经纬度")
    @RequestMapping(value = {"/lngLat-{addrName}"}, method = {RequestMethod.GET})
    public String getLngLat(String addrName) {
        //http://www.nmc.cn/f/rest/province全国各省份
        JSONObject object = new JSONObject();
        //创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String entityr = "";
        //创建Get实例
        HttpGet httpGet = new HttpGet("http://api.map.baidu.com/geocoder?address=" + addrName + "&output=json&coord_type=bd09ll&src=webapp.baidu.openAPIdemo");
        //添加请求头的信息，模拟浏览器访问
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3573.0 Safari/537.36");

        try {
            //获得Response
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                //当响应状态码为200时，获得该网页源码并打印
                String entity = EntityUtils.toString(response.getEntity(), "utf-8");
                object = (JSONObject) JSONObject.parse(entity);
                entityr = object.getJSONObject("result").get("location").toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        object = (JSONObject) JSONObject.parse(entityr);
        lat = object.get("lat").toString();
        lng = object.get("lng").toString();

        return "{" + lat + "," + lng + "}";
    }
}
