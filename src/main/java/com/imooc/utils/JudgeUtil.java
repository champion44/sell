package com.imooc.utils;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.imooc.VO.ResultVO;
import com.imooc.dataobject.Student;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JudgeUtil {

	public static ResultVO judge(String name, String id) {
		// String path = "/Users/champion/Desktop/all.json";
		String path = "/usr/java/studentsInfo/all.json";

		try {
			String input = FileUtils.readFileToString(new File(path), "UTF-8");
			List<Student> result = JsonUtil.fromJson(input);
			String n = "解析结束";
			log.info(n);
			for (Student s : result) {
				if (s.getStuName().equals(name)) {
					if (s.getID().equals(id)) {
						return new ResultVOUtil().success(s);
					} else {
						return new ResultVOUtil().error(1, "输入不匹配");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResultVOUtil().error(2, "系统没您的记录");
	}
}
