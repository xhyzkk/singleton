package cn.itcast.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Function;

/**
 * 权限管理Dao
 */
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements
		IFunctionDao {
	// 根据用户id查询对应的权限
	public List<Function> findFunctionsByUserId(String userid) {
		String hql = "from Function f left outer join fetch " +
				"f.roles r left outer join fetch r.users u where u.id = ?";
		return this.getHibernateTemplate().find(hql, userid);
	}

	//查询所有权限菜单数据
	public List<Function> findAllMenu() {
		String hql = "from Function f where f.generatemenu = '1' order by f.zindex desc";
		return this.getHibernateTemplate().find(hql);
	}
	
	//根据用户id查询权限菜单数据
	public List<Function> findMenu(String userid) {
		String hql = "select  distinct f from Function f left outer join fetch " +
				"f.roles r left outer join fetch r.users u where u.id = ?" +
				" and f.generatemenu = '1' order by f.zindex desc ";
		return this.getHibernateTemplate().find(hql, userid);
	}
}
