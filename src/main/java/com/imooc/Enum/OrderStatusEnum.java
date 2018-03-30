/**
 * 
 */
package com.imooc.Enum;

import lombok.Getter;

/**
 * @author champion
 *
 * @version Dec 22, 20174:29:37 PM
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{
	NEW(0,"新下单"),
	FINISH(1,"完结"),
	CANCEl(2,"已取消"),
	;
	private Integer code;
	
	private String msg;

	/**
	 * @param code
	 * @param msg
	 */
	OrderStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}
