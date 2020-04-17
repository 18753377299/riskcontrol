package com.picc.riskctrl.riskinfo.superRiskReport.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.riskinfo.expert.vo.RiskInfoResponseVo;
import com.picc.riskctrl.riskinfo.report.po.RiskInfoFile;
import com.picc.riskctrl.riskinfo.superRiskReport.service.SuperRiskReportService;
import com.picc.riskctrl.riskinfo.superRiskReport.vo.RiskInfoFileRequestVo;
import com.picc.riskctrl.riskinfo.superRiskReport.vo.SuperReportResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pdfc.framework.web.ApiResponse;

import java.util.List;

/**
 * 优秀风控报告
 *
 * @author wangwenjie
 * @date 2020-01-07
 */
@RestController
@RequestMapping(value = "/riskinfo/superRiskReport")
@Api
public class RiskInfoSuperReportApi extends BaseController {
    public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    @Autowired
    private SuperRiskReportService superRiskReportService;
    @Autowired
    private DataSourcesService dataSourcesService;


    @ApiOperation(value="注销优秀报告",notes="modifyby 周东旭 20200121")
    @RequestMapping(value="/examineOrDeleteFile" ,method = RequestMethod.GET)
    public ApiResponse<RiskInfoFile> examineOrDeleteFile(@RequestParam(value="serialNo") String serialNo,@RequestParam(value="type") String type){
        return superRiskReportService.examineOrDeleteFile(serialNo,type);
    }

