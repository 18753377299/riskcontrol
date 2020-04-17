package org.easy.excel.parsing;

import com.picc.riskctrl.common.utils.FTPUtil;
import com.picc.riskctrl.common.utils.PubTools;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.easy.excel.ExcelDefinitionReader;
import org.easy.excel.config.ExcelDefinition;
import org.easy.excel.config.FieldValue;
import org.easy.excel.exception.ExcelException;
import org.easy.excel.result.ExcelImportResult;
import org.easy.util.ReflectUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Excel导入实现类
 * @author lisuo
 *
 */
public class ExcelImport extends AbstractExcelResolver {
	
	
	public ExcelImport(ExcelDefinitionReader definitionReader) {
		super(definitionReader);
	}
	
	/**
	 * 读取Excel信息
	 * @param id 注册的ID
	 * @param titleIndex 标题索引
	 * @param excelStream Excel文件流
	 * @param sheetIndex Sheet索引位置
	 * @param multivalidate 是否逐条校验，默认单行出错立即抛出ExcelException，为true时为批量校验,可通过ExcelImportResult.hasErrors,和getErrors获取具体错误信息
	 * @return
	 * @throws Exception
	 */
	public ExcelImportResult readExcel(String id, int titleIndex,InputStream excelStream,Integer sheetIndex,boolean multivalidate,String path,String projectUrl) throws Exception {
		//从注册信息中获取Bean信息
		ExcelDefinition excelDefinition = definitionReader.getRegistry().get(id);
		if(excelDefinition==null){
			throw new ExcelException("没有找到 ["+id+"] 的配置信息");
		}
		return doReadExcel(excelDefinition,titleIndex,excelStream,sheetIndex,multivalidate,path,projectUrl);
	}
	
	protected ExcelImportResult doReadExcel(ExcelDefinition excelDefinition,int titleIndex,InputStream excelStream,Integer sheetIndex,boolean multivalidate,String path,String projectUrl) throws Exception {
		Workbook workbook = WorkbookFactory.create(excelStream);
		ExcelImportResult result = new ExcelImportResult();
		//读取sheet,sheetIndex参数优先级大于ExcelDefinition配置sheetIndex
		Sheet sheet = workbook.getSheetAt(sheetIndex==null?excelDefinition.getSheetIndex():sheetIndex);
	      
		
		Map<String, ArrayList<HSSFPicture>> pictureMap = this.findAllPictureDate((HSSFSheet)sheet);
     
		//标题之前的数据处理
		List<List<Object>> header = readHeader(excelDefinition, sheet,titleIndex);
		result.setHeader(header);
		//获取标题
		List<String> titles = readTitle(excelDefinition,sheet,titleIndex);
		//获取Bean
		List<Object> listBean = readRows(result.getErrors(),excelDefinition,titles, sheet,titleIndex,multivalidate,pictureMap,path,projectUrl);
		//在excel中加入错误提示
		setErrorMsg(sheet,titleIndex,result.getErrors());
		//设置图片文件名
		String fileName = "ERROR"+PubTools.getRandomFileName()+".xls";

		FTPUtil ftp =new FTPUtil();
		OutputStream out =ftp.uploadFile(path+"/temp/"+fileName);
		
//		FileOutputStream out = new FileOutputStream(new File(path+"/temp/",fileName));
		//保存错误文件
		workbook.write(out);
		out.close();
		ftp.close();
		workbook.close();
		
		//设置图片的网络地址及实际地址
		result.setErrorFileNetUrl(projectUrl+"temp/"+fileName);
		result.setErrorFileRealUrl(path+"/temp/"+fileName);
		result.setListBean(listBean);
		return result;
	}
	


	private void setErrorMsg(Sheet sheet, int titleIndex, List<ExcelError> errors) {
		// TODO Auto-generated method stub
		int columNum = sheet.getRow(titleIndex).getLastCellNum();
		for(ExcelError error:errors) {
			Row row = sheet.getRow(error.getRow()+titleIndex);
			if(row.getLastCellNum() <= columNum) {
				//替换poi版本3.1.1为3.16 add by wangwenjie 2019/7/22
				Cell errorCell =row.createCell(columNum, CellType.STRING);
//				Cell errorCell =row.createCell(columNum, CellType.STRING);
				errorCell.setCellValue(error.getErrorMsg());	
			}else {
				String oldValue = row.getCell(columNum).getStringCellValue();
				row.getCell(columNum).setCellValue(oldValue+error.getErrorMsg());
			}
		}
		
	}

