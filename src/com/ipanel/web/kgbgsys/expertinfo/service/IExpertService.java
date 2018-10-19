package com.ipanel.web.kgbgsys.expertinfo.service;

import com.ipanel.web.kgbgsys.common.PageDataModel;
import com.ipanel.web.kgbgsys.pojo.DepartmentDoctor;


public interface IExpertService {
	
	//--��ҳ��ѯָ��������ȫ��ҽ��
	PageDataModel findAllExpertByDepartId(int page, int nums, String departId);
	
	//--��ѯ��Ӧҽ��ȫ����Ϣ
	DepartmentDoctor findExpertByDoctorId(int doctorId);
	
}
