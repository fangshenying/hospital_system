package com.ipanel.web.kgbgsys.listener;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ipanel.web.kgbgsys.departmentsinfo.service.IDepartmentsService;
import com.ipanel.webapp.framework.core.SpringContextAware;
import com.ipanel.webapp.framework.util.Log;

public class SyncHostpInfoJob implements Job {
	private final String TAG = "SyncHostpInfoJob";
	private IDepartmentsService iDepartmentsService = SpringContextAware.getBean("departmentsServiceImpl");
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Log.i(TAG, "-------ִ�ж�ʱ����SyncHostpInfoJob start��-------");
		try {
			//--ͬ��ҽԺ�ӿ����ݵ��������ݿ�
			iDepartmentsService.syncHostpInfo();
			Log.i(TAG, "-------ִ�ж�ʱ����SyncHostpInfoJob over��-------");
		} catch (Exception e) {
			Log.e(TAG, "SyncHostpInfoJob error:"+e);
			e.printStackTrace();
		}
		
	}

}
