package com.picc.riskctrl.riskinfo.claim.api;

import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.riskinfo.claim.po.RiskInfoClaim;
import com.picc.riskctrl.riskinfo.claim.service.RiskInfoClaimService;
import com.picc.riskctrl.riskinfo.claim.vo.RiskInfoClaimRequestVo;
import com.picc.riskctrl.riskinfo.claim.vo.RiskInfoResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pdfc.framework.web.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@RestController
@RequestMapping(value="/riskinfo/claim")
@Api
public class RiskInfoClaimApi {

	@Autowired
	private RiskInfoClaimService riskInfoClaimService;


	/**
	 * 典型案例分析查询页面（查勘行业、出险原因、案件来源 ）下拉查询
	 * @param codeType
	 * @return
	 */
	@ApiOperation(value="riskDcodeQuery查询",notes="addby 崔凤志 20200108")
	@RequestMapping(value = "/riskDCodeQuery", method = RequestMethod.GET)
	public ApiResponse<List<RiskDcode>> riskDcodeQuery(@RequestParam String codeType) {
		return riskInfoClaimService.riskDcodeQuery(codeType);
	}
	/**
	 * @功能:多条件分页查询典型案例分析
	 * @author 周东旭
	 * @return ResultPage
	 *  @日期：20200107
	 **/
	@ApiOperation(value="多条件分页查询典型案例分析",notes="addby 周东旭 20200107")
	@RequestMapping(value = "/queryClaim",method = RequestMethod.POST)
	public ApiResponse<RiskInfoResponseVo> queryRiskInfoClaim(@RequestBody RiskInfoClaimRequestVo riskInfoClaimRequestVo){
		RiskInfoResponseVo riskInfoResponseVo = riskInfoClaimService.queryRiskInfoClaim(riskInfoClaimRequestVo);

		return ApiResponse.ok(riskInfoResponseVo);
	}

	/**
	 * @功能：典型案例维护--详细查询
	 * @作者：崔凤志
	 * @日期：20200205
	 */
	@ApiOperation(value="典型案例维护--详细信息查询",notes="addby 崔凤志 20200205")
	@RequestMapping(value="/queryClaimDetail", method = RequestMethod.GET)
	public ApiResponse<RiskInfoClaim>  queryClaimDetail(@RequestParam int serialNo){
		return ApiResponse.ok(riskInfoClaimService.queryRiskInfoClaimBySerialNo(serialNo));
	}
	/**
	 * @功能：pdf文件上传（临时）
	 * @return AjaxResult
	 * @作者：liangshang
	 * @日期：20180426
	 * @修改记录：
	 */
	@ApiOperation(value="典型案例维护--文件上传",notes="modify 张日炜 20200219")
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public ApiResponse<String[]> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("serialNo") String serialNo
			, @RequestParam("businessType") String businessType){

		ApiResponse<String[]> apiResponse = new ApiResponse<>();
		if(file != null) {
			try {
				//文件浏览路径
				ResourceBundle bundle = ResourceBundle.getBundle("config.savePath",
						Locale.getDefault());
				String projectUrl = bundle.getString("saveTypePath");

				//保存pdf及转换成jpg文件，供前端预览
				apiResponse = riskInfoClaimService.savePdf(file,projectUrl,"upload/temp",businessType,serialNo);

			} catch (Exception e) {
				log.error( e.getMessage() ,e);
				e.printStackTrace();
				apiResponse.setStatus(-1);
				apiResponse.setStatusText("保存文件失败！");
				throw new RuntimeException(e);
			}
		}
		return apiResponse;
	}
}
