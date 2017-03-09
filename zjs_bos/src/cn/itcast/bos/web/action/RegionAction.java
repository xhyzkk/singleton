package cn.itcast.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.utils.PageBean;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.base.BaseAction;

/**
 * 区域管理Action
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	// 提供属性接收上传的文件
	private File myFile;

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	/**
	 * 区域批量导入到数据库
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String importXls() throws FileNotFoundException, IOException {
		String flag = "1";// 导入是否成功的标识
		// 创建一个List对象，包装区域对象
		List<Region> list = new ArrayList<Region>();
		try {
			// 1、加载Excel文件
			HSSFWorkbook workbook = new HSSFWorkbook(
					new FileInputStream(myFile));
			// 2、加载第一个sheet页
			HSSFSheet sheet = workbook.getSheetAt(0);
			// 3、循环sheet页，获得每一行数据
			for (Row row : sheet) {
				int rowNum = row.getRowNum();
				if (rowNum == 0) {
					// 第一行为文件的标题行，忽略
					continue;
				}
				// 4、获得当前行的某一列
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				// 将解析出的Excel文件中的数据包装成一个Region对象
				Region region = new Region(id, province, city, district,
						postcode);

				// 使用Pinyin4J工具生成简码和城市编码

				// 简码----北京市北京市东城区----》BJSBJSDCQ
				String shortcode = province + city + district;
				String[] strings = PinYin4jUtils.getHeadByString(shortcode);
				shortcode = StringUtils.join(strings, "");

				// 城市编码----北京市-----》beijingshi
				String[] strings2 = PinYin4jUtils.stringToPinyin(city);
				String citycode = StringUtils.join(strings2);

				region.setShortcode(shortcode);
				region.setCitycode(citycode);
				list.add(region);
			}
			// 批量保存数据
			regionService.saveBatch(list);
		} catch (Exception e) {
			flag = "0";
		}
		// 向客户端浏览器写回数据
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}

	/**
	 * 分页查询方法
	 * 
	 * @throws IOException
	 */
	public String pageQuery() throws IOException {
		regionService.pageQuery(pageBean);
		this.writePageBean2json(pageBean, new String[] { "detachedCriteria",
				"pageSize", "currentPage", "subareas" });
		return NONE;
	}

	/**
	 * 查询所有区域，返回json格式数据
	 */
	public String findListByAjax() {
		List<Region> list = regionService.findAll();

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "subareas", "province", "city",
				"district", "postcode", "shortcode", "citycode" });
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		String json = jsonArray.toString();

		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
}
