/**
 * 
 */
package com.imooc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;

import com.imooc.Enum.PayStatusEnum;
import com.imooc.Enum.ResultEnum;
import com.imooc.VO.ResultVO;
import com.imooc.converter.OrderForm2OrderDTOConverter;
import com.imooc.converter.Timestamp2Time;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.UserInfo;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import com.imooc.service.impl.UserInfoServiceImpl;
import com.imooc.service.impl.WechatPayServiceImpl;
import com.imooc.utils.JudgeUtil;
import com.imooc.utils.ResultVOUtil;

import dto.OrderDTO;
import dto.OrderDTOForStore;
import lombok.extern.slf4j.Slf4j;

/**
 * @author champion
 *
 * @version Jan 13, 201812:16:02 PM
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private UserInfoServiceImpl userInfoService;
	@Autowired
	private WechatPayServiceImpl wechatPayServiceImpl;

	// 首次进入 写入数据
	@GetMapping("/firstIn")
	public ResultVO firstIn(@RequestParam("openid") String openid,
			@RequestParam(value = "gender", defaultValue = "1") Integer gender,
			@RequestParam(value = "wxName", defaultValue = "nul") String wxName,
			@RequestParam("avatarUrl") String avatarUrl) {
		UserInfo userInfo = new UserInfo();
		UserInfo isExist = userInfoService.findByUserOpenid(openid);
		if (isExist == null) {
			userInfo.setStage(0);
			userInfo.setCardNum("nul");
			userInfo.setUserOpenid(openid);
			userInfo.setUserWxname(wxName);
			userInfo.setUserGender(gender);
			userInfo.setBookDate("");
			userInfo.setBooked(0);
			userInfo.setUserCategory(0);
			userInfo.setUserAvater(avatarUrl);
			userInfo.setVip(0);
			userInfo.setPaid(0);
			log.info("第一次新增");
			try {
				userInfoService.save(userInfo);
			} catch (Exception es) {
				return new ResultVOUtil().error(1, es.getMessage());
			}
		}
		return new ResultVOUtil().success();
	}

	// 记录用户姓名 学号
	@GetMapping("/record")
	public ResultVO record(@RequestParam(value = "name", defaultValue = "nul") String name,
			@RequestParam(value = "studentCode", defaultValue = "nul") String studentCode,
			@RequestParam("openid") String openid, @RequestParam(value = "phone", defaultValue = "nul") String phone,
			@RequestParam(value = "add", defaultValue = "nul") String add) {
		UserInfo userInfo = userInfoService.findByUserOpenid(openid);
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		if (name != "nul") {
			userInfo.setUserName(name);
		}
		if (studentCode != "nul") {
			userInfo.setUserNum(studentCode);
		}
		if (phone != "nul") {
			userInfo.setUserPhone(phone);
		}
		if (add != "nul") {
			userInfo.setUserAdd(add);
		}
		userInfo.setUserOpenid(openid);
		userInfo.setPaid(0);
		userInfo.setVip(0);
		userInfo.setBooked(0);
		UserInfo result = null;
		try {
			result = userInfoService.save(userInfo);
		} catch (Exception e) {
			log.info(e.getMessage());
			return ResultVOUtil.error(1, "未能新增");
		}
		return ResultVOUtil.success(result);
		/*
		 * ResultVO resultV = JudgeUtil.judge(name, studentCode); log.info("第二次新增"); if
		 * (resultV.getCode() == 0) { UserInfo userInfo =
		 * userInfoService.findByUserOpenid(openid); if (userInfo == null) { userInfo =
		 * new UserInfo(); } if (name != "nul") { userInfo.setUserName(name); } if
		 * (studentCode != "nul") { userInfo.setUserNum(studentCode); } if (phone !=
		 * "nul") { userInfo.setUserPhone(phone); } userInfo.setUserOpenid(openid);
		 * userInfo.setPaid(0); userInfo.setVip(0); userInfo.setBooked(0); if (add !=
		 * "nul") { userInfo.setUserAdd(add); } UserInfo result = null; try { result =
		 * userInfoService.save(userInfo); } catch (Exception e) {
		 * log.info(e.getMessage()); return ResultVOUtil.error(1, "未能新增"); } return
		 * ResultVOUtil.success(result); } else { return resultV; }
		 */
	}

	// 创建订单 不是该项目
	@PostMapping("/create")
	public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【创建订单】参数不正确，orderForm={}", orderForm);
			throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
			// 具体是那个参数呢
		}

		OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【创建订单】失败 购物车不能为空");
			throw new SellException(ResultEnum.CART_EMPTY);
		}
		OrderDTO createResult = orderService.create(orderDTO);
		Map<String, String> map = new HashMap<>();
		map.put("orderId", createResult.getOrderId());
		return ResultVOUtil.success(map);
	}

	// 订单列表
	@GetMapping("/list")
	// 里面的对象可以使用orderDTO来写
	public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		if (StringUtils.isEmpty(openid)) {
			log.error("【查询订单列表】openid为空");
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		PageRequest request = new PageRequest(0, 200, sort);
		Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);
		return ResultVOUtil.success(orderDTOPage.getContent());

	}

	// 返回给商户的订单信息 应该重新封装 并且需要将日期分组 算出总金额和每天的金额 !!!未包含名字
	@GetMapping("/getOrderListByStoreId")
	public ResultVO<List<OrderDTO>> getOrderListByStoreId(@RequestParam("userCategory") Integer userCategory) {
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OrderMaster> list = orderService.findAll(new Specification<OrderMaster>() {
			@Override
			public Predicate toPredicate(Root<OrderMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Expression<? extends Number> path = root.get("payStatus");
				Expression<? extends Number> path2 = root.get("storeId");
				query.where(cb.gt(path, 0), cb.equal(path2, userCategory));
				return null;
			}
		}, sort);
		List<OrderDTO> orderDTOList = Timestamp2Time.convert(list);
		List<String> timeList = new ArrayList<String>();
		if (orderDTOList.size() != 0) {
			timeList.add(orderDTOList.get(0).getOrderByTime());
		}
		if (orderDTOList.size() != 0) {
			for (OrderDTO orderDTO : orderDTOList) {
				for (Integer i = 0; i < timeList.size(); i++) {
					if (orderDTO.getOrderByTime().equalsIgnoreCase(timeList.get(i))) {
						break;
					}
					if (i == timeList.size() - 1) {
						timeList.add(orderDTO.getOrderByTime());
					}
				}
			}
		}
		List<OrderDTOForStore> orderDTOForStoreList = new ArrayList<>();
		for (String time : timeList) {
			OrderDTOForStore orderDTOForStore = new OrderDTOForStore();
			orderDTOForStore.setTime(time);
			Double total = 0.0;
			Double refund = 0.0;
			List<OrderDTO> DTOList = new ArrayList<>();
			for (OrderDTO order : orderDTOList) {
				if (order.getOrderByTime().equals(time)) {
					DTOList.add(order);
					if (order.getPayStatus() == 1) {
						total += order.getOrderAmount();
					} else if (order.getPayStatus() == 2) {
						refund += order.getOrderAmount();
					}
				}
			}
			BigDecimal bg = new BigDecimal(total);
			BigDecimal bg2 = new BigDecimal(refund);
			double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double f2 = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			orderDTOForStore.setRefund(f2);
			orderDTOForStore.setTotal(f1);
			orderDTOForStore.setOrderDTOForStoreList(DTOList);
			orderDTOForStoreList.add(orderDTOForStore);
		}
		return ResultVOUtil.success(orderDTOForStoreList);
	}

	// 订单列表封装成list返回json给小程序 重新封装成有用户姓名的
	@GetMapping("/listType")
	// 里面的对象可以使用orderDTO来写
	public ResultVO<List<OrderMaster>> list(@RequestParam("openid") String openid) {
		if (StringUtils.isEmpty(openid)) {
			log.error("【查询订单列表】openid为空");
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OrderMaster> list = orderService.findAll(new Specification<OrderMaster>() {
			@Override
			public Predicate toPredicate(Root<OrderMaster> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Expression<? extends Number> path = root.get("payStatus");
				Path path2 = root.get("buyerOpenid");
				query.where(cb.gt(path, 0), cb.equal(path2, openid));
				return null;
			}
		}, sort);
		List<OrderDTO> orderDTOList = Timestamp2Time.convert(list);
		return ResultVOUtil.success(orderDTOList);
	}

	// 管理员端的付款成功的订单 多表联合查询没做
	@GetMapping("/paidOrderList")
	public ResultVO<List<OrderDTO>> paidOrderList() {
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OrderDTO> orderDTOPage = orderService.findByPayStatus(1, sort);
		return ResultVOUtil.success(orderDTOPage);
	}

	@GetMapping("/refundOrderList")
	public ResultVO<List<OrderDTO>> refundOrderList() {
		Sort sort = new Sort(Direction.DESC, "createTime");
		List<OrderDTO> orderDTOPage = orderService.findByPayStatus(2, sort);
		return ResultVOUtil.success(orderDTOPage);
	}

	// 订单详情
	@GetMapping("/detail")
	public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
		// TODO不安全的做法 不能传入orderId就查询 加入验证
		OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
		return ResultVOUtil.success(orderDTO);
	}

	// 退款
	@GetMapping("/refund")
	public ResultVO refund(@RequestParam("orderId") String orderId) {
		OrderDTO orderDTO = null;
		try {
			orderDTO = orderService.findOne(orderId);
			if (orderDTO.getOrderAmount() > 1.1) {
				throw new SellException(ResultEnum.REFUND_MONEY_ERROR);
			}
		} catch (Exception e) {
			return new ResultVOUtil().error(1, e.getMessage());
		}
		// 修改用户状态 可重新预约 全额缴费后不修改paid状态
		try {
			wechatPayServiceImpl.refund(orderDTO);
			orderDTO.setPayStatus(PayStatusEnum.REFUNDED.getCode());
			orderService.update(orderDTO);
			String openid = orderDTO.getBuyerOpenid();
			UserInfo info = userInfoService.findByUserOpenid(openid);
			if (info.getPaid() == 2) {
				info.setPaid(0);
			}
			userInfoService.save(info);
		} catch (Exception e) {
			return new ResultVOUtil().error(1, e.getMessage());
		}
		return new ResultVOUtil().success();

	}

	// 判断用户是否已经注册
	/*
	 * @GetMapping("/isregi") public Boolean isregi(@RequestParam("openid") String
	 * openid) { Boolean result = userInfoService.findByUserOpenid(openid); return
	 * result; }
	 */
	// 取消订单
	@PostMapping("/cancel")
	public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
		buyerService.cancelOrder(openid, orderId);
		return ResultVOUtil.success();
	}
}
