package cn.itcast.bos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.stereotype.Service;

import cn.itcast.bos.dao.IWorkordermanageDao;
import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.service.IWorkordermanageService;

/**
 * 工作单操作Service
 */
@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService{
	@Resource
	private IWorkordermanageDao workordermanageDao;

	public void save(Workordermanage model) {
		workordermanageDao.save(model);
	}

	public List<Workordermanage> findListNotStart() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Workordermanage.class);
		//添加查询条件
		criteria.add(Restrictions.eq("start", "0"));
		return workordermanageDao.findByCriteria(criteria );
	}

	public Workordermanage findById(String id) {
		return workordermanageDao.findById(id);
	}
	
	//注入流程引擎对象
	@Resource
	private ProcessEngine processEngine;
	
	//启动物流配送流程
	public void start(String id) {
		//根据工作单ID查询工作单对象
		Workordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1");//状态改为“1”，表示已启动
		//设置工作单对象到流程变量表
		String processDefinitionKey = "transfer";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("业务数据", workordermanage);
		processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey , variables);
	}

	public void update(Workordermanage workordermanage) {
		workordermanageDao.update(workordermanage);
	}
}
