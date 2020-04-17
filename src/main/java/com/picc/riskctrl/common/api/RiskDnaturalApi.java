package com.picc.riskctrl.common.api;


import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.po.RiskDnatural;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.service.RiskDnaturalService;
import com.picc.riskctrl.common.vo.*;
import com.picc.riskctrl.riskins.po.RiskDaddress;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdfc.framework.web.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/riskset/riskDnatural")
public class RiskDnaturalApi extends BaseController {


    @Autowired
    private RiskDnaturalService riskDnaturalService;

    @Autowired
    private DataSourcesService dataSourcesService;


    /**
     * @Description: 环境信息查询
     * @author: QLJ
     * @data: 2020/1/21
     * @param riskDnaturalRequestVo
     * @return:pdfc.framework.web.ApiResponse
     * @throws
     */
    @ApiOperation(value = "环境信息查询", notes = "环境信息查询")
    @RequestMapping(value = "/queryRiskDnatural", method = RequestMethod.POST)
    public ApiResponse queryRiskDnatural(@RequestBody RiskDnaturalRequestVo riskDnaturalRequestVo) {

        String naturalYear = riskDnaturalRequestVo.getRiskDnaturalVo().getNaturalYear();

        if (naturalYear.length() > 4) {
            naturalYear = String.valueOf(Integer.valueOf(naturalYear.substring(0, 4)) + 1);
            riskDnaturalRequestVo.getRiskDnaturalVo().setNaturalYear(naturalYear);
        }
        return ApiResponse.ok(riskDnaturalService.queryRiskDnatural(riskDnaturalRequestVo));
    }

    /**
     * @Description: 校验环境信息是否有效
     * @author: QuLingjie
     * @data: 2020/1/22
     * @param serialNo
     * @return:pdfc.framework.web.ApiResponse
     * @throws
     */
    @ApiOperation(value = "查询环境信息是否有效", notes = "查询环境信息是否有效")
    @RequestMapping(value = "/queryRiskDnaturalState", method = RequestMethod.GET)
    public ApiResponse queryRiskDnaturalState(@RequestParam("serialNo") String serialNo) {

        return ApiResponse.ok(riskDnaturalService.queryRiskDnaturalState(serialNo));
    }

    /**
     * 基本信息维护查询
     *
     * @author wangwenjie
     * @param riskDnaturalRequestVo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskDnaturalResponseVo>
     */
    @PostMapping("/queryRiskDcodeVo")
    @ApiOperation(value = "基本信息维护查询")
    public ApiResponse<RiskDnaturalResponseVo> queryRiskDcodeVo(@RequestBody RiskDnaturalRequestVo riskDnaturalRequestVo) {
        RiskDnaturalResponseVo riskDnaturalResponseVo = riskDnaturalService.queryRiskDcodeVo(riskDnaturalRequestVo);
        if (StringUtils.isEmpty(riskDnaturalResponseVo.getQCexception())) {
            return ApiResponse.ok(riskDnaturalResponseVo);
        } else {
            return error(riskDnaturalResponseVo);
        }
    }

    /**
     * 基本信息维护信息保存
     *
     * @author wangwenjie
     * @param riskDnaturalRequestVo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskDnaturalResponseVo>
     */
    @PostMapping(value = "/saveRiskBasicInfoTendDetail")
    @ApiOperation(value = "基本信息维护信息保存")
    public ApiResponse<RiskDnaturalResponseVo> saveRiskBasicInfoTendDetail(@RequestBody RiskDnaturalRequestVo riskDnaturalRequestVo) {
        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();
        String message = "";
        if ("edit".equals(riskDnaturalRequestVo.getModeifyFlag())) {
            message = riskDnaturalService.updateRiskBasicInfoTendDetail(riskDnaturalRequestVo);
        } else {
            message = riskDnaturalService.saveRiskBasicInfoTendDetail(riskDnaturalRequestVo);
        }
        riskDnaturalResponseVo.setMessage(message);
        return ApiResponse.ok(riskDnaturalResponseVo);
    }

