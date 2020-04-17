package com.picc.riskctrl.riskins.service;

import com.google.common.collect.Lists;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.picc.riskctrl.base.BaseService;
import com.picc.riskctrl.common.dao.PrpDcompanyFkRepository;
import com.picc.riskctrl.common.dao.RiskReportSaleMainRepository;
import com.picc.riskctrl.common.jpa.condition.Restrictions;
import com.picc.riskctrl.common.jpa.vo.Criteria;
import com.picc.riskctrl.common.po.*;
import com.picc.riskctrl.common.schema.PrpDcompanyFk;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.common.utils.BeanCopyUtils;
import com.picc.riskctrl.common.utils.FTPUtil;
import com.picc.riskctrl.common.utils.QueryRule;
import com.picc.riskctrl.exception.ApiErrorDataException;
import com.picc.riskctrl.exception.RiskInsSaleServiceException;
import com.picc.riskctrl.exception.RiskNoCanNotFoundException;
import com.picc.riskctrl.intf.image.model.vo.ROOT;
import com.picc.riskctrl.riskins.dao.RiskReportMessageSendRepository;
import com.picc.riskctrl.riskins.dao.RiskReportSaleCorrectionRepository;
import com.picc.riskctrl.riskins.dao.RiskReportSaleImaTypeRepository;
import com.picc.riskctrl.riskins.dao.RiskReportSaleImageRepository;
import com.picc.riskctrl.riskins.po.RiskReportMessageSend;
import com.picc.riskctrl.riskins.vo.RiskInsRequestVo;
import com.picc.riskctrl.riskins.vo.RiskInsResponseVo;
import com.picc.riskctrl.riskins.vo.RiskReportSaleMainQueryVo;
import com.picc.riskctrl.riskins.vo.RiskReportSaleMainVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdfc.framework.web.ApiResponse;

import javax.jms.*;
import java.io.*;
import java.net.URL;
import java.util.*;


/**
 * @ClassName: RiskInsSaleService
 * @Author: 张日炜
 * @Date: 2020-01-16 09:41
 **/
