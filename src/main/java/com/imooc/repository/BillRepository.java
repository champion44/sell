package com.imooc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.dataobject.Bill;

public interface BillRepository extends JpaRepository<Bill, String>{

}
