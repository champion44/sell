package com.imooc.converter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.imooc.dataobject.OrderMaster;

import dto.OrderDTO;

public class Timestamp2Time {

	public static OrderDTO convert(OrderMaster orderMaster) {
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format.format(orderMaster.getCreateTime());
		orderDTO.setTime(date);
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		String date2 = format2.format(orderMaster.getCreateTime());
		orderDTO.setOrderByTime(date2);
		return orderDTO;
	}

	public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
		// namuda表达式
		return orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
	}

}
