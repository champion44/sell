/**
 * 
 */
package com.imooc.service;

import java.util.List;

import com.imooc.dataobject.ProductCategory;

/**
 * @author champion
 *
 * @version 2017年12月20日下午1:44:52
 */
public interface CategoryService {

	ProductCategory findOne(Integer categoryId);
	
	List<ProductCategory> findAll();
	
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
