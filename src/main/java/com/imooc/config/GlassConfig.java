/**
 * 
 */
package com.imooc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
* @author champion
* @version 创建时间：Nov 6, 2018 10:25:54 AM
* 类说明
*/
/**
 * @author champion
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "glass")
public class GlassConfig {
	private Double mailFee;

}
