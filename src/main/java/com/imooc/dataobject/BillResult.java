package com.imooc.dataobject;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class BillResult {
	@Id
	private String billDate;
	private String totalRecord;
	private String totalFee;
	private String totalRefundFee;
	private String totalCouponFee;
	private String totalPoundageFee;
	private String wxPayBillBaseResultList;
}
