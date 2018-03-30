/**
 * 
 */
package com.imooc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.dataobject.ProductCategory;
import com.imooc.repository.ProductCategoryRepository;
import com.imooc.service.CategoryService;

/**
 * @author champion
 *
 * @version 2017年12月20日下午1:48:20
 */
@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private ProductCategoryRepository repository;
	
	@Override
	public ProductCategory findOne(Integer categoryId) {
		return repository.findOne(categoryId);
	}


	@Override
	public List<ProductCategory> findAll() {
		return repository.findAll();
	}

	
	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		return repository.findByCategoryTypeIn(categoryTypeList);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return repository.save(productCategory);
	}
	

}
