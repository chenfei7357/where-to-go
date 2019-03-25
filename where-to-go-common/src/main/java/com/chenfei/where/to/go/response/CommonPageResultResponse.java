package com.chenfei.where.to.go.response;
/*
 * Created by chenfei on 2019/3/24 14:19
 */

import lombok.Data;

@Data
public class CommonPageResultResponse<T> extends CommonResultResponse{

    private Long total;
}
