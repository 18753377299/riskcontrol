package com.picc.riskctrl.common.utils;

/**
 * @功能：接口静态常量类 <p>
 *             主要包括：各接口静态常量
 *             </p>
 * @作者：chenlei
 * @日期：2017-11-15
 * @修改记录：
 */
public class CommonConst {

	public static final String RISKCHECKMAIN = "riskcheck_main";
	
	/** 文本：text/html; charset=GBK */
	public static final String CONTENT_TYPE = "text/html; charset=GBK";
	/** 文本：... */
	public static final String TEXT_XML_EXT = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	public static final String TEXT_XML_EXT_UTF = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static final String TEXT_XML = "<?xml version=\"1.0\" encoding=\"GBK\"?><PACKET><BODY>";
	public static final String TEXT_PACKET_REQ = "<PACKET type=\"REQUEST\" version=\"1.0\">";
	public static final String TEXT_PACKET_RES = "<PACKET type=\"RESPONSE\" version=\"1.0\">";
	public static final String TEXT_PACKET_BEG = "<PACKET><BODY>";
	public static final String TEXT_PACKET_END = "</BODY></PACKET>";
	public static final String TEXT_PACKET = "<PACKET>";
	public static final String TEXT_PACK = "</PACKET>";
	public static final String TEXT_BODY_GEG = "<BODY>";
	public static final String TEXT_BODY_END = "</BODY>";
	public static final String TEXT_PACKET_RSP = "<RSPPacket type=\"RESPONSE\" version=\"1.0\">";
	public static final String TEXT_RSPPacket = "<RSPPacket>";
	public static final String TEXT_RSP = "</RSPPacket>";
	
	
	
	/** 文本：<ResponseVo> */
	public static final String TEXT_RESPONSEVO_BEG = "<ResponseVo>";
	/** 文本：</ResponseVo> */
	public static final String TEXT_RESPONSEVO_END = "</ResponseVo>";
	/** 文本：<RequestVo> */
	public static final String TEXT_REQUESTVO_BEG = "<RequestVo>";
	/** 文本：</ResponseVo> */
	public static final String TEXT_REQUESTVO_END = "</RequestVo>";
	/** 文本：<RequestVo/> */
	public static final String TEXT_REQUESTVO = "<RequestVo/>";
	/** 文本：<ResponseVo/> */
	public static final String TEXT_RESPONSEVO = "<ResponseVo/>";
	/** 文本：GBK */
	public static final String TEXT_GBK = "GBK";
	public static final String ENCODING_UTF8 = "UTF-8";
	/** 文本：（空） */
	public static final String TEXT_EMPTY = "";
	/** 文本：[CommServer: */
	public static final String TEXT_INFO_01 = "[CommServer:";
	/** 文本：] */
	public static final String TEXT_INFO_02 = "]";
	/** 文本： IP: */
	public static final String TEXT_INFO_03 = " Begin - IP: ";
	/** 文本： URI: */
	public static final String TEXT_INFO_04 = "; URI: ";
	/** 文本： IN: */
	public static final String TEXT_INFO_05 = " Info  - Input: ";
	/** 文本： OUT: */
	public static final String TEXT_INFO_06 = " Info  - Output: ";
	/** 文本： OUT: */
	public static final String TEXT_INFO_07 = " End   - Seconds: ";
	/** 文本： ... */
	public static final String TEXT_ERRPR_01 = "<?xml version=\"1.0\" encoding=\"GB2312\"?><PACKET type=\"RESPONSE\" version=\"1.0\" ><HEAD><REQUEST_TYPE>";
	/** 文本： ... */
	public static final String TEXT_ERRPR_02 = "</REQUEST_TYPE><RESPONSE_CODE>0</RESPONSE_CODE><ERROR_CODE>";
	/** 文本： ... */
	public static final String TEXT_ERRPR_03 = "</ERROR_CODE><ERROR_MESSAGE>";
	/** 文本： ... */
	public static final String TEXT_ERRPR_04 = "</ERROR_MESSAGE></HEAD></PACKET>";

	/** 客户管理系统 */
	public static final String CIF = "客户管理系统";
	/** 收付费平台系统 */
	public static final String SFFPLAT = "收付费平台系统";
	/** 核保管理系统 */
	public static final String UNDWRT = "核保管理系统";
	/** 单证系统 */
	public static final String VISA = "单证系统";
	/** 异常 */
	public static final String ERR = "异常:";

	public static final String SALE_CROSS = "1";// 1 : 叉销售销售员（健康险或寿险销售员）
	public static final String USER_ALL = "2";// 2：财险经办人、操作员（含10位编码的操作员，8位编码的销售员）
	/** 总公司机构代码 */
	public static final String COMCODE_ALL = "00000000";
	/** IMS中getSalesMsg()接口中，参数有效状态【0:查询全部1:不查询无效(默认)】; 是否包含下级机构【0:不包含(默认),1:包含】 */
	public static final String VALIDSTATUS_GETSALESMSG_ALL = "0";
	public static final String VALIDSTATUS_GETSALESMSG_VALID = "1";
	public static final String XIACOMCODE_GETSALESMSG_INCLUDE = "1";
	public static final String XIACOMCODE_GETSALESMSG_NOINCLUDE = "0";

	// 缓存key常用变量
	public static final String SYSTEM_IMS = "ims";
	public static final String SYSTEM_DMS = "dms";
	public static final String SYSTEM_LOCAL = "local";
	public static final String SYSTEM_PRPINS = "prpins";
	public static final String SYSTEM_CIF = "cif";
	public static final String SYSTEM_IDS = "ids";
	// 方法类型 push:推送 get：获取
	public static final String METHOD_PUSH = "push";
	public static final String METHOD_GET = "get";

	// 操作类型
	public static final String RC_T_CREATE = "RC00"; // 生成暂存单
	public static final String RC_T_SAVE = "RC01"; // 暂存
	public static final String RC_UPDATE = "RC02"; // 修改
	public static final String RC_SAVE = "RC03"; // 保存
	public static final String RC_SUBMIT = "RC04"; // 提交审核
	public static final String RC_UNDERWRITE = "RC05"; // 审核
	public static final String RC_DELETE = "RC06"; // 删除风控单
	public static final String RC_REPULSE = "RC07"; // 打回
	public static final String RC_UNDERWRITEFIR = "RC08"; // 一级审核通过
	
	
	// 汛期报告操作类型
	public static final String RX_T_CREATE = "RC00"; // 生成暂存单
	public static final String RX_T_SAVE = "RC01"; // 暂存
	public static final String RX_UPDATE = "RC02"; // 修改
	public static final String RX_SAVE = "RC03"; // 保存
	public static final String RX_DELETE = "RC06"; // 删除风控单
}
