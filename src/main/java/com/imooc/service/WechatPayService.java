package com.imooc.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.lly835.bestpay.model.RefundResponse;

import dto.OrderDTO;

public interface WechatPayService {

	WxPayUnifiedOrderResult unifiedOrder(OrderDTO orderDTO);

	WxPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws WxPayException;
	
	RefundResponse refund(OrderDTO orderDTO);
}
