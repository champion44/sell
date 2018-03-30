/**
 * 
 */
package dto;

import lombok.Data;

/**
 * @author champion
 *
 * @version Jan 11, 201812:14:25 AM
 */
@Data
public class CartDTO {
//商品id
	private String productId;
//	商品数量
	private Integer productQuantity;
/**
 * @param productId
 * @param productQuantity
 */
public CartDTO(String productId, Integer productQuantity) {
	this.productId = productId;
	this.productQuantity = productQuantity;
}
	
}
