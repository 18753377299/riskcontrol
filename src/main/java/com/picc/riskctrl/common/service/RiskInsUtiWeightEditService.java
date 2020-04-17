package com.picc.riskctrl.common.service;

import com.picc.riskctrl.base.BaseService;
import com.picc.riskctrl.common.RiskActionExceptionResolver;
import com.picc.riskctrl.common.dao.UtiWeightRepository;
import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.schema.UtiWeight;
import com.picc.riskctrl.common.utils.BeanCopyUtils;
import com.picc.riskctrl.common.utils.QueryRule;
import com.picc.riskctrl.common.vo.RiskUtiWeightResponseVo;
import com.picc.riskctrl.common.vo.RiskUtiWeightVo;
import com.picc.riskctrl.exception.RiskInsUtiWeightEditServiceException;
import com.picc.riskctrl.exception.RiskNoCanNotFoundException;
import com.picc.riskctrl.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 灾因权重维护service
 *
 * @author wangwenjie
 * @date 2020-02-06
 */
@Service
@Transactional
@Slf4j
public class RiskInsUtiWeightEditService extends BaseService {

    @Autowired
    private UtiWeightRepository utiWeightRepository;

    @Autowired
    private DataSourcesService dataSourcesService;

    /**
     * 灾因权重查询
     *
     * @author wangwenjie
     * @param riskUtiWeightResponseVo
     * @return com.picc.riskctrl.common.vo.RiskUtiWeightResponseVo
     */
    public RiskUtiWeightResponseVo queryRiskUtiWeight(RiskUtiWeightResponseVo riskUtiWeightResponseVo) {

        UserInfo userInfo = getUserInfo();

        QueryRule<UtiWeight> queryRule = QueryRule.getInstance();
        RiskUtiWeightVo riskUtiWeightVo = riskUtiWeightResponseVo.getRiskutiweightvo();
        String userCode = userInfo.getUserCode();
        try {
            if (riskUtiWeightVo != null) {
                //归属机构
                String comCode = riskUtiWeightVo.getComCode();
                if (StringUtils.isNotBlank(comCode) && !"[]".equals(comCode)) {
                    comCode = removeSpeSymbolsReg(comCode);
                    List<String> comCodeList = Arrays.asList(comCode.split(","));
                    queryRule.addIn("comCode", comCodeList);
                } else {
                    if (!"00000000".equals(userInfo.getComCode())) {
                        queryRule.addEqual("comCode", userCode.substring(0, 2) + "000000");
                    }
                }
                //维护人员
                String operatorCode = riskUtiWeightVo.getOperatorCode();
                if (StringUtils.isNotBlank(operatorCode)) {
                    queryRule.addEqual("operatorCode", operatorCode.trim());
                }
                //有效标志位
                String validStatus = riskUtiWeightVo.getValidStatus();
                if (StringUtils.isNotBlank(validStatus)) {
                    List<String> validStatusList = new ArrayList<String>();
                    if (validStatus.indexOf('0') > 0) {
                        validStatusList.add("0");
                    }
                    if (validStatus.indexOf('1') > 0) {
                        validStatusList.add("1");
                    }
                    if (!validStatusList.isEmpty()) {
                        queryRule.addIn("validStatus", validStatusList);
                    }
                }
            }
            //todo 权限校验开始-----*/
            /*SaaAPIService saaAPIService = new SaaAPIServiceImpl();
            try {
                String powerSQL = saaAPIService.addPower("riskcontrol", userCode, "riskins_set", "this_.comCode", "", "");
                queryRule.addSql(powerSQL);
            } catch (Exception e) {
                log.info("addPower执行异常：" + e.getMessage(), e);
                e.printStackTrace();
                throw new RuntimeException("addPower执行异常:" + e);
            }*/
            //排序
            queryRule.addAscOrder("id");
            //页数
            Page<UtiWeight> page = utiWeightRepository.findAll(queryRule, riskUtiWeightResponseVo.getPageNo(), riskUtiWeightResponseVo.getPageSize());

            //copy
            List<UtiWeight> sourceList = page.getContent();
            List<UtiWeight> targetList = BeanCopyUtils.copyPropertiesList(sourceList);

            //设置数据
            riskUtiWeightResponseVo.setDataList(targetList);
            //设置数据总条数
            riskUtiWeightResponseVo.setTotalCount(page.getTotalElements());
            //设置总页数
            riskUtiWeightResponseVo.setTotalPage(page.getTotalPages());

            //有效标志位翻译
            List resultList = riskUtiWeightResponseVo.getDataList();
            for (int i = 0; i < resultList.size(); i++) {
                UtiWeight utiWeight = (UtiWeight) resultList.get(i);
                if (StringUtils.isNotBlank(utiWeight.getValidStatus())) {
                    if ("1".equals(utiWeight.getValidStatus())) {
                        utiWeight.setValidStatus(("有效"));
                    } else if ("0".equals(utiWeight.getValidStatus())) {
                        utiWeight.setValidStatus("无效");
                    }
                }
            }
        } catch (Exception e) {
            log.error("灾因权重查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            String m = RiskActionExceptionResolver.getStackTrace(e);
            riskUtiWeightResponseVo.setQCexception(m);
        }
        return riskUtiWeightResponseVo;
    }

    /**
     * 通过 comCode 来更新状态
     *
     * @author wangwenjie
     * @param comCode
     * @return java.lang.String
     */
    public String updateValidStatusByComcode(String comCode) {
        QueryRule<UtiWeight> queryRule = QueryRule.getInstance();
        queryRule.addEqual("comCode", comCode);
        try {
            List<UtiWeight> list = utiWeightRepository.findAll(queryRule);

            for (UtiWeight utiWeight : list) {
                utiWeight.setValidStatus("0");
            }
            utiWeightRepository.saveAll(list);

        } catch (Exception e) {
            log.error("更新状态异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RiskInsUtiWeightEditServiceException("更新状态异常:" + e);
        }
        return "success";
    }

    /**
     * 查询id最大值
     *
     * @author wangwenjie
     * @return int
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int getMaxId() {
        return utiWeightRepository.queryMaxId();
    }

    /**
     * 灾因权重增加,增加前先更新
     *
     * @author wangwenjie
     * @param riskUtiWeightVo
     * @return java.lang.String
     */
    public String saveRiskUtiWeight(RiskUtiWeightVo riskUtiWeightVo) {
        UserInfo userInfo = getUserInfo();

        String message = "";
        UtiWeight utiweight = new UtiWeight();
        riskUtiWeightVo.setComCode(riskUtiWeightVo.getComCode());
        riskUtiWeightVo.setValidStatus("1");

        BigDecimal chushu = new BigDecimal("100");
        String name = userInfo.getUserName();
        String code = userInfo.getUserCode();
        riskUtiWeightVo.setOperatorName(name);
        riskUtiWeightVo.setOperatorCode(code);

        riskUtiWeightVo.setFireWeight(riskUtiWeightVo.getFireWeight().divide(chushu));
        riskUtiWeightVo.setWaterWeight(riskUtiWeightVo.getWaterWeight().divide(chushu));
        riskUtiWeightVo.setEarthquakeWeight(riskUtiWeightVo.getEarthquakeWeight().divide(chushu));
        riskUtiWeightVo.setGeologyWeight(riskUtiWeightVo.getGeologyWeight().divide(chushu));
        riskUtiWeightVo.setWindWeight(riskUtiWeightVo.getWindWeight().divide(chushu));
        riskUtiWeightVo.setThunderWeight(riskUtiWeightVo.getThunderWeight().divide(chushu));
        riskUtiWeightVo.setTheftWeight(riskUtiWeightVo.getTheftWeight().divide(chushu));
        riskUtiWeightVo.setSnowWeight(riskUtiWeightVo.getSnowWeight().divide(chushu));
        BeanUtils.copyProperties(riskUtiWeightVo, utiweight);
        try {
            utiWeightRepository.save(utiweight);
            message = "success";
        } catch (Exception e) {
            log.error("保存异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RiskInsUtiWeightEditServiceException("保存异常:" + e);
        }
        return message;
    }

    /**
     * 灾因权重浏览
     *
     * @author wangwenjie
     * @param id
     * @return com.picc.riskctrl.common.vo.RiskUtiWeightVo
     */
    public RiskUtiWeightVo scanById(String id) {
        RiskUtiWeightVo riskUtiWeightVo = new RiskUtiWeightVo();
        try {
            QueryRule<UtiWeight> queryRule = QueryRule.getInstance();
            queryRule.addEqual("id", Integer.parseInt(id));
            Optional<UtiWeight> optional = utiWeightRepository.findOne(queryRule);
            UtiWeight utiWeight =
                    optional.orElseThrow(() -> new RiskNoCanNotFoundException("query UtiWeight by id error").setErrorData(id));
            BeanUtils.copyProperties(utiWeight, riskUtiWeightVo);
            riskUtiWeightVo.setComCName(dataSourcesService.queryComCodeCName(riskUtiWeightVo.getComCode()));
        } catch (Exception e) {
            log.error("灾因权重浏览异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new ServiceException("灾因权重浏览异常", e);
        }
        return riskUtiWeightVo;
    }

    /**
     * 灾因权重注销
     *
     * @author wangwenjie
     * @param id
     * @return java.lang.String
     */
    public String cancelById(String id) {
        String message = "";
        try {
            QueryRule<UtiWeight> queryRule = QueryRule.getInstance();
            queryRule.addEqual("id", Integer.parseInt(id));
            Date date = new Date();
            if (StringUtils.isEmpty(id)) {
                message = "none";
            } else {
                UtiWeight utiWeight = utiWeightRepository.findOne(queryRule)
                        .orElseThrow(() -> new RiskNoCanNotFoundException("query UtiWeight by id error")
                                .setErrorData(id));
                utiWeight.setOperateTimeForHis(date);
                utiWeight.setValidStatus("0");
                utiWeightRepository.save(utiWeight);
                message = "success";
            }
        } catch (RiskNoCanNotFoundException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            log.error("灾因权重注销异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RiskInsUtiWeightEditServiceException("灾因权重注销异常:" + e);
        }
        return message;
    }
}