    /**
     * @功能：打回优秀报告维护
     * @return AjaxResult
     * @作者：liangshang
     * @日期：20180426
     * @修改记录：
     */
    @ApiOperation(value="打回优秀报告",notes="modifyby 周东旭 20200121")
    @RequestMapping(value="/flightBack",method=RequestMethod.POST)
    public ApiResponse<RiskInfoFile> flightBack(@RequestBody RiskInfoFileRequestVo riskInfoFileRequestVo){
        ApiResponse<RiskInfoFile> api = new ApiResponse<RiskInfoFile>();
        String serialNo = riskInfoFileRequestVo.getRiskInfoFileVo().getSerialNo().toString();
        RiskInfoFile riskInfoFile= new RiskInfoFile();
        try {
            riskInfoFile=superRiskReportService.queryRiskInfoFileBySerialNo(serialNo);
            if(null!=riskInfoFile){
                superRiskReportService.updateRiskInfoFile(riskInfoFileRequestVo.getRiskInfoFileVo(),"flightBackFlag");
                api.setStatus(0);
                api.setStatusText(riskInfoFileRequestVo.getRiskInfoFileVo().getRemark());
            }
        } catch (Exception e) {
            e.printStackTrace();
            api.setStatus(-1);
            api.setStatusText(e.getMessage());
            throw new RuntimeException(e);
        }
        return api;
    }
    /**
     * @功能：优秀报告维护审核通过
     * @return ApiResponse<RiskInfoFile>
     */
    @RequestMapping(value="/checkPass",method=RequestMethod.POST)
    public ApiResponse<RiskInfoFile> checkPass(@RequestBody RiskInfoFileRequestVo riskInfoFileRequestVo){
        ApiResponse<RiskInfoFile> api = new ApiResponse<RiskInfoFile>();
        String serialNo = riskInfoFileRequestVo.getRiskInfoFileVo().getSerialNo().toString();
        RiskInfoFile riskInfoFile=new RiskInfoFile();
        try {
            riskInfoFile=superRiskReportService.queryRiskInfoFileBySerialNo(serialNo);
            if(null!=riskInfoFile){
                superRiskReportService.updateRiskInfoFile(riskInfoFileRequestVo.getRiskInfoFileVo(),"checkPassFlag");
                api.setStatus(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            api.setStatus(-1);
            api.setStatusText(e.getMessage());
            throw new RuntimeException(e);
        }
        return api;
    }

    /**
     * @功能：查询优秀报告维护详细信杯
     * @return ApiResponse<RiskInfoFile>
     * @param serialNo
     * @修改记录：
     */
    @ApiOperation(value="优秀报告维护详细查询",notes="modifyby 周东旭 20200120")
    @RequestMapping(value="/queryFileDetail",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<RiskInfoFile>  queryFileDetail(@RequestParam(value="serialNo") String serialNo){
        ApiResponse<RiskInfoFile> api = new ApiResponse<RiskInfoFile>();
        try {
            RiskInfoFile riskInfoFile = superRiskReportService.queryRiskInfoFileBySerialNo(serialNo);
            api.setData(riskInfoFile);
            api.setStatus(0);
            api.setStatusText("success");
        }catch (Exception e) {
            api.setStatus(-1);
            api.setStatusText(e.getMessage());
            e.printStackTrace();
        }
        return api;
    }


    @ApiOperation(value="优秀报告维护分页查询",notes="modifyby 周东旭 20200120")
    @RequestMapping(value = "/querySetFile",method = RequestMethod.POST)
    public ApiResponse<RiskInfoResponseVo> querySetFile(@RequestBody RiskInfoFileRequestVo riskInfoFileRequestVo) {
        ApiResponse<RiskInfoResponseVo> api = new ApiResponse<RiskInfoResponseVo>();
        //校验岗佝，若是风控信杯审核岗，能够查找到失效的案例信杯
        UserInfo userInfo = getUserInfo();
        try {
            RiskInfoResponseVo riskInfoResponseVo =superRiskReportService.querySetFile(riskInfoFileRequestVo,userInfo);
            api.setData(riskInfoResponseVo);
            api.setStatus(0);
            api.setStatusText("success");
        }catch (Exception e) {
            api.setStatus(-1);
            api.setStatusText(e.getMessage());
            e.printStackTrace();
        }
        return api;
    }

    /**
     * 优秀风控报告查询
     *
     * @param vo
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.riskinfo.superRiskReport.vo.SuperReportResponseVo>
     * @author wangwenjie
     */
    @ApiOperation(value = "优秀风控报告查询", notes = "优秀风控报告查询")
    @PostMapping("/queryFile")
    public ApiResponse<SuperReportResponseVo> queryRiskFile(@RequestBody RiskInfoFileRequestVo vo) {
        String year = vo.getRiskFileVo().getRiskYear();
        if (year.length() > 4) {
            year = String.valueOf(Integer.valueOf(year.substring(0, 4)) + 1);
        }
        vo.getRiskFileVo().setRiskYear(year);
        SuperReportResponseVo reportResponseVo = superRiskReportService.queryRiskFile(vo);
        if (StringUtils.isEmpty(reportResponseVo.getQCexception())) {
            return ok(reportResponseVo);
        } else {
            return error(reportResponseVo);
        }
    }

    /**
     * @param codeType
     * @throws
     * @Description: 查询riskDcode进行中文翻译
     * @author: liqiankun
     * @data: 2018/4/3
     * @return:pdfc.framework.web.ApiResponse
     * @修改记录:modifyby QuLingjie 2020/1/10
     */
    @ApiOperation(value = "查询riskDcode进行中文翻译", notes = "查询riskDcode进行中文翻译")
    @RequestMapping(value = "/riskDCodeQuery", method = {RequestMethod.GET})
    public ApiResponse riskDcodeQuery(@RequestParam(value = "codeType") String codeType) {

        return ApiResponse.ok(dataSourcesService.riskDcodeQuery(codeType));

    }

     /**
     * 优秀报告维护保存
     * @param riskInfoFileVo
     * @return
     */
    @ApiOperation(value = "优秀报告维护保存", notes = "addby 崔凤志 2020/4/14 ")
    @RequestMapping(value = "/saveRiskInfoFileQueryDetail", method = RequestMethod.POST)
    public ApiResponse saveRiskInfoFileQueryDetail(@RequestParam(value = "riskInfoFileRequestVo") String riskInfoFileVo) {
        ApiResponse resp = new ApiResponse();

        //解决无文件上传时无法进入方法问题，从request获取所需文件数据
       /* MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) getRequest();
        List<MultipartFile> uploadFiles= multipartRequest.getFiles("file");*/
        List<MultipartFile> uploadFiles = null;

        //将json转为对象
        String editModel= JSONObject.parseObject(riskInfoFileVo).getString("editModel");
        RiskInfoFileRequestVo riskInfoFileRequestVo = JSON.parseObject(riskInfoFileVo,RiskInfoFileRequestVo.class);
        String riskYear = riskInfoFileRequestVo.getRiskInfoFileVo().getRiskYear();

        if(riskYear.length() > 4) {
            riskYear = String.valueOf(Integer.valueOf(riskYear.substring(0, 4)) + 1);
            riskInfoFileRequestVo.getRiskInfoFileVo().setRiskYear(riskYear);
        }
        riskInfoFileRequestVo.getRiskInfoFileVo().setRiskYear(riskYear);

        String serialNo="";
        Integer serialFileNo =0;
        if(riskInfoFileRequestVo.getRiskInfoFileVo()!=null){
            if(StringUtils.isNotBlank(editModel)){
                if("news".equals(editModel.trim())){
                    serialFileNo = superRiskReportService.getNewSerialNo("fileQueryDetail");
                    riskInfoFileRequestVo.getRiskInfoFileVo().setSerialNo(serialFileNo);
                }
            }
            try {
                //pdf保存
                /*
                if(uploadFiles != null &&uploadFiles.size()>0) {
                    // 获取存储地址
                    ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());

                    //文件浏览路径
                    String projectUrl = bundle.getString("saveTypePath");
                    //保存pdf及转换成jpg文件，供前端预览
                    resp = riskInfoService.savePdf(uploadFiles.get(0),projectUrl,"save","riskfile",riskInfoFileRequestVo.getRiskInfoFileVo().getSerialNo().toString());
                    riskInfoFileRequestVo.getRiskInfoFileVo().setUrl(((String[])resp.getData())[1]);
                }
                */
               // if(resp.getStatus() != 0 || uploadFiles == null || uploadFiles.size() == 0) {
                    serialNo = riskInfoFileRequestVo.getRiskInfoFileVo().getSerialNo().toString();

                    RiskInfoFile riskInfoFile=superRiskReportService.queryRiskInfoFileBySerialNo(serialNo);

                    //判断是否存在，存在更新，不存在，保存
                    if(riskInfoFile==null){
                        superRiskReportService.saveRiskInfoFile(riskInfoFileRequestVo.getRiskInfoFileVo());
                    }else{
                        superRiskReportService.updateRiskInfoFile(riskInfoFileRequestVo.getRiskInfoFileVo(),"");
                    }
                //}
            } catch (Exception e) {
                LOGGER.info( e.getMessage() ,e);
                e.printStackTrace();
                resp.setStatusText(e.getMessage());
                resp.setStatus(1);
                throw new RuntimeException(e);
            }
        }
        return resp;
    }

    /**
     * @功能：
     * @return String
     * @作者：liqiankun
     * @日期：20180404
     * @修改记录：
     */

    @ApiOperation(value = "优秀报告维护--获取最新序列号", notes = "addby 崔凤志 2020/4/16 ")
    @RequestMapping(value = "/getNewSerialNo",method = RequestMethod.GET)
    public ApiResponse  getNewSerialNo(@RequestParam(value="flag") String flag){
        ApiResponse resp = new ApiResponse();
        Integer serialNo=1;
        try{
            serialNo = superRiskReportService.getNewSerialNo(flag);
            resp.setData(serialNo);
        }catch (Exception e){
            resp.setStatusText(e.getMessage());
        }
        return resp;
    }

}
