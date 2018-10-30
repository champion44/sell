package com.imooc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.dataobject.Bill;
import com.imooc.repository.BillRepository;
import com.imooc.service.BillService;

@Service
public class BillServiceImpl implements BillService{

	@Autowired
	private BillRepository repository;
	
	@Override
	public Bill save(Bill bill) {
		return repository.save(bill);
	}

}
