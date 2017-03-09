package cn.itcast.bos.domain;

import java.util.HashSet;
import java.util.Set;
/**
 * 权限实体
 */
public class Function implements java.io.Serializable {

	// Fields

	private String id;//编号
	private Function parentFunction;//当前权限的上级权限
	private String name;//权限名称
	private String code;//关键字
	private String description;//描述
	private String page;//权限对应的访问url地址
	private String generatemenu = "1";//当前权限是否生成到菜单,1表示生成，0表示不生成
	private Integer zindex;//排序，保证菜单顺序
	private Set children = new HashSet(0);//当前权限的下级权限
	private Set roles = new HashSet(0);//当前权限对应的角色
	
	private String pId;//上级权限的id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Function getParentFunction() {
		return parentFunction;
	}
	public void setParentFunction(Function parentFunction) {
		this.parentFunction = parentFunction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getGeneratemenu() {
		return generatemenu;
	}
	public void setGeneratemenu(String generatemenu) {
		this.generatemenu = generatemenu;
	}
	public Integer getZindex() {
		return zindex;
	}
	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}
	public Set getChildren() {
		return children;
	}
	public void setChildren(Set children) {
		this.children = children;
	}
	public Set getRoles() {
		return roles;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}
	public String getpId() {
		if(parentFunction != null){
			return parentFunction.getId();
		}
		return "0";
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
}