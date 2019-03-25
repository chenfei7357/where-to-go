package com.chenfei.where.to.go.response;
/*
 * Created by chenfei on 2019/3/24 14:43
 */

import com.chenfei.where.to.go.enums.ResponseCodeEnum;

public class CommonPageResponseUtils {

    public static <T> CommonPageResultResponse success(T t,Long total){
        CommonPageResultResponse<T> pageResp = new CommonPageResultResponse();
        pageResp.setCode(ResponseCodeEnum.SUCCESS.getCode());
        pageResp.setMessage(ResponseCodeEnum.SUCCESS.getDesc());
        pageResp.setTotal(total);
        pageResp.setResult(t);
        return pageResp;
    }

    public static <T> CommonPageResultResponse<T> commonResponse(int code, String message, T t) {
        CommonPageResultResponse<T> pageResp = new CommonPageResultResponse();
        pageResp.setCode(code);
        pageResp.setMessage(message);
        pageResp.setResult(t);
        return pageResp;
    }
}
