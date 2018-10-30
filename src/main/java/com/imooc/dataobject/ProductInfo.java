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
 * @version 2017年12月20日下午2:36:08
 */
@Entity
@Data
public class ProductInfo {

	@Id
	private String productId;

	private String productName;

	private BigDecimal productPrice;

	private Integer productStock;

	private String productDescription;

	private String productIcon;

	// 0正常 1下架
	private Integer productStatus;

	private Integer categoryType;

}
