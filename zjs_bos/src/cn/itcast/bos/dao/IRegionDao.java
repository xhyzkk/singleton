package cn.itcast.bos.dao;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region>{

	public void saveOrUpdate(Region region);

}
