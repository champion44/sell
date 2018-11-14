package com.imooc.controller;

import com.google.gson.Gson;
import com.imooc.Enum.ResultEnum;
import com.imooc.config.WechatAccountingConfig;
import com.imooc.constant.CookieConstant;
import com.imooc.constant.RedisConstant;
import com.imooc.dataobject.WxMiniUser;
import com.imooc.service.impl.WxMiniUserServiceImpl;
import com.imooc.utils.AESDecodeUtils;
import com.imooc.utils.CookieUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 卖家用户 Created by 廖师兄 2017-07-30 15:28
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class SellerUserController {

	// @Autowired
	// private SellerService sellerService;

	@Autowired
	private WxMiniUserServiceImpl wxMiniUserServiceImpl;
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private WechatAccountingConfig config;
	// @Autowired
	// private ProjectUrlConfig projectUrlConfig;

	@GetMapping("/getOpenid")
	public String getOpenid(@RequestParam("code") String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+config.getAppId()+"&secret="+config.getAppSecret()+"&js_code="+code+"&grant_type=authorization_code";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);
		Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(result, map.getClass());
        return map.get("openid").toString();
	}
	
	@GetMapping("/writeOpenid")
	public Integer writeOpenid(@RequestParam("openid") String openid,@RequestParam("userPhone") String userPhone,
			@RequestParam("hostPhone") String hostPhone,@RequestParam("storeId") String storeId) {
		WxMiniUser wxMiniUser = new WxMiniUser();
		wxMiniUser.setHostPhone(hostPhone);
		wxMiniUser.setStoreId(storeId);
		wxMiniUser.setUserPhone(userPhone);
		wxMiniUser.setUserOpenid(openid);
		Integer insertResult = wxMiniUserServiceImpl.insert(wxMiniUser);
		return insertResult;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getPhone")
	public String getPhone(@RequestParam("code") String code, @RequestParam("encryptedData") String encryptedData,
			@RequestParam("iv") String iv) throws Exception {
		log.info(code);
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+config.getAppId()+"&secret="+config.getAppSecret()+"&js_code="+code+"&grant_type=authorization_code";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);
		log.info(result);
		Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(result, map.getClass());
		log.info(map.get("session_key").toString());
		String key = (String) map.get("session_key");
		byte[] encrypData = Base64.decodeBase64(encryptedData);
		byte[] ivData = Base64.decodeBase64(iv);
		byte[] sessionKey = Base64.decodeBase64(key);
		log.info(AESDecodeUtils.decrypt(sessionKey, ivData, encrypData));
		return AESDecodeUtils.decrypt(sessionKey, ivData, encrypData);
		//return result;
	}

	@GetMapping("/login")
	public ModelAndView login(HttpServletResponse response, Map<String, Object> map) {
		/*
		 * //1. openid去和数据库里的数据匹配 SellerInfo sellerInfo =
		 * sellerService.findSellerInfoByOpenid(openid); if (sellerInfo == null) {
		 * map.put("msg", ResultEnum.LOGIN_FAIL.getMessage()); map.put("url",
		 * "/sell/seller/order/list"); return new ModelAndView("common/error"); }
		 * 
		 * //2. 设置token至redis String token = UUID.randomUUID().toString(); Integer
		 * expire = RedisConstant.EXPIRE;
		 * 
		 * redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,
		 * token), openid, expire, TimeUnit.SECONDS);
		 * 
		 * //3. 设置token至cookie CookieUtil.set(response, CookieConstant.TOKEN, token,
		 * expire);
		 * 
		 * return new ModelAndView("redirect:" + projectUrlConfig.getSell() +
		 * "/sell/seller/order/list");
		 */
		return new ModelAndView("auth/login");
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
		// 1. 从cookie里查询
		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if (cookie != null) {
			// 2. 清除redis
			redisTemplate.opsForValue().getOperations()
					.delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

			// 3. 清除cookie
			CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
		}

		map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
		map.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success", map);
	}
}
