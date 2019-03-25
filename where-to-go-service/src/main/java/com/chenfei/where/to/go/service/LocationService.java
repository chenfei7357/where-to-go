package com.chenfei.where.to.go.service;

import com.chenfei.where.to.go.model.vo.Location.InsertLocationVO;

/**
 * @author lixt
 * @date 2019/3/25 下午5:21
 * @comment
 */

public interface LocationService {

    //LocationVO findLocationById(Long id);

    boolean insertLocation(InsertLocationVO insertLocationVO);

    //boolean deleteLocation(Long id);

    //boolean updateLocation(LocationVO updateLocationVO);

    //Page<LocationVO> findByPageQuery(LocationVO pageQuery);

    //LocationVO findLocationByAccount(String account);

    //int isExistLocationByAccount(String account);

    //List<LocationVO> findLocationsByGroupIds(String groupIds);
}
