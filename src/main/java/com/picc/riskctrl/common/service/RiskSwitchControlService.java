package com.picc.riskctrl.common.service;

import com.google.common.collect.Lists;
import com.picc.riskctrl.common.RiskActionExceptionResolver;
import com.picc.riskctrl.common.dao.PrpDcompanyFkRepository;
import com.picc.riskctrl.common.dao.UtiBackRuleConfigRepository;
import com.picc.riskctrl.common.po.UtiBackRuleConfig;
import com.picc.riskctrl.common.po.UtiBackRuleConfigId;
import com.picc.riskctrl.common.schema.PrpDcompanyFk;
import com.picc.riskctrl.common.utils.BeanCopyUtils;
import com.picc.riskctrl.common.utils.QueryRule;
import com.picc.riskctrl.common.vo.RiskSwitchControlRequestVo;
import com.picc.riskctrl.common.vo.RiskSwitchControlResponseVo;
import com.picc.riskctrl.common.vo.RiskSwitchControlVo;
import com.picc.riskctrl.exception.RiskSwitchControlServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统开关维护service
 *
 * @author wangwenjie
 * @date 2020-01-21
 */
@Service
@Slf4j
@Transactional
public class RiskSwitchControlService {

    @Autowired
    private PrpDcompanyFkRepository prpDcompanyFkRepository;

    @Autowired
    private UtiBackRuleConfigRepository utiBackRuleConfigRepository;

