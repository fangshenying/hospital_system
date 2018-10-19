package com.ipanel.web.kgbgsys.departmentsinfo.service;

import com.ipanel.web.kgbgsys.common.PageDataModel;
import com.ipanel.web.kgbgsys.pojo.DepartmentList;


public interface IDepartmentsService {
	
	//--ͬ��ҽԺ��Ϣ
	public void syncHostpInfo() throws Exception;
	
	//--��ҳ��ʾȫ������
	PageDataModel findAllDepartment(int page, int nums);
	
	//--��ҳ��ʾȫ���������
	PageDataModel findAllDepartmentTypes(int page, int nums);
	
	//--��ҳͨ����������ʶ��ѯ���п���
	PageDataModel findDepartmentListByTypeKeys(int page, int nums, String typeKeys);
	
	//--ͨ������id��ѯ��������
	DepartmentList findDepartmentInfoByDepartId(int departId);
	
}
