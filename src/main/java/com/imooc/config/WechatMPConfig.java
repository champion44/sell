package com.imooc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Component
public class WechatMPConfig {

	
	@Autowired
	private WechatAccountingConfig wechatAccountingConfig;
	
	@Bean
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}
	
	@Bean
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId(wechatAccountingConfig.getMpAppid());
		wxMpConfigStorage.setSecret(wechatAccountingConfig.getMpAppSecret());
		return wxMpConfigStorage;
	}
	
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
