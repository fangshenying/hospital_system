package com.ipanel.web.kgbgsys.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 常用基本增删改查操作
 *
 * @param <POJO>
 */
public interface BaseDAO<POJO> {
	
	//==============================查询 query=======================================
	
	/**
	 * 查询数据总量
	 * @return
	 */
	Integer getAllSize();
	
	/**
	 * 查询数据总量
	 * @param hqlBeginWhere (以空格开头，where子句以及后面的内容)
	 * @return
	 */
	Integer getAllSize(String hqlBeginFrom);
	
	/**
	 * 根据ID查找
	 *  load 延迟加载
	 * @param clazz
	 * @param id
	 * @return
	 */
	POJO loadByID(Serializable id);
	
	/**
	 * 根据ID查找
	 *  get 即时加载
	 * @param clazz
	 * @param id
	 * @return
	 */
	POJO getByID(Serializable id);
	
	/**
	 * 根据实体的属性查找
	 * @param propertyName 属性名称
	 * @param value 值
	 * @return
	 */
	List<POJO> findByProperty(String propertyName, Object value);
	
	/**
	 * 根据实体的属性查找
	 * @param propertyName 属性名称
	 * @param value 值
	 * @return 只返回一个
	 */
	POJO findByPropertyPojo(String propertyName, Object value);
	
	/**
	 * 根据自定义hql语句查询
	 * 		
	 * @param hql
	 * 		查询语句
	 * @return
	 */
	List<Object[]> queryByHQL(final String hql);
	
	/**
	 * 根据自定义hql语句查询
	 * @param hql
	 * 		查询语句,绑定参数
	 * @param conditions
	 * 		条件
	 * @return
	 */
	List<Object[]> queryByHQL(final String hql, final Map<String, Object> conditions);
	
	/**
	 * 支持任意查询，但不支持条件查询
	 * 		
	 * @param hql
	 *            HQL语句
	 * @return List
	 */
	List<POJO> query(final String hql);
	
	/**
	 * 条件查询,绑定参数时，必须使用名字，如s.id=:id，而不能用s.id=?
	 * 
	 * @param hql
	 *            hql语句
	 * @param conditions
	 *            条件集合
	 * @return List
	 */
	List<POJO> query(final String hql, final Map<String, Object> conditions);
	
	/**
	 * ======================================================================
	 * 查询所有,按照默认排序规则
	 * 		该方法实现基于List<POJO> query(String hql);
	 * @return
	 */
	List<POJO> queryAll();
	
	/**
	 * ======================================================================
	 * 只支持一个条件的查询
	 * 		该方法实现基于List<POJO> query(final String hql, final Map<String, Object> conditions);
	 * @param hql
	 *            hql语句
	 * @param field
	 *            条件
	 * @param value
	 *            值
	 * @return List
	 */
	List<POJO> query(String hql, String field, Object value);
	
	/**
	 * 无条件分页查询
	 * 		
	 * @param hql
	 * 			查询语句,不绑定参数
	 * @param firstResult
	 * 			起始查询记录索引
	 * @param pageNum
	 * 			每页显示数
	 * @return
	 */
	List<POJO> query(final String hql, final int startRecord, final int pagesize);
	
	/**
	 * 分页条件查询
	 * 
	 * @param hql
	 * 			绑定参数时，必须使用名字，如s.id=:id，而不能用s.id=?
	 * @param conditions
	 * 			查询条件
	 * @param curpage
	 *          当前页
	 * @param pagesize
	 *          每页显示数
	 * @return
	 */
	List<POJO> query(final String hql, final Map<String, Object> conditions,
			final int curpage, final int pagesize);
	
	/**
	 * 分页条件查询
	 * @param hql
	 * @param conditions
	 * @param startRecord
	 * @param pagesize
	 * @return
	 */
	List<Object[]> celeritySearchObj(final String hql, final Map<String, Object> conditions,
			final int startRecord, final int pagesize);
	
