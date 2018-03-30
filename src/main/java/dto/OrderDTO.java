/**
 * 
 */
package dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.Enum.OrderStatusEnum;
import com.imooc.Enum.PayStatusEnum;
import com.imooc.dataobject.OrderDetail;
import com.imooc.utils.EnumUtil;
import com.imooc.utils.serializer.Date2LongSerializer;

import lombok.Data;

/**
 * @author champion
 *
 * @version Dec 25, 201710:50:08 AM
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

	private String orderId;

	private String buyerName;
	
	private String realName = "";

	private String buyerPhone;

	private String buyerAddress;

	private String buyerOpenid;
	
	private Double oriPrice;
	
	private Integer storeId;
	
	private String clientIP;
	// 订单总金额
	private Double orderAmount;
	// 订单状态 0 新下单 1旧的订单
	private Integer orderStatus;
	// 默认0为未支付
	private Integer payStatus  ;
	
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;

	private String time;
	
	private String orderByTime;
	
	List<OrderDetail> orderDetailList;

	@JsonIgnore
	@SuppressWarnings("unchecked")
	public OrderStatusEnum getOrderStatusEnum() {
		return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
	}
	
	@JsonIgnore
	@SuppressWarnings("unchecked")
	public PayStatusEnum getPayStatusEnum() {
		return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
	}
}