    /**
     * 基本信息维护信息标志设置为无效
     *
     * @author wangwenjie
     * @param riskDcodeVo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskDnaturalResponseVo>
     */
    @PostMapping(value = "/cancelValidStatus")
    @ApiOperation(value = "基本信息维护信息标志设置为无效")
    public ApiResponse<RiskDnaturalResponseVo> cancelValidStatus(@RequestBody RiskDcodeVo riskDcodeVo) {
        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();
        String message = riskDnaturalService.updateValidStatus(riskDcodeVo);
        riskDnaturalResponseVo.setMessage(message);
        return ApiResponse.ok(riskDnaturalResponseVo);
    }

    /**
     * 获取最新环境信息序号
     *
     * @author wangwenjie
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskDnaturalResponseVo>
     */
    @ApiOperation(value = "获取最新环境信息序号")
    @GetMapping(value = "/getNewSerialNo")
    public ApiResponse<RiskDnaturalResponseVo> getNewSerialNo() {
        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();
        //获取最新环境信息序号
        String serialNo = String.valueOf(riskDnaturalService.getNewSerialNo());
        riskDnaturalResponseVo.setMessage(serialNo);
        return ok(riskDnaturalResponseVo);
    }

    /**
     * 保存环境信息
     *
     * @author wangwenjie
     * @param riskDnaturalRequestVo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskDnaturalResponseVo>
     */
    @PostMapping(value = "/saveRiskDnaturalDetail")
    @ApiOperation(value = "保存环境信息")
    public ApiResponse<RiskDnaturalResponseVo> saveRiskDnatural(@RequestBody RiskDnaturalRequestVo riskDnaturalRequestVo) {

        RiskDnaturalVo riskDnaturalVo = new RiskDnaturalVo();
        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();
        Integer serialNo = 0;
        String message = "";
        if (riskDnaturalRequestVo.getRiskDnaturalVo() != null) {
            String naturalYear = riskDnaturalRequestVo.getRiskDnaturalVo().getNaturalYear();
            if (StringUtils.isNotBlank(naturalYear)) {
                if (naturalYear.length() > 4) {
                    naturalYear = String.valueOf(Integer.valueOf(naturalYear.substring(0, 4)) + 1);
                }
                riskDnaturalRequestVo.getRiskDnaturalVo().setNaturalYear(naturalYear);
            }
            if ("news".equals(riskDnaturalRequestVo.getEditModel())) {
                serialNo = riskDnaturalService.getNewSerialNo();
                riskDnaturalRequestVo.getRiskDnaturalVo().setSerialNo(serialNo);
                //保存环境信息详情
                message = riskDnaturalService.saveRiskDnatural(riskDnaturalRequestVo.getRiskDnaturalVo());
            } else {
                serialNo = riskDnaturalRequestVo.getRiskDnaturalVo().getSerialNo();
                //修改环境信息详情
                message = riskDnaturalService.updateRiskDnatural(riskDnaturalRequestVo.getRiskDnaturalVo());
            }
        }
        if ("no".equals(message)) {
            return error(null, "已存在环境信息");
        }
        RiskDnatural riskDnatural = riskDnaturalService.queryBySerialNo(serialNo);
        BeanUtils.copyProperties(riskDnatural, riskDnaturalVo);
        if (riskDnaturalVo.getAddressCode() != null && !"".equals(riskDnaturalVo.getAddressCode())) {
            RiskDaddress riskDaddress = dataSourcesService.queryRiskDaddressByAddressCode(riskDnaturalVo.getAddressCode());
            if (riskDaddress != null) {
                if (riskDaddress.getGradeFlag().equals("1")) {
                    riskDnaturalVo.setAddressProvince(riskDaddress.getAddressCode());
                } else if (riskDaddress.getGradeFlag().equals("2")) {
                    riskDnaturalVo.setAddressProvince(riskDaddress.getUpperCode());
                }
            }
        }
        //归属机构中文翻译
        if (StringUtils.isNotBlank(riskDnaturalVo.getComCode())) {
            riskDnaturalVo.setComCodeCName(dataSourcesService.queryComCodeCName(riskDnaturalVo.getComCode()));
        }
        riskDnaturalResponseVo.setRiskDnaturalVo(riskDnaturalVo);
        riskDnaturalResponseVo.setMessage(message);
        return ApiResponse.ok(riskDnaturalResponseVo);
    }

