package com.picc.riskctrl.riskins.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.common.RiskActionExceptionResolver;
import com.picc.riskctrl.common.po.Code;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.po.RiskDnatural;
import com.picc.riskctrl.common.po.RiskReportSaleMain;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.service.BillService;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.service.RiskTimeService;
import com.picc.riskctrl.common.utils.CommonConst;
import com.picc.riskctrl.common.utils.FTPUtil;
import com.picc.riskctrl.riskins.po.RiskDaddress;
import com.picc.riskctrl.riskins.po.RiskReportAssess;
import com.picc.riskctrl.riskins.po.RiskReportMain;
import com.picc.riskctrl.riskins.po.RiskReportPicture;
import com.picc.riskctrl.riskins.service.RiskInsConfigService;
import com.picc.riskctrl.riskins.service.RiskInsImageService;
import com.picc.riskctrl.riskins.service.RiskInsSaleService;
import com.picc.riskctrl.riskins.service.RiskInsService;
import com.picc.riskctrl.riskins.vo.ImageTransferLogRequestVo;
import com.picc.riskctrl.riskins.vo.ImageTransferLogResponseVo;
import com.picc.riskctrl.riskins.vo.RiskControlResponseVo;
import com.picc.riskctrl.riskins.vo.RiskInsGradeVo;
import com.picc.riskctrl.riskins.vo.RiskInsRequestVo;
import com.picc.riskctrl.riskins.vo.RiskInsResponseVo;
import com.picc.riskctrl.riskins.vo.RiskInsRiskReportVo;
import com.picc.riskctrl.riskins.vo.RiskReportMainQueryVo;
import com.picc.riskctrl.riskins.vo.RiskReportMainResponseVo;
import com.picc.riskctrl.riskins.vo.UnfinishBusinessVo;

import freemarker.template.Configuration;
import io.swagger.annotations.ApiOperation;
import pdfc.framework.web.ApiResponse;

@RestController
@RequestMapping("/riskins")
public class RiskInsEditApi extends BaseController {
    public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    private Configuration configuration = new Configuration();

    @Autowired
    private RiskInsConfigService riskInsConfigService;

    @Autowired
    private RiskInsService riskInsService;
    @Autowired
    RiskInsImageService riskInsImageService;
    @Autowired
    private RiskInsSaleService riskInsSaleService;
    @Autowired
    private BillService billService;
    @Autowired
    private RiskTimeService riskTimeService;
    @Autowired
    private DataSourcesService dataSourcesService;

