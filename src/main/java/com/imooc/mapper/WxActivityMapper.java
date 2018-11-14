package com.imooc.mapper;


import com.imooc.dataobject.WxActivity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxActivity record);

    int insertSelective(WxActivity record);

    WxActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxActivity record);

    int updateByPrimaryKey(WxActivity record);
}