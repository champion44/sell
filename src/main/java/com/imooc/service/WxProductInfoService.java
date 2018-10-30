package com.imooc.service;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.dataobject.WxProductInfo;

public interface WxProductInfoService {

	Integer insert(WxProductInfo wxProductInfo);
	
	List<WxProductInfo> findList();
	
	WxProductInfo selectById(Integer id);
	
	Integer updateById(WxProductInfo wxProductInfo);
	
	Integer deleteById(Integer id);
}
