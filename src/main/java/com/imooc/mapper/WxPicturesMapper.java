package com.imooc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.imooc.dataobject.WxPictures;

@Mapper
public interface WxPicturesMapper {
    int insert(WxPictures record);

    int insertSelective(WxPictures record);
    
//    List<WxPictures> selectByPic();
}