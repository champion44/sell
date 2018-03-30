package com.imooc.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Stores {

	@Id
	 @GeneratedValue
	private String id;
	
	private String storeName;
	
	private String storeAdd;
	
	private String storeCategory = "";
	
	private String storePic;
	
	private String storeSubtitle;
	
	private String storeLocation = "";
	
	private String storeTel = "";
	
	private String storeTime = "";
	
	private Integer discount;
	
	private Integer storeId;
	
	private Integer delFlag = 0;
}
