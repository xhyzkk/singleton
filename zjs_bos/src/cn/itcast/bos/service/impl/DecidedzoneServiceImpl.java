package cn.itcast.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IDecidedzoneDao;
import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.IDecidedzoneService;
import cn.itcast.bos.utils.PageBean;

/**
 * 定区管理Service
 */
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{
	//注入dao
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	@Resource
	private ISubareaDao subareaDao;

	public void save(Decidedzone model,String subareaid) {
		decidedzoneDao.save(model);//持久状态的对象
		Subarea subarea = subareaDao.findById(subareaid);//持久状态对象
		subarea.setDecidedzone(model);
	}

	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}
}
