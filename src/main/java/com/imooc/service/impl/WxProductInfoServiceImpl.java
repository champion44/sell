package com.imooc.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.dataobject.WxProductInfo;
import com.imooc.mapper.WxProductInfoMapper;
import com.imooc.service.WxProductInfoService;

@Service
@Transactional
public class WxProductInfoServiceImpl implements WxProductInfoService{

	@Autowired
	private WxProductInfoMapper  wxProductInfoMapper;

//	@Autowired
//	private WxProductInfoRepository wxProductInfoRepository;
//	
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
	public WxProductInfo selectById(Integer id) {
		return wxProductInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer updateById(WxProductInfo wxProductInfo) {
		return wxProductInfoMapper.updateByPrimaryKey(wxProductInfo);
	}

	@Override
	public Integer deleteById(Integer id) {
		return wxProductInfoMapper.deleteByPrimaryKey(id);
	}

}
