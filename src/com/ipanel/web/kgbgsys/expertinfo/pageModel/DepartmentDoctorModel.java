package com.ipanel.web.kgbgsys.expertinfo.pageModel;


import java.util.List;

import com.ipanel.web.kgbgsys.pojo.DepartmentDoctor;

public class DepartmentDoctorModel implements java.io.Serializable {
	
	private Integer statusCode;
	
	private List<DepartmentDoctor> msg;

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public List<DepartmentDoctor> getMsg() {
		return msg;
	}

	public void setMsg(List<DepartmentDoctor> msg) {
		this.msg = msg;
	}
	

}
