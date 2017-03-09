package cn.itcast.bos.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class POITest {
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		// 解析Excel文件----读取Excel文件中的内容
		// 1、 加载要读取的文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(
				"e:\\区域导入测试数据.xls")));
		// 2、读取第一个sheet页
		HSSFSheet sheet = workbook.getSheetAt(0);
		//3、 循环当前sheet页中的每一行
		for (Row row : sheet) {
			//4、 获得当前行的某一列
			String v1 = row.getCell(0).getStringCellValue();
			String v2 = row.getCell(1).getStringCellValue();
			String v3 = row.getCell(2).getStringCellValue();
			String v4 = row.getCell(3).getStringCellValue();
			String v5 = row.getCell(4).getStringCellValue();
			System.out.println(v1 + " " + v2 + " " + v3 + " " + v4 + " " + v5) ;
		}
	}
}
