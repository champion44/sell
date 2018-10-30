package com.imooc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.dataobject.BillResult;
import com.imooc.repository.WxPayBillBaseResultListRepository;
import com.imooc.service.WxPayBillBaseResultListService;
@Service
public class WxPayBillBaseResultListServiceImpl implements WxPayBillBaseResultListService{

	@Autowired
	private WxPayBillBaseResultListRepository repository;
	
	@Override
	public BillResult save(BillResult wxPayBillBaseResultList) {

		return repository.save(wxPayBillBaseResultList);
	}

}
