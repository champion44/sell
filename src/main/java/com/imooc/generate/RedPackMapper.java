package com.imooc.generate;

import org.apache.ibatis.annotations.Mapper;

import com.imooc.generate.RedPack;
@Mapper
public interface RedPackMapper {
    int insert(RedPack record);

    int insertSelective(RedPack record);
}