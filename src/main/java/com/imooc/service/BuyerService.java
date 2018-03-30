/**
 * 
 */
package com.imooc.service;

import dto.OrderDTO;

/**
 * @author champion
 *
 * @version Jan 15, 20182:53:36 PM
 */
public interface BuyerService {
	
//	查询一个订单
	OrderDTO findOrderOne(String openid,String orderId);
//	取消订单
	OrderDTO cancelOrder(String openid,String orderId);
}