	/**
	 * 解析标题之前的内容,如果ExcelDefinition中titleIndex 不是0
	 * @param excelDefinition
	 * @param sheet
	 * @return
	 */
	protected List<List<Object>> readHeader(ExcelDefinition excelDefinition, Sheet sheet, int titleIndex){
		List<List<Object>> header = null;
		if(titleIndex!=0){
			header = new ArrayList<List<Object>>(titleIndex);
			for(int i=0;i<titleIndex;i++){
				Row row = sheet.getRow(i);
				short cellNum = row.getLastCellNum();
				List<Object> item = new ArrayList<Object>(cellNum);
				for(int j=0;j<cellNum;j++){
					Cell cell = row.getCell(j);
					Object value = getCellValue(cell);
					item.add(value);
				}
				header.add(item);
			}
		}
		return header;
	}
	
	/**
	 * 读取多行
	 * @param result
	 * @param excelDefinition
	 * @param titles
	 * @param sheet
	 * @param titleIndex
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> readRows(List<ExcelError> errors, ExcelDefinition excelDefinition, List<String> titles, Sheet sheet, int titleIndex, boolean multivalidate, Map<String, ArrayList<HSSFPicture>> pictureMap, String path, String projectUrl)throws Exception {
		int rowNum = sheet.getLastRowNum();
		//读取数据的总共次数
		int totalNum = rowNum - titleIndex;
		int startRow =  -titleIndex;
		List<T> listBean = new ArrayList<T>(totalNum);
		for (int i = titleIndex+1; i <= rowNum; i++) {
			Row row = sheet.getRow(i);
			// 校验是否是空行
			if(CheckRowNull(row)) {
				ArrayList<HSSFPicture> pictures = null;
				//判断是否存在照片
				if(pictureMap!=null) {
					pictures = pictureMap.get(String.valueOf(i));
				}
				Object bean = readRow(errors,excelDefinition,row,titles,startRow+i,multivalidate,pictures,path,projectUrl);
				listBean.add((T) bean);
			}
		}
		return listBean;
	}
	
	/**
	 * 读取1行
	 * @param excelDefinition
	 * @param row
	 * @param titles
	 * @param rowNum 第几行
	 * @return
	 * @throws Exception
	 */
	protected Object readRow(List<ExcelError> errors, ExcelDefinition excelDefinition, Row row, List<String> titles, int rowNum, boolean multivalidate, ArrayList<HSSFPicture> pictures, String path, String projectUrl) throws Exception {
		//创建注册时配置的bean类型
		Object bean = ReflectUtil.newInstance(excelDefinition.getClazz());
		for(FieldValue fieldValue:excelDefinition.getFieldValues()){
			String title = fieldValue.getTitle();
			for (int j = 0; j < titles.size(); j++) {
				if(title.equals(titles.get(j))){
	        		FTPUtil ftp =new FTPUtil();
					try{
						Cell cell = row.getCell(j);
						//获取Excel原生value值
						Object value = getCellValue(cell);
						//校验
						validate(fieldValue, value, rowNum);
						if(value != null && !fieldValue.isImage()){
							if(value instanceof String){
								//去除前后空格
								value = value.toString().trim();
							}
							value = super.convert(bean,value, fieldValue, Type.IMPORT,rowNum);
							ReflectUtil.setProperty(bean, fieldValue.getName(), value);
						//图片处理
						}else if(fieldValue.isImage()) {
							if(pictures!= null) {
								if(pictures.size()>1) {
									errors.add(new ExcelError(rowNum,"存在多张照片"));
								}else if(((HSSFClientAnchor)pictures.get(0).getAnchor()).getCol1() == j) {
									String name = PubTools.getRandomFileName()+".jpg";
									
									InputStream sbs =new ByteArrayInputStream(pictures.get(0).getPictureData().getData());
									

					        		ftp.uploadFile(sbs, path+"/"+name);
									value = super.convert(bean,projectUrl+name, fieldValue, Type.IMPORT,rowNum);
									ReflectUtil.setProperty(bean, fieldValue.getName(), projectUrl+name);
								}else if(!fieldValue.isNull()){
									errors.add(new ExcelError(rowNum,"不存在照片"));
									continue;
//									throw new ExcelException("第"+rowNum+"行图片位置错误");
								}
							}else if(!fieldValue.isNull()){
								errors.add(new ExcelError(rowNum,"不存在照片"));
								continue;
//								throw new ExcelException("第"+rowNum+"行不存在照片");
							}
						}
						break;
					}catch(ExcelException e){
						//应用multivalidate
						if(multivalidate){
							errors.add(new ExcelError(rowNum,e.getMessage()));
							continue;
						}else{
							errors.add(new ExcelError(rowNum,e.getMessage()));
							throw e;
						}
					}finally {
			            if(ftp!=null) {
				            try {
								ftp.close();
							} catch (IOException e) {
								System.out.println("关闭ftp异常：" + e.getMessage());
							}
			            }
					}
				}
			}
		}
		return bean;
	}

