package cn.itcast.bos.web.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Role;
import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 角色管理Action
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	private String functionIds;

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	
	/**
	 * 保存角色
	 */
	public String save(){
		roleService.save(model,functionIds);
		return "list";
	}
	
	/**
	 * 查询所有角色
	 */
	public String list(){
		List<Role> list = roleService.findAll();
		String[] excludes = new String[]{"users","functions"};
		this.writeList2json(list, excludes );
		return NONE;
	}
	
}
