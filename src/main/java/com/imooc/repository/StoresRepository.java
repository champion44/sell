package com.imooc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.dataobject.Stores;

public interface StoresRepository  extends JpaRepository<Stores, String>{

	List<Stores> findByDelFlag(Integer delFlag);
	
	Stores findByStoreId(Integer storeId);
}
