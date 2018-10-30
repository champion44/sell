package com.imooc.crimnal;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KillCrimnal {

	@Test
	public void testKillCrimnal() {
		ArrayList<Object> ori = new ArrayList<>();
		//		赋值
		for(int i = 1; i<=100; i++) {
			ori.add(i);
		}
		log.info("****************************************************************");
//		杀人逻辑
		do {
			ArrayList<Object>  temp = new ArrayList<>();
//			将幸存者保留 并重新排号
			for(int j = 0;j<ori.size();j++) {
				if((j+1)%2==0) {
//					活人留下 
					temp.add(ori.get(j));
				}
			}
//			一次循环后 更新幸存者数组
			ori = temp;
		}while(
//				当幸存者超出1个便不停处死犯人
				ori.size()>1
				);
		System.out.println(ori);
	}
}
