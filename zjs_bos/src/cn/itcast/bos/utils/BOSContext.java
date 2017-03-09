package cn.itcast.bos.utils;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bos.domain.User;

public class BOSContext {
	public static User getLoginUser(){
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("loginUser");
		return user;
	}
}
