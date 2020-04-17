package com.picc.riskctrl.activiti.api;

import com.picc.riskctrl.activiti.service.ActivitiService;
import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.riskins.vo.RiskInsUnderwriteVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdfc.framework.web.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * 工作流相关接口，风控报告审核相关
 *
 * @author wangwenjie
 * @date 2020-01-16
 */
@RestController
@RequestMapping("/riskins")
public class ActivitiController extends BaseController {

    @Autowired
    private ActivitiService activitiService;

    /**
     * 提交审核，审核状态
     * T 暂存
     * 1 保存
     * 4 或 9待审核 4为待一级审核 9为待二级审核
     * 1 通过
     * 2 不通过
     * 3 自动审核通过
     *
     * @author wangwenjie
     * @param riskFileNo
     * @return pdfc.framework.web.ApiResponse<java.util.Map < java.lang.String, java.lang.Object>>
     */
    @GetMapping("/submitUnderwrite")
    @ApiOperation(value = "风控报告提交审核")
    public ApiResponse<Map<String, Object>> submitUnderwrite(@RequestParam(value = "riskFileNo") String riskFileNo) {
        Map<String, Object> result = activitiService.submitUnderwrite(riskFileNo);
        return ApiResponse.ok(result);
    }

    /**
     * 风控报告审核处理
     *
     * @author wangwenjie
     * @param riskFileNo
     * @param executionId
     * @param underwriteFlag
     * @param repulsesugggest
     * @return pdfc.framework.web.ApiResponse<ins.framework.web.AjaxResult>
     */
    @ApiOperation(value = "风控报告审核处理")
    @RequestMapping(value = "/underwrite", method = RequestMethod.GET)
    public ApiResponse<String> underwrite(@RequestParam(value = "riskFileNo") String riskFileNo,
                                          @RequestParam(value = "executionId") String executionId,
                                          @RequestParam(value = "underwriteFlag") String underwriteFlag,
                                          @RequestParam(value = "repulsesugggest") String repulsesugggest) {
        activitiService.underwrite(riskFileNo, executionId, underwriteFlag, repulsesugggest);
        return ApiResponse.ok();
    }

    /**
     * 组织风控报告历史记录
     *
     * @author wangwenjie
     * @param executionId
     * @return pdfc.framework.web.ApiResponse<java.util.List < com.picc.riskctrl.riskins.vo.RiskInsUnderwriteVo>>
     */
    @ApiOperation(value = "组织风控报告历史记录")
    @RequestMapping(value = "/getUnderwriteProcess", method = RequestMethod.GET)
    public ApiResponse<List<RiskInsUnderwriteVo>> getUnderwriteProcess(@RequestParam(value = "executionId") String executionId) {
        List<RiskInsUnderwriteVo> riskInsUnderwriteVoList =
                activitiService.getUnderwriteProcess(executionId);
        return ApiResponse.ok(riskInsUnderwriteVoList);
    }
}
