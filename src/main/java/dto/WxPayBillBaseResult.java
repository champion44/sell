package dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WxPayBillBaseResult {
	private String tradeTime;
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
