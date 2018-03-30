package com.imooc.dataobject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserInfo {
	
	@Id
	@GeneratedValue
	private Integer userId;
	
	private String userOpenid;
	
	private String userName = "";
	
	private String userPhone = "";
	
	private String userAdd = "";
	
	private String userWxname;
	
	private String userAvater;
	
	private Integer userGender;
	
	private Integer stage;
	
	private String userNum;
	
	private Integer paid;
	
	private String cardNum;
	
	private Integer vip;
	
	private String bookDate;
	
	private Integer booked;
	
	private Integer userCategory;
	
	private long firstGet = 0;
	
	private long expire = 0;
}
