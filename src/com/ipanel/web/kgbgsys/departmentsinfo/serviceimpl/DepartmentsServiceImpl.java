package com.ipanel.web.kgbgsys.departmentsinfo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipanel.web.kgbgsys.common.PageDataModel;
import com.ipanel.web.kgbgsys.departmentsinfo.dao.IDepartmentListDao;
import com.ipanel.web.kgbgsys.departmentsinfo.dao.IDepartmentTypeDao;
import com.ipanel.web.kgbgsys.departmentsinfo.pageModel.DepartmentListModel;
import com.ipanel.web.kgbgsys.departmentsinfo.pageModel.DepartmentTypeModel;
import com.ipanel.web.kgbgsys.departmentsinfo.service.IDepartmentsService;
import com.ipanel.web.kgbgsys.expertinfo.dao.IDepartmentDoctorDao;
import com.ipanel.web.kgbgsys.expertinfo.pageModel.DepartmentDoctorModel;
import com.ipanel.web.kgbgsys.pojo.DepartmentDoctor;
import com.ipanel.web.kgbgsys.pojo.DepartmentList;
import com.ipanel.web.kgbgsys.pojo.DepartmentType;
import com.ipanel.web.kgbgsys.utils.Conf;
import com.ipanel.web.kgbgsys.utils.FileUtil;
import com.ipanel.web.kgbgsys.utils.FtpUtil;
import com.ipanel.web.kgbgsys.utils.IntersUtils;
import com.ipanel.webapp.framework.util.Log;

@Transactional
@Service
public class DepartmentsServiceImpl implements IDepartmentsService {
	private final String TAG = "DepartmentsServiceImpl";

	@Autowired
	private IDepartmentTypeDao iDepartmentTypeDao;

	@Autowired
	private IDepartmentListDao iDepartmentListDao;

	@Autowired
	private IDepartmentDoctorDao iDepartmentDoctorDao;

