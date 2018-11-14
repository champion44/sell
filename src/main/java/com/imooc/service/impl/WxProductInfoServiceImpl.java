package com.imooc.service.impl;


import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.dataobject.WxProductInfo;
import com.imooc.mapper.WxProductInfoMapper;
import com.imooc.repository.WxProductInfoRepository;
import com.imooc.service.WxProductInfoService;

@Service
@Transactional
public class WxProductInfoServiceImpl implements WxProductInfoService{

	@Autowired
	private WxProductInfoMapper  wxProductInfoMapper;

	@Autowired
	private WxProductInfoRepository wxProductInfoRepository;
	
	@Override
	public Integer insert(WxProductInfo wxProductInfo) {
		
		 return wxProductInfoMapper.insert(wxProductInfo);
	}

	@Override
	public List<WxProductInfo> findList() {
		
		List<WxProductInfo> wxProductInfoPage = wxProductInfoMapper.findList();
		return wxProductInfoPage;
	}

	@Override
	public WxProductInfo selectById(BigInteger  integer) {
		return wxProductInfoMapper.selectByPrimaryKey(integer);
	}

	@Override
	public Integer updateByObject(WxProductInfo wxProductInfo) {
		return wxProductInfoMapper.updateByPrimaryKey(wxProductInfo);
	}

	@Override
	public Integer deleteById(BigInteger i) {
		return wxProductInfoMapper.deleteByPrimaryKey(i);
	}

	@Override
	public WxProductInfo save(WxProductInfo wxProductInfo) {
		return wxProductInfoRepository.save(wxProductInfo);
	}

}
