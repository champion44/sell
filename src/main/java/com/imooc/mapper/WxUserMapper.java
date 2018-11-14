package com.imooc.mapper;

import com.imooc.dataobject.WxUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);
}