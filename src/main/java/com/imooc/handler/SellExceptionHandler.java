package com.imooc.handler;

import com.imooc.exception.SellerAuthorizeException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 廖师兄 2017-07-30 17:44
 */
@ControllerAdvice
@Slf4j
public class SellExceptionHandler {

	// 拦截登录异常
	@ExceptionHandler(value = SellerAuthorizeException.class)
	public ModelAndView handlerAuthorizeException() {
		log.info("拦截到登录异常");
		ModelMap map = new ModelMap();
		map.put("msg", "登录过期");
		map.put("url", "/sell/seller/login");
//		return new ModelAndView("redirect:localhost:8080/sell/seller/login");
		return new ModelAndView("common/error",map);
	}
}
