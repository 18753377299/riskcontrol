package com.picc.riskctrl.riskinfo.superRiskReport.service;

import com.picc.riskctrl.base.BaseService;
import com.picc.riskctrl.common.RiskActionExceptionResolver;
import com.picc.riskctrl.common.po.RiskDcode;
import com.picc.riskctrl.common.po.RiskDcodeId;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.utils.BeanCopyUtils;
import com.picc.riskctrl.common.utils.QueryRule;
import com.picc.riskctrl.common.utils.ResultPageUtils;
import com.picc.riskctrl.riskinfo.claim.dao.RiskInfoClaimDao;
import com.picc.riskctrl.riskinfo.expert.vo.RiskInfoResponseVo;
import com.picc.riskctrl.riskinfo.report.po.RiskInfoFile;
import com.picc.riskctrl.riskinfo.superRiskReport.dao.RiskInfoFileDao;
import com.picc.riskctrl.riskinfo.superRiskReport.dao.SuperRiskReportDao;
import com.picc.riskctrl.riskinfo.superRiskReport.po.SuperRiskReport;
import com.picc.riskctrl.riskinfo.superRiskReport.vo.RiskFileVo;
import com.picc.riskctrl.riskinfo.superRiskReport.vo.RiskInfoFileRequestVo;
import com.picc.riskctrl.riskinfo.superRiskReport.vo.RiskInfoFileVo;
import com.picc.riskctrl.riskinfo.superRiskReport.vo.SuperReportResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdfc.framework.web.ApiResponse;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 优秀风控报告
 *
 * @author wangwenjie
 * @date 2020-01-07
 */
@Service
@Transactional
@Slf4j
public class SuperRiskReportService extends BaseService {
    public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    @Autowired
    private SuperRiskReportDao superRiskReportDao;
    @Autowired
    private RiskInfoClaimDao riskInfoClaimDao;
    @Autowired
    private DataSourcesService dataSourcesService;
    @Autowired
    private RiskInfoFileDao riskInfoFileDao;

    /**
     * @功能：优秀报告维护打回功能
     * @作者：liqiankun
     * @日期：20180404
     * @修改记录：
     */
    public void updateRiskInfoFile(RiskInfoFileVo riskInfoFileVo, String checkPassFlag) {
        try {
            RiskInfoFile riskInfoFileNew=new RiskInfoFile();
            BeanUtils.copyProperties(riskInfoFileVo, riskInfoFileNew);
            if(null!=riskInfoFileNew){
                if("checkPassFlag".equals(checkPassFlag)){
                    riskInfoFileNew.setValidStatus("1");
                }else if ("flightBackFlag".equals(checkPassFlag)) {
                    riskInfoFileNew.setValidStatus("2");
                    riskInfoFileNew.setRemark(riskInfoFileVo.getRemark());
                }else{
                    riskInfoFileNew.setValidStatus("0");
                }
                this.riskInfoFileDao.save(riskInfoFileNew);
            }
        } catch (Exception e) {
            LOGGER.error("更新异常：" + e.getMessage() ,e);
            e.printStackTrace();
            throw new RuntimeException("更新异常:"+e);
        }
    }

