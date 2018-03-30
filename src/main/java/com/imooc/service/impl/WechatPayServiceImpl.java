package com.imooc.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.imooc.service.WechatPayService;
import com.imooc.utils.JsonUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WechatPayServiceImpl implements WechatPayService {

	@Autowired
	private WxPayService wxPayService;

	@Autowired
	private BestPayServiceImpl bestPayServiceImpl;

	@Override
	public WxPayUnifiedOrderResult unifiedOrder(OrderDTO orderDTO) {
		WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
		request.setBody("蚁百惠支付-商户协议收款");
		request.setOutTradeNo(orderDTO.getOrderId());
//		Integer totalFee = orderDTO.getOrderAmount()*100;
		BigDecimal bd=new BigDecimal(orderDTO.getOrderAmount()*100).setScale(0, BigDecimal.ROUND_HALF_UP);
		//BigDecimal bd=new BigDecimal(orderDTO.getOrderAmount()).setScale(0, BigDecimal.ROUND_HALF_UP);
		Integer totalFee =	Integer.parseInt(bd.toString());
		request.setTotalFee(totalFee);
		request.setSpbillCreateIp(orderDTO.getClientIP());
		request.setNotifyURL("https://antcar.net.cn/sell/weixin/notify");
		request.setTradeType("JSAPI");
		request.setLimitPay("no_credit");
		request.setOpenid(orderDTO.getBuyerOpenid());
		WxPayUnifiedOrderResult wxPayUnifiedOrderResult = new WxPayUnifiedOrderResult();
		try {
			wxPayUnifiedOrderResult = wxPayService.unifiedOrder(request);
		} catch (WxPayException e) {
			log.info(e.getMessage());
		}
		return wxPayUnifiedOrderResult;
	}

	@Override
	public WxPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws WxPayException {
		// TODO Auto-generated method stub
		return this.parseOrderNotifyResult(xmlData);
	}

	@Override
	public RefundResponse refund(OrderDTO orderDTO) {
		RefundRequest refundRequest = new RefundRequest();
		refundRequest.setOrderAmount(orderDTO.getOrderAmount());
		refundRequest.setOrderId(orderDTO.getOrderId());
		refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
		log.info("【微信退款】request={}", JsonUtil.toJson(refundRequest));
		RefundResponse response = bestPayServiceImpl.refund(refundRequest);
		log.info("【微信退款】response={}", JsonUtil.toJson(response));
		return response;
	}

}
