package cn.itcast.bos.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IDecidedzoneDao;
import cn.itcast.bos.dao.INoticebillDao;
import cn.itcast.bos.dao.IWorkbillDao;
import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.Noticebill;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.domain.Workbill;
import cn.itcast.bos.service.INoticebillService;
import cn.itcast.crm.service.CustomerService;

/**
 * 业务通知单操作Service
 */
@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{
	@Resource
	private INoticebillDao noticebillDao;
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	@Resource
	private IWorkbillDao workbillDao;
	//注入代理对象
	@Resource
	private CustomerService customerService;
	//保存一个业务通知单，并尝试自动分单
	public void save(Noticebill model) {
		noticebillDao.save(model);//持久状态的对象
		//尝试自动分单
		String pickaddress = model.getPickaddress();//客户的取件地址
		//通过代理对象调用crm服务，根据取件地址查询定区id
		String decidedzoneid = customerService.findDecidedzoneidByAddress(pickaddress);
		if(decidedzoneid != null){
			//可以自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneid);
			//根据定区查询取派员
			Staff staff = decidedzone.getStaff();
			//业务通知单关联取派员
			model.setStaff(staff);
			//设置分单类型为“自动”
			model.setOrdertype("自动");
			//为当前取派员创建一个工单
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单生成时间，系统时间
			workbill.setNoticebill(model);//工单关联业务通知单
			workbill.setPickstate("未取件");//取件状态：未取件、取件中、已取件
			workbill.setRemark(model.getRemark());//备注信息
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType("新");//工单类型：新、追、改、销
			workbillDao.save(workbill);//保存工单
			//调用Webservice完成为当前取派员发送短信
		}else{
			//不能自动分单，转为人工分单
			model.setOrdertype("人工");
		}
	}
}
