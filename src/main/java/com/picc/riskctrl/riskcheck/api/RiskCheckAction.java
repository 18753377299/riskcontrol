package com.picc.riskctrl.riskcheck.api;

import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.service.BillService;
import com.picc.riskctrl.common.service.RiskMaxNoService;
import com.picc.riskctrl.common.service.RiskTimeService;
import com.picc.riskctrl.common.utils.CommonConst;
import com.picc.riskctrl.common.utils.FTPUtil;
import com.picc.riskctrl.dataquery.util.CorporateRadarQueryRule;
import com.picc.riskctrl.riskcheck.po.RiskCheckMain;
import com.picc.riskctrl.riskcheck.service.RiskCheckService;
import com.picc.riskctrl.riskcheck.vo.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pdfc.framework.web.ApiResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/riskcheck")
public class RiskCheckAction  extends BaseController{
	@Autowired
	RiskCheckService riskCheckService;
	@Autowired
	private BillService billService;
	@Autowired
	private RiskMaxNoService riskMaxNoService;
	@Autowired
	private RiskTimeService riskTimeService;
	
	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");
	
	@ApiOperation(value="根据巡检编号查询详细信息",notes="modifyby 周东旭  20200114")
	@RequestMapping(value = "/queryRiskCheckByCheckNo", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ApiResponse<RiskCheckMainVo> queryRiskCheckByCheckNo(@RequestParam(value = "riskCheckNo") String riskCheckNo)throws Exception {
		ApiResponse<RiskCheckMainVo> apiResponse = new ApiResponse<RiskCheckMainVo>();
		UserInfo userInfo = getUserInfo();

		if(StringUtils.isNotBlank(riskCheckNo)) {
			RiskCheckMainVo riskCheckMainVo =null;
			riskCheckMainVo = riskCheckService.queryRiskCheckByCheckNo(riskCheckNo);
			String checkModel=riskCheckNo.substring(2, 5);
			if ("001".equals(checkModel) || "002".equals(checkModel)) {
				List<RiskDcode> riskDcodeList=riskCheckService.queryRiskDcodeByCheckModel(checkModel);
				String contextPath = getRequest().getContextPath();
				if(riskDcodeList!=null) {
					riskDcodeList = riskCheckService.queryRiskCheckImage(riskDcodeList,riskCheckNo,contextPath);
				}
				riskCheckMainVo.setRiskDcodeList(riskDcodeList);
			}
//			// 国民经济类型中文翻译
//			if (StringUtils.isNotBlank(riskCheckMainVo.getBusinessSource())) {
//				riskCheckMainVo.setBusinessSourceCName(riskInsService.queryCodeCName("TradeCode",
//				RiskCheckMain.getBusinessSource(), userCode, comCode, "QBB"));
//			}
//			// 查勘人中文翻译
//			if (StringUtils.isNotBlank(riskCheckMainVo.getChecker())) {
//				riskCheckMainVo.setCheckerName(riskInsService.queryCodeCName("UserCode", riskCheckMainVo.getChecker(),
//				userCode, comCode, riskCode));
//			}
//			// 归属机构中文翻译
//			if (StringUtils.isNotBlank(riskCheckMainVo.getCheckComCode())) {
//				riskCheckMainVo.setCheckComCodeCName(dataSourcesService.queryComCodeCName(riskCheckMainVo.getCheckComCode()));
//			}
			apiResponse.setData(riskCheckMainVo);
			apiResponse.setStatus(1);
			apiResponse.setStatusText("success");
		}else {
			apiResponse.setStatus(0);
			apiResponse.setStatusText("巡检报告无数据，请重新录入!");
		}
		return apiResponse;
	}
	
	@ApiOperation(value="暂存巡检报告",notes="modifyby 周东旭  20200115")
	@RequestMapping(value = "/temporarySave", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ApiResponse temporarySave(@RequestBody RiskCheckRequestVo riskCheckRequestVo) throws Exception {
		ApiResponse api = new ApiResponse();
		if(riskCheckRequestVo.getRiskCheckMainVo() !=null) {
//			String client = request.getHeader("user-agent");
			// 移动端请求处理
//			if (client.contains("Android") || client.contains("iPad")) {
//				riskCheckRequestVo.getRiskCheckMainVo().setMobileFlag("1");
//			}else if(client.contains("iPhone")){
//				riskCheckRequestVo.getRiskCheckMainVo().setMobileFlag("2");
//			}else{
				riskCheckRequestVo.getRiskCheckMainVo().setMobileFlag("0");
//			}
			// 获取用户信息
			UserInfo userInfo = getUserInfo();
			String checkModel = riskCheckRequestVo.getRiskCheckMainVo().getCheckModel();
			if ("001".equals(checkModel) || "002".equals(checkModel)) {
				//获取图片资料
				List<RiskCheckImageVo> riskCheckImages = riskCheckService.getRiskCheckImageList(riskCheckRequestVo.getRiskCheckMainVo().getRiskDcodeList(),riskCheckRequestVo.getRiskCheckMainVo().getRiskCheckNo());
				// 保存图片资料
				riskCheckRequestVo.setRiskCheckImageList(riskCheckImages);
			}
			api.setStatus(riskCheckService.temporarySave(userInfo,riskCheckRequestVo));
			// 设置时间轨迹
			riskTimeService.insertRiskTime(riskCheckRequestVo.getRiskCheckMainVo().getRiskCheckNo(), userInfo.getUserCode(), CommonConst.RX_T_SAVE);

		}else {
			api.setStatus(1);
			api.setStatusText("巡检报告无数据，请重新录入!");
		}
		return api;
	}

	 /**
	 * @功能 汛期检查查询
	 * @author 孙凯
	 * @param riskCheckRequestVo
	 * @return riskCheckResponseVo
	 * @日期 2019-03-13
	 * @修改 
	 */
	@ApiOperation(value="汛期检查查询",notes="addby 孙凯 20200107")
	@RequestMapping(value = "/queryMain", method=RequestMethod.POST)
	@ResponseBody
	public ApiResponse<RiskCheckResponseVo> checkQuery(@RequestBody RiskCheckRequestVo riskCheckRequestVo) {
		// 用户信息
		UserInfo userInfo = getUserInfo();
		return ApiResponse.ok(riskCheckService.checkQuery(riskCheckRequestVo, userInfo));
	 }
	/**
	 * @功能 汛期报告删除
	 * @author 周东旭
	 * @return riskCheckResponseVo
	 * @日期 2019-03-13
	 * @修改 
	 */
	@ApiOperation(value="汛期报告删除",notes="addby 周东旭 20200116")
	@RequestMapping(value = "/riskCheckDelete", method=RequestMethod.POST)
	@ResponseBody
	public ApiResponse<RiskCheckResponseVo> riskCheckDelete(@RequestBody RiskCheckMainQueryVo riskCheckMainQueryVo) {
		UserInfo userInfo = getUserInfo();
		
		RiskCheckResponseVo riskCheckResponseVo = new RiskCheckResponseVo();
			
		String message = null;
		List<String> riskCheckNoList = riskCheckMainQueryVo.getRiskCheckNoList();
			
		try {
			for (String riskCheckNo : riskCheckNoList) {
				message = riskCheckService.deleteRiskInfoByRiskCheckNo(riskCheckNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "error";
		}
//		 删除影像资料
//		message = riskInsImageService.deleteImageList(riskCheckMainQueryVo.getRiskCheckNoList(), userInfo);
		// 删除本地影像
//		riskInsImageService.deleteRiskFile(riskCheckMainQueryVo.getRiskCheckNoList());
		for (String riskFileNoTem : (List<String>) riskCheckMainQueryVo.getRiskCheckNoList()) {
			riskTimeService.insertRiskTime(riskFileNoTem, userInfo.getUserCode(), CommonConst.RX_DELETE);
		}
		riskCheckResponseVo.setMessage(message);
		return ApiResponse.ok(riskCheckResponseVo) ;
	 }
	
	/**
	 * 汛期报告查询结果导出excel文件
	 * @author 孙凯
	 * @param request
	 * @param riskCheckRequestVo
	 * @return
	 */
	@ApiOperation(value="汛期报告查询结果导出excel文件",notes="queryby 孙凯 20200115")
	@RequestMapping(value = "/exportRiskinsMainReport", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Object> exportRiskinsMainReport(@RequestBody RiskCheckRequestVo riskCheckRequestVo) {
		ApiResponse<Object> ajaxResult = new ApiResponse<Object>();

		UserInfo userInfo = getUserInfo();
		
		//创建HSSFWorkbook对象 (excel的文档对象)
		HSSFWorkbook wkb = new HSSFWorkbook();
		//建立新的sheet对象 （excel的表单）
		HSSFSheet sheet= wkb.createSheet("巡检报告查询结果清单");
		//在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
		HSSFRow row1= sheet.createRow(0);
		//创建单元格 （excel的单元格，参数为列索引，可以是0～255之间的任何一个
		HSSFCell cell= row1.createCell(0);
		//设置内容居中
		HSSFCellStyle style = wkb.createCellStyle();
		//替换poi版本3.1.1为3.16，修改调用居中为HorizontalAlignment.CENTER add by wangwenjie 2019/7/22
//		style.setAlignment(HorizontalAlignment.CENTER);
	    style.setAlignment(HorizontalAlignment.CENTER);
	    //设置字体
        HSSFFont font = wkb.createFont();
        //设置字体的大小
        font.setFontHeightInPoints((short)11);
        //设置字体加粗
		//字体加粗
		//替换poi版本3.1.1为3.16，修改调用加粗为font.setBold(true) add by wangwenjie 2019/7/22
		font.setBold(true);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //在样式用应用设置的字体;  
         style.setFont(font);
		//设置单元格的内容
		cell.setCellValue("巡检报告查询结果清单");
		cell.setCellStyle(style);
		//创建单元格并设置单元格内容
		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		//替换poi版本3.1.1为3.16,修改CellRangeAddress 为org.apache.poi.ss.util.CellRangeAddress add by wangwenjie 2019/7/22
		sheet.addMergedRegion(new CellRangeAddress(0,1,0,8));
		//在sheet里创建第二行
		HSSFRow row2= sheet.createRow(2);
		HSSFCell cell0= row2.createCell(0);
		cell0.setCellValue("巡检编号");
		sheet.setColumnWidth(cell0.getColumnIndex(), 256 * 30);
		
		HSSFCell cell1= row2.createCell(1);
		cell1.setCellValue("被保险人");
		sheet.setColumnWidth(cell1.getColumnIndex(), 256 * 20);
		
		HSSFCell cell2= row2.createCell(2);
		cell2.setCellValue("巡检人");
		sheet.setColumnWidth(cell2.getColumnIndex(), 256 * 20);
		
		HSSFCell cell3= row2.createCell(3);
		cell3.setCellValue("归属机构");
		sheet.setColumnWidth(cell3.getColumnIndex(), 256 * 20);
		
		HSSFCell cell4= row2.createCell(4);
		cell4.setCellValue("巡检机构");
		sheet.setColumnWidth(cell4.getColumnIndex(), 256 * 20);
		
		HSSFCell cell5= row2.createCell(5);
		cell5.setCellValue("巡检日期");
		sheet.setColumnWidth(cell5.getColumnIndex(), 256 * 20);
		
		HSSFCell cell6= row2.createCell(6);
		cell6.setCellValue("巡检地址");
		sheet.setColumnWidth(cell6.getColumnIndex(), 256 * 40);
		
		HSSFCell cell7= row2.createCell(7);
		cell7.setCellValue("审核状态");
		sheet.setColumnWidth(cell7.getColumnIndex(), 256 * 20);
		
		HSSFCell cell8= row2.createCell(8);
		cell8.setCellValue("来源");
		sheet.setColumnWidth(cell8.getColumnIndex(), 256 * 20);
		riskCheckRequestVo.setPageNo(1);
		riskCheckRequestVo.setPageSize(2000);
		RiskCheckResponseVo riskCheckResponseVo = riskCheckService.checkQuery(riskCheckRequestVo, userInfo);
		List<RiskCheckMainResponseVo> riskCheckMainList = riskCheckResponseVo.getDataList();
		if(riskCheckMainList.size()>0) {
			//最多只导出2000条
			Integer exportDataLength;
			if(riskCheckMainList.size()>2000) {
				exportDataLength = 2000;
			}else {
				exportDataLength = riskCheckMainList.size();
			}
			for (int i=0; i<exportDataLength; i++) {
				//创建新的行
				HSSFRow row=sheet.createRow(i+3);
				//创建每行对应的列
				row.createCell(0).setCellValue(riskCheckMainList.get(i).getRiskCheckNo());//巡检编号
				row.createCell(1).setCellValue(riskCheckMainList.get(i).getInsuredName());//被保险人
				row.createCell(2).setCellValue(riskCheckMainList.get(i).getChecker());//巡检人
				row.createCell(3).setCellValue(riskCheckMainList.get(i).getComCode());//归属机构
				row.createCell(4).setCellValue(riskCheckMainList.get(i).getCheckComCode());//巡检机构
				HSSFCell create5 = row.createCell(5);
				if(riskCheckMainList.get(i).getCheckDate()!=null) {
					create5.setCellValue(riskCheckMainList.get(i).getCheckDate().toString().substring(0, 10));//巡检日期
				}
				row.createCell(6).setCellValue(riskCheckMainList.get(i).getAddressDetail());//巡检地址
				row.createCell(7).setCellValue(riskCheckMainList.get(i).getUnderwriteFlag());//审核状态
				//判断来源状态   进行中文翻译
				String mobileFlagText = null;
				switch(riskCheckMainList.get(i).getMobileFlag()) {
					case "0":mobileFlagText = "PC";
							break;
					case "1":mobileFlagText = "Android";
							break;
					case "2":mobileFlagText = "iOS";
							break;
					default:mobileFlagText = "未说明";
				}
				row.createCell(8).setCellValue(mobileFlagText);//来源
			}
			OutputStream output = null;
			FTPUtil ftp = new FTPUtil();
			//生成Excel表格文档
			try {
				output =ftp.uploadFile("downloadExcel/riskCheckMainList.xls");	
				//将文件路径url地址返回页面
				ajaxResult.setData("/downloadExcel/riskCheckMainList.xls");
				ajaxResult.setStatusText("success");
				wkb.write(output);
				output.flush();
			}catch(Exception e) {
				LOGGER.info( e.getMessage() ,e);
				e.printStackTrace();
			}finally {
				if (output != null) {
					try {
						output.close();
					} catch (Exception e2) {
						LOGGER.error(e2.getMessage(), e2);
					}
				}
				if (ftp != null) {
					try {
						ftp.close();
					} catch (IOException e) {
						LOGGER.info("关闭ftp异常：" + e.getMessage(), e);
					}
				}
			}
		}else {
			//当前查询条件未查询到相应数据 文件返回路径返回null
			ajaxResult.setData(null);
		}
		return ajaxResult;
	}
	/**
	 * @功能 巡检编号查询巡检报告审核标志位
	 * @param riskCheckNo
	 * @return riskCheckResponseVo
	 * @author 孙凯
	 * @throws Exception 
	 * @日期 2019-03-18
	 * @修改记录
	 */
	@ApiOperation(value="巡检编号查询巡检报告审核标志位",notes="queryby 孙凯 20200115")
	@RequestMapping(value = "/queryCheckNo", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse<RiskCheckResponseVo> queryCheckNo(@RequestParam("riskCheckNo") String riskCheckNo) throws Exception {
		RiskCheckResponseVo riskCheckResponseVo = new RiskCheckResponseVo();
		RiskCheckMain riskCheckMain = riskCheckService.queryRiskCheckNo(riskCheckNo);
		riskCheckResponseVo.setMessage(riskCheckMain.getUnderwriteFlag());
		return ApiResponse.ok(riskCheckResponseVo);
	}
	/**
	 * @功能 根据经纬度获取暴雨分值
	 * @author 孙凯
	 * @param pointX
	 * @param pointY
	 * @return AjaxResult
	 * @Date 2019-04-26
	 */
	@ApiOperation(value="根据经纬度获取暴雨分值",notes="queryby 孙凯 20200116")
	@RequestMapping(value = "/getRainScoreByPoint",method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse<Object> getRainScoreByPoint(String pointX,String pointY) throws Exception {

		ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		if(StringUtils.isNotBlank(pointX) && StringUtils.isNotBlank(pointY)){
			apiResponse.setData(riskCheckService.getRainScoreByPoint(pointX, pointY));
		}else {
			apiResponse.setStatus(-1);
			apiResponse.setStatusText("请传入经纬度数据!");
		}
		return apiResponse;
	}
	/**
	 * 巡检任务派发批量删除
	 * @author 
	 * @param request
	 * @param riskCheckMainQueryVo
	 * @return AjaxResult
	 * @Date 2019/5/10
	 */
	@ApiOperation(value="巡检任务派发批量删除",notes="deleteby 孙凯 20200117")
	@RequestMapping(value = "/deleteCheckMainLog",method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Object> deleteCheckMainLog(@RequestBody RiskCheckMainQueryVo riskCheckMainQueryVo) {
		ApiResponse<Object> apiResponse = new ApiResponse<Object>();
		List<Integer> logIdList = riskCheckMainQueryVo.getRiskCheckLogIdList();
		try{
			for (Integer logId : logIdList) {
				String message = riskCheckService.deleteRiskCheckLogById(logId);
			}
			apiResponse.setStatusText("success");
		}catch(Exception e) {
			apiResponse.setStatusText("error");
			e.printStackTrace();
		}
		return apiResponse;
	}
	
	/**
	 * @功能:暂存巡检报告基本信息页面并生成单号以及查询企业失信信息
	 * @param request
	 * @param riskCheckRequestVo
	 * @return ApiResponse<>
	 * @throws Exception
	 * @作者：崔凤志
	 * @日期：2020-01-14
	 */
	@ApiOperation(value="暂存巡检报告基本信息页面并生成单号",notes="addby 崔凤志 20200116")
	@RequestMapping(value = "/saveBasicInfo", method = RequestMethod.POST)
	public ApiResponse<RiskCheckMainVo> saveBasicInfo(@RequestBody RiskCheckRequestVo riskCheckRequestVo) throws Exception {
		
		ApiResponse<RiskCheckMainVo> response = new ApiResponse<RiskCheckMainVo>();
		
		if(riskCheckRequestVo.getRiskCheckMainVo() !=null) {
			
			// 获取用户信息
			UserInfo userInfo = getUserInfo();
			if (userInfo.getIsPC()) {
				riskCheckRequestVo.getRiskCheckMainVo().setMobileFlag("0");
			} else {
				String client = getHeader("user-agent");
				if (client.contains("Android") || client.contains("iPad")) {
					riskCheckRequestVo.getRiskCheckMainVo().setMobileFlag("1");
				}else if(client.contains("iPhone")) {
					riskCheckRequestVo.getRiskCheckMainVo().setMobileFlag("2");
				}
			}
			
			// 年份
			int year = Calendar.getInstance().get(Calendar.YEAR);
			
			// 获取单号
			//String riskCheckNo = billService.getNo("RiskCheckMain", "riskCheckNo", "",userInfo.getComCode(), year, "", riskCheckRequestVo.getRiskCheckMainVo().getCheckModel());
			
			String riskCheckNo = billService.getNo("RiskCheckMain", "riskCheckNo", "",userInfo.getComCode(), year, "", riskCheckRequestVo.getRiskCheckMainVo().getCheckModel());
			
			riskCheckRequestVo.getRiskCheckMainVo().setRiskCheckNo(riskCheckNo);
			
			// 暂存基本信息
			response.setStatus(riskCheckService.saveBasicInfo(userInfo,riskCheckRequestVo));
			
			// 返回单号以及模版类型
			RiskCheckMainVo riskCheckMainVo = new RiskCheckMainVo();
			riskCheckMainVo.setRiskCheckNo(riskCheckNo);
			riskCheckMainVo.setCheckModel(riskCheckRequestVo.getRiskCheckMainVo().getCheckModel());
			
			response.setData(riskCheckMainVo);
			response.setStatus(0);
			response.setStatusText("success");
			
			// 设置时间轨迹
			riskTimeService.insertRiskTime(riskCheckNo, userInfo.getUserCode(), CommonConst.RX_T_CREATE);

		}else {
			response.setStatus(1);
			response.setStatusText("巡检报告无数据，请重新录入!");
		}
		return response;
	}
	/**
	 * @功能: 更新log表信息
	 * @author 孙凯
	 * @param riskCheckMainQueryVo
	 * @return AjaxResult 
	 * @日期：
	 */
	@ApiOperation(value="更新log表信息",notes="updateby 孙凯 20200117")
	@RequestMapping(value = "/updateCheckMainLog", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Object> updateCheckMainLog(@RequestBody RiskCheckMainQueryVo riskCheckMainQueryVo) {
		ApiResponse<Object> ajaxResult = new ApiResponse<Object>();
		
		Map<String,Object> map = riskCheckService.updateCheckMainLog(riskCheckMainQueryVo);
		ajaxResult.setStatus((int)map.get("status"));
		ajaxResult.setStatusText((String)map.get("statusText"));
		return ajaxResult;
	}
	/**
	 * @功能 对导入的巡检信息进行查询
	 * @param riskCheckRequestVo
	 * @return RiskCheckResponseVo
	 * @author 周东旭
	 * @throws Exception 
	 * @日期 2020-01-16
	 * @修改记录
	 */
	@RequestMapping(value = "/queryCheckMainLog", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<RiskCheckResponseVo> queryCheckMainLog(@RequestBody RiskCheckRequestVo riskCheckRequestVo) {
		ApiResponse<RiskCheckResponseVo> api = new ApiResponse<RiskCheckResponseVo>();
		// 用户信息
		UserInfo userInfo = getUserInfo();
		RiskCheckResponseVo riskCheckResponseVo = riskCheckService.queryCheckMainLog(riskCheckRequestVo, userInfo);
		api.setData(riskCheckResponseVo);
		return api;
	}
	
	/**
	 * 汛期报告分页查询---根据id修改保存巡检报告
	 * @return
	 */
	@ApiOperation(value="汛期报告分页查询---根据id修改巡检报告",notes="updateby 崔凤志  20200117")
	@RequestMapping(value = "/saveRiskCheck", method = RequestMethod.POST)
	public ApiResponse<RiskCheckResponseVo> saveRiskCheck(@RequestBody RiskCheckRequestVo riskCheckRequestVo){
		ApiResponse<RiskCheckResponseVo> resp = new ApiResponse<RiskCheckResponseVo>();
		
		if(riskCheckRequestVo.getRiskCheckMainVo() !=null) {
			
			String client = getHeader("user-agent");
			
			// 移动端请求处理
			if (client.contains("Android") || client.contains("iPad")) {
				riskCheckRequestVo.getRiskCheckMainVo().setMobileFlag("1");
			}else if(client.contains("iPhone")){
				riskCheckRequestVo.getRiskCheckMainVo().setMobileFlag("2");
			}else{
				riskCheckRequestVo.getRiskCheckMainVo().setMobileFlag("0");
			}
			
			// 获取用户信息
			UserInfo userInfo = getUserInfo();
			
			String checkModel = riskCheckRequestVo.getRiskCheckMainVo().getCheckModel();
			
			if ("001".equals(checkModel) || "002".equals(checkModel)) {
				
				//获取图片资料  
				List<RiskCheckImageVo> riskCheckImages = riskCheckService.getRiskCheckImageList(riskCheckRequestVo.getRiskCheckMainVo().getRiskDcodeList(),riskCheckRequestVo.getRiskCheckMainVo().getRiskCheckNo());
				// 保存图片资料  
				riskCheckRequestVo.setRiskCheckImageList(riskCheckImages);
			}
			
			resp.setStatus(riskCheckService.saveRiskCheck(userInfo,riskCheckRequestVo,getRequest().getScheme()));
			
			// 设置时间轨迹
			riskTimeService.insertRiskTime(riskCheckRequestVo.getRiskCheckMainVo().getRiskCheckNo(), userInfo.getUserCode(), CommonConst.RX_SAVE);

		}
		return resp;
	}

	/**
	 * @Description 企业失信具体信息查询
	 * @param queryRule
	 * @param request
	 */
	@RequestMapping(value = "/queryCorporateRadarInfo",method = RequestMethod.POST)
	@ApiOperation(value="汛期报告分页查询---根据id修改巡检报告",notes="addby 崔凤志  20200218")
	public ApiResponse queryCorporateRadarInfo(@RequestBody CorporateRadarQueryRule queryRule) throws IOException {
		UserInfo userInfo = getUserInfo();													   

		ApiResponse result = new ApiResponse();
		if(queryRule != null){
			Map<String,Object> map = this.riskCheckService.queryCorporateRadarInfo(queryRule, userInfo);
			result.setData(map.get("data"));
			result.setStatus((int)map.get("status"));
			result.setStatusText((String)map.get("statusText"));
		}else{
			result.setStatus(4);
			result.setStatusText("数据不存在!");
		}
		return result;
	}	
	/**
	 * @功能：巡检报告excel批量导入
	 * @return AjaxResult
	 * @author liqiankun
	 * @时间：2019-03-14
	 * @修改： 周东旭 @时间：２０２０－０２－２１
	 */
	@RequestMapping(value="/importRiskCheckInfo",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public ApiResponse importRiskCheckInfo(@RequestParam("file") MultipartFile file){
		ApiResponse api =new ApiResponse();
		UserInfo userInfo = getUserInfo();

		Map<String,Object> map = riskCheckService.importRiskCheckInfo(file,userInfo,getRequest());
		api.setData(map.get("data"));
		api.setStatus((int)map.get("status"));
		api.setStatusText((String)map.get("statusText"));
		
		return api;
	}

	/**
	 * @Description word下载
	 * @param riskCheckNo
	 */
	@RequestMapping(value = "/downloadWord",method = RequestMethod.GET)
	@ApiOperation(value="汛期报告---word文档下载",notes="addby 崔凤志  20200227")
	public ApiResponse downloadWordByTemplate(String riskCheckNo) throws Exception{
		// 用户信息
		UserInfo userInfo = getUserInfo();

		ApiResponse resp = new ApiResponse();
		if(StringUtils.isNotBlank(riskCheckNo)){
			RiskCheckMain riskCheckMain = this.riskCheckService.queryRiskCheckNo(riskCheckNo);
			Map<String,String> paths = this.riskCheckService.generateWord(riskCheckMain,userInfo,false);
			resp.setStatus(0);
			resp.setData(paths);
		}else{
			resp.setStatus(1);
			resp.setStatusText("数据不存在!");
		}
		return resp;
	}

	/**
	 * @Description 防汛报告pdf下载分离
	 * @param riskCheckNo
	 */
	@RequestMapping(value = "/downloadPdf",method = RequestMethod.GET)
	@ApiOperation(value="汛期报告---pdf下载",notes="addby 崔凤志  20200227")
	public ApiResponse downloadPdf(String riskCheckNo) throws Exception {
		// 用户信息
		UserInfo userInfo = getUserInfo();
		ApiResponse resp = new ApiResponse();
		if(StringUtils.isNotBlank(riskCheckNo)){
			RiskCheckMain riskCheckMain = this.riskCheckService.queryRiskCheckNo(riskCheckNo);
			String pdfPath = this.riskCheckService.generatePdf(riskCheckMain,userInfo);
			resp.setData(pdfPath);
		}else {
			resp.setStatus(1);
			resp.setStatusText("数据不存在!");
		}
		return resp;
	}

	/**
	 * @功能：巡检excel错误文件删除
	 * @author liqiankun
	 * @param url 文件路径
	 */
	@RequestMapping(value = "/deleteTempExcel",method=RequestMethod.POST)
	@ApiOperation(value="巡检excel错误文件删除",notes="addby 崔凤志  20200313")
	public ApiResponse deleteTempExcel(@RequestParam("url") String url) {
		ApiResponse resp =new ApiResponse();
		FTPUtil ftp =new FTPUtil();
		try {
			if(StringUtils.isNotBlank(url)) {
				ftp.removeFile(url);
			}
		}catch (Exception e) {
			LOGGER.info( e.getMessage() ,e);
			e.printStackTrace();
			resp.setStatus(1);
			resp.setStatusText(e.getMessage());
			throw new RuntimeException(e);
		}finally {
			if(ftp!=null) {
				try {
					ftp.close();
				} catch (IOException e) {
					LOGGER.info("关闭ftp异常：" + e.getMessage() ,e);
				}
			}
		}
		return resp;
	}
}
