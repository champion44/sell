package com.imooc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.Enum.ResultEnum;
import com.imooc.dataobject.WxOrder;
import com.imooc.exception.SellException;
import com.imooc.mapper.WxOrderMapper;
import com.imooc.service.WxOrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WxOrderServiceImpl implements WxOrderService {

	@Autowired
	private WxOrderMapper wxOrderMapper;

	@Autowired
	private WxOrderServiceImpl wxOrderServiceImpl;

	@Override
	public int insert(WxOrder record) {
		return wxOrderMapper.insert(record);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.imooc.service.WxOrderService#selectByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public WxOrder selectByPrimaryKey(String id) {
		return wxOrderMapper.selectByPrimaryKey(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.imooc.service.WxOrderService#paid()
	 */
	@Override
	public Integer paid(WxOrder orderDTO) {
		// 查询该订单
		WxOrder o = new WxOrder();
		Integer result  = 0;
		o = wxOrderMapper.selectByPrimaryKey(orderDTO.getId());
		if (o == null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		} else if (o.getPayflag() == 1) {
			// 支付状态不正确
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		} else {
			// 将支付状态修改为以支付
			o.setPayflag(1);
			try {
				 result = wxOrderMapper.updateByPrimaryKey(o);
			}catch (SellException e) {
				throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
			}catch (Exception e) {
				log.info(e.getMessage());
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.imooc.service.WxOrderService#update(com.imooc.dataobject.WxOrder)
	 */
	@Override
	public int update(WxOrder orderDTO) {
		return wxOrderServiceImpl.update(orderDTO);
	}

}
