package com.ipanel.web.kgbgsys.departmentsinfo.daoimpl;


import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ipanel.web.kgbgsys.daoimpl.BaseHibernateDAOImpl;
import com.ipanel.web.kgbgsys.departmentsinfo.dao.IDepartmentListDao;
import com.ipanel.web.kgbgsys.pojo.DepartmentList;

@Repository
public class DepartmentListDaoImpl extends BaseHibernateDAOImpl<DepartmentList> implements IDepartmentListDao {

	public DepartmentListDaoImpl() {
		super(DepartmentList.class);
		// TODO Auto-generated constructor stub
	}
	
	@Resource
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	


}
