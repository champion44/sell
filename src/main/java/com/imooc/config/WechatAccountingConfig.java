package com.imooc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountingConfig {
	private String appId;

	private String appSecret;

	private String mpAppid;

	private String mpAppSecret;

	private String mpChampionId;

	private String mpChampionSecret;
	/**
	 * 微信支付商户号
	 */
	private String mchId;

	/**
	 * 微信支付商户密钥
	 */
	private String mchKey;

	/**
	 * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
	 */
	private String keyPath;

}
