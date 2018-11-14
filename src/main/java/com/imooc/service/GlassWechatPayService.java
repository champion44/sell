/**
 * 
 */
package com.imooc.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.imooc.dataobject.WxOrder;
import com.lly835.bestpay.model.RefundResponse;

import dto.OrderDTO;
import dto.WxOrderDTO;

/**
* @author champion
* @version 创建时间：Nov 6, 2018 10:55:23 AM
* 类说明
*/
/**
 * @author champion
 *
 */
public interface GlassWechatPayService {
	WxPayUnifiedOrderResult unifiedOrder(WxOrderDTO wxOrder);

	WxPayUnifiedOrderResult unifiedOrder(OrderDTO orderDTO);
	
	WxPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws WxPayException;

	RefundResponse refund(OrderDTO orderDTO);

	WxPayBillResult downloadBill(String billDate, String billType, String tarType, String deviceInfo)
			throws WxPayException;
}
