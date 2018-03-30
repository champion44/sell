package com.imooc.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {

	/** 
	    * 获取过去第几天的日期 
	    * 
	    * @param past 
	    * @return 
	    */  
	   public static String getPastDate(int past) {  
	       Calendar calendar = Calendar.getInstance();  
	       calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
	       Date today = calendar.getTime();  
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	       String result = format.format(today);  
	       int dow=calendar.get(Calendar.DAY_OF_WEEK)-1;  
	        String[] data={"日","一","二","三","四","五","六"};
	       return result+"周"+data[dow];  
	   }  
	   
	   /** 
	    * 获取未来 第 past 天的日期 
	    * @param past 
	    * @return 
	    */  
	   public static String getFetureDate(int past) {  
	       Calendar calendar = Calendar.getInstance();  
	       calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);  
	       Date today = calendar.getTime();  
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	       String result = format.format(today);  
	       int dow=calendar.get(Calendar.DAY_OF_WEEK)-1;  
	        String[] data={"日","一","二","三","四","五","六"};
	       return result+"周"+data[dow];  
	   }  
	   
	   public static Long validate(Long expire) {
		   long day = new Date().getTime();
		   if(expire==0) {
			   return 0L;
		   }else if(expire-day<=0) {
			   return 1L;
		   }
		   return expire;
	   }
}
