package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.service.IWorkordermanageService;
import cn.itcast.bos.utils.BOSContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 任务操作Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport{
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private IWorkordermanageService workordermanageService;
	//查询组任务
	public String findGroupTask(){
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		String candidateUser = BOSContext.getLoginUser().getId();
		//组任务过滤
		query.taskCandidateUser(candidateUser);
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "groupTaskList";
	}
	
	//接收任务ID
	private String taskId;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	//查询业务数据
	public String showData() throws IOException{
		Map<String, Object> map = processEngine.getTaskService().getVariables(taskId);
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(map);
		return NONE;
	}
	
	//拾取组任务
	public String takeTask(){
		String userId = BOSContext.getLoginUser().getId();
		processEngine.getTaskService().claim(taskId, userId);
		return "toGroupTaskList";
	}
	
	//查询个人任务
	public String findPersonalTask(){
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.taskAssignee(BOSContext.getLoginUser().getId());
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "personalTaskList";
	}
	
	//是否审核通过
	private Integer check;
	
	//办理审核工作单任务
	public String checkWorkOrderManage(){
		Map<String, Object> map = null;
		Workordermanage workordermanage = null;
		map = processEngine.getTaskService().getVariables(taskId);
		if(check == null){
			//跳转到审核工作单页面
			ActionContext.getContext().getValueStack().set("map", map);
			return "checkUI";
		}else{
			//将managercheck改为“1”
			workordermanage = (Workordermanage) map.get("业务数据");
			workordermanage.setManagerCheck("1");
			workordermanageService.update(workordermanage);
			//重新设置流程变量
			processEngine.getTaskService().setVariable(taskId, "业务数据", workordermanage);
			//办理任务
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("check", check);
			processEngine.getTaskService().complete(taskId, variables);
			if(check == 0){
				//审核不通过,将工作单中的start改为“0”
				workordermanage.setStart("0");
				workordermanageService.update(workordermanage);
			}
		}
		return "toPersonalTaskList";
	}
	
	//办理其他任务
	public String outStore(){
		processEngine.getTaskService().complete(taskId);
		return "toPersonalTaskList";
	}
	
	public String transferGoods(){
		processEngine.getTaskService().complete(taskId);
		return "toPersonalTaskList";
	}
	
	public String receive(){
		processEngine.getTaskService().complete(taskId);
		return "toPersonalTaskList";
	}
	
	public Integer getCheck() {
		return check;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}
}
