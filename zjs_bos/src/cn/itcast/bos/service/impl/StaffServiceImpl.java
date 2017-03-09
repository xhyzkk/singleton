package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IStaffDao;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IStaffService;
import cn.itcast.bos.utils.PageBean;

/**
 * 取派员管理Service
 */
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	//注入dao
	@Resource
	private IStaffDao staffDao;

	public void save(Staff model) {
		staffDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	/**
	 * 批量作废取派员
	 */
	public void delete(String ids) {
		String[] sids = ids.split(",");
		for (int i = 0; i < sids.length; i++) {
			String id = sids[i];
			Staff staff = staffDao.findById(id);//持久状态对象
			staff.setDeltag("1");//逻辑删除
		}
	}
	
	public Staff findById(String id) {
		return staffDao.findById(id);
	}
	
	public void update(Staff staff) {
		staffDao.update(staff);
	}

	public List<Staff> findByCondition(DetachedCriteria dc) {
		return staffDao.findByCriteria(dc);
	}
}
