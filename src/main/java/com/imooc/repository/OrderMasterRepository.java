/**
 * 
 */
package com.imooc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imooc.dataobject.OrderMaster;

/**
 * @author champion
 *
 * @version Dec 22, 20174:53:29 PM
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> ,JpaSpecificationExecutor<OrderMaster>{

	Page<OrderMaster> findByBuyerOpenid(String buyerOpenid,Pageable pageable);
	
	List<OrderMaster> findByStoreId(Specification<OrderMaster> spec,Sort sort);
	
	List<OrderMaster> findByBuyerOpenid(Specification<OrderMaster> spec,Sort sort);
	
	List<OrderMaster> findByPayStatus(Integer payStatus,Sort sort);
	
	List<OrderMaster> findAll(Specification<OrderMaster> spec,Sort sort); 
	
}
