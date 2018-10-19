package com.ipanel.web.kgbgsys.departmentsinfo.service;

import com.ipanel.web.kgbgsys.common.PageDataModel;
import com.ipanel.web.kgbgsys.pojo.DepartmentList;


public interface IDepartmentsService {
	
	//--同步医院信息
	public void syncHostpInfo() throws Exception;
	
	//--分页显示全部科室
	PageDataModel findAllDepartment(int page, int nums);
	
	//--分页显示全部科室类别
	PageDataModel findAllDepartmentTypes(int page, int nums);
	
	//--分页通过科室类别标识查询所有科室
	PageDataModel findDepartmentListByTypeKeys(int page, int nums, String typeKeys);
	
	//--通过科室id查询科室详情
	DepartmentList findDepartmentInfoByDepartId(int departId);
	
}
