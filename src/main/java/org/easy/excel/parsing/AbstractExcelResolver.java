package org.easy.excel.parsing;


import com.picc.riskctrl.common.dao.RiskDcodeRepository;
import com.picc.riskctrl.common.jpa.condition.Restrictions;
import com.picc.riskctrl.common.jpa.vo.Criteria;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.service.DataSourcesService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.easy.excel.ExcelDefinitionReader;
import org.easy.excel.config.FieldValue;
import org.easy.excel.exception.ExcelException;
import org.easy.excel.util.ExcelUtil;
import org.easy.util.ReflectUtil;
import org.easy.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel抽象解析器
 * 
 * @author lisuo
 *
 */
public abstract class AbstractExcelResolver implements CellValueConverter{
	
	protected ExcelDefinitionReader definitionReader;
	
	@Autowired
	RiskDcodeRepository riskDcodeRepository;
	
	@Autowired
	DataSourcesService dataSourcesService;

	/** 注册字段解析信息 */
	private Map<String,CellValueConverter> cellValueConverters = new HashMap<String, CellValueConverter>();
	
	public AbstractExcelResolver(ExcelDefinitionReader definitionReader) {
		this.definitionReader = definitionReader;
	}

	/**
	 * 解析表达式format 属性
	 * 
	 * @param value
	 * @param format
	 * @param fieldValue
	 * @param rowNum
	 * @return
	 */
	protected String resolverExpression(String value, String format, Type type,FieldValue fieldValue,int rowNum) {
		try {
			String[] expressions = StringUtils.split(format, ",");
			for (String expression : expressions) {
				String[] val = StringUtils.split(expression, ":");
				String v1 = val[0];
				String v2 = val[1];
				if (Type.EXPORT == type) {
					if (value.equals(v1)) {
						return v2;
					}
				} else if (Type.IMPORT == type) {
					if (value.equals(v2)) {
						return v1;
					}
				}
			}
		} catch (Exception e) {
			throw new ExcelException(getErrorMsg(fieldValue, "表达式:" + format + "错误,正确的格式应该以[,]号分割,[:]号取值", rowNum));
		}
		throw new ExcelException(getErrorMsg(fieldValue, "["+value+"]取值错误", rowNum));
	}

	/**
	 * 设置Cell单元的值
	 * 
	 * @param cell
	 * @param value
	 */
	protected void setCellValue(Cell cell, Object value) {
		ExcelUtil.setCellValue(cell, value);
	}

	/**
	 * 获取cell值
	 * 
	 * @param cell
	 * @return
	 */
	protected Object getCellValue(Cell cell) {
		return ExcelUtil.getCellValue(cell);
	}
	
