package com.picc.riskctrl.common.api;

import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.common.service.RiskSwitchControlService;
import com.picc.riskctrl.common.vo.RiskSwitchControlRequestVo;
import com.picc.riskctrl.common.vo.RiskSwitchControlResponseVo;
import com.picc.riskctrl.common.vo.RiskSwitchControlVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pdfc.framework.web.ApiResponse;

/**
 * 系统开关配置
 *
 * @author wangwenjie
 * @date 2020-01-21
 */
@RestController
@RequestMapping("/riskset")
public class RiskSwitchControlApi extends BaseController {

    @Autowired
    private RiskSwitchControlService riskSwitchControlService;

    /**
     * 获取开关信息
     *
     * @author wangwenjie
     * @param riskSwitchControlRequestVo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskSwitchControlResponseVo>
     */
    @ApiOperation(value = "获取开关信息")
    @PostMapping(value = "/riskSwitch")
    public ApiResponse<RiskSwitchControlResponseVo> querySwitch(@RequestBody RiskSwitchControlRequestVo riskSwitchControlRequestVo) {
        RiskSwitchControlResponseVo vo = riskSwitchControlService.queryRiskSwitch(riskSwitchControlRequestVo);
        String erroMsg = vo.getQCexception();
        if (StringUtils.isEmpty(erroMsg)) {
            return ApiResponse.ok(vo);
        } else {
            return error(vo);
        }

    }

    /**
     * 开关信息保存
     *
     * @author wangwenjie
     * @param riskSwitchControlRequestVo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskSwitchControlResponseVo>
     */
    @ApiOperation(value = "开关信息保存")
    @PostMapping(value = "/riskSwitchSave")
    public ApiResponse<RiskSwitchControlResponseVo> riskSwitchSave(@RequestBody RiskSwitchControlRequestVo riskSwitchControlRequestVo) {
        RiskSwitchControlResponseVo vo = new RiskSwitchControlResponseVo();
        String message = "";
        RiskSwitchControlVo riskSwitchControlVo = riskSwitchControlRequestVo.getRiskSwitchControlVo();
        if (riskSwitchControlVo != null) {
            if ("news".equals(riskSwitchControlRequestVo.getEditModel())) {
                //新增
                message = riskSwitchControlService.saveRiskSwitchControl(riskSwitchControlVo);
            } else if ("edit".equals(riskSwitchControlRequestVo.getEditModel())) {
                //修改
                message = riskSwitchControlService.updateRiskSwitchControl(riskSwitchControlVo);
            }
        }
        vo.setMessage(message);
        return ApiResponse.ok(vo);
    }
}