@Service
@Transactional
public class RiskInsSaleService extends BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    @Autowired
    private RiskReportSaleMainRepository riskReportSaleMainRepository;
    @Autowired
    private RiskReportSaleImageRepository riskReportSaleImageRepository;
    @Autowired
    private RiskReportSaleCorrectionRepository riskReportSaleCorrectionRepository;
    @Autowired
    private DataSourcesService dataSourcesService;
    @Autowired
    private RiskReportSaleImaTypeRepository riskReportSaleImaTypeRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RiskInsJPushService riskInsJPushService;
    @Autowired
    private RiskReportMessageSendRepository riskReportMessageSendRepository;


    @Autowired
    private PrpDcompanyFkRepository prpDcompanyFkRepository;


    /**
     * @功能：查询风控（销售员版）基本信息
     * @author 马军亮
     * @param archivesNo
     *            照片档案号
     * @param companyname
     *            公司名称（被保险人）
     * @return RiskInsResponseVo
     * @throws @日期
     *             2018-5-9
     */
    public RiskInsResponseVo queryRiskReportSaleMain(String archivesNo, String companyname, int pageNo, int pageSize,
                                                     UserInfo userInfo) {
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();

        // String userCodeInfo = userInfo.getUserCode();
        
        try {
        	Page<RiskReportSaleMain> page = null;
        	Criteria<RiskReportSaleMain> criteria = new Criteria<>();
        	// 照片档案号
            if (StringUtils.isNotBlank(archivesNo)) {
                criteria.add(Restrictions.eq("archivesNo", archivesNo.trim()));
            }
            // 审核状态
            criteria.add(Restrictions.eq("checkUpFlag", "1"));
            // 企业名称
            if (StringUtils.isNotBlank(companyname)) {
                criteria.add(Restrictions.like("companyName", "%" + companyname.trim() + "%"));
            }
            Sort sort = Sort.by(Sort.Direction.DESC, "insertTimeForHis");
            page = riskReportSaleMainRepository.findAll(criteria,
                    PageRequest.of(pageNo, pageSize, sort));
            
//            Specification<RiskReportSaleMain> spec = new Specification<RiskReportSaleMain>() {
//                @Override
//                public Predicate toPredicate(Root<RiskReportSaleMain> root, CriteriaQuery<?> criteriaQuery,
//                    CriteriaBuilder criteriaBuilder) {
//                    List<Predicate> predicateList = new ArrayList<>();
//
//                    // 照片档案号
//                    if (StringUtils.isNotBlank(archivesNo)) {
//                        predicateList.add(criteriaBuilder.equal(root.get("archivesNo"), archivesNo));
//                    }
//                    // 审核状态
//                    predicateList.add(criteriaBuilder.equal(root.get("checkUpFlag"), "1"));
//                    // 企业名称
//                    if (StringUtils.isNotBlank(companyname)) {
//                        predicateList.add(criteriaBuilder.like(root.get("companyName"), "%" + companyname + "%"));
//                    }
//
//                    // 权限校验开始-----
//                    // SaaAPIService saaAPIService = new SaaAPIServiceImpl();
//                    // try {
//                    // String powerSQL = saaAPIService.addPower("riskcontrol", userCodeInfo, "riskins_query",
//                    // "this_.comCode",
//                    // "", "");
//                    // queryRule.addSql(powerSQL);
//                    // } catch (Exception e) {
//                    // LOGGER.info("addPower执行异常：" + e.getMessage(), e);
//                    // e.printStackTrace();
//                    // throw new RuntimeException("addPower执行异常:" + e);
//                    //
//                    // }
//                    // 权限校验结束-----
//                    Predicate[] arr = new Predicate[predicateList.size()];
//                    return criteriaBuilder.and(predicateList.toArray(arr));
//                }
//            };
//            Page<RiskReportSaleMain> page = riskReportSaleMainRepository.findAll(spec,
//                PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "insertTimeForHis")));

            Page<List> pageNew = null;
            List<RiskReportSaleMain> riskReportSaleMainList = page.getContent();
            List<RiskReportSaleMain> riskReportMainSaleListNew = new ArrayList<>();
            for (RiskReportSaleMain r : riskReportSaleMainList) {
                // 拷贝RiskReportMain
                r.setRiskReportSaleImaTypeList(null);
                RiskReportSaleMain rNew = new RiskReportSaleMain();
                BeanUtils.copyProperties(r, rNew);
                riskReportMainSaleListNew.add(rNew);
            }
            // 设置数据
            riskInsResponseVo.setDataList(riskReportMainSaleListNew);
            // 设置起始页码
            riskInsResponseVo.setPageNo(pageNo);
            // 设置行数
            riskInsResponseVo.setPageSize(pageSize);
            // 设置数据总条数
            riskInsResponseVo.setTotalCount(page.getTotalElements());
            // 设置总页数
            riskInsResponseVo.setTotalPage(page.getTotalPages());
            // 查询状态
            riskInsResponseVo.setStatus(1);
            riskInsResponseVo.setMessage("成功");
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            // 查询状态
            riskInsResponseVo.setStatus(0);
            riskInsResponseVo.setMessage(e.getMessage());
            throw new RuntimeException("查询异常:" + e);
        }
        return riskInsResponseVo;
    }

    /**
     * @author anqingsen
     * @功能：查询风控销售员版信息
     * @param archivesNo
     * @param userInfo
     * @param hostPath
     * @return RiskReportSaleMain 风控销售员版基本信息 @日期：2020-01-16
     */
    @SuppressWarnings("unused")
    public RiskReportSaleMain queryRiskReportSaleMain(String archivesNo, UserInfo userInfo, String hostPath) {
        RiskReportSaleMain saleMain = new RiskReportSaleMain();
        try {
            RiskReportSaleImage riskReportSaleImageTem = new RiskReportSaleImage();
            List<RiskReportSaleCorrection> riskReportSaleCorrections = new ArrayList<RiskReportSaleCorrection>();
            RiskReportSaleCorrectionId imageId = new RiskReportSaleCorrectionId();
            imageId.setArchivesNo(archivesNo);
            RiskReportSaleCorrection riskReportSaleCorrectionTem = new RiskReportSaleCorrection();
            riskReportSaleCorrectionTem.setId(imageId);
            riskReportSaleCorrections.add(riskReportSaleCorrectionTem);
            riskReportSaleImageTem.setRiskReportSaleCorrectionList(riskReportSaleCorrections);
            // 获取影像中对应照片档案号 的所有的图片信息
            List<RiskReportSaleImage> riskReportSaleImageAllList = achieveAllSaleImageInfo(archivesNo);
            RiskReportSaleMain riskReportSaleMain = null;
            if (StringUtils.isNotBlank(archivesNo)) {
                try {
                    Optional<RiskReportSaleMain> riskReportSaleMainOp = riskReportSaleMainRepository.findById(archivesNo);
                    riskReportSaleMain = riskReportSaleMainOp.orElseThrow(() -> new IllegalArgumentException("未查询到对应照片档案信息"));
                    riskReportSaleMain.setStatus(1);
                    riskReportSaleMain.setMessage("查询成功");
                } catch (Exception e) {
                    LOGGER.info("查询异常：" + e.getMessage(), e);
                    // 查询状态
                    if(riskReportSaleMain == null){
                        riskReportSaleMain = new RiskReportSaleMain();
                    }
                    riskReportSaleMain.setStatus(0);
                    riskReportSaleMain.setMessage(e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("查询异常:" + e);
                }

                BeanUtils.copyProperties(riskReportSaleMain, saleMain);
                if (riskReportSaleMain != null) {
                    List<RiskReportSaleImaType> riskReportSaleImaTypeList = new ArrayList<RiskReportSaleImaType>();

                    for (RiskReportSaleImaType riskReportSaleImaType : riskReportSaleMain.getRiskReportSaleImaTypeList()) {
                        RiskReportSaleImaType imaTypeTemp = new RiskReportSaleImaType();
//                        RiskReportSaleImaTypeId id = new RiskReportSaleImaTypeId();
                        BeanUtils.copyProperties(riskReportSaleImaType, imaTypeTemp);
//                        BeanUtils.copyProperties(riskReportSaleImaType.getId(), id);
//                        imaTypeTemp.setId(id);
                        // 获取该种类的第一张照片
                        if (null != riskReportSaleImageAllList && riskReportSaleImageAllList.size() > 0) {
                            for (RiskReportSaleImage riskReportSaleImage : riskReportSaleImageAllList) {
                                if (riskReportSaleImage.getId().getImageType()
                                        .equals(riskReportSaleImaType.getId().getImageType())) {
                                    // 组织照片路径的方式:(主机名+upload+名称地址 )
                                    if (null != riskReportSaleImage.getImageUrl()) {
                                        if (riskReportSaleImage.getImageUrl().indexOf("http") > -1) {
                                            imaTypeTemp.setUrl(riskReportSaleImage.getImageUrl());
                                        } else {
                                            imaTypeTemp.setUrl(hostPath + riskReportSaleImage.getImageUrl());
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                        List<RiskReportSaleImage> riskReportSaleImagelist = new ArrayList<RiskReportSaleImage>();
                        for (RiskReportSaleImage riskReportSaleImage : riskReportSaleImaType
                                .getRiskReportSaleImageList()) {
                            RiskReportSaleImage riskReportSaleImageTemp = new RiskReportSaleImage();
//                            RiskReportSaleImageId imageid = new RiskReportSaleImageId();
//                            BeanUtils.copyProperties(riskReportSaleImage.getId(), imageId);
                            BeanUtils.copyProperties(riskReportSaleImage, riskReportSaleImageTemp);
//                            riskReportSaleImageTemp.setId(imageid);
                            if (null != riskReportSaleImageTemp.getImageUrl()) {
                                if (riskReportSaleImageTemp.getImageUrl().indexOf("http") > -1) {
                                    riskReportSaleImageTemp.setUrlName(riskReportSaleImageTemp.getImageUrl());
                                } else {
                                    riskReportSaleImageTemp
                                            .setUrlName(hostPath + riskReportSaleImageTemp.getImageUrl());
                                }
                            }
                            List<RiskReportSaleCorrection> riskReportSaleCorrectionList =
                                    new ArrayList<>();
                            for (RiskReportSaleCorrection riskReportSaleCorrection : riskReportSaleImage
                                    .getRiskReportSaleCorrectionList()) {
                                RiskReportSaleCorrection riskReportSaleCorrectionTemp = new RiskReportSaleCorrection();
//                                RiskReportSaleCorrectionId correctionId = new RiskReportSaleCorrectionId();
//                                BeanUtils.copyProperties(riskReportSaleCorrection.getId(), correctionId);
                                BeanUtils.copyProperties(riskReportSaleCorrection, riskReportSaleCorrectionTemp);
//                                riskReportSaleCorrectionTemp.setId(correctionId);
                                // 整改意见照片路径赋值
                                if (StringUtils.isNotBlank(riskReportSaleCorrection.getNameBehind())) {
                                    if (null != riskReportSaleImageAllList && riskReportSaleImageAllList.size() > 0) {
                                        for (RiskReportSaleImage saleImage : riskReportSaleImageAllList) {
                                            if (saleImage.getId().getImageType()
                                                    .equals(riskReportSaleImage.getId().getImageName() + "_PG")) {
                                                String imageUrl = "";
                                                if (null != saleImage.getImageUrl()) {
                                                    if (saleImage.getImageUrl().indexOf("http") > -1) {
                                                        imageUrl = saleImage.getImageUrl();
                                                    } else {
                                                        imageUrl = hostPath + saleImage.getImageUrl();
                                                    }
                                                }
                                                if (saleImage.getId().getImageName()
                                                        .equals(riskReportSaleCorrection.getNameBefore())) {
                                                    riskReportSaleCorrectionTemp.setUrlBefore(imageUrl);
                                                } else if (saleImage.getId().getImageName()
                                                        .equals(riskReportSaleCorrection.getNameBehind())) {
                                                    riskReportSaleCorrectionTemp.setUrlBehind(imageUrl);
                                                }
                                            }
                                        }
                                    }
                                }
                                riskReportSaleCorrectionList.add(riskReportSaleCorrectionTemp);
                            }
                            riskReportSaleImageTemp.setRiskReportSaleCorrectionList(riskReportSaleCorrectionList);
                            riskReportSaleImagelist.add(riskReportSaleImageTemp);
                        }
                        imaTypeTemp.setRiskReportSaleImageList(riskReportSaleImagelist);
                        riskReportSaleImaTypeList.add(imaTypeTemp);
                        saleMain.setRiskReportSaleImaTypeList(riskReportSaleImaTypeList);
                    }

                } else {
                    saleMain = riskReportSaleMain;
                }
            } else {
                throw new RuntimeException("编号不能为空！");
            }
        } catch (Exception e) {
            LOGGER.info("查询风控销售员版信息：" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("查询风控报告销售员版信息：" + e);
        }
        return saleMain;
    }

    private List<RiskReportSaleImage> achieveAllSaleImageInfo(String archivesNo) {
        List<RiskReportSaleImage> riskReportSaleImages = new ArrayList<RiskReportSaleImage>();
        if (StringUtils.isNotBlank(archivesNo)) {
            try {
                Criteria<RiskReportSaleImage> criteria = new Criteria();
                criteria.add(Restrictions.eq("id.archivesNo", archivesNo));
                riskReportSaleImages = riskReportSaleImageRepository.findAll(criteria);
//                riskReportSaleImages = riskReportSaleImageRepository.findByArchivesNo(archivesNo);
            } catch (Exception e) {
                LOGGER.info("查询异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException("查询异常:" + e);
            }
        }
        return riskReportSaleImages;
    }

    /**
     * @功能：查询风控（销售员版）图片目录表有照片的节点信息
     * @author 马军亮
     * @param archivesNo
     * @throws @日期 2018-06-27
     * @modify 张日炜 2020-01-19
     */
    public List<Object[]> querySaleImageByArchivesNo_new(String archivesNo) {

        List<Object[]> saleImagesList;

        try {
            saleImagesList = riskReportSaleImageRepository.findAllByArchivesno(archivesNo);
        } catch (Exception e) {
            LOGGER.info("查询数据库异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询数据库异常:" + e);
        }
        return saleImagesList;
    }

    /**
     * @功能：查询整改历史信息
     * @author 安青森
     * @param
     * @日期：2017-01-04
     * @修改 liqiankun 20200217
     */
    public RiskInsResponseVo riskSuggestQuery(String archivesNo, String imageType, String imageName, String client,
                                              Integer pageNo, Integer pageSize, UserInfo userInfo, Integer serialNo, String hostPath) {

        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        // String key =
        // cacheManager.generateCacheKey("riskSuggestQuery",imageType,imageName,client,
        // pageNo,pageSize,archivesNo);
        // Object object = cacheManager.getCache(key);
        // if (object == null) {
        try {

//			QueryRule queryRule = QueryRule.getInstance();
//			Page page = new Page();

            Criteria<RiskReportSaleCorrection> criteria = new Criteria<>();
            Sort sort = null;
            // 多条件进行排序
            List<Order> orders = new ArrayList<Order>();
            // 照片档案号
            if (StringUtils.isNotBlank(archivesNo)) {
//				queryRule.addEqual("id.archivesNo", archivesNo.trim());
                criteria.add(Restrictions.eq("id.archivesNo", archivesNo.trim()));
            }

            // 图片类型
            if (StringUtils.isNotBlank(imageType)) {
//				queryRule.addEqual("id.imageType", imageType.trim());
                criteria.add(Restrictions.eq("id.imageType", imageType.trim()));
            }

            // 图片名
            if (StringUtils.isNotBlank(imageName)) {
//				queryRule.addEqual("id.imageName", imageName.trim());
                criteria.add(Restrictions.eq("id.imageName", imageName.trim()));
            }

            // 有无整改 null
            if (null != serialNo) {
//				queryRule.addEqual("id.serialNo", serialNo);
                criteria.add(Restrictions.eq("id.serialNo", serialNo));
            }

//			queryRule.addDescOrder("insertTimeForHis");
            orders.add(new Order(Direction.DESC, "insertTimeForHis"));

//			page = databaseDao.findPage(RiskReportSaleCorrection.class, queryRule, pageNo, pageSize);
            sort = new Sort(orders);
            Page<RiskReportSaleCorrection> page =
                    riskReportSaleCorrectionRepository.findAll(criteria, PageRequest.of(pageNo - 1, pageSize, sort));
            if (page != null) {
                Page pageNew = null;
//				List<RiskReportSaleCorrection> riskReportSaleCorrectionList = page.getResult();
                List<RiskReportSaleCorrection> riskReportSaleCorrectionList = page.getContent();
                List<RiskReportSaleCorrection> riskReportSaleCorrectionListNew = new ArrayList<RiskReportSaleCorrection>();
                for (RiskReportSaleCorrection r : riskReportSaleCorrectionList) {
                    // 拷贝RiskReportSaleCorrection
                    RiskReportSaleCorrection rNew = new RiskReportSaleCorrection();
                    BeanUtils.copyProperties(r, rNew);

                    // 整改标记翻译
                    if ("1".equals(rNew.getCorrectionFlag())) {
                        rNew.setCorrectionFlag("已完成整改");
                    } else if ("0".equals(rNew.getCorrectionFlag())) {
                        rNew.setCorrectionFlag("未完成整改");
                    } else if ("2".equals(rNew.getCorrectionFlag())) {
                        rNew.setCorrectionFlag("整改不合格");
                    }
                    // 有无反馈翻译
                    if ("1".equals(rNew.getFeedbackFlag())) {
                        rNew.setFeedbackFlag("有");
                    } else if ("0".equals(rNew.getFeedbackFlag())) {
                        rNew.setFeedbackFlag("无");
                    }
                    // 险种原因
                    if (StringUtils.isNotBlank(rNew.getRiskType())) {
                        RiskDcode riskDcode = dataSourcesService.queryRiskDcode("riskType", "", rNew.getRiskType(),
                                "1");
                        rNew.setRiskType(riskDcode.getCodeCname());
                    }

                    // 拷贝RiskReportSaleCorrectionId
                    RiskReportSaleCorrectionId idNew = new RiskReportSaleCorrectionId();
                    BeanUtils.copyProperties(r.getId(), idNew);
                    rNew.setId(idNew);

                    List<RiskReportSaleCorrection> riskReportSaleCorrectionListTemp = new ArrayList<RiskReportSaleCorrection>();
                    riskReportSaleCorrectionListTemp.add(rNew);
                    // 根据照片档案号和Node_id来获取影像系统中照片信息
                    RiskReportSaleImage riskReportSaleImage = new RiskReportSaleImage();
                    riskReportSaleImage.setRiskReportSaleCorrectionList(riskReportSaleCorrectionListTemp);
                    List<RiskReportSaleImage> riskReportSaleImages = querySaleImageList(rNew.getId().getArchivesNo(),
                            rNew.getId().getImageName() + "_PG");
//					RiskReportSaleImage riskReportSaleImage_new =queryRiskReportSaleImage(rNew.getId().getArchivesNo(), rNew.getId().getImageName()+"_PG", "");
                    if (null != riskReportSaleImages && riskReportSaleImages.size() > 0) {
                        for (RiskReportSaleImage reportSaleImage : riskReportSaleImages) {
                            String remark = reportSaleImage.getId().getImageName();
//							String remark = reportSaleImage.getImageUrl().substring(
//									reportSaleImage.getImageUrl().lastIndexOf("/")+1,reportSaleImage.getImageUrl().lastIndexOf(".jpg")) ;
                            // 影像上传前后的路径的判断
                            String imageUrl = "";
                            if (reportSaleImage.getImageUrl().indexOf("http") > -1) {
                                imageUrl = reportSaleImage.getImageUrl();
                            } else {
                                imageUrl = hostPath + reportSaleImage.getImageUrl();
                            }

                            if (StringUtils.isNotBlank(rNew.getNameBefore())
                                    && rNew.getNameBefore().trim().equals(remark)) {
                                rNew.setUrlBefore(imageUrl);
                            } else if (StringUtils.isNotBlank(rNew.getNameBehind())
                                    && rNew.getNameBehind().trim().equals(remark)
                                    && "0".equals(rNew.getFeedbackFlag())) {
                                rNew.setUrlBefore(imageUrl);
                            } else if (StringUtils.isNotBlank(rNew.getNameBehind())
                                    && rNew.getNameBehind().trim().equals(remark)) {
                                rNew.setUrlBehind(imageUrl);
                            }
                        }
                    }
//					String remark = riskReportSaleImage_new.getImageUrl().substring(
//							riskReportSaleImage_new.getImageUrl().lastIndexOf("/")+1,riskReportSaleImage_new.getImageUrl().lastIndexOf(".jpg")) ;
//					ROOT rootReturn = imageService.achieveImageInfo(riskReportSaleImage, userInfo, "");
//					List<PAGE> pages = rootReturn.getMETA_DATAS().getMETA_DATA().getTREE_VIEW().get(0).getPAGES();
                    // 把调用的影像信息中照片信息赋给
//					for (PAGE pagTemp : pages) {
//						if (StringUtils.isNotBlank(rNew.getNameBefore())
//								&& rNew.getNameBefore().trim().equals(pagTemp.getRemark())) {
//							rNew.setUrlBefore(pagTemp.getPAGE_URL());
//						} else if (StringUtils.isNotBlank(rNew.getNameBehind())
//								&& rNew.getNameBehind().trim().equals(pagTemp.getRemark())
//								&& "0".equals(rNew.getFeedbackFlag())) {
//							rNew.setUrlBefore(pagTemp.getPAGE_URL());
//						} else if (StringUtils.isNotBlank(rNew.getNameBehind())
//								&& rNew.getNameBehind().trim().equals(pagTemp.getRemark())) {
//							rNew.setUrlBehind(pagTemp.getPAGE_URL());
//						}
//					}
//					riskReportSaleCorrectionListNew.add(rNew);

                    riskReportSaleCorrectionListNew.add(rNew);
                }
                pageNew = new PageImpl<>(riskReportSaleCorrectionListNew, PageRequest.of(pageNo - 1, pageSize), page.getTotalElements());
//				pageNew = new Page(pageSize, page.getTotalElements(), pageSize, riskReportSaleCorrectionListNew);

                // 设置数据
                riskInsResponseVo.setDataList(pageNew.getContent());
                // 设置数据总条数
                riskInsResponseVo.setTotalCount(page.getTotalElements());
                // 设置总页数
                riskInsResponseVo.setTotalPage(page.getTotalPages());
                if (client.contains("Android") || client.contains("iPhone") || client.contains("iPad")) {
                    if (pageNew.getContent().size() == 1) {
                        riskInsResponseVo.setMessage("查询成功");
                        riskInsResponseVo.setStatus(1);
                    } else if (pageNew.getContent().size() == 0) {
                        riskInsResponseVo.setMessage("未查询到该条数据");
                        riskInsResponseVo.setStatus(2);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.info("查询出现异常：" + e.getMessage(), e);
            riskInsResponseVo.setMessage("查询出现异常");
            riskInsResponseVo.setStatus(0);
            e.printStackTrace();
            throw new RuntimeException("查询出现异常:" + e);
        }
        // cacheManager.putCache(key, riskInsResponseVo);
        // }else {
        // riskInsResponseVo = (RiskInsResponseVo) object;
        // }
        return riskInsResponseVo;
    }

    // 获取image表中数据的集合 addby liqiankun 20200217
    public List<RiskReportSaleImage> querySaleImageList(String archivesNo, String imageType) {
        Criteria<RiskReportSaleImage> criteria = new Criteria();
        List<RiskReportSaleImage> riskReportSaleImageList = new ArrayList<RiskReportSaleImage>();
        if (StringUtils.isNotBlank(archivesNo)) {
            criteria.add(Restrictions.eq("id.archivesNo", archivesNo.trim()));
        }
        if (StringUtils.isNotBlank(imageType)) {
            criteria.add(Restrictions.eq("id.imageType", imageType.trim()));
        }
        try {
//			riskReportSaleImageList = databaseDao.findAll(RiskReportSaleImage.class, queryRule);
            riskReportSaleImageList = riskReportSaleImageRepository.findAll(criteria);
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }
        return riskReportSaleImageList;
    }

    /**
     * @功能：风控意见是否完成整改
     * @author liqiankun
     * @param
     * @throws Exception
     * @throws
     * @日期 2018-1-5  modifyby liqiankun 20200217
     */
    public RiskReportSaleImage saveRiskInsSaleDetailModel(RiskReportSaleImage riskReportSaleImage, UserInfo userInfo,
                                                          String hostPath) throws Exception {
        // 风控意见是否完成整改
        // 已完成整改：直接提交到影像系统进行替换，
        // 未完成整改：重新执行打回操作
        String archivesNo = null, imageType = null, imageName = null;
        String pageId = "";
        boolean imageFlag = false;
        if (null != riskReportSaleImage.getRiskReportSaleCorrectionList()
                && riskReportSaleImage.getRiskReportSaleCorrectionList().size() > 0) {
            archivesNo = riskReportSaleImage.getRiskReportSaleCorrectionList().get(0).getId().getArchivesNo();
            imageType = riskReportSaleImage.getRiskReportSaleCorrectionList().get(0).getId().getImageType();
            imageName = riskReportSaleImage.getRiskReportSaleCorrectionList().get(0).getId().getImageName();
        }
        // 获取RiskReportSaleImage 的集合
        List<RiskReportSaleImage> riskReportSaleImages = this.querySaleImageList(archivesNo, imageType);

        // 根据照片档案号和Node_id来获取影像系统中照片信息
//		ROOT rootReturn = imageService.achieveImageInfo(riskReportSaleImage, userInfo, "ZG");
        int sum = queryRiskReportSaleCorrectionCount(archivesNo, imageName);
//		List<PAGE> pages = rootReturn.getMETA_DATAS().getMETA_DATA().getTREE_VIEW().get(0).getPAGES();
        // 根据名字来获取相应的数据
        // RiskReportSaleImage riskReportSaleImageOld = queryRiskReportSaleImage(
        // archivesNo, imageType, imageName);
        // 获取照片类型表中的数据
        RiskReportSaleImaType riskReportSaleImaTypeOld = this.queryRiskReportSaleImaType(archivesNo, imageType);

        RiskReportSaleImage riskReportSaleImageNew = new RiskReportSaleImage();

        RiskReportSaleImaType riskReportSaleImaTypeNew = new RiskReportSaleImaType();

        RiskReportSaleMain riskReportSaleMain = this.queryRiskReportSaleMain(archivesNo, userInfo, hostPath);
        List<RiskReportSaleImaType> list = riskReportSaleMain.getRiskReportSaleImaTypeList();
        if (StringUtils.isNotBlank(imageType)) {
            for (RiskReportSaleImaType riskReportSaleImaType : list) {
                if (StringUtils.equals(imageType, riskReportSaleImaType.getId().getImageType())) {
                    riskReportSaleImaTypeNew = riskReportSaleImaType;
                    // for (RiskReportSaleImage riskReportSaleImageVo : riskReportSaleImaType
                    // .getRiskReportSaleImageList()) {
                    // if (imageName.trim().equals(
                    // riskReportSaleImageVo.getId().getImageName())) {
                    // riskReportSaleImageNew = riskReportSaleImageVo;
                    // }
                    // }
                }
            }
        }
        ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
//		List<RiskReportSaleImage>  riskReportSaleImageAllList = achieveAllSaleImageInfo(archivesNo);
//		String path = bundle.getString("saveRootPath")+bundle.getString("saveTypePath");
        // 判断是否已整改
        if ("1".equals(riskReportSaleImage.getModifyFlag().trim())) {
            // 存在风险照片数量
            Integer imageRepulseSum = riskReportSaleImaTypeNew.getImageRepulseSum();
            if (null != imageRepulseSum && riskReportSaleImaTypeNew.getImageRepulseSum() > 0) {
                riskReportSaleImaTypeNew.setImageRepulseSum(imageRepulseSum - 1);
            } else {
                // throw new E
            }

            for (RiskReportSaleImage riskReportSaleImageVo : riskReportSaleImaTypeNew.getRiskReportSaleImageList()) {
                if (StringUtils.equals(imageName, riskReportSaleImageVo.getId().getImageName())) {
                    riskReportSaleImageNew = riskReportSaleImageVo;
                    // 替换影像的路径
                    riskReportSaleImageNew.setImageUrl(bundle.getString("saveTypePath").substring(1) + "/" + archivesNo
                            + "/" + imageType + "/" + imageName + ".jpg");
                    riskReportSaleImageNew.setThumUrl(null);
//					if(null!=riskReportSaleImageNew.getPageId()){
//						riskReportSaleImageNew.setPageId(riskReportSaleImageNew.getPageId());
//					}
                    riskReportSaleImageNew.setTitle(riskReportSaleImage.getTitle());
                }
            }

            // 已完成整改
            riskReportSaleImageNew.setStateFlag("0");
            if (null != riskReportSaleImageNew.getRiskReportSaleCorrectionList()
                    && riskReportSaleImageNew.getRiskReportSaleCorrectionList().size() > 0) {
                for (int i = 0; i < riskReportSaleImageNew.getRiskReportSaleCorrectionList().size(); i++) {
                    RiskReportSaleCorrection riskReportSaleCorrection = riskReportSaleImageNew
                            .getRiskReportSaleCorrectionList().get(i);
                    if ("0".equals(riskReportSaleCorrection.getCorrectionFlag())) {
                        riskReportSaleCorrection.setCorrectionFlag("1");
                        riskReportSaleCorrection.setFeedbackFlag("1");
                        riskReportSaleCorrection.setFeedbackConfirm(
                                riskReportSaleImage.getRiskReportSaleCorrectionList().get(i).getFeedbackConfirm());
                        // 完成整改之后，把整改后的图片的影像地址传给nameBefore,并且把新的影像地址传给影像系统(先进行删除然后在进行增加)
                        // 把调用的影像信息中照片信息赋给
                        if (null != riskReportSaleImages && riskReportSaleImages.size() > 0) {
                            for (RiskReportSaleImage saleImage : riskReportSaleImages) {
                                if (StringUtils.equals(imageName, saleImage.getId().getImageName())) {
                                    riskReportSaleCorrection.setNameBefore(Integer.toString(sum));
                                    // 删除的图片名称
//									riskReportSaleImageNew.getId().setImageName(saleImage.getId().getImageName());
                                    pageId = saleImage.getPageId();
                                }
                            }
                        }
//						for (PAGE page : pages) {
//							if (imageName.trim().equals(page.getRemark())) {
//								// riskReportSaleCorrection.setUrlBefore(page
//								// .getRemark());
//								// riskReportSaleCorrection.setNameBefore(page.getRemark());
//								riskReportSaleCorrection.setNameBefore(Integer.toString(sum));
//								pageId = page.getId();
//							}
//						}
                    }
                }
                // 获取整改意见条数
                try {
                    // 删除本地的单个文件
//					String message=deleteSingleImage(riskReportSaleImageNew);
                    // 影像删除
//					ROOT root = imageService.deleteImage(riskReportSaleImageNew, userInfo, pageId);
                    ROOT root = new ROOT();
                    if ("1".equals(root.getRESPONSE_CODE())) {
                        System.out.println("重新上传影像！");
                        // 如果删除成功，则进行影像重新上传
                        // “ZG”整改
//					if("success".equals(message)){
                        if (StringUtils.isNotBlank(riskReportSaleImage.getUrlAfter())) {
                            // beforeString="http://10.10.1.61:6011/SunTRM/servlet/GetImage?ZGF0ZT0yMDE4MDIxMSZmaWxlX25hbWU9L2hvbWUvbWlkZGxld2FyZS9FQ01Gb2xkZXIvdGVtcDYwMDEvbXlzZW5kL3Jpc2tDb250cm9sLzIwMTgvMDIvMDkvMTQvODkvRTY0RjQwNThBNTdDMkEzQzZEMjYyQjk0NzBBMjE1REJfMS9DQjMwM0JBNC1CRjc3LTFDMjctNDAwMC1GRUI1MEMxODdGMEEuanBn";
                            // String

                            // riskReportSaleImageNew.setUrlName("http://10.10.1.61:6011/SunTRM/servlet/GetImage?ZGF0ZT0yMDE4MDIxMSZmaWxlX25hbWU9L2hvbWUvbWlkZGxld2FyZS9FQ01Gb2xkZXIvdGVtcDYwMDEvbXlzZW5kL3Jpc2tDb250cm9sLzIwMTgvMDIvMDkvMTQvODkvRTY0RjQwNThBNTdDMkEzQzZEMjYyQjk0NzBBMjE1REJfMS9ENzY4N0U0OS04NEE1LTEwQ0ItN0QxQi1BODkwQzk2RThEOEMuanBn");
                            riskReportSaleImageNew.setUrlName(riskReportSaleImage.getUrlAfter());
                        }
                        // 在服务器下增加一个照片
//						createImage(riskReportSaleImageNew,hostPath, "");
//						riskReportSaleI	mageNew = imageService.uploadImage(riskReportSaleImageNew, userInfo, "", "ZG");
                        riskReportSaleImageNew.setRepulseReason(null);
                        riskReportSaleImageNew.setRiskType(null);
                        riskReportSaleImageNew.setRiskSuggest(null);
                        imageFlag = true;
//					}					
                    } else {
                        throw new Exception("影像 删除失败！");
                    }
                } catch (Exception e) {
                    LOGGER.info("影像交互异常：" + e.getMessage(), e);
                    e.printStackTrace();
                    imageFlag = false;
                    throw new RuntimeException(e);
                }

            }
        } else if ("0".equals(riskReportSaleImage.getModifyFlag().trim())) {
            for (RiskReportSaleImage riskReportSaleImageVo : riskReportSaleImaTypeNew.getRiskReportSaleImageList()) {
                if (StringUtils.equals(imageName, riskReportSaleImageVo.getId().getImageName())) {
                    riskReportSaleImageNew = riskReportSaleImageVo;
                    // 替换影像的路径
                    riskReportSaleImageNew.setImageUrl(bundle.getString("saveTypePath").substring(1) + "/" + archivesNo
                            + "/" + imageType + "/" + imageName + ".jpg");
                    riskReportSaleImageNew.setThumUrl(null);
//					if(null!=riskReportSaleImageNew.getPageId()){
//						riskReportSaleImageNew.setPageId(riskReportSaleImageNew.getPageId()+"delete");
//					}					
                    riskReportSaleImageNew.setTitle(riskReportSaleImage.getTitle());
                }
            }
            // 未整改
            riskReportSaleImageNew.setStateFlag("1");
            String feedbackConfirm = "";
            RiskReportSaleCorrection riskReportSaleCorrectionBehind = new RiskReportSaleCorrection();
            if (null != riskReportSaleImageNew.getRiskReportSaleCorrectionList()
                    && riskReportSaleImageNew.getRiskReportSaleCorrectionList().size() > 0) {
                for (int i = 0; i < riskReportSaleImageNew.getRiskReportSaleCorrectionList().size(); i++) {
                    RiskReportSaleCorrection riskReportSaleCorrectionOld = riskReportSaleImageNew
                            .getRiskReportSaleCorrectionList().get(i);
                    if ("0".equals(riskReportSaleCorrectionOld.getCorrectionFlag())) {

                        riskReportSaleCorrectionBehind = riskReportSaleCorrectionOld;
                        feedbackConfirm = riskReportSaleImage.getRiskReportSaleCorrectionList().get(i)
                                .getFeedbackConfirm();
                        riskReportSaleCorrectionOld.setCorrectionFlag("2");
                        riskReportSaleCorrectionOld.setFeedbackFlag("1");
                        riskReportSaleCorrectionOld.setFeedbackConfirm(feedbackConfirm);
                        // 把调用的影像信息中照片信息赋给
                        if (null != riskReportSaleImages && riskReportSaleImages.size() > 0) {
                            for (RiskReportSaleImage saleImage : riskReportSaleImages) {
                                if (StringUtils.equals(imageName, saleImage.getId().getImageName())) {
                                    riskReportSaleCorrectionOld.setNameBefore(Integer.toString(sum));
                                    //
//						    			riskReportSaleImageNew.getId().setImageName(saleImage.getId().getImageName());
                                    pageId = saleImage.getPageId();
                                }
                            }
                        }
//						for (PAGE page : pages) {
//							if (imageName.trim().equals(page.getRemark())) {
//								// riskReportSaleCorrection.setUrlBefore(page
//								// .getRemark());
//								// riskReportSaleCorrectionOld.setNameBefore(page.getRemark());
//								riskReportSaleCorrectionOld.setNameBefore(Integer.toString(sum));
//								pageId = page.getId();
//							}
//						}
                    }
                }
            }
            try {
//				String message=deleteSingleImage(riskReportSaleImageNew);
                // 影像删除
//				ROOT root = imageService.deleteImage(riskReportSaleImageNew, userInfo, pageId);
                ROOT root = new ROOT();
                if ("1".equals(root.getRESPONSE_CODE())) {
                    System.out.println("重新上传影像！");
                    // 如果删除成功，则进行影像重新上传
                    // “ZG”整改
//					if("success".equals(message)){
                    if (StringUtils.isNotBlank(riskReportSaleImage.getUrlAfter())) {
                        // String
                        // beforeString="http://10.10.1.61:6011/SunTRM/servlet/GetImage?ZGF0ZT0yMDE4MDIxMSZmaWxlX25hbWU9L2hvbWUvbWlkZGxld2FyZS9FQ01Gb2xkZXIvdGVtcDYwMDEvbXlzZW5kL3Jpc2tDb250cm9sLzIwMTgvMDIvMDkvMTQvODkvRTY0RjQwNThBNTdDMkEzQzZEMjYyQjk0NzBBMjE1REJfMS9DQjMwM0JBNC1CRjc3LTFDMjctNDAwMC1GRUI1MEMxODdGMEEuanBn";

                        // riskReportSaleImageNew.setUrlName(beforeString);
                        riskReportSaleImageNew.setUrlName(riskReportSaleImage.getUrlAfter());
                    } else {
                        throw new Exception("请重新上传照片！");
                    }
//						createImage(riskReportSaleImageNew,hostPath, "");
//					}					
//					riskReportSaleImageNew = imageService.uploadImage(riskReportSaleImageNew, userInfo, "", "ZG");
                    imageFlag = true;
                } else {
                    throw new Exception("影像 删除失败！");
                }
            } catch (Exception e) {
                LOGGER.info("影像交互异常：" + e.getMessage(), e);
                e.printStackTrace();
                imageFlag = false;
                throw new RuntimeException(e);
            }
            RiskReportSaleCorrection riskReportSaleCorrection = new RiskReportSaleCorrection();
            RiskReportSaleCorrectionId id = new RiskReportSaleCorrectionId();
            id.setArchivesNo(archivesNo);
            id.setImageType(imageType);
            id.setImageName(imageName);
            Integer serialNoMax = getSaleCorrectionNum(archivesNo, imageType, imageName);
            id.setSerialNo(serialNoMax);
            // try {
            // id.setSerialNo(sequenceGenerator.nextRechargeOrderSeq());
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
            riskReportSaleCorrection.setId(id);
            riskReportSaleCorrection.setTitle(riskReportSaleImage.getRiskReportSaleCorrectionList().get(0).getTitle());
            riskReportSaleCorrection.setFeedbackFlag("0");
            riskReportSaleCorrection.setCorrectionFlag("0");
            riskReportSaleCorrection.setSubmitDate(new Date());
            riskReportSaleCorrection.setOperateCode(userInfo.getUserCode());
            riskReportSaleCorrection.setContent(feedbackConfirm);
            if (null != riskReportSaleImages && riskReportSaleImages.size() > 0) {
                for (RiskReportSaleImage saleImage : riskReportSaleImages) {
                    if (StringUtils.equals(imageName, saleImage.getId().getImageName())) {
                        if (StringUtils.isNotBlank(riskReportSaleCorrectionBehind.getNameBehind())) {
                            riskReportSaleCorrection.setNameBefore(riskReportSaleCorrectionBehind.getNameBehind());
                        } else {
                            try {
                                throw new Exception("尚未重新上传照片！");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
//			for (PAGE page : pages) {
//				if (imageName.trim().equals(page.getRemark())) {
//					// riskReportSaleCorrection.setUrlBefore(page.getRemark());
//					// riskReportSaleCorrection.setNameBefore(page.getRemark());
//					// 假如整改后的照片有值则，则点击未整改替换以前照片，否则使用原来的照片
//					if (StringUtils.isNotBlank(riskReportSaleCorrectionBehind.getNameBehind())) {
//						riskReportSaleCorrection.setNameBefore(riskReportSaleCorrectionBehind.getNameBehind());
//					} else {
//						try {
//							throw new Exception("尚未重新上传照片！");
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
            riskReportSaleImageNew.getRiskReportSaleCorrectionList().add(riskReportSaleCorrection);
            /**
             * 打回照片时,保存信息到信息推送表
             */
            RiskReportMessageSend riskReportMessageSend = new RiskReportMessageSend();
            // 业务号：照片档案号+“_”+照片名称+“_”+序号
            riskReportMessageSend.setBusinessNo(archivesNo + "_" + riskReportSaleCorrection.getId().getImageName() + "_"
                    + riskReportSaleCorrection.getId().getSerialNo());
            // 打回标志或者批改标志:"DH","PG"
            riskReportMessageSend.setBusinessType("DH");
            riskReportMessageSend.setRelationNo(archivesNo);
            riskReportMessageSend.setMobileFlag(riskReportSaleMain.getMobileFlag());
            // 标题和内容
            riskReportMessageSend.setTitle("您有一条待办任务");
            riskReportMessageSend.setMessage(archivesNo + "照片档案号被打回，请处理");
            riskReportMessageSend.setStateFlag("0");
            // 账户account
            if (StringUtils.isNotBlank(riskReportSaleMain.getExplorer())) {
                riskReportMessageSend.setUserCode(riskReportSaleMain.getExplorer());
                // 向手机端推送消息
                String result = "";
                try {
                    result = riskInsJPushService.singleAccountPush(riskReportMessageSend);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(result);
                if (StringUtils.isNotBlank(result)) {
                    JSONObject resData = JSONObject.fromObject(result);
                    if (resData.has("error")) {
                        System.out.println("针对账户为" + riskReportMessageSend.getUserCode() + "的信息推送失败！");
                        JSONObject error = JSONObject.fromObject(resData.get("error"));
                        System.out.println("错误信息为：" + error.get("message").toString());
                        riskReportMessageSend.setStateFlag("2");
                    } else {
                        System.out.println("针对别名为" + riskReportMessageSend.getUserCode() + "的信息推送成功！");
                        riskReportMessageSend.setStateFlag("1");
                    }
                }
            } else {
                try {
                    throw new Exception("账户account不能为空！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 将信息保存到风控信息推送表中
            try {
//				databaseDao.save(RiskReportMessageSend.class, riskReportMessageSend);
                riskReportMessageSendRepository.save(riskReportMessageSend);
            } catch (Exception e) {
                LOGGER.info("与数据库交互异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if (imageFlag) {
            try {
//				PojoMerge pm = new PojoMerge(databaseDao);
                try {
//					pm.mergeObject(riskReportSaleImaTypeNew, riskReportSaleImaTypeOld, true);
                    // 更新，知道那个单子那种类型之后进行更新
                    riskReportSaleImaTypeRepository.save(riskReportSaleImaTypeNew);
                } catch (Exception e) {
                    LOGGER.info("执行PojoMerge方法异常：" + e.getMessage(), e);
                    e.printStackTrace();
                    throw new RuntimeException("执行PojoMerge方法异常:" + e);
                }
            } catch (Exception e) {
                LOGGER.info("执行PojoMerge方法异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            // 将表中的非上传影像的image表中信息上传影像(如果pageID为delete则我们应该先删除后上传影像)
            List<RiskReportSaleImage> reportSaleImageAllList = this.achieveAllSaleImageInfo(archivesNo);
            Map<String, RiskDcode> map = dataSourcesService.queryAllImageCategory("riskReportTree002");
            List<RiskReportSaleImage> imageNew = new ArrayList<RiskReportSaleImage>();
            String imageNamePG = imageName + "_PG";
            if (null != reportSaleImageAllList && reportSaleImageAllList.size() > 0) {
                for (RiskReportSaleImage saleImageUpload : reportSaleImageAllList) {
                    // 存在PG并且不是影像的url地址
                    if (imageNamePG.equals(saleImageUpload.getId().getImageType())
                            && saleImageUpload.getImageUrl().indexOf("PG") > -1
                            && saleImageUpload.getImageUrl().indexOf("http") == -1) {
                        // 图片地址
                        saleImageUpload.setUrlName(hostPath + saleImageUpload.getImageUrl());
                        String imageTypeBefore = saleImageUpload.getId().getImageType().substring(0,
                                saleImageUpload.getId().getImageType().length() - 5);
                        saleImageUpload.setTitle(map.get(imageTypeBefore).getCodeCname());

//						RiskReportSaleImage saleImage = imageService.uploadImage(saleImageUpload, userInfo, "", "");
                        RiskReportSaleImage saleImage = new RiskReportSaleImage();
                        saleImageUpload.setImageUrl(saleImage.getImageUrl());
                        saleImageUpload.setThumUrl(saleImage.getThumUrl());
                        saleImageUpload.setPageId(saleImage.getPageId());
                        imageNew.add(saleImageUpload);
                    }
                }
            }
            try {
                // 更新image表中数据信息
//				databaseDao.saveAll(RiskReportSaleImage.class, imageNew);
                riskReportSaleImageRepository.saveAll(imageNew);
            } catch (Exception e) {
                LOGGER.info("与数据库交互异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                FTPUtil ftp = new FTPUtil();
                ftp.removeFile(archivesNo + "/" + imageNamePG);
                ftp.close();
//				 riskInsImageService.deleteFile(new File(path + "/" + archivesNo+"/"+imageNamePG));
            }
        } else {
            throw new Exception("影像交互失败！");
        }
        return riskReportSaleImageNew;
    }

    // 获取整改意见条数
    public int queryRiskReportSaleCorrectionCount(String archivesNo, String imageName) {
        int sum = 0;
        try {
            Criteria<RiskReportSaleCorrection> criteria = new Criteria();
            if (StringUtils.isNotBlank(archivesNo) &&
                    StringUtils.isNotBlank(imageName)) {
                criteria.add(Restrictions.eq("id.archivesNo", archivesNo.trim()));
                criteria.add(Restrictions.eq("id.imageName", imageName.trim()));
                sum = (int) riskReportSaleCorrectionRepository.count(criteria);
            }
//				sum = (int) databaseDao.getCount(
//						"select count(*) from RiskReportSaleCorrection a where a.id.archivesNo=? and a.id.imageName=?",
//						archivesNo, imageName);
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }
        return sum;
    }

    /**
     * @功能：查询图片目录表信息
     * @author liqiankun
     * @param RiskReportSaleImaType 图片目录表信息
     * @throws
     * @日期 2018-1-5  modifyby liqiankun 20200217
     */
    public RiskReportSaleImaType queryRiskReportSaleImaType(String archivesNo, String imageType) {
        // String key = cacheManager.generateCacheKey("queryRiskReportSaleImaType",
        // imageType,archivesNo);
        // Object object = cacheManager.getCache(key);
        Criteria<RiskReportSaleImaType> criteria = new Criteria();
        RiskReportSaleImaType riskReportSaleImaType = new RiskReportSaleImaType();
        // if (object == null) {
//			QueryRule queryRule = QueryRule.getInstance();
        if (StringUtils.isNotBlank(archivesNo)) {
//				queryRule.addEqual("id.archivesNo", archivesNo.trim());
            criteria.add(Restrictions.eq("id.archivesNo", archivesNo.trim()));
        }
        if (StringUtils.isNotBlank(imageType)) {
//				queryRule.addEqual("id.imageType", imageType.trim());
            criteria.add(Restrictions.eq("id.imageType", imageType.trim()));
        }

        try {
//				riskReportSaleImaType = databaseDao.findUnique(RiskReportSaleImaType.class, queryRule);
            riskReportSaleImaType = riskReportSaleImaTypeRepository.findOne(criteria).get();
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询出现异常:" + e);
        }
        // cacheManager.putCache(key, riskReportSaleImaType);
        // }else {
        // riskReportSaleImaType = (RiskReportSaleImaType) object;
        // }
        return riskReportSaleImaType;
    }

    /**
     * @功能：获取整改意见表的自增主键
     * @author liqiankun
     * @param
     * @throws @日期 2018-2-5
     */
    public Integer getSaleCorrectionNum(String archivesNo, String imageType, String imageName) {
        // archivesNo="RC00332000000201800239";
        // imageType="2.1";
        // imageName="2.1.1";
        String sql = "";
        Integer serialNo = 1;
        if (StringUtils.isNotBlank(archivesNo) && StringUtils.isNotBlank(imageType)
                && StringUtils.isNotBlank(imageName)) {
            sql = "select max(SERIALNO) from  RiskReport_saleCorrection this_  where this_.ARCHIVESNO='"
                    + archivesNo.trim() + "' and this_.IMAGETYPE='" + imageType.trim() + "' and this_.IMAGENAME='"
                    + imageName.trim() + "'";
        }
        List<Map<String, Object>> maxSerialNo = null;
        try {
            maxSerialNo = jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }
        if (null != maxSerialNo && maxSerialNo.size() > 0 && null != maxSerialNo.get(0).get("(max)")) {
            return (Integer) maxSerialNo.get(0).get("(max)") + 1;
        } else {
            return serialNo;
        }
    }


    /**
     * 照片档案资料分页查询
     *
     * @author wangwenjie
     * @param riskInsRequestVo
     * @param userCode
     * @return com.picc.riskctrl.riskins.vo.RiskInsResponseVo
     */
    public RiskInsResponseVo queryRiskInsSaleMain(RiskInsRequestVo riskInsRequestVo, String userCode) {
        RiskInsResponseVo riskInsResponseVo = new RiskInsResponseVo();
        try {
            QueryRule<RiskReportSaleMain> queryRule = QueryRule.getInstance();
            RiskReportSaleMainQueryVo saleVo = riskInsRequestVo.getRiskReportSaleMainVo();
            if (saleVo != null) {
                // 照片档案号
                String archivesNo = saleVo.getArchivesNo();
                if (StringUtils.isNotBlank(archivesNo)) {
                    queryRule.addEqual("archivesNo", archivesNo.trim());
                }

                // 审核人
                String checkUpCode = saleVo.getCheckUpCode();
                if (StringUtils.isNotBlank(checkUpCode)) {
                    queryRule.addEqual("checkUpCode", checkUpCode.trim());
                }

                // 审核状态
                String checkUpFlag = saleVo.getCheckUpFlag();
                List<String> checkUpFlagList = new ArrayList<String>();
                if (StringUtils.isNotBlank(checkUpFlag)) {
                    if (checkUpFlag.indexOf('0') > 0) {
                        checkUpFlagList.add("0");
                    }
                    if (checkUpFlag.indexOf('1') > 0) {
                        checkUpFlagList.add("1");
                    }
                    if (checkUpFlag.indexOf('2') > 0) {
                        checkUpFlagList.add("2");
                    }
                    if (!checkUpFlagList.isEmpty()) {
                        queryRule.addIn("checkUpFlag", checkUpFlagList);
                    }
                }
                // 企业名称
                String companyName = saleVo.getCompanyName();
                if (StringUtils.isNotBlank(companyName)) {
                    queryRule.addLike("companyName", companyName.trim() + "%");
                }
                // 查勘机构
                String exploreComcode = saleVo.getExploreComcode();
                if (StringUtils.isNotBlank(exploreComcode)) {
                    QueryRule queryRuleList = QueryRule.getInstance();
                    String comCode = exploreComcode.trim();
                    PrpDcompanyFk prpDcompanyFk = prpDcompanyFkRepository.findByComCode(comCode);

                    if (prpDcompanyFk != null) {
                        String upperPath = prpDcompanyFk.getUpperPath();
                        if (StringUtils.isNotBlank(upperPath)) {
                            upperPath = upperPath.trim();
                            if ("00".equals(upperPath.substring(upperPath.length() - 8, upperPath.length() - 6))) {
                            } else if (upperPath.length() == 17) {
                                if ("2102,3302,3502,3702,4403".contains(upperPath.substring(9, 13))) {
                                    queryRule.addSubstr("exploreComcode", upperPath.substring(9, 13), 0, 5);
                                } else {
                                    if ("21".equals(upperPath.substring(9, 11))) {
                                        queryRule.addSubstrNotEqual("exploreComcode", "2102", 0, 5);
                                    } else if ("33".equals(upperPath.substring(9, 11))) {
                                        queryRule.addSubstrNotEqual("exploreComcode", "3302", 0, 5);
                                    } else if ("35".equals(upperPath.substring(9, 11))) {
                                        queryRule.addSubstrNotEqual("exploreComcode", "3502", 0, 5);
                                    } else if ("37".equals(upperPath.substring(9, 11))) {
                                        queryRule.addSubstrNotEqual("exploreComcode", "3702", 0, 5);
                                    } else if ("44".equals(upperPath.substring(9, 11))) {
                                        queryRule.addSubstrNotEqual("exploreComcode", "4403", 0, 5);
                                    }
                                    queryRule.addSubstr("exploreComcode", upperPath.substring(9, 11), 0, 3);
                                }
                            } else if (upperPath.length() > 17) {
                                queryRuleList.addSubstr("upperpath", upperPath.substring(0, 26), 0, 27);
                                List<PrpDcompanyFk> prpDcompanyFkList =
                                        prpDcompanyFkRepository.findAll(queryRuleList.getSpecification());
                                List<String> comCodeList = new ArrayList<String>();
                                for (PrpDcompanyFk dcompanyFk : prpDcompanyFkList) {
                                    comCodeList.add(dcompanyFk.getComCode());
                                }
                                queryRule.addIn("exploreComcode", comCodeList);
                            }
                        }
                    } else {
                        queryRule.addEqual("exploreComcode", exploreComcode.trim());
                    }
                }

                // 查勘人
                String explorer = saleVo.getExplorer();
                String explorerCode = saleVo.getExplorerCode();
                if (StringUtils.isNotBlank(explorer) && StringUtils.isNotBlank(explorerCode)) {
                    queryRule.addIn("explorer", explorer.trim(), explorerCode.trim());
                }
                if (StringUtils.isNotBlank(explorer) && !StringUtils.isNotBlank(explorerCode)) {
                    queryRule.addEqual("explorer", explorer.trim());
                }
                if (!StringUtils.isNotBlank(explorer) && StringUtils.isNotBlank(explorerCode)) {
                    queryRule.addEqual("explorer", explorerCode.trim());
                }
                // 时间倒叙排列
                queryRule.addDescOrder("insertTimeForHis");
                // 查勘日期起期
                Date exploreDateBegin = saleVo.getExploreDateBegin();
                // 查勘日期止期
                Date exploreDateEnd = saleVo.getExploreDateEnd();
                if (exploreDateBegin != null || exploreDateEnd != null) {
                    if (exploreDateBegin != null && exploreDateEnd != null) {
                        queryRule.addBetween("exploreDate", exploreDateBegin, exploreDateEnd);
                    } else {
                        if (exploreDateBegin != null) {
                            queryRule.addGreaterEqual("exploreDate", exploreDateBegin);
                        } else {
                            queryRule.addLessEqual("exploreDate", exploreDateEnd);
                        }
                    }
                }
                // 移动端根据usercode查询结果降序排列
                if (saleVo.getMobFlag()) {
                    if (StringUtils.isNotBlank(explorer)) {
                        queryRule.addEqual("explorer", explorer.trim());
                    }
                    queryRule.addDescOrder("archivesNo");
                }
                // 权限校验开始-----*/
                /*SaaAPIService saaAPIService = new SaaAPIServiceImpl();
                try {
                    String powerSQL = saaAPIService.addPower("riskcontrol", userCode, "risksale_examine",
                            "this_.comCode", "", "");
                    queryRule.addSql(powerSQL);
                } catch (Exception e) {
                    LOGGER.info("addPower执行异常：" + e.getMessage(), e);
                    e.printStackTrace();
                    throw new RuntimeException("执行addPower异常:" + e);
                }*/
                // 权限校验结束-----
                Page<RiskReportSaleMain> page = riskReportSaleMainRepository.findAll(queryRule.getSpecification(), queryRule.getPageable(riskInsRequestVo.getPageNo(), riskInsRequestVo.getPageSize()));

                List<RiskReportSaleMain> riskReportSaleMainList = page.getContent();
                List<RiskReportSaleMainVo> targetList = Lists.newArrayList();
                //copy
                BeanCopyUtils.copyPropertiesList(riskReportSaleMainList, targetList, RiskReportSaleMainVo.class);

                // 设置数据
                riskInsResponseVo.setDataList(targetList);
                // 设置数据总条数
                riskInsResponseVo.setTotalCount(page.getTotalElements());
                // 设置总页数
                riskInsResponseVo.setTotalPage(page.getTotalPages());
                // 查询状态
                riskInsResponseVo.setStatus(1);
                riskInsResponseVo.setMessage("成功");
            }
        } catch (Exception e) {
            LOGGER.info("查询销售员版基本信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RiskInsSaleServiceException("查询销售员版基本信息异常", e);
        }
        return riskInsResponseVo;
    }


    /**
     * 根据图片信息获取整改意见  崔凤志
     * @param archivesNo
     * @param imageName
     * @param serialNo
     * @param nameBehind
     * @return
     */
    public ApiResponse setBehindName(String archivesNo, String imageName, String serialNo, String nameBehind) {
        ApiResponse resp = new ApiResponse();
        RiskReportSaleImage riskReportSaleImageOld = null;
        String imageType = "";
        try {
            if (org.apache.commons.lang.StringUtils.isNotBlank(imageName)) {
                imageType = imageName.substring(0, imageName.lastIndexOf('.'));
            }
            RiskReportSaleImage riskReportSaleImageNew = new RiskReportSaleImage();

            RiskReportSaleImageId riskReportSaleImageId = new RiskReportSaleImageId();
            riskReportSaleImageId.setArchivesNo(archivesNo);
            riskReportSaleImageId.setImageType(imageType);
            riskReportSaleImageId.setImageName(imageName);
            Optional<RiskReportSaleImage> optional = riskReportSaleImageRepository.findById(riskReportSaleImageId);
            if (optional.isPresent()) {
                riskReportSaleImageOld = optional.get();
            }

            // 设置整改意见存在标志位
            boolean flag = false;
            if (riskReportSaleImageOld != null) {
                riskReportSaleImageNew = riskReportSaleImageOld;
                // 设置照片标志位为2（打回已上传）
                riskReportSaleImageNew.setStateFlag("2");
                if (StringUtils.isNotBlank(serialNo) && riskReportSaleImageNew.getRiskReportSaleCorrectionList() != null) {
                    for (RiskReportSaleCorrection correction : riskReportSaleImageNew.getRiskReportSaleCorrectionList()) {
                        if (correction.getId().getSerialNo() == Integer.parseInt(serialNo)) {
                            flag = true;
                            if (StringUtils.isBlank(correction.getNameBehind())) {
                                // 设置反馈标志位
                                correction.setFeedbackFlag("1");

                                correction.setNameBehind(nameBehind);

                                BeanUtils.copyProperties(riskReportSaleImageOld, riskReportSaleImageNew);

                                resp.setStatus(1);
                            } else {
                                resp.setStatus(2);
                                resp.setStatusText("该整改意见已重新上传过照片，无须再次上传！");
                            }
                            break;
                        }
                    }
                }
            }
            if (!flag) {
                resp.setStatus(0);
                resp.setStatusText("没有对应的整改意见数据！");
            }
        } catch (Exception e) {
            LOGGER.info("获取整改意见异常：" + e.getMessage(), e);
            e.printStackTrace();
            resp.setStatus(0);
            resp.setStatusText(e.getMessage());
            throw new RuntimeException("获取整改意见异常:" + e);
        }
        return resp;
    }

    /**
     * 保存照片信息  崔凤志
     * @param riskFileNo
     * @param childPath
     * @param fileName
     * @return
     */
    public ApiResponse saveRiskReportSaleImage(String riskFileNo, String childPath, String fileName) {
        ApiResponse resp = new ApiResponse();

        RiskReportSaleImage riskReportSaleImageOld = queryRiskReportSaleImage(riskFileNo, childPath, Integer.toString(Integer.valueOf(fileName) - 1));
        List<RiskReportSaleImage> riskReportSaleImageList = new ArrayList<RiskReportSaleImage>();

        RiskReportSaleImage riskReportSaleImage = new RiskReportSaleImage();
        RiskReportSaleImageId imageId = new RiskReportSaleImageId();
        imageId.setArchivesNo(riskFileNo);
        imageId.setImageType(childPath);
        imageId.setImageName(fileName);

        riskReportSaleImage.setId(imageId);
        riskReportSaleImage.setStateFlag("0");

        // 移动端重新上传的照片的Id是打回的照片的id，以便于进行整改的时候，对影像用于先删除后添加
        riskReportSaleImage.setPageId(riskReportSaleImageOld.getPageId());
        ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());

        riskReportSaleImage.setImageUrl(bundle.getString("saveTypePath").substring(1) + "/" + riskFileNo + "/" + childPath + "/" + fileName + ".jpg");
        riskReportSaleImageList.add(riskReportSaleImage);
        try {
            riskReportSaleImageRepository.saveAll(riskReportSaleImageList);
            resp.setStatus(1);
        } catch (Exception e) {
            LOGGER.info("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            resp.setStatus(0);
            throw new RuntimeException("查询异常:" + e);
        }
        return resp;
    }

    /**
     * 获取照片信息的数据
     * @param archivesNo
     * @param imageType
     * @param imageName
     * @return
     */
    public RiskReportSaleImage queryRiskReportSaleImage(String archivesNo, String imageType, String imageName) {
        RiskReportSaleImage riskReportSaleImage = null;
        if (StringUtils.isNotBlank(archivesNo) && StringUtils.isNotBlank(imageType)
                && StringUtils.isNotBlank(imageName)) {
            try {
                RiskReportSaleImageId riskReportSaleImageId = new RiskReportSaleImageId();
                riskReportSaleImageId.setArchivesNo(archivesNo.trim());
                riskReportSaleImageId.setImageType(imageType.trim());
                riskReportSaleImageId.setImageName(imageName.trim());
                Optional<RiskReportSaleImage> optional = riskReportSaleImageRepository.findById(riskReportSaleImageId);
                if (optional.isPresent()) {
                    riskReportSaleImage = optional.get();
                }
            } catch (Exception e) {
                LOGGER.info("查询异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException("查询异常:" + e);
            }
        }
        return riskReportSaleImage;
    }


    /**
     * 照片档案审核通过暂存
     *
     * @author wangwenjie
     * @param archivesNo
     * @param checkUpFlag
     * @return void
     */
    public void saveSaleMain(String archivesNo, String checkUpFlag) {
        UserInfo userInfo = getUserInfo();

        QueryRule<RiskReportSaleMain> queryRule = QueryRule.getInstance();
        RiskReportSaleMain riskReportSaleMain = null;
        if (StringUtils.isNotBlank(archivesNo)) {
            queryRule.addEqual("archivesNo", archivesNo.trim());
            try {
                riskReportSaleMain = riskReportSaleMainRepository.findOne(queryRule.getSpecification())
                        .orElseThrow(() -> new RiskNoCanNotFoundException().setErrorData(archivesNo));
            } catch (Exception e) {
                LOGGER.info(QUERY_ERROR, e.getMessage());
                e.printStackTrace();
                throw new ApiErrorDataException(QUERY_ERROR, e);
            }
        }

        if (riskReportSaleMain != null) {
            riskReportSaleMain.setCheckUpFlag(checkUpFlag);
            riskReportSaleMain.setCheckUpCode(userInfo.getUserCode());
        }
    }


    /**
     * 照片档案照片打回接口
     *
     * @author wangwenjie
     * @param riskReportSaleImage
     * @param hostPath
     * @return com.picc.riskctrl.common.po.RiskReportSaleCorrection
     */
    public RiskReportSaleCorrection modifySaleSaleImaType(RiskReportSaleImage riskReportSaleImage, String hostPath) {
        UserInfo userInfo = getUserInfo();

        String archivesNo = riskReportSaleImage.getId().getArchivesNo();
        String imageType = riskReportSaleImage.getId().getImageType();

        RiskReportSaleImaType riskReportSaleImaTypeVo = new RiskReportSaleImaType();
        RiskReportSaleImaType riskReportSaleImaTypeOld = this.queryRiskReportSaleImaType(archivesNo, imageType);
        RiskReportSaleMain riskReportSaleMain = this.queryRiskReportSaleMain(archivesNo, userInfo, hostPath);

        // 获取整改意见条数
        int sum = queryRiskReportSaleCorrectionCount(archivesNo, riskReportSaleImage.getId().getImageName());
        // 获取image表中的数据条数
        List<RiskReportSaleImage> riskReportSaleImages = querySaleImageList(archivesNo,
                riskReportSaleImage.getId().getImageName() + "_PG");

        List<RiskReportSaleImaType> list = riskReportSaleMain.getRiskReportSaleImaTypeList();
        // 获取相同类别的RiskReportSaleImaType数据
        if (StringUtils.isNotBlank(imageType)) {
            for (RiskReportSaleImaType riskReportSaleImaType : list) {
                if (imageType.trim().equals(riskReportSaleImaType.getId().getImageType())) {
                    riskReportSaleImaTypeVo = riskReportSaleImaType;
                    riskReportSaleImaTypeVo.setImageRepulseSum(riskReportSaleImaType.getImageRepulseSum() + 1);
                }
            }
        }
        RiskReportSaleImage riskReportSaleImageSelf = new RiskReportSaleImage();
        if (null != riskReportSaleImaTypeVo.getRiskReportSaleImageList()
                && riskReportSaleImaTypeVo.getRiskReportSaleImageList().size() > 0) {
            for (int i = 0; i < riskReportSaleImaTypeVo.getRiskReportSaleImageList().size(); i++) {
                String imageName = riskReportSaleImaTypeVo.getRiskReportSaleImageList().get(i).getId().getImageName()
                        .trim();
                // 获取相匹配的RiskReportSaleImage数据
                if (riskReportSaleImage.getId().getImageName().equals(imageName)) {
                    riskReportSaleImageSelf = riskReportSaleImaTypeVo.getRiskReportSaleImageList().get(i);
                    riskReportSaleImageSelf.setRepulseReason(riskReportSaleImage.getRepulseReason());
                    riskReportSaleImageSelf.setStateFlag("1");

                    if (riskReportSaleImage.getImageUrl().contains("riskcontrol_file")) {
                        riskReportSaleImageSelf.setImageUrl(riskReportSaleImage.getImageUrl()
                                .substring(riskReportSaleImage.getImageUrl().indexOf("riskcontrol_file")));
                    }
                    if (StringUtils.isNotBlank(riskReportSaleImage.getRiskSuggest())) {
                        riskReportSaleImageSelf.setRiskSuggest(riskReportSaleImage.getRiskSuggest());
                    }
                    if (StringUtils.isNotBlank(riskReportSaleImage.getRiskType())) {
                        riskReportSaleImageSelf.setRiskType(riskReportSaleImage.getRiskType());
                    }
                }
            }
            // 获取主机地址
            String imageUrl = "";
            int number = 0;
            if (null != riskReportSaleImages && riskReportSaleImages.size() > 0) {
                number = riskReportSaleImages.size();
            }
            try {
                // 服务器上新增一个打回的照片
                imageUrl = createImage(riskReportSaleImage, hostPath, Integer.toString(number + 1));
            } catch (Exception e) {
                LOGGER.info("新增图片异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            RiskReportSaleImage riskReportSaleImageAdd = new RiskReportSaleImage();
            RiskReportSaleImageId imageId = new RiskReportSaleImageId();
            imageId.setArchivesNo(archivesNo);
            imageId.setImageType(riskReportSaleImage.getId().getImageName() + "_PG");
            imageId.setImageName(Integer.toString(number + 1));
            riskReportSaleImageAdd.setId(imageId);
            riskReportSaleImageAdd.setStateFlag("1");
            // 给打回的照片设置pageId
            if (org.apache.commons.lang.StringUtils.isNotBlank(riskReportSaleImageSelf.getPageId())) {
                riskReportSaleImageAdd.setPageId(riskReportSaleImageSelf.getPageId());
            }
            riskReportSaleImageAdd.setImageUrl(imageUrl);
            riskReportSaleImaTypeVo.getRiskReportSaleImageList().add(riskReportSaleImageAdd);
        }
        Integer serialNo = getSaleCorrectionNum(riskReportSaleImageSelf.getId().getArchivesNo(),
                riskReportSaleImageSelf.getId().getImageType(), riskReportSaleImageSelf.getId().getImageName());
        RiskReportSaleCorrection riskReportSaleCorrection = new RiskReportSaleCorrection();
        RiskReportSaleCorrectionId id = new RiskReportSaleCorrectionId();
        id.setArchivesNo(riskReportSaleImageSelf.getId().getArchivesNo());
        id.setImageType(riskReportSaleImageSelf.getId().getImageType());
        id.setImageName(riskReportSaleImageSelf.getId().getImageName());
        id.setSerialNo(serialNo);

        riskReportSaleCorrection.setId(id);
        riskReportSaleCorrection.setContent(riskReportSaleImage.getRiskSuggest());
        riskReportSaleCorrection.setTitle(riskReportSaleImage.getTitle());
        riskReportSaleCorrection.setFeedbackFlag("0");
        riskReportSaleCorrection.setCorrectionFlag("0");
        riskReportSaleCorrection.setOperateCode(userInfo.getUserCode());
        riskReportSaleCorrection.setRiskType(riskReportSaleImage.getRiskType());
        riskReportSaleCorrection.setSubmitDate(new Date());
        if (StringUtils.isNotBlank(riskReportSaleImage.getRepulseReason())) {
            if ("1".equals(riskReportSaleImage.getRepulseReason().trim())) {
                riskReportSaleCorrection.setContent("影像不清晰");
            }
        }

        // 打回后的照片路径保存到整改意见表中
        riskReportSaleCorrection.setNameBefore(Integer.toString(sum + 1));
        riskReportSaleImageSelf.getRiskReportSaleCorrectionList().add(riskReportSaleCorrection);
        // 将照片上传影像系统(增加一条数据)
        try {
//			imageService.uploadImage(riskReportSaleImage, userInfo, Integer.toString(sum + 1), "DH");
            //todo merge
            /* PojoMerge pm = new PojoMerge(databaseDao);
            pm.mergeObject(riskReportSaleImaTypeVo, riskReportSaleImaTypeOld, true);*/
            /**
             * 打回照片时,保存信息到信息推送表
             */
            RiskReportMessageSend riskReportMessageSend = new RiskReportMessageSend();
            // 业务号：照片档案号+“_”+照片名称+“_”+序号
            riskReportMessageSend.setBusinessNo(archivesNo + "_" + riskReportSaleCorrection.getId().getImageName() + "_"
                    + riskReportSaleCorrection.getId().getSerialNo());
            // 打回标志或者批改标志:"DH","PG"
            riskReportMessageSend.setBusinessType("DH");
            riskReportMessageSend.setRelationNo(archivesNo);
            riskReportMessageSend.setMobileFlag(riskReportSaleMain.getMobileFlag());
            // 标题和内容
            riskReportMessageSend.setTitle("您有一条待办任务");
            riskReportMessageSend.setMessage(archivesNo + "照片档案号被打回，请处理");
            riskReportMessageSend.setStateFlag("0");
            // 账户account
            if (StringUtils.isNotBlank(riskReportSaleMain.getExplorer())) {
                riskReportMessageSend.setUserCode(riskReportSaleMain.getExplorer());
                // 向手机端推送消息
                String result = "";
                try {
//                    result = riskInsJPushService.singleAccountPush(riskReportMessageSend);
                } catch (Exception e) {
                    LOGGER.info("激光推送异常：" + e.getMessage(), e);
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                System.out.println(result);
                if (StringUtils.isNotBlank(result)) {
                    JSONObject resData = JSONObject.fromObject(result);
                    if (resData.has("error")) {
                        System.out.println("针对账户为" + riskReportMessageSend.getUserCode() + "的信息推送失败！");
                        JSONObject error = JSONObject.fromObject(resData.get("error"));
                        System.out.println("错误信息为：" + error.get("message").toString());
                        riskReportMessageSend.setStateFlag("2");
                    } else {
                        System.out.println("针对别名为" + riskReportMessageSend.getUserCode() + "的信息推送成功！");
                        riskReportMessageSend.setStateFlag("1");
                    }
                }
            } else {
                try {
                    throw new Exception("账户account不能为空！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 将信息保存到风控信息推送表中
            riskReportMessageSendRepository.save(riskReportMessageSend);
        } catch (Exception e) {
            LOGGER.info("打回照片信息失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("打回照片信息失败：" + e);
        }
        return riskReportSaleCorrection;
    }

    /**
     * 在服务器上建立打回的图片和路径
     *
     * @author wangwenjie
     * @param riskReportSaleImage
     * @param hostPath
     * @param nameBefore
     * @return java.lang.String
     */
    private String createImage(RiskReportSaleImage riskReportSaleImage, String hostPath, String nameBefore) throws IOException {
        String imageUrl = null;
        StringBuffer stringBuf = new StringBuffer();
        // 判断是影像地址的照片，还是本地地址的照片
        URL url;
        InputStream input = null;
        FTPUtil ftp = new FTPUtil();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("config.savePath", Locale.getDefault());
            // 当整改前照片不为空
//		   if(StringUtils.isNotBlank(nameBefore)){
            if (riskReportSaleImage.getImageUrl().indexOf("http") > -1) {
                url = new URL(riskReportSaleImage.getImageUrl());
            } else {
                url = new URL(hostPath + riskReportSaleImage.getImageUrl());
            }
            stringBuf.append(riskReportSaleImage.getId().getArchivesNo()).append("/")
                    .append(riskReportSaleImage.getId().getImageName() + "_PG").append("/");
            imageUrl = bundle.getString("saveTypePath").substring(1) + "/" + stringBuf.toString() + nameBefore + ".jpg";
//			    imageName=newFile+"/"+nameBefore+".jpg";
//		   }

            input = url.openStream();
            // 解决网络获取输入流，再通过ftp上传报错问题(先保存到本地再上传)
            String dir = bundle.getString("saveRootPath") + bundle.getString("saveTypePath") + "/";
            File newFile = new File(dir + "temp/" + stringBuf.toString());
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(
                    new File(dir + "temp/" + stringBuf.toString() + nameBefore + ".jpg"));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.close();

            FileInputStream fileInput = new FileInputStream(
                    new File(dir + "temp/" + stringBuf.toString() + nameBefore + ".jpg"));
            ftp.uploadFile(fileInput, stringBuf.toString() + "/" + nameBefore + ".jpg");
            fileInput.close();

            newFile.deleteOnExit();
        } catch (Exception e) {
            LOGGER.info("服务端增加图片异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("服务端增加图片异常:" + e);
        } finally {
            if (input != null) {
                try {
                    input.close();
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


        return imageUrl;
    }

    // 发送消息队列
    public void sendMessageQueue(String json) {
        ResourceBundle bundle = ResourceBundle.getBundle("config.sendMessage", Locale.getDefault());
        // 1、初始化连接工厂
        MQQueueConnectionFactory sendFactory = new MQQueueConnectionFactory();
        JMSContext context = null;
        try {
            sendFactory.setHostName(bundle.getString("hostName"));
// 			sendFactory.setPort(11428);
            sendFactory.setPort(Integer.valueOf(bundle.getString("port")).intValue());
            sendFactory.setChannel(bundle.getString("channel"));
// 			sendFactory.setCCSID(819);
            sendFactory.setCCSID(Integer.valueOf(bundle.getString("CCSID")).intValue());
            sendFactory.setQueueManager(bundle.getString("queueManager"));
// 			sendFactory.setTransportType(1);
            sendFactory.setTransportType(Integer.valueOf(bundle.getString("transportType")).intValue());

// 			context= sendFactory.createContext(userid,password,Session.AUTO_ACKNOWLEDGE);
// 			context= sendFactory.createContext(userid,password,Session.SESSION_TRANSACTED);
// 			Destination dest = context.createQueue("QAFCFKTOFCFK");

            context = sendFactory.createContext(bundle.getString("userid"), bundle.getString("password"),
                    Session.SESSION_TRANSACTED);
            Destination dest = context.createQueue(bundle.getString("queue"));

            TextMessage message = context.createTextMessage(json);
            context.createProducer().send(dest, message);
            System.out.println("========  send success! =========   " + message);
            context.commit();
        } catch (JMSException e) {
            LOGGER.info("Mq消息发送失败：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("Mq消息发送失败:" + e);
        } finally {
            if (null != context) {
                context.close();
            }
        }
    }


}