	//默认实现
	@Override
	public Object convert(Object bean,Object value, FieldValue fieldValue, Type type,int rowNum) throws Exception {
		if(value !=null){
			//通过上下文获取databaseDao，以下为示例
//			DatabaseDao databaseDao = (DatabaseDao)ApplicationContextUtil.getApplicationContext().getBean("databaseDo");
			//解析器实现，读取数据
			String convName = fieldValue.getCellValueConverterName();
			if(convName==null){
				//执行默认
				String name = fieldValue.getName();
				String pattern = fieldValue.getPattern();
				String format = fieldValue.getFormat();
				Boolean dcode = fieldValue.isDcode();
				Boolean isEmail = fieldValue.isEmail();
				Boolean isPhone = fieldValue.isPhone();
				Boolean isRiskName = fieldValue.isRiskName();
				DecimalFormat decimalFormat = fieldValue.getDecimalFormat();
				Boolean isNumberFlag = fieldValue.isNumberFlag();
				String fieldName = fieldValue.getName();
				String decimalNumber = fieldValue.getDecimalNumber();
				if("floodDepth,lon,lat,pointx_2000,pointy_2000,pointx_02,pointy_02".indexOf(fieldName)>-1){
					isNumberFlag = true;
				}				
				if (StringUtils.isNotBlank(pattern)) {
					String [] patterns = StringUtils.split(pattern, ",");
					if (Type.EXPORT == type) {
						//导出使用第一个pattern
						return DateFormatUtils.format((Date) value, patterns[0]);
					} else if (Type.IMPORT == type) {
						if (value instanceof String) {
							Date date;
							try {
								date = DateUtils.parseDate((String) value, patterns);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								StringBuilder errMsg = new StringBuilder("[");
								errMsg.append(value.toString()).append("]")
								.append("不能转换成日期,正确的格式应该是:[").append(pattern+"]");
								String err = getErrorMsg(fieldValue, errMsg.toString(), rowNum);
								throw new ExcelException(err);
							}
							if(date==null){
								StringBuilder errMsg = new StringBuilder("[");
								errMsg.append(value.toString()).append("]")
								.append("不能转换成日期,正确的格式应该是:[").append(pattern+"]");
								String err = getErrorMsg(fieldValue, errMsg.toString(), rowNum);
								throw new ExcelException(err);
							}
							return date;
						} else if (value instanceof Date) {
							return value;
						} else if(value instanceof Number){
							Number val = (Number) value;
							return new Date(val.longValue());
						} else {
							throw new ExcelException(getErrorMsg(fieldValue, "数据格式错误,[ " + name + " ]的类型是:" + value.getClass()+",无法转换成日期。", rowNum));
						}
					}
				} else if (dcode) {
					String typename = "";
					if("profession".equals(name)) {
						typename = "profession";
					}
					if("ascName".equals(name)) {
						typename = "expertIntroduce";
					}
					if("ascNature".equals(name)) {
						typename = "ascType";
					}
					if("sender".equals(name)) {
						typename = "expertSender";
					}
					int lenOfVal = value.toString().length();
					int lenOfStr = 0;
					List<String> result = new ArrayList();
					if("".equals(typename)){
						result = queryRiskDcodeListStr(typename);
					}
					Boolean b = true;
					lenOfStr = Integer.valueOf(result.get(1));
					String check = result.get(0);
					if("profession".equals(name)){
						String[] progessions = StringUtils.split((String)value,",");
						for(String profession:progessions){
							b = b && (check.indexOf(profession) >= 0);
						}
						if(progessions.length > 0){
							List<String> list = this.removeSameIdsFromList(progessions);
							if(progessions.length>list.size()){
								throw new ExcelException(getErrorMsg(fieldValue, "字段存在重复数据,请校验后重新操作。", rowNum));
							}
						}else {
							return value;
						}
						if(b){
							return value;
						} else {
							throw new ExcelException(getErrorMsg(fieldValue, "字段输入编码不是标准类型,请参照模板文件的基本代码表说明填写该字段所允许的参数，如需要输入多个参数，请用英文逗号分隔。", rowNum));
						}
					} else{
						if(lenOfStr == lenOfVal){
							b = check.indexOf((String)value) >= 0;
							if(b){
								return value;
							} else {
								throw new ExcelException(getErrorMsg(fieldValue, "字段输入编码不是标准类型,请参照模板文件的基本代码表说明填写该字段所允许的参数。", rowNum));
							}
						} else {
							throw new ExcelException(getErrorMsg(fieldValue, "字段输入编码位数不正确,长度应该是由" + lenOfStr+"个字符构成,详情参照模板文件的基本代码表说明。", rowNum));
						}
					}
				} else if (isRiskName) {
					String[] arr = value.toString().split(",");
					String check = "Q,G,JS,C,Z,J,H,9";
					Boolean result = true;
					Boolean res = true;
					for(String s:arr){
						if("S".equals(s)){
							result = false;
						}
						res = (check.indexOf(s) >= 0);
						result = result && res;
					}
					if(null!=arr&&arr.length>0){
						List<String> list = this.removeSameIdsFromList(arr);
						if(arr.length>list.size()){
							throw new ExcelException(getErrorMsg(fieldValue, "字段存在重复数据,请校验后重新操作。", rowNum));
						}
					}else {
						return value;
					}
					if(result){
						return value;
					}else{
						throw new ExcelException(getErrorMsg(fieldValue, "字段输入编码不是标准类型,请参照模板文件的基本代码表说明填写该字段所允许的参数，如有多个选项用英文逗号隔开。", rowNum));
					}
				} else if (isEmail) {
			    	Boolean flag = false;
		            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
		            Pattern regex = Pattern.compile(check);  
		            Matcher matcher = regex.matcher(value.toString());  
		            flag = matcher.matches();  
		            if(flag){
		            	return value;
		            }else{
//			            System.out.println("邮箱格式错误》》》》》》》》》》》》》》》》》》》》》》》》》》");
		            	throw new ExcelException(getErrorMsg(fieldValue, "字段格式错误,请输入标准格式的邮箱。", rowNum));
		            } 
				}else if(isNumberFlag){
					Boolean flag = false;
					String check = "";
					int num = 0;
					if("floodDepth".equals(fieldName)){
						decimalNumber = "2"; 
					}
					if(StringUtils.isNotBlank(decimalNumber)){
						num = Integer.valueOf(decimalNumber);
						check = "([1-9]\\d*|0)(\\.\\d{0,"+num+"})?";
					} else {
					   check = "(^-?[1-9]\\d*|0)(\\.\\d+)?";
					}
					
//		            String check = "^-?\\d+(\\.\\d+)?$"; 
		            Pattern regex = Pattern.compile(check);  
		            Matcher matcher = regex.matcher(value.toString());  
		            flag = matcher.matches();  
		            if(flag){
		            	double lonOrLat = Double.parseDouble(value.toString());
		            	if ("lon".equals(fieldName)){
		            		if (lonOrLat < 72.004 || lonOrLat > 137.8347){
		            			throw new ExcelException(getErrorMsg(fieldValue, "字段输入值错误,请输入72.004~137.8347范围内数据。", rowNum));
		            		}
		            	}else if("lat".equals(fieldName)){
		            		if (lonOrLat < 0.8293 || lonOrLat > 55.8271){
		            			throw new ExcelException(getErrorMsg(fieldValue, "字段输入值错误,请输入0.8293~55.8271范围内数据。", rowNum));
		            		}
		            	}
		            	return value;
		            }else{
		            	if(StringUtils.isNotBlank(decimalNumber)){
		            		throw new ExcelException(getErrorMsg(fieldValue, "字段格式或位数错误,请输入"+num+"位标准数字格式。", rowNum));
		            	}else{
		            		throw new ExcelException(getErrorMsg(fieldValue, "字段格式或位数错误,请输入标准数字格式。", rowNum));
		            	}		            	
		            } 
				} else if (isPhone) { 
					//验证移动号码
			    	Boolean mobileFlag = false;
			    	Pattern mobielRegex = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89]|0[0-9][0-9])?\\d{8}$");    
		            Matcher mobileMatcher = mobielRegex.matcher(value.toString());  
		            mobileFlag = mobileMatcher.matches();  
		            //验证座机号码
		            boolean phoneFlag = false;
		            Pattern phoneRegex=Pattern.compile("^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$");
		            Matcher phoneMatcher=phoneRegex.matcher(value.toString());
		            phoneFlag = phoneMatcher.matches();

		            if(mobileFlag || phoneFlag){
		            	return value;
		            }else{
//			            System.out.println("电话格式错误》》》》》》》》》》》》》》》》》》》》》》》》》》");
		            	throw new ExcelException(getErrorMsg(fieldValue, "字段格式错误,请输入标准格式的11位手机或8位座机电话号码。", rowNum));
		            } 
				} else if (format != null) {
					return resolverExpression(value.toString(), format, type,fieldValue,rowNum);
				} else if (decimalFormat!=null) {
					if (Type.IMPORT == type) {
						if(value instanceof String){
							return decimalFormat.parse(value.toString());
						}
					}else if(Type.EXPORT == type){
						if(value instanceof String){
							value = ConvertUtils.convert(value, BigDecimal.class);
						}
						return decimalFormat.format(value);
					}
				} else {
					return value;
				}
			}else{
				//自定义
				CellValueConverter conv = cellValueConverters.get(convName);
				if(conv == null){
					synchronized(this){
						if(conv == null){
							conv = getBean(convName);
							cellValueConverters.put(convName, conv);
						}
					}
					conv = cellValueConverters.get(convName);
				}
				value = conv.convert(bean,value, fieldValue, type, rowNum);
				return value;
			}
		}
		return fieldValue.getDefaultValue();

	}
	// 去除重复元素 
	public List<String> removeSameIdsFromList(String [] ids) { 
        List<String> result = new ArrayList<String>(); 
        if(null!=ids&&ids.length>0){
        	for(int i =0;i<ids.length;i++){
        		if(!result.contains(ids[i])) {    
        			result.add(ids[i]);    
                }  
        	}
        }
        return result;  
	 }  
	
