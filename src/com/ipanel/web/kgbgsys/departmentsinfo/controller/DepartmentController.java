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
		Log.i(TAG, "-------测试(/test)-------");	
		try {
			//--iDepartmentsService.syncHostpInfo();
			ResponseDataModel rdm = new ResponseDataModel(ResponseDataModel.SUCCESS,"测试数据成功！");
			return rdm;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "测试(/test):"+e);
			//失败返回数据
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"测试数据失败!");
		}
	}
	
	@RequestMapping("/findAllDepartment")
	@ResponseBody
	public ResponseDataModel findAllDepartment(HttpServletRequest request, Integer page, Integer nums){
		Log.i(TAG, "-------查询全部科室(/findAllDepartment)-------");	
		try {
			PageDataModel pdm = iDepartmentsService.findAllDepartment(page, nums);
			if(pdm != null){
				//--进行前缀的添加
				@SuppressWarnings("unchecked")
				List<DepartmentList> listofDeLists = (List<DepartmentList>)pdm.getRows();
				//--获取图片访问前缀
				String picUrlPrefix = FtpUtil.getUrlPrex();
				String picUrl = null;
				if((listofDeLists != null) && (listofDeLists.size() > 0)){
					for(DepartmentList deptList : listofDeLists){
						picUrl = deptList.getDepartPic();
						//--挨个遍历为科室图片加上前缀
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
			Log.e(TAG, "查询全部科室(/findAllDepartment)出现异常:"+e);
			//失败返回数据
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"查询全部科室出现异常");
		}
	}
	
	@RequestMapping("/findAllDepartmentTypes")
	@ResponseBody
	public ResponseDataModel findAllDepartmentTypes(HttpServletRequest request, Integer page, Integer nums){
		Log.i(TAG, "-------分页查询所有科室类别(/findAllDepartmentTypes)-------");	
		try {
			PageDataModel pdm = iDepartmentsService.findAllDepartmentTypes(page, nums);
			ResponseDataModel rdm = new ResponseDataModel(ResponseDataModel.SUCCESS, pdm);
			return rdm;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "分页查询所有科室类别(/findAllDepartmentTypes)出现异常:"+e);
			//失败返回数据
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"分页查询所有科室类别出现异常");
		}
	}
	
	@RequestMapping("/findDepartmentListByTypeKeys")
	@ResponseBody
	public ResponseDataModel findDepartmentListByTypeKeys(HttpServletRequest request, Integer page, Integer nums, String typeKeys){
		Log.i(TAG, "-------分页通过科室类别标识查询所有科室(/findDepartmentListByTypeKeys)-------");	
		try {
			PageDataModel pdm = iDepartmentsService.findDepartmentListByTypeKeys(page, nums, typeKeys);
			if(pdm != null){
				//--进行前缀的添加
				@SuppressWarnings("unchecked")
				List<DepartmentList> listofDeLists = (List<DepartmentList>)pdm.getRows();
				//--获取图片访问前缀
				String picUrlPrefix = FtpUtil.getUrlPrex();
				String picUrl = null;
				if((listofDeLists != null) && (listofDeLists.size() > 0)){
					for(DepartmentList deptList : listofDeLists){
						picUrl = deptList.getDepartPic();
						//--挨个遍历为科室图片加上前缀
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
			Log.e(TAG, "分页通过科室类别标识查询所有科室(/findDepartmentListByTypeKeys)出现异常:"+e);
			//失败返回数据
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"分页通过科室类别标识查询所有科室出现异常");
		}
	}
	
	@RequestMapping("/findDepartmentInfoByDepartId")
	@ResponseBody
	public ResponseDataModel findDepartmentInfoByDepartId(HttpServletRequest request, Integer departId){
		Log.i(TAG, "-------通过科室id查询科室详情(/findDepartmentInfoByDepartId)-------");	
		try {
			DepartmentList pmList = iDepartmentsService.findDepartmentInfoByDepartId(departId);
			if(pmList != null){
				//--获取图片前缀
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
			Log.e(TAG, "通过科室id查询科室详情(/findDepartmentInfoByDepartId)出现异常:"+e);
			//失败返回数据
			return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,"通过科室id查询科室详情出现异常");
		}
	}

	
	
}
