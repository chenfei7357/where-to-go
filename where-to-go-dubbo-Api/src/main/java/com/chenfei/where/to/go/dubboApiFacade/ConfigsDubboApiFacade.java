package com.chenfei.where.to.go.dubboApiFacade;
/*
 * Created by chenfei on 2019/4/4 17:06
 */

import com.chenfei.where.to.go.model.vo.ConfigsVO;
import com.chenfei.where.to.go.response.CommonResultResponse;

public interface ConfigsDubboApiFacade {

    CommonResultResponse<ConfigsVO> queryConfigByName(String name);
}
