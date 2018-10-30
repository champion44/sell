package com.imooc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imooc.dataobject.BillResult;


public interface WxPayBillBaseResultListRepository extends JpaRepository<BillResult,Integer>{

}
