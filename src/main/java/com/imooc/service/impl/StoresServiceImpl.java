package com.imooc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.imooc.dataobject.Stores;
import com.imooc.repository.StoresRepository;
import com.imooc.service.StoresService;

@Service
public class StoresServiceImpl implements StoresService {

	@Autowired
	private StoresRepository repository;

	@Override
	public Stores findOne(String id) {
		return repository.findOne("1");
	}

	@Override
	public List<Stores> findList(Integer delFlag) {
		List<Stores> result = repository.findByDelFlag(delFlag);
		return result;
	}

	@Override
	public Page<Stores> findList(Pageable pageable) {
		Page<Stores> storesPage = repository.findAll(pageable);
		return storesPage;
	}

	@Override
	public Stores findByStoreId(Integer storeId) {
		Stores store = repository.findByStoreId(storeId);
		return store;
	}

	@Override
	public Stores save(Stores stores) {
		return repository.save(stores);
	}

	@Override
	public List<Stores> findList() {
		return repository.findAll();
	}

}
