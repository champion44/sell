package com.imooc.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.dataobject.WxProductInfo;
import com.imooc.service.impl.WxProductInfoServiceImpl;

import lombok.extern.slf4j.Slf4j;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WxProductInfoTest {

	@Autowired
	private WxProductInfoServiceImpl serviceImpl;
	
	@Test
	public void test() {
		WxProductInfo wxProductInfo = new WxProductInfo();
		wxProductInfo.setStoreid("000000");
		wxProductInfo.setPhonenum("12322123112");
		wxProductInfo.setProductname("墨镜");
		wxProductInfo.setStars("4.5");
		wxProductInfo.setBrand("Chanel");
		wxProductInfo.setFullprice("1000");
		wxProductInfo.setSaleprice("600");
		wxProductInfo.setDeposit("35");
		wxProductInfo.setIndexpage("产品封面");
		wxProductInfo.setProductinfo("产品信息");
		wxProductInfo.setRemark1("re1");
		wxProductInfo.setRemark2("re2");
		Integer result = serviceImpl.insert(wxProductInfo);
		log.info(result+"");
	}

}
