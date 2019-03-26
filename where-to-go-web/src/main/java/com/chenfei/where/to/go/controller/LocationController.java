package com.chenfei.where.to.go.controller;

import com.alibaba.fastjson.JSONObject;
import com.chenfei.where.to.go.enums.BizEnum;
import com.chenfei.where.to.go.model.vo.Location.InsertLocationVO;
import com.chenfei.where.to.go.response.CommonResponseUtils;
import com.chenfei.where.to.go.response.CommonResultResponse;
import com.chenfei.where.to.go.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixt
 * @date 2019/3/22 下午3:38
 * @comment
 */

@RestController
@RequestMapping({"Location"})
@Api(description = "经纬度")
@Slf4j
public class LocationController {

    @Autowired
    private LocationService locationService;

    private String lng = "121.456798";//经度
    private String lat = "31.230647";//纬度

    @ApiOperation(value = "根据输入地址获取经纬度", notes = "根据输入地址获取经纬度")
    @RequestMapping(value = {"/location-{addrName}"}, method = {RequestMethod.GET})
    public CommonResultResponse getLocationLngLat(String addrName) {
        //http://www.nmc.cn/f/rest/province全国各省份
        InsertLocationVO record = new InsertLocationVO();
        //创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建Get实例
        HttpGet httpGet = new HttpGet("http://api.map.baidu.com/geocoder?address=" + addrName + "&output=json&coord_type=bd09ll&src=webapp.baidu.openAPIdemo");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3573.0 Safari/537.36");
        try {
            //获得Response
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                //当响应状态码为200时，获得该网页源码并打印
                String entity = EntityUtils.toString(response.getEntity(), "utf-8");
                JSONObject object = (JSONObject) JSONObject.parse(entity);
                Object resultType = object.get("result");
                if (resultType instanceof JSONObject) {
                    String lngLat = object.getJSONObject("result").get("location").toString();
                    JSONObject lngLatJson = (JSONObject) JSONObject.parse(lngLat);
                    lat = lngLatJson.get("lat").toString();
                    lng = lngLatJson.get("lng").toString();
                    String level = object.getJSONObject("result").get("level").toString();
                    record.setCode(lng + lat);
                    record.setName(addrName);
                    record.setLevel(level);
                    record.setLng(lng);
                    record.setLat(lat);
                } else {
                    log.info("地址输入有误！！");
                    return CommonResponseUtils.commonResponse(BizEnum.PARAMETER_EXCEPTION.getCode(), BizEnum.PARAMETER_EXCEPTION.getDesc(), "未找到该地址，请确认地址是否有误！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean ret = locationService.insertLocation(record);
        if (ret) {
            return CommonResponseUtils.success(record);
        } else {
            return CommonResponseUtils.error();
        }
    }
}
