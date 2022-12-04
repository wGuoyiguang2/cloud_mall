package com.hqtc.common.utils;

import com.hqtc.common.config.ColumnName;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * excel poi 工具类
 * @author WangBin
 *
 */
public class ExcelPOIUtil {
	private final static String FILE_TYPE=".xls";
	private final static int SHEET_SIZE=60000;
	private final static Log log = LogFactory.getLog(ExcelPOIUtil.class);
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 导出
	 *
	 * @param list
	 * @throws Exception
	 */
	public static void export(String fileName,List list,HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (list == null || list.size() == 0) {
			throw new Exception("数据为空！");
		}
		BrowserUtil.setDownloadName(request, response, fileName+FILE_TYPE);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle style = getCellStyle(wb);
		List<Field> fields = Arrays.asList(list.get(0).getClass().getDeclaredFields());
		int n0=list.size()/SHEET_SIZE+1;
		for (int n = 0; n < n0; n++) {
			HSSFSheet sheet = wb.createSheet("sheet"+(n+1));
			//生成表头
			createTitle(sheet,style,fields);
			//生成数据
			int startIndex=n*SHEET_SIZE+1;
			int endIndex=0;
			if(n==n0-1){
				endIndex=list.size();
			}else{
				endIndex=(n+1)*SHEET_SIZE;
			}
			createData(sheet,style,fields,list,startIndex,endIndex);
		}
		wb.write(response.getOutputStream());
	}

	/**
	 * 获取单元格样式
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle getCellStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行
		HSSFFont font = workbook.createFont();
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		return cellStyle;
	}

	/**
	 * 生成表头
	 * @param sheet
	 * @param style
	 * @param fields
	 */
	private static void createTitle(HSSFSheet sheet,HSSFCellStyle style,List<Field> fields){
		HSSFRow row0 = sheet.createRow(0);
		for (int y = 0; y < fields.size(); y++) {
			Field field = fields.get(y);
			HSSFCell cell = row0.createCell(y);
			cell.setCellStyle(style);
			ColumnName columnName = field.getAnnotation(ColumnName.class);
			if (columnName != null) {
				cell.setCellValue(columnName.value() == null ? "" : columnName.value().toString());
			}
		}
	}

	/**
	 * 生成数据
	 * @param sheet
	 * @param style
	 * @param fields
	 * @param list
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	private static void createData(HSSFSheet sheet,HSSFCellStyle style,List<Field> fields,List list,int startIndex,int endIndex) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		for (int i = startIndex,n=1; i <= endIndex; i++,n++) {
			HSSFRow row = sheet.createRow(n);
			for (int y = 0; y < fields.size(); y++) {
				HSSFCell cell = row.createCell(y);
				String fieldName = fields.get(y).getName();
				Object value = PropertyUtils.getProperty(list.get(i - 1), fieldName);
				if(value instanceof Date){
					value=dateFormat.format(value);
				}else if(value instanceof Double){
					value=new DecimalFormat("0.00").format((Double)value);
				}
				cell.setCellValue(value == null ? "" : value.toString());
				cell.setCellStyle(style);
			}
		}
	}
}