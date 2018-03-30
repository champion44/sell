/**
 * 
 */
package com.imooc.Enum;

import lombok.Getter;

/**
 * @author champion
 *
 * @version Dec 22, 20174:34:42 PM
 */
@Getter
public enum PayStatusEnum implements CodeEnum{
	WAIT(0,"等待支付"),
	SUCCESS(1,"支付成功"),
	REFUNDED(2,"退款成功")
	;
	private Integer code;
	
	private String msg;

	/**
	 * @param code
	 * @param msg
	 */
	 PayStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	
}
