package com.chenfei.where.to.go.service;
/*
 * Created by chenfei on 2019/3/24 14:55
 */

import com.chenfei.where.to.go.model.vo.ConfigsVO;

public interface ConfigsService {

    ConfigsVO queryConfigByName(String name);
}
