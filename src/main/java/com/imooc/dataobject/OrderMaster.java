/**
 * 
 */
package com.imooc.dataobject;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import com.imooc.Enum.OrderStatusEnum;
import com.imooc.Enum.PayStatusEnum;

import lombok.Data;
import lombok.Getter;

/**
 * @author champion
 *
 * @version Dec 22, 20174:25:36 PM
 */
@Data
@Getter
@Entity
//自动更新时间
@DynamicUpdate
public class OrderMaster {
	@Id
	private String orderId;
	//用来存储折扣去了
	private String buyerName;
	
	private String buyerPhone;
	
	private String buyerAddress;
	
	private String buyerOpenid;
//	订单总金额
	private Double orderAmount;
	
	private Double oriPrice;
	
	private Integer storeId;
//	订单状态 0 新下单 1旧的订单
	private Integer orderStatus = OrderStatusEnum.NEW.getCode();
//	默认0为未支付
	private Integer payStatus = PayStatusEnum.WAIT.getCode();
	
	private Date createTime;
	
	private Date updateTime;
	
//	@Transient
//	已作为DTO处理
//	private List<OrderDetail> orderDetailList;

}
