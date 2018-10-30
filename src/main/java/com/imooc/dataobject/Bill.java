package com.imooc.dataobject;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Entity
@Data
public class Bill{
	@Id
	private String tradeTime;
	private String billTime;
	private String appId;
	private String mchId;
	private String subMchId;
	private String deviceInfo;
	private String transactionId;
	private String outTradeNo;
	private String openId;
	private String tradeType;
	private String tradeState;
	private String bankType;
	private String feeType;
	private String totalFee;
	private String couponFee;
	private String refundId;
	private String outRefundNo;
	private String settlementRefundFee;
	private String couponRefundFee;
	private String refundChannel;
	private String refundState;
	private String body;
	private String attach;
	private String poundage;
	private String poundageRate;
}
