package com.picc.riskctrl.test.api;

import com.picc.riskctrl.common.request.RiskRequestVo;
import com.picc.riskctrl.test.po.Users;
import com.picc.riskctrl.test.service.TestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdfc.framework.common.ResultPage;
import pdfc.framework.web.ApiResponse;

@RestController
@RequestMapping("/test")
public class TestAPi {
	
	@Autowired
	TestService testService;
//	@Autowired
//	DataSourcesService dataSourcesServiced;
//	//测试  代码与中文含义的相互翻译 方法。
//	@RequestMapping(value = "/queryRiskDcode",method = RequestMethod.GET)
//	public RiskDcode queryRiskDcode(@RequestParam String codeType, @RequestParam String comCode, 
//			@RequestParam String codeCodeOrCodeCname, @RequestParam String queryType) {
//		return dataSourcesServiced.queryRiskDcode(codeType, comCode, codeCodeOrCodeCname, queryType);
//	}
	
	// 查询
	 @RequestMapping(value = "/findAll",method = RequestMethod.GET)
     public String findAll(){
		 System.out.println("hello everyBody");
		 testService.findAll();
         return "hello";
     }
	 // 增加
	 @RequestMapping(value = "/insert",method = RequestMethod.GET)
     public String insert(){
		 testService.insertTest();
         return "insertTest";
     }
	// 增加两个不同的表
	 @RequestMapping(value = "/insertTwoDiff",method = RequestMethod.GET)
     public String insertTwoDiff(){
		 testService.insertTwoDiff();
         return "insertTest";
     }
	 // 删除
	 @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
     public String delete(@PathVariable("id") Integer  id){
		 testService.deleteTest(id);
         return "deleteTest";
     }
	 //修改
	 @RequestMapping(value = "/update",method = RequestMethod.GET)
     public String update(){
//		 testService.updateTest();
         return "updateTest";
     }
	 
	 /**
	 * @功能:多条件分页查询
	 * @author liqiankun
	 * @return ResultPage
	 *  @日期：20200107
	 **/
	 @ApiOperation(value="多条件分页查询",notes="addby liqiankun 20200107")
	 @RequestMapping(value = "/findByPage",method = {RequestMethod.GET,RequestMethod.POST})
     public ApiResponse<ResultPage<Users>> findByPage(@RequestBody RiskRequestVo riskRequestVo){
//		 testService.updateTest();
		 ResultPage<Users> result = testService.findByPage(riskRequestVo);
		 
		 return ApiResponse.ok(result);
     }
}
