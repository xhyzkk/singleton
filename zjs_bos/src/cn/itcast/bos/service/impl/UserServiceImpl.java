package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IRoleDao;
import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.domain.Role;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.utils.MD5Utils;

/**
 * 用户操作Service
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	@Resource
	private IRoleDao roleDao;

	//用户登录操作
	public User login(User model) {
		//将输入的明文的密码进行md5加密
		String password = MD5Utils.md5(model.getPassword());
		List<User> list = userDao.findByNamedQuery("findByUsernameAndPassword", model.getUsername(),password);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	//根据用户id修改密码
	public void editPassword(String id, String password) {
		//命名查询语句修改密码
		password = MD5Utils.md5(password);
		userDao.executeNamedQuery("editPassword", password,id);
	}

	@Resource
	private ProcessEngine processEngine;
	
	//添加用户，同时关联角色,将用户数据同步到activiti的act_id_user表中
	public void save(User user, String[] roleIds) {
		//密码进行md5加密
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);//持久对象
		
		org.activiti.engine.identity.User actUser = new UserEntity();
		actUser.setId(user.getId());
		processEngine.getIdentityService().saveUser(actUser);
		
		if(roleIds != null && roleIds.length > 0){
			for (String id : roleIds) {
				//用户关联角色,构造脱管状态角色对象
				Role role = roleDao.findById(id);//new Role(id);
				user.getRoles().add(role);
				processEngine.getIdentityService().createMembership(actUser.getId(), role.getName());
			}
		}
	}
	
	public List<User> findAll() {
		return userDao.findAll();
	}
}
