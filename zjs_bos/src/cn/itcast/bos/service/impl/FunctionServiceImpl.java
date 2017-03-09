package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.stereotype.Service;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IFunctionService;

/**
 * 权限管理Service
 */
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService{
	//注入dao
	@Resource
	private IFunctionDao functionDao;

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	//根据登录人查询对应的权限菜单数据
	public List<Function> findMenu(User loginUser) {
		List<Function> list = null;
		if(loginUser.getUsername().equals("admin")){
			//超级管理员，查询所有菜单数据
			list = functionDao.findAllMenu();
		}else{
			//普通用户，查询对应的权限菜单数据
			list = functionDao.findMenu(loginUser.getId());
		}
		return list;
	}
}
