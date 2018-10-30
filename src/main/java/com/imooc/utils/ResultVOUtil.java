/**
 * 
 */
package com.imooc.utils;

import com.imooc.VO.ResultVO;

/**
 * @author champion
 *
 * @version Jan 13, 20183:29:39 PM
 */
public class ResultVOUtil {

	public static ResultVO success(Object object) {
		ResultVO resultVO = new ResultVO<>();
		resultVO.setData(object);
		resultVO.setCode(0);
		resultVO.setMsg("成功");
		return resultVO;
	}
	
	public static ResultVO success(Object object,String dateMsg) {
		ResultVO resultVO = new ResultVO<>();
		resultVO.setData(object);
		resultVO.setCode(0);
		resultVO.setMsg(dateMsg);
		return resultVO;
	}
	
	public static ResultVO success(){
		return success(null);
	}
	
	public static ResultVO error(Integer code,String msg){
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(code);
		resultVO.setMsg(msg);
		return resultVO;
	}
}
