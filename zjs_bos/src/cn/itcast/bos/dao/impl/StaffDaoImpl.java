package cn.itcast.bos.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IStaffDao;
import cn.itcast.bos.dao.base.BaseDaoImpl;
import cn.itcast.bos.domain.Staff;

/**
 * 取派员管理Dao
 */
@Repository
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements IStaffDao{

}
