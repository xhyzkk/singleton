package cn.itcast.bos.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;

/**
 * 定区管理Action
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	private String subareaid;// 属性驱动，分区id

	/**
	 * 保存定区方法
	 */
	public String save() {
		decidedzoneService.save(model, subareaid);
		return "list";
	}

	/**
	 * 分页查询
	 */
	public String pageQuery() {
		decidedzoneService.pageQuery(pageBean);
		String[] excludes = new String[] { "detachedCriteria", "pageSize",
				"currentPage", "subareas" ,"decidedzones"};
		this.writePageBean2json(pageBean, excludes);
		return NONE;
	}

	public void setSubareaid(String subareaid) {
		this.subareaid = subareaid;
	}
	
	//注入代理对象，通过这个代理对象可以远程调用crm项目中的方法
	@Resource
	private CustomerService customerService;
	
	/**
	 * 通过代理对象调用crm服务，查询未关联到定区的客户
	 */
	public String findCustomersNoAssociation(){
		//代理对象向指定的crm服务发送远程调用请求
		List<Customer> list = customerService.findnoassociationCustomers();
		String[] excludes = new String[]{"station","telephone","address","decidedzone_id"};
		//将list序列化为json数据，写回客户端浏览器
		this.writeList2json(list, excludes);
		return NONE;
	}
	
	/**
	 * 通过代理对象调用crm服务，查询已经关联到指定定区的客户
	 */
	public String findCustomersAssociation(){
		List<Customer> list = customerService.findhasassociationCustomers(model.getId());
		String[] excludes = new String[]{"station","telephone","address","decidedzone_id"};
		//将list序列化为json数据，写回客户端浏览器
		this.writeList2json(list, excludes);
		return NONE;
	}
	
	private Integer[] customerIds;//接收表单提交的客户id数组
	/**
	 * 通过代理对象调用crm服务，将客户关联到指定定区
	 */
	public String assigncustomerstodecidedzone(){
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		return "list";
	}
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}
}
