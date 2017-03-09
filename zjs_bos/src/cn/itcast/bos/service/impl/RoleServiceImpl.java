package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import cn.itcast.bos.dao.IRoleDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.Role;
import cn.itcast.bos.service.IRoleService;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
	@Resource
	private IRoleDao roleDao;

	//注入流程引擎对象
	@Resource
	private ProcessEngine processEngine;
	
	//添加角色时，将数据同步到activiti的act_id_group表中
	public void save(Role role, String functionIds) {
		roleDao.save(role);//持久状态对象
		Group group = new GroupEntity();
		group.setId(role.getName());
		processEngine.getIdentityService().saveGroup(group);
		String[] ids = functionIds.split(",");
		for (String id : ids) {
			//角色关联权限对象
			Function function = new Function();
			function.setId(id);
			role.getFunctions().add(function);
		}
	}

	public List<Role> findAll() {
		return roleDao.findAll();
	}
}
