package com.imooc.service;

import com.imooc.dataobject.WxOrder;

public interface WxOrderService {

	int insert(WxOrder record);
	
	WxOrder selectByPrimaryKey(String id);
	
	Integer paid(WxOrder orderDTO);
	
	int update(WxOrder orderDTO);
}
