package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.utils.PageBean;
import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 取派员管理Action
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{
	/**
	 * 保存取派员
	 */
	public String save(){
		//System.out.println(model);
		staffService.save(model);
		return "list";
	}
	
	/**
	 * 查询取派员数据，序列化为json返回
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		//调用Service完成分页查询
		staffService.pageQuery(pageBean);
		this.writePageBean2json(pageBean, new String[]{"detachedCriteria","pageSize","currentPage","decidedzones"});
		return NONE;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	//接收作废方法传递的参数
	private String ids;
	/**
	 * 作废方法
	 */
	@RequiresPermissions("staff-delete")//必须具有staff-deltet权限，才能访问当前方法
	public String delete(){
		staffService.delete(ids);
		return "list";
	}
	
	/**
	 * 修改取派员信息
	 */
	public String update(){
		//先查询原始数据，再进行数据覆盖
		Staff staff = staffService.findById(model.getId());
		//进行覆盖
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		if(model.getHaspda() != null){
			staff.setHaspda(model.getHaspda());
		}else{
			staff.setHaspda("0");
		}
		staff.setStandard(model.getStandard());
		staffService.update(staff);
		return "list";
	}
	
	/**
	 * 查询取派员数据，返回json
	 */
	public String findStaffByAjax(){
		//提供离线条件查询对象，包装查询条件
		DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
		//添加过滤条件：删除标志deltag等于“0”
		dc.add(Restrictions.eq("deltag", "0"));
		//添加过滤条件：没有负责定区取派员
		dc.add(Restrictions.isEmpty("decidedzones"));
		List<Staff> list = staffService.findByCondition(dc);
		
		String[] excludes = new String[]{"decidedzones","standard","station","deltag","haspda","telephone"};
		this.writeList2json(list, excludes);
		return NONE;
	}
}
