package com.imooc.mapper;

import com.imooc.dataobject.WxMiniUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxMiniUserMapper {
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into wx_mini_user (id, user_phone, user_openid, \n" + 
    		"      host_phone, store_id)\n" + 
    		"    values (#{id,jdbcType=INTEGER}, #{userPhone,jdbcType=VARCHAR}, #{userOpenid,jdbcType=VARCHAR}, \n" + 
    		"      #{hostPhone,jdbcType=VARCHAR}, #{storeId,jdbcType=VARCHAR})")
    int insert(WxMiniUser record);

    int insertSelective(WxMiniUser record);

    WxMiniUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WxMiniUser record);

    int updateByPrimaryKey(WxMiniUser record);
}