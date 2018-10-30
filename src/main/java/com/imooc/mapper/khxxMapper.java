package com.imooc.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.imooc.dataobject.Khxx;

@Mapper
public interface khxxMapper {
	
//    int deleteByPrimaryKey(Long khbh);

//    int insert(khxx record);

//    int insertSelective(khxx record);

    @Select("SELECT * FROM khxx WHERE khbh = #{khbh} ")
    Khxx selectByPrimaryKey(@Param("khbh") Long khbh);

//    int updateByPrimaryKeySelective(khxx record);

//    int updateByPrimaryKey(khxx record);
}