package com.ipanel.web.kgbgsys.departmentsinfo.daoimpl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ipanel.web.kgbgsys.daoimpl.BaseHibernateDAOImpl;
import com.ipanel.web.kgbgsys.departmentsinfo.dao.IDepartmentTypeDao;
import com.ipanel.web.kgbgsys.pojo.DepartmentType;

@Repository
public class DepartmentTypeDaoImpl extends BaseHibernateDAOImpl<DepartmentType> implements IDepartmentTypeDao {

	public DepartmentTypeDaoImpl() {
		super(DepartmentType.class);
		// TODO Auto-generated constructor stub
	}
	
	@Resource
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	


}
