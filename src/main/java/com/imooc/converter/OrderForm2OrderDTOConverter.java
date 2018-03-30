/**
 * 
 */
package com.imooc.converter;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.Enum.ResultEnum;
import com.imooc.dataobject.OrderDetail;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;

import dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author champion
 *
 * @version Jan 13, 201812:36:31 PM
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

	public static OrderDTO converter(OrderForm orderForm) {
		// 用gson
		Gson gson = new Gson();

		OrderDTO orderDTO = new OrderDTO();

		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());

		List<OrderDetail> orderDetailList = new ArrayList<>();
		try {
			orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
			}.getType());
		} catch (Exception e) {
			log.error("【对象转换】错误，string={}", orderForm.getItems());
			throw new SellException(ResultEnum.PARAM_ERROR);
		}

		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;

	}
}
