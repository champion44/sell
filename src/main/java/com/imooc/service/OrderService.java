/**
 * 
 */
package com.imooc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.imooc.dataobject.OrderMaster;

import dto.OrderDTO;

/**
 * @author champion
 *
 * @version Dec 25, 201710:44:25 AM
 */
public interface OrderService {

	// 创建订单
	OrderDTO create(OrderDTO orderDTO);

	// 创建微信订单
	OrderMaster createWX(OrderDTO orderDTO);

	// 查询单个订单
	OrderDTO findOne(String orderId);

	// 查询订单列表
	Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

	// 取消订单
	OrderDTO cancel(OrderDTO orderDTO);

	// 更新订单
	OrderMaster update(OrderDTO orderDTO);

	// 完结订单
	OrderDTO finish(OrderDTO orderDTO);

	// 支付订单
	OrderDTO paid(OrderDTO orderDTO);

	// 查询所有订单列表
	Page<OrderMaster> findList(Pageable pageable);

	List<OrderMaster> findByBuyerOpenid(Specification<OrderMaster> spec,Sort sort);

	List<OrderMaster> findByStoreId(Specification<OrderMaster> spec, Sort sort);

	List<OrderDTO> findByPayStatus(Integer patStatus, Sort sort);
	
	List<OrderMaster> findAll(Specification<OrderMaster> spec,Sort sort); 

}
