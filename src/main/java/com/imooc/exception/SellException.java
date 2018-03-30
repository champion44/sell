/**
 * 
 */
package com.imooc.exception;

import com.imooc.Enum.ResultEnum;

/**
 * @author champion
 *
 * @version Jan 10, 201811:21:20 PM
 */
public class SellException extends RuntimeException{

	private Integer code;
	
	public SellException(ResultEnum resultEnum){
		super(resultEnum.getMessage());
		
		this.code = resultEnum.getCode();
	}
	
	public SellException(Integer code,String message){
		super(message);
		this.code=code;
	}
}
