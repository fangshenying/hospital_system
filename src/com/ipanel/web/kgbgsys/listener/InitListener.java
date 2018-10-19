package com.ipanel.web.kgbgsys.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.ipanel.web.kgbgsys.utils.Conf;
import com.ipanel.webapp.framework.util.Log;

public class InitListener implements ServletContextListener {
	private final String TAG = "InitListener";
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Log.i(TAG, "-------初始化监听器（InitListener）-------");
		//--定时调用医院系统的接口存入本地数据库
		SchedulerFactory schedFact = new StdSchedulerFactory();
		Scheduler sched;
		try {
			sched = schedFact.getScheduler();
			sched.start();
			JobDetail jobDetail = new JobDetail("SyncHostpInfoJob", "SyncHostpInfoJob", SyncHostpInfoJob.class);
			jobDetail.getJobDataMap().put("type", "FULL");
			CronTrigger trigger = new CronTrigger("SyncHostpInfoJob", "SyncHostpInfoJob");
			trigger.setCronExpression(Conf.CRON_EXPRESSION_HOSPITAL);
			sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "InitListener occur exception：" + e);
		} 
		
	}
	
	
}
