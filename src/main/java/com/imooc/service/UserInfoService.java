package com.imooc.service;


import java.util.List;

import com.imooc.dataobject.UserInfo;

public interface UserInfoService {

	UserInfo save(UserInfo userInfo);
	
	List<UserInfo> findByBookDate(String bookDate);
	
	List<UserInfo> findAll();
	
	List<UserInfo> findByStage(Integer stage);

	UserInfo findByUserOpenid(String userOpenid);
	
	UserInfo findByUserPhone(String userPhone);
	
}
