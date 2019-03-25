package com.chenfei.where.to.go.response;
/*
 * Created by chenfei on 2019/3/24 14:17
 */

import lombok.Data;

@Data
public class CommonResultResponse<T> extends CommonResponse{

    private T result;


}
