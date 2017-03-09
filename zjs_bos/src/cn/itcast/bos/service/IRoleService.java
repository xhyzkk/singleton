package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.Role;

public interface IRoleService {

	public void save(Role model, String functionIds);

	public List<Role> findAll();

}
