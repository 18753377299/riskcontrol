package com.picc.riskctrl.riskprice.api;

import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.riskprice.service.RiskPriceService;
import com.picc.riskctrl.riskprice.vo.CommonPricingVo;
import com.picc.riskctrl.riskprice.vo.PricingTreatmentVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdfc.framework.web.ApiResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/riskins")
public class RiskPricingApi {
	@Autowired
	private RiskPriceService riskPriceService;
	/**
	 * @author 周东旭
	 * @功能  定价提交查询*/
	@ApiOperation(value = "根据风控编号查询定价信息", notes = "modifyby 周东旭 20200210")
	@RequestMapping(value = "/queryRiskFileNo", method = RequestMethod.GET)
	public ApiResponse<PricingTreatmentVo> queryRiskFileNo(@RequestParam(value = "riskFileNo") String riskFileNo) throws Exception {
		ApiResponse<PricingTreatmentVo> api = new ApiResponse<PricingTreatmentVo>();
		PricingTreatmentVo histroyPricingTreatmentVo =riskPriceService.queryRiskFileNo(riskFileNo);
		if(histroyPricingTreatmentVo==null) {
			api.setStatus(1);
			api.setStatusText("没有查询到相关信息");
		}else {
			api.setStatus(0);
			api.setData(histroyPricingTreatmentVo);
		}
		return api;
	}
	/**
	 * @author 周东旭
	 * @功能 风控定价处理
	 * @param pricingTreatmentVo
	 */
	@RequestMapping(value = "/toDealWithPricing", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse toDealWithPricing(@RequestBody PricingTreatmentVo pricingTreatmentVo) {
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//		String comcode = userInfo.getComCode();
		String riskFileNo = pricingTreatmentVo.getRiskFileNo();
		ApiResponse api = new ApiResponse();
		String message = "";
		if(StringUtils.isNotBlank(riskFileNo)) {
//			CommonPricingVo comPricVo = riskPriceService.getRPSXml(pricingTreatmentVo,comcode);
//			if(comPricVo.getPricingVo()!=null) {
//				comPricVo.getPricingVo().setRiskFileNo(riskFileNo);
//				comPricVo.setPricingTreatmentVo(pricingTreatmentVo);
//				message = riskPriceService.saveRiskPrice(comPricVo);
//			}else {
//				message = "error";
//			}
			CommonPricingVo comPricVo = null;
			message = riskPriceService.saveRiskPrice(comPricVo);
			if ("success".equals(message)) {
				api.setStatus(1);
				api.setStatusText("保存成功");
			}else {
				api.setStatus(0);
				api.setStatusText("保存失败");
			}
		}else {
			throw new RuntimeException("报告编号不能为空！");
		}
		return api;
	}
	/**
	 * @author 王坤龙
	 * @功能 定价回显
	 * @param HttpServletRequest 
	 * @param HttpServletResponse 
	 * @修改 周东旭*/
	@RequestMapping(value = "/reShowPricing", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse reShowPricing(HttpServletRequest request,
			@RequestParam(value = "riskFileNo") String riskFileNo) throws Exception {
		// 用户信息
		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
		ApiResponse api = new ApiResponse();
//		测试数据
//		riskFileNo = "RCQAK00200002019000221";
		api =riskPriceService.reShowPricing(riskFileNo,userInfo);
		return api;
	}
}
