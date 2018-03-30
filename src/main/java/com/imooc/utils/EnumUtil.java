package com.imooc.utils;

import com.imooc.Enum.CodeEnum;

public class EnumUtil {

	public static <T extends CodeEnum<T>> T getByCode(Integer code ,Class<T> enumClass) {
		
		for(T each:enumClass.getEnumConstants()) {
			if(code.equals(each.getCode())) {
				return each;
			}
		}
		return null;
		
	}
}
