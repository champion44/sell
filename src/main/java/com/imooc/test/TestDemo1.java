package com.imooc.test;

import com.imooc.utils.Sm4Utils;

public class TestDemo1 {

	public static void main(String[] args) {

		//待加密字符串
		String a = "18090525900"; 
		//加密后字符串
		String ssss = "3e9dc9232e4e733eab2dc3eab166dd92";
		
		//key
		String key = "15328501100";
		
		//加密
	    Sm4Utils sm4 = new Sm4Utils();	
	    
	    String  result1=sm4.StringToSm4(a,key);
	    
	    System.out.println("待加密字符串："+a);
	    System.out.println("加密结果："+result1);
	    
	    
	   //解密
        String  result2 = sm4.Sm4ToString(ssss,key); 

        System.out.println("待解密字符串："+ssss);
        System.out.println("解密结果："+result2);
   
	    
	}


	
    
	

}

