package cn.itcast.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.ISubareaService;
import cn.itcast.bos.utils.PageBean;
/**
 * 分区管理Service
 */
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService{
	@Resource
	private ISubareaDao subareaDao;

	public void save(Subarea model) {
		subareaDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	public List<Subarea> findByCondition(DetachedCriteria dc) {
		return subareaDao.findByCriteria(dc);
	}
}
