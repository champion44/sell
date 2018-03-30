/**
 * 
 */
package com.imooc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imooc.dataobject.OrderDetail;

/**
 * @author champion
 *
 * @version Dec 22, 20174:58:46 PM
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String>{
	List<OrderDetail> findByOrderId(String orderId);
}
