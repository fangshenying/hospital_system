package com.ipanel.web.kgbgsys.expertinfo.daoimpl;


import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ipanel.web.kgbgsys.daoimpl.BaseHibernateDAOImpl;
import com.ipanel.web.kgbgsys.expertinfo.dao.IDepartmentDoctorDao;
import com.ipanel.web.kgbgsys.pojo.DepartmentDoctor;

@Repository
public class DepartmentDoctorDaoImpl extends BaseHibernateDAOImpl<DepartmentDoctor> implements IDepartmentDoctorDao {

	public DepartmentDoctorDaoImpl() {
		super(DepartmentDoctor.class);
		// TODO Auto-generated constructor stub
	}
	
	@Resource
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	


}
