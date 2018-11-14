package com.imooc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.dataobject.WxProductInfo;

public interface WxProductInfoRepository extends JpaRepository<WxProductInfo,Integer>{
	
	WxProductInfo save(WxProductInfo wxProductInfo);
}
