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
		// --����ҽԺ�ӿڻ�ȡ����
		// --1.��ȡ��ȡ��������б�
		DepartmentTypeModel dtModel = IntersUtils.getDepartCateList();
		if ((dtModel != null) && (dtModel.getStatusCode() == 200)) {
			// --��ȡ���ݳɹ�������Ӧ�Ĵ���
			List<DepartmentType> listOfDepType = dtModel.getMsg();
			if ((listOfDepType != null) && (listOfDepType.size() > 0)) {
				// --������ձ�(department_type)����
				//--iDepartmentTypeDao.executeUpdateBySql("TRUNCATE TABLE department_type");
				iDepartmentTypeDao.executeUpdateBySql("DELETE FROM department_type");
				for(DepartmentType depTypeOfHosId : listOfDepType){
					//--���ҽԺid
					depTypeOfHosId.setHospitalId(1);
				}
				// --�����������Ϣ���뵽���ݿ���
				// --�������
				iDepartmentTypeDao.batchInsert(listOfDepType);

				// --2.��ȡ��������µ����п���
				// --��ձ�(department_list)����
				//--iDepartmentListDao.executeUpdateBySql("TRUNCATE TABLE department_list");
				iDepartmentListDao.executeUpdateBySql("DELETE FROM department_list");
				String picUrl = null;
				for (DepartmentType deparType : listOfDepType) {
					// --���ݿ��ұ�־��ȡ��ǰ��������µ����п���
					DepartmentListModel dListModel = IntersUtils
							.getAllDepartListOfCate(deparType.getTypeKeys());
					if ((dListModel != null)
							&& (dListModel.getStatusCode() == 200)) {
						// --��ȡ���ݳɹ�������Ӧ�Ĵ���
						List<DepartmentList> listOfDeList = dListModel.getMsg();
						if ((listOfDeList != null) && (listOfDeList.size() > 0)) {
							// --����ѭ������ͼƬ���ϴ���ftp��������
							for (DepartmentList departListForPicDown : listOfDeList) {
								picUrl = departListForPicDown.getDepartPic();
								if((picUrl != null) && (picUrl.length() > 0)){
									departListForPicDown
									.setDepartPic(picUrl.substring(picUrl
											.lastIndexOf("/") + 1));
							FileUtil.uploadRemoPicFileToFtpServer(departListForPicDown.getDepartPic(),
									Conf.HOSTPITAL_PIC_URL_PREFIX + picUrl);
								}
								//--����ҽԺid
								departListForPicDown.setHospitalId(1);

							}
							// --������ӣ���������Ϣ�������ݿ���
							iDepartmentListDao.batchInsert(listOfDeList);

							if (flagOfTime) {
								// --��ձ�(department_doctor)����
								//--iDepartmentDoctorDao.executeUpdateBySql("TRUNCATE TABLE department_doctor");
								iDepartmentDoctorDao.executeUpdateBySql("DELETE FROM department_doctor");
								flagOfTime = false;
							}

							// --3.��ȡ������ҽ���б�
							for (DepartmentList departList : listOfDeList) {
								// --���ݿ���id��ȡ��ǰ�����µ�����ҽ��
								DepartmentDoctorModel dDoctorModel = IntersUtils
										.getAllDoctorListOfDepart(String
												.valueOf(departList
														.getDepartId()));
								if ((dDoctorModel != null)
										&& (dDoctorModel.getStatusCode() == 200)) {
									// --��ȡ���ݳɹ�������Ӧ�Ĵ���
									List<DepartmentDoctor> listOfDeDoctorList = dDoctorModel.getMsg();
									if ((listOfDeDoctorList != null)
											&& (listOfDeDoctorList.size() > 0)) {
										// --����ѭ������ͼƬ���ϴ���ftp��������
										for(DepartmentDoctor listDoctForPicDown : listOfDeDoctorList){
											picUrl = listDoctForPicDown.getDoctorPic();
											if((picUrl != null) && (picUrl.length() > 0)){
												listDoctForPicDown
												.setDoctorPic(picUrl.substring(picUrl
														.lastIndexOf("/") + 1));
										FileUtil.uploadRemoPicFileToFtpServer(listDoctForPicDown.getDoctorPic(),
												Conf.HOSTPITAL_PIC_URL_PREFIX + picUrl);
											}
											//--����ҽԺid
											listDoctForPicDown.setHospitalId(1);
										}
										
										// --������ӣ�������ҽ����Ϣ�������ݿ���
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
		//--��ѯ���е���������
		Integer departListSize = iDepartmentListDao.getAllSize();
		//--��ѯ��ָ����Χ�������
		List<DepartmentList> listOfDepaList = iDepartmentListDao.query((page - 1) * nums, nums);
		return new PageDataModel(departListSize, listOfDepaList);
	}


	@Override
	public PageDataModel findAllDepartmentTypes(int page, int nums) {
		//--��ѯ���е���������
		Integer departTypeSize = iDepartmentTypeDao.getAllSize();
		//--��ѯ��ָ����Χ�������
		List<DepartmentType> listOfDepaType = iDepartmentTypeDao.query((page - 1) * nums, nums);
		return new PageDataModel(departTypeSize, listOfDepaType);
	}


	@Override
	public PageDataModel findDepartmentListByTypeKeys(int page, int nums, String typeKeys) {
		//--��ѯ���е���������
		Integer departListSize = iDepartmentListDao.getAllSize(" where departSort=" + "'" + typeKeys + "'");
		//--��ѯ��ָ����Χ�������
		List<DepartmentList> listOfDepaList = iDepartmentListDao.query("from DepartmentList where departSort=:departSort order by id asc",
				"departSort", typeKeys, page, nums);
		return new PageDataModel(departListSize, listOfDepaList);
	}


	@Override
	public DepartmentList findDepartmentInfoByDepartId(int departId) {
		return iDepartmentListDao.findByPropertyPojo("departId", departId);
	}
	
	
	
	
	
	

}
