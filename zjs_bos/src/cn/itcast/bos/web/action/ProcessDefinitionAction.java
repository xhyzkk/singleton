package cn.itcast.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 流程定义管理Action
 * 
 * @author zhaoqx
 * 
 */
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
	// 注入流程引擎对象
	@Resource
	private ProcessEngine processEngine;

	// 查询最新版本的流程定义列表
	public String list() {
		ProcessDefinitionQuery query = processEngine.getRepositoryService()
				.createProcessDefinitionQuery();
		query.latestVersion();
		List<ProcessDefinition> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}

	public void setDeploy(File deploy) {
		this.deploy = deploy;
	}

	// 接收上传的文件
	private File deploy;

	// 部署流程定义
	public String deploy() throws FileNotFoundException {
		DeploymentBuilder deploymentBuilder = processEngine
				.getRepositoryService().createDeployment();
		InputStream in = new FileInputStream(deploy);
		deploymentBuilder.addZipInputStream(new ZipInputStream(in));
		deploymentBuilder.deploy();
		return "toList";
	}

	public void setId(String id) {
		this.id = id;
	}

	// 接收流程定义ID
	private String id;

	// 根据流程定义ID获取对应的png图片输入流，使用文件下载方式展示到浏览器中
	public String viewpng() {
		InputStream pngStream = processEngine.getRepositoryService()
				.getProcessDiagram(id);
		ActionContext.getContext().getValueStack().set("pngStream",pngStream);
		return "viewpng";
	}
}
