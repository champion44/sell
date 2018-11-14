package com.imooc.controller;

import java.math.BigInteger;

import javax.persistence.Transient;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.imooc.VO.ResultVO;
import com.imooc.config.GlassConfig;
import com.imooc.config.WechatPayConfig;
import com.imooc.dataobject.WxOrder;
import com.imooc.dataobject.WxProductInfo;
import com.imooc.service.impl.GlassWechatPayServiceImpl;
import com.imooc.service.impl.WxOrderServiceImpl;
import com.imooc.service.impl.WxProductInfoServiceImpl;
import com.imooc.utils.KeyUtil;
import com.imooc.utils.ResultVOUtil;
import com.imooc.utils.SafeForNumberInputUtil;

import dto.WxOrderDTO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/glassOrder")
@Slf4j
public class GlassOrderContrllor {

	@Autowired
	private WxProductInfoServiceImpl wxProductInfoServiceImpl;

	@Autowired
	private WxOrderServiceImpl wxOrderServiceImpl;

	@Autowired
	private GlassWechatPayServiceImpl glassWechatPayServiceImpl;

	@Autowired
	private WechatPayConfig wxPayConfig;

	@Autowired
	private GlassConfig glassConfig;

	/*
	 * 创建订单 未支付
	 */
	@GetMapping("/create")
	@Transient
	public ResultVO createOrder(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "add", required = true) String add,
			@RequestParam(value = "tip", required = false) String tip,
			@RequestParam(value = "num", required = true) String num,
			@RequestParam(value = "radio", required = true) String radio,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "openid", required = true) String openid) {
		if (name == "" || name == null || num == "" || num == null || add == "" || add == null || id == ""
				|| id == null||openid == "" || openid == null ) {
			return new ResultVOUtil().error(1, "请检查空白输入");
		}
		WxProductInfo p = new WxProductInfo();
		try {
			// 先根据商品id查询商品信息以备入库订单表
			p = wxProductInfoServiceImpl.selectById(new BigInteger(id));
			if (p == null) {
				return new ResultVOUtil().error(1, "查无商品");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			return new ResultVOUtil().error(1, "查无商品");
		}
		// 开始订单入库
		WxOrder wxorder = new WxOrder();
		wxorder.setId(num+"-"+SafeForNumberInputUtil.limitLength(p.getPhonenum())+"-"+SafeForNumberInputUtil.limitLength(KeyUtil.getTimestamp()));
		wxorder.setCustname(name);
		wxorder.setCustaddr(add);
		wxorder.setCustphone(num);
		wxorder.setProductid(Integer.parseInt(id));
		wxorder.setOrderfee(Double.parseDouble(p.getDeposit()));
		wxorder.setStorephone(p.getPhonenum());
		wxorder.setBuyfee(Double.parseDouble(p.getSaleprice()));
		Integer mailNum = Integer.parseInt(radio.substring(0, 1));
		if (mailNum == 2) {
			// 着急送
			wxorder.setGettype(2);
			wxorder.setMailfee(glassConfig.getMailFee());
			wxorder.setTotalfee(Double.parseDouble(p.getDeposit()) + glassConfig.getMailFee());
		} else if (mailNum == 1) {
			// 自取
			wxorder.setGettype(1);
			wxorder.setMailfee(0.0);
			wxorder.setTotalfee(Double.parseDouble(p.getDeposit()));
		} else {
			return new ResultVOUtil().error(1, "配送问题");
		}
		wxorder.setPayflag(0);
		Integer insertResult = wxOrderServiceImpl.insert(wxorder);
		WxOrderDTO wxOrderDTO = new WxOrderDTO();
		BeanUtils.copyProperties(wxorder, wxOrderDTO);
		wxOrderDTO.setOpenid(openid);
		// log.info(insertResult.toString());
		if (insertResult.intValue() == 1) {
			// 增加成功
			WxPayUnifiedOrderResult result = glassWechatPayServiceImpl.unifiedOrder(wxOrderDTO);
			return new ResultVOUtil().success(result);
		} else {
			return new ResultVOUtil().error(1, "下单失败");
		}
		//return new ResultVOUtil().success(wxorder);
	}
}
