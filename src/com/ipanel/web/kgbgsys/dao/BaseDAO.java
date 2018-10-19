package com.ipanel.web.kgbgsys.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ���û�����ɾ�Ĳ����
 *
 * @param <POJO>
 */
public interface BaseDAO<POJO> {
	
	//==============================��ѯ query=======================================
	
	/**
	 * ��ѯ��������
	 * @return
	 */
	Integer getAllSize();
	
	/**
	 * ��ѯ��������
	 * @param hqlBeginWhere (�Կո�ͷ��where�Ӿ��Լ����������)
	 * @return
	 */
	Integer getAllSize(String hqlBeginFrom);
	
	/**
	 * ����ID����
	 *  load �ӳټ���
	 * @param clazz
	 * @param id
	 * @return
	 */
	POJO loadByID(Serializable id);
	
	/**
	 * ����ID����
	 *  get ��ʱ����
	 * @param clazz
	 * @param id
	 * @return
	 */
	POJO getByID(Serializable id);
	
	/**
	 * ����ʵ������Բ���
	 * @param propertyName ��������
	 * @param value ֵ
	 * @return
	 */
	List<POJO> findByProperty(String propertyName, Object value);
	
	/**
	 * ����ʵ������Բ���
	 * @param propertyName ��������
	 * @param value ֵ
	 * @return ֻ����һ��
	 */
	POJO findByPropertyPojo(String propertyName, Object value);
	
	/**
	 * �����Զ���hql����ѯ
	 * 		
	 * @param hql
	 * 		��ѯ���
	 * @return
	 */
	List<Object[]> queryByHQL(final String hql);
	
	/**
	 * �����Զ���hql����ѯ
	 * @param hql
	 * 		��ѯ���,�󶨲���
	 * @param conditions
	 * 		����
	 * @return
	 */
	List<Object[]> queryByHQL(final String hql, final Map<String, Object> conditions);
	
	/**
	 * ֧�������ѯ������֧��������ѯ
	 * 		
	 * @param hql
	 *            HQL���
	 * @return List
	 */
	List<POJO> query(final String hql);
	
	/**
	 * ������ѯ,�󶨲���ʱ������ʹ�����֣���s.id=:id����������s.id=?
	 * 
	 * @param hql
	 *            hql���
	 * @param conditions
	 *            ��������
	 * @return List
	 */
	List<POJO> query(final String hql, final Map<String, Object> conditions);
	
	/**
	 * ======================================================================
	 * ��ѯ����,����Ĭ���������
	 * 		�÷���ʵ�ֻ���List<POJO> query(String hql);
	 * @return
	 */
	List<POJO> queryAll();
	
	/**
	 * ======================================================================
	 * ֻ֧��һ�������Ĳ�ѯ
	 * 		�÷���ʵ�ֻ���List<POJO> query(final String hql, final Map<String, Object> conditions);
	 * @param hql
	 *            hql���
	 * @param field
	 *            ����
	 * @param value
	 *            ֵ
	 * @return List
	 */
	List<POJO> query(String hql, String field, Object value);
	
	/**
	 * ��������ҳ��ѯ
	 * 		
	 * @param hql
	 * 			��ѯ���,���󶨲���
	 * @param firstResult
	 * 			��ʼ��ѯ��¼����
	 * @param pageNum
	 * 			ÿҳ��ʾ��
	 * @return
	 */
	List<POJO> query(final String hql, final int startRecord, final int pagesize);
	
	/**
	 * ��ҳ������ѯ
	 * 
	 * @param hql
	 * 			�󶨲���ʱ������ʹ�����֣���s.id=:id����������s.id=?
	 * @param conditions
	 * 			��ѯ����
	 * @param curpage
	 *          ��ǰҳ
	 * @param pagesize
	 *          ÿҳ��ʾ��
	 * @return
	 */
	List<POJO> query(final String hql, final Map<String, Object> conditions,
			final int curpage, final int pagesize);
	
	/**
	 * ��ҳ������ѯ
	 * @param hql
	 * @param conditions
	 * @param startRecord
	 * @param pagesize
	 * @return
	 */
	List<Object[]> celeritySearchObj(final String hql, final Map<String, Object> conditions,
			final int startRecord, final int pagesize);
	
