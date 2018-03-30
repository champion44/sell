package com.imooc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

@Component
public class WechatMPConfig {

	
	@Autowired
	private WechatAccountingConfig wechatAccountingConfig;
	
	@Bean
	public BestPayServiceImpl bestPayService() {
		BestPayServiceImpl bestPayServiceImpl = new BestPayServiceImpl();
		bestPayServiceImpl.setWxPayH5Config(wxPayH5Config());
		return bestPayServiceImpl;
	}
	
	@Bean
	public WxPayH5Config wxPayH5Config() {
		WxPayH5Config wxPayH5Config = new WxPayH5Config();
		wxPayH5Config.setAppId(wechatAccountingConfig.getAppId());
		wxPayH5Config.setAppSecret(wechatAccountingConfig.getAppSecret());
		wxPayH5Config.setKeyPath(wechatAccountingConfig.getKeyPath());
		wxPayH5Config.setMchId(wechatAccountingConfig.getMchId());
		wxPayH5Config.setMchKey(wechatAccountingConfig.getMchKey());
		return wxPayH5Config;
	}
}
