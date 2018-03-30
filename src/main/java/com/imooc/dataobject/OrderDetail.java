/**
 * 
 */
package com.imooc.dataobject;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author champion
 *
 * @version Dec 22, 20174:41:06 PM
 */
@Entity
@Data
public class OrderDetail {

	@Id
	private String detailId;
//	订单id
	private String orderId;
//	商品id
	private String productId;
	
	private String productName;
//	单价
	private BigDecimal productPrice;
//	商品数量
	private Integer productQuantity;
//	图片
	private String productIcon;
}
