package com.chenfei.where.to.go.dao;


import com.chenfei.where.to.go.model.po.Configs;

public interface ConfigsMapper {

    int deleteByPrimaryKey(String conName);

    int insert(Configs record);

    int insertSelective(Configs record);

    Configs selectByPrimaryKey(String conName);

    int updateByPrimaryKeySelective(Configs record);

    int updateByPrimaryKey(Configs record);
}