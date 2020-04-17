package com.picc.riskctrl.riskinfo.report.api;

import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.riskinfo.report.service.RiskInfoModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pdfc.framework.web.ApiResponse;

@RestController
@RequestMapping("/riskinfo")
@Api
public class RiskInfoReportApi {
	
	@Autowired
	RiskInfoModelService riskInfoModelService;
	@Autowired
	DataSourcesService dataSourcesService;
	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");
	
	@ApiOperation(value="返回风控模板菜单数据",notes="modifyby liqiankun 20200107")
	@GetMapping(value = "/Report/list")
	public ApiResponse<String> list() {
		String jsonData = riskInfoModelService.getData();
		return ApiResponse.ok(jsonData);
	}




	
	
	
}
