/**
 * 
 */
package com.imooc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.dataobject.ProductInfo;

import dto.CartDTO;

/**
 * @author champion
 *
 * @version 2017年12月20日下午3:11:41
 */
public interface ProductService {

	ProductInfo findOne(String productId);
	
	List<ProductInfo> findUpAll();
	
	Page<ProductInfo> findAll(Pageable pageable);
	
	ProductInfo save(ProductInfo productInfo);
	
//	加入库存
	void increaseStock(List<CartDTO> cartDTOList);
//	减少库存
	void decreaseStock(List<CartDTO> cartDTOList);

}
