/**
 * 
 */
package com.imooc.Enum;

import lombok.Getter;

/**
 * 商品状态
 * 
 * @author champion
 *
 * @version 2017年12月20日下午3:21:49
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{
	UP(0,"在架"),
	DOWN(1,"下架"),
	STORE00(110,"新店铺"),
	STORE0(100,"预付金"),
	STORE1(101,"五谷鱼粉"),
	STORE2(102,"鸡公煲"),
	STORE3(103,"嘴乐冒菜"),
	STORE4(104,"东苑水果店"),
	STORE5(105,"理发店"),
	;
	private Integer code;

	private String message;
	/**
	 * 
	 */
	private ProductStatusEnum(Integer code,String message) {
		this.code = code;
		this.message = message;
	}

}
