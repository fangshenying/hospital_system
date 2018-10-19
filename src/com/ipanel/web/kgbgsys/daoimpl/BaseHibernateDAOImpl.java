package com.ipanel.web.kgbgsys.daoimpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ipanel.web.kgbgsys.dao.BaseDAO;

/**
 * 基本DAO的实现 该实现基于Spring或者其他支持事物管理的框架自动开启事物
 * 
 * 
 * @param <POJO>
 */
public abstract class BaseHibernateDAOImpl<POJO> implements BaseDAO<POJO> {

	private Class<POJO> pojo;
	
	@Resource
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public BaseHibernateDAOImpl(Class<POJO> pojo) {
		this.pojo = pojo;
	}

	public POJO loadByID(Serializable id) {
		return (POJO) this.getSession().load(pojo, id);
	}

	public POJO getByID(Serializable id) {
		return (POJO) this.getSession().get(pojo, id);
	}

	public Integer getAllSize() {
		Session session = this.getSession();
		//--String hql = "select count(t.id) from " + pojo.getSimpleName() + " as t";
		String hql = "select count(id) from " + pojo.getSimpleName();
		Query query = session.createQuery(hql);
		Long coutNum = (Long)query.uniqueResult();
		if(coutNum == null){
			return 0;
		}
		return coutNum.intValue();
	}
	
