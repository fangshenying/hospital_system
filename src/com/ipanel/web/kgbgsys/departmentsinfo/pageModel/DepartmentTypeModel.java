package com.ipanel.web.kgbgsys.departmentsinfo.pageModel;

import java.util.List;

import com.ipanel.web.kgbgsys.pojo.DepartmentType;

public class DepartmentTypeModel implements java.io.Serializable {
	
	private Integer statusCode;
	
	private List<DepartmentType> msg;
	
	
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public List<DepartmentType> getMsg() {
		return msg;
	}
	public void setMsg(List<DepartmentType> msg) {
		this.msg = msg;
	}
	
	

}
