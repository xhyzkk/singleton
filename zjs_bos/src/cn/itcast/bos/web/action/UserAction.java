package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.User;
import cn.itcast.bos.utils.BOSContext;
import cn.itcast.bos.utils.MD5Utils;
import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 用户管理Action
 */
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	// 接收验证码
	private String checkcode;

	// 用户登录功能
	public String login() {
		// 从session中获取生成的验证码
		String validateCode = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("key");
		// 确定验证码输入是否正确
		if (StringUtils.isBlank(checkcode) || !checkcode.equals(validateCode)) {
			// 验证码输入错误,跳转到登录页面，提示错误信息
			// 设置错误提示信息
			this.addActionError(this.getText("checkCodeError"));// 从资源文件中根据key获取对应的value
			return "login";
		} else {
			//使用shiro提供的方式进行权限认证
			//获得当前用户对象，现在状态为“未认证”
			Subject subject = SecurityUtils.getSubject();
			String username = model.getUsername();
			String password = model.getPassword();
			password = MD5Utils.md5(password);
			AuthenticationToken token = new UsernamePasswordToken(username,password);
			try{
				subject.login(token);//调用安全管理器，安全管理器调用Realm
				User user = (User) subject.getPrincipal();
				// 登录成功，将user放入session，跳转到系统首页
				ServletActionContext.getRequest().getSession()
						.setAttribute("loginUser", user);
				
			}catch (UnknownAccountException e) {
				e.printStackTrace();
				//用户名不存在，跳转到登录页面
				this.addActionError("用户名不存在！");
				return "login";
			}catch (IncorrectCredentialsException e) {
				// 密码错误，跳转到登录页面
				this.addActionError("密码错误！");
				e.printStackTrace();
				return "login";
			}
			return "home";
		}
	}

	// 用户登录功能
	public String login_bak() {
		// 从session中获取生成的验证码
		String validateCode = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("key");
		// 确定验证码输入是否正确
		if (StringUtils.isBlank(checkcode) || !checkcode.equals(validateCode)) {
			// 验证码输入错误,跳转到登录页面，提示错误信息
			// 设置错误提示信息
			this.addActionError(this.getText("checkCodeError"));// 从资源文件中根据key获取对应的value
			return "login";
		} else {
			// 验证码输入正确
			// 进行登录校验
			System.out.println(model);
			User user = userService.login(model);
			if (user != null) {
				// 登录成功，将user放入session，跳转到系统首页
				ServletActionContext.getRequest().getSession()
						.setAttribute("loginUser", user);
				return "home";
			} else {
				// 登录失败，跳转到登录页面，设置登录失败的错误提示信息
				this.addActionError(this.getText("loginFail"));
				return "login";
			}
		}
	}

	// 用户退出操作
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();// 将session失效
		return "login";
	}

	// 修改密码
	public String editPassword() throws IOException {
		String password = model.getPassword();
		String id = BOSContext.getLoginUser().getId();
		String f = "1";
		try {
			userService.editPassword(id, password);
		} catch (Exception e) {
			f = "0";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}

	//接收角色id数组
	private String[] roleIds;
	/**
	 * 添加用户操作
	 */
	public String save(){
		userService.save(model,roleIds);
		return "list";
	}
	
	/**
	 * 查询所有用户
	 */
	public String list(){
		List<User> list = userService.findAll();
		String[] excludes = new String[]{"noticebills","roles"};
		this.writeList2json(list, excludes );
		return NONE;
	}
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
}