	public Integer getAllSize(String hqlBeginWhere) {
		Session session = this.getSession();
		String hql = "select count(id) from " + pojo.getSimpleName() + hqlBeginWhere;
		Query query = session.createQuery(hql);
		Long coutNum = (Long)query.uniqueResult();
		if(coutNum == null){
			return 0;
		}
		return coutNum.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<POJO> findByProperty(String propertyName, Object value) {
		Session session = this.getSession();
		List<POJO> pojoList = null;
		Query query = null;
		String queryString = "from " + pojo.getSimpleName()
				+ " as model where model." + propertyName + " = ?";
		query = session.createQuery(queryString);
		
		//添加hql语句参数，将"?"号替换为具体参数
		query.setParameter(0,value);
		
		pojoList= query.list();
		return pojoList;
	}

	@SuppressWarnings("unchecked")
	public POJO findByPropertyPojo(String propertyName, Object value) {
		Session session = this.getSession();
		List<POJO> temp = null;
		Query query = null;
		String queryString = "from " + pojo.getSimpleName()
				+ " as model where model." + propertyName + " =:"
				+ propertyName;
		query = session.createQuery(queryString);
		
		query.setParameter(propertyName, value);
		query.setFirstResult(0);
		query.setMaxResults(1);
		
		temp= query.list();
		if (null == temp || temp.size() <= 0) {
			return null;
		}

		return temp.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> queryByHQL(final String hql) {
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> queryByHQL(final String hql,
			final Map<String, Object> conditions) {
		Query query = this.getSession().createQuery(hql);
		if (conditions != null) {
			Set<Map.Entry<String, Object>> entrys = conditions.entrySet();
			for (Map.Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<POJO> query(final String hql) {
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<POJO> query(final String hql,
			final Map<String, Object> conditions) {
		Query query = this.getSession().createQuery(hql);
		if (conditions != null) {
			Set<Map.Entry<String, Object>> entrys = conditions.entrySet();
			for (Map.Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.list();
	}

	// ============================start
	// query===================================
	public List<POJO> queryAll() {
		String hql = "from " + pojo.getSimpleName() + " t";
		return this.query(hql);
	}

	public List<POJO> query(String hql, String field, Object value) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(field, value);
		return this.query(hql, param);
	}

	// =============================end
	// query=====================================

	@SuppressWarnings("unchecked")
	public List<POJO> query(final String hql, final int startRecord,
			final int pagesize) {
		Query query = this.getSession().createQuery(hql);
		query.setFirstResult(startRecord);
		query.setMaxResults(pagesize);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<POJO> query(final String hql,
			final Map<String, Object> conditions, final int curpage,
			final int pagesize) {
		Query query = this.getSession().createQuery(hql);
		if (conditions != null) {
			Set<Map.Entry<String, Object>> entrys = conditions.entrySet();
			for (Map.Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.setFirstResult((curpage - 1) * pagesize);
		query.setMaxResults(pagesize);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> celeritySearchObj(final String hql,
			final Map<String, Object> conditions, final int startRecord,
			final int pagesize) {
		Query query =this.getSession().createQuery(hql);
		System.out.println(hql);
		if (conditions != null) {
			Set<Map.Entry<String, Object>> entrys = conditions.entrySet();
			for (Map.Entry<String, Object> entry : entrys) {
				System.out.println("--"+entry.getKey());
				System.out.println("--"+entry.getValue());
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.setFirstResult(startRecord);
		query.setMaxResults(pagesize);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<POJO> celeritySearch(final String hql,
			final Map<String, Object> conditions, final int startRecord,
			final int pagesize) {
		Query query = this.getSession().createQuery(hql);
		if (conditions != null) {
			Set<Map.Entry<String, Object>> entrys = conditions.entrySet();
			for (Map.Entry<String, Object> entry : entrys) {
				
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.setFirstResult(startRecord);
		query.setMaxResults(pagesize);
		return query.list();
	}

	// ============================start query
	// page===================================
	public List<POJO> query(int curpage, int pagesize) {
		String hql = "from " + pojo.getSimpleName() + " order by id asc";
		return this.query(hql, curpage, pagesize);
	}

	public List<POJO> query(String hql, String field, Object value,
			int curpage, int pagesize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(field, value);
		return this.query(hql, param, curpage, pagesize);
	}
	
	public Integer queryInt(final String hql){
		Query query = this.getSession().createQuery(hql);
		if (query.iterate().hasNext()) {
			return (Integer) query.iterate().next();
		} else {
			return Integer.decode("0");
		}
	}

	// ============================end query
	// page===================================

	// ===========================计数 count======================================
	public Integer queryNumber(final String hql) {
		Query query = this.getSession().createQuery(hql);
		if (query.iterate().hasNext()) {
			return ((Long) query.iterate().next()).intValue();
		} else {
			return Integer.decode("0");
		}
	}

	public Integer queryNumber(final String hql,
			final Map<String, Object> conditions) {
		Query query = this.getSession().createQuery(hql);
		if (conditions != null) {
			Set<Map.Entry<String, Object>> entrys = conditions.entrySet();
			for (Map.Entry<String, Object> entry : entrys) {
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}

		if (query.iterate().hasNext()) {

			Object obj = query.iterate().next();
			if (null == obj) {
				return Integer.decode("0");
			} else {
				return Integer.valueOf(query.iterate().next().toString())
						.intValue();
			}
		} else {
			return Integer.decode("0");
		}
	}

	// ==============================更新操作========================================
	public boolean save(POJO pojo) {
		this.getSession().save(pojo);
		return true;
	}

	public boolean update(POJO pojo) {
		this.getSession().update(pojo);
		return true;
	}

	public boolean saveOrUpdate(POJO pojo) {
		this.getSession().saveOrUpdate(pojo);
		return true;
	}

	public boolean merge(POJO pojo) {
		this.getSession().merge(pojo);
		return true;
	}

	public boolean delete(Serializable id) {
		Object obj = this.getSession().load(pojo, id);
		this.getSession().delete(obj);
		return true;
	}

	public boolean deleteEntry(POJO pojo) {
		this.getSession().delete(pojo);
		return true;
	}

	public int executeUpdateByHQL(final String hql) {
		Query query = this.getSession().createQuery(hql);
		return query.executeUpdate();
	}

	public int executeUpdateByHQL(final String hql,
			final Map<String, Object> conditions) {
		Query query = this.getSession().createQuery(hql);
		if (conditions != null) {
			Set<Map.Entry<String, Object>> entrys = conditions.entrySet();
			for (Map.Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.executeUpdate();
	}

	// ==============================批量操作========================================
	public void batchInsert(Collection<POJO> objs) throws Exception {
		Session session = this.getSession();
		if (objs.size() > 0) {
			int i = 0;
			for (Iterator<POJO> it = objs.iterator(); it.hasNext();) {
				session.save(it.next());
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
				i++;
			}
			session.flush();
			session.clear();
		}
	}

	/**
	 * 批量删除 修改 HQL
	 */
	public boolean batchDelOrUpdate(List<String> hqls) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (hqls != null && hqls.size() > 0) {
				for (String hql : hqls) {
					Query query = session.createQuery(hql);
					query.executeUpdate();
				}
			}
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	// ==============================SQL操作========================================
	@SuppressWarnings("unchecked")
	public List<POJO> queryForSQL(final String sql) {
		Query query = this.getSession().createSQLQuery(sql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> queryObjectForSQL(final String sql,
			final Map<String,Object> conditions){
		Query query =  this.getSession().createSQLQuery(sql);
		
		if (conditions != null) {
			Set<Map.Entry<String, Object>> entrys = conditions.entrySet();
			for (Map.Entry<String, Object> entry : entrys) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		
		return query.list();
	}

	public boolean batchDelOrUpdateBySql(List<String> sqls) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (sqls != null && sqls.size() > 0) {
				for (String sql : sqls) {
					SQLQuery query = session.createSQLQuery(sql);
					query.executeUpdate();
				}
			}
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	public int executeUpdateBySql(String sql) {
		Session session = this.getSession();
		Query query = session.createSQLQuery(sql);
		return query.executeUpdate();
	}

	/**
	 * 得到表中记录总数 参数tableName表格对应的pojo类名
	 * 
	 * @param hql
	 *            from Table t
	 * @param conditions
	 *            条件
	 * @return
	 */
	private Integer getNum(String hql, Map<String, Object> conditions) {
		if (hql == null || hql.length() < 1) {
			hql = "from " + pojo.getSimpleName() + " as t  ";
		}
		if (hql.trim().indexOf("order by") > -1) {
			hql = hql.substring(0, hql.indexOf("order by"));
		}
		if (hql.indexOf("from") > -1) {
			hql = hql.substring(hql.indexOf("from"));
		}
		hql = "select count(t.id) " + hql;
		return this.queryNumber(hql, conditions);
	}

	/**
	 * 得到表中记录总数 参数tableName表格对应的pojo类名
	 * 
	 * @param hql
	 *            from Table t
	 * @return
	 */
	private Integer getNum(String hql) {
		if (hql == null || hql.length() < 1) {
			hql = "from " + pojo.getSimpleName() + " as t  ";
		}
		if (hql.trim().indexOf("order by") > -1) {
			hql = hql.substring(0, hql.indexOf("order by"));
		}
		if (hql.indexOf("from") > -1) {
			hql = hql.substring(hql.indexOf("from"));
		}
		hql = "select count(t.id) " + hql;
		return this.queryNumber(hql);
	}
	
	
}