//	获取数据库中对应所有codecode字段数组
	public List<String> queryRiskDcodeListStr(String codeType) {
		List<RiskDcode> riskDcodeList = new ArrayList<RiskDcode>();
		//通过上下文获取databaseDao
		String strOfCodecode = "";
		List<String> list = new ArrayList<String>();
		List<String> result =  new ArrayList<String>();
		int lenOfCodecode = 0;
		try {
//			DatabaseDao databaseDao = (DatabaseDao)ApplicationContextUtil.getApplicationContext().getBean("databaseDo");
//			QueryRule queryRule = QueryRule.getInstance();
//			queryRule.addEqual("id.codeType", codeType);
//			riskDcodeList = databaseDao.findAll(RiskDcode.class, queryRule);
			Criteria<RiskDcode> criteriaList = new Criteria<>();
			criteriaList.add(Restrictions.eq("id.codeType", codeType));
			riskDcodeList = riskDcodeRepository.findAll(criteriaList);
			String codeCode = "";
			for(RiskDcode riskdcode:riskDcodeList){
				if(riskdcode.getId().getCodeCode() != null){
					codeCode = riskdcode.getId().getCodeCode();
					list.add(codeCode);
				}
			}
			lenOfCodecode = codeCode.length();
			strOfCodecode = list.toString().substring(1, list.toString().length()-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.add(strOfCodecode);
		result.add(lenOfCodecode + "");
		return result;
	}
	
	//获取bean
	private CellValueConverter getBean(String convName) throws ClassNotFoundException {
		CellValueConverter bean = null;
		if(SpringUtil.isInited()){
			bean = (CellValueConverter) SpringUtil.getBean(Class.forName(convName));
		}else{
			bean =  (CellValueConverter) ReflectUtil.newInstance(Class.forName(convName));
		}
		return bean;
	}


	/**
	 * 获取错误消息
	 * @param fieldValue
	 * @param errMsg 消息提示内容
	 * @param rowNum
	 * @return
	 */
	protected String getErrorMsg(FieldValue fieldValue,String errMsg,int rowNum){
		StringBuilder err = new StringBuilder();
		err.append("[")
		.append(fieldValue.getTitle()).append("]").append(errMsg);
		return err.toString();
	}
	
}
