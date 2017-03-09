package cn.itcast.bos.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.bos.utils.PageBean;

/**
 * 持久层实现类
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	@Resource
	public void setSF(SessionFactory sessionFactory){
		//调用父类的方法注入会话工厂对象
		super.setSessionFactory(sessionFactory);
	}
	
	// 定义一个属性，表示实体的类型
	private Class<T> domainClass;

	/**
	 * 在构造方法中动态获得实体的类型
	 */
	public BaseDaoImpl() {
		// 获得父类类型
		ParameterizedType genericSuperclass = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		// 获得父类上的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		// 获得实体类型
		domainClass = (Class<T>) actualTypeArguments[0];
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(domainClass, id);
	}

	public List<T> findAll() {
		String hql = "FROM " + domainClass.getSimpleName();// 注意FROM后面的空格
		return this.getHibernateTemplate().find(hql);
	}

	public List<T> findByCriteria(DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	public List<T> findByNamedQuery(String queryName, Object... args) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, args);
	}

	public void executeNamedQuery(String queryName, Object... args) {
		Session session = this.getSession();
		Query query = session.getNamedQuery(queryName);
		if (args != null && args.length > 0) {
			int i = 0;
			for (Object arg : args) {
				query.setParameter(i++, arg);
			}
		}
		query.executeUpdate();
	}
	
	/**
	 * 通用分页方法
	 */
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();//当前页码
		int pageSize = pageBean.getPageSize();//每页显示记录数
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();//离线条件查询对象
		
		// 查询总记录数,指定hibernate框架发出select count(id) from xxx where ...统计数据量的sql
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long total = list.get(0);//总记录数
		
		//查询当前页展示的数据集合,指定hibernate框架发出select * from xxx where....
		detachedCriteria.setProjection(null);
		//指定hibernate框架将从数据表中查询的每行数据对应的包装成一个实体对象
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		List recordList = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		
		pageBean.setTotal(total.intValue());
		pageBean.setRows(recordList);
	}

}
