package com.imooc.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	public static String toJson(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
/*
	public static List<Student> fromJson(String ori) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		List<Student> studentList = (List<Student>) gson.fromJson(ori, new TypeToken<List<Student>>() {
		}.getType());
		return studentList;
	}
	*/
}
