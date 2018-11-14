/**
 * 
 */
package com.imooc.utils;
/**
* @author champion
* @version 创建时间：Nov 7, 2018 4:24:53 PM
* 类说明
*/
/**
 * 避免用户输入号码太长的错误 
 * 截取11位入库订单库
 * @author champion
 *
 */

public class SafeForNumberInputUtil {

	public static String limitLength(String s) {
		
		if(s.length()>11) {
			s = s.substring(0, 11);
		}
		return s;
	}
}
