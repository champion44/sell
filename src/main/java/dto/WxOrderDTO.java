/**
 * 
 */
package dto;
/**
* @author champion
* @version 创建时间：Nov 6, 2018 2:48:21 PM
* 类说明
*/
/**
 * @author champion
 *
 */
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WxOrderDTO {

	private String id;

	private String storephone;

	private String custname;

	private String custphone;

	private String custaddr;

	private Integer productid;

	private Double orderfee;

	private Double buyfee;

	private Double mailfee;

	private Double totalfee;

	private Integer gettype;

	private Integer payflag;
	
	private String openid;
}
