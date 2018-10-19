package com.ipanel.web.kgbgsys.expertinfo.service;

import com.ipanel.web.kgbgsys.common.PageDataModel;
import com.ipanel.web.kgbgsys.pojo.DepartmentDoctor;


public interface IExpertService {
	
	//--分页查询指定科室下全部医生
	PageDataModel findAllExpertByDepartId(int page, int nums, String departId);
	
	//--查询对应医生全部信息
	DepartmentDoctor findExpertByDoctorId(int doctorId);
	
}
