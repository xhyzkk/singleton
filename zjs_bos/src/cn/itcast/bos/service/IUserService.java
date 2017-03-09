package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.User;

public interface IUserService {

	public User login(User model);

	public void editPassword(String id, String password);

	public void save(User model, String[] roleIds);

	public List<User> findAll();

}
