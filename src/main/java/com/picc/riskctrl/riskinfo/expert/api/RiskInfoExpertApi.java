package com.picc.riskctrl.riskinfo.expert.api;

import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.utils.FTPUtil;
import com.picc.riskctrl.dataquery.service.RiskInfoService;
import com.picc.riskctrl.riskinfo.expert.po.RiskInfoDiscuss;
import com.picc.riskctrl.riskinfo.expert.po.RiskInfoExpert;
import com.picc.riskctrl.riskinfo.expert.service.RiskInfoExpertService;
import com.picc.riskctrl.riskinfo.expert.vo.RiskInfoExpertDetailVo;
import com.picc.riskctrl.riskinfo.expert.vo.RiskInfoExpertRequestVo;
import com.picc.riskctrl.riskinfo.expert.vo.RiskInfoExpertVo;
import com.picc.riskctrl.riskinfo.expert.vo.RiskInfoResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.easy.excel.ExcelContext;
import org.easy.excel.result.ExcelImportResult;
import org.easy.excel.util.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import pdfc.framework.web.ApiResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author zrw
 */
@RestController
@RequestMapping(value="/riskinfo/expert")
@Api
public class RiskInfoExpertApi extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

	@Autowired
	private RiskInfoExpertService riskInfoExpertService;

	@Autowired
	private DataSourcesService dataSourcesService;

	@Autowired
	RiskInfoService riskInfoService;

	@Autowired
	ExcelContext excelContext;

	/**
	 * @功能：专家信息浏览和评论信息查看
	 * @author anqingsen
	 * @param expertNo 专家编号
	 * @param pageNo 评论当前页数
	 * @param pageSize 评论每页行数
	 * @return riskinfoVo
	 * @throws Exception
	 * @日期：2020-01-09
	 */
	@ApiOperation(value="专家信息浏览和评论信息查看",notes="addby anqingsen 20200109")
	@RequestMapping(value = "/queryRiskInfoExpert", method=RequestMethod.GET)
	public ApiResponse<RiskInfoExpertDetailVo>  queryRiskInfoExpertDetail(@RequestParam(value = "expertNo") Integer expertNo,
															@RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize)
			throws Exception {
		RiskInfoExpertDetailVo riskinfoVo = riskInfoExpertService.queryRiskInfoExpert(expertNo, pageNo, pageSize);
		return ApiResponse.ok(riskinfoVo);
	}
	@ApiOperation(value="评论模块数据提交",notes="modifyby liqiankun 20200107")
	@RequestMapping(value = "/updateExpertDiscuss",method=RequestMethod.POST)
	public ApiResponse<RiskInfoDiscuss> updateExpertDiscuss(@RequestBody RiskInfoExpertVo riskInfoExpertVo) {
		String discuss = "create";
		discuss = riskInfoExpertVo.getDiscuss();
		Integer expertNo = riskInfoExpertVo.getExpertNo();
		BigDecimal score = riskInfoExpertVo.getScore();
		// 用户信息
		UserInfo userInfo = getUserInfo();
		// 用户代码
		String userCode = userInfo.getUserCode();
		// 用户名
		String userName = userInfo.getUserName();
		
		// 保存评论表信息
		RiskInfoDiscuss riskInfoDiscuss = null;
		try {
			discuss = discuss.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			discuss = URLDecoder.decode(discuss, "UTF-8");
//			riskInfoDiscuss = expertService.saveRiskInfoDiscuss(discuss, expertNo, score, userCode, userName);
			riskInfoDiscuss = riskInfoExpertService.saveRiskInfoDiscuss(discuss, expertNo, score, userCode, userName);
		} catch (Exception e) {
			LOGGER.error( e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return ApiResponse.ok(riskInfoDiscuss);
	}
	
	@ApiOperation(value="展示所属机构详细介绍页面",notes="modifyby liqiankun 20200107")
	@RequestMapping(value = "/riskInfoExpertByIntroduce.dialog",method=RequestMethod.GET)
	public ApiResponse<RiskDcode> riskInfoExpertByIntroduce(@RequestParam(value = "ascNameCode") String ascNameCode){
		RiskDcode riskDcode = null;
		if (StringUtils.isNotBlank(ascNameCode)) {
			riskDcode = dataSourcesService.queryRiskDcode("expertIntroduce", "", ascNameCode, "1");
		}
		return ApiResponse.ok(riskDcode);
	}
	
	
	@ApiOperation(value="专家资源分页查询",notes="modifyby 张日炜 2020316")
	@RequestMapping(value = "/queryExpert", method = RequestMethod.POST)
	public ApiResponse<RiskInfoResponseVo> queryRiskInfoExpert(@RequestBody RiskInfoExpertRequestVo riskInfoExpertRequestVo) {
		return ApiResponse.ok(riskInfoExpertService.queryRiskExpert(riskInfoExpertRequestVo, ""));
	}
	
	/**
	 * @功能：删除专家信息及被评论信息
	 * @param expertNo	专家编号
	 * @return String
	 * @throws Exception
	 * @日期	2018-5-28
	 */
	@ApiOperation(value="删除专家信息及被评论信息",notes="modifyby 崔凤志  20200120")
	@RequestMapping(value = "/delRiskInfoExpert",method=RequestMethod.GET)
	public ApiResponse<String> delRiskInfoExpertDetail(@RequestParam(value = "expertNo") Integer expertNo)
			throws Exception {
		return riskInfoExpertService.delRiskInfoExpert(expertNo);
	}
	/**
	 * @功能：专家excel导入
	 * @param file
	 *            上传的文件
	 * @return AjaxResult
	 * @throws @日期
	 *             2018-5-24
	 */
	@ApiOperation(value="专家excel导入",notes="modifyby 张日炜  20200220")
	@RequestMapping(value = "/importExpertInfo",method=RequestMethod.POST)
	public ApiResponse<String[]> importExpertInfo(@RequestParam("file") MultipartFile file) {
		ApiResponse<String[]> apiResponse =new ApiResponse<>();
		if(!file.isEmpty()){
			try {
				//设置上下文，方便后来取到dao
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getRequest().getSession().getServletContext());
				ApplicationContextUtil.setApplicationContext(context);
				//文件实际存储路径
//				String path = request.getSession().getServletContext().getRealPath("/upload");
				// 获取存储地址
				ResourceBundle bundle = ResourceBundle.getBundle("config.savePath",
						Locale.getDefault());

				//文件浏览路径
				String projectUrl = bundle.getString("saveTypePath")+"/save/riskexpert/";

				//第二个参数需要注意,它是指标题索引的位置,可能你的前几行并不是标题,而是其他信息
				//比如数据批次号之类的,关于如何转换成javaBean,具体参考配置信息描述
				ExcelImportResult result = excelContext.readExcel("expert", 2, file.getInputStream(),"save/riskexpert",projectUrl);
				//无错误 方可存入数据库
				if(!result.hasErrors()) {
					List<RiskInfoExpert> experts = result.getListBean();
					if(experts == null || experts.size() == 0) {
						apiResponse.setStatus(2);
						apiResponse.setStatusText("表中不存在数据，请重新导入!");
						return apiResponse;
					}
					for(RiskInfoExpert expert:experts){
						expert.setValidStatus("1");
						expert.setScore(BigDecimal.valueOf(0));
						if (org.apache.commons.lang.StringUtils.isBlank(expert.getUrl())) {
							expert.setUrl(projectUrl + "default.jpg");
						}
						riskInfoService.saveRiskInfoExpert(expert);
					}
					apiResponse.setStatus(0);
				}else {
					apiResponse.setStatus(-1);
					String[] errorExcelUrl = {result.getErrorFileNetUrl(),result.getErrorFileRealUrl()};
					apiResponse.setData(errorExcelUrl);
				}
			} catch (Exception e) {
				LOGGER.error( e.getMessage() ,e);
				e.printStackTrace();
				apiResponse.setStatus(1);
				apiResponse.setStatusText(e.getMessage());
				throw new RuntimeException(e);
			}

		}else{
			apiResponse.setStatus(1);
			apiResponse.setStatusText("文件不存在");
		}
		return apiResponse;
	}
	/**
	 * @功能：专家excel错误文件删除
	 * @author 梁尚
	 * @param url
	 *            文件路径
	 * @return AjaxResult
	 * @throws @日期
	 *             2018-5-24
	 */
	@ApiOperation(value="专家excel错误文件删除",notes="modifyby 张日炜  20200221")
	@RequestMapping(value = "/deleteTempExcel",method=RequestMethod.POST)
	public ApiResponse deleteTempExcel(@RequestParam("url") String url) {
		ApiResponse apiResponse =new ApiResponse();
		FTPUtil ftp =new FTPUtil();
		try {
			if(org.apache.commons.lang.StringUtils.isNotBlank(url)) {
				ftp.removeFile(url);
				apiResponse.setStatus(0);
			}
		}catch (Exception e) {
			LOGGER.error( e.getMessage() ,e);
			e.printStackTrace();
			apiResponse.setStatus(1);
			apiResponse.setStatusText(e.getMessage());
			throw new RuntimeException(e);
		}finally {
			try {
				ftp.close();
			} catch (IOException e) {
				LOGGER.error("关闭ftp异常：" + e.getMessage() ,e);
			}
		}
		return apiResponse;
	}
}		
