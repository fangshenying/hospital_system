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
		Log.i(TAG, "-------通过科室id查询科室医生(/findAllExpertByDepartId)-------");	
		try {
			PageDataModel pdm = iExpertService.findAllExpertByDepartId(page, nums, departId);
			if(pdm != null){
				//--进行前缀的添加
				@SuppressWarnings("unchecked")
				List<DepartmentDoctor> listOfDepaDoctLists = (List<DepartmentDoctor>)pdm.getRows();
				//--获取图片访问前缀
				String picUrlPrefix = FtpUtil.getUrlPrex();
				String picUrl = null;
				if((listOfDepaDoctLists != null) && (listOfDepaDoctLists.size() > 0)){
					for(DepartmentDoctor deptDoctorList : listOfDepaDoctLists){
						picUrl = deptDoctorList.getDoctorPic();
						//--挨个遍历为科室图片加上前缀
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
			Log.e(TAG, "通过科室id查询科室医生(/findAllExpertByDepartId)出现异常:"+e);
			//失败返回数据
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"通过科室id查询科室医生出现异常");
		}
	}
	
	@RequestMapping("/findExpertByDoctorId")
	@ResponseBody
	public ResponseDataModel findExpertByDoctorId(HttpServletRequest request, Integer doctorId){
		Log.i(TAG, "-------通过医生id查询医生详情信息(/findExpertByDoctorId)-------");	
		try {
			DepartmentDoctor dparDoctor = iExpertService.findExpertByDoctorId(doctorId);
			if(dparDoctor != null){
				//--获取图片前缀
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
			Log.e(TAG, "通过医生id查询医生详情信息(/findExpertByDoctorId)出现异常:"+e);
			//失败返回数据
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"通过医生id查询医生详情信息出现异常");
		}
	}

	
	
}