    /**
     * 根据序号查询环境信息
     *
     * @author wangwenjie
     * @param serialNo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskDnaturalResponseVo>
     */
    @GetMapping(value = "/queryBySerialNo")
    @ApiOperation(value = "根据序号查询环境信息")
    public ApiResponse<RiskDnaturalResponseVo> queryBySerialNo(@RequestParam Integer serialNo) {
        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();
        RiskDnaturalVo riskDnaturalVo = new RiskDnaturalVo();
        RiskDnatural riskDnatural = new RiskDnatural();
        riskDnatural = riskDnaturalService.queryBySerialNo(serialNo);
        //把实体类数据复制vo类中
        BeanUtils.copyProperties(riskDnatural, riskDnaturalVo);
        if (riskDnaturalVo.getAddressCode() != null && !"".equals(riskDnaturalVo.getAddressCode())) {
            RiskDaddress riskDaddress = dataSourcesService.queryRiskDaddressByAddressCode(riskDnaturalVo.getAddressCode());
            if (riskDaddress != null) {
                if (riskDaddress.getGradeFlag().equals("1")) {
                    riskDnaturalVo.setAddressProvince(riskDaddress.getAddressCode());
                } else if (riskDaddress.getGradeFlag().equals("2")) {
                    riskDnaturalVo.setAddressProvince(riskDaddress.getUpperCode());
                }
            }
        }
        //归属机构中文翻译
        if (StringUtils.isNotBlank(riskDnaturalVo.getComCode())) {
            riskDnaturalVo.setComCodeCName(dataSourcesService.queryComCodeCName(riskDnaturalVo.getComCode()));
        }
        riskDnaturalResponseVo.setRiskDnaturalVo(riskDnaturalVo);
        return ok(riskDnaturalResponseVo);
    }

    /**
     * 环境信息注销
     *
     * @author wangwenjie
     * @param riskDnaturalRequestVo
     * @return com.picc.riskctrl.common.vo.RiskDnaturalResponseVo
     */
    @PostMapping(value = "/cancelBySerialNo")
    @ApiOperation(value = "环境信息注销")
    public ApiResponse<RiskDnaturalResponseVo> cancelBySerialNo(@RequestBody RiskDnaturalRequestVo riskDnaturalRequestVo) {
        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();
        String serialNo = riskDnaturalRequestVo.getSerialNo();
        String message = riskDnaturalService.cancelBySerialNo(serialNo);
        riskDnaturalResponseVo.setMessage(message);
        return ok(riskDnaturalResponseVo);
    }

    /**
     * 根据代码类型查询对应的行业代码
     *
     * @author wangwenjie
     * @param riskDcodeVo
     * @return ApiResponse<List<RiskDcode>>
     */
    @RequestMapping(value = "/getRiskDcodeList", method = RequestMethod.POST)
    @ApiOperation(value = "根据代码类型查询对应的行业代码")
    public ApiResponse<List<RiskDcode>> getRiskDcodeList(@RequestBody RiskDcodeVo riskDcodeVo) {
        List<RiskDcode> riskDcodeList = riskDnaturalService.getRiskDcodeList(riskDcodeVo);
        return ok(riskDcodeList);
    }

    /**
     * 获取基本信息的最大codecode值
     * @author wangwenjie
     * @param riskDcodeVo
     * @return
     */
    @PostMapping(value = "/getNewBasicSerialNo")
    @ApiOperation(value = "获取基本信息的最大codecode值")
    public ApiResponse<RiskBasicResponseVo> getNewBasicSerialNo(@RequestBody RiskDcodeVo riskDcodeVo) {
        RiskBasicResponseVo riskBasicResponseVo = riskDnaturalService.getNewBasicSerialNo(riskDcodeVo);
        return ok(riskBasicResponseVo);
    }
}
