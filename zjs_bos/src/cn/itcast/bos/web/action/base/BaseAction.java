package cn.itcast.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.service.IDecidedzoneService;
import cn.itcast.bos.service.IFunctionService;
import cn.itcast.bos.service.INoticebillService;
import cn.itcast.bos.service.IRegionService;
import cn.itcast.bos.service.IRoleService;
import cn.itcast.bos.service.IStaffService;
import cn.itcast.bos.service.ISubareaService;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.service.IWorkordermanageService;
import cn.itcast.bos.utils.PageBean;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 抽取表现层
 * 
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	// 声明模型对象
	protected T model;

	public T getModel() {
		return model;
	}

	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}

	protected PageBean pageBean = new PageBean();
	@Resource
	protected IUserService userService;
	@Resource
	protected IStaffService staffService;
	@Resource
	protected IRegionService regionService;
	@Resource
	protected ISubareaService subareaService;
	@Resource
	protected IDecidedzoneService decidedzoneService;
	@Resource
	protected INoticebillService noticebillService;
	@Resource
	protected IWorkordermanageService workordermanageService;
	@Resource
	protected IFunctionService functionService;
	@Resource
	protected IRoleService roleService;
	// 在构造方法中动态获得实体的类型，并通过反射实例化模型对象

	protected DetachedCriteria detachedCriteria;// 离线条件查询对象，包装分页的查询条件

	public BaseAction() {
		// 获得父类（BaseAction<T>）类型
		ParameterizedType genericSuperclass = null;
				
		Type genericSuperclass2 = this.getClass().getGenericSuperclass();
		if(genericSuperclass2 instanceof ParameterizedType){
			genericSuperclass = (ParameterizedType) genericSuperclass2;
		}else{
			genericSuperclass = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
		}
		// 获得父类上的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		// 实体类型
		Class<T> domainClass = (Class<T>) actualTypeArguments[0];
		// 在构造方法中动态创建离线条件查询对象
		detachedCriteria = DetachedCriteria.forClass(domainClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			model = domainClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	// 将PageBean对象序列化为json，并写回客户端浏览器
	public void writePageBean2json(PageBean pageBean, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String json = jsonObject.toString();
		// 将json数据通过输出流写到客户端
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 将PageBean对象序列化为json，并写回客户端浏览器
	public void writeList2json(List list, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(list, jsonConfig);
		String json = jsonObject.toString();
		// 将json数据通过输出流写到客户端
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeObject2json(Object object, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
		String json = jsonObject.toString();
		// 将json数据通过输出流写到客户端
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
