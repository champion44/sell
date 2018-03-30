package com.imooc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.imooc.dataobject.OrderMaster;
import com.imooc.dataobject.Stores;
import com.imooc.service.StoresService;
import com.imooc.service.impl.OrderServiceImpl;

import dto.OrderDTO;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

	@Autowired
	private OrderServiceImpl orderService;
	@Autowired
	private StoresService storesService;

	// 卖家网页订单列表
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
		Sort sort = new Sort(Direction.DESC, "updateTime");
		PageRequest request = new PageRequest(page - 1, size, sort);
		Page<OrderMaster> orderDTOPage = orderService.findList(request);
		map.put("orderDTOPage", orderDTOPage);
		map.put("currentPage", page);
		return new ModelAndView("order/orderList", map);
	}

	// 卖家网页商户列表
	@GetMapping("/storeList")
	public ModelAndView storeList(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
		PageRequest request = new PageRequest(page - 1, size);
		Page<Stores> storesPage = storesService.findList(request);
		map.put("storesPage", storesPage);
		return new ModelAndView("stores/storesList", map);
	}
}
