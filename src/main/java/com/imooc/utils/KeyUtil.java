/**
 * 
 */
package com.imooc.utils;

import java.util.Random;

/**
 * @author champion
 *
 * @version Jan 11, 201812:01:05 AM
 */
public class KeyUtil {

		public static synchronized String getUniqueKey(){
			Random random = new Random();
			Integer number = random.nextInt(900000)+100000;
			return  System.currentTimeMillis()+String.valueOf(number); 

		}
}
