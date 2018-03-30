package com.imooc.dataobject;

import javax.persistence.Entity;

import lombok.Data;


public class Student {
	private String ID;
	private String StudentCode;
	private String StuName;
	private String CollegeName;
	private String MajorName;
	private String Sex;
	private String Grade;
	private String ClassCode;
	private String OrgStatus;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getStudentCode() {
		return StudentCode;
	}
	public void setStudentCode(String studentCode) {
		StudentCode = studentCode;
	}
	public String getStuName() {
		return StuName;
	}
	public void setStuName(String stuName) {
		StuName = stuName;
	}
	public String getCollegeName() {
		return CollegeName;
	}
	public void setCollegeName(String collegeName) {
		CollegeName = collegeName;
	}
	public String getMajorName() {
		return MajorName;
	}
	public void setMajorName(String majorName) {
		MajorName = majorName;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getGrade() {
		return Grade;
	}
	public void setGrade(String grade) {
		Grade = grade;
	}
	public String getClassCode() {
		return ClassCode;
	}
	public void setClassCode(String classCode) {
		ClassCode = classCode;
	}
	public String getOrgStatus() {
		return OrgStatus;
	}
	public void setOrgStatus(String orgStatus) {
		OrgStatus = orgStatus;
	}

}
