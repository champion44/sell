/**
 * 
 */
package com.imooc.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.imooc.dataobject.OrderMaster;

import dto.OrderDTO;

/**
 * @author champion
 *
 * @version Jan 12, 20186:58:07 PM
 */
public class OrderMaster2OrderDTOConverter {

	public static OrderDTO convert(OrderMaster orderMaster) {
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		return orderDTO;
	}
	
	public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
//namuda表达式
	return	orderMasterList.stream().map(e->convert(e)).collect(Collectors.toList());
	}
}
