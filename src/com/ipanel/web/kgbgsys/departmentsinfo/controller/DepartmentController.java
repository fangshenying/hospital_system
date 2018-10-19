package com.ipanel.web.kgbgsys.departmentsinfo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ipanel.web.kgbgsys.common.BaseDataModel.ResponseDataModel;
import com.ipanel.web.kgbgsys.common.PageDataModel;
import com.ipanel.web.kgbgsys.departmentsinfo.service.IDepartmentsService;
import com.ipanel.webapp.framework.util.Log;
import com.ipanel.web.kgbgsys.pojo.DepartmentList;
import com.ipanel.web.kgbgsys.utils.FtpUtil;
import com.ipanel.web.kgbgsys.utils.StringUtils;

@Controller
@RequestMapping("/departmentController")
public class DepartmentController {
	private final String TAG = "DepartmentController";
	
	@Autowired
	private IDepartmentsService iDepartmentsService;

	
	@RequestMapping("/test")
	@ResponseBody
	public ResponseDataModel test(HttpServletRequest request){
		Log.i(TAG, "-------����(/test)-------");	
		try {
			//--iDepartmentsService.syncHostpInfo();
			ResponseDataModel rdm = new ResponseDataModel(ResponseDataModel.SUCCESS,"�������ݳɹ���");
			return rdm;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "����(/test):"+e);
			//ʧ�ܷ�������
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"��������ʧ��!");
		}
	}
	
	@RequestMapping("/findAllDepartment")
	@ResponseBody
	public ResponseDataModel findAllDepartment(HttpServletRequest request, Integer page, Integer nums){
		Log.i(TAG, "-------��ѯȫ������(/findAllDepartment)-------");	
		try {
			PageDataModel pdm = iDepartmentsService.findAllDepartment(page, nums);
			if(pdm != null){
				//--����ǰ׺�����
				@SuppressWarnings("unchecked")
				List<DepartmentList> listofDeLists = (List<DepartmentList>)pdm.getRows();
				//--��ȡͼƬ����ǰ׺
				String picUrlPrefix = FtpUtil.getUrlPrex();
				String picUrl = null;
				if((listofDeLists != null) && (listofDeLists.size() > 0)){
					for(DepartmentList deptList : listofDeLists){
						picUrl = deptList.getDepartPic();
						//--��������Ϊ����ͼƬ����ǰ׺
						if((picUrl != null) && (picUrl.length() > 0)){
							deptList.setDepartPic(picUrlPrefix + picUrl);
						}
						
					}
					pdm.setRows(listofDeLists);
				}
			}
			ResponseDataModel rdm = new ResponseDataModel(ResponseDataModel.SUCCESS, pdm);
			return rdm;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "��ѯȫ������(/findAllDepartment)�����쳣:"+e);
			//ʧ�ܷ�������
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"��ѯȫ�����ҳ����쳣");
		}
	}
	
	@RequestMapping("/findAllDepartmentTypes")
	@ResponseBody
	public ResponseDataModel findAllDepartmentTypes(HttpServletRequest request, Integer page, Integer nums){
		Log.i(TAG, "-------��ҳ��ѯ���п������(/findAllDepartmentTypes)-------");	
		try {
			PageDataModel pdm = iDepartmentsService.findAllDepartmentTypes(page, nums);
			ResponseDataModel rdm = new ResponseDataModel(ResponseDataModel.SUCCESS, pdm);
			return rdm;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "��ҳ��ѯ���п������(/findAllDepartmentTypes)�����쳣:"+e);
			//ʧ�ܷ�������
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"��ҳ��ѯ���п����������쳣");
		}
	}
	
	@RequestMapping("/findDepartmentListByTypeKeys")
	@ResponseBody
	public ResponseDataModel findDepartmentListByTypeKeys(HttpServletRequest request, Integer page, Integer nums, String typeKeys){
		Log.i(TAG, "-------��ҳͨ����������ʶ��ѯ���п���(/findDepartmentListByTypeKeys)-------");	
		try {
			PageDataModel pdm = iDepartmentsService.findDepartmentListByTypeKeys(page, nums, typeKeys);
			if(pdm != null){
				//--����ǰ׺�����
				@SuppressWarnings("unchecked")
				List<DepartmentList> listofDeLists = (List<DepartmentList>)pdm.getRows();
				//--��ȡͼƬ����ǰ׺
				String picUrlPrefix = FtpUtil.getUrlPrex();
				String picUrl = null;
				if((listofDeLists != null) && (listofDeLists.size() > 0)){
					for(DepartmentList deptList : listofDeLists){
						picUrl = deptList.getDepartPic();
						//--��������Ϊ����ͼƬ����ǰ׺
						if((picUrl != null) && (picUrl.length() > 0)){
							deptList.setDepartPic(picUrlPrefix + picUrl);
						}
						
					}
					pdm.setRows(listofDeLists);
				}
			}
			ResponseDataModel rdm = new ResponseDataModel(ResponseDataModel.SUCCESS, pdm);
			return rdm;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "��ҳͨ����������ʶ��ѯ���п���(/findDepartmentListByTypeKeys)�����쳣:"+e);
			//ʧ�ܷ�������
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"��ҳͨ����������ʶ��ѯ���п��ҳ����쳣");
		}
	}
	
	@RequestMapping("/findDepartmentInfoByDepartId")
	@ResponseBody
	public ResponseDataModel findDepartmentInfoByDepartId(HttpServletRequest request, Integer departId){
		Log.i(TAG, "-------ͨ������id��ѯ��������(/findDepartmentInfoByDepartId)-------");	
		try {
			DepartmentList pmList = iDepartmentsService.findDepartmentInfoByDepartId(departId);
			if(pmList != null){
				//--��ȡͼƬǰ׺
				String picUrlPrefix = FtpUtil.getUrlPrex();
				String picUrl = pmList.getDepartPic();
				if((picUrl != null) && (picUrl.length() > 0)){
					pmList.setDepartPic(picUrlPrefix + picUrl);
				}
			}
			
			ResponseDataModel rdm = new ResponseDataModel(ResponseDataModel.SUCCESS, pmList);
			return rdm;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "ͨ������id��ѯ��������(/findDepartmentInfoByDepartId)�����쳣:"+e);
			//ʧ�ܷ�������
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"ͨ������id��ѯ������������쳣");
		}
	}

	
	
}
