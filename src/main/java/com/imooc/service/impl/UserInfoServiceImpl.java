package com.imooc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.dataobject.UserInfo;
import com.imooc.repository.UserInfoRepository;
import com.imooc.service.UserInfoService;
@Service
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Override
	public UserInfo save(UserInfo userInfo) {
		UserInfo result = userInfoRepository.save(userInfo);
		return result;
	}

	@Override
	public UserInfo findByUserOpenid(String userOpenid) {
		UserInfo userInfo = userInfoRepository.findByUserOpenid(userOpenid);
		return userInfo;
		/*
		try {
		 List<UserInfo> user =	userInfoRepository.findByUserOpenid(userOpenid);
		 System.out.println(user.size());
		 if(user.size()==1) {
			 return true;
		 }
		// log.info(JsonUtil.toJson(user));
		} catch (Exception e) {
		}
		return false;
		*/
	}

	@Override
	public List<UserInfo> findByBookDate(String bookDate) {
		List<UserInfo>  userInfos = userInfoRepository.findByBookDate(bookDate);
		return userInfos;
	}

	@Override
	public UserInfo findByUserPhone(String userPhone) {
		UserInfo userInfo = userInfoRepository.findByUserPhone(userPhone);
		return userInfo;
	}

	@Override
	public List<UserInfo> findAll() {
		List<UserInfo> userList = userInfoRepository.findAll();
		return userList;
	}

	@Override
	public List<UserInfo> findByStage(Integer stage) {
		List<UserInfo> userList = userInfoRepository.findByPaid(stage);
		return userList;
	}

}
