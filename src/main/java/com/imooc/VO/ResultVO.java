/**
 * 
 */
package com.imooc.VO;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @author champion
 *
 * @version 2017年12月20日下午3:53:25
 * @param <T>
 * @param <T>
 */
@Data
public class ResultVO<T> {

	private Integer code;
	
	private String msg;
	
	private T data;

}
