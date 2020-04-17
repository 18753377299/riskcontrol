package com.picc.riskctrl.riskcheck.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.picc.riskctrl.common.RiskControlConst;
import com.picc.riskctrl.common.dao.*;
import com.picc.riskctrl.common.po.*;
import com.picc.riskctrl.common.schema.PrpDcompanyFk;
import com.picc.riskctrl.common.schema.UtiWeight;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.utils.BoCopyUtil;
import com.picc.riskctrl.common.utils.WordUtils;
import com.picc.riskctrl.dataquery.util.BasicInfor;
import com.picc.riskctrl.dataquery.util.CorporateRadarQueryRule;
import com.picc.riskctrl.dataquery.util.CorporateRadarResult;
import com.picc.riskctrl.riskcheck.dao.RiskCheckDao;
import com.picc.riskctrl.riskcheck.dao.RiskCheckImageDao;
import com.picc.riskctrl.riskcheck.dao.RiskCheckMainDao;
import com.picc.riskctrl.riskcheck.dao.RiskCheckMainLogDao;
import com.picc.riskctrl.riskcheck.model.WordTranslateEnmu;
import com.picc.riskctrl.riskcheck.po.*;
import com.picc.riskctrl.riskcheck.template.factory.NewRiskCheckIntactTemplateFactory;
import com.picc.riskctrl.riskcheck.template.factory.NewRiskCheckSimpleTemplateFactory;
import com.picc.riskctrl.riskcheck.template.factory.TemplateFactory;
import com.picc.riskctrl.riskcheck.template.newBuilder.NewTemplateInfo;
import com.picc.riskctrl.riskcheck.vo.*;
import com.picc.riskctrl.riskinfo.claim.dao.RiskDcodeDao;
import com.picc.riskctrl.riskins.po.RiskReportMain;
import com.picc.riskctrl.riskins.service.RiskInsService;
import com.picc.riskctrl.riskins.service.spring.DisastersScore;
import com.picc.riskctrl.riskins.vo.ImageConfigVo;
import com.picc.riskctrl.riskins.vo.ImagePreviewConfigVo;
import com.picc.riskctrl.riskins.vo.ImagePreviewExtraVo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.easy.excel.ExcelContext;
import org.easy.excel.result.ExcelImportResult;
import org.easy.excel.util.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class RiskCheckService {

    public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");
    //企业失信信息列表数据产品代码
    private static final String[] PRODUCTCODE_LIST = {"P0090000001", "P0090000004", "P0090000005", "P0090000007", "P0090000008", "P0090000010", "P0090000037", "P0090000047", "P0090000049", "P0090000052", "P0090000054", "P0090000055", "P0090000057", "P0090000073"};

    //企业失信信息详情数据产品代码
    private static final String[] PRODUCTCODE_DETAIL = {"P0090000002", "P0090000003", "P0090000006", "P0090000009", "P0090000048", "P0090000050", "P0090000053", "P0090000056"};

    private static final List<String> PRODUCTCODELIST;
    private static final List<String> PRODUCTCODE_DETAILLIST;

    static {
        PRODUCTCODELIST = Arrays.asList(PRODUCTCODE_LIST);
        PRODUCTCODE_DETAILLIST = Arrays.asList(PRODUCTCODE_DETAIL);
    }

    @Autowired
    private RiskCheckDao riskCheckDao;
    @Autowired
    private RiskDcodeDao riskDcodeDao;
    @Autowired
    private RiskCheckImageDao riskCheckImageDao;
    @Autowired
    private RiskCheckMainDao riskCheckMainDao;
    @Autowired
    private DataSourcesService dataSourcesService;
    @Autowired
    private RiskCheckMainLogDao riskCheckMainLogDao;
    @Autowired
    private UtilScoreRepository utilScoreRepository;
    @Autowired
    private UtiFactorDao utiFactorDao;
    @Autowired
    ExcelContext excelContext;
    @Autowired
    private UtiScoreDao utiScoreDao;
    @Autowired
    private UtiFormulaDao utiFormulaDao;
    @Autowired
    private UtiWeightDao utiWeightDao;
    @Autowired
    private UtiHighlightRiskDao utiHighlightRiskDao;
    @Autowired
    private RiskInsService riskInsService;

    /**
     * @return RiskCheckMainVo @日期：20200114
     * @功能:根据巡检单号查询巡检详细信息
     * @author 周东旭
     **/
    public RiskCheckMainVo queryRiskCheckByCheckNo(String riskCheckNo) {
        Assert.hasText(riskCheckNo, "请输入巡检报告编号");
        RiskCheckMainVo riskCheckMainVo = new RiskCheckMainVo();
        try {
            RiskCheckMain riskCheckMain = riskCheckDao.findById(riskCheckNo).orElse(null);
            if (riskCheckMain != null) {
                BoCopyUtil.convert(riskCheckMain, riskCheckMainVo, null, null, null);
            }
        } catch (Exception e) {
            LOGGER.error("根据编号查询巡检报告出错！", e);
            throw new RuntimeException("根据编号查询巡检报告出错！", e);
        }
        return riskCheckMainVo;
    }

    /**
     * 根据checkModel来查询riskDecode
     *
     * @author 周东旭 @日期： 20200114
     */
    public List<RiskDcode> queryRiskDcodeByCheckModel(String checkModel) {
        List<RiskDcode> riskDcodeList = null;
        String codeType = "riskCheckFree" + checkModel;
        Specification<RiskDcode> spec = new Specification<RiskDcode>() {

            @Override
            public Predicate toPredicate(Root<RiskDcode> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("id").get("codeType"), codeType));
                Predicate[] arr = new Predicate[list.size()];
                // Predicate p =cb.and(list1.toArray(arr));
                return criteriaBuilder.and(list.toArray(arr));
            }
        };
        riskDcodeList = this.riskDcodeDao.findAll(spec, Sort.by(Sort.Direction.ASC, "codeEname"));
        return riskDcodeList;
    }

    /**
     * 根据巡检报告编号获取报告照片
     *
     * @param riskDcodeList
     * @param riskCheckNo   巡检报告编号
     * @param contextPath
     * @return RiskDcodeList
     * @author 周东旭 @日期： 20200114
     */
    public List<RiskDcode> queryRiskCheckImage(List<RiskDcode> riskDcodeList, String riskCheckNo, String contextPath) {
        try {
            Specification<RiskCheckImage> spec = new Specification<RiskCheckImage>() {

                @Override
                public Predicate toPredicate(Root<RiskCheckImage> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    List<Predicate> list = new ArrayList<>();
                    list.add(criteriaBuilder.equal(root.get("riskCheckNo"), riskCheckNo));
                    Predicate[] arr = new Predicate[list.size()];
                    // Predicate p =cb.and(list1.toArray(arr));
                    return criteriaBuilder.and(list.toArray(arr));
                }
            };
            List<RiskCheckImage> riskCheckImageList = this.riskCheckImageDao.findAll(spec);
            String ip = "";
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
            // ResourceBundle bundle = ResourceBundle.getBundle("config.savePath",
            // Locale.getDefault());
            // String savePort = bundle.getString("savePort");
            // String savePath = "http" + "://" + ip + ":" + savePort;
            String savePath = "http://10.10.2.241:5001";
            for (RiskDcode riskDcodeTem : riskDcodeList) {
                ImageConfigVo imageConfigVo = new ImageConfigVo();
                List<String> urls = new ArrayList<String>();
                List<ImagePreviewConfigVo> configs = new ArrayList<>();
                String treeId = riskDcodeTem.getCodeEname();
                for (RiskCheckImage riskCheckImage : riskCheckImageList) {
                    if (riskCheckImage.getImagType().equals(treeId)) {
                        ImagePreviewConfigVo imagePreviewConfigVo = new ImagePreviewConfigVo();
                        ImagePreviewExtraVo ImagePreviewExtraVo = new ImagePreviewExtraVo();
                        ImagePreviewExtraVo.setChildPath(riskCheckImage.getImageUrl());
                        ImagePreviewExtraVo.setImageName(riskCheckImage.getImageName());
                        if (StringUtils.isNotBlank(riskCheckImage.getImageUrl())) {
                            if (riskCheckImage.getImageUrl().indexOf("http") > -1) {
                                urls.add(riskCheckImage.getImageUrl());
                            } else {
                                urls.add(savePath + riskCheckImage.getImageUrl());
                            }
                            ImagePreviewExtraVo.setImagePath(riskCheckImage.getImageUrl());
                            imagePreviewConfigVo.setRemark(riskCheckImage.getRemark());
                            imagePreviewConfigVo.setCaption(riskCheckImage.getImageName());
                            imagePreviewConfigVo.setLocalPath(riskCheckImage.getImageUrl());
                            imagePreviewConfigVo.setUrl(contextPath + "/riskins/image/imageRemove");
                            imagePreviewConfigVo.setDownloadUrl(contextPath + "/riskins/image/imageDownLoad;"
                                    + riskCheckImage.getImageUrl() + ";" + riskCheckImage.getImageName());
                            imagePreviewConfigVo.setExtra(ImagePreviewExtraVo);
                            configs.add(imagePreviewConfigVo);
                        }
                    }
                }
                imageConfigVo.setUrls(urls);
                imageConfigVo.setConfigs(configs);
                riskDcodeTem.setConfigList(imageConfigVo);
            }
        } catch (Exception e) {
            LOGGER.info("查询RiskCheckImage表数据异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询RiskCheckImage表数据异常:" + e);
        }
        return riskDcodeList;
    }

    /**
     * @param riskCheckRequestVo
     * @param userInfo
     * @功能 汛期检查查询
     * @author @日期：2019-03-13
     * @修改
     */
    @SuppressWarnings(value = {"rawtypes", "unchecked", "unused"})
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public RiskCheckResponseVo checkQuery(RiskCheckRequestVo riskCheckRequestVo, UserInfo userInfo) {

        RiskCheckResponseVo riskCheckResponseVo = null;
        try {
            int pageNo = riskCheckRequestVo.getPageNo();
            int pageSize = riskCheckRequestVo.getPageSize();
            String userCodeInfo = "";
            String comCodeInfo = "";

            Specification<RiskCheckMain> spec = new Specification<RiskCheckMain>() {

                @Override
                public Predicate toPredicate(Root<RiskCheckMain> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                    List<Predicate> list = new ArrayList<>();
                    String userCodeInfo = "";
                    String comCodeInfo = "";

                    if (userInfo != null) {
                        userCodeInfo = userInfo.getUserCode();
                        comCodeInfo = userInfo.getComCode();
                    }
                    RiskCheckMainQueryVo riskCheckMainQueryVo = riskCheckRequestVo.getRiskCheckMainQueryVo();
                    if (riskCheckMainQueryVo != null) {
                        // 巡检编号
                        String riskCheckNo = riskCheckMainQueryVo.getRiskCheckNo();
                        if (StringUtils.isNotBlank(riskCheckNo)) {
                            list.add(cb.equal(root.get("riskCheckNo"), riskCheckNo.trim()));
                        }
                        // 被保险人
                        String insuredName = riskCheckMainQueryVo.getInsuredName();
                        if (StringUtils.isNotBlank(insuredName)) {
                            Predicate like1 = cb.like(root.get("insuredCode"), "%" + insuredName + "%");
                            Predicate like2 = cb.like(root.get("insuredName"), "%" + insuredName + "%");
                            list.add(cb.or(like1, like2));
                        }
                        // 被保险人代码
                        String insuredCode = riskCheckMainQueryVo.getInsuredCode();
                        if (StringUtils.isNotBlank(insuredCode)) {
                            list.add(cb.equal(root.get("insuredCode"), insuredCode.trim()));
                        }
                        // 被保险人类型
                        String insuredType = riskCheckMainQueryVo.getInsuredType();
                        if (StringUtils.isNotBlank(insuredType)) {
                            list.add(cb.equal(root.get("insuredType"), insuredType.trim()));
                        }
                        // 巡检人
                        String checker = riskCheckMainQueryVo.getChecker();
                        if (StringUtils.isNotBlank(checker)) {
                            list.add(cb.equal(root.get("checker"), checker.trim()));
                        }
                        // 归属机构
                        String comCode = riskCheckMainQueryVo.getComCode();
                        if (StringUtils.isNotBlank(comCode)) {
                            PrpDcompanyFk prpDcompanyFk = new PrpDcompanyFk();

                            prpDcompanyFk = riskCheckMainDao.viewPrpDcompanyByComCode(comCode);

                            if (prpDcompanyFk != null) {
                                String upperPath = prpDcompanyFk.getUpperPath();
                                if (StringUtils.isNotBlank(upperPath)) {
                                    upperPath = upperPath.trim();
                                    // upperPath = "31000000,21029913";
                                    if ("00".equals(
                                            upperPath.substring(upperPath.length() - 8, upperPath.length() - 6))) {
                                    } else if (upperPath.length() == 17) {
                                        if ("2102,3302,3502,3702,4403".indexOf(upperPath.substring(9, 13)) > -1) {
                                            list.add(cb.equal(cb.substring(root.get("comCode"), 1, 4),
                                                    upperPath.substring(9, 13)));
                                        } else {
                                            if ("21".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 4), "2102"));
                                            } else if ("33".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 4), "3302"));
                                            } else if ("35".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 4), "3502"));
                                            } else if ("37".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 4), "3702"));
                                            } else if ("44".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 4), "4403"));
                                            } else {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 1, 2),
                                                        upperPath.substring(9, 11)));
                                            }
                                        }
                                    } else if (upperPath.length() > 17) {

                                        List<PrpDcompanyFk> prpDcompanyFkList = riskCheckMainDao
                                                .queryPrpDcompanyByUpperPath(upperPath.substring(0, 26));

                                        List<String> comCodeList = new ArrayList();
                                        if (null != prpDcompanyFkList && prpDcompanyFkList.size() > 0) {
                                            for (int i = 0; i < prpDcompanyFkList.size(); i++) {
                                                comCodeList.add(prpDcompanyFkList.get(i).getComCode());
                                            }
                                        }
                                        list.add(root.get("comCode").in(comCodeList));
                                    }
                                }
                            } else {
                                list.add(cb.equal(root.get("comCode"), comCode));
                            }
                        }
                    }
                    // 巡检起期
                    Date checkStartDate = riskCheckMainQueryVo.getCheckStartDate();
                    // 巡检止期
                    Date checkEndDate = riskCheckMainQueryVo.getCheckEndDate();

                    if (checkStartDate != null || checkEndDate != null) {
                        if (checkStartDate != null && checkEndDate != null) {
                            list.add(cb.between(root.get("checkDate"), checkStartDate, checkEndDate));
                        } else {
                            if (checkStartDate != null) {
                                list.add(cb.greaterThanOrEqualTo(root.get("checkDate"), checkStartDate));
                            } else {
                                list.add(cb.lessThanOrEqualTo(root.get("checkDate"), checkEndDate));
                            }
                        }
                    }
                    // 巡检报告类型
                    String checkModel = riskCheckMainQueryVo.getCheckModel();
                    if (StringUtils.isNotBlank(checkModel)) {
                        list.add(cb.equal(root.get("checkModel"), checkModel.trim()));
                    }
                    // 巡检机构
                    String CheckComCode = riskCheckMainQueryVo.getCheckComCode();
                    if (StringUtils.isNotBlank(CheckComCode)) {
                        list.add(cb.equal(root.get("checkComCode"), CheckComCode.trim()));
                    }
                    // 巡检地址
                    String addressDetail = riskCheckMainQueryVo.getAddressDetail();
                    if (StringUtils.isNotBlank(addressDetail)) {
                        list.add(cb.like(root.get("addressDetail"), addressDetail.trim() + "%"));
                    }

                    // 审核状态
                    String[] underwriteFlag = riskCheckMainQueryVo.getUnderwriteFlag();
                    // 审核查询若无标志位，则查询出待审核的风控报告
                    List<String> underwriteFlagList = new ArrayList<>();
                    if (null != underwriteFlag && underwriteFlag.length > 0) {
                        for (int i = 0; i < underwriteFlag.length; i++) {
                            underwriteFlagList.add(underwriteFlag[i]);
                        }
                    }
                    if (null != underwriteFlagList && underwriteFlagList.size() > 0) {
                        list.add(root.get("underwriteFlag").in(underwriteFlagList));
                    }

                    // 先不写***************************************
                    // if (!"1".equals(riskCheckRequestVo.getOutSystemFlag())) {
                    // //权限校验
                    // SaaAPIService saaAPIService = new SaaAPIServiceImpl();
                    // try {
                    // String powerSQL = saaAPIService.addPower("riskcontrol", userCodeInfo,
                    // "riskins_query",
                    // "this_.comCode", "", "");
                    // queryRule.addSql(powerSQL);
                    // } catch (Exception e) {
                    // LOGGER.info("addPower执行异常：" + e.getMessage(), e);
                    // e.printStackTrace();
                    // throw new RuntimeException("addPower执行异常:" + e);
                    // }
                    // }

                    Predicate[] arr = new Predicate[list.size()];
                    return cb.and(list.toArray(arr));
                }
            };
            Sort sort = null;
            if (riskCheckRequestVo.getOrderColumn() != null) {
                // 获取排序字段
                String orderColumn = riskCheckRequestVo.getOrderColumn().trim();
                // 获取升序/降序 标志位
                String orderType = riskCheckRequestVo.getOrderType().trim();
                if (StringUtils.isNotBlank(orderColumn)) {
                    // 根据巡检日期排序
                    if ("checkDate".equals(orderColumn)) {
                        if (StringUtils.isNotBlank(orderType)) {
                            if ("false".equals(orderType)) {
                                // 升序
                                sort = Sort.by(Sort.Direction.ASC, orderColumn);
                            } else if ("true".equals(orderType)) {
                                // 降序
                                sort = Sort.by(Sort.Direction.DESC, orderColumn);
                            }
                        }
                    } else if ("riskCheckNo".equals(orderColumn)) {
                        // 按照巡检编号排序
                        sort = Sort.by(Sort.Direction.ASC, orderColumn);
                    }
                }
            }
            // 分页加排序
            Page page = this.riskCheckMainDao.findAll(spec, PageRequest.of(pageNo - 1, pageSize, sort));

            riskCheckResponseVo = new RiskCheckResponseVo();
            if (page != null) {
                Page pageNew = null;
                List<RiskCheckMain> riskCheckMainList = page.getContent();
                List<RiskCheckMainResponseVo> riskCheckListNew = new ArrayList<RiskCheckMainResponseVo>();
                for (RiskCheckMain r : riskCheckMainList) {
                    RiskCheckMainResponseVo rNew = new RiskCheckMainResponseVo();
                    BeanUtils.copyProperties(r, rNew);
                    // 如果没有设定String[]数组的长度 会报空指针
                    // String[] Str = new String[1];
                    // Str[0] = r.getUnderwriteFlag();
                    // rNew.setUnderwriteFlag(Str);
                    // rNew.getUnderwriteFlag()[0] = r.getUnderwriteFlag();
                    // 各风险值赋值
                    if (r.getRiskCheckAssessList() != null && r.getRiskCheckAssessList().size() > 0) {
                        BeanUtils.copyProperties(r.getRiskCheckAssessList().get(0), rNew);
                    }
                    riskCheckListNew.add(rNew);
                }
                // 设置数据
                riskCheckResponseVo.setDataList(riskCheckListNew);
                // 设置数据总条数
                riskCheckResponseVo.setTotalCount(page.getTotalElements());
                // 设置总页数
                riskCheckResponseVo.setTotalPage(page.getTotalPages());
            }
            List dataList = riskCheckResponseVo.getDataList();
            Map<String, RiskDcode> underwriteFlagMap = null;
            if (dataList.size() > 0) {
                underwriteFlagMap = dataSourcesService.queryRiskDcode("checkUnderwriteFlag");
            }
            // 代码转中文
            for (int i = 0; i < dataList.size(); i++) {
                RiskCheckMainResponseVo mainVo = new RiskCheckMainResponseVo();
                // 拷贝vo中的属性到实体类
                BeanUtils.copyProperties(dataList.get(i), mainVo);
                // 归属机构中文翻译
                if (StringUtils.isNotBlank(mainVo.getComCode())) {
                    mainVo.setComCodeCName(mainVo.getComCode());
                }
                // 审核状态中文翻译:T 未处理 1已完成
                if (StringUtils.isNotBlank(mainVo.getUnderwriteFlag())) {
                    RiskDcode riskDcodeUnderwriteFlag = underwriteFlagMap.get(mainVo.getUnderwriteFlag());
                    if (riskDcodeUnderwriteFlag != null) {
                        mainVo.setUnderwriteFlag(riskDcodeUnderwriteFlag.getCodeCname());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("查询出现异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询出现异常:" + e);
        }

        return riskCheckResponseVo;
    }

    /**
     * 跟新图片信息
     *
     * @param riskDcodeList
     * @param riskCheckNo
     * @author 周东旭
     * @日期 20200115
     */
    public List<RiskCheckImageVo> getRiskCheckImageList(List<RiskDcode> riskDcodeList, String riskCheckNo) {
        List<RiskCheckImageVo> riskCheckImages = new ArrayList<RiskCheckImageVo>(0);
        if (riskDcodeList != null && riskDcodeList.size() != 0) {
            for (RiskDcode riskDcode : riskDcodeList) {
                if (riskDcode.getConfigList() != null && riskDcode.getConfigList().getConfigs().size() != 0) {
                    List<ImagePreviewConfigVo> configs = riskDcode.getConfigList().getConfigs();
                    for (ImagePreviewConfigVo imagePreviewConfigVo : configs) {
                        RiskCheckImageVo riskCheckImage = new RiskCheckImageVo();
                        riskCheckImage.setImageId("");
                        riskCheckImage.setImageUrl(imagePreviewConfigVo.getLocalPath());
                        riskCheckImage.setImageName(imagePreviewConfigVo.getExtra().getImageName());
                        riskCheckImage.setRemark(imagePreviewConfigVo.getRemark());
                        riskCheckImage.setRiskCheckNo(riskCheckNo);
                        riskCheckImage.setImagType(riskDcode.getCodeEname());
                        riskCheckImage.setImagTypeCName(imagePreviewConfigVo.getCaption());
                        riskCheckImages.add(riskCheckImage);
                    }
                }
            }
        }
        return riskCheckImages;
    }

    /**
     * 汛期报告暂存
     *
     * @param userInfo
     * @param riskCheckRequestVo
     * @return int
     * @author 周东旭
     * @日期 20200115
     */
    public int temporarySave(UserInfo userInfo, RiskCheckRequestVo riskCheckRequestVo) {
        int api = -1;
        try {
            RiskCheckMainVo riskCheckMainVo = riskCheckRequestVo.getRiskCheckMainVo();
            RiskCheckMain riskCheckMain = new RiskCheckMain();
            if (StringUtils.isNotBlank(riskCheckMainVo.getRiskCheckNo())) {
                Optional<RiskCheckMain> riskCheckMainone = riskCheckDao.findById(riskCheckMainVo.getRiskCheckNo());
                if (riskCheckMainone.isPresent()) {
                    riskCheckMain = riskCheckMainone.get();
                }
            }
//			Datas.copySimpleObjectToTargetFromSource(riskCheckMain, riskCheckMainVo);
            BeanUtils.copyProperties(riskCheckMainVo, riskCheckMain);
            riskCheckMain.setRiskCheckAssessList((List<RiskCheckAssess>) com.picc.riskctrl.common.utils.BeanUtils
                    .convert(riskCheckRequestVo.getRiskCheckAssessList(), RiskCheckAssess.class));
            riskCheckMain.setRiskCheckImageList((List<RiskCheckImage>) com.picc.riskctrl.common.utils.BeanUtils
                    .convert(riskCheckRequestVo.getRiskCheckImageList(), RiskCheckImage.class));
            riskCheckMain.setRiskCheckVentureList((List<RiskCheckVenture>) com.picc.riskctrl.common.utils.BeanUtils
                    .convert(riskCheckRequestVo.getRiskCheckVentureList(), RiskCheckVenture.class));
            // 为大对象的某特定字段赋值
            com.picc.riskctrl.common.utils.BeanUtils.setValueforSpecificField(riskCheckMain, "RiskCheckNo",
                    riskCheckMain.getRiskCheckNo());
            riskCheckMain.setUnderwriteFlag("T");
            this.riskCheckDao.save(riskCheckMain);
        } catch (Exception e) {
            LOGGER.info("暂存巡检报告异常:" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("暂存巡检报告异常:" + e);
        }
        api = 0;
        return api;
    }

    /**
     * @param riskCheckNo
     * @return message
     * @throws Exception
     * @功能 巡检报告删除
     * @author 周东旭
     * @日期 20200116
     * @修改记录
     */
    public String deleteRiskInfoByRiskCheckNo(String riskCheckNo) {
        String message = null;

        try {
            if (riskCheckNo != null && StringUtils.isNotBlank(riskCheckNo)) {
                this.riskCheckDao.deleteById(riskCheckNo);
                message = "success";
            } else {
                message = "none";
            }
        } catch (Exception e) {
            LOGGER.info("巡检报告删除异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("巡检报告删除异常:" + e);
        }
        return message;
    }

    /**
     * @param riskCheckNo
     * @return
     * @功能 巡检报告编号查询
     * @author 孙凯
     * @日期 2019-03-18
     * @修改记录
     */
    public RiskCheckMain queryRiskCheckNo(String riskCheckNo) throws Exception {
        RiskCheckMain riskCheckMain = null;
        RiskCheckMain checkMain = new RiskCheckMain();
        try {
            if (StringUtils.isNotBlank(riskCheckNo)) {
                riskCheckMain = riskCheckMainDao.getOne(riskCheckNo);

                BeanUtils.copyProperties(riskCheckMain, checkMain);
                if (riskCheckMain != null) {
                    List<RiskCheckAssess> riskCheckAssessList = new ArrayList<RiskCheckAssess>();
                    for (RiskCheckAssess riskCheckAddress : riskCheckMain.getRiskCheckAssessList()) {
                        RiskCheckAssess addressTemp = new RiskCheckAssess();
                        BeanUtils.copyProperties(riskCheckAddress, addressTemp);
                        riskCheckAssessList.add(addressTemp);
                        checkMain.setRiskCheckAssessList(riskCheckAssessList);
                    }
                    List<RiskCheckImage> riskCheckImageList = new ArrayList<RiskCheckImage>();
                    for (RiskCheckImage riskCheckImage : riskCheckMain.getRiskCheckImageList()) {
                        RiskCheckImage temp = new RiskCheckImage();
                        BeanUtils.copyProperties(riskCheckImage, temp);
                        riskCheckImageList.add(temp);
                        checkMain.setRiskCheckImageList(riskCheckImageList);
                    }
                    List<RiskCheckVenture> riskCheckVentureList = new ArrayList<RiskCheckVenture>();
                    for (RiskCheckVenture riskCheckVenture : riskCheckMain.getRiskCheckVentureList()) {
                        RiskCheckVenture temp = new RiskCheckVenture();
                        BeanUtils.copyProperties(riskCheckVenture, temp);
                        riskCheckVentureList.add(temp);
                        checkMain.setRiskCheckVentureList(riskCheckVentureList);
                    }
                } else {
                    checkMain = riskCheckMain;
                }
            } else {
                throw new RuntimeException("巡检编号不能为空！");
            }
        } catch (Exception e) {
            LOGGER.info("查询巡检报告信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询巡检报告信息异常:" + e);
        }

        return checkMain;
    }

    // 根据经纬度获取暴雨分数
    public Object getRainScoreByPoint(String pointX, String pointY) {
        Object ajaxResult = null;
        try {
            // 获取iserver的地址
            // ResourceBundle bundle = ResourceBundle.getBundle("config.map",
            // Locale.getDefault());
            // String serverName = bundle.getString("serverName");
            String serverName = "http://10.10.2.241:5001/";

            String rainScore = "";
            String url = serverName
                    + "iserver/services/data-FXDT/rest/data/datasources/china/datasets/rain_hazard_scale_1km/gridValue.json";
            String param = "x=" + pointX + "&y=" + pointY;
            String resJson = doGet(url, param);
            GridValue gridValue = new GridValue();
            if (StringUtils.isNotBlank(resJson)) {
                gridValue = JSON.parseObject(resJson.toString(), GridValue.class);
                BigDecimal gridValueBd = gridValue.getValue();
                // 假如值小于0，则置为0,保留两位小数
                if (gridValueBd.compareTo(new BigDecimal(0)) == -1) {
                    gridValueBd = BigDecimal.ZERO;
                }
                rainScore = gridValueBd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            } else {
                System.out.println("请求的url:" + url + "?" + param);
                rainScore = BigDecimal.ZERO.toString();
            }
            ajaxResult = rainScore;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("获取暴雨分数异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("获取暴雨分数异常:" + e);
        }
        return ajaxResult;
    }

    /**
     * 执行一个带参数的HTTP GET请求，返回请求响应的JSON字符串
     *
     * @param url 请求的URL地址
     * @return 返回请求响应的JSON字符串
     */
    public static String doGet(String url, String param) {
        String forObject = "";
        try {
            RestTemplate restTemplate = new RestTemplate();
            // 设置restemplate编码为utf-8
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            // 请求
            forObject = restTemplate.getForEntity(url + "?" + param, String.class).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("执行HTTP Get请求" + url + "时，发生异常！", e);
            throw new RuntimeException("执行HTTP Get请求" + url + "时，发生异常！", e);
        }
        return forObject;
    }

    /**
     * @param logId
     * @return String
     * @Description 根据id删除巡检任务报告
     * @Author 孙凯
     * @Date 2019/5/10
     */
    public String deleteRiskCheckLogById(Integer logId) {
        String message = "";
        try {
            if (logId != null) {
                riskCheckMainLogDao.deleteById(logId);
            }
            message = "success";
        } catch (Exception e) {
            LOGGER.info("巡检任务报告单删除异常：" + e.getMessage(), e);
            message = "error";
            e.printStackTrace();
        }
        return message;
    }

    /**
     * @param UserInfo           用户信息
     * @param RiskCheckRequestVo 巡检请求Vo类
     * @return AjaxResult
     * @throws Exception
     * @功能：暂存巡检报告基本信息页面并生成单号 @修改记录：
     */
    public int saveBasicInfo(UserInfo userInfo, RiskCheckRequestVo riskCheckRequestVo) {
        int response = 0;
        try {
            RiskCheckMainVo riskCheckMainVo = riskCheckRequestVo.getRiskCheckMainVo();
            riskCheckMainVo.setComCode(userInfo.getComCode());

            // 设置暂存标志位
            riskCheckMainVo.setUnderwriteFlag("T");

            riskCheckMainVo.setMadeDate(new Date());

            RiskCheckMain riskCheckMain = new RiskCheckMain();

            BeanUtils.copyProperties(riskCheckMainVo, riskCheckMain);

            riskCheckDao.save(riskCheckMain);

        } catch (Exception e) {
            LOGGER.info("生成巡检报告异常:" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("生成巡检报告异常:" + e);
        }
        response = 1;

        return response;
    }

    /**
     * @param riskCheckMainQueryVo
     * @return Map<String,Object>
     * @功能: 更新log表信息
     * @author 孙凯 @日期：2019-03-18
     */
    public Map<String,Object> updateCheckMainLog(RiskCheckMainQueryVo riskCheckMainQueryVo) {
    	Map<String,Object> map = new HashMap<String,Object>();
        // 审核状态
        String[] checkerStatus = riskCheckMainQueryVo.getCheckerStatus();
        // 审核查询若无标志位，则查询出待审核的风控报告
        List<Integer> checkerStatusList = new ArrayList<>();
        if (null != checkerStatus && checkerStatus.length > 0) {
            for (int i = 0; i < checkerStatus.length; i++) {
                checkerStatusList.add(Integer.parseInt(checkerStatus[i]));
            }
        }
        List<RiskCheckMainLog> riskCheckMainLogs = null;
        if (null != checkerStatusList && checkerStatusList.size() > 0) {
            List<RiskCheckMainLog> riskCheckMainLogList = riskCheckMainLogDao.findAllById(checkerStatusList);
            riskCheckMainLogs = new ArrayList<RiskCheckMainLog>();
            for (RiskCheckMainLog riskCheckMainLog : riskCheckMainLogList) {
                RiskCheckMainLog riskCheckMainLogNew = new RiskCheckMainLog();
                BeanUtils.copyProperties(riskCheckMainLog, riskCheckMainLogNew);
                riskCheckMainLogNew.setChecker(riskCheckMainQueryVo.getChecker());
                riskCheckMainLogNew.setCheckComCode(riskCheckMainQueryVo.getCheckComCode());
                riskCheckMainLogNew.setCheckModel(riskCheckMainQueryVo.getCheckModel());
                // 巡检日期
                Date myDate = new Date();
                riskCheckMainLogNew.setCheckDate(myDate);
                // 改变标志位为‘0 --未生成’
                riskCheckMainLogNew.setCheckerStatus("0");
                riskCheckMainLogs.add(riskCheckMainLogNew);
            }
        } else {
        	map.put("status", 0);
        	map.put("statusText", "没有要更新log表数据");
//            ajaxResult.setStatus(0);
//            ajaxResult.setStatusText("没有要更新log表数据");
            return map;
        }
        try {
            riskCheckMainLogDao.saveAll(riskCheckMainLogs);
            map.put("status", 0);
        	map.put("statusText", "更新log表数据成功");
//            ajaxResult.setStatus(0);
//            ajaxResult.setStatusText("更新log表数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        	map.put("statusText", "更新log表数据失败");
//            ajaxResult.setStatus(-1);
//            ajaxResult.setStatusText("更新log表数据失败");
        }
        return map;
    }

    /**
     * @param RiskCheckRequestVo
     * @return RiskCheckResponseVo
     * @throws Exception
     * @功能 对导入的巡检信息进行查询
     * @author 周东旭
     * @日期 2020-01-16
     * @修改记录
     */
    public RiskCheckResponseVo queryCheckMainLog(RiskCheckRequestVo riskCheckRequestVo, UserInfo userInfo) {
        RiskCheckResponseVo riskCheckResponseVo = new RiskCheckResponseVo();
        int pageNo = riskCheckRequestVo.getPageNo();
        int pageSize = riskCheckRequestVo.getPageSize();
        Sort sort = null;
        try {
            RiskCheckMainQueryVo riskCheckMainQueryVo = riskCheckRequestVo.getRiskCheckMainQueryVo();
            if (riskCheckMainQueryVo != null) {
                // String userCodeInfo = userInfo.getUserCode();
                String userCodeInfo = "00000000";
                Specification<RiskCheckMainLog> spec = new Specification<RiskCheckMainLog>() {
                    @Override
                    public Predicate toPredicate(Root<RiskCheckMainLog> root, CriteriaQuery<?> query,
                                                 CriteriaBuilder cb) {
                        List<Predicate> list = new ArrayList<>();
                        // 投保单号
                        if (StringUtils.isNotBlank(riskCheckMainQueryVo.getProposalNo())) {
                            list.add(cb.equal(root.get("proposalNo"), riskCheckMainQueryVo.getProposalNo().trim()));
                        }
                        // 被保险人名称
                        String insuredName = riskCheckMainQueryVo.getInsuredName();
                        if (StringUtils.isNotBlank(insuredName)) {
                            List<Predicate> list2 = new ArrayList<>();
                            list2.add(cb.like(root.get("insuredName"), "%" + insuredName.trim() + "%"));
                            list2.add(cb.like(root.get("insuredCode"), "%" + insuredName.trim() + "%"));
                            Predicate[] predicate = new Predicate[list2.size()];
                            list.add(cb.or(list2.toArray(predicate)));
                        }
                        // 被保险人代码
                        String insuredCode = riskCheckMainQueryVo.getInsuredCode();
                        if (StringUtils.isNotBlank(insuredCode)) {
                            list.add(cb.equal(root.get("insuredCode"), insuredCode.trim()));
                        }
                        // 巡检编号
                        String riskCheckNo = riskCheckMainQueryVo.getRiskCheckNo();
                        if (StringUtils.isNotBlank(riskCheckNo)) {
                            list.add(cb.equal(root.get("riskCheckNo"), riskCheckNo.trim()));
                        }
                        // 被保险人类型
                        String insuredType = riskCheckMainQueryVo.getInsuredType();
                        if (StringUtils.isNotBlank(insuredType)) {
                            list.add(cb.equal(root.get("insuredType"), insuredType.trim()));
                        }
                        String comCode = riskCheckMainQueryVo.getComCode();
                        if (StringUtils.isNotBlank(comCode)) {
                            PrpDcompanyFk prpDcompanyFk = new PrpDcompanyFk();
                            prpDcompanyFk = riskCheckMainDao.viewPrpDcompanyByComCode(comCode);
                            if (prpDcompanyFk != null) {
                                String upperPath = prpDcompanyFk.getUpperPath();
                                if (StringUtils.isNotBlank(upperPath)) {
                                    upperPath = upperPath.trim();
                                    // upperPath = "31000000,21029913";
                                    if ("00".equals(
                                            upperPath.substring(upperPath.length() - 8, upperPath.length() - 6))) {
                                    } else if (upperPath.length() == 17) {
                                        if ("2102,3302,3502,3702,4403".indexOf(upperPath.substring(9, 13)) > -1) {
                                            list.add(cb.equal(cb.substring(root.get("comCode"), 0, 4),
                                                    upperPath.substring(9, 13)));
                                            // queryRule.addSql("substr(this_.comCode,0,4)='" + upperPath.substring(9,
                                            // 13) + "'");
                                        } else {
                                            if ("21".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 0, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.notEqual(cb.substring(root.get("comCode"), 0, 4), "2102"));
                                            } else if ("33".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 0, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.notEqual(cb.substring(root.get("comCode"), 0, 4), "3302"));
                                            } else if ("35".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 0, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.notEqual(cb.substring(root.get("comCode"), 0, 4), "3502"));
                                            } else if ("37".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 0, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.notEqual(cb.substring(root.get("comCode"), 0, 4), "3702"));
                                            } else if ("44".equals(upperPath.substring(9, 11))) {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 0, 2),
                                                        upperPath.substring(9, 11)));
                                                list.add(cb.notEqual(cb.substring(root.get("comCode"), 0, 4), "4403"));
                                            } else {
                                                list.add(cb.equal(cb.substring(root.get("comCode"), 0, 2),
                                                        upperPath.substring(9, 11)));
                                            }
                                        }
                                    } else if (upperPath.length() > 17) {
                                        List<PrpDcompanyFk> prpDcompanyFkList = riskCheckMainDao
                                                .queryPrpDcompanyByUpperPath(upperPath.substring(0, 26));

                                        List<String> comCodeList = new ArrayList();
                                        if (null != prpDcompanyFkList && prpDcompanyFkList.size() > 0) {
                                            for (int i = 0; i < prpDcompanyFkList.size(); i++) {
                                                comCodeList.add(prpDcompanyFkList.get(i).getComCode());
                                            }
                                        }
                                        list.add(root.get("comCode").in(comCodeList));
                                    }
                                }
                            } else {
                                list.add(cb.equal(root.get("comCode"), comCode));
                            }
                        }
                        // 巡检起期
                        Date checkStartDate = riskCheckMainQueryVo.getCheckStartDate();
                        // 巡检止期
                        Date checkEndDate = riskCheckMainQueryVo.getCheckEndDate();
                        if (checkStartDate != null || checkEndDate != null) {
                            if (checkStartDate != null && checkEndDate != null) {
                                list.add(cb.between(root.get("checkDate"), checkStartDate, checkEndDate));
                            } else {
                                if (checkStartDate != null) {
                                    list.add(cb.greaterThanOrEqualTo(root.get("checkDate"), checkStartDate));
                                } else {
                                    list.add(cb.lessThanOrEqualTo(root.get("checkDate"), checkEndDate));
                                }
                            }
                        }
                        // 审核状态
                        String[] checkerStatus = riskCheckMainQueryVo.getCheckerStatus();
                        // 审核查询若无标志位，则查询出待审核的风控报告
                        List<String> checkerStatusList = new ArrayList<>();
                        if (null != checkerStatus && checkerStatus.length > 0) {
                            for (int i = 0; i < checkerStatus.length; i++) {
                                checkerStatusList.add(checkerStatus[i]);
                            }
                        }
                        if (null != checkerStatusList && checkerStatusList.size() > 0) {
                            list.add(root.get("checkerStatus").in(checkerStatusList));
                        }
                        Predicate[] arr = new Predicate[list.size()];
                        return cb.and(list.toArray(arr));
                    }
                };
                // 排序操作
                String orderColum = riskCheckRequestVo.getOrderColumn().trim();
                String orderType = riskCheckRequestVo.getOrderType().trim();
                if (StringUtils.isNotBlank(orderColum)) {
                    // 默认做升序处理
                    if ("logId".equals(orderColum)) {
                        sort = Sort.by(Sort.Direction.ASC, orderColum);
                        // queryRule.addAscOrder(orderColum);
                    } else {
                        if (StringUtils.isNotBlank(orderType)) {
                            if ("true".equals(orderType)) {
                                sort = Sort.by(Sort.Direction.DESC, orderColum);
                            } else if ("false".equals(orderType)) {
                                sort = Sort.by(Sort.Direction.ASC, orderColum);
                            }
                        }
                    }
                }
                // 分页加排序
                Page page = this.riskCheckMainLogDao.findAll(spec, PageRequest.of(pageNo - 1, pageSize, sort));
                // 设置数据
                riskCheckResponseVo.setDataList(page.getContent());
                // 设置数据总条数
                riskCheckResponseVo.setTotalCount(page.getTotalElements());
                // 设置总页数
                riskCheckResponseVo.setTotalPage(page.getTotalPages());
            } else {
                return null;
            }
        } catch (Exception e) {
            LOGGER.info("巡检日志表中查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("巡检日志表中查询异常:" + e);
        }
        return riskCheckResponseVo;
    }

    /**
     * @param HttpServletRequest
     * @param RiskCheckRequestVo
     * @return AjaxResult
     * @throws Exception
     * @功能:保存巡检报告
     * @作者：崔凤志 @日期：2020-01-19
     */
    public int saveRiskCheck(UserInfo userInfo, RiskCheckRequestVo riskCheckRequestVo,
                                                          String protocol) {
        int resp = 0 ;
        RiskCheckMainVo riskCheckMainVo = null;

        try {
            riskCheckMainVo = riskCheckRequestVo.getRiskCheckMainVo();

            RiskCheckMain source = null;

            // 巡检编号不为null 根据 巡检id 查询巡检报告基本信息RiskCheckMain
            if (StringUtils.isNotBlank(riskCheckMainVo.getRiskCheckNo())) {

                Optional<RiskCheckMain> optional = riskCheckMainDao.findById(riskCheckMainVo.getRiskCheckNo().trim());
                if (optional.isPresent()) {
                    source = optional.get();
                }
            }

            // 设置审核流程为 保存时---就 审核通过
            // 无审核流程暂定为保存即为审核通过
            riskCheckMainVo.setUnderwriteFlag("1");

            // 获取模板编号 知道时哪种模板类型的巡检报告
            // 巡检报告模板编号
            String riskModel = riskCheckRequestVo.getRiskCheckMainVo().getCheckModel();

            // 处理riskmodel与checkmodel模板编号相同问题
            riskCheckRequestVo.getRiskCheckMainVo().setCheckModel("001".equals(riskModel) ? "003" : "004");

            // 进行打分
            RiskCheckGradeVo riskCheckGradeVo = grade(riskCheckRequestVo);

            // 恢复原数据
            riskCheckRequestVo.getRiskCheckMainVo().setCheckModel(riskModel);

            // 判断是否已存在分值数据
            if (riskCheckMainVo.getRiskCheckAssessList() != null
                    && riskCheckMainVo.getRiskCheckAssessList().size() > 0) {
                // 设置各项分值
                riskCheckMainVo.getRiskCheckAssessList().set(0, riskCheckGradeVo.getRiskCheckAssess());
            } else {
                // 设置各项分值
                riskCheckMainVo.getRiskCheckAssessList().add(riskCheckGradeVo.getRiskCheckAssess());
            }

            // 设置分数
            riskCheckMainVo.setScore(riskCheckGradeVo.getScore());

            // 设置突出风险
            riskCheckMainVo.setHighlightRisk(riskCheckGradeVo.getHighlightRisk());

            // 设置权重表id
            RiskCheckMain riskCheckMain = new RiskCheckMain();
            // 对象拷贝更新
            BeanUtils.copyProperties(riskCheckMainVo, riskCheckMain);

            // 将vo类数据赋值到pojo类(List数据)
            riskCheckMain.setRiskCheckVentureList((List<RiskCheckVenture>) com.picc.riskctrl.common.utils.BeanUtils.convert(riskCheckRequestVo.getRiskCheckVentureList(), RiskCheckVenture.class));
            riskCheckMain.setRiskCheckImageList((List<RiskCheckImage>) com.picc.riskctrl.common.utils.BeanUtils.convert(riskCheckRequestVo.getRiskCheckImageList(), RiskCheckImage.class));
            riskCheckMain.setRiskCheckAssessList((List<RiskCheckAssess>) com.picc.riskctrl.common.utils.BeanUtils.convert(riskCheckMainVo.getRiskCheckAssessList(), RiskCheckAssess.class));

            // 为大对象的某特定字段赋值
            com.picc.riskctrl.common.utils.BeanUtils.setValueforSpecificField(riskCheckMain, "RiskCheckNo", riskCheckMain.getRiskCheckNo());

            // 数据保存
            riskCheckMainDao.save(riskCheckMain);

            // 设置返回数据，方便移动端展示分值
            BoCopyUtil.convert(riskCheckMain, riskCheckMainVo, null, null, null);

        } catch (Exception e) {
            resp = 1;
            LOGGER.info("保存巡检报告异常:" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("保存巡检报告异常:" + e);
        }
        return resp;
    }

    /**
     * @param RiskCheckRequestVo 打分大对象
     * @throws Exception
     * @功能：打分功能实现
     * @author 梁尚 @时间：2017-11-20 @修改记录：
     */
    @SuppressWarnings("unchecked")
    public RiskCheckGradeVo grade(RiskCheckRequestVo riskCheckRequestVo) throws Exception {
        RiskCheckGradeVo riskCheckGradeVo = new RiskCheckGradeVo();
        try {
            // 设置factorMap集合，用于存储factor分子
            Map<String, List<UtiFactor>> factorFMap = new HashMap<String, List<UtiFactor>>();
            // 设置factorMap集合，用于存储factor原子(里面存储为map，方便查找计算因子时快速找出对应因子数据)
            Map<String, Map<String, UtiFactor>> factorYMap = new HashMap<String, Map<String, UtiFactor>>();
            // 设置objectMap集合，用于存储反射出的对象的字段值
            Map<String, String> objectMap = new HashMap<String, String>();
            // 设置scoreMap集合，用于存储各灾因分数
            Map<String, BigDecimal> scoreMap = new HashMap<String, BigDecimal>();

            // 设置scoreRealMap集合，用于存储巡检报告折算之前的分数，便于处理部分特殊风险
            Map<String, BigDecimal> scoreRealMap = new HashMap<String, BigDecimal>();
            try {
                objectMap = getObjectColum(riskCheckRequestVo);
            } catch (Exception e) {
                LOGGER.info("反射信息异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            String lon = riskCheckRequestVo.getRiskCheckMainVo().getPointx_2000() + "";
            String lat = riskCheckRequestVo.getRiskCheckMainVo().getPointy_2000() + "";

            String[] str = this.queryDenger(lon, lat);
            String riskModel = riskCheckRequestVo.getRiskCheckMainVo().getCheckModel();
            String[] dangerType = RiskControlConst.TYPE_SUM_CHECK.split(",");


            for (int j = 0; j < dangerType.length; j++) {

                String temp = dangerType[j];

                // 分子集合
                List<UtiFactor> resultF = utiFactorDao.findAll(new Specification<UtiFactor>() {

                    @Override
                    public Predicate toPredicate(Root<UtiFactor> root, CriteriaQuery<?> query,
                                                 CriteriaBuilder criteriaBuilder) {
                        Predicate predicate1 = criteriaBuilder.equal(root.get("id").get("riskModel"), riskCheckRequestVo.getRiskCheckMainVo().getCheckModel());
                        Predicate predicate2 = criteriaBuilder.equal(root.get("id").get("dangerType"), temp);
                        Predicate predicate3 = criteriaBuilder.equal(root.get("validStatus"), "1");

                        Path<Object> path = root.get("factorType");
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                        in.value("01");
                        in.value("02");

                        return criteriaBuilder.and(predicate1, predicate2, predicate3, criteriaBuilder.and(in));
                    }
                });

                factorFMap.put(dangerType[j], resultF);

                // 原子集合
                List<UtiFactor> resultY = utiFactorDao.findAll(new Specification<UtiFactor>() {

                    @Override
                    public Predicate toPredicate(Root<UtiFactor> root, CriteriaQuery<?> query,
                                                 CriteriaBuilder criteriaBuilder) {
                        Predicate predicate1 = criteriaBuilder.equal(root.get("id").get("riskModel"),
                                riskCheckRequestVo.getRiskCheckMainVo().getCheckModel());
                        Predicate predicate2 = criteriaBuilder.equal(root.get("id").get("dangerType"), temp);
                        Predicate predicate3 = criteriaBuilder.equal(root.get("validStatus"), "1");

                        Path<Object> path = root.get("factorType");
                        CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                        in.value("03");
                        in.value("04");

                        return criteriaBuilder.and(predicate1, predicate2, predicate3, criteriaBuilder.and(in));
                    }
                });

                Map<String, UtiFactor> factorYColumMap = new HashMap<String, UtiFactor>();
                for (UtiFactor riskReportUtiFactor : resultY) {
                    factorYColumMap.put(riskReportUtiFactor.getId().getFactorNo(), riskReportUtiFactor);
                }
                factorYMap.put(dangerType[j], factorYColumMap);
            }


            Map<String, UtiScore> utiScoreMap = new HashMap<String, UtiScore>();

            List<UtiScore> scoreList = new ArrayList<UtiScore>();

            scoreList = utiScoreDao.findAll(new Specification<UtiScore>() {

                @Override
                public Predicate toPredicate(Root<UtiScore> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("validStatus"), "1");
                }
            });

            for (UtiScore score : scoreList) {
                UtiScoreId id = score.getId();
                utiScoreMap.put(id.getRiskModel().trim() + "_" + id.getFactorNo().trim() + "_"
                        + id.getDangerType().trim() + "_" + id.getFactorValue().trim(), score);
            }


            Map<String, UtiFormula> UtiFormulaMap = new HashMap<String, UtiFormula>();


            List<UtiFormula> utiFormulaList = utiFormulaDao.findAll(new Specification<UtiFormula>() {

                @Override
                public Predicate toPredicate(Root<UtiFormula> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    Predicate predicate = criteriaBuilder.equal(root.get("validStatus"), "1");
                    return predicate;
                }
            });

            for (UtiFormula utiFormula : utiFormulaList) {
                UtiFormulaId id = utiFormula.getId();
                UtiFormulaMap.put(
                        id.getRiskModel().trim() + "_" + id.getFactorNo().trim() + "_" + id.getDangerType().trim(),
                        utiFormula);
            }


            // 权重表缓存处理
            String comCode = riskCheckRequestVo.getRiskCheckMainVo().getComCode();

            List<UtiWeight> utiWeightList = new ArrayList<UtiWeight>();


            List<String> comCodeList = new ArrayList<String>();
            comCodeList.add("00000000");
            if (!"00000000".equals(comCode)) {
                comCodeList.add(comCode.substring(0, 2) + "000000");
            }

            utiWeightList = utiWeightDao.findAll(new Specification<UtiWeight>() {

                @Override
                public Predicate toPredicate(Root<UtiWeight> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    Predicate predicate1 = criteriaBuilder.equal(root.get("riskModel"), riskModel);
                    Predicate predicate2 = criteriaBuilder.equal(root.get("validStatus"), "1");

                    Path<Object> path = root.get("comCode");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
                    for (String comCode : comCodeList) {
                        in.value(comCode);
                    }

                    return criteriaBuilder.and(predicate1, predicate2, criteriaBuilder.and(in));
                }
            });

            // 突出风险缓存处理

            List<UtiHighlightRisk> highlightRiskList = new ArrayList<UtiHighlightRisk>();

            highlightRiskList = utiHighlightRiskDao.findAll(new Specification<UtiHighlightRisk>() {

                @Override
                public Predicate toPredicate(Root<UtiHighlightRisk> root, CriteriaQuery<?> query,
                                             CriteriaBuilder criteriaBuilder) {
                    Predicate predicate1 = criteriaBuilder.equal(root.get("id").get("riskModel"),
                            riskCheckRequestVo.getRiskCheckMainVo().getCheckModel());
                    Predicate predicate2 = criteriaBuilder.equal(root.get("validStatus"), "1");
                    return criteriaBuilder.and(predicate1, predicate2);
                }
            });


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
                        scoreRealMap.put(riskReportUtiFactor.getFromTable() + "." + riskReportUtiFactor.getFromColumn(),
                                score);
                        // 将校验之后的分值存入分值集合中
                        scoreMap.put(key, scoreMap.get(key).add(validateExt(riskReportUtiFactor, score)));
                    }

                }
                scoreMap.put(key, scoreMap.get(key).setScale(2, BigDecimal.ROUND_HALF_UP));

            }
            // 权重表字段名数组
            String[] weightArray = {"fireWeight", "waterWeight", "windWeight", "thunderWeight", "snowWeight",
                    "theftWeight"};
            // 风险值表字段名数组
            String[] assesArray = {"envDanger", "impDanger", "buildDanger", "cargoDanger", "typDanger",
                    "floodDanger"};

            // 风险值表赋值
            RiskCheckAssessVo riskCheckAssess = (RiskCheckAssessVo) setValueBycolum(new RiskCheckAssessVo(), scoreMap,
                    dangerType, assesArray);
            riskCheckGradeVo.setRiskCheckAssess(riskCheckAssess);
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
                riskCheckGradeVo.setUtiWeightId(realWeight.getId());
                riskCheckGradeVo.setScore(score);
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
                                highlightRisk.append(count + "、 " + riskReportHighlightRisk.getRiskReminder() + "\n");
                            }
                            // 存货选项触发建议（存货水敏性选b、c项，且存放位置选b、c、d项）
                        } else if ("2".equals(riskReportHighlightRisk.getRiskFlag())) {
                            if ("cargoWaterSen".equals(riskReportHighlightRisk.getId().getFromColumn()) && objectMap
                                    .get(key).indexOf(riskReportHighlightRisk.getId().getRiskValue()) > -1) {
                                if ("B、C、D".indexOf(objectMap.get("RiskCheckVentureVo.stoLocation")) > -1) {
                                    count++;
                                    highlightRisk
                                            .append(count + "、 " + riskReportHighlightRisk.getRiskReminder() + "\n");
                                }
                            }
                            // RiskReportHighlightRisk表里的riskValue取值并不为风险取值
                        } else {
                            if (objectMap.get(key).indexOf(riskReportHighlightRisk.getId().getRiskValue()) == -1) {
                                count++;
                                highlightRisk.append(count + "、 " + riskReportHighlightRisk.getRiskReminder() + "\n");
                            }
                        }

                    }
                }
            }
            // 排水系统分数值触发建议（5项分值总和低于6.6）
            if (riskCheckRequestVo.getRiskCheckMainVo().getCheckModel() != null
                    && "003".equals(riskCheckRequestVo.getRiskCheckMainVo().getCheckModel())) {
                BigDecimal sum = BigDecimal.ZERO;
                String[] specialDangerArr = {"RiskCheckVentureVo.drainageMethod", "RiskCheckVentureVo.drainageBlock",
                        "RiskCheckVentureVo.dredgeCondition", "RiskCheckVentureVo.connectedFlag",
                        "RiskCheckVentureVo.unobstructedFlag"};
                for (String specialDanger : specialDangerArr) {
                    if (scoreRealMap.containsKey(specialDanger)) {
                        if (scoreRealMap.get(specialDanger) != null) {
                            sum = sum.add(scoreRealMap.get(specialDanger).divide(new BigDecimal(4)).setScale(2,
                                    BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }
                if (sum.compareTo(new BigDecimal("6.6")) < 0) {
                    count++;
                    highlightRisk.append(count + "、 " + "建筑物排水系统存在排水隐患， 建议：\n" + "①、减少内部排水管下方货物堆放，加强排水管完整性维护\n"
                            + "②、汛期前及时疏通排水沟/井，确保排水沟与市政主管畅通连接\n");
                    if (objectMap.containsKey("RiskCheckVentureVo.unobstructedFlag")) {
                        if ("B、C".indexOf(objectMap.get("RiskCheckVentureVo.unobstructedFlag")) > -1) {
                            highlightRisk.append("③、建议尽快清理屋顶杂物，确保屋顶排水通畅\n");
                        }
                    }
                }
            }
            riskCheckGradeVo.setHighlightRisk(highlightRisk.toString());
        } catch (Exception e) {
            LOGGER.info("打分功能异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("打分功能异常:" + e);
        }
        return riskCheckGradeVo;
    }

    /**
     * @param objectMap  对象值集合
     * @param key        灾因
     * @param factorYMap 原子map
     * @param utiFormula 计算公式
     * @return BigDecimal 分数
     * @throws Exception
     * @功能：间接因子计算
     * @author 梁尚 @时间：2017-11-20 @修改记录：
     */
    private BigDecimal calculateByFactor(Map<String, String> objectMap, String key,
                                         Map<String, Map<String, UtiFactor>> factorYMap, UtiFormula utiFormula, Map<String, UtiScore> utiScoreMap,
                                         String[] str) throws Exception {

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
                                // 将原子值作为分值
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
                String content = "";
                // 用因子分值替换因子
                content = utiFormula.getContent();

                DisastersScore DS = new DisastersScore();
                Class DSClass = DS.getClass();
                Method[] methods = DSClass.getMethods();
                for (int i = 0; i < methods.length; i++) {
                    if (content.equals(methods[i].getName())) {
                        sumScore = (BigDecimal) methods[i].invoke(DSClass.newInstance(), str, utiFormula);
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
                                    // 间接原子（没有分值）
                                } else if ("04".equals(factorY.getFactorType())) {
                                    // 将原子值作为分值
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
                        // 多数据取最大值
                    } else if ("02".equals(utiFormula.getListType())) {
                        BigDecimal score = getResultByFormula(contentMap.get(i));
                        if (score.compareTo(sumScore) > 0) {
                            sumScore = score;
                        }
                        // 多数据取最小值
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
            LOGGER.info("间接因子计算异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("间接因子计算异常:" + e);
        }
        return sumScore;
    }

    /**
     * @param riskReportUtiFactor 因子
     * @param value               因子值
     * @return BigDecimal 分数
     * @功能：根据公式获取所需因子
     * @author 梁尚 @时间：2017-11-20 @修改记录：
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
            LOGGER.info("根据公式获取所需因子异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("根据公式获取所需因子异常:" + e);
        }
        return factorMap;
    }

    /**
     * @param riskReportUtiFactor 因子对象
     * @param score               计算得到的分值
     * @return BigDecimal 最终的分值
     * @功能：校验最值
     * @author 梁尚 @时间：2017-11-20 @修改记录：
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
     * @param objectClass 要反射的对象
     * @param objectMap   存入的map集合
     * @throws Exception
     * @功能：利用反射将对象里的值存入map集合中
     * @author 梁尚 @时间：2017-11-20 @修改记录：
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
     * @param riskReportUtiFactor 因子
     * @param value               因子值
     * @return BigDecimal 分数
     * @throws Exception
     * @功能：将字符串计算公式计算出结果
     * @author 梁尚 @时间：2017-11-20 @修改记录：
     */
    private BigDecimal getResultByFormula(String formula) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        BigDecimal result = BigDecimal.ZERO;
        result = new BigDecimal(engine.eval(formula).toString()).setScale(4, BigDecimal.ROUND_HALF_UP);

        return result;
    }

    /**
     * @param obj        权重对象
     * @param map        各风险分值map集合
     * @param dangerType 风险key值
     * @param array      对应字段名
     * @throws Exception
     * @功能：根据权重以及各风险分值计算总分
     * @author 梁尚 @时间：2017-11-20 @修改记录：
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
     * @author 梁尚 @时间：2017-11-20 @修改记录：
     */
    private Object setValueBycolum(Object obj, Map map, String[] dangerType, String[] array) throws Exception {
        try {
            if (dangerType.length == array.length) {
                Class objectClass = obj.getClass();
                Field[] fields = objectClass.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    for (int i = 0; i < array.length; i++) {
                        String fieldName = field.getName().substring(field.getName().lastIndexOf('.') + 1);
                        if (fieldName.equals(array[i])) {
                            field.set(obj, map.get(dangerType[i]));
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
     * @param riskReportUtiFactor 因子对象
     * @param score               计算得到的分值
     * @return BigDecimal 最终的分值
     * @功能：根据经纬度坐标获取灾因值
     * @author 孙凯 @时间：2020-1-19 @修改记录：
     */
    public String[] queryDenger(String lon, String lat) {
        // 获取iserver的地址

        String serverName = "http://10.10.2.241:5001/";

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
     * @param riskReportUtiFactor因子
     * @param value                 因子值
     * @return BigDecimal 分数
     * @功能：根据utiScore表将因子的值转成分数
     * @author 梁尚 @时间：2017-11-20 @修改记录：
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
            // 兼职版出险原因处理（不存在某项时则有分,分值表配置的分值为此分）
            if ("04".equals(riskReportUtiFactor.getColumnType())) {
                List<UtiScore> scoreList = new ArrayList<UtiScore>();
                Specification<UtiScore> spec = new Specification<UtiScore>() {

                    @Override
                    public Predicate toPredicate(Root<UtiScore> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                        List<Predicate> list = new ArrayList<>();
                        list.add(cb.equal(root.get("id").get("riskModel"),
                                riskReportUtiFactor.getId().getRiskModel()));
                        list.add(cb.equal(root.get("id").get("factorNo"),
                                riskReportUtiFactor.getId().getFactorNo()));
                        list.add(cb.equal(root.get("id").get("dangerType"),
                                riskReportUtiFactor.getId().getDangerType()));
                        list.add(cb.equal(root.get("validStatus"), "1"));
                        Predicate[] arr = new Predicate[list.size()];
                        return cb.and(list.toArray(arr));
                    }
                };
                scoreList = this.utilScoreRepository.findAll(spec);
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
     * @param queryRule
     * @param userInfo
     * @return ins.framework.web.AjaxResult
     * @Description 企业失信具体信息查询
     * @Date 2020/2/17
     * @author 崔凤志
     */
    public Map<String,Object> queryCorporateRadarInfo(CorporateRadarQueryRule queryRule, UserInfo userInfo) {
//        ApiResponse result = new ApiResponse();
    	Map<String,Object> map = new HashMap<String, Object>();
        String productCode = queryRule.getProductCode();

        ResourceBundle bundle = ResourceBundle.getBundle("config.map", Locale.getDefault());
        String url = bundle.getString("url3");
        StringBuffer urlParam = new StringBuffer(url);

        urlParam.append("?riskcode=").append(userInfo.getRiskCode())
                .append("&comcode=").append(userInfo.getComCode())
                .append("&operatorcode=").append(userInfo.getComCode())
                .append("&provincecode=").append(userInfo.getComCode().substring(0, 4));
        Map<String, Object> productMap = new HashMap<String, Object>();
        // 超频率提示信息
        StringBuffer eMessage = new StringBuffer();
        // 其他错误提示信息
        StringBuffer errorMessage = new StringBuffer();
        if (!(PRODUCTCODE_DETAILLIST.contains(productCode))) {
            //企业关键字精确获取详细信息(Full) url
            StringBuffer fullUrl = new StringBuffer();
            if (("P0090000073").equals(productCode)) { // PRODUCTCODELIST.contains("P0090000073")
                fullUrl.append(urlParam)
                        .append("&productcode=").append("P0090000073")
                        .append("&keyword=").append(queryRule.getKeyword());

                //full 信息 获取基本信息
                JSONObject fullMessage = this.doPost(fullUrl.toString());
                BasicInfor basic = new BasicInfor();

                if (!(fullMessage == null)) {
                    CorporateRadarResult _result = null;
                    if ("200".equals(fullMessage.getString("Status"))) {
                        basic.setKeyNo(fullMessage.getJSONObject("Result").getString("KeyNo"));
                        basic.setName(fullMessage.getJSONObject("Result").getString("Name"));
                        basic.setUpdatedDate(fullMessage.getJSONObject("Result").getString("UpdatedDate"));
                        basic.setOperName(fullMessage.getJSONObject("Result").getString("OperName"));
                        basic.setStartDate(fullMessage.getJSONObject("Result").getString("StartDate"));
                        basic.setStatus(fullMessage.getJSONObject("Result").getString("Status"));
                        basic.setPhoneNumber(fullMessage.getJSONObject("Result").getJSONObject("ContactInfo").getString("PhoneNumber"));
                        basic.setAddress(fullMessage.getJSONObject("Result").getString("Address"));

                        //行政处罚
                        JSONArray penaltyResult = JSONArray.parseArray(fullMessage.getJSONObject("Result").getJSONArray("Penalty").toString());
                        if (penaltyResult != null && penaltyResult.size() > 0) {
                            _result = new CorporateRadarResult(penaltyResult, (long) penaltyResult.size());
                        } else {
                            _result = null;
                        }
                        productMap.put("administrativePenalty", _result);

                        //股权出质
                        JSONArray pledgeResult = JSONArray.parseArray(fullMessage.getJSONObject("Result").getJSONArray("Pledge").toString());
                        if (pledgeResult != null && pledgeResult.size() > 0) {
                            _result = new CorporateRadarResult(pledgeResult, (long) pledgeResult.size());
                        } else {
                            _result = null;
                        }
                        productMap.put("pledge", _result);
                        map.put("status", 1);
//                        result.setStatus(1);
                    } else {
                        if ("110".equals(fullMessage.getString("Status"))) {
                            eMessage.append("企业雷达查询：").append(fullMessage.getString("Message")); //.append(";")
                            map.put("statusText", eMessage.toString());
                            map.put("status", 0);
//                            result.setStatusText(eMessage.toString());
//                            result.setStatus(0);
                        } else {
                        	map.put("statusText", fullMessage.getString("Message"));
                            map.put("status", 2);
//                            result.setStatusText(fullMessage.getString("Message"));
//                            result.setStatus(2);
                        }
                    }
                    productMap.put("basic", basic);
                    map.put("data", productMap);
//                    result.setData(productMap);
                } else {
                	map.put("statusText", "企业雷达数据异常！");
                    map.put("status", 2);
//                    result.setStatus(2);
//                    result.setStatusText("企业雷达数据异常！");
                }
                // 列表信息查询
            } else {
                CorporateRadarResult corporateRadarResult = null;
                StringBuffer buffer = new StringBuffer();
                for (String code : PRODUCTCODELIST) {
                    if (code.equals(productCode)) {
                        if ("P0090000001".equals(code) || "P0090000005".equals(code)
                                || "P0090000047".equals(code) || "P0090000049".equals(code)) {
                            //裁判文书查询 开庭公告查询 被执行信息 失信信息
                            buffer.append(urlParam)
                                    .append("&searchKey=").append(queryRule.getKeyword())
                                    .append("&pageIndex=").append(queryRule.getPageIndex())
                                    .append("&pageSize=").append(queryRule.getPageSize());

                        } else if ("P0090000004".equals(code)) {
                            //法院公告列表信息
                            buffer.append(urlParam)
                                    .append("&companyName=").append(queryRule.getKeyword())
                                    .append("&pageIndex=").append(queryRule.getPageIndex())
                                    .append("&pageSize=").append(queryRule.getPageSize());
                        } else if ("P0090000007".equals(code) || "P0090000010".equals(code)
                                || "P0090000054".equals(code) || "P0090000057".equals(code)) {
                            //获取动产抵押信息 获取简易注销信息 获取司法协助信息 获取严重违法信息
                            buffer.append(urlParam)
                                    .append("&keyWord=").append(queryRule.getKeyword());
                        } else if ("P0090000008".equals(code) || "P0090000052".equals(code)
                                || "P0090000055".equals(code)) {
                            //获取环保处罚列表 司法拍卖列表 获取土地抵押列表
                            buffer.append(urlParam)
                                    .append("&keyWord=").append(queryRule.getKeyword())
                                    .append("&pageIndex=").append(queryRule.getPageIndex())
                                    .append("&pageSize=").append(queryRule.getPageSize());
                        } else if ("P0090000037".equals(code)) {
                            //查询企业经营异常信息
                            buffer.append(urlParam)
                                    .append("&keyNo=").append(queryRule.getKeyword());
                        }
                        buffer.append("&productcode=").append(code);
                    }
                }
                JSONObject jsonObject = doPost(buffer.toString());
                if (!(jsonObject == null)) {
                    if ("200".equals(jsonObject.getString("Status"))) {
                        //简易注销 Result[String类型]
                        if ("P0090000010".equals(productCode)) {
                            JSONObject jsonResult = jsonObject.getJSONObject("Result");
                            corporateRadarResult = new CorporateRadarResult(com.alibaba.fastjson.JSONObject.parse(jsonResult.toString()), 1L);
                            productMap.put(productCode, corporateRadarResult);
                        } else {
                            JSONArray jsonResult = jsonObject.getJSONArray("Result");
                            //获取数据
                            JSONArray parseObj = JSONArray.parseArray(jsonResult.toString());
                            //获取总条数
                            long total = 0L;
                            /*
                             * result没有paging信息的：
                             * 动产抵押[P0090000007],司法协助[P0090000054],
                             * 经营异常[P0090000037],严重违法[P0090000057]
                             */
                            if ("P0090000007".equals(productCode) || "P0090000054".equals(productCode)
                                    || "P0090000037".equals(productCode) || "P0090000057".equals(productCode)) {
                                total = parseObj.size();
                            } else {
                                total = jsonObject.getJSONObject("Paging").getLong("TotalRecords");
                            }
                            corporateRadarResult = new CorporateRadarResult(parseObj, total);
                            productMap.put(productCode, corporateRadarResult);
                        }
                        map.put("status", 1);
//                        result.setStatus(1);
                    } else if ("110".equals(jsonObject.getString("Status"))) {
                        eMessage.append(productCode).append(jsonObject.getString("Message"))
                                .append(";");
                        map.put("statusText", eMessage.toString());
//                        result.setStatusText(eMessage.toString());
                        productMap.put(productCode, null);
                        map.put("status", 0);
//                        result.setStatus(0);
                    } else {
                        errorMessage.append(productCode)
                                .append(jsonObject.getString("Message"))
                                .append(";");
                        productMap.put(productCode, null);
                    }
                } else {
                    productMap.put(productCode, null);
                    map.put("status", 2);
//                    result.setStatus(2);
                }
                map.put("data", productMap);
//                result.setData(productMap);
            }
            // 详细信息查询
        } else {
            String queryId = queryRule.getId();
            urlParam.append("&productcode=").append(productCode);
            if ("P0090000002".equals(productCode) || "P0090000003".equals(productCode)
                    || "P0090000006".equals(productCode) || "P0090000009".equals(productCode)
                    || "P0090000053".equals(productCode) || "P0090000056".equals(productCode)) {
                //裁判文书详情查询 法院公告详细信息 开庭公告详情查询 获取环保处罚详情 司法拍卖详情 获取土地抵押详情
                urlParam.append("&id=").append(queryId);
            } else if ("P0090000048".equals(productCode)) {
                //获取失信详细信息
                urlParam.append("&shiXinId=").append(queryId);
            } else if ("P0090000050".equals(productCode)) {
                //获取被执行人详细信息
                urlParam.append("&zhiXingId=").append(queryId);
            }
            JSONObject detailJsonObject = doPost(urlParam.toString());
            if ("200".equals(detailJsonObject.getString("Status"))) {
                map.put("data", com.alibaba.fastjson.JSONObject.parse(detailJsonObject.getString("Result")));
            	map.put("status", 1);
//                result.setData(com.alibaba.fastjson.JSONObject.parse(detailJsonObject.getString("Result")));
//                result.setStatus(1);
            } else if ("110".equals(detailJsonObject.getString("Status"))) {
                eMessage.append(detailJsonObject.getString("Message")).append(";");
                map.put("statusText", eMessage.toString());
                map.put("status",0);
//                result.setStatusText(eMessage.toString());
//                result.setStatus(0);
            } else {
                errorMessage.append(detailJsonObject.getString("Message")).append(";");
                map.put("statusText", errorMessage.toString());
                map.put("status",2);
//                result.setStatusText(errorMessage.toString());
//                result.setStatus(2);
            }
        }
        return map;
    }

    /**
     * @param url
     * @return response
     * @功能：企业雷达接口调用采用 HTTP协议，以 POST 方式进行调用
     * @日期：2020-02-17
     */
    public static JSONObject doPost(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        String result = null;
        try {
            post.setHeader("Content-Type", "application/json;charset=UTF-8"); //发送json数据需要设置contentType
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = EntityUtils.toString(entity, "UTF-8");// 返回json格式：
                response = JSONObject.parseObject(result);
            }
        } catch (JSONException e) {
            System.out.println("JSONException");
        } catch (Exception e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * 巡检报告数据批量导入
     * 周东旭
     *
     * @param
     * @return
     */
    public Map<String,Object> importRiskCheckInfo(MultipartFile file, UserInfo userInfo, HttpServletRequest request) {
//        ApiResponse ajaxResult = new ApiResponse();
    	Map<String,Object> map = new HashMap<String,Object>();
        if (!file.isEmpty() && (file.getOriginalFilename().indexOf("mapDataStandard") != -1 ||
                file.getOriginalFilename().indexOf("mapwarnPush") != -1)) {
            try {
                //设置上下文，方便后来取到dao
                ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
                ApplicationContextUtil.setApplicationContext(context);
                //文件实际存储路径
//				String path = request.getSession().getServletContext().getRealPath("/upload");
                // 获取存储地址
                ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());

                String path = bundle.getString("saveRootPath") + bundle.getString("saveTypePath");
                //文件浏览路径
//				String projectUrl = request.getScheme() + "://" + InetAddress.getLocalHost().getHostAddress() + ":" + 
//							request.getLocalPort() + request.getContextPath() + "/upload/save/riskexpert/";
                String projectUrl = bundle.getString("saveTypePath") + "/save/riskcheck/";

                //第二个参数需要注意,它是指标题索引的位置,可能你的前几行并不是标题,而是其他信息
                //比如数据批次号之类的,关于如何转换成javaBean,具体参考配置信息描述:  path+
                ExcelImportResult result = excelContext.readExcel("riskcheck", 2, file.getInputStream(), "/save/riskcheck", projectUrl);
                //无错误 方可存入数据库
                if (!result.hasErrors()) {
                    List<RiskCheckMainLog> riskCheckMainLogList = result.getListBean();
                    if (riskCheckMainLogList == null || riskCheckMainLogList.size() == 0) {
                    	map.put("status", 2);
                    	map.put("statusText", "表中不存在数据，请重新导入!");
//                    	ajaxResult.setStatus(2);
//                        ajaxResult.setStatusText("表中不存在数据，请重新导入!");
                        return map;
                    }
                    // 导入excel数据之前，先记录一下导入的信息

                    // 保存excel表格信息
                    this.saveRiskCheckMain(riskCheckMainLogList, userInfo);
                    map.put("status", 0);
//                    ajaxResult.setStatus(0);
                } else {
                	map.put("status", -1);
//                    ajaxResult.setStatus(-1);
                    String[] errorExcelUrl = {result.getErrorFileNetUrl(), result.getErrorFileRealUrl()};
                    map.put("data", errorExcelUrl);
//                    ajaxResult.setData(errorExcelUrl);
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage(), e);
                e.printStackTrace();
                map.put("status", 0);
                map.put("statusText", e.getMessage());
//                ajaxResult.setStatus(0);
//                ajaxResult.setStatusText(e.getMessage());
                throw new RuntimeException(e);
            }

        } else {
        	map.put("status", 0);
            map.put("statusText", "文件不存在");
//            ajaxResult.setStatus(0);
//            ajaxResult.setStatusText("文件不存在");
        }

        return map;
    }

    // 保存excel表格信息
    public void saveRiskCheckMain(List<RiskCheckMainLog> riskCheckMainLogList, UserInfo userInfo) {

        List<RiskCheckMainLog> riskCheckMainLogs = new ArrayList<RiskCheckMainLog>();
        String comCode = userInfo.getComCode();
        if (riskCheckMainLogList != null && riskCheckMainLogList.size() > 0) {
            for (RiskCheckMainLog riskCheckMainLog : riskCheckMainLogList) {
                //设置状态为‘未分发’
                riskCheckMainLog.setCheckerStatus("9");
                // 把登录用户的comcode设置为checkComCode的值
                riskCheckMainLog.setCheckComCode(comCode);
                riskCheckMainLogs.add(riskCheckMainLog);
            }
            try {
                // 对导入的单子进行批量保存
                this.riskCheckMainLogDao.saveAll(riskCheckMainLogs);
//                databaseDao.saveAll(RiskCheckMainLog.class, riskCheckMainLogs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param riskCheckMain
     * @param userInfo
     * @param generateAidPdfFile 是否生成pdf辅助文件
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @Description 生成word word模板,模板需docx格式,导出docx,doc都可以，
     * pdf需要word为doc版，docx版本字体会变化并且需要jodconverter-2.2.2.jar，本次采用doc
     * openoffice在转换为pdf格式时文档样式会有变化，因此单独为pdf制作一套(_pdf.docx)模板,
     * 通过模板生成pdf需要的doc文件，为保证样式报告封面和内容分离生成pdf
     */
    public Map<String, String> generateWord(RiskCheckMain riskCheckMain, UserInfo userInfo, Boolean generateAidPdfFile) {
        //巡检人翻译
        if (StringUtils.isNotBlank(riskCheckMain.getChecker())) {
//			riskCheckMain.setChecker(riskInsService.queryCodeCName("UserCode", riskCheckMain.getChecker(), userInfo.getUserCode(), userInfo.getComCode(), userInfo.getComCode()));
        }
        // 国民经济类型中文翻译
        if (StringUtils.isNotBlank(riskCheckMain.getBusinessSource())) {
//			riskCheckMain.setBusinessSource(riskInsService.queryCodeCName("TradeCode",riskCheckMain.getBusinessSource(), userInfo.getUserCode(), userInfo.getComCode(), "QBB"));
        }
        List<RiskCheckVenture> ventureList = riskCheckMain.getRiskCheckVentureList();

        RiskCheckVenture riskCheckVenture = null;
        if (ventureList != null && ventureList.size() > 0) {
            riskCheckVenture = ventureList.get(0);
        }
        String path = this.getClass().getResource("/").getPath();

        //模板路径
        StringBuffer basePath = new StringBuffer();
        basePath.append(path);
        basePath.append("com/picc/riskctrl/riskcheck/template");
        Map<String, Object> params = new HashMap<>();

        //防汛报告 word下载版
        StringBuffer templatePath = new StringBuffer();
        templatePath.append(basePath);

        //pdf下载
        StringBuffer pdfPath = new StringBuffer();
        pdfPath.append(basePath);

        TemplateFactory factory = null;

        //001 2019防汛版 002 2019防汛简版
        // 获取模板信息
        //使用工厂创建模板
        if ("001".equals(riskCheckMain.getCheckModel())) {
            factory = new NewRiskCheckIntactTemplateFactory();
        } else if ("002".equals(riskCheckMain.getCheckModel())) {
            factory = new NewRiskCheckSimpleTemplateFactory();
        } else {
            throw new IllegalArgumentException("模板类型选择有误，请核实");
        }

        //新文档生成方式 pdf单文件直接生成
        NewTemplateInfo templateInfo = (NewTemplateInfo) factory.createTemplate();

        //通知书 word docx版 用于word下载
        StringBuffer noticeReportPath = new StringBuffer();

        noticeReportPath.append(basePath).append(templateInfo.getWordNoticeTemplate());

        //word版报告下载模板docx路径
        templatePath.append(templateInfo.getWordReportTemplate());

        //pdf下载docx路径
        pdfPath.append(templateInfo.getDefaultPdfTemplate());

        //构建word模板中的插值数据 插值数据{{data}},data和括号中间不能有空格
        Map<String, Object> mainResult = null;

        try {
            mainResult = WordUtils.getWordResultByReflect(riskCheckMain);

            Map<String, Object> ventureResult = null;
            if (riskCheckVenture != null) {
                ventureResult = WordUtils.getWordResultByReflect(riskCheckVenture);
            }
            params.putAll(mainResult);
            if (ventureResult != null) {
                params.putAll(ventureResult);
            }
            //bug:2712044 围周围有无大型施工工程为无，报告中显示大型施工工程影响，同时标题错误 add by wangwenjie 2019/4/15 start
            if ("A".equals(params.get("largeProjects"))) {
                params.put("largeProImpact", "A");
            }
            //bug:2712044 围周围有无大型施工工程为无，报告中显示大型施工工程影响，同时标题错误 add by wangwenjie 2019/4/15 end
            Set<Map.Entry<String, Object>> entries = params.entrySet();
            String enumName = "";
            WordTranslateEnmu[] enums = WordTranslateEnmu.values();
            for (Map.Entry<String, Object> entry : entries) {
                enumName = entry.getKey() + "_" + entry.getValue();
                for (WordTranslateEnmu e : enums) {
                    if (e.name().equals(enumName)) {
                        params.put(entry.getKey(), e.getValue());
                        break;
                    }
                }
            }
            //修改日期格式
            Date checkDate = (Date) params.get("checkDate");
            DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String cd = "";
            if (checkDate != null) {
                cd = sdf.format(checkDate);
                //巡检日期
                params.put("checkDate", cd);
                mainResult.put("checkDate", cd);
            }
            //当前报告生成日期
            Date date = new Date();
            String current = sdf.format(date);
            params.put("currentDate", current);
            mainResult.put("currentDate", current);
            //checkcomcode翻译中文
            String checkcomcode = (String) params.get("checkComCode");
            //巡检机构为直接代码翻译
            String sourceCodeName = this.dataSourcesService.queryComCodeCName(checkcomcode);
            //通知书巡检机构翻译

            checkcomcode = translateProvinceNameByCheckComCode(checkcomcode);

            params.put("checkComCode", checkcomcode);
            params.put("sourceCodeName", sourceCodeName);

            //2716773:巡检下载通知书查勘机构没有翻译为对应查勘公司 add by wangwenjie 2019/4/16 start
            mainResult.put("checkComCode", checkcomcode);
            //2716773:巡检下载通知书查勘机构没有翻译为对应查勘公司 add by wangwenjie 2019/4/16 end
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        //报告前缀
        String prefix = "notice_";
        //后缀
        String suffix = riskCheckMain.getRiskCheckNo().trim() + ".doc";
        ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
        try {
            WordUtils.uploadFileByFtp("downloadFile/" + suffix, templatePath.toString(), params);
            WordUtils.uploadFileByFtp("downloadFile/" + prefix + suffix, noticeReportPath.toString(), mainResult);

            //是否需要生成pdf辅助文件
            if (generateAidPdfFile) {
                WordUtils.uploadFileByFtp("downloadFile/" + riskCheckMain.getRiskCheckNo().trim() + "_pdf.docx", pdfPath.toString(), params);
            }
        } catch (Exception e) {
            LOGGER.info("上传ftp失败" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("文件下载异常");
        }
        //构建下载路径
        HashMap<String, String> map = new HashMap<>();

        String dir = bundle.getString("saveRootPath") + bundle.getString("saveTypePath");
        String downloadPath = dir + "/downloadFile/";
        //通知书 报告 pdf文件路径
        map.put("notice", downloadPath + prefix + suffix);
        map.put("report", downloadPath + suffix);
        return map;
    }

    /**
     * @param riskCheckMain
     * @return java.lang.String
     * @Description 生成pdf
     * @Author wangwenjie
     * @Date 2019/3/27
     */
    public String generatePdf(RiskCheckMain riskCheckMain, UserInfo userInfo) {
        //生成word，再生成pdf
        this.generateWord(riskCheckMain, userInfo, true);
        String riskCheckNo = riskCheckMain.getRiskCheckNo().trim();
        ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
        try {
            //转换通知书和报告为pdf
            WordUtils.openOfficeConvertPDF2(riskCheckNo + "_pdf.docx", riskCheckNo + ".pdf");
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String dir = bundle.getString("saveRootPath") + bundle.getString("saveTypePath");
        String downloadPath = dir + "/downloadFile/";
        return downloadPath + riskCheckNo + ".pdf";
    }

    /**
     * @Description 根据巡检机构编号翻译对应机构为省级机构
     * @Author wangwenjie
     * @Params [checkComcode]
     * @Return java.lang.String
     * @Date 2020/2/26
     */
    public String translateProvinceNameByCheckComCode(String checkComcode) {
        List<RiskDcode> riskDcodeList = dataSourcesService.queryRiskDcodeList("riskFileAscName");
        for (RiskDcode riskDcode : riskDcodeList) {
            String codeCode = riskDcode.getId().getCodeCode();
            //优先返回计划单列市
            if (codeCode.equals(checkComcode.substring(0, 4))
                    && "2102,3302,3502,4403,3702".indexOf(checkComcode.substring(0, 4)) != -1) {
                return riskDcode.getCodeCname();
            }
        }
        for (RiskDcode riskDcode : riskDcodeList) {
            String codeCode = riskDcode.getId().getCodeCode();
            if (codeCode.startsWith(checkComcode.substring(0, 2))) {
                if ((checkComcode.substring(0, 2) + "00").equals(codeCode)) {
                    return riskDcode.getCodeCname();
                }
            }
        }
        System.out.println(" === 请检查riskdcode表中是否含有对应机构翻译 === ");
        return checkComcode;
    }
}
