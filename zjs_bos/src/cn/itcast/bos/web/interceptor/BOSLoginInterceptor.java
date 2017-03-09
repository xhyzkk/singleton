package cn.itcast.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bos.domain.User;
import cn.itcast.bos.utils.BOSContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
/**
 * 自定义拦截器
 * 失效用户未登录，跳转到登录页面
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor{
	//拦截的方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		System.out.println("自定义拦截器执行了。。。。");
		//从session中获取当前用户
		User user = BOSContext.getLoginUser();
		if(user == null){
			//用户没有登录,跳转到登录页面
			return "login";
		}else{
			//用户已经登录，放行
			return invocation.invoke();//放行
		}
	}

}