    /**
     * @author 周东旭
     * @功能 查询优秀报告维护详细信息
     * @param serialNo
     * @return RiskInfoFile*/
    public RiskInfoFile queryRiskInfoFileBySerialNo(String serialNo) {
        RiskInfoFile riskInfoFile = new RiskInfoFile();
        try {
            if(StringUtils.isNotBlank(serialNo)){
                Optional<RiskInfoFile> riskInfoFileOne=this.riskInfoFileDao.findById(Integer.parseInt(serialNo.trim()));
                if (riskInfoFileOne.isPresent()) {
                    riskInfoFile = riskInfoFileOne.get();
                }
            }
        } catch (Exception e) {
            LOGGER.error("查询异常：" + e.getMessage() ,e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:"+e);
        }
        return riskInfoFile;
    }

    /**
     * @author 周东旭
     * @功能 分页查询优秀报告维护
     * @param RiskInfoFileRequestVo
     * @return RiskInfoResponseVo*/
    public RiskInfoResponseVo querySetFile(RiskInfoFileRequestVo riskInfoFileRequestVo, UserInfo userInfo) {
        RiskInfoResponseVo riskInfoResponseVo=new RiskInfoResponseVo();
        try {
            String postList ="riskins_undsec-风控报告二级审核岗,riskins_set-风控信息维护岗,riskins_undfir-风控报告一级审核岗,riskins_input-风控报告录入岗,riskins-风控结果查询岗,riskinsexp_set-风控专家维护岗,riskcheck_inputAndQuery-风控巡检岗,risksale_collect-照片档案采集岗,riskins_und-风控信息审核岗,riskset-风控管理岗,riskcheck_distribute-风控巡检派发岗,riskins_query-风控报告查询岗,risksale_examine-照片档案审核岗,riskinfo-风控信息岗";
            RiskFileVo riskFileVo =riskInfoFileRequestVo.getRiskFileVo();
            if(riskFileVo!=null){
                //获取是默认、险种、年度、赔款金额--排序标志位
                String orderColum= riskInfoFileRequestVo.getOrderColumn().trim();
                String orderType = riskInfoFileRequestVo.getOrderType().trim();
                Specification<RiskInfoFile> spec = new Specification<RiskInfoFile>() {
                    @Override
                    public Predicate toPredicate(Root<RiskInfoFile> root, CriteriaQuery<?> query,
                                                 CriteriaBuilder cb) {
                        List<Predicate> list = new ArrayList<>();
                        if(org.apache.commons.lang3.StringUtils.isNotBlank(riskFileVo.getRiskFileName())){
                            list.add(cb.like(root.get("riskFileName"), riskFileVo.getRiskFileName().trim()+"%"));
                        }
                        String validStatus=riskFileVo.getValidStatus();
                        List<String> validStatusList = new ArrayList<String>();
//							风控信息审核岗		风控信息维护岗
                        if(postList.toString().indexOf("风控信息审核岗")>-1){
                            if(validStatus.indexOf("0")>-1){
                                validStatusList.add("0");
                            }
                            if(validStatus.indexOf("2")>-1){
                                validStatusList.add("2");
                            }
                            if(validStatus.indexOf("1")>-1){
                                validStatusList.add("1");
                            }
                            if(null!=validStatusList&&validStatusList.size()>0){
                                list.add(root.get("validStatus").in(validStatusList));
                            }
                        }else if(postList.toString().indexOf("风控信息维护岗")>-1){
                            List<Predicate> list2 = new ArrayList<>();
                            String sqlBuf="",status="";
                            if(validStatus.indexOf("0")>-1||validStatus.indexOf("2")>-1){
                                list2.add(cb.equal(root.get("operatorCode"), userInfo.getUserCode().trim()));
                                if(validStatus.indexOf("0")>-1){
                                    status+="'0',";
                                }
                                if(validStatus.indexOf("2")>-1){
                                    status+="'2'";
                                }
                                if(status.length()>0&&status.endsWith(",")){
                                    status=status.substring(0,status.length()-1);
                                    list2.add(root.get("validstatus").in(status));

                                }else if(status.length()>0){
                                    list2.add(root.get("validstatus").in(status));
                                }
                                if(validStatus.indexOf("1")>-1){
                                    Predicate p= cb.equal(root.get("validstatus"),"1");
                                    list2.add(cb.or(p));
                                }
                            }else if(validStatus.indexOf("1")>-1){
                                sqlBuf+=" this_.VALIDSTATUS ='1' ";
                                list2.add(cb.equal(root.get("validstatus"), "1"));
                            }
                            Predicate[] predicate1 = new Predicate[status.length()];
                            list.add(cb.and(list2.toArray(predicate1)));
                        }
                        Predicate[] arr = new Predicate[list.size()];
                        return cb.and(list.toArray(arr));
                    }
                };
                Sort.Direction direction =Sort.Direction.ASC;
                //有关升降序号的处理
                if(StringUtils.isNotBlank(orderColum)){
                    //默认做升序处理
                    if("serialNo".equals(orderColum)){
                        direction =Sort.Direction.ASC;
                    }else{
                        if(StringUtils.isNotBlank(orderType)){
                            if("true".equals(orderType)){
                                direction=Sort.Direction.DESC;
                            }else if("false".equals(orderType)){
                                direction =Sort.Direction.ASC;
                            }
                        }
                    }
                }

				/*
				 *
				 * //权限校验开始-----
				 */
//				String userCodeInfo =userInfo.getUserCode();
//				SaaAPIService saaAPIService = new SaaAPIServiceImpl();
//				try {
//					String powerSQL = saaAPIService.addPower("riskcontrol", userCodeInfo, "riskset", "this_.comCode","", "");
//					queryRule.addSql(powerSQL);
//				} catch (Exception e) {
//					LOGGER.info("addPower执行异常：" + e.getMessage() ,e);
//					e.printStackTrace();
//					throw new RuntimeException("addPower执行异常:"+e);
//				}
                int pageNo = riskInfoFileRequestVo.getPageNo();
                int pageSize = riskInfoFileRequestVo.getPageSize();
                try {
                    Page page = this.riskInfoFileDao.findAll(spec,
                            PageRequest.of(pageNo - 1, pageSize,Sort.by(direction,riskInfoFileRequestVo.getOrderColumn())));
                    riskInfoResponseVo.setDataList(page.getContent());
                    riskInfoResponseVo.setTotalCount(page.getTotalElements());
                    riskInfoResponseVo.setTotalPage(page.getTotalPages());
//					page= databaseDao.findPage(RiskInfoFile.class, queryRule, riskInfoFileRequestVo.getPageNo(), riskInfoFileRequestVo.getPageSize());
                } catch (Exception e) {
                    LOGGER.error("查询异常：" + e.getMessage() ,e);
                    e.printStackTrace();
                    throw new RuntimeException("查询异常:"+e);
                }
            }
            //部分代码翻译
            List<RiskInfoFile> resultList = riskInfoResponseVo.getDataList(); // riskDnaturalResponseVo
            for(int i=0;i<resultList.size();i++) {
                RiskInfoFile riskinfoFile = (RiskInfoFile)resultList.get(i);
                riskinfoFile.setRiskNameC(dataSourcesService.getRiskCName(riskinfoFile.getRiskName()));
                if(StringUtils.isBlank(riskinfoFile.getRiskName())){
                    riskinfoFile.setRiskNameC("未说明");
                }
                if(StringUtils.isBlank(riskinfoFile.getRiskYear())){
                    riskinfoFile.setRiskYear("未说明");
                }
                if(StringUtils.isNotBlank(riskinfoFile.getValidStatus())) {
                    if("1".equals(riskinfoFile.getValidStatus())) {
                        riskinfoFile.setValidStatusName("已通过");
                    }else if("0".equals(riskinfoFile.getValidStatus())) {
                        riskinfoFile.setValidStatusName("未审核");
                    }else if("2".equals(riskinfoFile.getValidStatus())) {
                        riskinfoFile.setValidStatusName("未通过");
                    }
                }
                //				if(StringUtils.isNotBlank(riskinfoFile.getAscName())){
//					String  [] codeCname =(riskinfoFile.getAscName()).split(",");
//					for(int j=0;j<codeCname.length;j++){
//						riskinfoFile.setAscName(dataSourcesService.queryRiskDcode("riskFileAscName","",codeCname[j],"1").getCodeCname());
//
//					}
//				}
//
//				if(StringUtils.isNotBlank(riskinfoFile.getSender())){
//					String [] codeCname =(riskinfoFile.getSender().split(","));
//					for(int j=0;j<codeCname.length;j++){
//						riskinfoFile.setSender(dataSourcesService.queryRiskDcode("riskFileSender","",codeCname[j],"1").getCodeCname());
//					}
//				}
//
//				if(StringUtils.isNotBlank(riskinfoFile.getProfession())){
//					String  [] codeCname =(riskinfoFile.getProfession()).split(",");
//					for(int j=0;j<codeCname.length;j++){
//						riskinfoFile.setProfession(dataSourcesService.queryRiskDcode("profession","",codeCname[j],"1").getCodeCname());
//					}
//				}
                resultList.set(i, riskinfoFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return riskInfoResponseVo;
    }

    /**
     * @功能：删除案例信息（即置有效标志位）
     */
    public ApiResponse<RiskInfoFile> examineOrDeleteFile(String serialNo, String type) {
        ApiResponse<RiskInfoFile> api = new ApiResponse<RiskInfoFile>();
        RiskInfoFile riskInfoFile=new RiskInfoFile();
        try {
            riskInfoFile=queryRiskInfoFileBySerialNo(serialNo);
            if("delete".equals(type)) {
                riskInfoFile.setValidStatus("0");
            }else if("examine".equals(type)) {
                riskInfoFile.setValidStatus("1");
            }
            this.riskInfoFileDao.save(riskInfoFile);
            api.setStatus(0);
        }catch (Exception e) {
            LOGGER.error("更新异常：" + e.getMessage() ,e);
            api.setStatus(-1);
            api.setStatusText(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("更新异常:"+e);
        }
        return api;
    }

    /**
     * 优秀风控报告查询
     *
     * @param vo
     * @return com.picc.riskctrl.riskinfo.superRiskReport.vo.SuperReportResponseVo
     * @author wangwenjie
     */
    public SuperReportResponseVo queryRiskFile(RiskInfoFileRequestVo vo) {
        SuperReportResponseVo responseVo = new SuperReportResponseVo();
        RiskFileVo riskFileVo = vo.getRiskFileVo();
        try {
            if (riskFileVo != null) {
                QueryRule<SuperRiskReport> queryRule = QueryRule.getInstance();

                //已选条件
                List conditions = new ArrayList<>();

                String riskFileName = vo.getRiskFileVo().getRiskFileName().trim();
                if (StringUtils.isNotBlank(riskFileName)) {
                    queryRule.addLike("riskFileName", riskFileName + "%");
                }
                //出具报告年度
                String riskYear = vo.getRiskFileVo().getRiskYear().trim();
                if (StringUtils.isNotBlank(riskYear)) {
                    queryRule.addEqual("riskYear", riskYear);
                }
                //行业
                String professions = vo.getRiskFileVo().getProfessions();
                if (!"[]".equals(professions)) {
                    professions = removeSpeSymbolsReg(professions);
                    String[] professionType = professions.split(",");
                    for (int i = 0; i < professionType.length; i++) {
                        String type = professionType[i];
                        RiskDcode riskDcode = dataSourcesService.queryRiskDcode("profession", "", type, "1");
                        conditions.add(riskDcode);
                        professionType[i] = "%" + professionType[i] + "%";
                    }
                    queryRule.addLike("profession", professionType);
                }
                //险种riskName
                String riskNames = vo.getRiskFileVo().getRiskNames();
                if (!"[]".equals(riskNames)) {
                    riskNames = removeSpeSymbolsReg(riskNames);
                    String[] riskNameArray = riskNames.split(",");
                    for (int i = 0; i < riskNameArray.length; i++) {
                        String riskName = riskNameArray[i];
                        String codeCname = dataSourcesService.getRiskCName(riskName.trim());
                        RiskDcode riskDcode = new RiskDcode();
                        riskDcode.setCodeCname(codeCname);
                        RiskDcodeId id = new RiskDcodeId();
                        id.setCodeCode(riskName.trim());
                        id.setCodeType("riskName");
                        riskDcode.setId(id);
                        conditions.add(riskDcode);
                        riskNameArray[i] = "%" + riskName + "%";
                    }
                    queryRule.addLike("riskName", riskNameArray);
                }
                //出具报告机构
                String senders = vo.getRiskFileVo().getSenders();
                if (!"[]".equals(senders)) {
                    senders = removeSpeSymbolsReg(senders);
                    String[] sendersArray = senders.split(",");
                    for (int i = 0; i < sendersArray.length; i++) {
                        String sender = sendersArray[i];
                        RiskDcode riskDcode = dataSourcesService.queryRiskDcode("riskFileSender", "", sender, "1");
                        conditions.add(riskDcode);
                        sendersArray[i] = "%" + sender + "%";
                    }
                    queryRule.addLike("sender", sendersArray);
                }
                //风控报告来源
                String ascNames = vo.getRiskFileVo().getAscNames();
                if (!"[]".equals(ascNames)) {
                    ascNames = removeSpeSymbolsReg(ascNames);
                    String[] ascNameArray = ascNames.split(",");
                    for (int i = 0; i < ascNameArray.length; i++) {
                        String ascName = ascNameArray[i];
                        RiskDcode riskDcode = dataSourcesService.queryRiskDcode("riskFileAscName", "", ascName, "1");
                        conditions.add(riskDcode);
                        ascNameArray[i] = "%" + ascName + "%";
                    }
                    queryRule.addLike("ascName", ascNameArray);
                }
                //有效状态
                queryRule.addEqual("validStatus", "1");

                //排序规则
                String orderColumn = vo.getOrderColumn().trim();
                String orderType = vo.getOrderType().trim();
                //分页
                if (StringUtils.isNotBlank(orderColumn)) {
                    if ("serialNo".equals(orderColumn)) {
                        queryRule.addAscOrder("serialNo");
                    } else {
                        if (StringUtils.isNotEmpty(orderType)) {
                            if ("true".equals(orderType)) {
                                queryRule.addDescOrder(orderColumn);
                            } else if ("false".equals(orderType)) {
                                queryRule.addAscOrder(orderColumn);
                            }
                            //解决数据排序时导致的数据重复问题，增加序号排序
                            queryRule.addAscOrder("serialNo");
                        }
                    }
                }
                Page<SuperRiskReport> page = superRiskReportDao.findAll(queryRule, vo.getPageNo(), vo.getPageSize());

                //翻译查询结果集
                List<SuperRiskReport> sourceContent = page.getContent();

                //List深度拷贝
                List<SuperRiskReport> content = BeanCopyUtils.copyPropertiesList(sourceContent);

                //有效状态翻译
                for (SuperRiskReport report : content) {
                    String status = report.getValidStatus();
                    if (StringUtils.isNotBlank(status)) {
                        if ("1".equals(status)) {
                            report.setValidStatus("有效");
                        } else if ("0".equals(status)) {
                            report.setValidStatus("无效");
                        }
                    }
                    //工程险、企财险翻译
                    report.setRiskName(dataSourcesService.getRiskCName(report.getRiskName()));

                    if (StringUtils.isBlank(report.getRiskName())) {
                        report.setRiskName("未说明");
                    }
                    if (StringUtils.isBlank(report.getRiskYear())) {
                        report.setRiskYear("未说明");
                    }
                    if (StringUtils.isNotBlank(report.getAscName())) {
                        String[] codeCname = (report.getAscName()).split(",");
                        for (int j = 0; j < codeCname.length; j++) {
                            report.setAscName(dataSourcesService.queryRiskDcode("riskFileAscName", "", codeCname[j], "1").getCodeCname());

                        }
                    }
                    if (StringUtils.isNotBlank(report.getSender())) {
                        String[] codeCname = (report.getSender().split(","));
                        for (int j = 0; j < codeCname.length; j++) {
                            report.setSender(dataSourcesService.queryRiskDcode("riskFileSender", "", codeCname[j], "1").getCodeCname());
                        }
                    }
                    if (StringUtils.isNotBlank(report.getProfession())) {
                        String[] codeCname = (report.getProfession()).split(",");
                        for (int j = 0; j < codeCname.length; j++) {
                            report.setProfession(dataSourcesService.queryRiskDcode("profession", "", codeCname[j], "1").getCodeCname());
                        }
                    }
                }

                //responsevo添加信息
                ResultPageUtils.covertSuperReportResponseVo(responseVo, page);
                responseVo.setDataList(content);
                responseVo.setConditionList(conditions);
            }
        } catch (Exception e) {
            //log.error("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            String m = RiskActionExceptionResolver.getStackTrace(e);
            responseVo.setQCexception(m);
        }
        return responseVo;
    }

     /**
     * @功能：优秀报告维护 保存
     * @作者：liqiankun
     * @日期：20180404
     * @修改记录：崔凤志  2020/4/14
     */
    public void saveRiskInfoFile(RiskInfoFileVo riskInfoFileVo)throws Exception{
        RiskInfoFile  riskInfoFileNew=new RiskInfoFile();
        try {
            BeanUtils.copyProperties(riskInfoFileVo, riskInfoFileNew);
            if(null!=riskInfoFileNew){
                riskInfoFileNew.setValidStatus("0");
                riskInfoFileDao.save(riskInfoFileNew);
            }
        } catch (Exception e) {
            LOGGER.info("保存异常：" + e.getMessage() ,e);
            e.printStackTrace();
            throw new RuntimeException("保存异常:"+e);
        }
    }

    public Integer getNewSerialNo(String flag){
        Integer maxSerialNo=null;
        Integer serialNo=1;
        try {
            if(StringUtils.isNotBlank(flag) && "claimQueryDetail".equals(flag)){
                maxSerialNo = riskInfoClaimDao.getMaxNo();
            }else if(StringUtils.isNotBlank(flag)&&"fileQueryDetail".equals(flag)){
                maxSerialNo = superRiskReportDao.getMaxNo();
            }
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage() ,e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:"+e);
        }
        if(null != maxSerialNo && maxSerialNo > 0){
            return maxSerialNo+1;
        }else{
            return serialNo;
        }
    }
}
