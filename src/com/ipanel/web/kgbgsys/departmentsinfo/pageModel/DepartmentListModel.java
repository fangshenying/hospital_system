package com.ipanel.web.kgbgsys.departmentsinfo.pageModel;

import java.util.List;

import com.ipanel.web.kgbgsys.pojo.DepartmentList;

public class DepartmentListModel implements java.io.Serializable {
	
	private Integer statusCode;
	
	private List<DepartmentList> msg;
	

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public List<DepartmentList> getMsg() {
		return msg;
	}

	public void setMsg(List<DepartmentList> msg) {
		this.msg = msg;
	}

}
