/**
 * 
 */
package com.imooc.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.apache.http.params.HttpParams;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.github.binarywang.wxpay.bean.result.WxPayBillBaseResult;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.imooc.Enum.ResultEnum;
import com.imooc.VO.ResultVO;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.Stores;
import com.imooc.dataobject.UserInfo;
import com.imooc.dataobject.Bill;
import com.imooc.dataobject.BillResult;
import com.imooc.exception.SellException;
import com.imooc.service.impl.BillServiceImpl;
import com.imooc.service.impl.OrderServiceImpl;
import com.imooc.service.impl.StoresServiceImpl;
import com.imooc.service.impl.UserInfoServiceImpl;
import com.imooc.service.impl.WechatPayServiceImpl;
import com.imooc.service.impl.WxPayBillBaseResultListServiceImpl;
import com.imooc.utils.DateUtil;
import com.imooc.utils.JsonUtil;
import com.imooc.utils.KeyUtil;
import com.imooc.utils.MathUtil;
import com.imooc.utils.ResultVOUtil;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author champion
 *
 * @version Jan 15, 20185:01:27 PM
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

	@Autowired
	private WechatPayServiceImpl wechatPayServiceImpl;

	@Autowired
	private OrderServiceImpl orderServiceImpl;

	@Autowired
	private BestPayServiceImpl bestPayServiceImpl;

	@Autowired
	private StoresServiceImpl storeService;

	@Autowired
	private UserInfoServiceImpl userInfoService;

	@Autowired
	private WxPayBillBaseResultListServiceImpl billService;

	@Autowired
	private BillServiceImpl billServiceImpl;

	public static String APPID = "wx8982d3b2acdab91e";

	public static String SECRET = "2bff41acaf1004f852d653d47821310e";


	@RequestMapping("/Oauth")
	public void Oauth(@RequestParam("code") String code) {
		log.info("code={}", code);
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx8982d3b2acdab91e&secret=2bff41acaf1004f852d653d47821310e&code="
				+ code + "&grant_type=authorization_code";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);
		log.info(JsonUtil.toJson(result));
	}

	@RequestMapping("/getDate")
	public ArrayList<String> getDate() {
		ArrayList<String> fetureDaysList = new ArrayList<>();
		for (int i = 1; i < 2; i++) {
			fetureDaysList.add(DateUtil.getFetureDate(i));
		}
		return fetureDaysList;
	}

	// 删除预约
	@RequestMapping("/deleteTime")
	public ResultVO deleteTime(@RequestParam("openid") String openid) {
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoService.findByUserOpenid(openid);
		if (userInfo == null) {
			return new ResultVOUtil().error(1, "用户不存在");
		}
		try {
			userInfo.setBookDate("");
			userInfo.setBooked(0);
			userInfoService.save(userInfo);
		} catch (Exception e) {
			return new ResultVOUtil().error(1, e.getMessage());
		}
		return new ResultVOUtil().success();
	}

	// getIP
	@RequestMapping("/getIP")
	public String getIP() {
		String getOpenidurl = "http://ip-api.com/json";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(getOpenidurl, String.class);
		return result;
	}

	// 管理员修改用户信息
	@RequestMapping("/modifyUser")
	public ResultVO modifyUser(@RequestParam("userOpenid") String userOpenid, @RequestParam("userName") String userName,
			@RequestParam("userPhone") String userPhone, @RequestParam("opraterOpenid") String opraterOpenid,
			@RequestParam("vip") Integer vip, @RequestParam("paid") Integer paid,
			@RequestParam("userNum") String userNum, @RequestParam("userCategory") Integer userCategory) {
		UserInfo info = new UserInfo();
		info = userInfoService.findByUserOpenid(userOpenid);
		if (info == null) {
			return new ResultVOUtil().error(1, "用户不存在");
		}
		UserInfo check = userInfoService.findByUserOpenid(opraterOpenid);
		try {
			Integer userCategoryInMysql = check.getUserCategory();
			if (userCategoryInMysql != 1) {
				throw new SellException(ResultEnum.OPERATEOR_ERR);
			}
		} catch (Exception e) {
			return new ResultVOUtil().error(1, e.getMessage());
		}
		info.setUserCategory(userCategory);
		info.setUserNum(userNum);
		info.setUserName(userName);
		info.setUserPhone(userPhone);
		info.setPaid(paid);
		info.setVip(vip);
		userInfoService.save(info);
		return new ResultVOUtil().success();
	}

	// 教练预约
	@RequestMapping("/bookByTrench")
	public ResultVO bookByTrench(@RequestParam("userName") String userName, @RequestParam("userPhone") String userPhone,
			@RequestParam("bookedTime") String bookedTime) {
		UserInfo info = userInfoService.findByUserPhone(userPhone);
		if (info == null) {
			return new ResultVOUtil().error(1, "用户不存在");
		}
		if (!info.getUserName().equals(userName)) {
			return new ResultVOUtil().error(1, "输入不匹配");
		}
		info.setBookDate(bookedTime);
		info.setBooked(1);
		userInfoService.save(info);
		return new ResultVOUtil().success();
	}

	// 用户预约
	@RequestMapping("/book")
	public ResultVO book(@RequestParam("openid") String openid, @RequestParam("date") String date) {
		if (openid == "" || openid == null) {
			// throw new SellException(ResultEnum.USER_NOT_EXIST);
			return new ResultVOUtil().error(1, "用户不存在");
		}
		UserInfo userInfo = userInfoService.findByUserOpenid(openid);
		if (userInfo == null) {
			return new ResultVOUtil().error(1, "用户不存在");

			// throw new SellException(ResultEnum.USER_NOT_EXIST);
		}
		// 不再验证 因为加入了过期自动初始化
		/*
		 * if (userInfo.getBooked() == 1) { String msg = "您已预约过" +
		 * userInfo.getBookDate(); return new ResultVOUtil().error(1, msg); }
		 */
		userInfo.setBookDate(date);
		userInfo.setBooked(1);
		userInfoService.save(userInfo);
		return new ResultVOUtil().success();
	}

	// 返回预约用户
	@RequestMapping("/getSingleBookedTime")
	public ResultVO getSingleBookedTime(@RequestParam("openid") String openid) {
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoService.findByUserOpenid(openid);
		if (userInfo == null) {
			return new ResultVOUtil().error(1, "用户不存在");
		}
		return new ResultVOUtil().success(userInfo);
	}

	// 返回时间段预约的用户数量
	@RequestMapping("/getBooked")
	public ArrayList<Integer> getBooked() {
		ArrayList<String> fetureDaysList = new ArrayList<>();
		ArrayList<Integer> counts = new ArrayList<>();
		String up = DateUtil.getFetureDate(1) + "上午";
		fetureDaysList.add(up);
		String down = DateUtil.getFetureDate(1) + "下午";
		fetureDaysList.add(down);
		for (int i = 0; i < fetureDaysList.size(); i++) {
			int count = userInfoService.findByBookDate(fetureDaysList.get(i)).size();
			counts.add(count);
		}
		return counts;
	}

	// 返回所有预约的用户 给教练查看信息 前端不可更改名字电话 如有误 管理员通过用户信息查看修改
	@RequestMapping("/getBookedInfo")
	public List<UserInfo> getBookedInfo() {
		List<UserInfo> infoList = null;
		String up = DateUtil.getFetureDate(1) + "上午";
		infoList = userInfoService.findByBookDate(up);
		String down = DateUtil.getFetureDate(1) + "下午";
		infoList.addAll(userInfoService.findByBookDate(down));
		return infoList;
	}

	// 返回报名成功的学员信息
	@RequestMapping("/allUserWhoPaid")
	public List<UserInfo> allUserWhoPaid() {
		List<UserInfo> userList = userInfoService.findByStage(3);
		return userList;
	}

	// 取消预约
	@RequestMapping("/cancelBook")
	public ResultVO cancelBook(@RequestParam("openid") String openid) {
		UserInfo userInfo = userInfoService.findByUserOpenid(openid);
		try {
			userInfo.setBooked(0);
			userInfo.setBookDate("");
			userInfoService.save(userInfo);
		} catch (Exception e) {
			return new ResultVOUtil().error(1, e.getMessage());
		}
		return new ResultVOUtil().success();
	}

	// 教练修改学车进程
	@RequestMapping("/modifyStage")
	public ResultVO modifyStage(@RequestParam("stage") Integer stage, @RequestParam("userPhone") String userPhone) {
		if (userPhone == "") {
			return new ResultVOUtil().error(1, "电话为空");
		}
		UserInfo info = new UserInfo();
		info = userInfoService.findByUserPhone(userPhone);
		if (info == null) {
			return new ResultVOUtil().error(1, "用户不存在");
		}
		info.setStage(stage);
		userInfoService.save(info);
		return ResultVOUtil.success();
	}

	// 授权获取openid
	@RequestMapping("/auth")
	public String auth(@RequestParam("code") String code) {
		// 小蚁学车
		String getOpenidurl = "https://api.weixin.qq.com/sns/jscode2session?appid=wx975b8fb14fbbef20&secret=58d919a4945f37f26c4a9cb9496ba35b&js_code="
				+ code + "&grant_type=authorization_code";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(getOpenidurl, String.class);
		return result;
	}

	@RequestMapping("/authForStudy")
	public String authForStudy(@RequestParam("code") String code) {
		// 通过code转换成openid wxfdc1cfad38b7885f
		// 爱学习
		String getOpenidurl = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=wxfdc1cfad38b7885f&"
				+ "secret=c2a8399db32fbab4952abeedaa7c797c&" + "js_code=" + code + "&grant_type=authorization_code";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(getOpenidurl, String.class);
		return result;
	}

	//获取access_token
	@RequestMapping("/codeToAccesstoken")
	public String codeToAccesstoken() {
		String codeToAccesstoken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET;
		//		String getOpenidurl = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=wxfdc1cfad38b7885f&"
		//				+ "secret=c2a8399db32fbab4952abeedaa7c797c&" + "js_code=" + code + "&grant_type=authorization_code";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(codeToAccesstoken, String.class);
		log.info(result.toString());
		return result;
	}
	//	不刷新 返回账单数据
	@RequestMapping("/downloadbillJson")
	public ResultVO downloadbillJson(@RequestParam("date") String date, Map<String, Object> map,HttpSession session) throws WxPayException {
		String billDate = date;
		String billType = "ALL";
		String tarType = "";
		String deviceInfo = "";
		WxPayBillResult wxPayBillResult = new WxPayBillResult();
		try {
			wxPayBillResult = wechatPayServiceImpl.downloadBill(billDate, billType, tarType, deviceInfo);
			BillResult wxPayBillBaseResultList = new BillResult();
			wxPayBillBaseResultList.setBillDate(billDate);
			wxPayBillBaseResultList.setTotalCouponFee(wxPayBillResult.getTotalCouponFee());
			wxPayBillBaseResultList.setTotalFee(wxPayBillResult.getTotalFee());
			wxPayBillBaseResultList.setTotalPoundageFee(wxPayBillResult.getTotalPoundageFee());
			wxPayBillBaseResultList.setTotalRecord(wxPayBillResult.getTotalRecord());
			wxPayBillBaseResultList.setTotalRefundFee(wxPayBillResult.getTotalRefundFee());
			wxPayBillBaseResultList.setWxPayBillBaseResultList(wxPayBillResult.getWxPayBillBaseResultLst().toString());
			billService.save(wxPayBillBaseResultList);
			for(WxPayBillBaseResult result : wxPayBillResult.getWxPayBillBaseResultLst()) {
				Bill bill = new Bill();
				BeanUtils.copyProperties(result, bill);
				bill.setBillTime(billDate);
				billServiceImpl.save(bill);
			}
			session.setAttribute("detailList",wxPayBillResult.getWxPayBillBaseResultLst());
			//	map.put("billList", wxPayBillBaseResultList);
			//		map.put("detailList",wxPayBillResult.getWxPayBillBaseResultLst());
			//			map.put("totalRecord", wxPayBillResult.getTotalRecord());
			//			map.put("totalFee", wxPayBillResult.getTotalFee());
			//			map.put("totalRefundFee", wxPayBillResult.getTotalRefundFee());
			//			map.put("msg", "微信账单统计");
		} catch (Exception e) {
			log.info(e.toString());
			log.info(e.getMessage());
		}
		if(wxPayBillResult.getTotalRecord()==null) {
			return new ResultVOUtil().error(1, date);
		}
		return new ResultVOUtil().success(wxPayBillResult,date);
		//map.put("tradeDate", date);
		//	return new ModelAndView("bill/bill", map);
	}
	// 下载对账单
	@RequestMapping("/downloadbill")
	public ModelAndView downloadbill(Map<String, Object> map,String date) throws WxPayException {
		//HttpParams	uri = request.getParams();
		String billDate = "";
		if(date==null) {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			billDate = f.format(c.getTime());
		}else {
			billDate  = date;
		}
		String billType = "ALL";
		String tarType = "";
		String deviceInfo = "";
		WxPayBillResult wxPayBillResult = new WxPayBillResult();
		try {
			wxPayBillResult = wechatPayServiceImpl.downloadBill(billDate, billType, tarType, deviceInfo);
			BillResult wxPayBillBaseResultList = new BillResult();
			wxPayBillBaseResultList.setBillDate(billDate);
			wxPayBillBaseResultList.setTotalCouponFee(wxPayBillResult.getTotalCouponFee());
			wxPayBillBaseResultList.setTotalFee(wxPayBillResult.getTotalFee());
			wxPayBillBaseResultList.setTotalPoundageFee(wxPayBillResult.getTotalPoundageFee());
			wxPayBillBaseResultList.setTotalRecord(wxPayBillResult.getTotalRecord());
			wxPayBillBaseResultList.setTotalRefundFee(wxPayBillResult.getTotalRefundFee());
			wxPayBillBaseResultList.setWxPayBillBaseResultList(wxPayBillResult.getWxPayBillBaseResultLst().toString());
			billService.save(wxPayBillBaseResultList);
			for(WxPayBillBaseResult result : wxPayBillResult.getWxPayBillBaseResultLst()) {
				Bill bill = new Bill();
				BeanUtils.copyProperties(result, bill);
				bill.setBillTime(billDate);
				billServiceImpl.save(bill);
			}		
			map.put("billList", wxPayBillBaseResultList);
			map.put("detailList",wxPayBillResult.getWxPayBillBaseResultLst());
			map.put("totalRecord", wxPayBillResult.getTotalRecord());
			map.put("totalFee", wxPayBillResult.getTotalFee());
			map.put("totalRefundFee", wxPayBillResult.getTotalRefundFee());
			map.put("msg", "微信账单统计");
		} catch (Exception e) {
			log.info(e.toString());
			log.info(e.getMessage());
		}
		map.put("tradeDate", billDate);
		return new ModelAndView("bill/bill", map);
	}

	// 支付接口
	@RequestMapping("/pay")
	public WxPayUnifiedOrderResult pay(@RequestParam("ip") String ip, @RequestParam("amount") Double amount,
			@RequestParam("openId") String openId, @RequestParam("storeId") Integer storeId,
			@RequestParam(value = "phone", defaultValue = "132") String phone) {
		if (openId.equalsIgnoreCase("undefined")) {
			throw new SellException(ResultEnum.USER_NOT_EXIST);
		}
		UserInfo userInfo = userInfoService.findByUserOpenid(openId);
		if (userInfo == null) {
			throw new SellException(ResultEnum.USER_NOT_EXIST);
		}
		// 大笔金额验证

		if (storeId == 199) {
			if (!MathUtil.equals(amount, 2680.0)) {
				WxPayUnifiedOrderResult errResult = new WxPayUnifiedOrderResult();
				errResult.setReturnMsg("支付金额不正确");
				errResult.setErrCode("1");
				log.info(errResult.toString());
				return errResult;
			}
		}
		if (storeId == 198) {
			if (!MathUtil.equals(amount, 99.0)) {
				WxPayUnifiedOrderResult errResult = new WxPayUnifiedOrderResult();
				errResult.setReturnMsg("支付金额不正确");
				errResult.setErrCode("1");
				log.info(errResult.toString());
				return errResult;
			}
		}

		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setBuyerOpenid(openId);
		orderDTO.setBuyerPhone(phone);
		// BigDecimal bd = new BigDecimal(amount).setScale(0, BigDecimal.ROUND_HALF_UP);
		// 查找商户折扣
		Stores storeInfo = storeService.findByStoreId(storeId);
		// 写入折扣
		orderDTO.setBuyerName(storeInfo.getDiscount().toString());
		// 设置商户名字
		orderDTO.setBuyerAddress(storeInfo.getStoreName());
		// 设置商户ID
		orderDTO.setStoreId(storeId);
		// 写入原价
		orderDTO.setOriPrice(amount);
		// 写入折扣价
		BigDecimal bd = new BigDecimal(amount).setScale(0, BigDecimal.ROUND_HALF_UP);
		Double discountAmount = Double.parseDouble(bd.toString());
		discountAmount = discountAmount * storeInfo.getDiscount() / 10.0;
		orderDTO.setOrderAmount(discountAmount);
		if (storeId < 150) {
			try {
				UserInfo result = userInfoService.findByUserOpenid(openId);
				if (result.getVip() == 0) {
					// 如果不是vip 则原价
					orderDTO.setOrderAmount(amount);
					orderDTO.setBuyerName("10");
				} else if (result.getVip() == 1) {
					// 再判断是否会员过期
					Long isValidate = DateUtil.validate(result.getExpire());
					result.setExpire(isValidate);
					// 0未激活 1已过期
					if (isValidate < 10) {
						// 存入原价 折扣存为10
						orderDTO.setOrderAmount(amount);
						orderDTO.setBuyerName("10");
						// 设置会员过期
						result.setVip(0);
					}
				}
				// 将result重新入库 无论是否修改
				userInfoService.save(result);
			} catch (Exception e) {
				WxPayUnifiedOrderResult errResult = new WxPayUnifiedOrderResult();
				errResult.setReturnMsg("用户不存在");
				errResult.setErrCode("1");
				return errResult;
			}
		}
		// 创建唯一系统orderid
		// 设置商户自定义订单号 由商户号加系统时间戳构成
		long currentTimeMillis = System.currentTimeMillis();
		String orderId = storeId.toString();
		String orderIds = currentTimeMillis + "";
		orderId = orderId + orderIds;
		orderDTO.setOrderId(orderId);
		// 客户端传入ip 支付必传字段
		orderDTO.setClientIP(ip);
		orderServiceImpl.createWX(orderDTO);
		WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wechatPayServiceImpl.unifiedOrder(orderDTO);
		log.info(wxPayUnifiedOrderResult.toString());
		return wxPayUnifiedOrderResult;
	}

	// 领取卡片 返回用户 其中有卡号和是否激活
	@GetMapping("/card")
	public ResultVO card(@RequestParam("openid") String openid) {
		UserInfo userInfo = new UserInfo();
		String cardNum = KeyUtil.getUniqueKey();
		try {
			userInfo = userInfoService.findByUserOpenid(openid);
			if (userInfo != null) {
				// 首次开发用户初始化
				if (userInfo.getCardNum().equalsIgnoreCase("nul")) {
					userInfo.setCardNum(cardNum);
					userInfo.setFirstGet(0);
					userInfo.setExpire(0);
					userInfoService.save(userInfo);
				} else {
					// 每次刷新查看会员日期
					Long result = DateUtil.validate(userInfo.getExpire());
					userInfo.setExpire(result);
					if (result == 0 || result == 1) {
						// 0未激活 1已过期
						userInfo.setVip(0);
					}
					userInfoService.save(userInfo);
				}
			} else {
				throw new SellException(ResultEnum.USER_NOT_EXIST);
			}
		} catch (Exception e) {
			return new ResultVOUtil().error(1, e.getMessage());
		}

		return new ResultVOUtil().success(userInfo);

	}

	@RequestMapping("/allUsers")
	public List<UserInfo> allusers() {
		List<UserInfo> infoList = userInfoService.findAll();
		return infoList;
	}

	// 重复了
	@RequestMapping("/allUser")
	public List<UserInfo> allUser() {
		List<UserInfo> userList = userInfoService.findAll();
		return userList;
	}

	// 判断是否有卡片
	@GetMapping("/hasCard")
	public ResultVO hasCard(@RequestParam("openid") String openid) {
		try {
			UserInfo userInfo = userInfoService.findByUserOpenid(openid);
			return new ResultVOUtil().success(userInfo);
		} catch (Exception e) {
			return new ResultVOUtil().error(1, e.getMessage());
		}
	}

	// 返回用户学车阶级 0考虑中 1科目一 4科目四 5拿证 首页调用 点亮图标
	@GetMapping("/stage")
	public Integer stage(@RequestParam(value = "openid", defaultValue = "") String openid) {
		try {
			UserInfo result = userInfoService.findByUserOpenid(openid);
			return result.getStage();
		} catch (Exception e) {
			return 0;
		}
	}

	// 支付阶段 0表示未支付未注册 2表示信息已经记录支付过1元 页面将向用户收取剩余费用 3表示收费结束 1繁琐废除未用
	@GetMapping("/paid")
	public Integer paidStage(@RequestParam("openid") String openid) {
		UserInfo userInfo = new UserInfo();
		userInfo = userInfoService.findByUserOpenid(openid);
		if (userInfo == null) {
			return 100;
		} else {
			return userInfo.getPaid();
		}
	}

	// 获取用户权限 // 0普通用户 101-190商户 2驾校
	@RequestMapping("/getUserCategory")
	public Integer getUserCategory(@RequestParam("openid") String openid) {

		UserInfo userInfo = userInfoService.findByUserOpenid(openid);
		try {
			if (userInfo == null) {
				throw new SellException(ResultEnum.USER_NOT_EXIST);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		Integer result = 0;
		try {
			result = userInfo.getUserCategory();
		} catch (Exception e) {

		}
		// 0普通用户 101-190商户 2驾校
		return result;
	}

	// 微信异步通知
	@PostMapping("/notify")
	public ModelAndView getNotify(@RequestBody String notifyData) throws WxPayException {
		// 1.验证签名
		// 2.支付的状态
		// 3.支付金额
		PayResponse response = bestPayServiceImpl.asyncNotify(notifyData);
		OrderDTO orderDTO = orderServiceImpl.findOne(response.getOrderId());
		if (orderDTO == null) {
			log.error("[微信支付]异步通知，订单不存在");
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		if (!MathUtil.equals(response.getOrderAmount(), orderDTO.getOrderAmount())) {
			log.error("【微信支付】异步通知，订单金额不一致,orderId={},微信通知金额={},系统金额={}", response.getOrderId(),
					response.getOrderAmount(), orderDTO.getOrderAmount());
			throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
		}
		log.info(JsonUtil.toJson(response));
		orderServiceImpl.paid(orderDTO);
		UserInfo userInfo = userInfoService.findByUserOpenid(orderDTO.getBuyerOpenid());
		// 1元保证金
		if (orderDTO.getStoreId() == 200) {
			userInfo.setPaid(2);
		}
		// 全额学费
		if (orderDTO.getStoreId() == 199) {
			userInfo.setPaid(3);
			userInfo.setVip(1);
			long day = new Date().getTime();
			long month = day + 3600 * 24 * 30 * 1000L;
			userInfo.setFirstGet(day);
			userInfo.setExpire(month);
		}
		// 会员充值99
		if (orderDTO.getStoreId() == 198) {
			// 设置为vip
			userInfo.setVip(1);
			// 将激活时间记录 并追加30天时间戳
			long day = new Date().getTime();
			long month = day + 3600 * 24 * 30 * 1000L;
			userInfo.setFirstGet(day);
			userInfo.setExpire(month);
		}
		userInfoService.save(userInfo);
		return new ModelAndView("pay/success");
	}

	// @RequestMapping("/queryTest")
	// public List<OrderMaster> queryTest() {
	// Sort sort = new Sort(Direction.DESC, "createTime");
	// return orderServiceImpl.findAll(new Specification<OrderMaster>() {
	// @Override
	// public Predicate toPredicate(Root<OrderMaster> root, CriteriaQuery<?> query,
	// CriteriaBuilder cb) {
	// Expression<? extends Number> path = root.get("payStatus");
	// Path path2 = root.get("buyerOpenid");
	// // query.where(cb.like(path, "%金%"));
	// query.where(cb.gt(path, 0), cb.equal(path2, "oIirx5NEV_ld_kAT5C7hC5ayClKI"));
	// return null;
	// }
	// }, sort);
	// }
}
