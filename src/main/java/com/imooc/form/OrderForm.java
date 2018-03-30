/**
 * 
 */
package com.imooc.form;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * @author champion
 *
 * @version Jan 13, 201812:26:48 PM
 */
@Data
public class OrderForm {
	// 买家姓名
	@NotEmpty(message = "姓名必填")
	private String name;
	// 买家电话
	@NotEmpty(message = "手机号必填")
	private String phone;
	// 买家地址
	@NotEmpty(message = "地址必填")
	private String address;
	// 很重要 订单查询我们就靠他了
	@NotEmpty(message = "openid必填")
	private String openid;
	// 购物车信息 其实也是字符串
	@NotEmpty(message = "购物车不能为空")
	private String items;
}
