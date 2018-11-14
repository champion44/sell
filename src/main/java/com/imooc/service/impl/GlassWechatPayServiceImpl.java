/**
 * 
 */
package com.imooc.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.gson.Gson;
import com.imooc.dataobject.WxOrder;
import com.imooc.service.GlassWechatPayService;
import com.imooc.utils.JsonUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import dto.OrderDTO;
import dto.WxOrderDTO;
import lombok.extern.slf4j.Slf4j;

/**
* @author champion
* @version 创建时间：Nov 6, 2018 10:54:35 AM
* 类说明
*/
/**
 * @author champion
 *
 */
@Service
@Slf4j
public class GlassWechatPayServiceImpl implements GlassWechatPayService {

	@Autowired
	private WxPayService wxPayService;

	@Autowired
	private BestPayServiceImpl bestPayServiceImpl;

	@Override
	public WxPayUnifiedOrderResult unifiedOrder(OrderDTO orderDTO) {
		WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
		request.setBody("glass收款");
		request.setOutTradeNo(orderDTO.getOrderId());
		// Integer totalFee = orderDTO.getOrderAmount()*100;
		BigDecimal bd = new BigDecimal(orderDTO.getOrderAmount() * 100).setScale(0, BigDecimal.ROUND_HALF_UP);
		// BigDecimal bd=new BigDecimal(orderDTO.getOrderAmount()).setScale(0,
		// BigDecimal.ROUND_HALF_UP);
		Integer totalFee = Integer.parseInt(bd.toString());
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

	@Override
	public WxPayBillResult downloadBill(String billDate, String billType, String tarType, String deviceInfo)
			throws WxPayException {

		return wxPayService.downloadBill(billDate, billType, tarType, deviceInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.service.GlassWechatPayService#unifiedOrder(com.imooc.dataobject.
	 * WxOrder)
	 */
	@Override
	public WxPayUnifiedOrderResult unifiedOrder(WxOrderDTO wxOrder) {
		String getOpenidurl = "http://ip-api.com/json";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(getOpenidurl, String.class);
		Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(result, map.getClass());
		WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
		
		request.setBody("glass收款");
		request.setOutTradeNo(wxOrder.getId());
		// Integer totalFee = orderDTO.getOrderAmount()*100;
		BigDecimal bd = new BigDecimal(wxOrder.getTotalfee() * 100).setScale(0, BigDecimal.ROUND_HALF_UP);
		// BigDecimal bd=new BigDecimal(orderDTO.getOrderAmount()).setScale(0,
		// BigDecimal.ROUND_HALF_UP);
		Integer totalFee = Integer.parseInt(bd.toString());
		request.setTotalFee(totalFee);
		request.setSpbillCreateIp(map.get("query").toString());
		request.setNotifyURL("http://2ibay4.natappfree.cc/sell/wechat/notify");
		request.setTradeType("JSAPI");
		request.setLimitPay("no_credit");
		request.setOpenid(wxOrder.getOpenid());
		WxPayUnifiedOrderResult wxPayUnifiedOrderResult = new WxPayUnifiedOrderResult();
		try {
			wxPayUnifiedOrderResult = wxPayService.unifiedOrder(request);
		} catch (WxPayException e) {
			log.info(e.getMessage());
		}
		return wxPayUnifiedOrderResult;
	}

}