    @ApiOperation(value = "投保险类查询", notes = "modifyby liqiankun 20200113")
    @RequestMapping(value = "/classCodeQuery", method = RequestMethod.POST)
    public ApiResponse<RiskInsResponseVo> queryClassCode() {
         RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();

        List<Code> classCodeList = null;
        try {
            classCodeList = riskInsConfigService.queryClassCode();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
         riskInsResponseVo.setDataList(classCodeList);
        return ApiResponse.ok(riskInsResponseVo);
    }

    @ApiOperation(value = "查勘地址查询", notes = "@param upperCode,gradeFlag ;modifyby liqiankun 20200114")
    @RequestMapping(value = "/queryRiskDaddress")
    public ApiResponse<RiskInsResponseVo> queryRiskDaddress(@RequestParam(value = "upperCode") String upperCode,
                                                             @RequestParam(value = "gradeFlag") String gradeFlag) {
        // List<RiskDaddress> riskDaddressList = new ArrayList<RiskDaddress>();
         RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();

        List<RiskDaddress> riskDaddressList = riskInsService.queryRiskDaddress(null, upperCode, gradeFlag, false);
         riskInsResponseVo.setDataList(riskDaddressList);
        return ApiResponse.ok(riskInsResponseVo);
    }

    @ApiOperation(value = "投保险种查询", notes = "modifyby zrw 20200115")
    @RequestMapping(value = "/riskCodeQuery", method = RequestMethod.GET)
    public ApiResponse<RiskInsResponseVo> queryRiskCode(@RequestParam(value = "classCode") String classCode) {

        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        List<Code> riskCodeList = null;
        UserInfo userInfo = getUserInfo();
        try {
            riskCodeList = riskInsConfigService.queryRiskCode(classCode, userInfo.getComCode());
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        riskInsResponseVo.setDataList(riskCodeList);
        return ApiResponse.ok(riskInsResponseVo);
    }

    /**
     * @param request
     * @param riskFileNo
     * @return
     * @throws Exception
     * @author anqingsen
     */
    @SuppressWarnings({"static-access", "rawtypes"})
    @ApiOperation(value = "风控报告详细信息浏览查询", notes = "modifyby anqingsen 20200114")
    @GetMapping(value = "/queryRiskReport")
    public ApiResponse<RiskInsRiskReportVo> queryRiskInfoExpertDetail(
        @RequestParam(value = "riskFileNo") String riskFileNo) throws Exception {
        RiskInsRiskReportVo riskInsRiskReportVo = new RiskInsRiskReportVo();
        RiskActionExceptionResolver raer = new RiskActionExceptionResolver();
        try {

            // 用户信息
            // UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        	 UserInfo userInfo = getUserInfo();
             String userCode = userInfo.getUserCode();
             String comCode = userInfo.getComCode();
             String riskCode = userInfo.getRiskCode();
            String riskModel = riskFileNo.substring(5, 8);
            // 查询风控报告信息
            RiskReportMain riskReportMain = riskInsService.queryRiskReportMain(riskFileNo);
            // // 投保险种翻译
            // if (StringUtils.isNotBlank(riskReportMain.getRiskCode())) {
            // riskReportMain.setRiskCName(dataSourcesService.queryRiskCodeName(riskReportMain.getRiskCode(), comCode));
            // }
            // // 投保险类翻译
            // if (StringUtils.isNotBlank(riskReportMain.getClassCode())) {
            // try {
            // List<Code> classCodeList = riskInsConfigService.queryClassCode();
            // for (Code code : classCodeList) {
            // if (riskReportMain.getClassCode().equals(code.getCode())) {
            // riskReportMain.setClassCName(code.getName());
            // }
            // }
            // } catch (Exception e) {
            // LOGGER.info( e.getMessage() ,e);
            // e.printStackTrace();
            // String m=raer.getStackTrace(e);
            // riskInsRiskReportVo.setQCexception(m);
            //// throw new RuntimeException(e);
            // }
            // }
            // // 录入人员中文翻译
            // if (StringUtils.isNotBlank(riskReportMain.getOperatorCode())) {
            // riskReportMain.setOperator(riskInsService.queryCodeCName("UserCode", riskReportMain.getOperatorCode(),
            // userCode, comCode, riskCode));
            // }
            // // 查勘人中文翻译
            // if (StringUtils.isNotBlank(riskReportMain.getExplorer())) {
            // riskReportMain.setExplorerCName(riskInsService.queryCodeCName("UserCode", riskReportMain.getExplorer(),
            // userCode, comCode, riskCode));
            // }
            // // 国民经济类型中文翻译
            // if (StringUtils.isNotBlank(riskReportMain.getBusinessSource())) {
            // riskReportMain.setBusinessSourceCName(riskInsService.queryCodeCName("TradeCode",
            // riskReportMain.getBusinessSource(), userCode, comCode, "QBB"));
            // }
            // // 归属机构中文翻译
            // if (StringUtils.isNotBlank(riskReportMain.getExplorecomCode())) {
            // riskReportMain.setComCodeCName(dataSourcesService.queryComCodeCName(riskReportMain.getExplorecomCode()));
            // }
            riskInsRiskReportVo = riskInsService.getObjectVo(riskReportMain);

            if ("001".equals(riskModel) || "002".equals(riskModel) || "006".equals(riskModel)
                || "007".equals(riskModel)) {
                List<RiskDcode> riskDcodeList = riskInsImageService
                    .queryRiskDcodeByRiskModel(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel());
//                String contextPath = request.getContextPath();
                String contextPath = getRequest().getContextPath();

                if (riskDcodeList != null) {
                    riskDcodeList = riskInsService.queryPicture(riskDcodeList, riskFileNo, contextPath);
                }

                riskInsRiskReportVo.setRiskDcodeList(riskDcodeList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            String m = RiskActionExceptionResolver.getStackTrace(e);
            riskInsRiskReportVo.setQCexception(m);
        }
        return ApiResponse.ok(riskInsRiskReportVo);
    }

    /**
     * @param riskInsRequestVo
     * @return RiskInsResponseVo @日期：2017-10-23
     * @功能：风控报告查询
     * @author 李博儒
     */
    @ApiOperation(value = "风控报告模糊查询", notes = "modifyby 张日炜 20200115")
    @RequestMapping(value = "/queryMain", method = RequestMethod.POST)
    public ApiResponse<RiskInsResponseVo> queryRiskReportMain(@RequestBody RiskInsRequestVo riskInsRequestVo) {
//         用户信息
        UserInfo userInfo = getUserInfo();
        if (!userInfo.getIsPC()) {
            riskInsRequestVo.getRiskReportMainVo().setMobFlag(true);
        }
        RiskInsResponseVo riskInsResponseVo = riskInsService.queryRiskIns(riskInsRequestVo, userInfo);

        return ApiResponse.ok(riskInsResponseVo);
    }

    @ApiOperation(value = "影响关联风控报告时组织报保险人及查勘地址", notes = "addby anqingsen 20200116")
    @RequestMapping(value = "/queryAddress", method = RequestMethod.GET)
    public ApiResponse<RiskReportMain> queryAddress(
        @RequestParam(value = "archivesNo") String archivesNo) {
        // 用户信息
//        UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");
        UserInfo userInfo = getUserInfo();
        RiskReportMain riskReportMain = new RiskReportMain();
        String addressProvince;
        String addressCity;
        String addressCounty;
        try {
            RiskReportSaleMain riskReportSaleMain =
                riskInsSaleService.queryRiskReportSaleMain(archivesNo, userInfo, "");
            String companyName = riskReportSaleMain.getCompanyName();
            riskReportMain.setInsuredName(companyName);
            String exploreAddress = riskReportSaleMain.getExploreAddress();
            addressProvince = riskInsService.subStr(exploreAddress, "省", "市", "自治区");

            if (StringUtils.isNotBlank(addressProvince)) {
                riskReportMain.setAddressProvince(addressProvince);
                exploreAddress = exploreAddress.substring(addressProvince.length(), exploreAddress.length());
                List<RiskDaddress> addressList = riskInsService.queryAddressByCName(addressProvince, "", "1");
                if (addressList.size() == 1) {
                    String addressProvinceCode = addressList.get(0).getAddressCode();
                    riskReportMain.setAddressProvinceCode(addressProvinceCode);
                    addressCity = riskInsService.subStr(exploreAddress, "市", "地区", "市辖区", "盟", "行政区划", "自治州", "区", "县");
                    if (StringUtils.isNotBlank(addressCity)) {
                        exploreAddress = exploreAddress.substring(addressCity.length(), exploreAddress.length());
                        List<RiskDaddress> addressCityList = new ArrayList<RiskDaddress>();
                        if ("北京市,上海市,天津市,重庆市".indexOf(addressProvince) > -1) {
                            riskReportMain.setAddressCity("市辖区");
                            riskReportMain.setAddressCounty(addressCity);
                            addressCityList = riskInsService.queryAddressByCName("市辖区", addressProvinceCode, "2");
                            if (addressCityList.size() == 1) {
                                String addressCityCode = addressCityList.get(0).getAddressCode();
                                riskReportMain.setAddressCityCode(addressCityCode);
                                List<RiskDaddress> addressCountyList =
                                        riskInsService.queryAddressByCName(addressCity, addressCityCode, "3");
                                if (addressCountyList.size() == 1) {
                                    riskReportMain.setAddressCountyCode(addressCountyList.get(0).getAddressCode());
                                    riskReportMain.setAddress(addressCountyList.get(0).getAddress());
                                    exploreAddress =
                                            exploreAddress.substring(addressCity.length(), exploreAddress.length());
                                    riskReportMain.setAddressDetail(exploreAddress);
                                }
                            }
                        } else {
                            riskReportMain.setAddressCity(addressCity);
                            addressCityList = riskInsService.queryAddressByCName(addressCity, addressProvinceCode, "2");
                            if (addressCityList.size() == 1) {
                                String addressCityCode = addressCityList.get(0).getAddressCode();
                                riskReportMain.setAddressCityCode(addressCityCode);
                                addressCounty = riskInsService.subStr(exploreAddress, "市", "地区", "市辖区", "盟", "行政区划",
                                        "自治州", "区", "县");
                                if (StringUtils.isNotBlank(addressCounty)) {
                                    riskReportMain.setAddressCounty(addressCounty);
                                    exploreAddress =
                                            exploreAddress.substring(addressCounty.length(), exploreAddress.length());
                                    List<RiskDaddress> addressCountyList =
                                            riskInsService.queryAddressByCName(addressCounty, addressCityCode, "3");
                                    if (addressCountyList.size() == 1) {
                                        riskReportMain.setAddress(addressCountyList.get(0).getAddress());
                                        riskReportMain.setAddressCountyCode(addressCountyList.get(0).getAddressCode());
                                        riskReportMain.setAddressDetail(exploreAddress);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ApiResponse.ok(riskReportMain);
    }

    /**
     * @return RiskInsResponseVo
     * @throws @日期 2018-5-9
     * @功能：查询风控（销售员版）基本信息
     * @author 马军亮
     */
    @ApiOperation(value = "风控（销售员版）基本信息接口", notes = "modifyby 张日炜 20200116")
    @GetMapping(value = "/queryRiskReportSaleMain")
    public ApiResponse<RiskInsResponseVo> queryRiskReportSaleMain(
            @RequestParam(value = "archivesNo") String archivesNo,
            @RequestParam(value = "companyname", required = false) String companyname,
            @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize) {
        // 用户信息
        UserInfo userInfo = getUserInfo();
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        try {
            riskInsResponseVo =
                    riskInsSaleService.queryRiskReportSaleMain(archivesNo, companyname, pageNo, pageSize, userInfo);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ApiResponse.ok(riskInsResponseVo);
    }

    /**
     * @param riskInsRequestVo
     * @return RiskInsResponseVo @日期：2017-10-23
     * @功能：汛期报告查勘地址校验环境信息是否存在
     * @author 周东旭
     */
    @ApiOperation(value = "汛期报告查勘地址校验环境信息是否存在", notes = "modifyby 周东旭 20200116")
    @RequestMapping(value = "/checkRiskDnaturalAddress", method = RequestMethod.GET)
    public ApiResponse<RiskInsResponseVo> checkRiskDnaturalAddress(@RequestParam("addressCode") String addressCode,
                                                                   @RequestParam("addressProCode") String addressProCode, @RequestParam("exploreDate") String exploreDate) {
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        String message = "";
        try {
            // 移动端调用，传兼职版代码
            List<RiskDnatural> riskDnaturalList =
                    riskInsService.checkRiskDnaturalAddress(addressCode, addressProCode, exploreDate, "002");
            if (riskDnaturalList != null && riskDnaturalList.size() > 0) {
                riskInsResponseVo.setDataList(riskDnaturalList);
                message = "yes";
            } else {
                message = "no";
            }
            riskInsResponseVo.setMessage(message);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ApiResponse.ok(riskInsResponseVo);
    }

    @ApiOperation(value = "根据风控报告编号和被保险人名称模糊查询信息", notes = "modifyby 张日炜 20200117")
    @RequestMapping(value = "/queryRiskMainByInsured")
    public ApiResponse<RiskInsResponseVo> queryRiskMainByInsured(
        @RequestParam(value = "riskModel", required = false) String riskModel,
        @RequestParam(value = "riskFileNo", required = false) String riskFileNo,
        @RequestParam(value = "insureName", required = false) String insureName,
        @RequestParam(value = "pageNo", required = true) int pageNo,
        @RequestParam(value = "pageSize", required = false) int pageSize) throws Exception {

        // 用户信息
        UserInfo userInfo = getUserInfo();
        // 查询风控报告信息
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        try {
            riskInsResponseVo =
                    riskInsService.queryRiskMainByInsured(riskFileNo, insureName, pageNo, pageSize, userInfo, riskModel);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return ApiResponse.ok(riskInsResponseVo);
    }

    @ApiOperation(value = "暂存风控报告选择录入页面", notes = "modifyby 张日炜 20200117")
    @RequestMapping(value = "/saveRiskInsDetailInput", method = RequestMethod.POST)
    public ApiResponse<RiskReportMain> saveRiskInsDetailInput(
        @RequestBody RiskInsRiskReportVo riskInsRiskReportVo) throws Exception {
        // 用户信息
        UserInfo userInfo = getUserInfo();
        if (userInfo.getIsPC()) {
            riskInsRiskReportVo.getRiskReportMainVo().setMobileFlag("0");
        }
        // 机构代码
        String comCode = userInfo.getComCode();
        // 集团工号
        String operatorCodeUni = userInfo.getGroupUserCode();
        // 操作人员代码
        String operatorCode = userInfo.getUserCode();
        // UserInfo userInfo = new UserInfo();
        // String comCode = "00000000";
        // String operatorCodeUni = "";
        // String operatorCode = "A000011533";

        // 年份
        int year = Calendar.getInstance().get(Calendar.YEAR);
        RiskReportMain main = new RiskReportMain();
        riskInsRiskReportVo.getRiskReportMainVo().setOperatorCodeUni(operatorCodeUni);
        riskInsRiskReportVo.getRiskReportMainVo().setOperatorCode(operatorCode);
        riskInsRiskReportVo.getRiskReportMainVo().setMadeDate(new Date());
        Date date = riskInsRiskReportVo.getRiskReportMainVo().getExploreDate();
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        // String exploreDate = sdf.format(date);
        String riskModel = riskInsRiskReportVo.getRiskReportMainVo().getRiskModel();
        // 生成新单号并保存入库
        if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel())) {
            String riskFileNo = billService.getNo("RiskReportMain", "riskFileNo",
                    riskInsRiskReportVo.getRiskReportMainVo().getRiskCode(), comCode, year, "",
                    riskInsRiskReportVo.getRiskReportMainVo().getRiskModel());
            riskInsRiskReportVo.getRiskReportMainVo().setRiskFileNo(riskFileNo);
            riskInsRiskReportVo.getRiskReportMainVo().setUnderwriteFlag("T");
            riskInsRiskReportVo.getRiskReportMainVo().setComCode(comCode);
            // 判断是否为复勘
            if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getLastRiskFileNo())) {
                // 通过前风控报告单号查询报告信息
                RiskReportMain riskReportMainLast =
                        riskInsService.queryRiskReportMain(riskInsRiskReportVo.getRiskReportMainVo().getLastRiskFileNo());
                // 组织返回页面Vo
                RiskInsRiskReportVo riskInsRiskReportVoLast = riskInsService.getObjectVo(riskReportMainLast);
                // 复制风控报告信息
                if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getClassCode())) {
                    riskInsRiskReportVoLast.getRiskReportMainVo()
                            .setClassCode(riskInsRiskReportVo.getRiskReportMainVo().getClassCode());
                }
                if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getRiskCode())) {
                    riskInsRiskReportVoLast.getRiskReportMainVo()
                            .setRiskCode(riskInsRiskReportVo.getRiskReportMainVo().getRiskCode());
                }
                if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel())) {
                    riskInsRiskReportVoLast.getRiskReportMainVo()
                            .setRiskModel(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel());
                }
                if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getExploreType())) {
                    riskInsRiskReportVoLast.getRiskReportMainVo()
                            .setExploreType(riskInsRiskReportVo.getRiskReportMainVo().getExploreType());
                }
                riskInsRiskReportVoLast.getRiskReportMainVo()
                        .setLastRiskFileNo(riskInsRiskReportVo.getRiskReportMainVo().getLastRiskFileNo());
                if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getComCode())) {
                    riskInsRiskReportVoLast.getRiskReportMainVo()
                            .setComCode(riskInsRiskReportVo.getRiskReportMainVo().getComCode());
                }
                if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getExplorer())) {
                    riskInsRiskReportVoLast.getRiskReportMainVo()
                            .setExplorer(riskInsRiskReportVo.getRiskReportMainVo().getExplorer());
                }
                riskInsRiskReportVoLast.getRiskReportMainVo()
                        .setInsuredCode(riskInsRiskReportVo.getRiskReportMainVo().getInsuredCode());
                if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getInsuredName())) {
                    riskInsRiskReportVoLast.getRiskReportMainVo()
                            .setInsuredName(riskInsRiskReportVo.getRiskReportMainVo().getInsuredName());
                }
                if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getInsuredType())) {
                    riskInsRiskReportVoLast.getRiskReportMainVo()
                            .setInsuredType(riskInsRiskReportVo.getRiskReportMainVo().getInsuredType());
                }
                riskInsRiskReportVoLast.getRiskReportMainVo()
                        .setExploreDate(riskInsRiskReportVo.getRiskReportMainVo().getExploreDate());
                riskInsRiskReportVoLast.getRiskReportMainVo().setRiskFileNo(riskFileNo);
                riskInsRiskReportVoLast.getRiskReportMainVo().setOperatorCodeUni(operatorCodeUni);
                riskInsRiskReportVoLast.getRiskReportMainVo().setOperatorCode(operatorCode);
                riskInsRiskReportVoLast.getRiskReportMainVo()
                        .setExplorecomCode(riskInsRiskReportVo.getRiskReportMainVo().getExplorecomCode());
                riskInsRiskReportVoLast.getRiskReportMainVo()
                        .setMobileFlag(riskInsRiskReportVo.getRiskReportMainVo().getMobileFlag());
                riskInsRiskReportVoLast.getRiskReportMainVo().setMadeDate(new Date());
                riskInsRiskReportVoLast.getRiskReportMainVo().setRiskSuggest(null);
                riskInsRiskReportVoLast.getRiskReportMainVo().setExecutionId(null);
                riskInsRiskReportVoLast.getRiskReportMainVo().setUnderwriteDate(null);
                riskInsRiskReportVoLast.getRiskReportMainVo().setUndwrtSubmitDate(null);
                riskInsRiskReportVoLast.getRiskReportMainVo().setUnderwriteCode(null);
                riskInsRiskReportVoLast.getRiskReportMainVo().setUnderwriteName(null);
                riskInsRiskReportVoLast.getRiskReportMainVo().setRepulsesugggest(null);
                riskInsRiskReportVoLast.getRiskReportMainVo().setOthSuggest("");
                riskInsRiskReportVoLast.getRiskReportMainVo().setUnderwriteFlag("T");

                if ("001".equals(riskModel) || "002".equals(riskModel) || "006".equals(riskModel)
                        || "007".equals(riskModel)) {
                    // 清空照片档案号
                    riskReportMainLast.setArchivesNo("");
                    // 复制照片档案号
                    if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getArchivesNo())) {
                        riskInsRiskReportVoLast.getRiskReportMainVo()
                                .setArchivesNo(riskInsRiskReportVo.getRiskReportMainVo().getArchivesNo());
                    }
                    riskInsRiskReportVoLast.getRiskReportAssessList().clear();
                    List<RiskReportAssess> riskReportAssessList = new ArrayList<RiskReportAssess>();
                    RiskReportAssess riskReportAssessTemp = new RiskReportAssess();
                    riskReportAssessList.add(riskReportAssessTemp);
                    riskInsRiskReportVoLast.setRiskReportAssessList(riskReportAssessList);
                    riskInsRiskReportVoLast.getRiskReportMainVo().setScore(null);
                    riskInsRiskReportVoLast.getRiskReportMainVo().setUtiWeightId(null);
                    riskInsRiskReportVoLast.getRiskReportMainVo().setHighlightRisk(null);
                }
                riskInsService.saveRiskReportMain(riskInsRiskReportVoLast, userInfo);
                riskTimeService.insertRiskTime(riskFileNo, operatorCode, CommonConst.RC_T_CREATE);
                if ("001".equals(riskModel) || "002".equals(riskModel) || "006".equals(riskModel)
                        || "007".equals(riskModel)) {
                    // 复制影像系统销售员版照片到风控编号节点下
                    // boolean copyFlag = false;
                    String archivesNo = riskInsRiskReportVo.getRiskReportMainVo().getArchivesNo();
                    if (StringUtils.isNotBlank(archivesNo)) {
                        List<Object[]> lists = riskInsSaleService.querySaleImageByArchivesNo_new(archivesNo);
                        riskInsService.copyImagesByArchivesNo(archivesNo, riskFileNo, lists,
                                riskInsRiskReportVo.getRiskReportMainVo().getRiskModel());
                    }
                }
            } else {
                // 说明不是复勘
                if ("004".equals(riskModel) || "005".equals(riskModel)) {
                    riskInsService.saveRiskReportMain(riskInsRiskReportVo, userInfo);
                    riskTimeService.insertRiskTime(riskFileNo, operatorCode, CommonConst.RC_T_CREATE);
                } else {
                    List<RiskReportPicture> riskReportPictureList = new ArrayList<RiskReportPicture>(0);
                    riskInsRiskReportVo.setRiskReportPictureList(riskReportPictureList);
                    // 生成风控报告信息（保存风控基本信息）
                    riskInsService.saveRiskReportMain(riskInsRiskReportVo, userInfo);
                    riskTimeService.insertRiskTime(riskFileNo, operatorCode, CommonConst.RC_T_CREATE);
                    // 复制影像系统销售员版照片到风控编号节点下
                    String archivesNo = riskInsRiskReportVo.getRiskReportMainVo().getArchivesNo();
                    if (StringUtils.isNotBlank(archivesNo)) {
                        List<Object[]> lists = riskInsSaleService.querySaleImageByArchivesNo_new(archivesNo);
                        riskInsService.copyImagesByArchivesNo(archivesNo, riskFileNo, lists,
                                riskInsRiskReportVo.getRiskReportMainVo().getRiskModel());
                    }
                }
            }

            main.setRiskFileNo(riskFileNo);
            // 模板类型
            main.setRiskModel(riskModel);
        }
        return ApiResponse.ok(main);
    }

    @ApiOperation(value = "根据风险档案编号删除风控报告信息", notes = "modifyby liqiankun 20200120")
    @RequestMapping(value = "/riskReportDelete", method = RequestMethod.POST)
    public ApiResponse<String> deleteRiskReportMain(@RequestBody RiskReportMainQueryVo riskReportMainQueryVo) {
//        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        UserInfo userInfo = getUserInfo();
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();

        String message = null;
        List<String> riskFileNoList = riskReportMainQueryVo.getRiskFileNoList();

        try {
            for (String riskFileNo : riskFileNoList) {
                message = riskInsService.deleteRiskInfoByRiskFileNo(riskFileNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "error";
        }
        // 删除影像资料
        message = riskInsImageService.deleteImageList(riskReportMainQueryVo.getRiskFileNoList(), userInfo);
        // 删除本地影像
        riskInsImageService.deleteRiskFile(riskReportMainQueryVo.getRiskFileNoList());
        for (String riskFileNoTem : (List<String>) riskReportMainQueryVo.getRiskFileNoList()) {
            riskTimeService.insertRiskTime(riskFileNoTem, userInfo.getUserCode(), CommonConst.RC_DELETE);
        }
        riskInsResponseVo.setMessage(message);

        return ApiResponse.ok(message);
    }

    @ApiOperation(value = "风控报告查询结果清单导出", notes = "modifyby liqiankun 20200120")
    @RequestMapping(value = "/expertRiskReportMain", method = RequestMethod.POST)
    public ApiResponse<String> expertRiskReportMain(@RequestBody RiskInsRequestVo riskInsRequestVo) {
        //获取用户信息
    	UserInfo userInfo = getUserInfo();

        String data = null;
//		AjaxResult  ajaxResult=new AjaxResult();
        riskInsRequestVo.setPageSize(0);
        riskInsRequestVo.setPageNo(0);
        if (!userInfo.getIsPC()) {
            //判断是否是pc端
            riskInsRequestVo.getRiskReportMainVo().setMobFlag(true);
        }
        //创建HSSFWorkbook对象 (excel的文档对象)
        HSSFWorkbook wkb = new HSSFWorkbook();
        //建立新的sheet对象 （excel的表单）
        HSSFSheet sheet = wkb.createSheet("风控报告查询结果清单");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格 （excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置内容居中
        HSSFCellStyle style = wkb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
//			style.setAlignment(HorizontalAlignment.CENTER);
        //设置字体
        HSSFFont font = wkb.createFont();
        //设置字体的大小
        font.setFontHeightInPoints((short) 11);
        //设置字体加粗
        font.setBold(true);
        //在样式用应用设置的字体;  
        style.setFont(font);
        //设置单元格的内容
        cell.setCellValue("风控报告查询结果清单");
        cell.setCellStyle(style);
        //创建单元格并设置单元格内容
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 10));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(2);
        HSSFCell cell0 = row2.createCell(0);
        cell0.setCellValue("风险编号");
        sheet.setColumnWidth(cell0.getColumnIndex(), 256 * 20);

        HSSFCell cell1 = row2.createCell(1);
        cell1.setCellValue("被保险人");
        sheet.setColumnWidth(cell1.getColumnIndex(), 256 * 20);

        HSSFCell cell2 = row2.createCell(2);
        cell2.setCellValue("投保险种");
        sheet.setColumnWidth(cell2.getColumnIndex(), 256 * 20);

        HSSFCell cell3 = row2.createCell(3);
        cell3.setCellValue("查勘人");
        sheet.setColumnWidth(cell3.getColumnIndex(), 256 * 20);

        HSSFCell cell4 = row2.createCell(4);
        row2.createCell(4).setCellValue("归属机构");
        sheet.setColumnWidth(cell4.getColumnIndex(), 256 * 20);

        HSSFCell cell5 = row2.createCell(5);
        row2.createCell(5).setCellValue("查勘日期");
        sheet.setColumnWidth(cell5.getColumnIndex(), 256 * 20);

        HSSFCell cell6 = row2.createCell(6);
        row2.createCell(6).setCellValue("查勘类别");
        sheet.setColumnWidth(cell6.getColumnIndex(), 256 * 20);

        HSSFCell cell7 = row2.createCell(7);
        row2.createCell(7).setCellValue("审核状态");
        sheet.setColumnWidth(cell7.getColumnIndex(), 256 * 20);

        HSSFCell cell8 = row2.createCell(8);
        row2.createCell(8).setCellValue("风控报告模板类型");
        sheet.setColumnWidth(cell8.getColumnIndex(), 256 * 40);

        HSSFCell cell9 = row2.createCell(9);
        row2.createCell(9).setCellValue("来源");
        sheet.setColumnWidth(cell9.getColumnIndex(), 256 * 20);

        RiskInsResponseVo riskInsResponseVo = riskInsService.queryRiskInsForExport(riskInsRequestVo, userInfo);
        List<RiskReportMainResponseVo> dataList = riskInsResponseVo.getDataList();
        //判断后台查询结果是否有数据  如有 导出表格
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                Date test = dataList.get(0).getExploreDate();
                //创建新的行
                HSSFRow row = sheet.createRow(i + 3);
                //创建每行对应的列
                row.createCell(0).setCellValue(dataList.get(i).getRiskFileNo());//风险编号
                row.createCell(1).setCellValue(dataList.get(i).getInsuredName());//被保险人
                row.createCell(2).setCellValue(dataList.get(i).getRiskCode());//投保险种
                row.createCell(3).setCellValue(dataList.get(i).getExplorer());//查勘人
                row.createCell(4).setCellValue(dataList.get(i).getComCode());//归属机构
                HSSFCell create5 = row.createCell(5);
                if (dataList.get(i).getExploreDate() != null) {
                    create5.setCellValue(dataList.get(i).getExploreDate().toString());//查勘日期
                }
                row.createCell(6).setCellValue(dataList.get(i).getExploreType());//查勘类别
                row.createCell(7).setCellValue(dataList.get(i).getUnderwriteFlag());//审核状态
                row.createCell(8).setCellValue(dataList.get(i).getRiskModel());//风控报告模板类型
                row.createCell(9).setCellValue(dataList.get(i).getMobileFlag());
            }
            OutputStream output = null;
            FTPUtil ftp = new FTPUtil();
            //生成Excel表格文档
            try {
                output = ftp.uploadFile("downloadExcel/riskReportMainList.xls");
                //将文件路径url地址返回页面
//				ajaxResult.setData("/downloadExcel/riskReportMainList.xls");
                data = "/downloadExcel/riskReportMainList.xls";
                wkb.write(output);
                output.flush();
            } catch (Exception e) {
                LOGGER.info(e.getMessage(), e);
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (Exception e2) {
                        LOGGER.error(e2.getMessage(), e2);
                    }

                }
                if (ftp != null) {
                    try {
                        ftp.close();
                    } catch (IOException e) {
                        LOGGER.info("关闭ftp异常：" + e.getMessage(), e);
                    }
                }

            }
        } else {
            //说明没有数据  文件路径返回null
//			ajaxResult.setData(null);
            data = null;
        }
        return ApiResponse.ok(data);
    }

    @ApiOperation(value = "查看风控报告核保状态", notes = "modifyby liqiankun 20200120")
    @GetMapping(value = "/queryRiskInsState")
    public ApiResponse<String> queryRiskInsState(@RequestParam("riskFileNo") String riskFileNo) throws Exception {

//		RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        RiskReportMain riskReportMain = riskInsService.queryRiskReportMain(riskFileNo);
//		riskInsResponseVo.setMessage(riskReportMain.getUnderwriteFlag());

        return ApiResponse.ok(riskReportMain.getUnderwriteFlag());

    }

    @ApiOperation(value = "暂存风控报告录入详细页面并查询回显", notes = "modifyby liqiankun 20200120")
    @RequestMapping(value = "/temporarySave", method = RequestMethod.POST)
    public ApiResponse temporarySave(@RequestBody RiskInsRiskReportVo riskInsRiskReportVo)
            throws Exception {
//		AjaxResult ajaxResult = new AjaxResult();
        ApiResponse apiResponse = new ApiResponse();
        // 用户信息
//        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        UserInfo userInfo =  getUserInfo();
        String userCode = userInfo.getUserCode();
        String comCode = userInfo.getComCode();
        String riskCode = userInfo.getRiskCode();

        String riskModel = riskInsRiskReportVo.getRiskReportMainVo().getRiskModel();

        if (userInfo.getIsPC()) {
            riskInsRiskReportVo.getRiskReportMainVo().setMobileFlag("0");
        } else {
//            String client = request.getHeader("user-agent");
            String client = getRequest().getHeader("user-agent");
            if (client.contains("Android") || client.contains("iPad")) {
                riskInsRiskReportVo.getRiskReportMainVo().setMobileFlag("1");
            } else if (client.contains("iPhone")) {
                riskInsRiskReportVo.getRiskReportMainVo().setMobileFlag("2");
            }
        }
        riskInsRiskReportVo.getRiskReportMainVo().setUnderwriteFlag("T");

//        String protocol = request.getScheme();
        String protocol = getRequest().getScheme();
        if ("001".equals(riskModel) || "002".equals(riskModel) ||
                "006".equals(riskModel) || "007".equals(riskModel)) {
            //获取图片资料
            List<RiskReportPicture> riskReportPictures = riskInsService.getRiskReportPictureList(riskInsRiskReportVo.getRiskDcodeList(), riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo(), userInfo);
            // 保存图片资料
            riskInsRiskReportVo.setRiskReportPictureList(riskReportPictures);
        }
        // 保存风控报告信息
        apiResponse = riskInsService.saveDetailRiskReportMain(riskInsRiskReportVo, null, userInfo, protocol, null);
        if (apiResponse.getStatus() == 0) {
            riskTimeService.insertRiskTime(riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo(),
                    riskInsRiskReportVo.getRiskReportMainVo().getOperatorCode(), CommonConst.RC_T_SAVE);

            // 获取风控报告编号
            String riskFileNo = riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo();

            // 查询回显
            RiskReportMain riskReportMain = riskInsService.queryRiskReportMain(riskFileNo);
            // 投保险种翻译
            if (StringUtils.isNotBlank(riskReportMain.getRiskCode())) {
                riskReportMain
                        .setRiskCName(dataSourcesService.queryRiskCodeName(riskReportMain.getRiskCode(), comCode));
            }
            // 投保险类翻译
            if (StringUtils.isNotBlank(riskReportMain.getClassCode())) {
                try {
//                    List<Code> classCodeList = riskInsConfigService.queryClassCode();
//                    for (Code code : classCodeList) {
//                        if (riskReportMain.getClassCode().equals(code.getCode())) {
//                            riskReportMain.setClassCName(code.getName());
//                        }
//                    }
                } catch (Exception e) {
                    LOGGER.info(e.getMessage(), e);
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            // 查勘人中文翻译
            if (StringUtils.isNotBlank(riskReportMain.getExplorer())) {
//				riskReportMain.setExplorerCName(riskInsService.queryCodeCName("UserCode", riskReportMain.getExplorer(),
//						userCode, comCode, riskCode));
            }
            // 国民经济类型中文翻译
            if (StringUtils.isNotBlank(riskReportMain.getBusinessSource())) {
//				riskReportMain.setBusinessSourceCName(riskInsService.queryCodeCName("TradeCode",
//						riskReportMain.getBusinessSource(), userCode, comCode, "QBB"));
            }
            // 归属机构中文翻译
            if (StringUtils.isNotBlank(riskReportMain.getComCode())) {
//				riskReportMain.setComCodeCName(dataSourcesService.queryComCodeCName(riskReportMain.getComCode()));
            }

            // 组织返回页面Vo
            RiskInsRiskReportVo riskInsRiskReportType = riskInsService.getObjectVo(riskReportMain);

            if ("001".equals(riskModel) || "002".equals(riskModel) ||
                    "006".equals(riskModel) || "007".equals(riskModel)) {
                // 查询影像资料信息
                riskInsRiskReportType.setRiskDcodeList(riskInsRiskReportVo.getRiskDcodeList());
            }
            apiResponse.setData(riskInsRiskReportType);
        }
        return apiResponse;
    }


    /**
     * @param riskFileNo 风控编号
     * @param othSuggest 其他意见
     * @return RiskInsResponseVo @日期：2018-5-24
     * @功能:保存风控报告其他意见
     * @author 马军亮
     * @modify 张日炜 2020-01-20
     */
    @ApiOperation(value = "保存风控报告其他意见", notes = "modifyby 张日炜  20200120")
    @PostMapping(value = "/updateOthSuggest")
    public ApiResponse<RiskInsResponseVo> updateOthSuggest(@RequestBody RiskInsRiskReportVo riskInsRiskReportVo) {
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        try {
            String riskFileNo = riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo();
            String othSuggest = riskInsRiskReportVo.getRiskReportMainVo().getOthSuggest();
            riskInsResponseVo = riskInsService.updateOthSuggest(riskFileNo, othSuggest);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ApiResponse.ok(riskInsResponseVo);
    }

    @ApiOperation(value = "风控打分和保存", notes = "modifyby liqiankun  20200121")
    @RequestMapping(value = "/gradeAndSaveDetail", method = RequestMethod.POST)
    public ApiResponse gradeAndSaveDetail(
    					@RequestBody RiskInsRiskReportVo riskInsRiskReportVo) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            String riskModel = riskInsRiskReportVo.getRiskReportMainVo().getRiskModel();
            // 获取风控报告编号
            String riskFileNo = riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo();
            String protocol = getRequest().getScheme();
//            UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
            UserInfo userInfo = getUserInfo();
            if (riskInsRiskReportVo.getRiskReportMainVo() != null) {
                riskInsRiskReportVo.getRiskReportMainVo().setUnderwriteFlag("0");
                riskInsRiskReportVo.getRiskReportMainVo().setOperatorCode(userInfo.getUserCode());
                // 若是PC端,添加标志位
                if (userInfo.getIsPC()) {
//					String riskFileNoNew = billService.getNo("RiskReportMain", "riskFileNo",
//							riskInsRiskReportVo.getRiskReportMainVo().getRiskCode(), userInfo.getComCode(),
//							Calendar.getInstance().get(Calendar.YEAR), "",
//							riskInsRiskReportVo.getRiskReportMainVo().getRiskModel());
//					riskInsRiskReportVo.getRiskReportMainVo().setRiskFileNo(riskFileNoNew);
//
//				} else {
                    riskInsRiskReportVo.getRiskReportMainVo().setMobileFlag("0");
                } else {
//                    String client = request.getHeader("user-agent");
                    String client = getRequest().getHeader("user-agent");
                    if (client.contains("Android") || client.contains("iPad")) {
                        riskInsRiskReportVo.getRiskReportMainVo().setMobileFlag("1");
                    } else if (client.contains("iPhone")) {
                        riskInsRiskReportVo.getRiskReportMainVo().setMobileFlag("2");
                    }
                }

            }
            if ("001".equals(riskModel) || "002".equals(riskModel) ||
                    "006".equals(riskModel) || "007".equals(riskModel)) {
                // 移动端保存增加打分处理
                RiskInsGradeVo riskInsGradeVo = riskInsService.grade(riskInsRiskReportVo);
                //获取图片资料
                List<RiskReportPicture> riskReportPictures = riskInsService.getRiskReportPictureList(riskInsRiskReportVo.getRiskDcodeList(), riskFileNo, userInfo);
                // 保存图片资料
                riskInsRiskReportVo.setRiskReportPictureList(riskReportPictures);
                // 保存风控报告信息
                apiResponse = riskInsService.saveDetailRiskReportMain(riskInsRiskReportVo, null, userInfo, protocol, riskInsGradeVo);
            } else {
                // 保存风控报告信息
                apiResponse = riskInsService.saveDetailRiskReportMain(riskInsRiskReportVo, null, userInfo, protocol, null);
            }
            //保存成功才可进行下一步
            if (apiResponse.getStatus() == 0) {
                // 查询回显
                RiskReportMain riskReportMain = riskInsService.queryRiskReportMain(riskFileNo);
                // 组织返回页面Vo
                RiskInsRiskReportVo riskInsRiskReportType = riskInsService.getObjectVo(riskReportMain);
                if ("001".equals(riskModel) || "002".equals(riskModel)
                        || "006".equals(riskModel) || "007".equals(riskModel)) {
                    List<RiskDcode> riskDcodeList = riskInsImageService
                            .queryRiskDcodeByRiskModel(riskInsRiskReportType.getRiskReportMainVo().getRiskModel());
                    // 查询影像资料信息 结束
//                    String contextPath = request.getContextPath();
                    String contextPath = getRequest().getContextPath();
                    if (riskDcodeList != null) {
                        riskDcodeList = riskInsService.queryPicture(riskDcodeList, riskFileNo, contextPath);
                    }
                }
//				ajaxResult.setData(riskInsRiskReportType);
//				ajaxResult.setStatus(1);

                apiResponse.setData(riskInsRiskReportType);
                apiResponse.setStatus(0);
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
//			ajaxResult.setStatus(0);
//			ajaxResult.setStatusText(e.getMessage());
            apiResponse.setStatus(1);
            apiResponse.setStatusText(e.getMessage());

            throw new RuntimeException(e);
        }
        return apiResponse;
    }


    /**
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @throws
     * @Description: 查询归属机构
     * @author: QuLingjie
     * @data: 2020/1/21
     * @return:pdfc.framework.web.ApiResponse<com.picc.riskctrl.riskins.vo.RiskInsResponseVo>
     */
    @ApiOperation(value = "查询归属机构", notes = "查询归属机构")
    @GetMapping(value = "/queryComCode")
    public ApiResponse<RiskInsResponseVo> queryComCode(@RequestParam(value = "keyWord") String keyWord,
                                                       @RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize) {

        UserInfo userInfo = getUserInfo();
        RiskInsResponseVo riskInsResponseVo = dataSourcesService.queryComCode(keyWord, pageNo, pageSize,
                userInfo.getComCode());

        return ApiResponse.ok(riskInsResponseVo);

    }
    /**
	 * @功能  查询未审核档案照片
	 * @author 周东旭*/
    @ApiOperation(value = "查询未审核档案照片", notes = "modifyby 张日炜  20200213")
	@RequestMapping(value = "/queryphotoFilesNotAudited", method = RequestMethod.GET)
	public ApiResponse<List<UnfinishBusinessVo>> queryphotoFilesNotAudited(
			@RequestParam(value = "pageNo",  required = true) int pageNo,
			@RequestParam(value = "pageSize",  required = false) int pageSize) {
		return riskInsService.queryphotoFilesNotAudited(getUserInfo().getUserCode(),pageNo,pageSize);

    }
    /**
	 * @功能 按行业大类进行统计审核通过的单子*/
    @ApiOperation(value = " 按行业大类进行统计审核通过的单子", notes = "modifyby liqiankun  20200213")
	@RequestMapping(value = "/queryRiskDecode",method=RequestMethod.GET)
	public ApiResponse queryRiskDecode() {
    	ApiResponse apiResponse = new ApiResponse();
    	apiResponse = riskInsService.queryRiskDecode();
		return apiResponse;
	}
    
    /**
	 * @功能： 近30日的出单量
	 * @author 周东旭*/
    @ApiOperation(value = " 近30日的出单量", notes = "modifyby liqiankun  20200213")
	@RequestMapping(value = "/queryOrders",method=RequestMethod.GET)
	public ApiResponse queryOrders(@RequestParam(value = "madedateBegin") String madedateBegin,
			@RequestParam(value = "madedateEnd",  required = true) String madedateEnd,
			@RequestParam(value = "flag",  required = true) String flag) {
		ApiResponse apiResponse = new ApiResponse();
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
		UserInfo userInfo =getUserInfo();
		String comCode = userInfo.getComCode();
//		String comCode = "32000000";
		apiResponse=riskInsService.queryOrders(comCode,madedateBegin,madedateEnd,flag);
		return apiResponse;
	}

    /**
     * @return RiskInsRiskReportVo
     * @throws @日期 20170319
     * @功能：生成word文件
     * @author liqiankun
     */
    @ApiOperation(value = "生成word文件", notes = "modify by liqiankun 20200205")
    @RequestMapping(value = "/makeWord", method = {RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<String> transferToPdf(@RequestBody RiskInsRiskReportVo riskInsRiskReportVoBefore) throws Exception {
//   		AjaxResult ajaxResult = new AjaxResult();
//   		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
   		UserInfo userInfo = getUserInfo();
   		String userCode = userInfo.getUserCode();
   		String comCode = userInfo.getComCode();
   		String riskCode = userInfo.getRiskCode();
   		String riskFileNo = riskInsRiskReportVoBefore.getRiskReportMainVo().getRiskFileNo();
   		String riskModel = riskInsRiskReportVoBefore.getRiskReportMainVo().getRiskModel();
   		// 查询风控报告信息
   		RiskReportMain riskReportMain = riskInsService.queryRiskReportMain(riskFileNo);
   		// 投保险种翻译
   		if (StringUtils.isNotBlank(riskReportMain.getRiskCode())) {
   			 riskReportMain.setRiskCName(dataSourcesService.queryRiskCodeName(riskReportMain.getRiskCode(), comCode));
//   			riskReportMain.setRiskCName(riskInsRiskReportVoBefore.getRiskReportMainVo().getRiskCName());
        }
        // 投保险类翻译
        if (StringUtils.isNotBlank(riskReportMain.getClassCode())) {
            try {
//   				List<Code> classCodeList = riskInsConfigService.queryClassCode();
//   				for (Code code : classCodeList) {
//   					if (riskReportMain.getClassCode().equals(code.getCode())) {
//   						riskReportMain.setClassCName(code.getName());
//   					}
//   				}
            } catch (Exception e) {
                LOGGER.info(e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        // 录入人员中文翻译
        if (StringUtils.isNotBlank(riskReportMain.getOperatorCode())) {
//   			riskReportMain.setOperator(riskInsService.queryCodeCName("UserCode", riskReportMain.getOperatorCode(),userCode,comCode,riskCode)); 
        }
        // 查勘人中文翻译
        if (StringUtils.isNotBlank(riskReportMain.getExplorer())) {
//   			riskReportMain.setExplorerCName(riskInsService.queryCodeCName("UserCode", riskReportMain.getExplorer(),
//   					userCode, comCode, riskCode));
        }
        //userCode中文翻译
        if (StringUtils.isNotBlank(userInfo.getUserCode())) {
//   			userInfo.setUserCode(riskInsService.queryCodeCName("UserCode", userInfo.getUserCode(),userCode,comCode,riskCode)); 
        }
        // 国民经济类型中文翻译
        if (StringUtils.isNotBlank(riskReportMain.getBusinessSource())) {
//   			riskReportMain.setBusinessSourceCName(riskInsService.queryCodeCName("TradeCode",
//   							riskReportMain.getBusinessSource(), userCode, comCode, "QBB"));
        }
        // 归属机构中文翻译
        if (StringUtils.isNotBlank(riskReportMain.getComCode())) {
//   			 riskReportMain.setComCodeCName(dataSourcesService.queryComCodeCName(riskReportMain.getExplorecomCode()));
        }

        // 查勘机构中文翻译
        if (StringUtils.isNotBlank(riskReportMain.getExplorecomCode())) {
//   			 riskReportMain.setComCodeCName(riskInsService.queryComCodeCName(riskReportMain.getComCode()));
            //翻译
//   			riskReportMain.setExplorecomCode(dataSourcesService.queryComCodeCName(riskReportMain.getExplorecomCode()));
        }

        RiskInsRiskReportVo riskInsRiskReportVo = riskInsService.getObjectVo(riskReportMain);
        // 转换成中文
        riskInsRiskReportVo.getRiskReportMainVo()
                .setAddress(riskInsRiskReportVoBefore.getRiskReportMainVo().getAddress());
        riskInsRiskReportVo = riskInsService.transferToChinese(riskInsRiskReportVo);
        if ("001".equals(riskModel) || "002".equals(riskModel)) {
            riskInsRiskReportVo.setPicBase64Info(riskInsRiskReportVoBefore.getPicBase64Info());
            List<RiskDcode> riskDcodeList = riskInsService.setUrlToBase64(riskInsRiskReportVoBefore.getRiskDcodeList());
//   			List<RiskDcode> riskDcodeList = null;
            riskInsRiskReportVo.setRiskDcodeList(riskDcodeList);
        }
        // 生成word文档
//   		String transferToWord = "";
   		String transferToWord = riskInsService.transferToWord(riskInsRiskReportVo, configuration,userInfo);
//   		ajaxResult.setData(transferToWord);

        return ApiResponse.ok(transferToWord);
    }

    /**
     * @return AjaxResult
     * @功能:按照单号对影像上传的异常队列信息的查询
     * @author 王坤龙
     * @RequestParam businessNo
     * @日期：2018-7-30
     */
    @ApiOperation(value = "按照单号对影像上传的异常队列信息的查询", notes = "modifyby liqiankun 20200224")
    @RequestMapping(value = "/imgTransferLogSolve")
    public ApiResponse queryImageTransferLog(@RequestParam(value = "businessNo") String businessNo) {
//		AjaxResult ajaxResult = null;
        ApiResponse apiResponse = null;
        // 用户信息
//		UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        UserInfo userInfo = getUserInfo();
//        userInfo.setUserCode("A000011533");
        // 根据单号查出该条异常队列信息
//		ajaxResult = riskInsService.handleQueryImageTransferLogByID(businessNo, userInfo);
//		apiResponse = riskInsService.handleQueryImageTransferLogByID(businessNo, userInfo);

        return apiResponse;
    }

    /**
     * @param ImageTransferLogRequestVo
     * @return ImageTransferLogResponseVo
     * @功能:对影像上传的异常队列信息的查询
     * @author 王坤龙
     * @日期：2018-7-30
     */
    @ApiOperation(value = "对影像上传的异常队列信息的查询", notes = "modifyby 张日炜  20200224")
    @RequestMapping(value = "/imgTransferLogQuery", method = RequestMethod.POST)
    public ApiResponse<ImageTransferLogResponseVo> queryImageTransferLog(@RequestBody ImageTransferLogRequestVo riskImgRequestVo) {

        // 用户信息
        UserInfo userInfo = getUserInfo();

        ImageTransferLogResponseVo imageTransferLogResponseVo = null;
//        		riskInsService.queryImageTransferLog(riskImgRequestVo, userInfo);

        return ApiResponse.ok(imageTransferLogResponseVo);

    }

    /**
     * @Description: 火灾报告下载
     * @Author: wangwenjie
     * @Params: [fireNo 火灾编号, flag 下载报告类型, request]
     * @Return: ins.framework.web.AjaxResult
     * @Date: 2019/7/23
     * @修改： 周东旭
     * @时间 ：2020-02-28
     */
    @GetMapping("/downloadFireReport")
    public ApiResponse downloadFireReport(String fireNo, String flag) {
        UserInfo userInfo = getUserInfo();
        ApiResponse api = new ApiResponse();
        if (StringUtils.isNotBlank(fireNo)) {
            try {
                String reportUrl =  riskInsService.downloadFireReport(fireNo, flag, userInfo);
                api.setData(reportUrl);
                api.setStatus(0);
            } catch (Exception e) {
                api.setStatus(1);
                api.setStatusText(e.getMessage());
            }
        } else {
            api.setStatus(1);
            api.setStatusText("数据不存在!");
        }
        return api;
    }

    /**
     * @param riskInsRequestVo
     * @return RiskInsResponseVo @日期：2017-10-23
     * @功能：外系统风控报告查询
     * @author liqiankun
     */
    @ApiOperation(value = "外系统风控报告查询", notes = "modify by liqiankun 20200207")
    @RequestMapping(value = "/queryOuterMain", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RiskInsResponseVo queryOuterRiskReportMain(
                                                      @RequestParam("riskInsRequestVoJson") String riskInsRequestVoJson) {

        RiskInsRequestVo riskInsRequestVo = JSON.parseObject(riskInsRequestVoJson,
                RiskInsRequestVo.class);

//        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        UserInfo userInfo =getUserInfo();
        RiskInsResponseVo riskInsResponseVo = riskInsService.queryRiskIns(riskInsRequestVo, userInfo);

        return riskInsResponseVo;

    }

    /**
     * @param requestJsonStr
     * @return String @日期：2017-12-13
     * @功能：根据风控报告编号查询(提供非车接口服务) @author 王亚军
     */
    @ApiOperation(value = "根据风控报告编号查询(提供非车接口服务)", notes = "modify by liqiankun 20200207")
	@RequestMapping(value = "/queryOutlineByRiskFileNo", method = RequestMethod.POST)
	public String queryByRiskFileNo(String riskControlRequest) {
		// 接收请求报文json字符串转为对象，通过风控编号查询
		RiskControlResponseVo riskControlResponseVo = riskInsService.queryByRiskFileNo(riskControlRequest);
		String ResponseJsonStr = JSON.toJSONString(riskControlResponseVo, SerializerFeature.UseISO8601DateFormat);
		return ResponseJsonStr;
	}
    /**
	 * @功能：复勘风控报告时组织被保险人及查勘地址
	 * @author 马军亮
	 * @return RiskReportMain
	 * @throws @日期
	 *             2018-5-17
	 */
    @ApiOperation(value = "复勘风控报告时组织被保险人及查勘地址，校验初堪编号", notes = "modify by liqiankun 20200304")
	@RequestMapping(value = "/queryAddressByRiskFileNo", method = RequestMethod.GET)
	public ApiResponse<RiskReportMain>  queryAddressByRiskFileNo(@RequestParam(value = "lastRiskFileNo") String lastRiskFileNo) {
		// 用户信息
		RiskReportMain riskReportMain = new RiskReportMain();
		try {
			riskReportMain = riskInsService.queryRiskReportMain(lastRiskFileNo);
			if (StringUtils.isNotBlank(riskReportMain.getAddressProvince())) {
				riskReportMain.setAddressProvince(riskReportMain.getAddressProvince());
				List<RiskDaddress> addressList = riskInsService.queryAddressByCName(riskReportMain.getAddressProvince(), "", "1");
				if (addressList.size() == 1) {
					String addressProvinceCode = addressList.get(0).getAddressCode();
					riskReportMain.setAddressProvinceCode(addressProvinceCode);
					if (StringUtils.isNotBlank(riskReportMain.getAddressCity())) {
						List<RiskDaddress> addressCityList = new ArrayList<RiskDaddress>();
						addressCityList = riskInsService.queryAddressByCName(riskReportMain.getAddressCity(), addressProvinceCode, "2");
						if (addressCityList.size() == 1) {
							String addressCityCode = addressCityList.get(0).getAddressCode();
							riskReportMain.setAddressCityCode(addressCityCode);
							if (StringUtils.isNotBlank(riskReportMain.getAddressCounty())) {
								List<RiskDaddress> addressCountyList = riskInsService
										.queryAddressByCName(riskReportMain.getAddressCounty(), addressCityCode, "3");

								String addressDetail = riskReportMain.getAddressDetail();
								if(StringUtils.isNotBlank(addressDetail)) {
									int index = addressDetail.indexOf(riskReportMain.getAddressCounty());
									int beginIndex = index + riskReportMain.getAddressCounty().length();
									addressDetail = addressDetail.substring(beginIndex, addressDetail.length());
								}
								if (addressCountyList.size() == 1) {
									riskReportMain.setAddress(addressCountyList.get(0).getAddress());
									riskReportMain.setAddressCountyCode(addressCountyList.get(0).getAddressCode());
									riskReportMain.setAddressDetail(addressDetail);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error( e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return ApiResponse.ok(riskReportMain);
	}
	/**
	 * @功能  查询风控报告未审核单
	 * @author 周东旭*/
    @ApiOperation(value = "查询风控报告未审核单", notes = "modifyby 张日炜  20200214")
	@RequestMapping(value = "/querycontrolReport", method = RequestMethod.GET)
	public ApiResponse<List<UnfinishBusinessVo>> querycontrolReport(
			@RequestParam(value = "riskInsUnderFirFlag") String riskInsUnderFirFlag,
			@RequestParam(value = "riskInsUnderSecFlag") String riskInsUnderSecFlag,
			@RequestParam(value = "pageNo",  required = true) int pageNo,
			@RequestParam(value = "pageSize",  required = false) int pageSize) {
		return riskInsService.querycontrolReport(getUserInfo().getUserCode(),riskInsUnderFirFlag,riskInsUnderSecFlag,pageNo,pageSize);
	}
	/**
	 * @功能  查询已反馈照片档案
	 * @author 周东旭*/
    @ApiOperation(value = "查询已反馈照片档案", notes = "modifyby 张日炜  20200214")
	@RequestMapping(value = "/queryphotoFileHasBeenFeedback", method = RequestMethod.GET)
	public ApiResponse<List<UnfinishBusinessVo>> queryphotoFileHasBeenFeedback(@RequestParam(value = "pageNo",  required = true) int pageNo,
			@RequestParam(value = "pageSize",  required = false) int pageSize) {
		return riskInsService.queryphotoFileHasBeenFeedback(getUserInfo().getUserCode(),pageNo,pageSize);
	}
    /**
     * @功能  查询风控报告打回单
     * @author 周东旭*/
    @ApiOperation(value = "查询风控报告打回单", notes = "modifyby 张日炜  20200318")
    @RequestMapping(value = "/querycallBack", method = RequestMethod.GET)
    public ApiResponse<List<UnfinishBusinessVo>> querycallBack(
                                    @RequestParam(value = "pageNo",  required = true) int pageNo,
                                    @RequestParam(value = "pageSize",  required = false) int pageSize) {
        return riskInsService.querycallBack(getUserInfo().getUserCode(),pageNo,pageSize);

    }

       
}
