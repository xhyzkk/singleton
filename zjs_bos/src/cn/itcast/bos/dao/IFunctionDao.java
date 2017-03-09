package cn.itcast.bos.dao;

import java.util.List;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.Function;

public interface IFunctionDao extends IBaseDao<Function>{

	public List<Function> findFunctionsByUserId(String id);

	public List<Function> findAllMenu();

	public List<Function> findMenu(String id);

}
