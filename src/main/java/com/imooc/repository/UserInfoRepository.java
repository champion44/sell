package com.imooc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.dataobject.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

	// List<UserInfo> findByUserOpenid(String userOpenid);
	UserInfo findByUserOpenid(String userOpenid);
	
	UserInfo findByUserPhone(String userPhone);
	
	List<UserInfo> findByBookDate(String bookDate);
	
	List<UserInfo> findAll();
	
	List<UserInfo> findByPaid(Integer paid);
	
}
