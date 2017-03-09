package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.utils.FileUtils;
import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 分区管理Action
 * 
 */
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	/**
	 * 添加分区
	 */
	public String save() {
		subareaService.save(model);
		return "list";
	}

	/**
	 * 分页查询
	 */
	public String pageQuery() {
		String addresskey = model.getAddresskey();
		Region region = model.getRegion();
		DetachedCriteria subareaDC = pageBean.getDetachedCriteria();

		if (StringUtils.isNotBlank(addresskey)) {
			// 按照地址关键字进行模糊查询
			subareaDC.add(Restrictions.like("addresskey", "%" + addresskey
					+ "%"));
		}
		if (region != null) {
			DetachedCriteria regionDC = subareaDC.createCriteria("region");

			String province = region.getProvince();// 省
			String city = region.getCity();// 市
			String district = region.getDistrict();// 区

			if (StringUtils.isNotBlank(province)) {
				// 按照省进行模糊查询
				regionDC.add(Restrictions
						.like("province", "%" + province + "%"));
			}
			if (StringUtils.isNotBlank(city)) {
				// 按照市进行模糊查询
				regionDC.add(Restrictions.like("city", "%" + city + "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				// 按照区进行模糊查询
				regionDC.add(Restrictions
						.like("district", "%" + district + "%"));
			}
		}
		subareaService.pageQuery(pageBean);
		// 写回json数据
		this.writePageBean2json(pageBean, new String[] { "subareas",
				"decidedzone", "detachedCriteria", "pageSize", "currentPage" });
		return NONE;
	}

	/**
	 * 导出Excel文件
	 * 
	 * @throws IOException
	 */
	public String exportXls() throws IOException {
		// 查询所有的分区数据
		List<Subarea> list = subareaService.findAll();
		// 将list集合中的数据写到一个Excel文件中
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件，当前这个文件在内存中
		HSSFSheet sheet = workbook.createSheet("分区数据");// 创建一个sheet页
		HSSFRow headRow = sheet.createRow(0);// 创建标题行
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编码");
		headRow.createCell(2).setCellValue("关键字");
		headRow.createCell(3).setCellValue("起始号");
		headRow.createCell(4).setCellValue("结束号");
		headRow.createCell(5).setCellValue("当双号");
		headRow.createCell(6).setCellValue("位置信息");
		for (Subarea subarea : list) {// 循环list，将数据写到Excel文件中
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			dataRow.createCell(3).setCellValue(subarea.getStartnum());
			dataRow.createCell(4).setCellValue(subarea.getEndnum());
			dataRow.createCell(5).setCellValue(subarea.getSingle());
			dataRow.createCell(6).setCellValue(subarea.getPosition());
		}

		// 文件下载：一个流（输出流）、两个头
		ServletOutputStream out = ServletActionContext.getResponse()
				.getOutputStream();

		String filename = "分区数据.xls";
		filename = FileUtils.encodeDownloadFilename(filename,
				ServletActionContext.getRequest().getHeader("user-agent"));
		ServletActionContext.getResponse().setContentType(
				ServletActionContext.getServletContext().getMimeType(filename));

		ServletActionContext.getResponse().setHeader("content-disposition",
				"attachment;filename=" + filename);
		workbook.write(out);

		return NONE;
	}
	
	/**
	 * 查询分区数据，返回json
	 */
	public String findSubareaByAjax(){
		DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件：未分配到定区的分区
		dc.add(Restrictions.isNull("decidedzone"));
		List<Subarea> list = subareaService.findByCondition(dc);
		String[] excludes = new String[]{"decidedzone","region","startnum","endnum","single"};
		this.writeList2json(list, excludes );
		return NONE;
	}
}
