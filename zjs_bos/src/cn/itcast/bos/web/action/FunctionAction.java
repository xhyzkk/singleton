package cn.itcast.bos.web.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Function;
import cn.itcast.bos.utils.BOSContext;
import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 权限管理Action
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	/**
	 * 查询所有的权限数据，返回json
	 */
	public String list(){
		List<Function> list = functionService.findAll();
		String[] excludes = new String[]{"parentFunction","children","roles"};
		this.writeList2json(list, excludes);
		return NONE;
	}
	
	/**
	 *根据登录用户查询对应的权限菜单数据
	 */
	public String findMenu(){
		List<Function> list = functionService.findMenu(BOSContext.getLoginUser());
		String[] excludes = new String[]{"parentFunction","children","roles"};
		this.writeList2json(list, excludes);
		return NONE;
	}
}
