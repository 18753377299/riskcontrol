package com.picc.riskctrl.riskins.service;

import com.alibaba.fastjson.JSON;
import com.picc.riskctrl.common.RiskControlConst;
import com.picc.riskctrl.common.dao.*;
import com.picc.riskctrl.common.jpa.condition.Restrictions;
import com.picc.riskctrl.common.jpa.vo.Criteria;
import com.picc.riskctrl.common.po.*;
import com.picc.riskctrl.common.schema.PrpDcompanyFk;
import com.picc.riskctrl.common.schema.RiskUnderwriteback;
import com.picc.riskctrl.common.schema.RiskUnderwritebackId;
import com.picc.riskctrl.common.schema.UtiWeight;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.service.MQMessageService;
import com.picc.riskctrl.common.service.RiskTimeService;
import com.picc.riskctrl.common.utils.CommonConst;
import com.picc.riskctrl.common.utils.FTPUtil;
import com.picc.riskctrl.common.utils.MapTransferUtils;
import com.picc.riskctrl.common.utils.WordUtils;
import com.picc.riskctrl.common.vo.Gps;
import com.picc.riskctrl.riskcheck.vo.GridValue;
import com.picc.riskctrl.riskins.dao.*;
import com.picc.riskctrl.riskins.model.FireDangerProfessionTranslateEnum;
import com.picc.riskctrl.riskins.po.*;
import com.picc.riskctrl.riskins.service.spring.DisastersScore;
import com.picc.riskctrl.riskins.vo.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import pdfc.framework.web.ApiResponse;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.sql.DataSource;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.*;
import java.util.regex.Pattern;

//import org.apache.http.client.utils.DateUtils;

@Service
@Transactional
public class RiskInsService {
    private static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    @Autowired
    private PrpDcompanyFkRepository prpDcompanyFkRepository;
    @Autowired
    private DataSourcesService dataSourcesService;
    @Autowired
    private RiskReportMainRepository riskReportMainRepository;
    @Autowired
    private RiskReportPictureRepository riskReportPictureRepository;
    @Autowired
    private RiskDaddressRepository riskDaddressRepository;
    @Autowired
    private RiskDnaturalRepository riskDnaturalRepository;
    @Autowired
    RiskUnderwritebackRepository riskUnderwritebackRepository;
    @Autowired
    private ImageTransferLogRepository imageTransferLogRepository;
    @Autowired
    private RiskReportSaleMainRepository riskReportSaleMainRepository;
    @Autowired
    private RiskReportSaleCorrectionRepository riskReportSaleCorrectionRepository;
    @Autowired
    RiskTimeService riskTimeService;
    @Autowired
    private RiskInsImageService riskInsImageService;
    @Autowired
    private UtiFactorDao utiFactorDao;
    @Autowired
    private UtiScoreDao utiScoreDao;
    @Autowired
    private UtiWeightDao utiWeightDao;
    @Autowired
    private UtiKeyRepository utiKeyRepository;
    @Autowired
    private UtiFormulaDao utiFormulaDao;
    @Autowired
    private UtiHighlightRiskDao utiHighlightRiskDao;

    @Autowired
    private DataSource dataSource;


    // 避免在方法内多次新建数组而设立的两个基础数组
    @Autowired
    private MQMessageService mQMessageService;
    @Autowired
    private RiskInsSaleService riskInsSaleService;
    @Autowired
    private RiskDcodeRepository riskDcodeRepository;
    // 靿兝在方法内多次新建数组而设立的两个基础数组
    private static final Object[] EMPTY_ARRAY = new Object[0];
    //睫睾模板相关常亮
    private static final String FIRE_NOTICE = "notice";
    private static final String FIRE_DOC = "doc";
    private static final String FIRE_PDF = "pdf";
    private static final String FIRE_PROFESSION_MODEL = "006";
    private static final String FIRE_SIMPLE_MODEL = "007";