    /**
     * 开关查询
     *
     * @author wangwenjie
     * @param riskSwitchControlRequestVo
     * @return com.picc.riskctrl.common.vo.RiskSwitchControlResponseVo
     */
    public RiskSwitchControlResponseVo queryRiskSwitch(RiskSwitchControlRequestVo riskSwitchControlRequestVo) {
        RiskSwitchControlResponseVo riskSwitchControlResponseVo = new RiskSwitchControlResponseVo();
        try {
            RiskSwitchControlVo riskSwitchControlVo = riskSwitchControlRequestVo.getRiskSwitchControlVo();
            if (riskSwitchControlVo != null) {
                String comCode = riskSwitchControlVo.getComCode();
                QueryRule<UtiBackRuleConfig> queryRule = QueryRule.getInstance();

                if (StringUtils.isNotBlank(comCode)) {
                    PrpDcompanyFk prpDcompanyFk = prpDcompanyFkRepository.findByComCode(comCode);
                    String oldUpperPath = prpDcompanyFk.getUpperPath();
                    String subStringName = "comCode";

                    if (StringUtils.isNotBlank(oldUpperPath)) {
                        String upperPath = oldUpperPath.trim();
                        if ("00".equals(upperPath.substring(upperPath.length() - 8, upperPath.length() - 6))) {
                        } else if (upperPath.length() == 17) {
                            if ("2102,3302,3502,3702,4403".contains(upperPath.substring(9, 13))) {
                                queryRule.addSubstr(subStringName, upperPath.substring(9, 13), 0, 5);
                            } else {
                                if ("21".equals(upperPath.substring(9, 11))) {
                                    queryRule.addSubstrNotEqual(subStringName, "2102", 0, 5);
                                } else if ("33".equals(upperPath.substring(9, 11))) {
                                    queryRule.addSubstrNotEqual(subStringName, "3302", 0, 5);
                                } else if ("35".equals(upperPath.substring(9, 11))) {
                                    queryRule.addSubstrNotEqual(subStringName, "3502", 0, 5);
                                } else if ("37".equals(upperPath.substring(9, 11))) {
                                    queryRule.addSubstrNotEqual(subStringName, "3702", 0, 5);
                                } else if ("44".equals(upperPath.substring(9, 11))) {
                                    queryRule.addSubstrNotEqual(subStringName, "4403", 0, 5);
                                }
                                queryRule.addSubstr("comCode", upperPath.substring(9, 11), 0, 3);
                            }
                        } else if (upperPath.length() > 17) {
                            QueryRule<PrpDcompanyFk> prpDcompanyQueryRule = QueryRule.getInstance();
                            prpDcompanyQueryRule.addSubstr("upperPath", upperPath.substring(0, 26), 0, 27);
                            List<PrpDcompanyFk> prpDcompanyFkList =
                                    prpDcompanyFkRepository.findAll(prpDcompanyQueryRule.getSpecification());
                            List<String> comCodeList = Lists.newArrayList();
                            for (PrpDcompanyFk dcompanyFk : prpDcompanyFkList) {
                                comCodeList.add(dcompanyFk.getComCode());
                            }
                            queryRule.addIn("comCode", comCodeList);
                        }
                    }
                }

                // 时间倒叙排列
                queryRule.addDescOrder("insertTimeForHis");

                //有效标志位
                String validStatus = riskSwitchControlRequestVo.getRiskSwitchControlVo().getValidStatus();
                if (validStatus != null) {
                    List<String> validStatusList = new ArrayList<String>();
                    if (!("-1".equals(validStatus.indexOf('0') + ""))) {
                        validStatusList.add("0");
                    }
                    if (!("-1".equals(validStatus.indexOf('1') + ""))) {
                        validStatusList.add("1");
                    }
                    if (!validStatusList.isEmpty()) {
                        queryRule.addIn("validStatus", validStatusList);
                    }
                }

                Page<UtiBackRuleConfig> page = utiBackRuleConfigRepository.findAll(queryRule, riskSwitchControlRequestVo.getPageNo()
                        , riskSwitchControlRequestVo.getPageSize());

                //copy 结果集
                List<UtiBackRuleConfig> pageResultList = page.getContent();
                List<UtiBackRuleConfig> copyList = Lists.newArrayList();
                BeanCopyUtils.copyPropertiesList(pageResultList, copyList);

                //设置数据
                riskSwitchControlResponseVo.setDataList(copyList);
                //设置数据总条数
                riskSwitchControlResponseVo.setTotalCount(page.getTotalElements());
                //设置总页数
                riskSwitchControlResponseVo.setTotalPage(page.getTotalPages());

                //有效标志位翻译
                List resultList = riskSwitchControlResponseVo.getDataList();
                for (int i = 0; i < resultList.size(); i++) {
                    UtiBackRuleConfig utiBackRuleConfig = (UtiBackRuleConfig) resultList.get(i);
                    if (StringUtils.isNotBlank(utiBackRuleConfig.getValidStatus())) {
                        if ("1".equals(utiBackRuleConfig.getValidStatus())) {
                            utiBackRuleConfig.setValidStatus("有效");
                        } else if ("0".equals(utiBackRuleConfig.getValidStatus())) {
                            utiBackRuleConfig.setValidStatus("无效");
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("系统开关查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            String m = RiskActionExceptionResolver.getStackTrace(e);
            riskSwitchControlResponseVo.setQCexception(m);
        }
        return riskSwitchControlResponseVo;
    }

    /**
     * 保存开关信息
     *
     * @author wangwenjie
     * @param riskSwitchControlVo
     * @return java.lang.String
     */
    public String saveRiskSwitchControl(RiskSwitchControlVo riskSwitchControlVo) {
        String message = "";
        try {
            UtiBackRuleConfig riskSwitchControlNew = new UtiBackRuleConfig();
            UtiBackRuleConfigId id = new UtiBackRuleConfigId();
            if (!isRiskSwitchControlExist(riskSwitchControlVo)) {
                message = "no";
            } else {
                BeanUtils.copyProperties(riskSwitchControlVo, riskSwitchControlNew);

                id.setComCode(riskSwitchControlVo.getComCode());
                id.setRuleCode(riskSwitchControlVo.getRuleCode());
                riskSwitchControlNew.setId(id);
                riskSwitchControlNew.setValidStatus("1");
                utiBackRuleConfigRepository.save(riskSwitchControlNew);
                message = "success";
            }
        } catch (Exception e) {
            log.error("保存环境信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RiskSwitchControlServiceException("保存环境信息异常: " + e.getMessage());
        }
        return message;
    }

    /**
     * 修改开关信息
     *
     * @author wangwenjie
     * @param riskSwitchControlVo
     * @return java.lang.String
     */
    public String updateRiskSwitchControl(RiskSwitchControlVo riskSwitchControlVo) {
        String message = "";
        try {
            UtiBackRuleConfig riskSwitchControlNew = new UtiBackRuleConfig();
            UtiBackRuleConfigId id = new UtiBackRuleConfigId();
            if (riskSwitchControlVo == null) {
                message = "no";
            } else {
                BeanUtils.copyProperties(riskSwitchControlVo, riskSwitchControlNew);

                id.setComCode(riskSwitchControlVo.getComCode());
                id.setRuleCode(riskSwitchControlVo.getRuleCode());
                riskSwitchControlNew.setId(id);
                utiBackRuleConfigRepository.save(riskSwitchControlNew);
                message = "success";
            }
        } catch (Exception e) {
            log.error("保存环境信息异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RiskSwitchControlServiceException("保存环境信息异常:" + e);
        }
        return message;
    }

    /**
     * 判断开关是否存在
     *
     * @author wangwenjie
     * @param riskSwitchControlVo
     * @return boolean
     */
    private boolean isRiskSwitchControlExist(RiskSwitchControlVo riskSwitchControlVo) {
        boolean flag;
        try {
            QueryRule<UtiBackRuleConfig> queryRule = QueryRule.getInstance();

            String comCode = riskSwitchControlVo.getComCode();
            if (StringUtils.isNotBlank(comCode)) {
                queryRule.addEqual("id.comCode", comCode.trim());
            }
            queryRule.addEqual("id.ruleCode", "underwriteFlag");
            queryRule.addEqual("validStatus", "1");

            List<UtiBackRuleConfig> riskSwitchControlList = utiBackRuleConfigRepository.findAll(queryRule);

            if (riskSwitchControlList == null || riskSwitchControlList.size() == 0) {
                flag = true;
            } else {
                flag = false;
            }

        } catch (Exception e) {
            log.error("查询异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("查询异常:" + e);
        }

        return flag;
    }
}
