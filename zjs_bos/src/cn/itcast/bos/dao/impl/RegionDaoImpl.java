package cn.itcast.bos.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IRegionDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Region;

/**
 * 区域管理Dao
 */
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {
	public void saveOrUpdate(Region region) {
		this.getHibernateTemplate().saveOrUpdate(region);
	}

}
