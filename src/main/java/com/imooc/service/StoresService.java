package com.imooc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.dataobject.Stores;

public interface StoresService {

	Stores findOne(String id);

	List<Stores> findList();
	
	List<Stores> findList(Integer delFlag);
	
	Page<Stores> findList(Pageable pageable);
	
	Stores findByStoreId(Integer storeId);
	
	Stores save(Stores stores);
}
