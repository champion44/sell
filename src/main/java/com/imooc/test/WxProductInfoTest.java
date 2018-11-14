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

	
	@Test
	public void test() {
	
		String s ="186280207321";
		//String s1 = s.substring(0,11);
		if(s.length()>11) {
			s = s.substring(0, 11);
		}
		log.info(s);
	}

}
