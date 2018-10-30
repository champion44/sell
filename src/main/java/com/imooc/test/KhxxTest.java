package com.imooc.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.dataobject.Khxx;
import com.imooc.dataobject.sgjc;
import com.imooc.dataobject.sgjcKey;
import com.imooc.mapper.sgjcMapper;
import com.imooc.service.impl.KhxxServiceImpl;
import com.imooc.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KhxxTest {

	@Autowired
	private KhxxServiceImpl khxxService;

	@Autowired
	private sgjcMapper sgjcmapper;

	@Test
	public void test() {
		Khxx k = khxxService.selectByPrimaryKey(new Long(1));
		log.info(k.toString());
	}

	@Test
	public void test2() {
		sgjcKey key = new sgjcKey(Integer.parseInt("0"), "20141001195033", "朱海菊", "a632dcbe9faca5c98b8ac15c0882c3f9",
				"00000000000");
		sgjc s = sgjcmapper.selectByPrimaryKey(key);
		log.info(JsonUtil.toJson(s));
	}
}
