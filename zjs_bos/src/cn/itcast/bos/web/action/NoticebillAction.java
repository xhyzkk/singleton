package cn.itcast.bos.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Noticebill;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.utils.BOSContext;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;

/**
 * 业务通知单管理Action
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	//提供一个属性驱动，接收手机号
	private String phone;
	//注入代理对象，远程调用crm
	@Resource
	private CustomerService customerService;
	/**
	 * 通过代理对象远程调用crm项目，根据手机号查询客户
	 */
	public String findCustomerByPhone(){
		Customer customer = customerService.findCustomerByPhone(phone);
		String[] excludes = new String[]{"decidedzone_id"};
		//将客户对象序列化为json返回
		this.writeObject2json(customer, excludes );
		return NONE;
	}
	
	/**
	 * 业务受理,保存业务通知单，尝试自动分单
	 */
	public String save(){
		//从session中获取登录用户
		User user = BOSContext.getLoginUser();
		model.setUser(user);//业务通知单关联当前用户
		noticebillService.save(model);
		return "toSave";
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
