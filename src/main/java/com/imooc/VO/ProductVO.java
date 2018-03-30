/**
 * 
 */
package com.imooc.VO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author champion
 *
 * @version 2017年12月20日下午4:07:08
 */
@Data
public class ProductVO {
	
	@JsonProperty("name")
	private String categoryName;
	
	@JsonProperty("type")
	private Integer categoryType;
	
	@JsonProperty("foods")
	private List<ProductInfoVO> productInfoVOList;
}
