package com.imooc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

@Component
public class WechatPayConfig {

	@Autowired
	private WechatAccountingConfig accountingConfig;

	@Bean
	@ConditionalOnMissingBean
	public WxPayConfig config() {

		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(accountingConfig.getAppId());
		payConfig.setMchId(accountingConfig.getMchId());
		payConfig.setMchKey(accountingConfig.getMchKey());
		payConfig.setKeyPath(accountingConfig.getKeyPath());
		return payConfig;
	}

	@Bean
	public WxPayService wxPayService(WxPayConfig payConfig) {
		WxPayService wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(payConfig);
		return wxPayService;
	}
}
