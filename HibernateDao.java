import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HibernateDao {

	@Autowired
	private @Qualifier("sessionFactory")
	SessionFactory sessionFactory;

	@Autowired
	private @Qualifier("dataSource")
	DataSource dataSource;

	/**
	 * 获取数据库连接信息
	 */
	public String getDbConnectionInfo() throws HibernateException, SQLException {
		String dbinfo = "Database: ";
		String dbname = "";
		dbname += dataSource.getConnection().getMetaData()
				.getDatabaseProductName();
		dbinfo += "" + dbname + ", url: ";
		String url = dataSource.getConnection().getMetaData().getURL();
		dbinfo += "" + url + ", user: ";
		String user = dataSource.getConnection().getMetaData().getUserName();
		String[] userarray = user.split("@");
		String username = userarray[0];
		dbinfo += username;
		return dbinfo;
	}

	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}

	/**
	 * session.evict(obj)，会把指定的缓冲对象进行清除 <br/>
	 * session.clear()，把缓冲区内的全部对象清除，但不包括操作中的对象
	 */
	public void evict(Object model) {
		sessionFactory.getCurrentSession().evict(model);
	}

	/**
	 * 更新对象
	 */
	public void mergeModel(Object model) {
		sessionFactory.getCurrentSession().merge(model);
	}

	/**
	 * 更新对象
	 */
	public void updateModel(Object model) {
		sessionFactory.getCurrentSession().saveOrUpdate(model);
	}

	/**
	 * 保存对象
	 */
	public void createModel(Object model) {
		sessionFactory.getCurrentSession().save(model);
	}

	/**
	 * 删除对象
	 */
	public void deleteModel(Object model) {
		sessionFactory.getCurrentSession().delete(model);
	}

	/**
	 * 加载对象 没找到跑异常
	 */
	@SuppressWarnings("unchecked")
	public <T> T loadModel(Class<T> objType, Serializable key) {
		Object o = sessionFactory.getCurrentSession().load(objType, key);
		Hibernate.initialize(o);
		return (T) o;
	}

	/**
	 * 获取对象 没找到返回null
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> T getModel(Class<T> objType, Serializable key) {
		return (T) sessionFactory.getCurrentSession().get(objType, key);
	}

	/**
	 * 获取数据库中所有该类对象
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> getModels(Class<T> objType) {
		Query q = sessionFactory.getCurrentSession().createQuery(
				"FROM " + objType.getName());
		return (List<T>) q.list();
	}

	/**
	 * 执行hql语句,根据条件查询数据
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> hqlQueryWithParams(String hql, Map params) {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		setParams(q, params);
		return (List<T>) q.list();
	}

	/**
	 * 执行hql语句,根据条件查询数据 分页查询
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> hqlQueryWithParams(String hql, Map params, int pageSize,
			int currentPage) {
		currentPage = (currentPage > 0) ? currentPage - 1 : currentPage;
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		setParams(q, params);
		q.setMaxResults(pageSize);
		q.setFirstResult(currentPage * pageSize);
		return (List<T>) q.list();
	}

	/**
	 * 执行sql语句,根据条件查询数据 <br/>分页查询 返回List<Map>对象
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> sqlQuery(String sql, int pageSize, int currentPage) {
		currentPage = (currentPage > 0) ? currentPage - 1 : currentPage;
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		q.setMaxResults(pageSize);
		q.setFirstResult(currentPage * pageSize);
		return (List<T>) q.list();
	}

	/**
	 * 执行sql语句删除操作
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public void sqlDelete(String sql, Map params) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		setParams(q, params);
		q.executeUpdate();
	}

	/**
	 * 执行sql语句更新操作
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public void sqlUpdate(String sql, Map params) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		setParams(q, params);
		q.executeUpdate();
	}

	/**
	 * 执行sql语句查询操作
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> sqlQuery(String sql) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return (List<T>) q.list();
	}

	/**
	 * 执行sql语句查询操作 <br/> 带条件查询参数
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> sqlQueryWithParams(String sql, Map params) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		setParams(q, params);
		return (List<T>) q.list();
	}

	/**
	 * 执行sql语句查询操作 <br/> 带条件查询参数 <br/> 分页
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> sqlQueryWithParams(String sql, Map params, int pageSize,
			int currentPage) {
		currentPage = (currentPage > 0) ? currentPage - 1 : currentPage;
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql);
		setParams(q, params);
		q.setMaxResults(pageSize);
		q.setFirstResult(currentPage * pageSize);

		return (List<T>) q.list();
	}

	/**
	 * 执行hql语句查询唯一对象
	 */
	public <T> T hqlQueryWithUniqueResult(String hql) {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		return (T) q.uniqueResult();
	}

	/**
	 * 设置查询参数 <br/>params可以为null
	 */
	@SuppressWarnings("unchecked")
	private void setParams(Query q, Map params) {
		String key;
		if (params != null) {
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				key = (String) it.next();
				q.setParameter(key, params.get(key));
			}
		}

	}

	/**
	 * 查询结果数,与count(*)类似<br/> ep:select count(1) from student;
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public int sqlCountWithParams(String sql, Map params) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery(
				"SELECT COUNT(1) " + sql);
		setParams(q, params);
		List<Integer> list = q.list();
		for (Integer o : list) {
			return (o != null) ? o.intValue() : 0;
		}
		return 0;
	}

	/**
	 * 将执行hql语句的结果转换为指定对象,也可以转为LIST，也可以转化为MAP
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> hqlWithResultTransformer(String hql,
			ResultTransformer resultTransformer) {
		return (List<T>) sessionFactory.getCurrentSession().createQuery(hql)
				.setResultTransformer(resultTransformer).list();
	}

	/**
	 * 执行hql语句返回唯一整型结果(uniqueResult)
	 */
	public Integer hqlQueryReturnInteger(String hql) {
		return (Integer) sessionFactory.getCurrentSession().createQuery(hql)
				.uniqueResult();
	}

	/**
	 * 执行sql语句返回整型结果集
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> sqlQueryReturnInteger(String sql) {
		return (List<Integer>) sessionFactory.getCurrentSession()
				.createSQLQuery(sql).list();
	}

	/**
	 * 执行hql语句返回唯一长整型结果(uniqueResult)
	 */
	public Long hqlCountQuery(String hql) {
		// Generally, the return type of aggregation function count(*) is Long.
		return (Long) sessionFactory.getCurrentSession().createQuery(hql)
				.uniqueResult();
	}

	/**
	 * 将执行sql语句的结果转换为指定对象,也可以转为LIST，也可以转化为MAP
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> sqlWithResultTransformer(String sql,
			ResultTransformer resultTransformer) {
		return (List<T>) sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(resultTransformer).list();
	}

	/**
	 * 未知功能
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> sqlQueryWithParamsForRegexp(String sql, Map params,
			String variableName, Class<T> clazz) {
		// For "e" in sql, please check EventSearchCriteria.generateParams().
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql,
				variableName, clazz);
		setParams(q, params);
		return (List<T>) q.list();
	}

	/**
	 * 执行sql语句返回结果集大小(uniqueResult) 返回String类型
	 */
	public String getMaxColumn(String sql) throws SQLException {
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(
				sql);
		return query.uniqueResult().toString();
	}

	/**
	 * 执行sql语句返回结果集大小(uniqueResult) 返回int类型
	 */
	public int getSqlCounts(String sql) throws SQLException {
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(
				sql);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	/**
	 * 执行hql语句返回结果集大小(uniqueResult) 返回int类型
	 */
	public int getHqlCounts(String hql) throws SQLException {
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	/**
	 * 从数据数据库中读取单条记录的数据
	 * 
	 * @param prop_tableName
	 *            表示数据库表
	 * @param field
	 *            要查询的列名
	 * @param condition
	 *            要查询的条件
	 */
	public String getFieldValue(String prop_tableName, String field,
			String condition) {
		String sql = "select " + field + " as LLL0 from " + prop_tableName
				+ " where  " + condition;
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(
				sql);
		return query.uniqueResult().toString();
	}

	/**
	 * 执行sql语句,从数据数据库中读取单条记录的数据
	 */
	public String getFieldValue(String sql) {
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(
				sql);
		return query.uniqueResult().toString();
	}

	/**
	 * 执行sql语句,查询所有数据集合,直接将集合转换成含有map对象的子集
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> List<T> sqlAllQuery(String sql) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<T>) q.list();
	}

	/**
	 * 执行hql返回集合总数
	 */
	public int hqlCountWithParams(String hql, Map params) {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		setParams(q, params);
		List<Object> list = q.list();
		for (Object o : list) {
			return (o != null) ? Integer.parseInt(o.toString()) : 0;
		}
		return 0;
	}

	/**
	 */
	public int qbcCountQuery(DetachedCriteria detachedCriteria) {
		Object obj = detachedCriteria.getExecutableCriteria(
				sessionFactory.getCurrentSession()).uniqueResult();
		return (Integer) obj;
	}

	/**
	 */
	public List qbcQueryWithDetachedCriteria(DetachedCriteria detachedCriteria) {
		return detachedCriteria.getExecutableCriteria(
				sessionFactory.getCurrentSession()).list();
	}

	/**
	 * 分页方法实现
	 */
	public String pageQuery(Integer totalCount, Integer totalPage, Integer currentPage) {
		String link = "";
		link = "<div id=\"wai\">";
		link += "<ul id=\"ul\"><li class=\"showPage\">"
				+ "第"
				+ currentPage
				+ "页/共"
				+ totalPage
				+ "页"
				+ "</li><li class=\"li\"><a style=\"width:40px;\" class=\"firstpage\" href=\"?currentPage=1\">首页</a></li>";
		int x = 1, y = 0;
		if (currentPage < 5 && currentPage > 0) {
			if (totalPage >= 5) {
				x = 1;
				y = 5;
			} else {
				x = 1;
				y = totalPage;
			}
		}
		if (currentPage > 2 && (currentPage + 2) <= totalPage) {
			x = currentPage - 2;
			y = currentPage + 2;
		}
		if (currentPage > 4 && (currentPage + 2) > totalPage) {
			x = currentPage - 3;
			y = totalPage;
		}
		if (currentPage.intValue() == totalPage && currentPage > 4) {
			x = currentPage - 4;
			y = totalPage;
		}
		if (totalPage == 1) {
			x = 1;
			y = 1;
		}
		if (totalPage == 0) {
			x = 1;
			y = 1;
		}
		for (int i = x; i <= y; i++) {
			String cls = (i == currentPage ? "page02" : "page");
			link += "<li class=\"li\"><a class=\"" + cls + "\" href=\"?currentPage="
					+ i + "\">" + i + "</a></li>";
		}
		link += "<li class=\"li\"><a style=\"width:40px;\" class=\"firstpage\" href=\"?currentPage="
				+ totalPage + "\">末页</a></li></ul></div>";

		return link;
	}

}
