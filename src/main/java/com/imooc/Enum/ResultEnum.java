/**
 * 
 */
package com.imooc.Enum;

import lombok.Getter;

/**
 * @author champion
 *
 * @version Jan 10, 201811:13:57 PM
 */
@Getter
public enum ResultEnum {

	PARAM_ERROR(1, "参数不正确"),

	PRODUCT_NOT_EXIST(10, "商品不存在"),

	PRODUCT_STOCK_ERROR(11, "商品库存不正确"),

	ORDER_NOT_EXIST(12, "订单不存在"),

	ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),

	ORDER_STATUS_ERROR(14, "订单状态不正确"),

	ORDER_UPDATE_FAIL(15, "订单更新失败"),

	ORDER_DETAIL_EMPTY(16, "订单详情为空"),

	ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),

	CART_EMPTY(18, "购物车为空"),

	ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),

	WECHAT_MP_ERROR(20, "微信公众账号方面错误"),

	WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信支付异步通知金额校验不通过"),

	REFUND_MONEY_ERROR(22, "退款金额不正确"),

	USER_NOT_EXIST(23, "用户不存在"),

	INFO_NOT_MATCH(24, "输入不匹配"),

	OPERATEOR_ERR(25, "无操作权限"),

	REFUND_ID_ERROR(26, "此商户不可退款"),

	LOGIN_FAIL(27, "登录失败, 登录信息不正确"),

	LOGOUT_SUCCESS(28, "登出成功"),;

	private Integer code;

	private String message;

	/**
	 * @param code
	 * @param message
	 */
	ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}
