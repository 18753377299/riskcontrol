package com.picc.riskctrl.riskins.api;

import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.common.po.RiskReportSaleCorrection;
import com.picc.riskctrl.common.po.RiskReportSaleImage;
import com.picc.riskctrl.common.po.RiskReportSaleMain;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.config.SavePathConfig;
import com.picc.riskctrl.riskins.service.RiskInsSaleService;
import com.picc.riskctrl.riskins.vo.RiskInsRequestVo;
import com.picc.riskctrl.riskins.vo.RiskInsResponseVo;
import com.picc.riskctrl.riskins.vo.RiskReportSaleMainVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pdfc.framework.web.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/risksale")
@Slf4j
public class RiskInsSaleApi extends BaseController {
    public static final Logger log = LoggerFactory.getLogger("RISKCONTROLLOG");

    @Autowired
    private RiskInsSaleService riskInsSaleService;

    @Autowired
    private SavePathConfig savePathConfig;

    /**
     * @功能：查询整改历史信息
     * @param archivesNo
     * @param imageType
     * @param imageName
     * @return riskReportSaleCorrectionVo
     * @throws Exception
     * @author 安青森 @日期：2017-01-04 @修改记录：
     */
    @ApiOperation(value = "查询整改历史信息", notes = "modifyby liqiankun 20200217")
    @RequestMapping(value = "/riskSuggestQuery", method = RequestMethod.GET)
    public ApiResponse<RiskInsResponseVo> riskSuggestQuery(
            @RequestParam(value = "archivesNo") String archivesNo,
            @RequestParam(value = "imageType", required = false) String imageType,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "serialNo", required = false) Integer serialNo,
            @RequestParam(value = "imageName") String imageName) throws Exception {

        RiskInsResponseVo riskInsResponseVo = null;
//		String client = request.getHeader("user-agent");
        String client = "user-agent";
        // 用户信息
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");

//		String hostPath = obtainConfigPath(request);
        String hostPath = "";
        // // 移动端请求处理
        // if (client.contains("Android") || client.contains("iPhone")
        // || client.contains("iPad")) {
        // riskInsResponseVo = riskInsSaleService.riskSuggestQuery(archivesNo,
        // imageType, imageName, "0", 1, 5, userInfo);
        // } else {
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        riskInsResponseVo = riskInsSaleService.riskSuggestQuery(archivesNo, imageType, imageName, client, pageNo,
                pageSize, null, serialNo, hostPath);
        // }
        return ApiResponse.ok(riskInsResponseVo);
    }

    /**
     * 获取properties配置文件中的路径
     */
    public String obtainConfigPath(HttpServletRequest request) throws Exception {

        String protocol = request.getScheme();
        String ip = "";
        InetAddress address = InetAddress.getLocalHost();
        ip = address.getHostAddress();
        // String projectURL = protocol+"://"+ip+":"+port+projectName+"/upload/";
        //
        // 获取存储地址
        ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());

        String projectUrl = protocol + "://" + ip + ":" + bundle.getString("savePort") + "/";

        return projectUrl;
    }

    /**
     * 照片档案资料分页查询
     *
     * @author wangwenjie
     * @param riskInsRequestVo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.riskins.vo.RiskInsResponseVo>
     */
    @RequestMapping(value = "/querySaleMain", method = RequestMethod.POST)
    @ApiOperation(value = "照片档案资料分页查询")
    public ApiResponse<RiskInsResponseVo> queryRiskReportMain(@RequestBody RiskInsRequestVo riskInsRequestVo) {
        // 用户信息
        UserInfo userInfo = getUserInfo();
        String userCode = userInfo.getUserCode();
        if (!userInfo.getIsPC()) {
            riskInsRequestVo.getRiskReportSaleMainVo().setMobFlag(true);
        }
        RiskInsResponseVo riskInsResponseVo = riskInsSaleService
                .queryRiskInsSaleMain(riskInsRequestVo, userCode);
        List list = riskInsResponseVo.getDataList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                RiskReportSaleMainVo riskInsSaleMain = (RiskReportSaleMainVo) list.get(i);
                // 审核状态翻译
                String checkUpFlag = riskInsSaleMain.getCheckUpFlag();
                if (StringUtils.isNotBlank(checkUpFlag)) {
                    if ("0".equals(checkUpFlag.trim())) {
                        riskInsSaleMain.setCheckUpFlag("待办");
                    } else if ("1".equals(checkUpFlag)) {
                        riskInsSaleMain.setCheckUpFlag("办结");
                    } else if ("2".equals(checkUpFlag)) {
                        riskInsSaleMain.setCheckUpFlag("影像同步中");
                    }
                }
            }
        }
        return ok(riskInsResponseVo);
    }

    /**
     * 照片档案详细信息查询
     *
     * @author wangwenjie
     * @param archivesNo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.po.RiskReportSaleMain>
     */
    @GetMapping(value = "/querySaleImage")
    @ApiOperation(value = "照片档案详细信息查询")
    public ApiResponse<RiskReportSaleMain> querySaleImage(@RequestParam(value = "archivesNo") String archivesNo) throws Exception {
        // 用户信息
        UserInfo userInfo = getUserInfo();
        String hostPath = obtainConfigPath(getRequest());

        try {
            RiskReportSaleMain riskReportSaleMain = riskInsSaleService.queryRiskReportSaleMain(archivesNo, userInfo,
                    hostPath);
            return ok(riskReportSaleMain);
        } catch (Exception e) {
            return error(null, e.getMessage());
        }
    }

    /**
     * 照片档案审核通过暂存
     *
     * @author wangwenjie
     * @param archivesNo
     * @param checkUpFlag
     * @return pdfc.framework.web.ApiResponse<java.lang.Object>
     */
    @RequestMapping(value = "/saveSaleMain", method = RequestMethod.POST)
    @ApiOperation(value = "照片档案审核通过暂存")
    public ApiResponse<Object> saveSaleMain(@RequestParam(value = "archivesNo") String archivesNo, @RequestParam(value = "checkUpFlag") String checkUpFlag) {
        String[] archivesNoArr = archivesNo.split(",");
        for (String archivesNoTemp : archivesNoArr) {
            if (StringUtils.isNotBlank(archivesNoTemp)) {
                riskInsSaleService.saveSaleMain(archivesNoTemp, checkUpFlag);
            }
        }
        return ok("success");
    }

    /**
     * 照片档案照片打回接口
     *
     * @author wangwenjie
     * @param riskReportSaleImage
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.po.RiskReportSaleCorrection>
     */
    @RequestMapping(value = "/modifySaleSaleImaType", method = RequestMethod.POST)
    @ApiOperation(value = "照片档案照片打回接口")
    public ApiResponse<RiskReportSaleCorrection> modifySaleSaleImaType(@RequestBody RiskReportSaleImage riskReportSaleImage) {
        // 用户信息
        String hostPath = "";
        try {
            hostPath = savePathConfig.getHostPath(getRequest());
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            e.printStackTrace();
        }
        RiskReportSaleCorrection riskReportSaleCorrection = riskInsSaleService
                .modifySaleSaleImaType(riskReportSaleImage, hostPath);
        return ok(riskReportSaleCorrection);
    }


    /**
     * @功能：风控意见是否完成整改
     * @author liqiankun
     * @param
     * @throws @日期
     *             2018-1-5
     */
    @ApiOperation(value = "查询整改历史信息", notes = "modifyby liqiankun 20200217")
    @RequestMapping(value = "/saveRiskInsSaleDetailModel", method = RequestMethod.POST)
    public ApiResponse<RiskReportSaleImage> saveRiskInsSaleDetailModel(
            @RequestBody RiskReportSaleImage riskReportSaleImage) throws Exception {
        RiskReportSaleImage newImage = new RiskReportSaleImage();
        // 用户信息
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
//		String hostPath = obtainConfigPath(request);
        String hostPath = "";
//		newImage = riskInsSaleService.saveRiskInsSaleDetailModel(riskReportSaleImage, userInfo, hostPath);
        newImage = riskInsSaleService.saveRiskInsSaleDetailModel(riskReportSaleImage, null, hostPath);
        // 处理json解析异常，将无用数据删除
        newImage.setRiskReportSaleImaType(null);
        if (newImage.getRiskReportSaleCorrectionList() != null
                && newImage.getRiskReportSaleCorrectionList().size() > 0) {
            for (int i = 0; i < newImage.getRiskReportSaleCorrectionList().size(); i++) {
                if (newImage.getRiskReportSaleCorrectionList().get(i) != null
                        && "2".equals(newImage.getRiskReportSaleCorrectionList().get(i).getCorrectionFlag())) {
                    newImage.getRiskReportSaleCorrectionList().remove(i);
                }
            }
        }
        return ApiResponse.ok(newImage);
    }

}
