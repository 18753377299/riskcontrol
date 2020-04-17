package com.picc.riskctrl.common.api;

import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.common.service.RiskInsUtiWeightEditService;
import com.picc.riskctrl.common.vo.RiskUtiWeightResponseVo;
import com.picc.riskctrl.common.vo.RiskUtiWeightVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdfc.framework.web.ApiResponse;

/**
 * 灾因权重维护 api
 *
 * @author wangwenjie
 * @date 2020-02-06
 */
@RestController
@RequestMapping("/riskset/riskUtiWeight")
public class RiskInsUtiWeightEditApi extends BaseController {

    @Autowired
    private RiskInsUtiWeightEditService riskInsUtiWeightEditService;

    /**
     * 权重信息查询
     *
     * @author wangwenjie
     * @param riskUtiWeightResponseVo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskUtiWeightResponseVo>
     */
    @ApiOperation(value = "权重信息查询")
    @PostMapping(value = "/queryRiskUtiWeight")
    //使用同一个vo类，为了减少代码冗余
    public ApiResponse<RiskUtiWeightResponseVo> queryRiskUtiWeight(@RequestBody RiskUtiWeightResponseVo riskUtiWeightResponseVo) {
        RiskUtiWeightResponseVo responnseVo =
                riskInsUtiWeightEditService.queryRiskUtiWeight(riskUtiWeightResponseVo);
        if (StringUtils.isEmpty(responnseVo.getQCexception())) {
            return ApiResponse.ok(responnseVo);
        } else {
            return error(responnseVo);
        }
    }

    /**
     * 保存权重信息
     *
     * @author wangwenjie
     * @param riskUtiWeightResponseVo
     * @return java.lang.String
     */
    @ApiOperation(value = "保存权重信息")
    @PostMapping(value = "/saveRiskUtiWeight")
    public ApiResponse<String> saveRiskUtiWeight(@RequestBody RiskUtiWeightResponseVo riskUtiWeightResponseVo) {
        String message = "";
        if (riskUtiWeightResponseVo != null) {
            String success = riskInsUtiWeightEditService.updateValidStatusByComcode(riskUtiWeightResponseVo.getRiskutiweightvo().getComCode());
            if ("success".equals(success)) {
                RiskUtiWeightVo riskUtiWeightVo = riskUtiWeightResponseVo.getRiskutiweightvo();
                message = riskInsUtiWeightEditService.saveRiskUtiWeight(riskUtiWeightVo);
            }
        }
        return ApiResponse.ok(message);
    }

    /**
     * 浏览权重信息
     *
     * @author wangwenjie
     * @param id
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskUtiWeightVo>
     */
    @GetMapping(value = "/scanById")
    @ApiOperation(value = "浏览权重信息")
    public ApiResponse<RiskUtiWeightVo> scanById(@RequestParam String id) {
        return ApiResponse.ok(riskInsUtiWeightEditService.scanById(id));
    }

    /**
     * 灾因权重注销
     *
     * @author wangwenjie
     * @param id
     * @return pdfc.framework.web.ApiResponse<java.lang.String>
     */
    @ApiOperation(value = "灾因权重注销")
    @RequestMapping(value = "/cancelById", method = RequestMethod.GET)
    public ApiResponse<String> cancelById(@RequestParam String id) {
        return ApiResponse.ok(riskInsUtiWeightEditService.cancelById(id));
    }
}
