/**
 * 
 */
package com.imooc.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.Enum.OrderStatusEnum;
import com.imooc.Enum.PayStatusEnum;
import com.imooc.Enum.ResultEnum;
import com.imooc.converter.Timestamp2Time;
import com.imooc.dataobject.OrderDetail;
import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.ProductInfo;
import com.imooc.dataobject.UserInfo;
import com.imooc.exception.SellException;
import com.imooc.repository.OrderDetailRepository;
import com.imooc.repository.OrderMasterRepository;
import com.imooc.service.OrderService;
import com.imooc.service.ProductService;
import com.imooc.utils.KeyUtil;

import dto.CartDTO;
import dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author champion
 *
 * @version Jan 9, 20189:14:22 PM
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	@Autowired
	private WechatPayServiceImpl wechatPayServiceImpl;
	@Autowired
	private UserInfoServiceImpl userInfoServiceImpl;

	@Override
	@Transactional
	public OrderMaster createWX(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderAmount(orderDTO.getOrderAmount());
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterRepository.save(orderMaster);
		return orderMaster;
	}

	// not for redant
	@Override
	@Transactional
	public OrderDTO create(OrderDTO orderDTO) {
		String orderId = KeyUtil.getUniqueKey();
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
		// controller调用
		// 1查询商品 数量 价格
		// 2计算总价
		// 3写入订单数据库 两个 ordermaster orderdetail
		// 4扣库存

		// 查询商品
		for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			// 计算总价
			orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
					.add(orderAmount);
			// 订单详情入库
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetail.setOrderId(orderId);
			orderDetail.setDetailId(KeyUtil.getUniqueKey());
			orderDetailRepository.save(orderDetail);
		}
		// 写入订单数据库
		OrderMaster orderMaster = new OrderMaster();
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
		// Integer amount = orderAmount.intValue();
		Double amount = orderAmount.doubleValue();
		orderMaster.setOrderAmount(amount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterRepository.save(orderMaster);

		// 扣库存 注意访问量大的时候 超卖
		// TODO
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
				.map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
		productService.decreaseStock(cartDTOList);

		return orderDTO;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
		if (orderMaster == null) {
			log.info(orderId);
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
		if (orderDetailList == null) {
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
		// 不需要判断是否为空
		List<OrderDTO> orderDTOList = Timestamp2Time.convert(orderMasterPage.getContent());

		Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
		return orderDTOPage;
	}

	@Override
	@Transactional
	public OrderDTO cancel(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();
		// 先判断订单状态 已经接单 已经完结 不能取消
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		// 修改订单状态为取消
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEl.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【取消订单】更新失败，orderMaster={},", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		// 回退库存
		/*
		 * if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
		 * log.error("【取消订单】订单中无商品详情，orderDTO={}", orderDTO); throw new
		 * SellException(ResultEnum.ORDER_DETAIL_EMPTY); } List<CartDTO> cartDTOList =
		 * orderDTO.getOrderDetailList().stream() .map(e -> new
		 * CartDTO(e.getProductId(),
		 * e.getProductQuantity())).collect(Collectors.toList());
		 * 
		 * productService.increaseStock(cartDTOList);
		 */
		// 退款 修改支付状态
		if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
			// TODO
			wechatPayServiceImpl.refund(orderDTO);
		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO finish(OrderDTO orderDTO) {
		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【完结订单】订单状态不正确，orderId={}", orderDTO.getOrderId());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		// 修改状态
		orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【完结订单】更新失败，orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		// 判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【完结支付成功】订单状态不正确，orderId={}", orderDTO.getOrderId());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		// 判断支付状态
		if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【订单支付完成】订单支付状态不正确，orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if (updateResult == null) {
			log.error("【订单支付完成】更新失败，orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		// 修改支付状态
		return orderDTO;
	}

	@Override
	public Page<OrderMaster> findList(Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
		// List<OrderDTO> orderDTOList =
		// OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		// Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable,
		// orderMasterPage.getTotalElements());
		return orderMasterPage;
	}

	@Override
	public List<OrderMaster> findByBuyerOpenid(Specification<OrderMaster> spec, Sort sort) {
		List<OrderMaster> orderMasterList = orderMasterRepository.findByBuyerOpenid(spec, sort);
		return orderMasterList;
	}

	@Override
	public OrderMaster update(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		return orderMasterRepository.save(orderMaster);
	}

	// 通过userCategory对应的storeID进行查询
	@Override
	public List<OrderMaster> findByStoreId(Specification<OrderMaster> spec, Sort sort) {
		List<OrderMaster> orderMasterPage = orderMasterRepository.findByStoreId(spec, sort);
		// List<OrderDTO> orderDTOList = Timestamp2Time.convert(orderMasterPage);
		// Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable,
		// orderMasterPage.getTotalElements());
		return orderMasterPage;
	}

	@Override
	public List<OrderDTO> findByPayStatus(Integer patStatus, Sort sort) {
		List<OrderMaster> orderMasterPage = orderMasterRepository.findByPayStatus(patStatus, sort);
		List<OrderDTO> orderDTOList = Timestamp2Time.convert(orderMasterPage);
		List<OrderDTO> newOrderDTOList = new ArrayList<OrderDTO>();
		for (OrderDTO orderDTO : orderDTOList) {
			try {
				UserInfo info = userInfoServiceImpl.findByUserOpenid(orderDTO.getBuyerOpenid());
				if (info.getUserName().equals("") || info.getUserName() == null) {
					orderDTO.setRealName(info.getUserWxname());
				} else {
					orderDTO.setRealName(info.getUserName());
				}
			} catch (Exception e) {
				orderDTO.setRealName("无用户");
			}
			newOrderDTOList.add(orderDTO);
		}
		// Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable,
		// orderMasterPage.getTotalElements());
		return newOrderDTOList;
	}

	@Override
	public List<OrderMaster> findAll(Specification<OrderMaster> spec, Sort sort) {
		List<OrderMaster> list = orderMasterRepository.findAll(spec, sort);
		return list;
	}

}