    /**
     * @param riskFileNo
     * @return RiskReportMain
     * @throws Exception
     * @author anqingsen
     * @功能: 根杮报告编坷查询 @日期：2020-01-13
     */
    public RiskReportMain queryRiskReportMain(String riskFileNo) throws Exception {
        RiskReportMain riskReportMain = null;
        RiskReportMain ap = new RiskReportMain();
        Optional<RiskReportMain> result = null;
        try {
            if (StringUtils.isNotBlank(riskFileNo)) {
                result = riskReportMainRepository.findById(riskFileNo);
                riskReportMain = result.isPresent() ? result.get() : null;

                String riskModel = riskFileNo.substring(5, 8);
                if (riskReportMain != null) {
                	BeanUtils.copyProperties(riskReportMain, ap);
                	
                    if ("004".equals(riskModel)) {
                        List<RiskReportMachine> riskReportMachineList = new ArrayList<RiskReportMachine>();
                        for (RiskReportMachine riskReportMachine : riskReportMain.getRiskReportMachineList()) {
                            RiskReportMachine temp = new RiskReportMachine();
                            BeanUtils.copyProperties(riskReportMachine, temp);
                            riskReportMachineList.add(temp);
                            ap.setRiskReportMachineList(riskReportMachineList);
                        }
                    } else if ("005".equals(riskModel)) {
                        List<RiskReportCloBusiness> riskReportCloBusinessList = new ArrayList<RiskReportCloBusiness>();
                        for (RiskReportCloBusiness riskReportCloBusiness : riskReportMain
                                .getRiskReportCloBusinessList()) {
                            RiskReportCloBusiness temp = new RiskReportCloBusiness();
                            BeanUtils.copyProperties(riskReportCloBusiness, temp);
                            riskReportCloBusinessList.add(temp);
                            ap.setRiskReportCloBusinessList(riskReportCloBusinessList);
                        }
                    } else {
                        List<RiskReportAddress> riskReportAddressList = new ArrayList<RiskReportAddress>();
                        for (RiskReportAddress riskReportAddress : riskReportMain.getRiskReportAddressList()) {
                            RiskReportAddress addressTemp = new RiskReportAddress();
                            RiskReportAddressId id = new RiskReportAddressId();
                            BeanUtils.copyProperties(riskReportAddress, addressTemp);
                            BeanUtils.copyProperties(riskReportAddress.getId(), id);
                            addressTemp.setId(id);
                            riskReportAddressList.add(addressTemp);
                            ap.setRiskReportAddressList(riskReportAddressList);
                        }
                        List<RiskReportAirStorage> riskReportAirStorageList = new ArrayList<RiskReportAirStorage>();
                        for (RiskReportAirStorage riskReportAirStorage : riskReportMain.getRiskReportAirStorageList()) {
                            RiskReportAirStorage temp = new RiskReportAirStorage();
                            BeanUtils.copyProperties(riskReportAirStorage, temp);
                            riskReportAirStorageList.add(temp);
                            ap.setRiskReportAirStorageList(riskReportAirStorageList);
                        }
                        List<RiskReportAssess> riskReportAssessList = new ArrayList<RiskReportAssess>();
                        for (RiskReportAssess riskReportAsses : riskReportMain.getRiskReportAssessList()) {
                            RiskReportAssess temp = new RiskReportAssess();
                            BeanUtils.copyProperties(riskReportAsses, temp);
                            riskReportAssessList.add(temp);
                            ap.setRiskReportAssessList(riskReportAssessList);
                        }
                        List<RiskReportClaim> riskReportClaimList = new ArrayList<RiskReportClaim>();
                        for (RiskReportClaim riskReportClaim : riskReportMain.getRiskReportClaimList()) {
                            RiskReportClaim temp = new RiskReportClaim();
                            RiskReportClaimId id = new RiskReportClaimId();
                            BeanUtils.copyProperties(riskReportClaim, temp);
                            BeanUtils.copyProperties(riskReportClaim.getId(), id);
                            temp.setId(id);
                            riskReportClaimList.add(temp);
                            ap.setRiskReportClaimList(riskReportClaimList);
                        }
                        List<RiskReportConstructInfo> riskReportConstructInfoList =
                                new ArrayList<RiskReportConstructInfo>();
                        for (RiskReportConstructInfo riskReportConstructInfo : riskReportMain
                                .getRiskReportConstructInfoList()) {
                            RiskReportConstructInfo temp = new RiskReportConstructInfo();
                            RiskReportConstructInfoId id = new RiskReportConstructInfoId();
                            BeanUtils.copyProperties(riskReportConstructInfo, temp);
                            BeanUtils.copyProperties(riskReportConstructInfo.getId(), id);
                            temp.setId(id);
                            riskReportConstructInfoList.add(temp);
                            ap.setRiskReportConstructInfoList(riskReportConstructInfoList);
                        }
                        List<RiskReportConstruct> riskReportConstructList = new ArrayList<RiskReportConstruct>();
                        for (RiskReportConstruct riskReportConstruct : riskReportMain.getRiskReportConstructList()) {
                            RiskReportConstruct temp = new RiskReportConstruct();
                            BeanUtils.copyProperties(riskReportConstruct, temp);
                            riskReportConstructList.add(temp);
                            ap.setRiskReportConstructList(riskReportConstructList);
                        }
                        List<RiskReportEnvironment> RiskReportEnvironmentList = new ArrayList<RiskReportEnvironment>();
                        for (RiskReportEnvironment riskReportEnvironment : riskReportMain
                                .getRiskReportEnvironmentList()) {
                            RiskReportEnvironment temp = new RiskReportEnvironment();
                            BeanUtils.copyProperties(riskReportEnvironment, temp);
                            RiskReportEnvironmentList.add(temp);
                            ap.setRiskReportEnvironmentList(RiskReportEnvironmentList);
                        }
                        List<RiskReportInterrupt> riskReportInterruptList = new ArrayList<RiskReportInterrupt>();
                        for (RiskReportInterrupt riskReportInterrupt : riskReportMain.getRiskReportInterruptList()) {
                            RiskReportInterrupt temp = new RiskReportInterrupt();
                            BeanUtils.copyProperties(riskReportInterrupt, temp);
                            riskReportInterruptList.add(temp);
                            ap.setRiskReportInterruptList(riskReportInterruptList);
                        }
                        List<RiskReportOccupation> riskReportOccupationList = new ArrayList<RiskReportOccupation>();
                        for (RiskReportOccupation riskReportOccupation : riskReportMain.getRiskReportOccupationList()) {
                            RiskReportOccupation temp = new RiskReportOccupation();
                            BeanUtils.copyProperties(riskReportOccupation, temp);
                            riskReportOccupationList.add(temp);
                            ap.setRiskReportOccupationList(riskReportOccupationList);
                        }
                        List<RiskReportProtection> riskReportProtectionList = new ArrayList<RiskReportProtection>();
                        for (RiskReportProtection riskReportProtection : riskReportMain.getRiskReportProtectionList()) {
                            RiskReportProtection temp = new RiskReportProtection();
                            BeanUtils.copyProperties(riskReportProtection, temp);
                            riskReportProtectionList.add(temp);
                            ap.setRiskReportProtectionList(riskReportProtectionList);
                        }
                        List<RiskReportTheft> riskReportTheftList = new ArrayList<RiskReportTheft>();
                        for (RiskReportTheft riskReportTheft : riskReportMain.getRiskReportTheftList()) {
                            RiskReportTheft temp = new RiskReportTheft();
                            BeanUtils.copyProperties(riskReportTheft, temp);
                            riskReportTheftList.add(temp);
                            ap.setRiskReportTheftList(riskReportTheftList);
                        }
                        /*火灾风险排查*/
                        List<RiskReportFireDanger> riskReportFireDangerList = new ArrayList<RiskReportFireDanger>();
                        for (RiskReportFireDanger riskReportFireDanger : riskReportMain.getRiskReportFireDangerList()) {
                            RiskReportFireDanger temp = new RiskReportFireDanger();
                            BeanUtils.copyProperties(riskReportFireDanger, temp);
                            riskReportFireDangerList.add(temp);
                            ap.setRiskReportFireDangerList(riskReportFireDangerList);
                        }

                    }

                } else {
                    ap = riskReportMain;
                }
            } else {
                throw new RuntimeException("风险编号不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询风控报告信息异常" + e);
        }
        return ap;
    }

    /**
     * @param riskReportMain
     * @return RiskInsRiskReportVo
     * @throws Exception
     * @author anqingsen
     * @功能：组织返回页面Vo @日期：2020-01-14
     */
    @SuppressWarnings("rawtypes")
    public RiskInsRiskReportVo getObjectVo(RiskReportMain riskReportMain) throws Exception {
        RiskInsRiskReportVo riskInsRiskReportVo = new RiskInsRiskReportVo();
        riskInsRiskReportVo.setRiskReportMainVo(riskReportMain);
        // 风控基本信息
        Class objectClass = riskReportMain.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(riskReportMain) != null) {
                    if (field.getType().equals(List.class)) {
                        Class riskInsVoClass = riskInsRiskReportVo.getClass();
                        Field[] riskInsVoFields = riskInsVoClass.getDeclaredFields();
                        for (Field riskInsVoField : riskInsVoFields) {
                            riskInsVoField.setAccessible(true);
                            if (field.getName().equals(riskInsVoField.getName())) {
                                riskInsVoField.set(riskInsRiskReportVo, field.get(riskReportMain));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("组织返回页面异常:" + e);
        }
        return riskInsRiskReportVo;
    }

    /**
     * @param riskDcodeList
     * @param riskFileNo    报告编号
     * @param contextPath
     * @return RiskDcodeList
     * @throws Exception
     * @author anqingsen
     * @功能：查询RiskReportPicture表数据 @日期：2020-01-14
     */
    public List<RiskDcode> queryPicture(List<RiskDcode> riskDcodeList, String riskFileNo, String contextPath)
            throws Exception {
        try {
            List<RiskReportPicture> riskReportPictures = riskReportPictureRepository.findByRiskFileNo(riskFileNo);
            String ip = "";
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();

            ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
            String savePort = bundle.getString("savePort");
            String savePath = "http" + "://" + ip + ":" + savePort;
            for (RiskDcode riskDcodeTem : riskDcodeList) {
                ImageConfigVo imageConfigVo = new ImageConfigVo();
                List<String> urls = new ArrayList<String>();
                List<ImagePreviewConfigVo> configs = new ArrayList<>();
                String treeId = riskDcodeTem.getCodeEname();
                for (RiskReportPicture riskReportPicture : riskReportPictures) {
                    if (riskReportPicture.getId().getChildPath().equals(treeId)) {

                        ImagePreviewConfigVo imagePreviewConfigVo = new ImagePreviewConfigVo();
                        ImagePreviewExtraVo ImagePreviewExtraVo = new ImagePreviewExtraVo();
                        ImagePreviewExtraVo.setChildPath(riskReportPicture.getId().getChildPath());
                        ImagePreviewExtraVo.setImageName(riskReportPicture.getId().getImageName());
                        if (StringUtils.isNotBlank(riskReportPicture.getImageUrl())) {
                            if (riskReportPicture.getImageUrl().indexOf("http") > -1) {
                                urls.add(riskReportPicture.getImageUrl());
                            } else {
                                urls.add(savePath + riskReportPicture.getImageUrl());
                            }
                        }
                        ImagePreviewExtraVo.setImagePath(riskReportPicture.getImageUrl());
                        imagePreviewConfigVo.setRemark(riskReportPicture.getRemark());
                        imagePreviewConfigVo.setCaption(riskReportPicture.getId().getImageName());
                        imagePreviewConfigVo.setLocalPath(riskReportPicture.getImageUrl());
                        imagePreviewConfigVo.setUrl(contextPath + "/riskins/image/imageRemove");
                        imagePreviewConfigVo.setDownloadUrl(contextPath + "/riskins/image/imageDownLoad;"
                                + riskReportPicture.getImageUrl() + ";" + riskReportPicture.getId().getImageName());
                        imagePreviewConfigVo.setExtra(ImagePreviewExtraVo);
                        configs.add(imagePreviewConfigVo);
                    }
                }
                imageConfigVo.setUrls(urls);
                imageConfigVo.setConfigs(configs);
                riskDcodeTem.setConfigList(imageConfigVo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询RiskReportPicture表数据异常：" + e);
        }
        return riskDcodeList;
    }

    /**
     * @param riskInsRequestVo
     * @return RiskInsResponseVo @日期：2017-10-23
     * @功能：风控报告查询
     * @author 李博儒
     */
    @SuppressWarnings(value = {"rawtypes", "unchecked"})
    public RiskInsResponseVo queryRiskIns(RiskInsRequestVo riskInsRequestVo, UserInfo userInfo) {
        RiskInsResponseVo riskInsResponseVo;
        try {
            String userCodeInfo = "";
            String comCodeInfo = "";
            if (userInfo != null) {
                userCodeInfo = userInfo.getUserCode();
                comCodeInfo = userInfo.getComCode();
            }
            RiskReportMainQueryVo riskReportMainVo = riskInsRequestVo.getRiskReportMainVo();
            Page<RiskReportMain> page = null;
            if (riskReportMainVo != null) {
                Specification<RiskReportMain> spec = new Specification<RiskReportMain>() {
                    @Override
                    public Predicate toPredicate(Root<RiskReportMain> root, CriteriaQuery<?> criteriaQuery,
                                                 CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicateList = new ArrayList<>();
                        // 风险档案编号
                        String riskFileNo = riskReportMainVo.getRiskFileNo();
                        if (StringUtils.isNotBlank(riskFileNo)) {
                            predicateList.add(criteriaBuilder.equal(root.get("riskFileNo"), riskFileNo.trim()));
                        }
                        // 投保险类
                        String classCode = riskReportMainVo.getClassCode();
                        if (StringUtils.isNotBlank(classCode)) {
                            predicateList.add(criteriaBuilder.equal(root.get("classCode"), classCode.trim()));
                        }

                        // 投保险种
                        String riskCode = riskReportMainVo.getRiskCode();
                        if (StringUtils.isNotBlank(riskCode)) {
                            predicateList.add(criteriaBuilder.equal(root.get("riskCode"), riskCode.trim()));
                        }

                        // 风控报告模板
                        String riskModel = riskReportMainVo.getRiskModel();
                        if (StringUtils.isNotBlank(riskModel)) {
                            predicateList.add(criteriaBuilder.equal(root.get("riskModel"), riskModel.trim()));
                        }

                        // 被保险人
                        String insuredName = riskReportMainVo.getInsuredName();
                        if (StringUtils.isNotBlank(insuredName)) {
                            Predicate in = criteriaBuilder.like(root.get("insuredName"), "%" + insuredName + "%");
                            Predicate ic = criteriaBuilder.like(root.get("insuredCode"), "%" + insuredName + "%");
                            predicateList.add(criteriaBuilder.or(in, ic));
                        }
                        // 被保险人代码
                        String insuredCode = riskReportMainVo.getInsuredCode();
                        if (StringUtils.isNotBlank(insuredCode)) {
                            predicateList.add(criteriaBuilder.equal(root.get("insuredCode"), insuredCode.trim()));
                        }

                        // 查勘人
                        String explorer = riskReportMainVo.getExplorer();
                        String explorerCode = riskReportMainVo.getExplorerCode();
                        if (StringUtils.isNotBlank(explorer) && StringUtils.isNotBlank(explorerCode)) {
                            CriteriaBuilder.In<Object> explorerIn = criteriaBuilder.in(root.get("explorer"));
                            explorerIn.value(explorer.trim());
                            explorerIn.value(explorerCode.trim());
                            predicateList.add(explorerIn);
                        }

                        if (!StringUtils.isNotBlank(explorerCode) && StringUtils.isNotBlank(explorer)) {
                            predicateList.add(criteriaBuilder.equal(root.get("explorer"), explorer.trim()));
                        }
                        if (StringUtils.isNotBlank(explorerCode) && !StringUtils.isNotBlank(explorer)) {
                            predicateList.add(criteriaBuilder.equal(root.get("explorer"), explorerCode.trim()));
                        }

                        // 归属机构
                        String comCode = riskReportMainVo.getComCode();
                        if (StringUtils.isNotBlank(comCode)) {
                            PrpDcompanyFk prpDcompanyFk = getPrpDcompanyFk(comCode);
                            if (prpDcompanyFk != null) {
                                String upperPath = prpDcompanyFk.getUpperPath();
                                if (StringUtils.isNotBlank(upperPath)) {
                                    upperPath = upperPath.trim();
                                    if (upperPath.length() == 17) {
                                        if ("2102,3302,3502,3702,4403".contains(upperPath.substring(9, 13))) {
                                            predicateList.add(criteriaBuilder.equal(
                                                    criteriaBuilder.substring(root.get("comCode"), 1, 4),
                                                    upperPath.substring(9, 13)));
                                        } else {
                                            predicateList.add(criteriaBuilder.equal(
                                                    criteriaBuilder.substring(root.get("comCode"), 1, 2),
                                                    upperPath.substring(9, 11)));
                                            switch (upperPath.substring(9, 11)) {
                                                case "21":
                                                    predicateList.add(criteriaBuilder.notEqual(
                                                            criteriaBuilder.substring(root.get("comCode"), 1, 4), "2102"));
                                                    break;
                                                case "33":
                                                    predicateList.add(criteriaBuilder.notEqual(
                                                            criteriaBuilder.substring(root.get("comCode"), 1, 4), "3302"));
                                                    break;
                                                case "35":
                                                    predicateList.add(criteriaBuilder.notEqual(
                                                            criteriaBuilder.substring(root.get("comCode"), 1, 4), "3502"));
                                                    break;
                                                case "37":
                                                    predicateList.add(criteriaBuilder.notEqual(
                                                            criteriaBuilder.substring(root.get("comCode"), 1, 4), "3702"));
                                                    break;
                                                case "44":
                                                    predicateList.add(criteriaBuilder.notEqual(
                                                            criteriaBuilder.substring(root.get("comCode"), 1, 4), "4403"));
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    } else if (upperPath.length() > 17) {
                                        List<PrpDcompanyFk> prpDcompanyFkList = getPrpDcompanyFkList(upperPath);
                                        CriteriaBuilder.In<Object> comCodeIn = criteriaBuilder.in(root.get("comCode"));
                                        if (null != prpDcompanyFkList && prpDcompanyFkList.size() > 0) {
                                            for (PrpDcompanyFk dcompanyFk : prpDcompanyFkList) {
                                                comCodeIn.value(dcompanyFk.getComCode());
                                            }
                                        }
                                        predicateList.add(comCodeIn);
                                    }
                                }
                            } else {
                                predicateList.add(criteriaBuilder.equal(root.get("comCode"), comCode));
                            }
                        }

                        // 查勘日期起期
                        Date exploreDateBegin = riskReportMainVo.getExploreDateBegin();
                        // 查勘日期止期
                        Date exploreDateEnd = riskReportMainVo.getExploreDateEnd();

                        if (exploreDateBegin != null && exploreDateEnd != null) {
                            predicateList.add(
                                    criteriaBuilder.between(root.get("exploreDate"), exploreDateBegin, exploreDateEnd));
                        } else {
                            if (exploreDateBegin != null) {
                                predicateList
                                        .add(criteriaBuilder.greaterThanOrEqualTo(root.get("exploreDate"), exploreDateBegin));
                            } else if (exploreDateEnd != null) {
                                predicateList
                                        .add(criteriaBuilder.lessThanOrEqualTo(root.get("exploreDate"), exploreDateEnd));
                            }
                        }

                        // 外系统起期
                        Date outerOperateDateBegin = riskReportMainVo.getOuterOperateDateBegin();
                        // 外系统止期
                        Date outerOperateDateEnd = riskReportMainVo.getOuterOperateDateEnd();
                        if (outerOperateDateBegin != null && outerOperateDateEnd != null) {
                            predicateList.add(criteriaBuilder.between(root.get("madeDate"), outerOperateDateBegin,
                                    outerOperateDateEnd));
                        } else {
                            if (outerOperateDateBegin != null) {
                                predicateList
                                        .add(criteriaBuilder.greaterThanOrEqualTo(root.get("madeDate"), outerOperateDateBegin));
                            } else if (outerOperateDateEnd != null) {
                                predicateList
                                        .add(criteriaBuilder.lessThanOrEqualTo(root.get("madeDate"), outerOperateDateEnd));
                            }
                        }

                        // 查勘类别
                        String exploreType = riskReportMainVo.getExploreType();
                        if (StringUtils.isNotBlank(exploreType)) {
                            predicateList.add(criteriaBuilder.equal(root.get("exploreType"), exploreType.trim()));
                        }
                        // 查勘地址
                        String addressDetail = riskReportMainVo.getAddressDetail();
                        if (StringUtils.isNotBlank(addressDetail)) {
                            predicateList
                                    .add(criteriaBuilder.like(root.get("addressDetail"), addressDetail.trim() + "%"));
                        }
                        // 普通查询中的核保状态：:暂存，1:通过；审核查询中的标志位:4:待市级审核，9:待省级审核
                        String[] underwriteFlag = riskReportMainVo.getUnderwriteFlag();
                        // 审核查询若无标志位，则查询出待审核的风控报告
                        CriteriaBuilder.In<Object> underwriteFlag1 = criteriaBuilder.in(root.get("underwriteFlag"));
                        if ("underwrite".equals(riskReportMainVo.getBusinessType())
                                && (underwriteFlag == null || underwriteFlag.length == 0)) {
                            // 201926127-003 关于完善商业非车险风控服务平台部分使用功能的请示：报告添加审核中状态
                            underwriteFlag1.value(Arrays.asList("4", "9", "A"));
                            predicateList.add(underwriteFlag1);
                        } else if (null != underwriteFlag && underwriteFlag.length > 0) {
                            underwriteFlag1.value(Arrays.asList(underwriteFlag));
                            predicateList.add(underwriteFlag1);
                        }

                        // 权限校验开始-----
                        // 外系统不走addpower方法
                        // if (!"1".equals(riskReportMainVo.getOuterOperateFlag())) {
                        // SaaAPIService saaAPIService = new SaaAPIServiceImpl();
                        // try {
                        // String powerSQL = saaAPIService.addPower("riskcontrol", userCodeInfo, "riskins_query",
                        // "this_.comCode", "", "");
                        // queryRule.addSql(powerSQL);
                        // } catch (Exception e) {
                        // LOGGER.info("addPower执行异常：" + e.getMessage(), e);
                        // e.printStackTrace();
                        // throw new RuntimeException("addPower执行异常:" + e);
                        // }
                        // }
                        // 权限校验结束-----
                        Predicate[] arr = new Predicate[predicateList.size()];
                        return criteriaBuilder.and(predicateList.toArray(arr));
                    }
                };
                // 时间倒叙排列
                Sort sort = Sort.by(Sort.Direction.DESC, "insertTimeForHis");
                // 移动端根据usercode查询结果降序排列
                if (riskReportMainVo.getMobFlag()) {
                    sort = Sort.by(Sort.Direction.DESC, "riskFileNo", "insertTimeForHis");
                }
                page = riskReportMainRepository.findAll(spec,
                        PageRequest.of(riskInsRequestVo.getPageNo() - 1, riskInsRequestVo.getPageSize(), sort));
            }
            riskInsResponseVo = new RiskInsResponseVo();
            if (page != null) {
                List<RiskReportMain> riskReportMainList = page.getContent();
                List<RiskReportMainResponseVo> riskReportMainListNew = new ArrayList<RiskReportMainResponseVo>();
                for (RiskReportMain r : riskReportMainList) {
                    RiskReportMainResponseVo rNew = new RiskReportMainResponseVo();
                    BeanUtils.copyProperties(r, rNew);
                    // 各灾因风险值赋值
                    if (r.getRiskReportAssessList() != null && r.getRiskReportAssessList().size() > 0) {
                        BeanUtils.copyProperties(r.getRiskReportAssessList().get(0), rNew);
                    }
                    riskReportMainListNew.add(rNew);
                }
                // 设置数杮
                riskInsResponseVo.setDataList(riskReportMainListNew);
                // 设置数据总条数
                riskInsResponseVo.setTotalCount(page.getTotalElements());
                // 设置总页数
                riskInsResponseVo.setTotalPage(page.getTotalPages());
            }
            List dataList = riskInsResponseVo.getDataList();
            Map<String, RiskDcode> exploreTypeMap = null;
            Map<String, RiskDcode> underwriteFlagMap = null;
            Map<String, Code> riskCodeMap = null;

            if (dataList.size() > 0) {
                exploreTypeMap = dataSourcesService.queryRiskDcode("exploreType");
                underwriteFlagMap = dataSourcesService.queryRiskDcode("underwriteFlag");
                try {
                    riskCodeMap = dataSourcesService.queryAllRiskCode(comCodeInfo);
                } catch (Exception e) {
                    LOGGER.info("查询信杯异常：" + e.getMessage(), e);
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

            }

            // 代砝转中文
            for (Object o : dataList) {
                RiskReportMainResponseVo mainVo = (RiskReportMainResponseVo) o;
                // 查勘类别翻译
                if (StringUtils.isNotBlank(mainVo.getExploreType())) {
                    RiskDcode riskDcodeExploreType = exploreTypeMap.get(mainVo.getExploreType());
                    if (riskDcodeExploreType != null) {
                        mainVo.setExploreType(riskDcodeExploreType.getCodeCname());
                    }
                }
                // 核保状思翻译
                if (StringUtils.isNotBlank(mainVo.getUnderwriteFlag())) {
                    mainVo.setUnderwriteFlagCode(mainVo.getUnderwriteFlag());
                    RiskDcode riskDcodeUnderwriteFlag = underwriteFlagMap.get(mainVo.getUnderwriteFlag());
                    if (riskDcodeUnderwriteFlag != null) {
                        mainVo.setUnderwriteFlag(riskDcodeUnderwriteFlag.getCodeCname());
                    }
                }
                // 投保险秝翻译
                if (StringUtils.isNotBlank(mainVo.getRiskCode())) {
                    Code codeRiskCode = riskCodeMap.get(mainVo.getRiskCode());
                    if (codeRiskCode != null) {
                        mainVo.setRiskCode(codeRiskCode.getName());
                    }
                }

                // 查勘人中文翻译
                if (StringUtils.isNotBlank(mainVo.getExplorer())) {
                    // riskReportMain.setExplorerCName(riskInsService.queryCodeCName("UserCode",riskReportMain.getExplorer(),
                    // userCodeInfo, comCodeInfo, riskCodeInfo));
                    mainVo.setExplorerCName(mainVo.getExplorer());
                }
                // 归属机构中文翻译
                if (StringUtils.isNotBlank(mainVo.getComCode())) {
                    // riskReportMain.setComCodeCName(riskInsService.queryComCodeCName(riskReportMain.getComCode()));
                    mainVo.setComCodeCName(mainVo.getComCode());
                }
                // 承保建议中文翻译
                if (StringUtils.isNotBlank(mainVo.getRiskSuggest())) {
                    switch (mainVo.getRiskSuggest().trim()) {
                        case "A":
                            mainVo.setRiskSuggest("谨慎承保");
                            break;
                        case "B":
                            mainVo.setRiskSuggest("杝高条件承保");
                            break;
                        case "C":
                            mainVo.setRiskSuggest("竞争性承保");
                            break;
                    }
                }
                if (StringUtils.isNotBlank(mainVo.getMobileFlag())) {
                    switch (mainVo.getMobileFlag().trim()) {
                        case "0":
                            mainVo.setMobileFlag("PC");
                            break;
                        case "1":
                            mainVo.setMobileFlag("Android");
                            break;
                        case "2":
                            mainVo.setMobileFlag("iOS");
                            break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }

        return riskInsResponseVo;
    }

    public PrpDcompanyFk getPrpDcompanyFk(String comCode) {
        PrpDcompanyFk prpDcompanyFk = new PrpDcompanyFk();
        Optional<PrpDcompanyFk> po = prpDcompanyFkRepository.findById(comCode);
        if (po.isPresent()) {
            prpDcompanyFk = po.get();
        }
        return prpDcompanyFk;
    }

    public List<PrpDcompanyFk> getPrpDcompanyFkList(String upperPath) {
        try {
            Specification<PrpDcompanyFk> spec = new Specification<PrpDcompanyFk>() {
                @Override
                public Predicate toPredicate(Root<PrpDcompanyFk> root, CriteriaQuery<?> criteriaQuery,
                                             CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(criteriaBuilder.substring(root.get("upperpath"), 1, 26),
                            upperPath.substring(0, 26));
                }
            };
            return this.prpDcompanyFkRepository.findAll(spec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RiskDnatural> checkRiskDnaturalAddress(String addressCode, String addressProCode, String exploreDate,
                                                       String string) {
        List<RiskDnatural> riskDnaturalList = null;
        try {
            Specification<RiskDnatural> spec = new Specification<RiskDnatural>() {
                @Override
                public Predicate toPredicate(Root<RiskDnatural> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<>();
                    if (StringUtils.isNotBlank(addressCode)) {
                        if ("31".equals(addressCode.substring(0, 2)) || "50".equals(addressCode.substring(0, 2))
                                || "11".equals(addressCode.substring(0, 2)) || "12".equals(addressCode.substring(0, 2))) {
                            predicateList
                                    .add(criteriaBuilder.like(root.get("addressCode"), addressCode.substring(0, 2) + "%"));
                        } else {
                            predicateList.add(criteriaBuilder.like(root.get("addressCode"), addressCode.trim()));
                        }
                    }
                    if (StringUtils.isNotBlank(exploreDate)) {
                        predicateList.add(criteriaBuilder.equal(root.get("naturalYear"), exploreDate.trim()));
                    }
                    predicateList.add(criteriaBuilder.equal(root.get("validStatus"), "1"));
                    Predicate[] arr = new Predicate[predicateList.size()];
                    return criteriaBuilder.and(predicateList.toArray(arr));
                }
            };

            riskDnaturalList = this.riskDnaturalRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "naturalYear"));
            if (riskDnaturalList != null && riskDnaturalList.size() > 0) {
                return riskDnaturalList;
            } else {
                Specification<RiskDnatural> specone = new Specification<RiskDnatural>() {
                    @Override
                    public Predicate toPredicate(Root<RiskDnatural> root, CriteriaQuery<?> query,
                                                 CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicateList = new ArrayList<>();
                        if (StringUtils.isNotBlank(addressCode)) {
                            predicateList.add(criteriaBuilder.like(root.get("addressCode"), addressCode.trim()));
                        }
                        if (StringUtils.isNotBlank(exploreDate)) {
                            predicateList.add(criteriaBuilder.equal(root.get("naturalYear"), exploreDate.trim()));
                        }
                        predicateList.add(criteriaBuilder.equal(root.get("validStatus"), "1"));
                        Predicate[] arr = new Predicate[predicateList.size()];
                        return criteriaBuilder.and(predicateList.toArray(arr));
                    }
                };
                riskDnaturalList =
                        this.riskDnaturalRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "naturalYear"));
            }
            // 兼职版环境信息默认带出赋值更改
            if (StringUtils.isNotBlank(string) && riskDnaturalList != null && riskDnaturalList.size() > 0
                    && "002".equals(string)) {
                RiskDnatural riskDnatural = riskDnaturalList.get(0);
                // riskDnatural.setCollapseHis(null);
                riskDnaturalList.set(0, riskDnatural);
            }
        } catch (Exception e) {
            LOGGER.info("与数据库交互异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("与数据库交互异常:" + e);
        }
        return riskDnaturalList;
    }

    /**
     * @param address
     * @return
     * @author anqingsen
     * @功能：截取省市县
     * @日期: 2020-01-16
     */
    public String subStr(String address, String... str) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        for (String s : str) {
            if (address.indexOf(s) == -1) {
                continue;
            }
            int index = address.indexOf(s);
            String sub = address.substring(0, index + s.length());
            // address = address.substring(index + s.length());
            return sub;
        }
        return "";
    }

    /**
     * @param countyName
     * @param upperCode  上级代码
     * @param gradeFlag  必传 1-省级；2-市级；3-县区级
     * @return List<RiskDaddress>
     * @author anqingsen @功能：风控地址查询（根据中文） @日期：2020-01-16
     */
    public List<RiskDaddress> queryAddressByCName(String countyName, String upperCode, String gradeFlag) {
        List<RiskDaddress> riskDaddressList = new ArrayList<RiskDaddress>();
        Criteria<RiskDaddress> criteria = new Criteria<>();
        try {
            if (StringUtils.isNotBlank(upperCode)) {
                criteria.add(Restrictions.eq("countyName", countyName));
                criteria.add(Restrictions.eq("upperCode", upperCode));
                criteria.add(Restrictions.eq("gradeFlag", gradeFlag));
            } else {
                criteria.add(Restrictions.eq("gradeFlag", gradeFlag));
                criteria.add(Restrictions.eq("countyName", countyName));
            }
            criteria.add(Restrictions.eq("validStatus", "1"));
            riskDaddressList = riskDaddressRepository.findAll(criteria);
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }
        return riskDaddressList;
    }

    /**
     * @param riskFileNo
     * @param insuredName
     * @return RiskInsResponseVo
     * @throws Exception
     * @功能：根据风控报告编号和被保险人名称模糊查询信息
     * @author wangkunlong @日期： 2018-5-9 @修改记录：
     */
    public RiskInsResponseVo queryRiskMainByInsured(String riskFileNo, String insuredName, int pageNo, int pageSize,
                                                    UserInfo userInfo, String riskModel) throws Exception {
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        try {
            Specification<RiskReportMain> spec = new Specification<RiskReportMain>() {
                @Override
                public Predicate toPredicate(Root<RiskReportMain> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<>();

                    // String userCodeInfo = userInfo.getUserCode();
                    if (StringUtils.isNotBlank(riskFileNo)) {
                        predicateList.add(criteriaBuilder.equal(root.get("riskFileNo"), riskFileNo.trim()));
                    }
                    if (StringUtils.isNotBlank(insuredName)) {
                        predicateList.add(criteriaBuilder.like(root.get("insuredName"), insuredName.trim()));
                    }
                    if (StringUtils.isNotBlank(riskModel)) {
                        predicateList.add(criteriaBuilder.equal(root.get("riskModel"), riskModel.trim()));
                    }
                    CriteriaBuilder.In<Object> underwriteFlagIn = criteriaBuilder.in(root.get("underwriteFlag"));
                    underwriteFlagIn.value(Arrays.asList("1", "3"));
                    predicateList.add(underwriteFlagIn);

                    // 权限校验开始-----
                    // SaaAPIService saaAPIService = new SaaAPIServiceImpl();
                    // try {
                    // String powerSQL = saaAPIService.addPower("riskcontrol", userCodeInfo, "riskins_query",
                    // "this_.comCode",
                    // "", "");
                    // queryRule.addSql(powerSQL);
                    // } catch (Exception e) {
                    // e.printStackTrace();
                    // LOGGER.info("addPower执行异常：" + e.getMessage(), e);
                    // throw new RuntimeException("addPower执行异常:" + e);
                    // }
                    Predicate[] arr = new Predicate[predicateList.size()];
                    return criteriaBuilder.and(predicateList.toArray(arr));
                }
            };

            // 权限校验结束-----
            Page<RiskReportMain> page = riskReportMainRepository.findAll(spec, PageRequest.of(pageNo - 1, pageSize));
            riskInsResponseVo = new RiskInsResponseVo();
            List<RiskReportMain> riskReportMainList = page.getContent();
            // List<RiskReportMain> riskReportMainListNew = new ArrayList<RiskReportMain>();
            // for (RiskReportMain r : riskReportMainList) {
            // // 拷贝RiskReportMain
            // RiskReportMain rNew = new RiskReportMain();
            // Datas.copySimpleObjectToTargetFromSource(rNew, r);
            // riskReportMainListNew.add(rNew);
            // }
            // Page pageNew = new Page(pageNo, page.getTotalCount(), pageSize, riskReportMainListNew);

            // 设置页码
            riskInsResponseVo.setPageNo(pageNo);
            // 设置条数
            riskInsResponseVo.setPageSize(pageSize);
            // 设置数据
            riskInsResponseVo.setDataList(page.getContent());
            // 设置数据总条数
            riskInsResponseVo.setTotalCount(page.getTotalElements());
            // 设置总页数
            riskInsResponseVo.setTotalPage(page.getTotalPages());
        } catch (Exception e) {
            LOGGER.info("查询信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询信息异常:" + e);
        }
        return riskInsResponseVo;
    }

    /**
     * @param comCode   登录人归属机构
     * @param upperCode 上级机构代码
     * @param gradeFlag 必传 1-省级；2-市级；3-县区级
     * @param powerFlag 是否加权限
     * @return List<RiskDaddress> @日期：2017-12-28
     * @功能:风控地址查询
     * @author 王亚军
     * @修改：liqiankun 20200120
     */
    public List<RiskDaddress> queryRiskDaddress(String comCode, String upperCode, String gradeFlag, boolean powerFlag) {
        Criteria<RiskDaddress> criteria = new Criteria<>();
        List<RiskDaddress> riskDaddressList = new ArrayList<RiskDaddress>();

        try {
            if ("1".equals(gradeFlag) && StringUtils.isBlank(upperCode) && powerFlag) {
                if (StringUtils.isNotBlank(comCode) && !"00000000".equals(comCode)) {
                    // List<String> upperCodeString = dictService.getAllUpperCode(comCode);
                    // upperCode = upperCodeString.get(upperCodeString.size()-2).substring(0,6);
                    upperCode = comCode.substring(0, 2) + "0000";
                }
            }

            if ("1".equals(gradeFlag)) {
                if (StringUtils.isBlank(upperCode)) {
                    criteria.add(Restrictions.eq("gradeFlag", gradeFlag));
                    criteria.add(Restrictions.eq("validStatus", "1"));
                    Sort sort = new Sort(Sort.Direction.ASC, "addressCode");
                    riskDaddressList = riskDaddressRepository.findAll(criteria, sort);
                } else {
                    criteria.add(Restrictions.eq("gradeFlag", gradeFlag));
                    criteria.add(Restrictions.eq("addressCode", upperCode));
                    criteria.add(Restrictions.eq("validStatus", "1"));
                    riskDaddressList = riskDaddressRepository.findAll(criteria);
                }
            } else {
                if (powerFlag && ("2102".equals(comCode.substring(0, 4)) // 大连
                        || "3302".equals(comCode.substring(0, 4)) // 宁波
                        || "3502".equals(comCode.substring(0, 4)) // 厦门
                        || "3702".equals(comCode.substring(0, 4)) // 青岛
                        || "4403".equals(comCode.substring(0, 4)) // 深圳
                )) {
                    criteria.add(Restrictions.eq("gradeFlag", gradeFlag));
                    criteria.add(Restrictions.eq("addressCode", comCode.substring(0, 6)));
                    criteria.add(Restrictions.eq("validStatus", "1"));
                    riskDaddressList = riskDaddressRepository.findAll(criteria);
                } else {
                    criteria.add(Restrictions.eq("upperCode", upperCode));
                    criteria.add(Restrictions.eq("gradeFlag", gradeFlag));
                    Sort sort = new Sort(Sort.Direction.ASC, "addressCode");
                    criteria.add(Restrictions.eq("validStatus", "1"));
                    riskDaddressList = riskDaddressRepository.findAll(criteria, sort);
                    // 某省下非计划单列市的市不能看单列市(除去全0机构)
                    if (powerFlag && !"00000000".equals(comCode)) {

                        String deleteCode = null;

                        if ("21".equals(comCode.substring(0, 2))) {
                            deleteCode = "210200";
                        } else if ("33".equals(comCode.substring(0, 2))) {
                            deleteCode = "330200";
                        } else if ("35".equals(comCode.substring(0, 2))) {
                            deleteCode = "350200";
                        } else if ("37".equals(comCode.substring(0, 2))) {
                            deleteCode = "370200";
                        } else if ("44".equals(comCode.substring(0, 2))) {
                            deleteCode = "440300";
                        }

                        if (StringUtils.isNotBlank(deleteCode)) {
                            Iterator<RiskDaddress> it = riskDaddressList.iterator();
                            while (it.hasNext()) {
                                RiskDaddress rdTem = it.next();
                                if (rdTem.getAddressCode().equals(deleteCode)) {
                                    it.remove();
                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("风控地址查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("风控地址查询异常:" + e);
        }

        return riskDaddressList;
    }

    /**
     * @param riskFileNo 风险档案编号
     * @return String 描述删除信息
     * @功能：风控报告删除
     * @author 李博儒
     * @修改：liqiankun 20200120
     */
    public String deleteRiskInfoByRiskFileNo(String riskFileNo) {
        String message = null;
        try {
            if (riskFileNo != null && StringUtils.isNotBlank(riskFileNo)) {
                riskReportMainRepository.deleteById(riskFileNo);
                message = "success";
            } else {
                message = "none";
            }
        } catch (Exception e) {
            LOGGER.info("风控报告删除异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("风控报告删除异常:" + e);
        }
        return message;
    }

    /**
     * @param riskInsRiskReportVo 风控基本信息
     * @throws @日期 2017-10-23
     * @功能：生成风控报告信息
     * @author 马军亮
     * @modify 张日炜 2020-01-19
     */
    public void saveRiskReportMain(RiskInsRiskReportVo riskInsRiskReportVo, UserInfo userInfo) throws Exception {

        try {
            if (riskInsRiskReportVo != null) {
                // 获取风控基本信息
                RiskReportMain riskReportMain = riskInsRiskReportVo.getRiskReportMainVo();
                riskReportMain.setUnderwriteFlag("T");
                if ("004".equals(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel())) {
                    List<RiskReportMachine> riskReportMachineList = riskInsRiskReportVo.getRiskReportMachineList();
                    if (riskReportMachineList != null) {
                        riskReportMain.setRiskReportMachineList(riskReportMachineList);
                    }
                } else if ("005".equals(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel())) {
                    List<RiskReportCloBusiness> riskReportCloBusinessList =
                            riskInsRiskReportVo.getRiskReportCloBusinessList();
                    if (riskReportCloBusinessList != null) {
                        riskReportMain.setRiskReportCloBusinessList(riskReportCloBusinessList);
                    }
                } else {
                    // 风控地址信息
                    List<RiskReportAddress> riskReportAddressList = riskInsRiskReportVo.getRiskReportAddressList();
                    if (riskReportAddressList != null) {
                        for (int i = 0; i < riskReportAddressList.size(); i++) {
                            RiskReportAddressId id = new RiskReportAddressId();
                            id.setSerialNo(i + 1);
                            riskReportAddressList.get(i).setId(id);
                        }
                        riskReportMain.setRiskReportAddressList(riskReportAddressList);
                    }
                    // 风控历史损失
                    List<RiskReportClaim> riskReportClaimList = new ArrayList<>();
                    try {
                        if (!"".equals(riskInsRiskReportVo.getRiskReportMainVo().getInsuredCode())) {
                            // 调外系统
                            // riskReportClaimList = this.setRiskHistory(riskInsRiskReportVo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    riskReportMain.setRiskReportClaimList(riskReportClaimList);
                    // 风控建筑
                    List<RiskReportConstruct> riskReportConstructList =
                            riskInsRiskReportVo.getRiskReportConstructList();
                    if (riskReportConstructList != null) {
                        riskReportMain.setRiskReportConstructList(riskReportConstructList);
                    }
                    // 风控建筑详细信息
                    List<RiskReportConstructInfo> riskReportConstructInfoList =
                            riskInsRiskReportVo.getRiskReportConstructInfoList();
                    if (riskReportConstructInfoList != null) {
                        for (int i = 0; i < riskReportConstructInfoList.size(); i++) {
                            RiskReportConstructInfoId id = new RiskReportConstructInfoId();
                            id.setSerialNo(i + 1);
                            riskReportConstructInfoList.get(i).setId(id);
                        }
                        riskReportMain.setRiskReportConstructInfoList(riskReportConstructInfoList);
                    }
                    // 风控占用性质
                    List<RiskReportOccupation> riskReportOccupationList =
                            riskInsRiskReportVo.getRiskReportOccupationList();
                    if (riskReportOccupationList != null) {
                        riskReportMain.setRiskReportOccupationList(riskReportOccupationList);
                    }
                    // 风控保护措施
                    List<RiskReportProtection> riskReportProtectionList =
                            riskInsRiskReportVo.getRiskReportProtectionList();
                    if (riskReportProtectionList != null) {
                        riskReportMain.setRiskReportProtectionList(riskReportProtectionList);
                    }
                    // 风控环境
                    List<RiskReportEnvironment> riskReportEnvironmentList =
                            riskInsRiskReportVo.getRiskReportEnvironmentList();
                    if (riskReportEnvironmentList != null) {
                        riskReportMain.setRiskReportEnvironmentList(riskReportEnvironmentList);
                    }
                    // 风控附加扩展盗窃、抢劫责任
                    List<RiskReportTheft> riskReportTheftList = riskInsRiskReportVo.getRiskReportTheftList();
                    if (riskReportTheftList != null) {
                        riskReportMain.setRiskReportTheftList(riskReportTheftList);
                    }
                    // 风控附加扩展供应中断责任
                    List<RiskReportInterrupt> riskReportInterruptList =
                            riskInsRiskReportVo.getRiskReportInterruptList();
                    if (riskReportInterruptList != null) {
                        riskReportMain.setRiskReportInterruptList(riskReportInterruptList);
                    }
                    // 风控风险值
                    List<RiskReportAssess> riskReportAssesList = riskInsRiskReportVo.getRiskReportAssessList();
                    if (riskReportAssesList != null) {
                        riskReportMain.setRiskReportAssessList(riskReportAssesList);
                    }
                    // 风控附加扩展露天堆放
                    List<RiskReportAirStorage> riskReportAirStorageList =
                            riskInsRiskReportVo.getRiskReportAirStorageList();
                    if (riskReportAirStorageList != null) {
                        riskReportMain.setRiskReportAirStorageList(riskReportAirStorageList);
                    }
                    /*风控火灾风险排查专业版和简化版*/
                    List<RiskReportFireDanger> riskReportFireDangerList =
                            riskInsRiskReportVo.getRiskReportFireDangerList();
                    if (riskReportFireDangerList != null) {
                        riskReportMain.setRiskReportFireDangerList(riskReportFireDangerList);
                    }

                }
                // 为大对象的某特定字段赋值
                setValueforSpecificField(riskInsRiskReportVo, "RiskFileNo",
                        riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo());
                riskReportMainRepository.save(riskReportMain);
            }
        } catch (Exception e) {
            LOGGER.info("生成风控报告信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("生成风控报告信息异常:" + e);
        }
    }

    /**
     * @param object    大对象
     * @param fieldName 字段名称（以大写字母开头）
     * @param value     要设置的值
     * @throws @日期 2017-10-23
     * @功能：为大对象的某特定字段赋值
     * @author 马军亮
     * @modify 张日炜 2020-01-19
     */
    @SuppressWarnings("unchecked")
    public static void setValueforSpecificField(Object object, String fieldName, Object value) throws Exception {
        try {
            if (object != null) {
                Object[] SINGLE_ELEM_ARRAY = new Object[1];
                Class objectClass = object.getClass();
                // 获取所有getter方法
                List<Method> getterMethods = getGetter(objectClass);
                // 获取方法实例
                Class paramType = null;
                String currentFieldName;
                // 获取所有字表
                for (Method method : getterMethods) {
                    currentFieldName = method.getName().substring(3);
                    // 一对多子表处理
                    if (method.getReturnType() == List.class) {
                        List<Object> subObjects = (List<Object>) method.invoke(object, EMPTY_ARRAY);
                        if (subObjects == null) {
                            continue;
                        }
                        // 循环遍历子对象
                        for (Object obj : subObjects) {
                            setValueforSpecificField(obj, fieldName, value);
                        }
                        // 联合主键处理
                    } else if ("Id".equals(currentFieldName)) {
                        Object objectID = method.invoke(object, EMPTY_ARRAY);
                        setValueforSpecificField(objectID, fieldName, value);
                    } else if (currentFieldName.equals(fieldName)) {
                        // 找到了要赋值的属性，执行赋值（1、加if是为了不重复获取，2、在这里获取是避免NoSuchMethodException）
                        if (paramType == null) {
                            // 首先得到getter方法（用于获取返回值的类型）
                            Method getterMethod = objectClass.getMethod("get" + fieldName);
                            // 获取其返回值的类型
                            paramType = getterMethod.getReturnType();
                        }
                        // 得到其setter方法
                        Method setterMethod = objectClass.getMethod("set" + fieldName, paramType);

                        SINGLE_ELEM_ARRAY[0] = value;
                        setterMethod.invoke(object, SINGLE_ELEM_ARRAY);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("某特定字段赋值异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("某特定字段赋值异常:" + e);
        }
    }

    /**
     * @param cl 待获取的类
     * @return 所有get方法
     * @throws @日期 2017-10-23
     * @功能：获取类及其父类的所有get方法
     * @author 马军亮
     * @modify 张日炜 2020-01-19
     */
    private static List<Method> getGetter(Class cl) {
        List<Method> list = new ArrayList<>();
        try {
            Method[] methods = cl.getDeclaredMethods();
            int lgn = methods.length;
            for (Method method : methods) {
                String methodName = method.getName();
                // 以set或is开头的方法
                if (methodName.startsWith("get") || methodName.startsWith("is")) {
                    list.add(method);
                }
            }
            cl = cl.getSuperclass();
            // 递归获取父类的get方法
            if (cl != Object.class) {
                list.addAll(getGetter(cl));
            }
        } catch (SecurityException e) {
            LOGGER.info("获取类及其父类的所有get方法异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("获取类及其父类的所有get方法异常:" + e);
        }
        return list;
    }

    /**
     * @param archivesNo,riskFileNo, RiskReportSaleImaTypeList,riskModel
     * @return boolean @日期：2018-07-04
     * @功能：复制影像系统照片档案号的图片信息到对应风控编号节点下
     * @author 马军亮
     * @modify 张日炜 2020-01-19
     */
    public void copyImagesByArchivesNo(String archivesNo, String riskFileNo, List<Object[]> riskReportSaleImageList,
                                       String riskModel) throws Exception {

        try {
            if ("002".equals(riskModel)) {
                for (Object[] objects : riskReportSaleImageList) {
                    String imageType = (String) objects[1];
                    String imageName = (String) objects[2];
                    String remark = (String) objects[3];
                    String imageurl = (String) objects[4];
                    String thumurl = (String) objects[5];
                    if (!(imageType.contains("_PG"))) {
                        RiskReportPicture riskReportPicture = new RiskReportPicture();
                        RiskReportPictureId riskReportPictureId = new RiskReportPictureId();
                        riskReportPictureId.setChildPath(imageType);
                        riskReportPictureId.setImageName(imageName);
                        riskReportPictureId.setRiskFileNo(riskFileNo);
                        riskReportPicture.setImageUrl(imageurl);
                        riskReportPicture.setThumUrl(thumurl);
                        riskReportPicture.setRemark(remark);
                        riskReportPicture.setId(riskReportPictureId);
                        this.savePicture(riskReportPicture);
                    }
                }
            } else if ("001".equals(riskModel)) {
                for (Object[] objects : riskReportSaleImageList) {
                    String imageType = (String) objects[1];
                    String imageName = (String) objects[2];
                    String remark = (String) objects[3];
                    String imageurl = (String) objects[4];
                    String thumurl = (String) objects[5];
                    if (!(imageType.contains("_PG"))) {
                        RiskReportPicture riskReportPicture = new RiskReportPicture();
                        RiskReportPictureId riskReportPictureId = new RiskReportPictureId();
                        riskReportPictureId.setRiskFileNo(riskFileNo);
                        riskReportPicture.setImageUrl(imageurl);
                        riskReportPicture.setThumUrl(thumurl);
                        riskReportPicture.setRemark(remark);
                        switch (imageType) {
                            case "3.2":
                                imageType = "3.2.1";
                                imageName = imageName.replaceAll("3.2", "3.2.1");
                                break;
                            case "3.3":
                                imageType = "3.4";
                                imageName = imageName.replaceAll("3.3", "3.4");
                                break;
                            case "4.2.3":
                                imageType = "4.2.4";
                                imageName = imageName.replaceAll("4.2.3", "4.2.4");
                                break;
                            case "4.2.2":
                                imageType = "4.2.3";
                                imageName = imageName.replaceAll("4.2.2", "4.2.3");
                                break;
                            case "4.1.1.2":
                                imageType = "4.1.1.4";
                                imageName = imageName.replaceAll("4.1.1.2", "4.1.1.4");
                                break;
                            case "4.1.3":
                                imageType = "4.1.3.1";
                                imageName = imageName.replaceAll("4.1.3", "4.1.3.1");
                                break;
                            case "4.2.4":
                                imageType = "4.2.5";
                                imageName = imageName.replaceAll("4.2.4", "4.2.5");
                                break;
                            case "4.2.5":
                                imageType = "4.2.6.1";
                                imageName = imageName.replaceAll("4.2.5", "4.2.6.1");
                                break;
                            case "4.2.6":
                                imageType = "4.2.9";
                                imageName = imageName.replaceAll("4.2.6", "4.2.9");
                                break;
                            case "5.1":
                                imageType = "5.1.3";
                                imageName = imageName.replaceAll("5.1", "5.1.3");
                                break;
                            case "4.3.2":
                                imageType = "4.3.3";
                                imageName = imageName.replaceAll("4.3.2", "4.3.3");
                                break;
                            case "2.2":
                                imageType = "2.2.1";
                                imageName = imageName.replaceAll("2.2", "2.2.1");
                                break;
                            default:
                                imageType = "2.2.1";
                                imageName = imageName.replaceAll("2.2", "2.2.1");
                                break;
                        }

                        riskReportPictureId.setChildPath(imageType);
                        riskReportPictureId.setImageName(imageName + ".jpg");
                        riskReportPicture.setId(riskReportPictureId);
                        this.savePicture(riskReportPicture);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("复制影像系统异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("复制影像系统异常:" + e);
        }
    }

    private void savePicture(RiskReportPicture riskReportPicture) {
        try {
            riskReportPictureRepository.save(riskReportPicture);
        } catch (Exception e) {
            LOGGER.info("保存异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("保存异常:" + e);
        }
    }

    /**
     * @Description:插入打回原因 @author: QuLingjie @data: 2020/1/20 @param executionId @param
     * updateType @return:java.lang.Long @throws
     */
    public Long returnRiskUnderwritebackCount(String executionId, String updateType) {
        Long size = null;
        try {

            size = riskUnderwritebackRepository.countByExecutionIdAndUpdateType(executionId, updateType);
            return size;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * "导出"按钮查询 不使用分页
     */
    public RiskInsResponseVo queryRiskInsForExport(RiskInsRequestVo riskInsRequestVo, UserInfo userInfo) {
        List<RiskReportMain> riskReportMainList = null;
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        try {
            String userCodeInfo = "";
            String comCodeInfo = "";
            if (userInfo != null) {
                userCodeInfo = userInfo.getUserCode();
                comCodeInfo = userInfo.getComCode();
            }
            Criteria<RiskReportMain> criteria = new Criteria<>();
            Sort sort = null;
            // 多条件进行排序
            List<Order> orders = new ArrayList<Order>();
            // QueryRule queryRule = QueryRule.getInstance();
            // Page page = new Page();

            RiskReportMainQueryVo riskReportMainVo = riskInsRequestVo.getRiskReportMainVo();

            if (riskReportMainVo != null) {
                // 风险档案编号
                String riskFileNo = riskReportMainVo.getRiskFileNo();
                if (StringUtils.isNotBlank(riskFileNo)) {
                    // queryRule.addEqual("riskFileNo", riskFileNo.trim());
                    criteria.add(Restrictions.eq("riskFileNo", riskFileNo.trim()));
                }
                // 投保险类
                String classCode = riskReportMainVo.getClassCode();
                if (StringUtils.isNotBlank(classCode)) {
                    // queryRule.addEqual("classCode", classCode.trim());
                    criteria.add(Restrictions.eq("classCode", classCode.trim()));
                }
                // 投保险种
                String riskCode = riskReportMainVo.getRiskCode();
                if (StringUtils.isNotBlank(riskCode)) {
                    // queryRule.addEqual("riskCode", riskCode.trim());
                    criteria.add(Restrictions.eq("riskCode", riskCode.trim()));
                }
                // 风控报告模板
                String riskModel = riskReportMainVo.getRiskModel();
                if (StringUtils.isNotBlank(riskModel)) {
                    // queryRule.addEqual("riskModel", riskModel.trim());
                    criteria.add(Restrictions.eq("riskModel", riskModel.trim()));
                }
                // 被保险人
                String insuredName = riskReportMainVo.getInsuredName();
                if (StringUtils.isNotBlank(insuredName)) {
                    // queryRule.addLike("insuredName", insuredName.trim() + "%");
                    // StringBuilder hql = new StringBuilder();
                    // hql.append("(this_.insuredName like '" + insuredName + "%' or this_.insuredCode like'"
                    // + insuredName + "%' )");
                    // queryRule.addSql(hql.toString());
                    criteria.add(Restrictions.or(Restrictions.like("insuredName", insuredName + "%"),
                            Restrictions.like("insuredCode", insuredName + "%")));
                }
                // 被保险人代码
                String insuredCode = riskReportMainVo.getInsuredCode();
                if (StringUtils.isNotBlank(insuredCode)) {
                    // queryRule.addEqual("insuredCode", insuredCode.trim());
                    criteria.add(Restrictions.eq("insuredCode", insuredCode.trim()));
                }
                // 查勘人
                String explorer = riskReportMainVo.getExplorer();
                String explorerCode = riskReportMainVo.getExplorerCode();
                if (StringUtils.isNotBlank(explorer) && StringUtils.isNotBlank(explorerCode)) {
                    // queryRule.addIn("explorer", explorer.trim(), explorerCode.trim());
                    criteria
                            .add(Restrictions.in("explorer", Arrays.asList(explorer.trim(), explorerCode.trim()), true));
                }
                if (StringUtils.isNotBlank(explorerCode) && !StringUtils.isNotBlank(explorer)) {
                    // queryRule.addEqual("explorer", explorerCode.trim());
                    criteria.add(Restrictions.eq("explorer", explorerCode.trim()));
                }
                if (!StringUtils.isNotBlank(explorerCode) && StringUtils.isNotBlank(explorer)) {
                    // queryRule.addEqual("explorer", explorer.trim());
                    criteria.add(Restrictions.eq("explorer", explorer.trim()));
                }
                // 移动端根据usercode查询结果降序排列
                if (riskReportMainVo.getMobFlag()) {
                    // queryRule.addDescOrder("riskFileNo");
                    // sort = new Sort(Sort.Direction.DESC, "riskFileNo");
                    orders.add(new Order(Direction.DESC, "riskFileNo"));
                }
                // 归属机构
                String comCode = riskReportMainVo.getComCode();
                if (StringUtils.isNotBlank(comCode)) {
                    PrpDcompanyFk prpDcompanyFk = new PrpDcompanyFk();
                    // QueryRule queryRuleP = QueryRule.getInstance();
                    // QueryRule queryRuleList = QueryRule.getInstance();
                    Criteria<PrpDcompanyFk> criteriaList = new Criteria<>();

                    // queryRuleP.addEqual("comCode", comCode);
                    // prpDcompanyFk = databaseDao.findUnique(PrpDcompanyFk.class, queryRuleP);

                    prpDcompanyFk = prpDcompanyFkRepository.findByComCode(comCode);
                    String upperPath = prpDcompanyFk.getUpperPath();
                    if (StringUtils.isNotBlank(upperPath)) {
                        upperPath = upperPath.trim();
                        // upperPath = "31000000,21029913";
                        if ("00".equals(upperPath.substring(upperPath.length() - 8, upperPath.length() - 6))) {
                        } else if (upperPath.length() == 17) {
                            if ("2102,3302,3502,3702,4403".indexOf(upperPath.substring(9, 13)) > -1) {
                                // queryRule.addSql("substr(this_.comCode,0,4)='" + upperPath.substring(9, 13) + "'");
                                criteria.add(Restrictions.intercept("comCode", upperPath.substring(9, 13), 1, 4));
                            } else {
                                if ("21".equals(upperPath.substring(9, 11))) {
                                    // queryRule.addSql("substr(this_.comCode,0,2)='" + upperPath.substring(9, 11)
                                    // + "' and substr(this_.comCode,0,4) !='2102'");
                                    criteria.add(Restrictions.and(
                                            Restrictions.intercept("comCode", upperPath.substring(9, 11), 1, 2),
                                            Restrictions.nintercept("comCode", "2102", 1, 4)));
                                } else if ("33".equals(upperPath.substring(9, 11))) {
                                    // queryRule.addSql("substr(this_.comCode,0,2)='" + upperPath.substring(9, 11)
                                    // + "' and substr(this_.comCode,0,4) !='3302'");
                                    criteria.add(Restrictions.and(
                                            Restrictions.intercept("comCode", upperPath.substring(9, 11), 1, 2),
                                            Restrictions.nintercept("comCode", "3302", 1, 4)));
                                } else if ("35".equals(upperPath.substring(9, 11))) {
                                    // queryRule.addSql("substr(this_.comCode,0,2)='" + upperPath.substring(9, 11)
                                    // + "' and substr(this_.comCode,0,4) !='3502'");
                                    criteria.add(Restrictions.and(
                                            Restrictions.intercept("comCode", upperPath.substring(9, 11), 1, 2),
                                            Restrictions.nintercept("comCode", "3502", 1, 4)));
                                } else if ("37".equals(upperPath.substring(9, 11))) {
                                    // queryRule.addSql("substr(this_.comCode,0,2)='" + upperPath.substring(9, 11)
                                    // + "' and substr(this_.comCode,0,4) !='3702'");
                                    criteria.add(Restrictions.and(
                                            Restrictions.intercept("comCode", upperPath.substring(9, 11), 1, 2),
                                            Restrictions.nintercept("comCode", "3702", 1, 4)));
                                } else if ("44".equals(upperPath.substring(9, 11))) {
                                    // queryRule.addSql("substr(this_.comCode,0,2)='" + upperPath.substring(9, 11)
                                    // + "' and substr(this_.comCode,0,4) !='4403'");
                                    criteria.add(Restrictions.and(
                                            Restrictions.intercept("comCode", upperPath.substring(9, 11), 1, 2),
                                            Restrictions.nintercept("comCode", "4403", 1, 4)));
                                } else {
                                    // queryRule.addSql("substr(this_.comCode,0,2)='" + upperPath.substring(9, 11) +
                                    // "'");
                                    criteria.add(Restrictions.intercept("comCode", upperPath.substring(9, 11), 1, 2));
                                }
                            }
                        } else if (upperPath.length() > 17) {
                            // queryRuleList.addSql("substr(this_.upperpath,0,26)='" + upperPath.substring(0, 26) +
                            // "'");
                            // List<PrpDcompanyFk> prpDcompanyFkList = databaseDao.findAll(PrpDcompanyFk.class,
                            // queryRuleList);

                            criteriaList.add(Restrictions.intercept("upperpath", upperPath.substring(0, 26), 1, 26));
                            List<PrpDcompanyFk> prpDcompanyFkList = prpDcompanyFkRepository.findAll(criteriaList);
                            List<String> comCodeList = new ArrayList();
                            if (null != prpDcompanyFkList && prpDcompanyFkList.size() > 0) {
                                for (int i = 0; i < prpDcompanyFkList.size(); i++) {
                                    comCodeList.add(prpDcompanyFkList.get(i).getComCode());
                                }
                            }
                            // queryRule.addIn("comCode", comCodeList);
                            criteria.add(Restrictions.in("comCode", comCodeList, true));
                        }
                    }
                }
                // 时间倒叙排列
                // queryRule.addDescOrder("insertTimeForHis");
                orders.add(new Order(Direction.DESC, "insertTimeForHis"));
                // 查勘日期起期
                Date exploreDateBegin = riskReportMainVo.getExploreDateBegin();
                // 查勘日期止期
                Date exploreDateEnd = riskReportMainVo.getExploreDateEnd();
                if (exploreDateBegin != null || exploreDateEnd != null) {
                    if (exploreDateBegin != null && exploreDateEnd != null) {
                        // queryRule.addBetween("exploreDate", exploreDateBegin, exploreDateEnd);
                        criteria.add(Restrictions.between("exploreDate", exploreDateBegin, exploreDateEnd));
                    } else {
                        if (exploreDateBegin != null) {
                            // queryRule.addGreaterEqual("exploreDate", exploreDateBegin);
                            criteria.add(Restrictions.gte("exploreDate", exploreDateBegin));
                        } else {
                            // queryRule.addLessEqual("exploreDate", exploreDateEnd);
                            criteria.add(Restrictions.lte("exploreDate", exploreDateBegin));
                        }
                    }
                }
                // 外系统起期
                Date outerOperateDateBegin = riskReportMainVo.getOuterOperateDateBegin();
                // 外系统止期
                Date outerOperateDateEnd = riskReportMainVo.getOuterOperateDateEnd();
                if (outerOperateDateBegin != null || outerOperateDateEnd != null) {
                    if (outerOperateDateBegin != null && outerOperateDateEnd != null) {
                        // queryRule.addBetween("madeDate", outerOperateDateBegin, outerOperateDateEnd);
                        criteria.add(Restrictions.between("madeDate", outerOperateDateBegin, outerOperateDateEnd));
                    } else {
                        if (outerOperateDateBegin != null) {
                            // queryRule.addGreaterEqual("madeDate", outerOperateDateBegin);
                            criteria.add(Restrictions.gte("madeDate", outerOperateDateBegin));
                        } else {
                            // queryRule.addLessEqual("madeDate", outerOperateDateEnd);
                            criteria.add(Restrictions.lte("madeDate", outerOperateDateEnd));
                        }
                    }
                }
                // 查勘类别
                String exploreType = riskReportMainVo.getExploreType();
                if (StringUtils.isNotBlank(exploreType)) {
                    // queryRule.addEqual("exploreType", exploreType.trim());
                    criteria.add(Restrictions.eq("exploreType", exploreType.trim()));
                }
                // 查勘地址
                String addressDetail = riskReportMainVo.getAddressDetail();
                if (StringUtils.isNotBlank(addressDetail)) {
                    // queryRule.addLike("addressDetail", addressDetail.trim() + "%");
                    criteria.add(Restrictions.like("addressDetail", addressDetail.trim() + "%"));
                }
                // 普通查询中的核保状态：:暂存，1:通过；审核查询中的标志位:4:待市级审核，9:待省级审核
                String[] underwriteFlag = riskReportMainVo.getUnderwriteFlag();
                // 审核查询若无标志位，则查询出待审核的风控报告
                if ("underwrite".equals(riskReportMainVo.getBusinessType())
                        && (underwriteFlag == null || underwriteFlag.length == 0)) {
                    // queryRule.addIn("underwriteFlag", Arrays.asList("4", "9"));
                    criteria.add(Restrictions.in("underwriteFlag", Arrays.asList("4", "9"), true));
                } else {
                    List<String> underwriteFlagList = new ArrayList<>();
                    if (null != underwriteFlag && underwriteFlag.length > 0) {
                        for (int i = 0; i < underwriteFlag.length; i++) {
                            underwriteFlagList.add(underwriteFlag[i]);
                        }
                    }
                    if (null != underwriteFlagList && underwriteFlagList.size() > 0) {
                        // queryRule.addIn("underwriteFlag", underwriteFlagList);
                        criteria.add(Restrictions.in("underwriteFlag", underwriteFlagList, true));
                    }
                }
                /*
                 * //权限校验开始-----外系统不走addpower方法
                 */
                if (!"1".equals(riskReportMainVo.getOuterOperateFlag())) {
                    // SaaAPIService saaAPIService = new SaaAPIServiceImpl();
                    // try {
                    // String powerSQL = saaAPIService.addPower("riskcontrol", userCodeInfo, "riskins_query",
                    // "this_.comCode", "", "");
                    // queryRule.addSql(powerSQL);
                    // } catch (Exception e) {
                    // LOGGER.info("addPower执行异常：" + e.getMessage(), e);
                    // e.printStackTrace();
                    // throw new RuntimeException("addPower执行异常:" + e);
                    // }
                }
                // 权限校验结束-----执行查询 获得风控信息表数据集合 最多查询条数不超过2000条
                // Page resultPage = databaseDao.findPage(RiskReportMain.class, queryRule, 0, 2000);
                // riskReportMainList = resultPage.getResult();
                sort = new Sort(orders);
                Page<RiskReportMain> resultPage =
                        riskReportMainRepository.findAll(criteria, PageRequest.of(0, 2000, sort));
                riskReportMainList = resultPage.getContent();
            }
            List<RiskReportMainResponseVo> riskReportMainListNew = new ArrayList<RiskReportMainResponseVo>();
            for (RiskReportMain r : riskReportMainList) {
                RiskReportMainResponseVo rNew = new RiskReportMainResponseVo();
                BeanUtils.copyProperties(r, rNew);
                // 各灾因风险值赋值
                if (r.getRiskReportAssessList() != null && r.getRiskReportAssessList().size() > 0) {
                    BeanUtils.copyProperties(r.getRiskReportAssessList().get(0), rNew);
                }
                riskReportMainListNew.add(rNew);
            }
            riskInsResponseVo.setDataList(riskReportMainListNew);

            Map<String, RiskDcode> exploreTypeMap = null;
            Map<String, RiskDcode> underwriteFlagMap = null;
            Map<String, Code> riskCodeMap = null;

            List dataList = riskInsResponseVo.getDataList();
            if (dataList.size() > 0) {
                exploreTypeMap = dataSourcesService.queryRiskDcode("exploreType");
                underwriteFlagMap = dataSourcesService.queryRiskDcode("underwriteFlag");
                try {
                    //险种翻译注释20200302modifyby liqiankun
//                    riskCodeMap = dataSourcesService.queryAllRiskCode(comCodeInfo);
                } catch (Exception e) {
                    LOGGER.info("查询信息异常：" + e.getMessage(), e);
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            // 代码转中文
            for (int i = 0; i < dataList.size(); i++) {
                RiskReportMainResponseVo mainVo = (RiskReportMainResponseVo) dataList.get(i);
                // 查勘类别翻译
                if (StringUtils.isNotBlank(mainVo.getExploreType())) {
                    RiskDcode riskDcodeExploreType = exploreTypeMap.get(mainVo.getExploreType());
                    if (riskDcodeExploreType != null) {
                        mainVo.setExploreType(riskDcodeExploreType.getCodeCname());
                    }
                }
                // 核保状态翻译
                if (StringUtils.isNotBlank(mainVo.getUnderwriteFlag())) {
                    RiskDcode riskDcodeUnderwriteFlag = underwriteFlagMap.get(mainVo.getUnderwriteFlag());
                    if (riskDcodeUnderwriteFlag != null) {
                        mainVo.setUnderwriteFlag(riskDcodeUnderwriteFlag.getCodeCname());
                    }
                }
                // 投保险种翻译
                if (StringUtils.isNotBlank(mainVo.getRiskCode())) {
//                    Code codeRiskCode = riskCodeMap.get(mainVo.getRiskCode());
//                    if (codeRiskCode != null) {
//                        mainVo.setRiskCode(codeRiskCode.getName());
//                    }
                }
                // 查勘人中文翻译
                if (StringUtils.isNotBlank(mainVo.getExplorer())) {
                    mainVo.setExplorerCName(mainVo.getExplorer());
                }
                // 归属机构中文翻译
                if (StringUtils.isNotBlank(mainVo.getComCode())) {
                    mainVo.setComCodeCName(mainVo.getComCode());
                }
                // 承保建议中文翻译
                if (StringUtils.isNotBlank(mainVo.getRiskSuggest())) {
                    switch (mainVo.getRiskSuggest().trim()) {
                        case "A":
                            mainVo.setRiskSuggest("谨慎承保");
                            break;
                        case "B":
                            mainVo.setRiskSuggest("提高条件承保");
                            break;
                        case "C":
                            mainVo.setRiskSuggest("竞争性承保");
                            break;
                    }
                }
                // 风控模板类型翻译
                if (StringUtils.isNotBlank(mainVo.getRiskModel())) {
                    switch (mainVo.getRiskModel().trim()) {
                        case "001":
                            mainVo.setRiskModel("2017版财产险风控模板（专职版）");
                            break;
                        case "002":
                            mainVo.setRiskModel("2017版财产险风控模板（兼职版）");
                            break;
                        case "004":
                            mainVo.setRiskModel("机器损坏险专用风控模板");
                            break;
                        case "005":
                            mainVo.setRiskModel("营业中断险专用风控模板");
                            break;
                        case "006":
                            mainVo.setRiskModel("火灾风险排查（专业版）");
                            break;
                        case "007":
                            mainVo.setRiskModel("火灾风险排查（简化版）");
                            break;
                    }
                }
                // 风控报告来源翻译
                if (StringUtils.isNotBlank(mainVo.getMobileFlag())) {
                    switch (mainVo.getMobileFlag().trim()) {
                        case "0":
                            mainVo.setMobileFlag("PC");
                            break;
                        case "1":
                            mainVo.setMobileFlag("Android");
                            break;
                        case "2":
                            mainVo.setMobileFlag("IOS");
                            break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }
        return riskInsResponseVo;
    }

    /**
     * @Description: @author: QuLingjie @data: 2020/1/20 @param executionId @param operatorCode @param updateType @param
     * repulsesugggest @return:void @throws
     */
    public void insertRiskUnderwriteback(String executionId, String operatorCode, String updateType,
                                         String repulsesugggest) {
        Long size = returnRiskUnderwritebackCount(executionId, updateType);
        int count = Integer.parseInt(String.valueOf(size));
        RiskUnderwriteback time = new RiskUnderwriteback();
        RiskUnderwritebackId id = new RiskUnderwritebackId();
        id.setExecutionId(executionId);
        id.setSerialNo((short) (count + 1));
        id.setUpdateType(updateType);
        time.setId(id);
        time.setOperatorCode(operatorCode);
        time.setRepulsesugggest(repulsesugggest);

        riskUnderwritebackRepository.save(time);
    }

    /**
     * @param riskFileNo 风控编号
     * @param othSuggest 其他意见
     * @return RiskInsResponseVo @日期：2018-5-24
     * @功能:保存风控报告其他意见
     * @author 马军亮
     */
    public RiskInsResponseVo updateOthSuggest(String riskFileNo, String othSuggest) {
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        try {
            Optional<RiskReportMain> findById = riskReportMainRepository.findById(riskFileNo.trim());
            RiskReportMain riskReportMain = findById.get();
            riskReportMainRepository.save(riskReportMain);
            riskReportMain.setOthSuggest(othSuggest);
            riskInsResponseVo.setMessage("更新成功");
            riskInsResponseVo.setStatus(0);
        } catch (Exception e) {
            LOGGER.info("保存风控报告其他意见异常：" + e.getMessage(), e);
            e.printStackTrace();
            riskInsResponseVo.setMessage("更新失败");
            riskInsResponseVo.setStatus(1);
            throw new RuntimeException("保存风控报告其他意见异常:" + e);
        }
        return riskInsResponseVo;
    }

    /**
     * @return RiskInsResponseVo
     * @功能:更新风控报告照片信息
     * @author liqiankun
     * @日期：20200120
     */
    public List<RiskReportPicture> getRiskReportPictureList(List<RiskDcode> riskDcodeList, String riskFileNo,
                                                            UserInfo userInfo) {
        List<RiskReportPicture> riskReportPictures = new ArrayList<RiskReportPicture>(0);
        try {
            if (riskDcodeList != null && riskDcodeList.size() != 0) {
                // List<RiskDcode> riskDcodeList = riskInsRiskReportVo.getRiskDcodeList();
                // 移动端照片资料表
                for (RiskDcode riskDcode : riskDcodeList) {
                    if (riskDcode.getConfigList() != null && riskDcode.getConfigList().getConfigs().size() != 0) {
                        List<ImagePreviewConfigVo> configs = riskDcode.getConfigList().getConfigs();
                        for (ImagePreviewConfigVo imagePreviewConfigVo : configs) {
                            RiskReportPicture riskReportPicture = new RiskReportPicture();
                            RiskReportPictureId riskReportPictureId = new RiskReportPictureId();
                            riskReportPictureId.setChildPath(imagePreviewConfigVo.getExtra().getChildPath());
                            riskReportPictureId.setImageName(imagePreviewConfigVo.getCaption());
                            riskReportPictureId.setRiskFileNo(riskFileNo);
                            // 封装影像资料
                            // riskReportPicture.setBusinessNo(riskFileNo);
                            // riskReportPicture.setChildPath(imagePreviewConfigVo.getExtra().getChildPath());
                            riskReportPicture.setImageID("");
                            riskReportPicture.setImageUrl(imagePreviewConfigVo.getLocalPath());
                            // riskReportPicture.setImageName(imagePreviewConfigVo.getCaption());
                            riskReportPicture.setUserCode(userInfo.getUserCode());
                            riskReportPicture.setRemark(imagePreviewConfigVo.getRemark());
                            riskReportPicture.setId(riskReportPictureId);
                            riskReportPictures.add(riskReportPicture);
                        }
                    }
                }
                // 保存日志和图片资料
                // riskReportMain.setRiskReportPictureList(riskReportPictures);
            }
        } catch (Exception e) {
            LOGGER.info("更新风控报告照片信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("更新风控报告照片信息异常:" + e);
        }
        return riskReportPictures;
    }

    /**
     * @功能：风控报告保存方法
     * @author 马军亮
     * @时间：2018-06-21
     * @修改记录：liqiankun 20200121
     */
    public ApiResponse saveDetailRiskReportMain(RiskInsRiskReportVo riskInsRiskReportVo,
                                                List<CommonsMultipartFile> fileLists, UserInfo userInfo, String protocol, RiskInsGradeVo riskInsGradeVo) {
//        AjaxResult ajaxResult = new AjaxResult();
        ApiResponse apiResponse = new ApiResponse();
        FTPUtil ftp = new FTPUtil();
        try {
            // 获取持久对象
            Criteria<RiskReportMain> criteria = new Criteria<>();
            RiskReportMain source = null;
            if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo())) {
                criteria.add(Restrictions.eq("riskFileNo", riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo().trim()));
                Optional<RiskReportMain> optionalMain = riskReportMainRepository.findOne(criteria);
                source = optionalMain.isPresent() ? optionalMain.get() : null;
            }

            // 获取风控报告编号
            String riskFileNo = riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo();
            String riskModel = riskInsRiskReportVo.getRiskReportMainVo().getRiskModel();
            // 获取风控基本信息
            RiskReportMain riskReportMain = riskInsRiskReportVo.getRiskReportMainVo();
            if (riskInsRiskReportVo.getRiskReportMainVo().getPointy2000() != null
                    && riskInsRiskReportVo.getRiskReportMainVo().getPointx2000() != null
                    && StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getPointy2000().toString())
                    && StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getPointx2000().toString())
            ) {
                Gps gps = MapTransferUtils.gps84_To_Gcj02(riskInsRiskReportVo.getRiskReportMainVo().getPointy2000().doubleValue(),
                        riskInsRiskReportVo.getRiskReportMainVo().getPointx2000().doubleValue());
                riskReportMain.setPointx02(new BigDecimal(gps.getWgLon()));
                riskReportMain.setPointy02(new BigDecimal(gps.getWgLat()));
            }
            // 风控地址信息
            List<RiskReportAddress> riskReportAddressList = riskInsRiskReportVo.getRiskReportAddressList();
            if (riskReportAddressList != null) {
                for (int i = 0; i < riskReportAddressList.size(); i++) {
                    RiskReportAddressId id = new RiskReportAddressId();
                    id.setSerialNo(i + 1);
                    riskReportAddressList.get(i).setId(id);
                }
                riskReportMain.setRiskReportAddressList(riskReportAddressList);
            }
            if ("004".equals(riskModel)) {
                // 获取机器损坏风险评估
                List<RiskReportMachine> riskReportMachineList = riskInsRiskReportVo.getRiskReportMachineList();
                if (riskReportMachineList != null) {
                    riskReportMain.setRiskReportMachineList(riskReportMachineList);
                }
            } else if ("005".equals(riskModel)) {
                // 获取营业中断风险评估
                List<RiskReportCloBusiness> riskReportCloBusinessList = riskInsRiskReportVo.getRiskReportCloBusinessList();
                if (riskReportCloBusinessList != null) {
                    riskReportMain.setRiskReportCloBusinessList(riskReportCloBusinessList);
                }
            } else {
                if (riskInsGradeVo != null) {
                    if (riskInsRiskReportVo.getRiskReportMainVo() != null
                            && "0".equals(riskInsRiskReportVo.getRiskReportMainVo().getUnderwriteFlag())) {
                        riskInsRiskReportVo.getRiskReportMainVo().setHighlightRisk(riskInsGradeVo.getHighlightRisk());
                        riskInsRiskReportVo.getRiskReportMainVo().setScore(riskInsGradeVo.getScore());
                        riskInsRiskReportVo.getRiskReportMainVo().setUtiWeightId(riskInsGradeVo.getUtiWeightId());
                        // 增加风控建议赋值
                        if (riskInsGradeVo.getScore() != null) {
                            if (riskInsGradeVo.getScore().compareTo(new BigDecimal(40)) < 0) {
                                riskInsRiskReportVo.getRiskReportMainVo().setRiskSuggest("A");
                            } else if (riskInsGradeVo.getScore().compareTo(new BigDecimal(80)) > 0) {
                                riskInsRiskReportVo.getRiskReportMainVo().setRiskSuggest("C");
                            } else {
                                riskInsRiskReportVo.getRiskReportMainVo().setRiskSuggest("B");
                            }
                        }
                    }
                    List<RiskReportAssess> riskReportAssessList = new ArrayList<RiskReportAssess>();
                    riskReportAssessList.add(riskInsGradeVo.getRiskReportAssess());
                    riskInsRiskReportVo.setRiskReportAssessList(riskReportAssessList);
                }
                // 风控历史损失
                List<RiskReportClaim> riskReportClaimList = riskInsRiskReportVo.getRiskReportClaimList();
                if (riskReportClaimList != null) {
                    for (int i = 0; i < riskReportClaimList.size(); i++) {
                        RiskReportClaimId id = new RiskReportClaimId();
                        id.setSerialNo(i + 1);
                        riskReportClaimList.get(i).setId(id);
                    }
                    riskReportMain.setRiskReportClaimList(riskReportClaimList);
                }
                // 风控建筑
                List<RiskReportConstruct> riskReportConstructList = riskInsRiskReportVo.getRiskReportConstructList();
                if (riskReportConstructList != null) {
                    riskReportMain.setRiskReportConstructList(riskReportConstructList);
                }
                // 风控建筑详细信息
                List<RiskReportConstructInfo> riskReportConstructInfoList = riskInsRiskReportVo
                        .getRiskReportConstructInfoList();
                if (riskReportConstructInfoList != null) {
                    for (int i = 0; i < riskReportConstructInfoList.size(); i++) {
                        RiskReportConstructInfoId id = new RiskReportConstructInfoId();
                        id.setSerialNo(i + 1);
                        riskReportConstructInfoList.get(i).setId(id);
                    }
                    riskReportMain.setRiskReportConstructInfoList(riskReportConstructInfoList);
                }
                // 风控占用性质
                List<RiskReportOccupation> riskReportOccupationList = riskInsRiskReportVo.getRiskReportOccupationList();
                if (riskReportOccupationList != null) {
                    riskReportMain.setRiskReportOccupationList(riskReportOccupationList);
                }
                // 风控保护措施
                List<RiskReportProtection> riskReportProtectionList = riskInsRiskReportVo.getRiskReportProtectionList();
                if (riskReportProtectionList != null) {
                    riskReportMain.setRiskReportProtectionList(riskReportProtectionList);
                }
                // 风控环境
                List<RiskReportEnvironment> riskReportEnvironmentList = riskInsRiskReportVo
                        .getRiskReportEnvironmentList();
                if (riskReportEnvironmentList != null) {
                    riskReportMain.setRiskReportEnvironmentList(riskReportEnvironmentList);
                }
                // 风控附加扩展盗窃、抢劫责任
                List<RiskReportTheft> riskReportTheftList = riskInsRiskReportVo.getRiskReportTheftList();
                if (riskReportTheftList != null) {
                    riskReportMain.setRiskReportTheftList(riskReportTheftList);
                }
                // 风控附加扩展供应中断责任
                List<RiskReportInterrupt> riskReportInterruptList = riskInsRiskReportVo.getRiskReportInterruptList();
                if (riskReportInterruptList != null) {
                    riskReportMain.setRiskReportInterruptList(riskReportInterruptList);
                }
                // 风控风险值
                List<RiskReportAssess> riskReportAssesList = riskInsRiskReportVo.getRiskReportAssessList();
                if (riskReportAssesList != null) {
                    riskReportMain.setRiskReportAssessList(riskReportAssesList);
                }
                // 风控附加扩展露天堆放
                List<RiskReportAirStorage> riskReportAirStorageList = riskInsRiskReportVo.getRiskReportAirStorageList();
                if (riskReportAirStorageList != null) {
                    riskReportMain.setRiskReportAirStorageList(riskReportAirStorageList);
                }

                /*风控火灾风险排查专业版和简化版*/
                List<RiskReportFireDanger> riskReportFireDangerList = riskInsRiskReportVo
                        .getRiskReportFireDangerList();
                if (riskReportFireDangerList != null) {
                    riskReportMain.setRiskReportFireDangerList(riskReportFireDangerList);
                }

                String ip = "";

                InetAddress address = InetAddress.getLocalHost();
                ip = address.getHostAddress();

                ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
                String saveRootPath = bundle.getString("saveRootPath");
                String saveTypePath = bundle.getString("saveTypePath");
                String savePort = bundle.getString("savePort");
                String savePath = protocol + "://" + ip + ":" + savePort;

                // 如果是移动端,将图片保存到服务中，再讲图片信息封装到RiskDcodeList中
                if (userInfo.getIsPC() && userInfo.getIsOuterSystem()) {
                    // 移动端照片日志表
                    // ImageLog log = new ImageLog();
                    // 移动端照片资料表
                    List<RiskReportPicture> riskReportPictures = new ArrayList<RiskReportPicture>(0);
                    Map<String, List<CommonsMultipartFile>> mapFiles = null;

                    if (fileLists != null && fileLists.size() > 0) {
                        mapFiles = new HashMap<String, List<CommonsMultipartFile>>();
                        Map<String, RiskDcode> map = new HashMap<String, RiskDcode>();
                        if ("001".equals(riskModel) || "002".equals(riskModel)) {
                            map = dataSourcesService.queryAllImageCategory(
                                    "riskReportTree" + riskInsRiskReportVo.getRiskReportMainVo().getRiskModel());
                        } else if ("006".equals(riskModel) || "007".equals(riskModel)) {
                            /*火灾风险排查*/
                            String riskModelFlag = "006".equals(riskModel.trim()) ? "001" : "002";
                            map = dataSourcesService.queryAllImageCategory(
                                    "riskReportFireTree" + riskModelFlag);
                        }


                        for (CommonsMultipartFile fileTem : fileLists) {

                            // 照片名
                            String fileName = fileTem.getOriginalFilename();
                            // 照片名无后缀
                            // String fileNameNo = fileName.substring(0, fileName.lastIndexOf('.'));
                            String fileNameNo = fileName;
                            // 照片目录
                            String childPath = fileNameNo.substring(0, fileName.lastIndexOf('.'));

                            childPath = childPath + "#" + map.get(childPath).getCodeCname();

                            if (mapFiles.containsKey(childPath)) {
                                List<CommonsMultipartFile> fileListTem = mapFiles.get(childPath);
                                fileListTem.add(fileTem);
                            } else {
                                List<CommonsMultipartFile> fileListTem = new ArrayList<CommonsMultipartFile>();
                                fileListTem.add(fileTem);
                                mapFiles.put(childPath, fileListTem);
                            }

                        }

                    }

                    if (mapFiles != null) {
                        Iterator<String> iterator = mapFiles.keySet().iterator();
                        String childPath;
                        String childName;
                        String pathString = saveRootPath + saveTypePath + "/" + riskFileNo + "/";

                        // 先删除该风控编号对应的文件夹
                        ftp.removeDirectoryALLFile(riskFileNo);
                        while (iterator.hasNext()) {

                            String key = iterator.next();
                            childPath = key.split("#")[0];
                            childName = key.split("#")[1];
                            List<CommonsMultipartFile> files = (List<CommonsMultipartFile>) mapFiles.get(key);

                            if (files != null && files.size() > 0) {

                                for (CommonsMultipartFile file : files) {

                                    // 照片名
                                    String fileName = file.getOriginalFilename();
                                    // 照片名无后缀
                                    // String fileNameNo = fileName.substring(0, fileName.lastIndexOf('.'));
                                    String fileNameNo = fileName;

                                    // 保存文件到服务
                                    String imageName = riskInsImageService.saveImage(file, riskFileNo + "/" + childPath,
                                            riskFileNo, childPath,
                                            fileNameNo.substring(fileNameNo.lastIndexOf('.') + 1), ftp);

                                    String url = saveTypePath + "/" + riskFileNo + "/" + childPath + "/" + imageName
                                            + "?uuid=" + UUID.randomUUID().toString();

                                    String remark = file.getFileItem().getName();

                                    if (remark.indexOf("\\") != -1) {
                                        String[] arr = remark.split("\\\\");
                                        remark = arr[arr.length - 1];
                                    }

                                    int index = remark.indexOf(".");
                                    if (index != -1) {
                                        remark = remark.substring(0, index);
                                    }

                                    // 封装影像资料
                                    RiskReportPicture riskReportPicture = new RiskReportPicture();
                                    RiskReportPictureId riskReportPictureId = new RiskReportPictureId();
                                    riskReportPictureId.setChildPath(childPath);
                                    riskReportPictureId.setImageName(imageName);
                                    riskReportPictureId.setRiskFileNo(riskFileNo);
                                    riskReportPicture.setImageID("");
                                    riskReportPicture.setImageUrl(url);
                                    riskReportPicture.setRemark(remark);
                                    riskReportPicture.setUserCode(userInfo.getUserCode());
                                    riskReportPicture.setId(riskReportPictureId);
                                    riskReportPictures.add(riskReportPicture);
                                }

                            }

                        }
                    }

                    // 保存日志和图片资料
                    riskReportMain.setRiskReportPictureList(riskReportPictures);
                } else {
                    if (riskInsRiskReportVo.getRiskReportPictureList() != null
                            && riskInsRiskReportVo.getRiskReportPictureList().size() != 0) {
                        riskReportMain.setRiskReportPictureList(riskInsRiskReportVo.getRiskReportPictureList());
                    }
                }
            }
            // 为大对象的某特定字段赋值
            setValueforSpecificField(riskReportMain, "RiskFileNo",
                    riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo());
//            PojoMerge pm = new PojoMerge(databaseDao);
//            pm.mergeObject(riskReportMain, source, true);
            riskReportMainRepository.save(riskReportMain);

            if (userInfo.getIsPC() && userInfo.getIsOuterSystem()) {
//                databaseDao.save(RiskReportMain.class, riskReportMain);
                riskReportMainRepository.save(riskReportMain);
            }

//            ajaxResult.setStatus(1);
            //apiResponse中状态为0 是成功，其他状态是失败
            apiResponse.setStatus(0);
            String underwriteFlag = riskInsRiskReportVo.getRiskReportMainVo().getUnderwriteFlag();
            if (StringUtils.isNotBlank(underwriteFlag) && "0".equals(underwriteFlag.trim())) {
                riskTimeService.insertRiskTime(riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo(),
                        riskInsRiskReportVo.getRiskReportMainVo().getOperatorCode(), CommonConst.RC_SAVE);
            }

        } catch (Exception e) {
            LOGGER.info("风控报告保存方法异常：" + e.getMessage(), e);
            e.printStackTrace();
//            ajaxResult.setStatus(0);
//            ajaxResult.setStatusText(e.getMessage());
            apiResponse.setStatus(1);
            apiResponse.setStatusText(e.getMessage());
            // throw new RuntimeException("风控报告保存方法异常:"+e);
        } finally {
            if (ftp != null) {
                try {
                    ftp.close();
                } catch (IOException e) {
                    LOGGER.info("关闭ftp异常：" + e.getMessage(), e);
                }
            }
        }
        return apiResponse;
    }

    /**
     * @param RiskInsRiskReportVo 打分大对象
     * @throws Exception
     * @功能：打分功能实现
     * @author 梁尚
     * @时间：2017-11-20
     * @修改记录：modify by liqiankun 20200121
     */
    public RiskInsGradeVo grade(RiskInsRiskReportVo riskInsRiskReportVo) throws Exception {
        RiskInsGradeVo riskInsGradeVo = new RiskInsGradeVo();
        try {
            // 设置factorMap集合，用于存储factor分子
            Map<String, List<UtiFactor>> factorFMap = new HashMap<String, List<UtiFactor>>();
            // 设置factorMap集合，用于存储factor原子(里面存储为map，方便查找计算因子时快速找出对应因子数据)
            Map<String, Map<String, UtiFactor>> factorYMap = new HashMap<String, Map<String, UtiFactor>>();
            // 设置objectMap集合，用于存储反射出的对象的字段值
            Map<String, String> objectMap = new HashMap<String, String>();
            // 设置scoreMap集合，用于存储各灾因分数
            Map<String, BigDecimal> scoreMap = new HashMap<String, BigDecimal>();
            try {
                objectMap = getObjectColum(riskInsRiskReportVo);
            } catch (Exception e) {
                LOGGER.info("反射信息异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            // 设置返回对象
            // RiskInsGradeVo riskInsGradeVo = gradeValidate(objectMap,
            // riskInsRiskReportVo.getRiskReportMainVo().getRiskModel());
            // // 校验未通过
            // if (riskInsGradeVo.getFlag().equals("0")) {
            // return riskInsGradeVo;
            // }
//            String lon = riskInsRiskReportVo.getRiskReportMainVo().getPointx_2000();
//            String lat = riskInsRiskReportVo.getRiskReportMainVo().getPointy_2000();
            String lon = riskInsRiskReportVo.getRiskReportMainVo().getPointx2000() + "";
            String lat = riskInsRiskReportVo.getRiskReportMainVo().getPointy2000() + "";
            String[] str = this.queryDenger(lon, lat);
            String riskModel = riskInsRiskReportVo.getRiskReportMainVo().getRiskModel();
            String[] dangerType = RiskControlConst.TYPE_SUM.split(",");

            // 分子、原子表缓存处理
            for (int i = 0; i < dangerType.length; i++) {
                // 分子集合
                Criteria<UtiFactor> criteriaUtiFactor = new Criteria<>();
                criteriaUtiFactor.add(Restrictions.eq("id.riskModel", riskInsRiskReportVo.getRiskReportMainVo().getRiskModel()));
                criteriaUtiFactor.add(Restrictions.eq("id.dangerType", dangerType[i]));
                criteriaUtiFactor.add(Restrictions.eq("validStatus", "1"));
                criteriaUtiFactor.add(Restrictions.in("factorType", Arrays.asList("01", "02"), true));
                List<UtiFactor> resultF = utiFactorDao.findAll(criteriaUtiFactor);
                factorFMap.put(dangerType[i], resultF);
                // 原子集合
                Criteria<UtiFactor> criteriaUtiFactorY = new Criteria<>();
                criteriaUtiFactorY.add(Restrictions.eq("id.riskModel", riskInsRiskReportVo.getRiskReportMainVo().getRiskModel()));
                criteriaUtiFactorY.add(Restrictions.eq("id.dangerType", dangerType[i]));
                criteriaUtiFactorY.add(Restrictions.eq("validStatus", "1"));
                criteriaUtiFactorY.add(Restrictions.in("factorType", Arrays.asList("03", "04"), true));
                List<UtiFactor> resultY = utiFactorDao.findAll(criteriaUtiFactorY);

                Map<String, UtiFactor> factorYColumMap = new HashMap<String, UtiFactor>();
                for (UtiFactor riskReportUtiFactor : resultY) {
                    factorYColumMap.put(riskReportUtiFactor.getId().getFactorNo(), riskReportUtiFactor);
                }
                factorYMap.put(dangerType[i], factorYColumMap);
            }

            // 分值表缓存处理
            Map<String, UtiScore> utiScoreMap = new HashMap<String, UtiScore>();
            List<UtiScore> scoreList = new ArrayList<UtiScore>();
            Criteria<UtiScore> criteriaUtiScore = new Criteria<>();
            criteriaUtiScore.add(Restrictions.eq("validStatus", "1"));
            scoreList = utiScoreDao.findAll(criteriaUtiScore);
            for (UtiScore score : scoreList) {
                UtiScoreId id = score.getId();
                utiScoreMap.put(id.getRiskModel().trim() + "_" + id.getFactorNo().trim() + "_"
                        + id.getDangerType().trim() + "_" + id.getFactorValue().trim(), score);
            }

            // 计算表缓存处理
            Map<String, UtiFormula> UtiFormulaMap = new HashMap<String, UtiFormula>();
            Criteria<UtiFormula> criteriaUtiFormula = new Criteria<>();
            criteriaUtiFormula.add(Restrictions.eq("validStatus", "1"));
            List<UtiFormula> utiFormulaList = utiFormulaDao.findAll(criteriaUtiFormula);
            for (UtiFormula utiFormula : utiFormulaList) {
                UtiFormulaId id = utiFormula.getId();
                UtiFormulaMap.put(
                        id.getRiskModel().trim() + "_" + id.getFactorNo().trim() + "_" + id.getDangerType().trim(),
                        utiFormula);
            }

            // 权重表缓存处理
            String comCode = riskInsRiskReportVo.getRiskReportMainVo().getComCode();
            List<UtiWeight> utiWeightList = new ArrayList<UtiWeight>();
//                QueryRule queryRule = QueryRule.getInstance();
            Criteria<UtiWeight> criteriaUtiWeight = new Criteria<>();

            List<String> comCodeList = new ArrayList<String>();
            comCodeList.add("00000000");
            if (!"00000000".equals(comCode)) {
                comCodeList.add(comCode.substring(0, 2) + "000000");
            }
            criteriaUtiWeight.add(Restrictions.in("comCode", comCodeList, true));

            if (StringUtils.isNotBlank(riskModel) && "006,007".indexOf(riskModel.trim()) > -1) {
                /*这个是后面新增的006,007的权重取值*/
                criteriaUtiWeight.add(Restrictions.eq("riskModel", riskModel));
            } else {
                /*这个是风控报告打分时候的权重*/
                criteriaUtiWeight.add(Restrictions.isnull("riskModel"));
            }
            criteriaUtiWeight.add(Restrictions.eq("validStatus", "1"));
            utiWeightList = utiWeightDao.findAll(criteriaUtiWeight);

            // 突出风险缓存处理
            List<UtiHighlightRisk> highlightRiskList = new ArrayList<UtiHighlightRisk>();
//                QueryRule queryRuleHighlightRisk = QueryRule.getInstance();
//                queryRuleHighlightRisk.addEqual("id.riskModel",
//                        riskInsRiskReportVo.getRiskReportMainVo().getRiskModel());
//                queryRuleHighlightRisk.addEqual("validStatus", "1");
//                highlightRiskList = databaseDao.findAll(UtiHighlightRisk.class, queryRuleHighlightRisk);
            Criteria<UtiHighlightRisk> criteriaUtiHighlightRisk = new Criteria<>();
            criteriaUtiHighlightRisk.add(Restrictions.eq("id.riskModel",
                    riskInsRiskReportVo.getRiskReportMainVo().getRiskModel()));
            criteriaUtiHighlightRisk.add(Restrictions.eq("validStatus", "1"));
            highlightRiskList = utiHighlightRiskDao.findAll(criteriaUtiHighlightRisk);

            // 开始计算分值
            for (String key : factorFMap.keySet()) {
                List<UtiFactor> list = factorFMap.get(key);
                scoreMap.put(key, BigDecimal.ZERO);
                for (UtiFactor riskReportUtiFactor : list) {
                    // 直接因子
                    if ("01".equals(riskReportUtiFactor.getFactorType())) {
                        String value = objectMap
                                .get(riskReportUtiFactor.getFromTable() + "." + riskReportUtiFactor.getFromColumn());
                        BigDecimal score = getScoreByValue(riskReportUtiFactor, value, utiScoreMap);
                        // 将校验之后的分值存入分值集合中
                        scoreMap.put(key, scoreMap.get(key).add(validateExt(riskReportUtiFactor, score)));
                        // 间接因子
                    } else if ("02".equals(riskReportUtiFactor.getFactorType())) {
                        UtiFormula utiFormula = UtiFormulaMap.get(riskReportUtiFactor.getId().getRiskModel().trim()
                                + "_" + riskReportUtiFactor.getId().getFactorNo().trim() + "_" + key.trim());
                        BigDecimal score = BigDecimal.ZERO;
                        if (utiFormula != null) {
                            score = calculateByFactor(objectMap, key, factorYMap, utiFormula, utiScoreMap, str);
                        }
                        // 将校验之后的分值存入分值集合中
                        scoreMap.put(key, scoreMap.get(key).add(validateExt(riskReportUtiFactor, score)));
                    }

                }
                scoreMap.put(key, scoreMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            // 权重表字段名数组
            String[] weightArray = {"fireWeight", "waterWeight", "windWeight", "thunderWeight", "snowWeight",
                    "theftWeight", "earthquakeWeight", "geologyWeight"};
            // 风险值表字段名数组
            String[] assesArray = {"fireDanger", "waterDanger", "windDanger", "thunderDanger", "snowDanger",
                    "theftDanger", "earthquakeDanger", "geologyDanger"};

            // 风险值表赋值
            RiskReportAssess riskReportAssess = (RiskReportAssess) setValueBycolum(new RiskReportAssess(), scoreMap,
                    dangerType, assesArray, riskModel);
            riskInsGradeVo.setRiskReportAssess(riskReportAssess);
            // 总分计算
            if (utiWeightList != null && utiWeightList.size() > 0) {
                UtiWeight realWeight = new UtiWeight();
                // 存在两条数据取非全国数据
                if (utiWeightList.size() > 1) {
                    for (UtiWeight weight : utiWeightList) {
                        if (!"00000000".equals(weight.getComCode())) {
                            realWeight = weight;
                            break;
                        }
                    }
                } else {
                    realWeight = utiWeightList.get(0);
                }
                BigDecimal score = setValueByWeight(realWeight, scoreMap, dangerType, weightArray);
                riskInsGradeVo.setUtiWeightId(realWeight.getId());
                riskInsGradeVo.setScore(score);
            }

            // 突出风险处理
            StringBuffer highlightRisk = new StringBuffer();
            int count = 0;
            for (UtiHighlightRisk riskReportHighlightRisk : highlightRiskList) {
                String key = riskReportHighlightRisk.getId().getFromTable() + "."
                        + riskReportHighlightRisk.getId().getFromColumn();
                if (objectMap.containsKey(key)) {
                    // 突出风险没有存在
                    if (highlightRisk.indexOf(riskReportHighlightRisk.getRiskReminder()) == -1) {
                        // RiskReportHighlightRisk表里的riskValue取值为风险取值
                        if ("1".equals(riskReportHighlightRisk.getRiskFlag())) {
                            if (objectMap.get(key).indexOf(riskReportHighlightRisk.getId().getRiskValue()) > -1) {
                                count++;
                                highlightRisk.append(count + "〝 " + riskReportHighlightRisk.getRiskReminder() + "\n");
                            }
                            // RiskReportHighlightRisk表里的riskValue取值并不为风险取值
                        } else {
                            if (objectMap.get(key).indexOf(riskReportHighlightRisk.getId().getRiskValue()) == -1) {
                                count++;
                                highlightRisk.append(count + "〝 " + riskReportHighlightRisk.getRiskReminder() + "\n");
                            }
                        }
                    }
                }
            }
            riskInsGradeVo.setHighlightRisk(highlightRisk.toString());
        } catch (Exception e) {
            LOGGER.info("打分功能异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("打分功能异常:" + e);
        }
        return riskInsGradeVo;
    }

    /**
     * @param obj        权重对象
     * @param map        各风险分值map集合
     * @param dangerType 风险key值
     * @param array      对应字段名
     * @throws Exception
     * @功能：根据权重以及各风险分值计算总分
     * @author 梁尚
     * @时间：2017-11-20
     * @修改记录： modifyby liqiankun 20200121
     */
    private BigDecimal setValueByWeight(Object obj, Map map, String[] dangerType, String[] array) throws Exception {
        BigDecimal result = BigDecimal.ZERO;
        try {
            if (dangerType.length == array.length) {
                Class objectClass = obj.getClass();
                Field[] fields = objectClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    for (int i = 0; i < array.length; i++) {
                        String fieldName = field.getName().substring(field.getName().lastIndexOf('.') + 1);
                        if (fieldName.equals(array[i])) {
                            result = result.add(new BigDecimal(field.get(obj).toString())
                                    .multiply(new BigDecimal(map.get(dangerType[i]).toString())));
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("根据权重以及各风险分值计算总分异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("根据权重以及各风险分值计算总分异常:" + e);
        }
        return result.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @param obj        风险值表对象
     * @param map        各风险分值map集合
     * @param dangerType 风险key值
     * @param array      对应字段名
     * @throws Exception
     * @功能：风险值表赋值
     * @author 梁尚
     * @时间：2017-11-20
     * @修改记录：modifyby liqiankun 20200121
     */
    private Object setValueBycolum(Object obj, Map map, String[] dangerType, String[] array, String riskModel) throws Exception {
        List<String> riskAssess = new ArrayList<String>();
        if ("006".equals(riskModel)) {
            riskAssess.addAll(Arrays.asList("12.20", "32.93", "28.05", "9.75", "17.07", "1", "1", "1"));
        } else if ("007".equals(riskModel)) {
            riskAssess.addAll(Arrays.asList("10.0", "26.67", "30.0", "10", "23.33", "1", "1", "1"));
        }
        try {
            if (dangerType.length == array.length) {
                Class objectClass = obj.getClass();
                Field[] fields = objectClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    for (int i = 0; i < array.length; i++) {
                        String fieldName = field.getName().substring(field.getName().lastIndexOf('.') + 1);
                        if (fieldName.equals(array[i])) {
                            if ("006,007".indexOf(riskModel) > -1) {
                                Object score = map.get(dangerType[i]);
                                if (null != score) {
                                    BigDecimal bigDecimal = new BigDecimal(score.toString()).divide(new BigDecimal(riskAssess.get(i)),
                                            3, RoundingMode.HALF_UP).multiply(new BigDecimal("10"));
                                    field.set(obj, bigDecimal);
                                }
                            } else {
                                field.set(obj, map.get(dangerType[i]));
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("风险值表赋值异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("风险值表赋值异常:" + e);
        }
        return obj;
    }

    /**
     * @param objectClass 要反射的对象
     * @param objectMap   存入的map集合
     * @throws Exception
     * @功能：利用反射将对象里的值存入map集合中
     * @author 梁尚 @时间：2017-11-20
     * @修改记录：modifyby liqiankun 20200121
     */
    private Map<String, String> getObjectColum(Object object) throws Exception {
        Map<String, String> objectMap = new HashMap<String, String>();
        try {
            Class objectClass = object.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            String objectClassName = objectClass.toString().substring(objectClass.toString().lastIndexOf('.') + 1);
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(object) != null) {
                    if (field.getType().equals(List.class) && objectClass != RiskReportMain.class) {
                        for (Object obj : (List) field.get(object)) {
                            if (obj != null) {
                                Map<String, String> objectMapTemp = new HashMap<String, String>();
                                objectMapTemp = getObjectColum(obj);
                                for (String key : objectMapTemp.keySet()) {
                                    if (!objectMap.containsKey(key)) {
                                        objectMap.put(key, objectMapTemp.get(key));
                                    } else {
                                        objectMap.put(key, objectMap.get(key) + ";" + objectMapTemp.get(key));
                                    }
                                }
                            }
                        }
                    } else if (field.getType().equals(RiskReportMain.class)) {
                        Map<String, String> objectMapTemp = new HashMap<String, String>();
                        objectMapTemp = getObjectColum(field.get(object));
                        for (String key : objectMapTemp.keySet()) {
                            if (!objectMap.containsKey(key)) {
                                objectMap.put(key, objectMapTemp.get(key));
                            }
                        }

                    } else {
                        if (field.get(object) != null) {
                            String fieldValue = "";
                            // 资产占比单独处理
                            if ("RiskReportConstructInfo".equals(objectClassName)
                                    && "assetsRatio".equals(field.getName())) {
                                fieldValue = new BigDecimal(field.get(object).toString()).divide(new BigDecimal(100))
                                        .toString();
                            } else {
                                fieldValue = field.get(object).toString();
                            }
                            // 设置对象值
                            if (!objectMap.containsKey(objectClass + "." + field.getName())) {
                                objectMap.put(objectClassName + "." + field.getName(), fieldValue);
                            } else {
                                objectMap.put(objectClassName + "." + field.getName(),
                                        objectMap.get(objectClassName + "." + field.getName()) + ";" + fieldValue);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("对象里的值存入map集合中异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("对象里的值存入map集合中异常:" + e);
        }
        return objectMap;
    }

    /**
     * @throws Exception
     * @功能：根据经纬度坐标获取灾因值
     * @author
     * @修改记录：modifyby liqiankun 20200121
     */
    public String[] queryDenger(String lon, String lat) {
        // 获取iserver的地址
        ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
        String serverName = bundle.getString("serverName");

        Connection conn = null;
        PreparedStatement stat = null;
        // ResultSet rs = null;
        String[] rasterDangerArray = {"rain_hazard_scale_1km", "thunderstorm_hazard_scale_1km",
                "snowstorm_hazard_scale_1km", "hail_hazard_scale_1km", "flood_hazard_scale_1km",
                "typhoon_hazard_scale_1km", "eq_hazard_PGA", "landslide_hazard_scale_1km"};

        String[] valueArray = new String[8];
        for (int i = 0; i < rasterDangerArray.length; i++) {
            String url = serverName + "iserver/services/data-FXDT/rest/data/datasources/china/datasets/"
                    + rasterDangerArray[i] + "/gridValue.json";
            String param = "x=" + lon + "&y=" + lat;
            String resJson = doGet(url, param);
            // System.out.println("请求的url:"+url+"?"+param);
            GridValue gridValue = new GridValue();
            if (StringUtils.isNotBlank(resJson)) {
                gridValue = JSON.parseObject(resJson.toString(), GridValue.class);
                BigDecimal gridValueBd = gridValue.getValue();
                // 假如值小于0，则置为0,保留两位小数
                if (gridValueBd.compareTo(new BigDecimal(0)) == -1) {
                    gridValueBd = BigDecimal.ZERO;
                }
                valueArray[i] = gridValueBd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            } else {
                System.out.println("请求的url:" + url + "?" + param);
                valueArray[i] = BigDecimal.ZERO.toString();
            }
        }
        return valueArray;
    }

    /**
     * 执行一个带参数的HTTP GET请求，返回请求响应的JSON字符串
     *
     * @param url 请求的URL地址
     * @return 返回请求响应的JSON字符串
     */
    public static String doGet(String url, String param) {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url + "?" + param);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                return StreamUtils.copyToString(method.getResponseBodyAsStream(), Charset.forName("utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("执行HTTP Get请求" + url + "时，发生异常！", e);
            throw new RuntimeException("执行HTTP Get请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return "";
    }

    /**
     * @param riskReportUtiFactor 因子
     * @param value               因子值
     * @return BigDecimal 分数
     * @功能：根据utiScore表将因子的值转成分数
     * @author 梁尚
     * @时间：2017-11-20
     * @修改记录：modifyby liqiankun 20200121
     */
    private BigDecimal getScoreByValue(UtiFactor riskReportUtiFactor, String value, Map<String, UtiScore> utiScoreMap) {
        BigDecimal score = BigDecimal.ZERO;
        try {
            if (value != null) {
                // 单选
                if ("01".equals(riskReportUtiFactor.getColumnType())) {
                    UtiFactorId id = riskReportUtiFactor.getId();
                    UtiScore utiScore = utiScoreMap.get(id.getRiskModel().trim() + "_" + id.getFactorNo().trim() + "_"
                            + id.getDangerType().trim() + "_" + value.trim());
                    if (utiScore != null) {
                        score = utiScore.getFactorScore();
                    }
                    // 复选
                } else if ("02".equals(riskReportUtiFactor.getColumnType())) {
                    value = value.replace("[", "").replace("]", "").replace("\"", "");
                    String[] arrayValue = value.split(",");
                    for (String s : arrayValue) {
                        UtiFactorId id = riskReportUtiFactor.getId();
                        UtiScore utiScore = utiScoreMap.get(id.getRiskModel().trim() + "_" + id.getFactorNo().trim()
                                + "_" + id.getDangerType().trim() + "_" + s.trim());
                        BigDecimal scoreSingle = BigDecimal.ZERO;
                        if (utiScore != null) {
                            scoreSingle = utiScore.getFactorScore();
                        }
                        score = score.add(scoreSingle);
                    }
                }
            }
            // 兼兼职版出险原因处理（不存在某项时则有分,分值表配置的分值为此分）
            if ("04".equals(riskReportUtiFactor.getColumnType())) {
                // 缓存处理
                List<UtiScore> scoreList = new ArrayList<UtiScore>();
                List<UtiHighlightRisk> highlightRiskList = new ArrayList<UtiHighlightRisk>();
//                    QueryRule queryRule = QueryRule.getInstance();
//                    queryRule.addEqual("id.riskModel", riskReportUtiFactor.getId().getRiskModel());
//                    queryRule.addEqual("id.factorNo", riskReportUtiFactor.getId().getFactorNo());
//                    queryRule.addEqual("id.dangerType", riskReportUtiFactor.getId().getDangerType());
//                    queryRule.addEqual("validStatus", "1");
//                    scoreList = databaseDao.findAll(UtiScore.class, queryRule);
                Criteria<UtiScore> criteriaUtiScore = new Criteria<>();
                criteriaUtiScore.add(Restrictions.eq("id.riskModel", riskReportUtiFactor.getId().getRiskModel()));
                criteriaUtiScore.add(Restrictions.eq("id.factorNo", riskReportUtiFactor.getId().getFactorNo()));
                criteriaUtiScore.add(Restrictions.eq("id.dangerType", riskReportUtiFactor.getId().getDangerType()));
                criteriaUtiScore.add(Restrictions.eq("validStatus", "1"));
                scoreList = utiScoreDao.findAll(criteriaUtiScore);

                if (scoreList != null && scoreList.size() > 0) {
                    if (value == null || value.indexOf(scoreList.get(0).getId().getFactorValue()) == -1) {
                        score = scoreList.get(0).getFactorScore();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("根据utiScore表将因子的值转成分数异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("根据utiScore表将因子的值转成分数异常:" + e);
        }
        return score;
    }

    /**
     * @param riskReportUtiFactor 因子对象
     * @param score               计算得到的分值
     * @return BigDecimal 最终的分值
     * @功能：校验最值
     * @author 梁尚 @时间：2017-11-20
     * @修改记录：modifyby liqiankun 20200121
     */
    private BigDecimal validateExt(UtiFactor riskReportUtiFactor, BigDecimal score) {
        BigDecimal result = BigDecimal.ZERO;
        try {
            if (("01".equals(riskReportUtiFactor.getFactorExtType())
                    && score.compareTo(riskReportUtiFactor.getFactorExt()) > 0)
                    || ("02".equals(riskReportUtiFactor.getFactorExtType())
                    && score.compareTo(riskReportUtiFactor.getFactorExt()) < 0)) {
                result = riskReportUtiFactor.getFactorExt();
            } else {
                result = score;
            }
        } catch (Exception e) {
            LOGGER.info("校验最值异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("校验最值异常:" + e);
        }
        return result;

    }

    /**
     * @param objectMap  对象值集合
     * @param key        灾因
     * @param factorYMap 原子map
     * @param utiFormula 计算公式
     * @return BigDecimal 分数
     * @throws Exception
     * @功能：间接因子计算
     * @author 梁尚
     * @时间：2017-11-20
     * @修改记录：modifyby liqiankun 20200121
     */
    private BigDecimal calculateByFactor(Map<String, String> objectMap, String key,
                                         Map<String, Map<String, UtiFactor>> factorYMap, UtiFormula utiFormula, Map<String, UtiScore> utiScoreMap, String[] str)
            throws Exception {

        BigDecimal sumScore;
        try {
            // 设置存储计算因子的map
            Map<String, String> factorMapTemp = emulativeFormula(utiFormula.getContent());
            Map<String, BigDecimal> factorCodeMap = new HashMap<String, BigDecimal>();
            // 删除掉重复因子
            for (String factorCode : factorMapTemp.keySet()) {
                factorCodeMap.put(factorCode, BigDecimal.ZERO);
            }
            // 用于多数据值的存储
            Map<String, List<BigDecimal>> stringMap = new HashMap<String, List<BigDecimal>>();

            sumScore = BigDecimal.ZERO;
            // 非多数据处理
            if ("00".equals(utiFormula.getListType())) {
                String content = "";
                // 将因子分数存入map中
                for (String factorCodeKey : factorCodeMap.keySet()) {
                    if (factorYMap.get(key).containsKey(factorCodeKey)) {
                        UtiFactor factorY = factorYMap.get(key).get(factorCodeKey);
                        String factorYValue = objectMap.get(factorY.getFromTable() + "." + factorY.getFromColumn());
                        if (factorYValue != null) {
                            // 直接原子（具有分值）
                            if ("03".equals(factorY.getFactorType())) {
                                BigDecimal score = getScoreByValue(factorY, factorYValue, utiScoreMap);
                                factorCodeMap.put(factorCodeKey, score);
                                // 间接原子（没有分值）
                            } else if ("04".equals(factorY.getFactorType())) {
                                //  将原子值作为分值
                                factorCodeMap.put(factorCodeKey, new BigDecimal(factorYValue));
                            }
                        }
                    }
                    // 用因子分值替换因子
                    content = utiFormula.getContent().replaceAll(factorCodeKey,
                            factorCodeMap.get(factorCodeKey).toString());
                }
                sumScore = getResultByFormula(content);
            } else if ("05".equals(utiFormula.getListType())) {
                System.out.println("===========================begin==in==================================");
                String content = "";
                // 用因孝分值替杢因孝
                content = utiFormula.getContent();
                DisastersScore DS = new DisastersScore();
                Class DSClass = DS.getClass();
                Method[] methods = DSClass.getMethods();
                for (int i = 0; i < methods.length; i++) {
                    if (content.equals(methods[i].getName())) {
                        sumScore = (BigDecimal) methods[i].invoke(DSClass.newInstance(), str, utiFormula);
                        System.out.println("===========================begin====================================" + sumScore);
                    }
                }
            } else if ("06".equals(utiFormula.getListType())) {
                System.out.println("===========================begin==in===listtype06===============================");
                String content = "";
                // 用因子分值替换因子
                content = utiFormula.getContent();
                DisastersScore DS = new DisastersScore();
                Class DSClass = DS.getClass();
                Method[] methods = DSClass.getMethods();
                for (int i = 0; i < methods.length; i++) {
                    if (content.equals(methods[i].getName())) {

                        sumScore = (BigDecimal) methods[i].invoke(DSClass.newInstance(), objectMap, utiFormula);
                        System.out.println("===========================begin===listtype06=================================" + sumScore);
                    }
                }

            }
            // 多数据间处理
            else {
                // 将因子分数存入map中
                for (String factorCodeKey : factorCodeMap.keySet()) {
                    List<BigDecimal> factorScoreList = new ArrayList<BigDecimal>();
                    if (factorYMap.get(key).containsKey(factorCodeKey)) {
                        UtiFactor factorY = factorYMap.get(key).get(factorCodeKey);
                        String factorYValue = objectMap.get(factorY.getFromTable() + "." + factorY.getFromColumn());
                        if (factorYValue != null) {
                            String[] valueArray = factorYValue.split(";");
                            for (String value : valueArray) {
                                // 直接原子（具有分值）
                                if ("03".equals(factorY.getFactorType())) {
                                    BigDecimal score = getScoreByValue(factorY, value, utiScoreMap);
                                    factorScoreList.add(score);
                                    // 间接原孝（没有分值）
                                } else if ("04".equals(factorY.getFactorType())) {
                                    // 将原孝值作为分值
                                    factorScoreList.add(new BigDecimal(value));
                                }
                            }
                        }
                    }
                    stringMap.put(factorCodeKey, factorScoreList);

                }
                // 计算公式Map
                Map<Integer, String> contentMap = new HashMap<Integer, String>();
                for (String contentKey : stringMap.keySet()) {
                    List<BigDecimal> scoreList = stringMap.get(contentKey);
                    for (int i = 0; i < scoreList.size(); i++) {
                        String content = "";
                        if (contentMap.containsKey(i)) {
                            content = contentMap.get(i).replaceAll(contentKey, scoreList.get(i).toString());
                            ;
                        } else {
                            content = utiFormula.getContent().replaceAll(contentKey, scoreList.get(i).toString());
                        }
                        contentMap.put(i, content);
                    }
                }
                // 最小值处理计数器
                int count = 0;
                for (Integer i : contentMap.keySet()) {
                    // 多数据相加
                    if ("01,04".indexOf(utiFormula.getListType()) > -1) {
                        sumScore = sumScore.add(getResultByFormula(contentMap.get(i)));
                        // 多数杮坖最大值
                    } else if ("02".equals(utiFormula.getListType())) {
                        BigDecimal score = getResultByFormula(contentMap.get(i));
                        if (score.compareTo(sumScore) > 0) {
                            sumScore = score;
                        }
                        // 多数杮坖最尝值
                    } else if ("03".equals(utiFormula.getListType())) {
                        if (count == 0) {
                            sumScore = getResultByFormula(contentMap.get(i));
                        } else {
                            BigDecimal score = getResultByFormula(contentMap.get(i));
                            if (score.compareTo(sumScore) < 0) {
                                sumScore = score;
                            }
                        }
                        count++;
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.info("间接因孝计算异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("间接因孝计算异常:" + e);
        }
        return sumScore;
    }

    /**
     * @param riskReportUtiFactor 因孝
     * @param value               因孝值
     * @return BigDecimal 分数
     * @throws Exception
     * @功能：将字符串计算公弝计算出结果
     * @author 條尚
     * @时间：2017-11-20
     * @修改记录：modifyby liqiankun 20200121
     */
    private BigDecimal getResultByFormula(String formula) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        BigDecimal result = BigDecimal.ZERO;
        result = new BigDecimal(engine.eval(formula).toString()).setScale(4, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * @param riskReportUtiFactor 因孝
     * @param value               因孝值
     * @return BigDecimal 分数
     * @功能：根杮公弝获坖所需因孝
     * @author 條尚
     * @时间：2017-11-20
     * @修改记录：modifyby liqiankun 20200121
     */
    private Map<String, String> emulativeFormula(String formulaContent) {
        Map<String, String> factorMap = new HashMap<String, String>();
        try {
            int lgn = formulaContent.length();
            StringBuilder s = null;
            for (int i = 0; i < lgn; i++) {
                s = new StringBuilder(String.valueOf(formulaContent.charAt(i)));
                if ("P".equals(s.toString())) {
                    String factor = s.append(formulaContent.charAt(i + 1)).append(formulaContent.charAt(i + 2))
                            .append(formulaContent.charAt(i + 3)).append(formulaContent.charAt(i + 4))
                            .append(formulaContent.charAt(i + 5)).toString();
                    factorMap.put(factor, "");
                }
                s = null;
            }
        } catch (Exception e) {
            LOGGER.info("根杮公弝获坖所需因孝异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("根杮公弝获坖所需因孝异常:" + e);
        }
        return factorMap;
    }

    // 转换为word文档
    public String transferToWord(RiskInsRiskReportVo riskInsRiskReportVo, Configuration configuration,
                                 UserInfo userInfo) {
        StringBuffer stringBuf = new StringBuffer();
        Writer out = null;
        FTPUtil ftp = new FTPUtil();
        try {
//            UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");

            String riskModel = riskInsRiskReportVo.getRiskReportMainVo().getRiskModel();
            //判断main表字段中是否包含&符号
            if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getInsuredName())) {
                if (riskInsRiskReportVo.getRiskReportMainVo().getInsuredName().indexOf("&") != -1) {
                    riskInsRiskReportVo.getRiskReportMainVo().setInsuredName(this.convertPercent(riskInsRiskReportVo.getRiskReportMainVo().getInsuredName()));
                }
            }
            if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getAddressDetail())) {
                if (riskInsRiskReportVo.getRiskReportMainVo().getAddressDetail().indexOf("&") != -1) {
                    riskInsRiskReportVo.getRiskReportMainVo().setAddressDetail(this.convertPercent(riskInsRiskReportVo.getRiskReportMainVo().getAddressDetail()));
                }
            }
            if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getOthSuggest())) {
                if (riskInsRiskReportVo.getRiskReportMainVo().getOthSuggest().indexOf("&") != -1) {
                    riskInsRiskReportVo.getRiskReportMainVo().setOthSuggest(this.convertPercent(riskInsRiskReportVo.getRiskReportMainVo().getOthSuggest()));
                }
            }
            if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getAddMessage())) {
                if (riskInsRiskReportVo.getRiskReportMainVo().getAddMessage().indexOf("&") != -1) {
                    riskInsRiskReportVo.getRiskReportMainVo().setAddMessage(this.convertPercent(riskInsRiskReportVo.getRiskReportMainVo().getAddMessage()));
                }
            }
            if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getSupplementAppraisal())) {
                if (riskInsRiskReportVo.getRiskReportMainVo().getSupplementAppraisal().indexOf("&") != -1) {
                    riskInsRiskReportVo.getRiskReportMainVo().setSupplementAppraisal(this.convertPercent(riskInsRiskReportVo.getRiskReportMainVo().getSupplementAppraisal()));
                }
            }
            if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getExplain())) {
                if (riskInsRiskReportVo.getRiskReportMainVo().getExplain().indexOf("&") != -1) {
                    riskInsRiskReportVo.getRiskReportMainVo().setExplain(this.convertPercent(riskInsRiskReportVo.getRiskReportMainVo().getExplain()));
                }
            }
            if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportMainVo().getRiskUnitExplain())) {
                if (riskInsRiskReportVo.getRiskReportMainVo().getRiskUnitExplain().indexOf("&") != -1) {
                    riskInsRiskReportVo.getRiskReportMainVo().setRiskUnitExplain(this.convertPercent(riskInsRiskReportVo.getRiskReportMainVo().getRiskUnitExplain()));
                }
            }

            if (riskInsRiskReportVo.getRiskReportAddressList() != null && riskInsRiskReportVo.getRiskReportAddressList().size() > 0) {
                for (int i = 0; i < riskInsRiskReportVo.getRiskReportAddressList().size(); i++) {
                    RiskReportAddressId id = new RiskReportAddressId();
                    id.setSerialNo(i + 1);
                    riskInsRiskReportVo.getRiskReportAddressList().get(i).setId(id);
                    if (StringUtils.isNotBlank(riskInsRiskReportVo.getRiskReportAddressList().get(i).getItemAddress())) {
                        if (riskInsRiskReportVo.getRiskReportAddressList().get(i).getItemAddress().indexOf("&") != -1) {
                            riskInsRiskReportVo.getRiskReportAddressList().get(i).setItemAddress(this.convertPercent(riskInsRiskReportVo.getRiskReportAddressList().get(i).getItemAddress()));
                        }
                    }

                }
            }
            // 去除重复的元素
            // riskDcodeOutList =removeSameIdsFromList(riskDcodeOutList);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.YEAR_FIELD, new Locale("zh", "CN"));
            if ("001".equals(riskModel) || "002".equals(riskModel)) {
                // 对突出风险字符串进行处理
                String[] highlightRiskList;
                // 存储风险照片信息
                List<RiskInsImageInfoVo> riskInsImageInfoVoList = new ArrayList<RiskInsImageInfoVo>();
                if (null != riskInsRiskReportVo.getRiskReportMainVo()) {
                    String highlightRisk = riskInsRiskReportVo.getRiskReportMainVo().getHighlightRisk();
                    if (StringUtils.isNotBlank(highlightRisk)) {
                        // 去除字符串中的数字
//						String pat = "\\d+";    // \\d+[、]
                        String pat = "\\d+[、]";
                        // 指定好正则表达式
                        Pattern p = Pattern.compile(pat);
                        // 实例化Pattern类
                        highlightRiskList = p.split(highlightRisk);
                        // 29 条突出风险数据信息
                        String[] constructBuild = {"、 存在地下资产", "、 存在砖木结构厂房、 存在钢结构厂房、 存在简易建筑", "、建筑物过道堆放可能导致延烧的可燃物",
                                "、地势较周围道路或建筑物相比相对较低", "、企业所在区域受热带气旋影响较大", "、企业所在区属于高雷区、企业所在地属于强雷区",
                                "、企业周围沿江、沿河、沿湖、企业周围沿海、企业周围沿山", "、存在作业车辆充电区域在仓库内部", "、生产过程含特殊风险", "、含易燃材料",
                                "、仓储物摆放未使用垫板", "、仓库内存在生活用电", "、电气线路为明线", "、电气线路使用年限超过10年、电气线路使用年限超过10年", "、现场环境杂乱无序",
                                "、无自动灭火系统", "、无消防器材", "、轻钢结构房屋屋面铆钉汛期前未检修", "、企业内全部区域禁烟，但执行不足、企业内部分区域禁烟，但执行不足、企业内无吸烟管制",
                                "、厂区历史上有进水历史", "、企业无防盗设施", "、被保物吸引力高", "、企业无定时巡逻制度"};
                        String[] picturePaths = {"2.2.1", "2.1", "5.2.2", "5.1.4", "", "", "5.1.3", "3.12", "", "",
                                "3.11", "3.13", "3.7", "", "3.5", "4.1.1.1,4.1.1.2,4.1.1.3,4.1.1.4,4.1.1.5,4.1.1.6", "",
                                "", "4.2.4", "", "6.1.2", "", "6.1.5"};

                        for (int i = 0; i < constructBuild.length; i++) {
                            if (StringUtils.isNotBlank(constructBuild[i])) {
                                String riskReminders = "";
                                String constructBuildindex = constructBuild[i].replaceAll(" ", "");
                                RiskInsImageInfoVo riskInsImageInfoVo = new RiskInsImageInfoVo();
                                for (int j = 1; j < highlightRiskList.length; j++) {
                                    if (StringUtils.isNotBlank(highlightRiskList[j])) {
                                        String highlightRiskListindex = highlightRiskList[j].replaceAll(" ", "");
                                        highlightRiskListindex = highlightRiskListindex.substring(0,
                                                highlightRiskListindex.length());
                                        if (constructBuildindex.trim().indexOf(highlightRiskListindex.trim()) > -1) {
                                            riskReminders += highlightRiskListindex + " ";
                                        }
                                    }
                                }
                                if (StringUtils.isNotBlank(riskReminders)) {
                                    riskInsImageInfoVo.setRiskReminders(riskReminders);
                                    // String picturePath=picturePaths[i];
                                    if (null != picturePaths[i] && picturePaths[i].length() > 0) {
                                        List<String> pictureList = new ArrayList<String>();
                                        String[] pictureArray = picturePaths[i].split(",");
                                        if (null != pictureArray && pictureArray.length > 0) {
                                            for (int k = 0; k < pictureArray.length; k++) {
                                                pictureList.add(pictureArray[k]);
                                            }
                                        }
                                        riskInsImageInfoVo.setPicturePath(pictureList);
                                    } else {
                                        List<String> pictList = new ArrayList<String>();
                                        riskInsImageInfoVo.setPicturePath(pictList);
                                    }
                                    riskInsImageInfoVoList.add(riskInsImageInfoVo);
                                }

                            }
                        }
                    }
                }
                List<RiskDcode> riskDcodeInList = new ArrayList<RiskDcode>();
                List<RiskDcode> riskDcodeOutList = new ArrayList<RiskDcode>();
                if (null != riskInsImageInfoVoList && riskInsImageInfoVoList.size() > 0) {
                    for (int i = 0; i < riskInsImageInfoVoList.size(); i++) {
                        RiskInsImageInfoVo riskInsImageInfoVo = riskInsImageInfoVoList.get(i);
                        if (null != riskInsRiskReportVo.getRiskDcodeList()
                                && riskInsRiskReportVo.getRiskDcodeList().size() > 0) {
                            // riskDcodeOutList=riskInsRiskReportVo.getRiskDcodeList();
                            for (int j = 0; j < riskInsRiskReportVo.getRiskDcodeList().size(); j++) {
                                RiskDcode riskDcode = riskInsRiskReportVo.getRiskDcodeList().get(j);
                                if (null != riskInsImageInfoVo.getPicturePath()
                                        && riskInsImageInfoVo.getPicturePath().size() > 0
                                        && null != riskDcode.getCodeEname()) {
                                    for (int m = 0; m < riskInsImageInfoVo.getPicturePath().size(); m++) {
                                        if (riskInsImageInfoVo.getPicturePath().get(m)
                                                .equals(riskDcode.getCodeEname().trim())) {
                                            riskDcodeInList.add(riskDcode);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // 去除突出风险的照片
                if (null != riskInsRiskReportVo.getRiskDcodeList()
                        && riskInsRiskReportVo.getRiskDcodeList().size() > 0) {
                    for (int n = 0; n < riskInsRiskReportVo.getRiskDcodeList().size(); n++) {
                        if (null != riskDcodeInList && riskDcodeInList.size() > 0) {
                            Integer sum = 0;
                            for (int l = 0; l < riskDcodeInList.size(); l++) {
                                if (!riskInsRiskReportVo.getRiskDcodeList().get(n).getCodeEname()
                                        .equals(riskDcodeInList.get(l).getCodeEname())) {
                                    sum += 1;
                                }
                            }
                            if (sum == riskDcodeInList.size()) {
                                riskDcodeOutList.add(riskInsRiskReportVo.getRiskDcodeList().get(n));
                            }
                        } else {
                            riskDcodeOutList.add(riskInsRiskReportVo.getRiskDcodeList().get(n));
                        }
                    }
                }
                if (null != riskInsRiskReportVo.getRiskReportClaimList()
                        && riskInsRiskReportVo.getRiskReportClaimList().size() > 0) {
                    List<RiskReportClaim> riskReportClaimList = riskInsRiskReportVo.getRiskReportClaimList();
                    for (RiskReportClaim riskReportClaim : riskReportClaimList) {
                        String riskFileNo = riskReportClaim.getId().getRiskFileNo();
                        riskFileNo = dateFormat.format(riskReportClaim.getRiskDate());
                        riskReportClaim.getId().setRiskFileNo(riskFileNo);
                        if (StringUtils.isNotBlank(riskReportClaim.getRiskReason())) {
                            if (riskReportClaim.getRiskReason().indexOf("&") != -1) {
                                riskReportClaim.setRiskReason(this.convertPercent(riskReportClaim.getRiskReason()));
                            }
                        }
                        if (StringUtils.isNotBlank(riskReportClaim.getRiskPosition()) && riskReportClaim.getRiskPosition().indexOf("&") != -1) {
                            riskReportClaim.setRiskPosition(this.convertPercent(riskReportClaim.getRiskPosition()));
                        }
                        if (StringUtils.isNotBlank(riskReportClaim.getLoseInfo()) && riskReportClaim.getLoseInfo().indexOf("&") != -1) {
                            riskReportClaim.setLoseInfo(this.convertPercent(riskReportClaim.getLoseInfo()));
                        }
                        if (StringUtils.isNotBlank(riskReportClaim.getAbarbeitungMeasure()) && riskReportClaim.getAbarbeitungMeasure().indexOf("&") != -1) {
                            riskReportClaim.setAbarbeitungMeasure(this.convertPercent(riskReportClaim.getAbarbeitungMeasure()));
                        }
                    }
                    dataMap.put("riskReportClaimList", riskReportClaimList);// 风控标的要素
                }
                if (null != riskInsRiskReportVo.getRiskDcodeList()
                        && riskInsRiskReportVo.getRiskDcodeList().size() > 0) {
                    RiskDcode riskDcode = riskInsRiskReportVo.getRiskDcodeList().get(0);
                    if (null != riskDcode && "1.1".equals(riskDcode.getCodeEname())) {
                        if (null != riskDcode.getConfigList()) {
                            if (null != riskDcode.getConfigList().getUrls()
                                    && riskDcode.getConfigList().getUrls().size() > 0) {
                                // 增加第一张大门照的图片 doorImage
                                dataMap.put("doorImage", riskDcode.getConfigList().getUrls().get(0));
                            }
                        }
                    }
                }
                if (riskInsRiskReportVo.getRiskReportOccupationList().size() > 0 && riskInsRiskReportVo.getRiskReportOccupationList() != null) {
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getProductProcess() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getProductProcess().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setProductProcess(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getProductProcess()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getMaterialName() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getMaterialName().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setMaterialName(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getMaterialName()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getEleProcessArea() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getEleProcessArea().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setEleProcessArea(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getEleProcessArea()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getEleProcessPro() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getEleProcessPro().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setEleProcessPro(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getEleProcessPro()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaLiquidArea() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaLiquidArea().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setFlaLiquidArea(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaLiquidArea()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaLiquidPro() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaLiquidPro().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setFlaLiquidPro(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaLiquidPro()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaGasArea() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaGasArea().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setFlaGasArea(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaGasArea()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaGasPro() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaGasPro().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setFlaGasPro(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getFlaGasPro()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getExpDustArea() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getExpDustArea().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setExpDustArea(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getExpDustArea()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getExpDustPro() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getExpDustPro().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setExpDustPro(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getExpDustPro()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHotWorkArea() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHotWorkArea().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setHotWorkArea(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHotWorkArea()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHotWorkPro() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHotWorkPro().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setHotWorkPro(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHotWorkPro()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getCleanRoomArea() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getCleanRoomArea().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setCleanRoomArea(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHotWorkArea()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getCleanRoomPro() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getCleanRoomPro().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setCleanRoomPro(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getCleanRoomPro()));
                    }
                    if (riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHighTemPrePro() != null && riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHighTemPrePro().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportOccupationList().get(0).setHighTemPrePro(this.convertPercent(riskInsRiskReportVo.getRiskReportOccupationList().get(0).getHighTemPrePro()));
                    }
                }
                if (riskInsRiskReportVo.getRiskReportEnvironmentList().size() > 0 && riskInsRiskReportVo.getRiskReportEnvironmentList() != null) {
                    if (riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).getAddEnvironment() != null && riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).getAddEnvironment().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).setAddEnvironment(this.convertPercent(riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).getAddEnvironment()));
                    }
                    if (riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).getLowGoods() != null && riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).getLowGoods().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).setLowGoods(this.convertPercent(riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).getLowGoods()));
                    }
                    if (riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).getAddMessage() != null && riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).getAddMessage().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).setAddMessage(this.convertPercent(riskInsRiskReportVo.getRiskReportEnvironmentList().get(0).getAddMessage()));
                    }
                }
                if (riskInsRiskReportVo.getRiskReportConstructList() != null && riskInsRiskReportVo.getRiskReportConstructList().size() > 0) {
                    if (riskInsRiskReportVo.getRiskReportConstructList().get(0).getAddMessage() != null && riskInsRiskReportVo.getRiskReportConstructList().get(0).getAddMessage().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportConstructList().get(0).setAddMessage(this.convertPercent(riskInsRiskReportVo.getRiskReportConstructList().get(0).getAddMessage()));
                    }
                }
                if (riskInsRiskReportVo.getRiskReportProtectionList() != null && riskInsRiskReportVo.getRiskReportProtectionList().size() > 0) {
                    if (riskInsRiskReportVo.getRiskReportProtectionList().get(0).getAddWaterFacility() != null && riskInsRiskReportVo.getRiskReportProtectionList().get(0).getAddWaterFacility().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportProtectionList().get(0).setAddWaterFacility(this.convertPercent(riskInsRiskReportVo.getRiskReportProtectionList().get(0).getAddWaterFacility()));
                    }
                }
                if (riskInsRiskReportVo.getRiskReportInterruptList().size() > 0 && riskInsRiskReportVo.getRiskReportInterruptList() != null) {
                    if (riskInsRiskReportVo.getRiskReportInterruptList().get(0).getAddMessage() != null && riskInsRiskReportVo.getRiskReportInterruptList().get(0).getAddMessage().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportInterruptList().get(0).setAddMessage(this.convertPercent(riskInsRiskReportVo.getRiskReportInterruptList().get(0).getAddMessage()));
                    }
                }
                dataMap.put("riskDcodeInList", riskDcodeInList);
                dataMap.put("riskDcodeOutList", riskDcodeOutList);
                // 风险类型图片
                dataMap.put("riskInsImageInfoVoList", riskInsImageInfoVoList);
                dataMap.put("reportAddressList", riskInsRiskReportVo.getRiskReportAddressList());// 风控地址信息
                // dataMap.put("riskReportClaimList", riskReportClaimList);//风控标的要素
                dataMap.put("riskReportConstructInfoList", riskInsRiskReportVo.getRiskReportConstructInfoList());// 风控建筑详细信息
                dataMap.put("riskReportConstructList", riskInsRiskReportVo.getRiskReportConstructList());// 风控建筑
                dataMap.put("riskReportOccupationList", riskInsRiskReportVo.getRiskReportOccupationList());// 风控占用性质
                dataMap.put("riskReportProtectionList", riskInsRiskReportVo.getRiskReportProtectionList());// 风控保护措施
                dataMap.put("riskReportEnvironmentList", riskInsRiskReportVo.getRiskReportEnvironmentList());// 风控环境
                dataMap.put("riskReportInterruptList", riskInsRiskReportVo.getRiskReportInterruptList());// 风控附加扩展供应中断责任
                dataMap.put("riskReportTheftList", riskInsRiskReportVo.getRiskReportTheftList());// 风控附加扩展盗窃、抢劫责任
                dataMap.put("riskReportAirStorageList", riskInsRiskReportVo.getRiskReportAirStorageList());// 风控附加扩展露天堆放
                dataMap.put("riskReportAssessList", riskInsRiskReportVo.getRiskReportAssessList());// 风控风险值
                dataMap.put("riskDcodeList", riskInsRiskReportVo.getRiskDcodeList());// 风控报告影像信息
                dataMap.put("gradeImage", this.generateImage(riskInsRiskReportVo.getPicBase64Info()));// 打分图
            } else if ("004".equals(riskModel)) {
                if (riskInsRiskReportVo.getRiskReportMachineList() != null && riskInsRiskReportVo.getRiskReportMachineList().size() > 0) {
                    if (riskInsRiskReportVo.getRiskReportMachineList().get(0).getRepairSystem() != null && riskInsRiskReportVo.getRiskReportMachineList().get(0).getRepairSystem().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportMachineList().get(0).setRepairSystem(this.convertPercent(riskInsRiskReportVo.getRiskReportMachineList().get(0).getRepairSystem()));
                    }
                }
                dataMap.put("riskReportMachineList", riskInsRiskReportVo.getRiskReportMachineList()); // 风控机器损坏险表
            } else if ("005".equals(riskModel)) {
                if (riskInsRiskReportVo.getRiskReportCloBusinessList() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().size() > 0) {
                    if (riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getItemsAmount() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getItemsAmount().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).setItemsAmount(this.convertPercent(riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getItemsAmount()));
                    }
                    if (riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getCertainWay() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getCertainWay().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).setCertainWay(this.convertPercent(riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getCertainWay()));
                    }
                    if (riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaintainCost() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaintainCost().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).setMaintainCost(this.convertPercent(riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaintainCost()));
                    }
                    if (riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxPayoutDate() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxPayoutDate().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).setMaxPayoutDate(this.convertPercent(riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxPayoutDate()));
                    }
                    if (riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getFranchiseDate() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getFranchiseDate().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).setFranchiseDate(this.convertPercent(riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getFranchiseDate()));
                    }
                    if (riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getKeyEquip() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getKeyEquip().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).setKeyEquip(this.convertPercent(riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getKeyEquip()));
                    }
                    if (riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxRecovTime() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxRecovTime().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).setMaxRecovTime(this.convertPercent(riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxRecovTime()));
                    }
                    if (riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxBusiTime() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxBusiTime().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).setMaxBusiTime(this.convertPercent(riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxBusiTime()));
                    }
                    if (riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxLoss() != null && riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxLoss().indexOf("&") != -1) {
                        riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).setMaxLoss(this.convertPercent(riskInsRiskReportVo.getRiskReportCloBusinessList().get(0).getMaxLoss()));
                    }
                }

                dataMap.put("riskReportCloBusinessList", riskInsRiskReportVo.getRiskReportCloBusinessList()); // 风控营业中断险表
            }
            String format = dateFormat.format(riskInsRiskReportVo.getRiskReportMainVo().getExploreDate());
            // 需要哪些数据？
            dataMap.put("userInfo", userInfo);

            dataMap.put("riskReportMainExploreDate", format);
            dataMap.put("riskReportMainVo", riskInsRiskReportVo.getRiskReportMainVo());// 风控信息主表

            String path = this.getClass().getResource("/").getPath();
            path = path + "com/picc/riskctrl/riskins/template";
            File file = new File(path);
            configuration.setDirectoryForTemplateLoading(file);
            Template template = null;
            File outFile = null;
//            String pathDir = RiskInsService.class.getClassLoader().getResource("/").getPath();

            // String dir = pathDir.substring(0, pathDir.indexOf("WEB-INF/classes/"));
            // 存储到公共上传目录下
            ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());

            String dir = bundle.getString("saveRootPath") + bundle.getString("saveTypePath");

            String riskFileNo = riskInsRiskReportVo.getRiskReportMainVo().getRiskFileNo();
            String fileName = riskFileNo;

            // 操作人员代码
            String operatorCode = riskInsRiskReportVo.getRiskReportMainVo().getOperatorCode();
            stringBuf.append(dir).append("/").append("downloadFile");
            if ("002".equals(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel())) {
                template = configuration.getTemplate("riskInsPluralismInput.ftl");
            } else if ("001".equals(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel())) {
                template = configuration.getTemplate("riskInsDetailInput.ftl");
            } else if ("004".equals(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel())) {
                template = configuration.getTemplate("riskInsMachinePut.ftl");
            } else if ("005".equals(riskInsRiskReportVo.getRiskReportMainVo().getRiskModel())) {
                template = configuration.getTemplate("riskInsCloBusiness.ftl");
            }
            stringBuf.append("/" + fileName + "_" + operatorCode + ".doc");


            out = new BufferedWriter(new OutputStreamWriter(
                    ftp.uploadFile("downloadFile/" + fileName + "_" + operatorCode + ".doc"), "UTF-8"));

//			outFile = new File(stringBuf.toString());
//			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));

            template.process(dataMap, out);
        } catch (Exception e) {
            LOGGER.info("生成文档异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("生成文档异常:" + e);
        } finally {
            if (out != null) {
                try {
                    out.close();
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
        return stringBuf.toString();
    }

    //处理word文档不识别符号
    public String convertPercent(String str) {
        String field = str.replace("&", "&amp");
        return field;
    }

    // 生成图片的路径
    public String generateImage(String picBase64Info) {
        if (picBase64Info == null) { // 图像数据为空
            return "";
        }
        int index = picBase64Info.indexOf(',') + 1;
        picBase64Info = picBase64Info.substring(index, picBase64Info.length());
        if ("".equals(picBase64Info.trim())) {
            return "";
        }
        // BASE64Decoder decoder = new BASE64Decoder();
        // try{
        // //Base64解码,转成byte[]
        // byte[] b = decoder.decodeBuffer(picBase64Info);
        // for(int i=0;i<b.length;++i){
        // if(b[i]<0) {//调整异常数据
        // b[i]+=256;
        // }
        // }
        // String imgFilePath = "d://打分图.png";//新生成的图片
        // OutputStream out = new FileOutputStream(imgFilePath);
        // out.write(b);
        // out.flush();
        // out.close();
        // return imgFilePath;
        // }catch (Exception e){
        // return "";
        // }
        return picBase64Info;
    }

    // addby liqiankun
    // word文档中中文的翻译
    public RiskInsRiskReportVo transferToChinese(RiskInsRiskReportVo riskInsRiskReportVo) {
        // RiskReportMainVo下的
        try {
            String riskModel = riskInsRiskReportVo.getRiskReportMainVo().getRiskModel();
            if (null != riskInsRiskReportVo.getRiskReportMainVo()) {
                List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportMainVo");
                RiskReportMain riskReportMainVo = riskInsRiskReportVo.getRiskReportMainVo();
                this.setPublicRiskDcode(riskDcodes, riskReportMainVo);
                if ("001".equals(riskModel) || "002".equals(riskModel)) {
                    // 投保附加险情况
                    String addRisk = riskReportMainVo.getAddRisk();
                    if (isNotNull(addRisk)) {
                        setPublicRiskDcodeCheckBox(queryRiskDcodeCheckBox("addRisk"), riskReportMainVo, "addRisk");
                    }
                }
            }
            if ("004".equals(riskModel)) {
                if (null != riskInsRiskReportVo.getRiskReportMachineList()
                        && riskInsRiskReportVo.getRiskReportMachineList().size() > 0) {
                    for (int i = 0; i < riskInsRiskReportVo.getRiskReportMachineList().size(); i++) {
                        RiskReportMachine riskReportMachine = riskInsRiskReportVo.getRiskReportMachineList().get(i);
                        List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportMachine");
                        this.setPublicRiskDcode(riskDcodes, riskReportMachine);
                    }
                }

            } else if ("005".equals(riskModel)) {
                if (null != riskInsRiskReportVo.getRiskReportCloBusinessList()
                        && riskInsRiskReportVo.getRiskReportCloBusinessList().size() > 0) {
                    for (int i = 0; i < riskInsRiskReportVo.getRiskReportCloBusinessList().size(); i++) {
                        RiskReportCloBusiness riskReportCloBusiness = riskInsRiskReportVo.getRiskReportCloBusinessList()
                                .get(i);
                        List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportCloBusiness");
                        // 由于riskDcodes表中004和005的字段有重名，所以暂时用字符串判断翻译
                        if ("A".equals(riskReportCloBusiness.getSpareParts())) {
                            riskReportCloBusiness.setSpareParts("有");
                        } else if ("B".equals(riskReportCloBusiness.getSpareParts())) {
                            riskReportCloBusiness.setSpareParts("无");
                        }
                        if ("A".equals(riskReportCloBusiness.getRepairman())) {
                            riskReportCloBusiness.setRepairman("国内");
                        } else if ("B".equals(riskReportCloBusiness.getRepairman())) {
                            riskReportCloBusiness.setRepairman("国外");
                        }
                        this.setPublicRiskDcode(riskDcodes, riskReportCloBusiness);
                    }
                }
            } else {
                if (null != riskInsRiskReportVo.getRiskReportClaimList()
                        && riskInsRiskReportVo.getRiskReportClaimList().size() > 0) {
                    // List<RiskReportClaim> riskReportClaimList=new ArrayList<RiskReportClaim>();
                    for (int i = 0; i < riskInsRiskReportVo.getRiskReportClaimList().size(); i++) {
                        RiskReportClaim riskReportClaim = riskInsRiskReportVo.getRiskReportClaimList().get(i);
                        List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportClaim");
                        this.setPublicRiskDcode(riskDcodes, riskReportClaim);
                    }
                }
                if (null != riskInsRiskReportVo.getRiskReportConstructInfoList()
                        && riskInsRiskReportVo.getRiskReportConstructInfoList().size() > 0) {
                    // List<RiskReportConstructInfo> riskReportConstructInfoList=new
                    // ArrayList<RiskReportConstructInfo>();
                    for (int i = 0; i < riskInsRiskReportVo.getRiskReportConstructInfoList().size(); i++) {
                        RiskReportConstructInfo riskReportConstructInfo = riskInsRiskReportVo
                                .getRiskReportConstructInfoList().get(i);
                        List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportConstructInfo");
                        this.setPublicRiskDcode(riskDcodes, riskReportConstructInfo);
                    }
                }
                if (null != riskInsRiskReportVo.getRiskReportConstructList()
                        && riskInsRiskReportVo.getRiskReportConstructList().size() > 0) {
                    RiskReportConstruct riskReportConstruct = riskInsRiskReportVo.getRiskReportConstructList().get(0);
                    List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportConstruct");
                    this.setPublicRiskDcode(riskDcodes, riskReportConstruct);
                    // 建筑物内建筑防火分隔设施类型 ["C","B","A"]
                    String fireproofingType = riskInsRiskReportVo.getRiskReportConstructList().get(0)
                            .getFireproofingType();
                    if (isNotNull(fireproofingType)) {
                        setPublicRiskDcodeCheckBox(queryRiskDcodeCheckBox("fireproofingType"), riskReportConstruct,
                                "fireproofingType");
                    }
                }
                if (null != riskInsRiskReportVo.getRiskReportOccupationList()
                        && riskInsRiskReportVo.getRiskReportOccupationList().size() > 0) {
                    RiskReportOccupation riskReportOccupation = riskInsRiskReportVo.getRiskReportOccupationList()
                            .get(0);
                    List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportOccupation");
                    this.setPublicRiskDcode(riskDcodes, riskReportOccupation);
                }
                if (null != riskInsRiskReportVo.getRiskReportProtectionList()
                        && riskInsRiskReportVo.getRiskReportProtectionList().size() > 0) {
                    RiskReportProtection riskReportProtection = riskInsRiskReportVo.getRiskReportProtectionList()
                            .get(0);
                    List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportProtection");
                    this.setPublicRiskDcode(riskDcodes, riskReportProtection);
                    // 消防器材类型
                    String fireEquipType = riskInsRiskReportVo.getRiskReportProtectionList().get(0).getFireEquipType();
                    if (isNotNull(fireEquipType)) {
                        setPublicRiskDcodeCheckBox(queryRiskDcodeCheckBox("fireEquipType"), riskReportProtection,
                                "fireEquipType");
                    }
                    // 企业防水灾设施
                    String waterFacility = riskInsRiskReportVo.getRiskReportProtectionList().get(0).getWaterFacility();
                    if (isNotNull(waterFacility)) {
                        setPublicRiskDcodeCheckBox(queryRiskDcodeCheckBox("waterFacility"), riskReportProtection,
                                "waterFacility");
                    }
                    // 自动灭火系统(覆盖区域)
                    String selfExtArea = riskInsRiskReportVo.getRiskReportProtectionList().get(0).getSelfExtArea();
                    if (isNotNull(selfExtArea)) {
                        setPublicRiskDcodeCheckBox(queryRiskDcodeCheckBox("selfExtArea"), riskReportProtection,
                                "selfExtArea");
                    }
                }
                if (null != riskInsRiskReportVo.getRiskReportEnvironmentList()
                        && riskInsRiskReportVo.getRiskReportEnvironmentList().size() > 0) {
                    RiskReportEnvironment riskReportEnvironment = riskInsRiskReportVo.getRiskReportEnvironmentList()
                            .get(0);
                    List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportEnvironment");
                    this.setPublicRiskDcode(riskDcodes, riskReportEnvironment);
                }
                if (null != riskInsRiskReportVo.getRiskReportTheftList()
                        && riskInsRiskReportVo.getRiskReportTheftList().size() > 0) {
                    RiskReportTheft riskReportTheft = riskInsRiskReportVo.getRiskReportTheftList().get(0);
                    List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportTheft");
                    this.setPublicRiskDcode(riskDcodes, riskReportTheft);
                }
                if (null != riskInsRiskReportVo.getRiskReportInterruptList()
                        && riskInsRiskReportVo.getRiskReportInterruptList().size() > 0) {
                    RiskReportInterrupt riskReportInterrupt = riskInsRiskReportVo.getRiskReportInterruptList().get(0);
                    List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportInterrupt");
                    this.setPublicRiskDcode(riskDcodes, riskReportInterrupt);
                }
                if (null != riskInsRiskReportVo.getRiskReportAirStorageList()
                        && riskInsRiskReportVo.getRiskReportAirStorageList().size() > 0) {
                    RiskReportAirStorage riskReportAirStorage = riskInsRiskReportVo.getRiskReportAirStorageList()
                            .get(0);
                    List<RiskDcode> riskDcodes = this.queryRiskDcodeToWord("riskReportAirStorage");
                    this.setPublicRiskDcode(riskDcodes, riskReportAirStorage);
                }
            }

        } catch (Exception e) {
            LOGGER.info("word中文翻译异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("word中文翻译异常:" + e);
        }
        return riskInsRiskReportVo;
    }

    // 给下拉框设置中文值的方法
    @SuppressWarnings("static-access")
    public void setPublicRiskDcode(List<RiskDcode> riskDcodes, Object object) {
        try {
            if (null != riskDcodes && riskDcodes.size() > 0) {
                for (int k = 0; k < riskDcodes.size(); k++) {
                    String codeType = riskDcodes.get(k).getId().getCodeType().trim();
                    String codeCode = riskDcodes.get(k).getId().getCodeCode().trim();
                    String validStatus = riskDcodes.get(k).getValidStatus().trim();
                    if (this.getMethod(object, codeType).equals(codeCode) && "1".equals(validStatus)) {
                        try {
                            setMethod(object, codeType, riskDcodes.get(k).getCodeCname());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("设置中文值异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("设置中文值异常:" + e);
        }
    }

    // 给复选框设置中文值的方法
    @SuppressWarnings("static-access")
    public void setPublicRiskDcodeCheckBox(List<RiskDcode> riskDcodes, Object object, String codeType) {
        if (null != riskDcodes && riskDcodes.size() > 0) {
            String totalString = "";
            for (int k = 0; k < riskDcodes.size(); k++) {
                String codeCode = riskDcodes.get(k).getId().getCodeCode().trim();
                if (this.getMethod(object, codeType).toString().indexOf(codeCode) != -1) {
                    totalString += riskDcodes.get(k).getCodeCname() + " ";
                }
            }
            try {
                setMethod(object, codeType, totalString);
            } catch (Exception e) {
                LOGGER.info("复选框设置中文值异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException("复选框设置中文值异常:" + e);
            }

        }
    }

    // 通过upperCode来查询RiskDcode表中信息
    public List<RiskDcode> queryRiskDcodeToWord(String upperCode) {
        List<RiskDcode> riskDcodeList;
        try {
            riskDcodeList = new ArrayList<RiskDcode>();
            if (StringUtils.isNotBlank(upperCode)) {

//                QueryRule queryRule = QueryRule.getInstance();
//                queryRule.addEqual("upperCode", upperCode);
//                queryRule.addEqual("validStatus", "1");
//                riskDcodeList = databaseDao.findAll(RiskDcode.class, queryRule);
                Criteria<RiskDcode> criteria = new Criteria<>();
                criteria.add(Restrictions.eq("upperCode", upperCode));
                criteria.add(Restrictions.eq("validStatus", "1"));
                riskDcodeList = riskDcodeRepository.findAll(criteria);
            }
        } catch (Exception e) {
            LOGGER.info("查询RiskDcode表中信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询RiskDcode表中信息异常:" + e);
        }
        return riskDcodeList;
    }

    // 复选框中文含义的翻译
    public List<RiskDcode> queryRiskDcodeCheckBox(String codeType) {
        List<RiskDcode> riskDcodeList = new ArrayList<RiskDcode>();
        try {
            if (StringUtils.isNotBlank(codeType)) {
//                QueryRule queryRule = QueryRule.getInstance();
//                queryRule.addEqual("id.codeType", codeType);
//                queryRule.addEqual("validStatus", "0");
//                riskDcodeList = databaseDao.findAll(RiskDcode.class, queryRule);
                Criteria<RiskDcode> criteria = new Criteria<>();
                criteria.add(Restrictions.eq("id.codeType", codeType));
                criteria.add(Restrictions.eq("validStatus", "0"));
                riskDcodeList = riskDcodeRepository.findAll(criteria);
            }
        } catch (Exception e) {
            LOGGER.info("复选框中文含义的翻译异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("复选框中文含义的翻译异常:" + e);
        }
        return riskDcodeList;
    }

    // 获取类中相应字段的值
    public static Object getMethod(Object object, String fieldName) {
        Field f;
        Object obj = new Object();
        try {
            f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            obj = f.get(object);
            if (obj == null) {
                obj = "";
            }
        } catch (NoSuchFieldException e) {
            LOGGER.info("反射异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("反射异常:" + e);
        } catch (SecurityException e) {
            LOGGER.info("反射异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("反射异常:" + e);
        } catch (IllegalArgumentException e) {
            LOGGER.info("反射异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("反射异常:" + e);
        } catch (IllegalAccessException e) {
            LOGGER.info("反射异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("反射异常:" + e);
        }
        return obj;
    }

    // 给相应的字段进行set值
    public void setMethod(Object object, String fieldName, String fieldValue) throws Exception {
        try {
            Field f;
            f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(object, fieldValue);
        } catch (Exception e) {
            LOGGER.info("给相应的字段设置值异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("给相应的字段设置值异常:" + e);
        }
    }

    public boolean isNotNull(Object obj) {
        if (null != obj && !"".equals(obj.toString().trim())) {
            return true;
        }
        return false;
    }

    public String isNull(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * @return List<RiskDcode>
     * @throws @日期 20180326
     * @功能：把影像图片转为base64格式
     * @author wyj
     */
    public List<RiskDcode> setUrlToBase64(List<RiskDcode> riskDcodeList) {
        List<RiskDcode> newList = new ArrayList<RiskDcode>();
        try {
            String ip = "";
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();

            ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
            String savePort = bundle.getString("savePort");
            String savePath = "http" + "://" + ip + ":" + savePort;
            if (riskDcodeList != null && riskDcodeList.size() > 0) {
                for (int i = 0; i < riskDcodeList.size(); i++) {
                    ImageConfigVo configList = riskDcodeList.get(i).getConfigList();
                    if (configList != null) {
                        List<String> urls = configList.getUrls();
                        if (urls != null && urls.size() > 0) {
                            for (int j = 0; j < urls.size(); j++) {
                                String url = urls.get(j);
                                if (url.indexOf("http") == -1) {
                                    url = savePath + url;
                                }
//                                AjaxResult ajaxResult = riskInsImageService.getImageBase(url);
//                                urls.set(j, (String) ajaxResult.getData());
                            }
                            configList.setUrls(urls);
                        }
                    } else {
                        continue;
                    }
                    riskDcodeList.get(i).setConfigList(configList);
                    newList.add(riskDcodeList.get(i));
                }
            }
        } catch (Exception e) {
            LOGGER.info("影像图片格式转换异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("影像图片格式转换异常:" + e);
        }
        return newList;
    }

    /**
     * @param requestJsonStr 请求报文json字符串
     * @return RiskControlResponseVo
     * @功能：返回风控报告报文信息
     * @author 王亚军
     * @日期 2017-12-13
     */
    public RiskControlResponseVo queryByRiskFileNo(String requestJsonStr) {
        // 解析请求报文为RiskControlRequestVo对象
        RiskControlRequestVo riskControlRequestVo = JSON.parseObject(requestJsonStr, RiskControlRequestVo.class);
        String responseJsonStr = "";
        // 返回报文对象创建
        RiskControlResponseVo riskControlResponseVo = new RiskControlResponseVo();
//        ResponseHead responsehead = new ResponseHead();
        ReturnRiskControlDataDto returnDataDto = new ReturnRiskControlDataDto();
        try {
            if (riskControlRequestVo != null) {
                Date date = new Date();

                if (
//                		riskControlRequestVo.getRequesthead() != null&& 
                        riskControlRequestVo.getRiskControlConditionDto() != null) {
//                    String request_type = riskControlRequestVo.getRequesthead().getRequest_type();
//                    String uuid = riskControlRequestVo.getRequesthead().getUuid();
                    // String sender = riskControlRequestVo.getRequesthead().getSender();
//                    String server_version = riskControlRequestVo.getRequesthead().getServer_version();
//                    responsehead.setRequest_type(request_type);
//                    responsehead.setSender(Constant.SENDER);
//                    responsehead.setUuid(uuid);
//                    responsehead.setServer_version(server_version);
//                    responsehead.setTimestamp(date);
                    RiskReportMain riskReportMain = new RiskReportMain();
                    // 获取请求报文信息
                    String riskFileNo = riskControlRequestVo.getRiskControlConditionDto().getRiskFileNo();
                    // 判断风控报告编号是否为空
                    if (StringUtils.isBlank(riskFileNo)) {
//                        responsehead.setResponse_code("-1");
//                        responsehead.setError_message("没有提供风险编号！");
                    } else {
                        try {
                            // 查询风控报告信息
                            riskReportMain = this.queryRiskReportMain(riskFileNo);
                            if (riskReportMain != null) {
                                returnDataDto.setRiskFileNo(riskReportMain.getRiskFileNo());
                                returnDataDto.setClassCode(riskReportMain.getClassCode());
                                returnDataDto.setComCode(riskReportMain.getComCode());
                                returnDataDto.setInsuredCode(riskReportMain.getInsuredCode());
                                returnDataDto.setInsuredName(riskReportMain.getInsuredName());
                                returnDataDto.setInsuredType(riskReportMain.getInsuredType());
                                returnDataDto.setRiskCode(riskReportMain.getRiskCode());
                                returnDataDto.setRiskModel(riskReportMain.getRiskModel());
                                if (riskReportMain.getProposalNo() != null) {
                                    returnDataDto.setProposalNo(riskReportMain.getProposalNo());
                                } else {
                                    returnDataDto.setProposalNo("");
                                }
                                if (riskReportMain.getPolicyNo() != null) {
                                    returnDataDto.setPolicyNo(riskReportMain.getPolicyNo());
                                } else {
                                    returnDataDto.setPolicyNo("");
                                }
                                riskControlResponseVo.setReturnRiskControlDataDto(returnDataDto);
                                if ("1".equals(riskReportMain.getUnderwriteFlag())
                                        || "3".equals(riskReportMain.getUnderwriteFlag())) {
                                    if (StringUtils.isNotBlank(riskReportMain.getProposalNo())) {
//                                        responsehead.setResponse_code("2");
//                                        responsehead.setError_message("此风控报告为通过状态!且投保单号已绑定!");
                                    } else {
//                                        responsehead.setResponse_code("1");
//                                        responsehead.setError_message("此风控报告为通过状态!且投保单号未绑定!");
                                    }
                                } else {
//                                    responsehead.setResponse_code("0");
//                                    responsehead.setError_message("此风控报告不为通过状态！");
                                }
                            } else {
//                                responsehead.setResponse_code("0");
//                                responsehead.setError_message("不存在数据!");
                            }
                        } catch (Exception e) {
                            LOGGER.info("查询数据出错：" + e.getMessage(), e);
                            e.printStackTrace();
//                            responsehead.setResponse_code("-1");
//                            responsehead.setError_message("查询数据出错！");
                            throw new RuntimeException("查询数据出错:" + e);
                        }

                    }
                } else {
//                    responsehead.setResponse_code("-1");
//                    responsehead.setError_message("请求头或请求体不能为空！");
                }
//                riskControlResponseVo.setResponsehead(responsehead);
                // 把riskControlResponseVo解析为json字符串
//				responseJsonStr = JSON.toJSONString(riskControlResponseVo, SerializerFeature.UseISO8601DateFormat);

            }
        } catch (Exception e) {
            LOGGER.info("返回风控报告报文信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("返回风控报告报文信息异常:" + e);
        }
        return riskControlResponseVo;

    }

    /**
     * @功能：按行业大类进行统计审核通过的单子
     * @author liqiankun
     * @时间：2017-11-20
     * @修改记录：modifyby liqiankun 20200213
     */
    public ApiResponse queryRiskDecode() {
        ApiResponse apiResponse = new ApiResponse();
        List<Object[]> countNewList = new ArrayList<Object[]>();
        Object[] object = new Object[2];
        object[0] = "A";
        object[1] = "0";
        countNewList.add(object);
        Object[] object1 = new Object[2];
        object1[0] = "B";
        object1[1] = "0";
        countNewList.add(object1);
        Object[] object2 = new Object[2];
        object2[0] = "C";
        object2[1] = "0";
        countNewList.add(object2);
        Object[] object3 = new Object[2];
        object3[0] = "D";
        object3[1] = "0";
        countNewList.add(object3);
        Object[] object4 = new Object[2];
        object4[0] = "E";
        object4[1] = "0";
        countNewList.add(object4);
        Object[] object5 = new Object[2];
        object5[0] = "F";
        object5[1] = "0";
        countNewList.add(object5);
        Object[] object6 = new Object[2];
        object6[0] = "G";
        object6[1] = "0";
        countNewList.add(object6);
        Object[] object7 = new Object[2];
        object7[0] = "H";
        object7[1] = "0";
        countNewList.add(object7);
        Object[] object8 = new Object[2];
        object8[0] = "I";
        object8[1] = "0";
        countNewList.add(object8);
        Object[] object9 = new Object[2];
        object9[0] = "J";
        object9[1] = "0";
        countNewList.add(object9);
        Object[] object10 = new Object[2];
        object10[0] = "K";
        object10[1] = "0";
        countNewList.add(object10);
        Object[] object11 = new Object[2];
        object11[0] = "L";
        object11[1] = "0";
        countNewList.add(object11);
        Object[] object12 = new Object[2];
        object12[0] = "M";
        object12[1] = "0";
        countNewList.add(object12);
        Object[] object13 = new Object[2];
        object13[0] = "N";
        object13[1] = "0";
        countNewList.add(object13);
        Object[] object14 = new Object[2];
        object14[0] = "O";
        object14[1] = "0";
        countNewList.add(object14);
        Object[] object15 = new Object[2];
        object15[0] = "P";
        object15[1] = "0";
        countNewList.add(object15);
        Object[] object16 = new Object[2];
        object16[0] = "Q";
        object16[1] = "0";
        countNewList.add(object16);
        Object[] object17 = new Object[2];
        object17[0] = "R";
        object17[1] = "0";
        countNewList.add(object17);
        Object[] object18 = new Object[2];
        object18[0] = "S";
        object18[1] = "0";
        countNewList.add(object18);
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<String[]> countListC = new ArrayList<String[]>();
        try {
            conn = dataSource.getConnection();
            String sqlC = "select count(substring(businesssource,1,1)),substring(businesssource,1,1) from riskReport_main where businesssource is not null and trim(businesssource) !='' and underwriteflag = '1' or underwriteflag='3'  group by substring(businesssource,1,1) order by substring(businesssource,1,1)";
//                String sqlC = "select count(businesssource[1]), businesssource[1] from riskReport_main where businesssource is not null and trim(businesssource) !='' and underwriteflag = 1 or underwriteflag=3  group by businesssource[1] order by businesssource[1]";
            stat = conn.prepareStatement(sqlC);
            rs = stat.executeQuery();
            for (; rs.next(); ) {
                String[] str = new String[2];
                str[0] = rs.getString(1);
                str[1] = rs.getString(2);
                countListC.add(str);
            }
        } catch (SQLException e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        } finally {
            releaseResources(stat, conn);
        }
        for (Object[] objectNew : countNewList) {
            if (countListC != null && countListC.size() != 0) {
                for (String[] objects : countListC) {
                    if (objectNew[0].equals(objects[1])) {
                        if (objects[0] != null) {
                            objectNew[1] = objects[0];
                        }
                    }
                }
            }
        }
        apiResponse.setData(countNewList);
        return apiResponse;
    }

    /**
     * @param stat PrepareStatement对象
     * @param conn 数据库连接
     * @return RiskCountVo @日期：2018-5-11
     * @功能:释放数据库资源，包括数据库连接和PrepareStatement对象
     * @author 马军亮
     */
    private void releaseResources(Statement stat, Connection conn) {
        try {
            if (stat != null) {
                stat.close();
            }
        } catch (SQLException e) {
            LOGGER.info("关闭异常：" + e.getMessage(), e);
            throw new RuntimeException("关闭异常:" + e);
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.info("关闭异常：" + e.getMessage(), e);
            throw new RuntimeException("关闭异常:" + e);
        }
    }

    /**
     * @功能 近30日的出单量
     * @author 周东旭
     */
    public ApiResponse queryOrders(String comCode, String madedateBegin, String madedateEnd, String flag) {
        ApiResponse apiResponse = new ApiResponse();
        List<String[]> countListC = new ArrayList<String[]>();
        List<String[]> list = new ArrayList<String[]>();
        Connection conn = null;
        PreparedStatement stat = null;

        String[] list_City = {"2102", "3302", "3502", "4403", "3702", "0000", "9999"};
        boolean isCity = false;
        for (String s : list_City) {
            if (s.equals(comCode.substring(0, 4))) {
                isCity = true;
            }
        }
        if (isCity) {
            comCode = comCode.substring(0, 4);
        } else {
            comCode = comCode.substring(0, 2);
        }
        List<String[]> countListC_temp = new ArrayList<String[]>();
        try {
            if ("A".equals(flag)) {
                String name = "queryOrdersList" + comCode;
                conn = dataSource.getConnection();

//					String sqlC = "select extend( madedate, year to day ), count(*) from riskreport_main where madedate between '"+madedateEnd+"' and '"+madedateBegin+"' and (underwriteFlag ='1' or underwriteFlag='3') group by 1 order by 1";
                String sqlC;
                if ("0000".equals(comCode)) {
                    sqlC = "select to_char(madedate,'yyyy-mm-dd hh24:mi:ss'), count(*) from riskreport_main where madedate between ? and ? and underwriteFlag in ('1','3') group by 1 order by 1";
                    stat = conn.prepareStatement(sqlC);
                    stat.setString(1, madedateBegin);
                    stat.setString(2, madedateEnd);
                } else if (comCode.length() == 4) {
                    sqlC = "select to_char(madedate,'yyyy-mm-dd hh24:mi:ss'), count(*) from riskreport_main where madedate between ? and ? and underwriteFlag in ('1','3') and substring(comcode,1,4) = ? group by 1 order by 1";
                    stat = conn.prepareStatement(sqlC);
                    stat.setString(1, madedateBegin);
                    stat.setString(2, madedateEnd);
                    stat.setString(3, comCode);
                } else {
                    sqlC = "select to_char(madedate,'yyyy-mm-dd hh24:mi:ss'), count(*) from riskreport_main where madedate between ? and ? and underwriteFlag in ('1','3') and substring(comcode,1,4) not in ('2102','3302','3502','4403','3702','0000','9999') and substring(comcode,1,2) = ? group by 1 order by 1";
                    stat = conn.prepareStatement(sqlC);
                    stat.setString(1, madedateBegin);
                    stat.setString(2, madedateEnd);
                    stat.setString(3, comCode);
                }
                ResultSet rs = stat.executeQuery();
                for (; rs.next(); ) {
                    String[] str = new String[2];
                    str[0] = rs.getString(1);
                    str[1] = rs.getString(2);
                    countListC.add(str);
                }
                list = countListC;
            } else if ("B".equals(flag)) {
                String name = "queryOrderCount" + comCode;
                conn = dataSource.getConnection();
//					String sqlC = "select count(*) from riskReport_Main where madedate between '"+madedateEnd+"' and '"+madedateBegin+"' and (underwriteFlag = 1 or underwriteFlag= 3)";
                String sqlC;
                if ("0000".equals(comCode)) {
                    sqlC = "select count(*) from riskReport_Main where madedate between ? and ? and underwriteFlag in ('1','3')";
                    stat = conn.prepareStatement(sqlC);
                    stat.setString(1, madedateBegin);
                    stat.setString(2, madedateEnd);
                } else if (comCode.length() == 4) {
                    sqlC = "select count(*) from riskReport_Main where madedate between ? and ? and underwriteFlag in ('1','3') and substring(comcode,1,4) = ?";
                    stat = conn.prepareStatement(sqlC);
                    stat.setString(1, madedateBegin);
                    stat.setString(2, madedateEnd);
                    stat.setString(3, comCode);
                } else {
                    sqlC = "select count(*) from riskReport_Main where madedate between ? and ? and underwriteFlag in ('1','3') and substring(comcode,1,4) not in ('2102','3302','3502','4403','3702','0000','9999') and substring(comcode,1,2) = ?";
                    stat = conn.prepareStatement(sqlC);
                    stat.setString(1, madedateBegin);
                    stat.setString(2, madedateEnd);
                    stat.setString(3, comCode);
                }
                ResultSet rs = stat.executeQuery();
                for (; rs.next(); ) {
                    String[] str = new String[1];
                    str[0] = rs.getString(1);
                    countListC.add(str);
                }
                list = countListC;
            } else if ("C".equals(flag)) {
                String name = "queryAllOrderCount" + comCode;
                conn = dataSource.getConnection();
//					String sqlC = "select count(*) from riskReport_Main where underwriteFlag = 1 or underwriteFlag= 3";
                String sqlC;
                if ("0000".equals(comCode)) {
                    sqlC = "select count(*) from riskReport_Main where underwriteFlag = '1' or underwriteFlag= '3'";
                    stat = conn.prepareStatement(sqlC);
                } else if (comCode.length() == 4) {
                    sqlC = "select count(*) from riskReport_Main where underwriteFlag in ('1','3') and substring(comcode,1,4) = ?";
                    stat = conn.prepareStatement(sqlC);
                    stat.setString(1, comCode);
                } else {
                    sqlC = "select count(*) from riskReport_Main where underwriteFlag in ('1','3') and substring(comcode,1,4) not in ('2102','3302','3502','4403','3702','0000','9999') and substring(comcode,1,2) = ?";
                    stat = conn.prepareStatement(sqlC);
                    stat.setString(1, comCode);
                }

                ResultSet rs = stat.executeQuery();
                for (; rs.next(); ) {
                    String[] str = new String[1];
                    str[0] = rs.getString(1);
                    countListC.add(str);
                }
                list = countListC;
            }
            apiResponse.setData(list);
        } catch (SQLException e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        } finally {
            releaseResources(stat, conn);
        }
        return apiResponse;
    }


    public ApiResponse<List<UnfinishBusinessVo>> queryphotoFileHasBeenFeedback(String userCode, int pageNo, int pageSize) {
        ApiResponse<List<UnfinishBusinessVo>> apiResponse = new ApiResponse<>();
        List<UnfinishBusinessVo> unBusinessVoList = new ArrayList<UnfinishBusinessVo>();

//        QueryRule queryRule = QueryRule.getInstance();
        Criteria<RiskReportSaleCorrection> criteriaUtiFactor = new Criteria<>();
        if (StringUtils.isNotBlank(userCode)) {
//            queryRule.addEqual("operateCode", userCode);
            criteriaUtiFactor.add(Restrictions.eq("operateCode", userCode));
        }
        criteriaUtiFactor.add(Restrictions.eq("feedbackFlag", "1"));
        criteriaUtiFactor.add(Restrictions.eq("correctionFlag", "0"));
//        queryRule.addEqual("feedbackFlag", "1");
//        queryRule.addEqual("correctionFlag", "0");
//        queryRule.addDescOrder("submitDate");
        Page<RiskReportSaleCorrection> pageSaleCorrection = riskReportSaleCorrectionRepository.findAll(criteriaUtiFactor, PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, "submitDate")));
//        pageSaleCorrection = databaseDao.findPage(RiskReportSaleCorrection.class, queryRule, pageNo, pageSize);
        for (RiskReportSaleCorrection riskReportSaleCorrection : pageSaleCorrection.getContent()) {
            UnfinishBusinessVo unfinishBusinessVo = new UnfinishBusinessVo();
            unfinishBusinessVo.setBusinessNo(riskReportSaleCorrection.getId().getArchivesNo());
            unfinishBusinessVo.setBusinessType("已反馈照片档案");
            unfinishBusinessVo.setSendtime(riskReportSaleCorrection.getSubmitDate());
            // 获取对应dcode表信息，进行照片类别翻译
            RiskDcode dcode = dataSourcesService.queryRiskDcodeForImage("riskReportTree002", "",
                    riskReportSaleCorrection.getId().getImageType(), "1");
            // unfinishBusinessVo.setExt1(dcode.getCodeCname());
            // unfinishBusinessVo.setExt2(riskReportSaleCorrection.getId().getImageName());
            unfinishBusinessVo.setHybrid(riskReportSaleCorrection.getId().getArchivesNo() + "-" + dcode.getCodeCname()
                    + "-" + riskReportSaleCorrection.getId().getImageName());
            unBusinessVoList.add(unfinishBusinessVo);
        }
        apiResponse.setData(unBusinessVoList);
        apiResponse.setStatusText(String.valueOf(pageSaleCorrection.getTotalElements()));
        return apiResponse;
    }

    public ApiResponse<List<UnfinishBusinessVo>> querycontrolReport(String userCode, String riskInsUnderFirFlag, String riskInsUnderSecFlag,
                                                                    int pageNo, int pageSize) {
        ApiResponse apiResponse = new ApiResponse();
        List<UnfinishBusinessVo> unBusinessVoList = new ArrayList<UnfinishBusinessVo>();
//		QueryRule queryRule = QueryRule.getInstance();
//		SaaAPIService saaAPIService = new SaaAPIServiceImpl();
//		String powerSQL = "";
        Page<RiskReportMain> page = null;
        try {
//			powerSQL = saaAPIService.addPower("riskcontrol", userCode, "riskins_query", "this_.comCode", "", "");
//			queryRule.addSql(powerSQL);
            String businessTypeName = "";
            Collection<String> validStatusList = new ArrayList<String>();
// 一级审核岗
            if ("1".equals(riskInsUnderFirFlag)) {
                validStatusList.add("4");
            }
// 二级审核岗
            if ("1".equals(riskInsUnderSecFlag)) {
                validStatusList.add("9");
            }
//			queryRule.addDescOrder("exploreDate");
            Criteria<RiskReportMain> criteriaUtiFactor = new Criteria<>();
            criteriaUtiFactor.add(Restrictions.in("underwriteFlag", validStatusList, false));
//			queryRule.addIn("underwriteFlag", validStatusList);
            businessTypeName = "待审核风控报告";
            page = riskReportMainRepository.findAll(criteriaUtiFactor, PageRequest.of(pageNo, pageSize));
//			page = databaseDao.findPage(RiskReportMain.class, queryRule, pageNo, pageSize);
// 组织返回前台数据
            for (RiskReportMain riskReportMain : page.getContent()) {
                UnfinishBusinessVo unfinishBusinessVo = new UnfinishBusinessVo();
                unfinishBusinessVo.setBusinessNo(riskReportMain.getRiskFileNo());
                unfinishBusinessVo.setRiskModel(riskReportMain.getRiskModel());
                unfinishBusinessVo.setSendtime(riskReportMain.getExploreDate());
                unfinishBusinessVo.setBusinessType(businessTypeName);
                unBusinessVoList.add(unfinishBusinessVo);
            }
            apiResponse.setData(unBusinessVoList);
            apiResponse.setStatusText(String.valueOf(page.getTotalElements()));
        } catch (Exception e) {
            LOGGER.info("代办业务查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            apiResponse.setStatus(0);
            apiResponse.setStatusText(e.getMessage());
            throw new RuntimeException("代办业务查询异常:" + e);
        }
        return apiResponse;
    }


    public ApiResponse<List<UnfinishBusinessVo>> queryphotoFilesNotAudited(String userCode, int pageNo, int pageSize) {
        ApiResponse<List<UnfinishBusinessVo>> apiResponse = new ApiResponse<>();
        List<UnfinishBusinessVo> unBusinessVoList = new ArrayList<UnfinishBusinessVo>();
        Criteria<RiskReportSaleMain> criteriaUtiScore = new Criteria<>();
//        QueryRule queryRule = QueryRule.getInstance();
//        SaaAPIService saaAPIService = new SaaAPIServiceImpl();

//        String powerSQL = "";
        try {
            if (StringUtils.isNotBlank(userCode)) {
                criteriaUtiScore.add(Restrictions.eq("checkUpCode", userCode));
            }
            criteriaUtiScore.add(Restrictions.eq("checkUpFlag", "0"));

//            queryRule.addEqual("checkUpFlag", "0");
//            queryRule.addDescOrder("exploreDate");
//            powerSQL = saaAPIService.addPower("riskcontrol", userCode, "risksale_examine", "this_.comCode", "", "");
//            queryRule.addSql(powerSQL);
            Page<RiskReportSaleMain> pageSale = riskReportSaleMainRepository.findAll(criteriaUtiScore, PageRequest.of(pageNo - 1, pageSize, Sort.by(Direction.DESC, "exploreDate")));

            for (RiskReportSaleMain riskReportSaleMain : pageSale.getContent()) {
                UnfinishBusinessVo unfinishBusinessVo = new UnfinishBusinessVo();
                unfinishBusinessVo.setBusinessNo(riskReportSaleMain.getArchivesNo());
                unfinishBusinessVo.setBusinessType("未审核照片档案");
                unfinishBusinessVo.setSendtime(riskReportSaleMain.getExploreDate());
                unBusinessVoList.add(unfinishBusinessVo);
            }
            apiResponse.setData(unBusinessVoList);
            apiResponse.setStatusText(String.valueOf(pageSale.getTotalPages()));
        } catch (Exception e) {
            LOGGER.info("代办业务查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            apiResponse.setStatus(1);
            apiResponse.setStatusText(e.getMessage());
            throw new RuntimeException("代办业务查询异常:" + e);
        }
        return apiResponse;

    }


    public ApiResponse<List<UnfinishBusinessVo>> querycallBack(String userCode, int pageNo, int pageSize) {
        ApiResponse<List<UnfinishBusinessVo>> apiResponse = new ApiResponse<>();
        List<UnfinishBusinessVo> unBusinessVoList = new ArrayList<UnfinishBusinessVo>();
//        QueryRule queryRule = QueryRule.getInstance();
        Criteria<RiskReportMain> criteriaUtiFactor = new Criteria<>();
//        SaaAPIService saaAPIService = new SaaAPIServiceImpl();
        String powerSQL = "";
        Page<RiskReportMain> page = null;
        try {
//            powerSQL = saaAPIService.addPower("riskcontrol", userCode, "riskins_query", "this_.comCode", "", "");
//            queryRule.addSql(powerSQL);
            String businessTypeName = "";
            if (StringUtils.isNotBlank(userCode)) {
//                queryRule.addEqual("operatorCode", userCode);
                criteriaUtiFactor.add(Restrictions.eq("operatorCode", userCode));
            }
//            queryRule.addDescOrder("exploreDate");
            criteriaUtiFactor.add(Restrictions.eq("underwriteFlag", "2"));
//            queryRule.addEqual("underwriteFlag", "2");
            businessTypeName = "打回风控报告";
//            page = databaseDao.findPage(RiskReportMain.class, queryRule, pageNo, pageSize);
            page = riskReportMainRepository.findAll(criteriaUtiFactor, PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, "exploreDate")));
            // 组织返回前台数据
            for (RiskReportMain riskReportMain : page.getContent()) {
                UnfinishBusinessVo unfinishBusinessVo = new UnfinishBusinessVo();
                unfinishBusinessVo.setBusinessNo(riskReportMain.getRiskFileNo());
                unfinishBusinessVo.setRiskModel(riskReportMain.getRiskModel());
                unfinishBusinessVo.setSendtime(riskReportMain.getExploreDate());
                unfinishBusinessVo.setBusinessType(businessTypeName);
                unBusinessVoList.add(unfinishBusinessVo);
            }
            apiResponse.setData(unBusinessVoList);
            apiResponse.setStatusText(String.valueOf(page.getTotalElements()));
        } catch (Exception e) {
            LOGGER.info("代办业务查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            apiResponse.setStatus(0);
            apiResponse.setStatusText(e.getMessage());
            throw new RuntimeException("代办业务查询异常:" + e);
        }
        return apiResponse;
    }
    /**
     * @Description: 火灾报告下载
     * @Author: wangwenjie
     * @Params: [fireNo, flag]
     * @Return: java.lang.String
     * @Date: 2019/7/23
     */
    public String downloadFireReport(String fireNo, String flag, UserInfo userInfo) throws Exception {
        try {
            RiskReportMain riskReportMain = this.queryRiskReportMain(fireNo);
            String riskModel = riskReportMain.getRiskModel();
            StringBuilder basePath = new StringBuilder(getClass().getResource("/").getPath()).append("com/picc/riskctrl/riskins/template");
//            basePath.append(getTemplatePath(flag, riskModel));
            RiskReportFireDanger riskReportFireDanger = riskReportMain.getRiskReportFireDangerList().get(0);
            //获取fire数据
            Map<String, Object> fireResult = WordUtils.getWordResultByReflect(riskReportFireDanger);
            //通知书结果集
            Map<String, Object> noticeResult = new HashMap<>();
            //通知书
            if (FIRE_NOTICE.equals(flag) || FIRE_PDF.equals(flag)) {
                noticeResult.put("insuredName", riskReportMain.getInsuredName());
//                noticeResult.put("exploreDate", DateUtils.getCustomDateString(riskReportMain.getExploreDate(), DateUtils.chineseDtFormat));
                noticeResult.put("addressDetail", riskReportMain.getAddressDetail());
                //翻译为对应省级机构
//                noticeResult.put("comCode", riskCheckService.translateProvinceNameByCheckComCode(riskReportMain.getComCode()));
//                noticeResult.put("currentDate", DateUtils.getCustomDateString(new Date(), DateUtils.chineseDtFormat));
            }

            FireDangerProfessionTranslateEnum[] professionTranslateEnums = FireDangerProfessionTranslateEnum.values();
            Iterator<Map.Entry<String, Object>> iterator = fireResult.entrySet().iterator();
            //专业版和简化版翻译
            if (FIRE_PROFESSION_MODEL.equals(riskModel)) {
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    for (FireDangerProfessionTranslateEnum e : professionTranslateEnums) {
                        if (e.name().equals(entry.getKey() + "_" + entry.getValue())) {
                            fireResult.put(entry.getKey(), e.getProfessionValue());
                            break;
                        }
                    }
                }
            } else if (FIRE_SIMPLE_MODEL.equals(riskModel)) {
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    for (FireDangerProfessionTranslateEnum e : professionTranslateEnums) {
                        if (e.name().equals(entry.getKey() + "_" + entry.getValue())) {
                            fireResult.put(entry.getKey(), e.getSimpleValue());
                            break;
                        }
                    }
                }
            }

            //翻译多选字段
            String[] mulitpleSelectionFields = {"dangerProcessPart", "fireFacility", "fireWaterSupply", "autoExtinguishSystem"};
            for (String field : mulitpleSelectionFields) {
                String value = (String) fireResult.get(field);
                /*
                    自动灭火系统、自动报警系统 翻译为 []的问题
                    无值为[] 有值为对应单选项[正常，不正常]
                 */
                if (FIRE_SIMPLE_MODEL.equals(riskModel) && "autoExtinguishSystem".equals(field)) {
                    if ("[]".equals(value)) {
                        fireResult.put(field, "");
                    } else {
                        fireResult.put(field, value);
                    }
                    continue;
                }
                if(StringUtils.isNotBlank(value)){
                    //["B","C"] 截掉前后中括号 "[]"
                    value = value.substring(1, value.length() - 1);
                    if ("".equals(value)) {
                        fireResult.put(field, value);
                        continue;
                    }
                    String[] subValues = value.split(",");
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0, len = subValues.length; i < len; i++) {
                        String _value = subValues[i];
                        //截掉引号
                        _value = _value.substring(1, _value.length() - 1);
                        if (FIRE_PROFESSION_MODEL.equals(riskModel)) {
                            stringBuilder.append(FireDangerProfessionTranslateEnum.valueOf(field + "_" + _value).getProfessionValue());
                        } else if (FIRE_SIMPLE_MODEL.equals(riskModel)) {
                            stringBuilder.append(FireDangerProfessionTranslateEnum.valueOf(field + "_" + _value).getSimpleValue());
                        }
                        if (i != len - 1) {
                            stringBuilder.append(",");
                        }
                    }
                    fireResult.put(field, stringBuilder.toString());
                }
            }

            //doc
            if (FIRE_DOC.equals(flag) || FIRE_PDF.equals(flag)) {
                fireResult.put("insuredName", riskReportMain.getInsuredName());
                fireResult.put("addressDetail", riskReportMain.getAddressDetail());
//                fireResult.put("businessSource", queryCodeCName("TradeCode", riskReportMain.getBusinessSource(), userInfo.getUserCode(), userInfo.getComCode(), "QBB"));
                fireResult.put("businessClass", riskReportMain.getBusinessClass());
//                fireResult.put("unitNature", translateUnitNature(riskReportMain.getUnitNature()));
            }

            String path = "";
            String prefix = "downloadFile/";
            Map<String, String> translateTableData = null;
//            Map<String, String> translateTableData = generateWordTableData(riskReportFireDanger, riskModel);
            List<RiskReportClaim> riskReportClaimList = riskReportMain.getRiskReportClaimList();
            List<Map<String, String>> riskClaims = null;
//            List<Map<String, String>> riskClaims = generateRiskClaimMapData(riskReportClaimList);

            if (FIRE_NOTICE.equals(flag)) {
                path = prefix + fireNo + "_notice.doc";
                WordUtils.uploadFileByFtp(path, basePath.toString(), noticeResult);
                WordUtils.insertTable("整改建议", path, translateTableData);
            } else if (FIRE_DOC.equals(flag)) {
                path = prefix + fireNo + ".doc";
                WordUtils.uploadFileByFtp(path, basePath.toString(), fireResult);
                if (riskClaims.size() > 0) {
                    for (Map<String, String> riskClaim : riskClaims) {
                        WordUtils.insertTableHasMerge("历史损失记录", path, riskClaim);
                    }
                } else {
                    WordUtils.insertTableHasMerge("历史损失记录", path, null);
                }
            } else if (FIRE_PDF.equals(flag)) {
                noticeResult.putAll(fireResult);
                //转换pdf所需模板必须为doc文档，docx会有格式问题
                String _path = fireNo + "_pdf.doc";
                path = fireNo + ".pdf";
                WordUtils.uploadFileByFtp(prefix + _path, basePath.toString(), noticeResult);
                WordUtils.insertTable("整改建议", prefix + _path, translateTableData);
                if (riskClaims.size() > 0) {
                    for (Map<String, String> riskClaim : riskClaims) {
                        WordUtils.insertTableHasMerge("历史损失记录", prefix + _path, riskClaim);
                    }
                } else {
                    WordUtils.insertTableHasMerge("历史损失记录", prefix + _path, null);
                }
                WordUtils.openOfficeConvertPDF2(_path, path);
                path = prefix + path;
            }
            return path;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    
    
}
