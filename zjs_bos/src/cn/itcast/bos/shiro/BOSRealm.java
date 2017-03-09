package cn.itcast.bos.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.utils.BOSContext;

/**
 * 自定义Realm，进行认证和授权操作
 */
public class BOSRealm extends AuthorizingRealm {
	// 注入dao，访问数据库
	@Resource
	private IUserDao userDao;
	@Resource
	private IFunctionDao functionDao;

	// 认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken myToken = (UsernamePasswordToken) token;
		String username = myToken.getUsername();
		char[] password = myToken.getPassword();
		// 根据用户名查询数据库中的密码，将密码交给安全管理器，由安全管理器对象负责比较数据库中的密码和页面传递的密码是否一致
		User user = userDao.findUserByUsername(username);
		if(user == null){
			return null;
		}
		// 参数一：签名对象，认证通过后，可以在程序的任意位置获取当前放入的对象
		// 参数二：数据库中查询出的密码
		// 参数三：当前realm的类名
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
				user.getPassword(), this.getClass().getName());
		return info;
	}

	// 授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//授权信息对象
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//根据当前登录用户查询数据库，获得其对应的权限
		User user = (User) principals.getPrimaryPrincipal();
		if(user.getUsername().equals("admin")){
			//超级管理员，查询所有权限
			List<Function> list = functionDao.findAll();
			for (Function function : list) {
				info.addStringPermission(function.getCode());
			}
		}else{
			//普通用户，根据用户查询对应的权限
			List<Function> list = functionDao.findFunctionsByUserId(user.getId());
			for (Function function : list) {
				info.addStringPermission(function.getCode());
			}
		}
		return info;
	}

}
