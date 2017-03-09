package cn.itcast.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.User;
/**
 * 用户管理Dao
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{
	//根据用户名查询用户
	public User findUserByUsername(String username) {
		String hql = "from User u where u.username = ?";
		List<User> list = this.getHibernateTemplate().find(hql,username);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
