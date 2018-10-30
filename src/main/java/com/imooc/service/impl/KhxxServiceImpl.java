package com.imooc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.dataobject.Khxx;
import com.imooc.mapper.khxxMapper;
import com.imooc.service.KhxxService;

@Service
public class KhxxServiceImpl {

	@Autowired
	private khxxMapper khxxmapper;
	
	public Khxx selectByPrimaryKey(Long khbh) {
		
		return khxxmapper.selectByPrimaryKey(khbh);
	}

}
