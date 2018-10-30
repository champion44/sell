package com.imooc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.imooc.dataobject.sgjc;
import com.imooc.dataobject.sgjcKey;
@Mapper
public interface sgjcMapper {
    int deleteByPrimaryKey(sgjcKey key);

    int insert(sgjc record);

    int insertSelective(sgjc record);
    
    /*
     *  where mdbh = #{mdbh,jdbcType=INTEGER}
      and jcrq = #{jcrq,jdbcType=CHAR}
      and khxm = #{khxm,jdbcType=VARCHAR}
      and sjh = #{sjh,jdbcType=VARCHAR}
      and khh = #{khh,jdbcType=VARCHAR}
     */
    @Select("SELECT * FROM sgjc WHERE mdbh = #{mdbh} & jcrq = #{jcrq}&khxm = #{khxm}&sjh = #{sjh}&khh = #{khh} limit 1")
    sgjc selectByPrimaryKey(sgjcKey key);

    int updateByPrimaryKeySelective(sgjc record);

    int updateByPrimaryKey(sgjc record);
}