	/**
	 * ��ҳ������ѯ
	 * 
	 * @param hql
	 * 			�󶨲���ʱ������ʹ�����֣���s.id=:id����������s.id=?
	 * @param conditions
	 * 			��ѯ����
	 * @param startRecord
	 *          ��ʼ��ѯ��¼����
	 * @param pagesize
	 *          ÿҳ��ʾ��
	 * @return
	 */
	List<POJO> celeritySearch(final String hql, final Map<String, Object> conditions,
			final int startRecord, final int pagesize);
	
	/**
	 * ======================================================================
	 * ��ҳ��ѯ
	 * 		�÷���ʵ�ֻ���List<POJO> query(final String hql, final int startRecord, final int pagesize);
	 * @param curpage
	 *            ��ǰҳ
	 * @param pagesize
	 *            ÿҳ��С
	 * @return
	 */
	List<POJO> query(int curpage, int pagesize);
	
	/**
	 * ======================================================================
	 * ֻ֧��һ�������ķ�ҳ��ѯ
	 * 		�÷���ʵ�ֻ���List<POJO> query(final String hql, final Map<String, Object> conditions, final int curpage, final int pagesize);
	 * @param hql
	 *            hql���
	 * @param field
	 *            ����
	 * @param value
	 *            ֵ
	 * @return List
	 */
	List<POJO> query(String hql, String field, Object value, int curpage, int pagesize);
	
	//===========================���� count======================================
	/**
	 * �����������
	 * @param hql
	 *            hql���,���� select count(*) from Table t;��ѯ���ǵ�����������
	 */
	Integer queryNumber(final String hql);
	
	/**
	 * ������ѯ�����
	 * 		
	 * @param hql
	 * 		hql���,�󶨲���
	 * @param conditions
	 * 		����map
	 * @return
	 */
	Integer queryNumber(final String hql, final Map<String, Object> conditions);
	
	
	Integer queryInt(final String hql);

	//==============================���²���========================================
	/**
	 * ���ʵ��
	 * @param pojo
	 * 		ʵ��
	 */
	boolean save(POJO pojo);
	
	/**
	 * �޸�
	 * 		����֮���ͬ��session�־û�
	 * @param pojo
	 * 		ʵ��
	 */
	boolean update(POJO pojo);
	
	/**
	 * saveOrUpdate
	 * 		����֮���ͬ��session�־û�
	 * @param pojo
	 * @return
	 */
	boolean saveOrUpdate(POJO pojo);
	
	/**
	 * merge
	 * 		����֮���������й�״̬,���ǻὫsession�־û���������ɸ���
	 * 			��sessionδ�ر�֮ǰ���²���������ô˷���
	 * @param pojo
	 * @return
	 */
	boolean merge(POJO pojo);
	
	/**
	 * ɾ��
	 * 		ͨ������
	 * @param id
	 * 		���ݿ������
	 */
	boolean delete(Serializable id);
	
	/**
	 * ɾ��
	 * 		ͨ��ʵ��
	 * @param object
	 * @return
	 */
	boolean deleteEntry(POJO pojo);
	
	/**
	 * ͨ��hql���±����
	 * 	
	 * @param hql
	 * 			hql���
	 * @return
	 */
	int executeUpdateByHQL(final String hql);
	
	/**
	 * ͨ��hql���±����
	 * 	
	 * @param hql
	 * 			hql���
	 * @return
	 */
	int executeUpdateByHQL(final String hql, final Map<String, Object> conditions);
    
	
	//==============================��������========================================
	
	/**
	 * �������
	 * @param hqls
	 * 	
	 * @return
	 * @throws Exception
	 */
	void batchInsert(Collection<POJO> objs) throws Exception;
	
	/**
	 * ����ɾ�� �޸� HQL
	 * @param hqls
	 * 	
	 * @return
	 * @throws Exception
	 */
	 boolean batchDelOrUpdate(final List<String> hqls) throws Exception;
	
	//==============================SQL����========================================
	/**
	 * SQL��ѯ
	 * @param sql
	 * @return
	 */
	List<POJO> queryForSQL(final String sql);
	
	List<Object[]> queryObjectForSQL(final String sql,Map<String,Object> conditions);
	
	/**
	  * ����ɾ�� �޸� SQL
	  */
	 boolean batchDelOrUpdateBySql(final List<String> hqls) throws Exception;
	 
	 /**
	  * SQL���²��� 
	  * 	��ɾ��
	  * @param sql
	  */
	 int executeUpdateBySql(String sql);
	 
}
