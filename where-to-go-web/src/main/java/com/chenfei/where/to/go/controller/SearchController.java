package com.chenfei.where.to.go.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author lixt
 * @date 2019/3/21 下午6:42
 * @comment
 */

@Controller
@RequestMapping({"search"})
@Api(description = "搜索附近")
public class SearchController {

    String lng = "121.456798";//经度
    String lat = "31.230647";//纬度


    //周边检索
    //http://api.map.baidu.com/place/search?query=海底捞
    // &location=31.204055632862,121.41117785465&radius=1000&region=上海&output=html&src=webapp.baidu.openAPIdemo
    @ApiOperation(value = "搜索附近美食", notes = "搜索附近美食")
    @RequestMapping(value = {"/eat-{name}"}, method = {RequestMethod.GET})
    public String searchEat(@PathVariable(value = "name", required = true) String name) throws Exception {
        String msg = name;
        msg = new String(msg.getBytes("GBK"), "iso-8859-1");
        return "redirect:http://api.map.baidu.com/place/search?query=" + msg +
                "&location=" + lat + "," + lng + "&radius=1000&output=html&coord_type=bd09ll&src=webapp.baidu.openAPIdemo";
    }


}
