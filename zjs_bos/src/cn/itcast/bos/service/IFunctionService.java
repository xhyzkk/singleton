package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;

public interface IFunctionService {

	public List<Function> findAll();

	public List<Function> findMenu(User loginUser);

}
