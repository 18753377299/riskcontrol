package com.picc.riskctrl.common;

/**
 * @功能：风险控制系统静态常量类
 * @作者：
 * @日期：
 */
public class RiskControlConst {
	/** 风控系统代码  */
	public static final String SYSTEMCODE = "riskcontrol";


	/** 灾因 */
	public static final String TYPE_SUM = "01,02,03,04,05,06,07,08";	

	
	/** 巡检报告灾因 */
	public static final String TYPE_SUM_CHECK = "01,02,03,04,05,06";	
	
	/** 险类代码 基本险 */
	public static final String JB  = "JB";
	/** 险类代码 综合险 */
	public static final String ZH  = "ZH";
	/** 险类代码 一切险 */
	public static final String YQ  = "YQ";
	/** 险类代码 机损险 */
	public static final String JS  = "JS";
	/** 险类代码 中断险 */
	public static final String ZD  = "ZD";
	/** 险类代码 其他险 */
	public static final String QT  = "QT";
	
	/** 险类名称 基本险 */
	public static final String JB_CNAME  = "企财险（基本险）";
	/** 险类名称 综合险 */
	public static final String ZH_CNAME  = "企财险（综合险）";
	/** 险类名称 一切险 */
	public static final String YQ_CNAME  = "企财险（一切险）";
	/** 险类名称 机损险 */
	public static final String JS_CNAME  = "企财险（机器损坏险）";
	/** 险类名称 中断险 */
	public static final String ZD_CNAME  = "企财险（营业中断险）";
	/** 险类名称 其他险 */
	public static final String QT_CNAME  = "企财险（其他险）";

	/** 险种代码 基本险 */
	public static final String JBX  = "JBX";
	/** 险种代码 综合险 */
	public static final String ZHX  = "ZHX";
	/** 险种代码 一切险 */
	public static final String YQX  = "YQX";
	
	/** 险种名称 基本险 */
	public static final String JBX_CNAME  = "基本险";
	/** 险种名称 综合险 */
	public static final String ZHX_CNAME  = "综合险";
	/** 险种名称 一切险 */
	public static final String YQX_CNAME  = "一切险";
	
	
	/** 文本：GBK */
	public static final String TEXT_GBK = "GBK";
	public static final String ENCODING_UTF8 = "UTF-8";
	
	/** jbpm流程name和to名称 */
	public static final String CITY_DECISION = "to 判断一级是否审核通过";
	public static final String CITY_TASK = "to 一级审核";
	
	public static final String PROVICE_DECISION = "to 判断二级是否审核通过";
	public static final String PROVICE_TASK = "to 二级审核";
	
	public static final String BUSINESS_DECISION = "to 判断是否自动审核通过";
	public static final String BUSINESS_TASK = "to 业务员提交申请";
	
	public static final String PASS_TASK = "to 审核通过";
	public static final String END_TASK = "to 流程结束";
	public static final String CHECK_PADING="to 待审核";
	
	public static final String NUM_REVIEWED="to 判断几级审核";
	
	/** 汛期报告mq消息标识*/
	public static final String MQ_RISKCHECKFLAG = "riskcheck";
	/** 标准汛期报告影像模板标识*/
	public static final String IAMGE_RISKCHECK_STANDARD = "riskCheckFree001";
	/** 简化汛期报告影响模板标识*/
	public static final String IMAGE_RISKCHECK_SIMPLE = "riskCheckFree002";
	
	/** 火灾风险排查（专业版）影像模板标识*/
	public static final String IAMGE_RISKREPORT_PROFESSION = "riskReportFireTree001";
	/** 火灾风险排查（简化版）影响模板标识*/
	public static final String IMAGE_RISKREPORT_SIMPLE = "riskReportFireTree002";
	/** 全景式风控系统mq消息标识*/
	public static final String IMAGE_RISKFUN="riskFun";
	

}
