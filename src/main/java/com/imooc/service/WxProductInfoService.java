package com.imooc.service;



import java.math.BigInteger;
import java.util.List;

import com.imooc.dataobject.WxProductInfo;

public interface WxProductInfoService {

	Integer insert(WxProductInfo wxProductInfo);
	
	List<WxProductInfo> findList();
	
	WxProductInfo selectById(BigInteger bigInteger);
	
	Integer updateByObject(WxProductInfo wxProductInfo);
	
	Integer deleteById(BigInteger bigInteger);
	
	WxProductInfo save(WxProductInfo wxProductInfo);
}
