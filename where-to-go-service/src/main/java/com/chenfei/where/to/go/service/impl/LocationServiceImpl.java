package com.chenfei.where.to.go.service.impl;

import com.chenfei.where.to.go.dao.LocationMapper;
import com.chenfei.where.to.go.model.vo.Location.InsertLocationVO;
import com.chenfei.where.to.go.model.vo.Location.LocationVO;
import com.chenfei.where.to.go.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @or lixt
 * @date 2019/3/25 下午5:30
 * @comment
 */

@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    LocationMapper locationMapper;

    @Override
    @Transactional
    public boolean insertLocation(InsertLocationVO record) {
        LocationVO Location = new LocationVO();
        Location.setCode(record.getCode());
        Location.setName(record.getName());
        Location.setLevel(record.getLevel());
        Location.setLng(record.getLng());
        Location.setLat(record.getLat());
        Location.setNote(record.getNote());
        int updateNum = locationMapper.insert(Location);
        return updateNum>0?true:false;
    }
}
