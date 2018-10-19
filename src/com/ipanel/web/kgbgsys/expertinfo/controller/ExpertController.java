package com.ipanel.web.kgbgsys.expertinfo.controller;

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
import com.ipanel.web.kgbgsys.expertinfo.service.IExpertService;
import com.ipanel.webapp.framework.util.Log;
import com.ipanel.web.kgbgsys.pojo.DepartmentDoctor;
import com.ipanel.web.kgbgsys.pojo.DepartmentList;
import com.ipanel.web.kgbgsys.utils.FtpUtil;

@Controller
@RequestMapping("/expertController")
public class ExpertController {
	private final String TAG = "ExpertController";
	
	@Autowired
	private IExpertService iExpertService;
	
	
	@RequestMapping("/findAllExpertByDepartId")
	@ResponseBody
	public ResponseDataModel findAllExpertByDepartId(HttpServletRequest request, Integer page, Integer nums, String departId){
		Log.i(TAG, "-------ͨ������id��ѯ����ҽ��(/findAllExpertByDepartId)-------");	
		try {
			PageDataModel pdm = iExpertService.findAllExpertByDepartId(page, nums, departId);
			if(pdm != null){
				//--����ǰ׺�����
				@SuppressWarnings("unchecked")
				List<DepartmentDoctor> listOfDepaDoctLists = (List<DepartmentDoctor>)pdm.getRows();
				//--��ȡͼƬ����ǰ׺
				String picUrlPrefix = FtpUtil.getUrlPrex();
				String picUrl = null;
				if((listOfDepaDoctLists != null) && (listOfDepaDoctLists.size() > 0)){
					for(DepartmentDoctor deptDoctorList : listOfDepaDoctLists){
						picUrl = deptDoctorList.getDoctorPic();
						//--��������Ϊ����ͼƬ����ǰ׺
						if((picUrl != null) && (picUrl.length() > 0)){
							deptDoctorList.setDoctorPic(picUrlPrefix + picUrl);
						}
						
					}
					pdm.setRows(listOfDepaDoctLists);
				}
			}
			ResponseDataModel rdm = new ResponseDataModel(ResponseDataModel.SUCCESS, pdm);
			return rdm;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "ͨ������id��ѯ����ҽ��(/findAllExpertByDepartId)�����쳣:"+e);
			//ʧ�ܷ�������
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"ͨ������id��ѯ����ҽ�������쳣");
		}
	}
	
	@RequestMapping("/findExpertByDoctorId")
	@ResponseBody
	public ResponseDataModel findExpertByDoctorId(HttpServletRequest request, Integer doctorId){
		Log.i(TAG, "-------ͨ��ҽ��id��ѯҽ��������Ϣ(/findExpertByDoctorId)-------");	
		try {
			DepartmentDoctor dparDoctor = iExpertService.findExpertByDoctorId(doctorId);
			if(dparDoctor != null){
				//--��ȡͼƬǰ׺
				String picUrlPrefix = FtpUtil.getUrlPrex();
				String picUrl = dparDoctor.getDoctorPic();
				if((picUrl != null) && (picUrl.length() > 0)){
					dparDoctor.setDoctorPic(picUrlPrefix + picUrl);
				}	
			}		
			ResponseDataModel rdm = new ResponseDataModel(ResponseDataModel.SUCCESS, dparDoctor);
			return rdm;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "ͨ��ҽ��id��ѯҽ��������Ϣ(/findExpertByDoctorId)�����쳣:"+e);
			//ʧ�ܷ�������
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"ͨ��ҽ��id��ѯҽ��������Ϣ�����쳣");
		}
	}

	
	
}