	/**
	 * 分页条件查询
	 * 
	 * @param hql
	 * 			绑定参数时，必须使用名字，如s.id=:id，而不能用s.id=?
	 * @param conditions
	 * 			查询条件
	 * @param startRecord
	 *          起始查询记录索引
	 * @param pagesize
	 *          每页显示数
	 * @return
	 */
	List<POJO> celeritySearch(final String hql, final Map<String, Object> conditions,
			final int startRecord, final int pagesize);
	
	/**
	 * ======================================================================
	 * 分页查询
	 * 		该方法实现基于List<POJO> query(final String hql, final int startRecord, final int pagesize);
	 * @param curpage
	 *            当前页
	 * @param pagesize
	 *            每页大小
	 * @return
	 */
	List<POJO> query(int curpage, int pagesize);
	
	/**
	 * ======================================================================
	 * 只支持一个条件的分页查询
	 * 		该方法实现基于List<POJO> query(final String hql, final Map<String, Object> conditions, final int curpage, final int pagesize);
	 * @param hql
	 *            hql语句
	 * @param field
	 *            条件
	 * @param value
	 *            值
	 * @return List
	 */
	List<POJO> query(String hql, String field, Object value, int curpage, int pagesize);
	
	//===========================计数 count======================================
	/**
	 * 无条件结果数
	 * @param hql
	 *            hql语句,例如 select count(*) from Table t;查询的是单列数字类型
	 */
	Integer queryNumber(final String hql);
	
	/**
	 * 条件查询结果数
	 * 		
	 * @param hql
	 * 		hql语句,绑定参数
	 * @param conditions
	 * 		条件map
	 * @return
	 */
	Integer queryNumber(final String hql, final Map<String, Object> conditions);
	
	
	Integer queryInt(final String hql);

	//==============================更新操作========================================
	/**
	 * 添加实体
	 * @param pojo
	 * 		实体
	 */
	boolean save(POJO pojo);
	
	/**
	 * 修改
	 * 		更新之后会同步session持久化
	 * @param pojo
	 * 		实体
	 */
	boolean update(POJO pojo);
	
	/**
	 * saveOrUpdate
	 * 		更新之后会同步session持久化
	 * @param pojo
	 * @return
	 */
	boolean saveOrUpdate(POJO pojo);
	
	/**
	 * merge
	 * 		更新之后依旧是托管状态,但是会将session持久化的数据完成更新
	 * 			在session未关闭之前更新操作建议采用此方法
	 * @param pojo
	 * @return
	 */
	boolean merge(POJO pojo);
	
	/**
	 * 删除
	 * 		通过主键
	 * @param id
	 * 		数据库表主键
	 */
	boolean delete(Serializable id);
	
	/**
	 * 删除
	 * 		通过实体
	 * @param object
	 * @return
	 */
	boolean deleteEntry(POJO pojo);
	
	/**
	 * 通过hql更新表操作
	 * 	
	 * @param hql
	 * 			hql语句
	 * @return
	 */
	int executeUpdateByHQL(final String hql);
	
	/**
	 * 通过hql更新表操作
	 * 	
	 * @param hql
	 * 			hql语句
	 * @return
	 */
	int executeUpdateByHQL(final String hql, final Map<String, Object> conditions);
    
	
	//==============================批量操作========================================
	
	/**
	 * 批量添加
	 * @param hqls
	 * 	
	 * @return
	 * @throws Exception
	 */
	void batchInsert(Collection<POJO> objs) throws Exception;
	
	/**
	 * 批量删除 修改 HQL
	 * @param hqls
	 * 	
	 * @return
	 * @throws Exception
	 */
	 boolean batchDelOrUpdate(final List<String> hqls) throws Exception;
	
	//==============================SQL操作========================================
	/**
	 * SQL查询
	 * @param sql
	 * @return
	 */
	List<POJO> queryForSQL(final String sql);
	
	List<Object[]> queryObjectForSQL(final String sql,Map<String,Object> conditions);
	
	/**
	  * 批量删除 修改 SQL
	  */
	 boolean batchDelOrUpdateBySql(final List<String> hqls) throws Exception;
	 
	 /**
	  * SQL更新操作 
	  * 	增删改
	  * @param sql
	  */
	 int executeUpdateBySql(String sql);
	 
}