	@Override
	public void syncHostpInfo() throws Exception {
		Log.i(TAG, "start to syncHostpInfo!");
		boolean flagOfTime = true;
		// --调用医院接口获取数据
		// --1.获取获取科室类别列表
		DepartmentTypeModel dtModel = IntersUtils.getDepartCateList();
		if ((dtModel != null) && (dtModel.getStatusCode() == 200)) {
			// --获取数据成功进行相应的处理
			List<DepartmentType> listOfDepType = dtModel.getMsg();
			if ((listOfDepType != null) && (listOfDepType.size() > 0)) {
				// --进行清空表(department_type)操作
				//--iDepartmentTypeDao.executeUpdateBySql("TRUNCATE TABLE department_type");
				iDepartmentTypeDao.executeUpdateBySql("DELETE FROM department_type");
				for(DepartmentType depTypeOfHosId : listOfDepType){
					//--添加医院id
					depTypeOfHosId.setHospitalId(1);
				}
				// --将科室类别信息存入到数据库中
				// --批量添加
				iDepartmentTypeDao.batchInsert(listOfDepType);

				// --2.获取科室类别下的所有科室
				// --清空表(department_list)操作
				//--iDepartmentListDao.executeUpdateBySql("TRUNCATE TABLE department_list");
				iDepartmentListDao.executeUpdateBySql("DELETE FROM department_list");
				String picUrl = null;
				for (DepartmentType deparType : listOfDepType) {
					// --根据科室标志获取当前科室类别下的所有科室
					DepartmentListModel dListModel = IntersUtils
							.getAllDepartListOfCate(deparType.getTypeKeys());
					if ((dListModel != null)
							&& (dListModel.getStatusCode() == 200)) {
						// --获取数据成功进行相应的处理
						List<DepartmentList> listOfDeList = dListModel.getMsg();
						if ((listOfDeList != null) && (listOfDeList.size() > 0)) {
							// --挨个循环下载图片并上传到ftp服务器上
							for (DepartmentList departListForPicDown : listOfDeList) {
								picUrl = departListForPicDown.getDepartPic();
								if((picUrl != null) && (picUrl.length() > 0)){
									departListForPicDown
									.setDepartPic(picUrl.substring(picUrl
											.lastIndexOf("/") + 1));
							FileUtil.uploadRemoPicFileToFtpServer(departListForPicDown.getDepartPic(),
									Conf.HOSTPITAL_PIC_URL_PREFIX + picUrl);
								}
								//--存入医院id
								departListForPicDown.setHospitalId(1);

							}
							// --批量添加，将科室信息存入数据库中
							iDepartmentListDao.batchInsert(listOfDeList);

							if (flagOfTime) {
								// --清空表(department_doctor)操作
								//--iDepartmentDoctorDao.executeUpdateBySql("TRUNCATE TABLE department_doctor");
								iDepartmentDoctorDao.executeUpdateBySql("DELETE FROM department_doctor");
								flagOfTime = false;
							}

							// --3.获取科室下医生列表
							for (DepartmentList departList : listOfDeList) {
								// --根据科室id获取当前科室下的所有医生
								DepartmentDoctorModel dDoctorModel = IntersUtils
										.getAllDoctorListOfDepart(String
												.valueOf(departList
														.getDepartId()));
								if ((dDoctorModel != null)
										&& (dDoctorModel.getStatusCode() == 200)) {
									// --获取数据成功进行相应的处理
									List<DepartmentDoctor> listOfDeDoctorList = dDoctorModel.getMsg();
									if ((listOfDeDoctorList != null)
											&& (listOfDeDoctorList.size() > 0)) {
										// --挨个循环下载图片并上传到ftp服务器上
										for(DepartmentDoctor listDoctForPicDown : listOfDeDoctorList){
											picUrl = listDoctForPicDown.getDoctorPic();
											if((picUrl != null) && (picUrl.length() > 0)){
												listDoctForPicDown
												.setDoctorPic(picUrl.substring(picUrl
														.lastIndexOf("/") + 1));
										FileUtil.uploadRemoPicFileToFtpServer(listDoctForPicDown.getDoctorPic(),
												Conf.HOSTPITAL_PIC_URL_PREFIX + picUrl);
											}
											//--存入医院id
											listDoctForPicDown.setHospitalId(1);
										}
										
										// --批量添加，将科室医生信息存入数据库中
										iDepartmentDoctorDao.batchInsert(listOfDeDoctorList);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	
	@Override
	public PageDataModel findAllDepartment(int page, int nums) {
		//--查询出有的数据总量
		Integer departListSize = iDepartmentListDao.getAllSize();
		//--查询出指定范围类的数据
		List<DepartmentList> listOfDepaList = iDepartmentListDao.query((page - 1) * nums, nums);
		return new PageDataModel(departListSize, listOfDepaList);
	}


	@Override
	public PageDataModel findAllDepartmentTypes(int page, int nums) {
		//--查询出有的数据总量
		Integer departTypeSize = iDepartmentTypeDao.getAllSize();
		//--查询出指定范围类的数据
		List<DepartmentType> listOfDepaType = iDepartmentTypeDao.query((page - 1) * nums, nums);
		return new PageDataModel(departTypeSize, listOfDepaType);
	}


	@Override
	public PageDataModel findDepartmentListByTypeKeys(int page, int nums, String typeKeys) {
		//--查询出有的数据总量
		Integer departListSize = iDepartmentListDao.getAllSize(" where departSort=" + "'" + typeKeys + "'");
		//--查询出指定范围类的数据
		List<DepartmentList> listOfDepaList = iDepartmentListDao.query("from DepartmentList where departSort=:departSort order by id asc",
				"departSort", typeKeys, page, nums);
		return new PageDataModel(departListSize, listOfDepaList);
	}


	@Override
	public DepartmentList findDepartmentInfoByDepartId(int departId) {
		return iDepartmentListDao.findByPropertyPojo("departId", departId);
	}
	
	
	
	
	
	

}
