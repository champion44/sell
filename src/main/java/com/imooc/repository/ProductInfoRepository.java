/**
 * 
 */
package com.imooc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.dataobject.ProductInfo;

/**
 * @author champion
 *
 * @version 2017年12月20日下午2:40:23
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String>{

	List<ProductInfo> findByProductStatus(Integer productStatus);
}