	protected List<String> readTitle(ExcelDefinition excelDefinition, Sheet sheet, int titleIndex) {
		// 获取Excel标题数据
		Row hssfRowTitle = sheet.getRow(titleIndex);
		int cellNum = hssfRowTitle.getLastCellNum();
		List<String> titles = new ArrayList<String>(cellNum);
		// 获取标题数据
		for (int i = 0; i < cellNum; i++) {
			Cell cell = hssfRowTitle.getCell(i);
			Object value = getCellValue(cell);
			if(value==null){
				throw new ExcelException("id 为:["+excelDefinition.getId()+"]的标题不能为[ null ]。");
			}
			titles.add(value.toString());
		}
		return titles;
	}
	
	/**
	 * 数据有效性校验
	 * @param fieldValue
	 * @param value
	 * @param rowNum
	 */
	private void validate(FieldValue fieldValue, Object value, int rowNum){
		if(value == null || StringUtils.isBlank(value.toString())){
			//照片可以为空
			if(fieldValue.isImage()) {
			//空校验
			}else if(!fieldValue.isNull()){
				String err = getErrorMsg(fieldValue, "不能为空。", rowNum);
				throw new ExcelException(err);
			}
		}else{
			//正则校验
			String regex = fieldValue.getRegex();
			if(StringUtils.isNotBlank(regex)){
				String val = value.toString().trim();
				if(!val.matches(regex)){
					String errMsg = fieldValue.getRegexErrMsg()==null?"格式错误":fieldValue.getRegexErrMsg();
					String err = getErrorMsg(fieldValue, errMsg, rowNum);
					throw new ExcelException(err);
				}
			}
		}
	}

	
	
	public Map<String, ArrayList<HSSFPicture>> findAllPictureDate(HSSFSheet sheet) throws IOException{

	       Map<String, ArrayList<HSSFPicture>> dataMap = null;

	       //处理sheet中的图形
	       HSSFPatriarch hssfPatriarch = sheet.getDrawingPatriarch();
	       
	       ArrayList<HSSFPicture> pictureList =null;
	       if(hssfPatriarch!= null) {
		       //获取所有的形状图
		       List<HSSFShape> shapes = hssfPatriarch.getChildren();
	
		       if(shapes.size()>0){
	
		           dataMap = new HashMap<String, ArrayList<HSSFPicture>>();
	
	
		           for(HSSFShape sp : shapes){
		               if(sp instanceof HSSFPicture){
		                   //转换
		                   HSSFPicture picture = (HSSFPicture)sp;
		                   //图形定位
		                   if(picture.getAnchor() instanceof HSSFClientAnchor){
	
		                       HSSFClientAnchor anchor = (HSSFClientAnchor)picture.getAnchor();
		                       //获取图片所在行作为key值,插入图片时，默认图片只占一行的单个格子，不能超出格子边界
		                       int row1 = anchor.getRow1();
		                       String rowNum = String.valueOf(row1);
	
		                       if(dataMap.get(rowNum)!=null){
		                    	   pictureList = dataMap.get(rowNum);
		                       }else{
		                    	   pictureList = new ArrayList<HSSFPicture>();
		                       }
		                       pictureList.add(picture);
		                       dataMap.put(rowNum,pictureList);
	
		                       // 测试部分
		                         int row2 = anchor.getRow2();
		                         short col1 = anchor.getCol1();
		                         short col2 = anchor.getCol2();
		                         int dx1 = anchor.getDx1();
		                         int dx2 = anchor.getDx2();
		                         int dy1 = anchor.getDy1();
		                         int dy2 = anchor.getDy2();
	
		                         System.out.println("row1: "+row1+" , row2: "+row2+" , col1: "+col1+" , col2: "+col2);
		                         System.out.println("dx1: "+dx1+" , dx2: "+dx2+" , dy1: "+dy1+" , dy2: "+dy2);
		                   }
		               }
		           }
		       }
	       }

	       return dataMap;
	   }
	
	private boolean CheckRowNull(Row hssfRow){
		boolean flag = true; 
		if (hssfRow == null) {
			flag = false;
		} else {
			Iterator<Cell> cellItr =hssfRow.iterator();
			int cellNum =hssfRow.getPhysicalNumberOfCells();
			int count = 0;
			while(cellItr.hasNext()){  
			 Cell c =cellItr.next();
			 //替换poi版本3.1.1为3.16 add by wangwenjie 2019/7/22
//			 if(c.getCellTypeEnum() == CellType.BLANK){
			 if(c.getCellType() == CellType.BLANK){
				 count++;
			 }  
			}  
			if(count == cellNum) {
				flag =false;
			}
		}
		return flag;  
	} 
}
