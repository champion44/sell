package com.imooc.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.imooc.dataobject.WxNews;

@Mapper
public interface WxNewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxNews record);

    int insertSelective(WxNews record);

    WxNews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxNews record);

    int updateByPrimaryKey(WxNews record);
}