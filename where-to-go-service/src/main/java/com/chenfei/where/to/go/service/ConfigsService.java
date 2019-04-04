package com.chenfei.where.to.go.service;
/*
 * Created by chenfei on 2019/3/24 14:55
 */

import com.chenfei.where.to.go.model.dto.ConfigsDTO;
import com.chenfei.where.to.go.model.vo.ConfigsVO;
import com.chenfei.where.to.go.response.CommonPageResultResponse;

import java.util.List;

public interface ConfigsService {

    ConfigsVO queryConfigByName(String name);

    CommonPageResultResponse<List<ConfigsVO>> queryPageConfig(ConfigsDTO configsDTO);

    ConfigsVO queryConfigByRedLock(String name) throws Exception;
}
