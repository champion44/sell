/**
 * 
 */
package com.imooc.VO;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author champion
 *
 * @version 2017年12月20日下午4:10:05
 */
@Data
public class ProductInfoVO {

	@JsonProperty("id")
	private String productId;
	
	@JsonProperty("name")
	private String productName;
	
	@JsonProperty("price")
	private BigDecimal productPrice;
	
	@JsonProperty("description")
	private String productDescription;
	
	@JsonProperty("icon")
	private String productIcon;
}
