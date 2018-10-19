package com.ipanel.web.kgbgsys.expertinfo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipanel.web.kgbgsys.common.PageDataModel;
import com.ipanel.web.kgbgsys.departmentsinfo.dao.IDepartmentListDao;
import com.ipanel.web.kgbgsys.departmentsinfo.dao.IDepartmentTypeDao;
import com.ipanel.web.kgbgsys.expertinfo.dao.IDepartmentDoctorDao;
import com.ipanel.web.kgbgsys.expertinfo.service.IExpertService;
import com.ipanel.web.kgbgsys.pojo.DepartmentDoctor;
import com.ipanel.web.kgbgsys.pojo.DepartmentList;

@Transactional
@Service
public class ExpertServiceImpl implements IExpertService {
	private final String TAG = "ExpertServiceImpl";

	@Autowired
	private IDepartmentTypeDao iDepartmentTypeDao;

	@Autowired
	private IDepartmentListDao iDepartmentListDao;

	@Autowired
	private IDepartmentDoctorDao iDepartmentDoctorDao;

	
	@Override
	public PageDataModel findAllExpertByDepartId(int page, int nums, String departId) {
		//--查询出有的数据总量
		Integer departDoctorListSize = iDepartmentDoctorDao.getAllSize(" where doctorSort=" + "'" + departId + "'");
		//--查询出指定范围类的数据
		List<DepartmentDoctor> listOfDepaDoctList = iDepartmentDoctorDao.query("from DepartmentDoctor where doctorSort=:doctorSort order by id asc",
				"doctorSort", departId, page, nums);
		return new PageDataModel(departDoctorListSize, listOfDepaDoctList);
	}


	@Override
	public DepartmentDoctor findExpertByDoctorId(int doctorId) {
		return iDepartmentDoctorDao.findByPropertyPojo("doctorId", doctorId);
	}
	
	
	
	
}
