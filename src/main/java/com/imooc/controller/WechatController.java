package com.imooc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.imooc.Enum.ResultEnum;
import com.imooc.VO.ResultVO;
import com.imooc.config.WechatAccountingConfig;
import com.imooc.dataobject.WxOrder;
import com.imooc.exception.AesException;
import com.imooc.exception.SellException;
import com.imooc.service.impl.WxOrderServiceImpl;
import com.imooc.utils.JsonUtil;
import com.imooc.utils.MathUtil;
import com.imooc.utils.WXPublicUtils;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private WechatAccountingConfig wechatAccountingConfig;

	@Autowired
	private BestPayServiceImpl bestPayServiceImpl;

	@Autowired
	private WxOrderServiceImpl wxOrderImpl;

	// 微信异步通知
	@PostMapping("/notify")
	public ModelAndView getNotify(@RequestBody String notifyData) throws WxPayException {
		// 1.验证签名
		// 2.支付的状态
		// 3.支付金额
		PayResponse response = bestPayServiceImpl.asyncNotify(notifyData);
		WxOrder orderDTO = wxOrderImpl.selectByPrimaryKey(response.getOrderId());
		if (orderDTO == null) {
			log.error("[微信支付]异步通知，订单不存在");
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		if (!MathUtil.equals(response.getOrderAmount(), orderDTO.getTotalfee())) {
			log.error("【微信支付】异步通知，订单金额不一致,orderId={},微信通知金额={},系统金额={}", response.getOrderId(),
					response.getOrderAmount(), orderDTO.getTotalfee());
			throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
		}
		log.info(JsonUtil.toJson(response));
		// 设置为已支付
		wxOrderImpl.paid(orderDTO);
		return new ModelAndView("pay/success");
	}

	@GetMapping("/authorize")
	public String authorize(@RequestParam("returnUrl") String returnUrl) {
		// 配置
		// 调用方法
		returnUrl = "https://www.baidu.com";
		String url = "http://champion.natapp1.cc/sell/wechat/userInfo";
		String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO,
				java.net.URLEncoder.encode(returnUrl));
		log.info("【微信网页授权】获取code，resutl={}", redirectUrl);

		return "redirect:" + redirectUrl;

	}

	@ResponseBody
	@GetMapping("/accessToken")
	public String accessToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ wechatAccountingConfig.getMpChampionId() + "&secret=" + wechatAccountingConfig.getMpChampionSecret();
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);
		log.info(result);
		return result;
	}

	@GetMapping("/userInfo")
	public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl) {
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
		try {
			wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.info("【微信网页授权】{}", e);
		}
		String openid = wxMpOAuth2AccessToken.getOpenId();
		log.info("openid={}", openid);
		return "redirect:" + returnUrl + "?openid=" + openid;
	}

	@RequestMapping("/wxpublic/verify_wx_token")
	@ResponseBody
	public String verifyWXToken(HttpServletRequest request) throws AesException {
		String msgSignature = request.getParameter("signature");
		String msgTimestamp = request.getParameter("timestamp");
		String msgNonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		if (WXPublicUtils.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
			return echostr;
		}
		return null;
	}
}
