package com.picc.riskctrl.riskins.service;

import com.picc.riskctrl.common.RiskControlConst;
import com.picc.riskctrl.common.dao.RiskDtemplateRepository;
import com.picc.riskctrl.common.po.Code;
import com.picc.riskctrl.common.po.RiskDtemplate;
import com.picc.riskctrl.common.vo.RiskDtemplateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RiskInsConfigService
 * @Author: 张日炜
 * @Date: 2020-01-15 10:02
 **/
@Service
@Transactional
public class RiskInsConfigService {

    @Autowired
    private RiskDtemplateRepository riskDtemplateRepository;

    public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    /**
     * @return List<Code>
     * @throws
     * @功能：从RiskDtemplate表中获取classCode险类代码
     * @author 马军亮
     * @日期 2017-10-23
     */
    public List<Code> queryClassCode() throws Exception {
        List<RiskDtemplateVo> riskDtemplateVoList = new ArrayList<RiskDtemplateVo>();
        List<Code> codeList = new ArrayList<Code>();
        try {
            List<RiskDtemplate> result = riskDtemplateRepository.findAll();
            // 用来装去重后的classCode
            List<String> classCodeList = new ArrayList<String>();

            // 去重开始
            if (result != null && result.size() != 0) {
                for (RiskDtemplate rtemp : result) {
                    String classCodeTemp = rtemp.getId().getClassCode();
                    // 假设不重复
                    boolean flag = false;
                    for (String sTemp : classCodeList) {
                        if (classCodeTemp.equals(sTemp)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        classCodeList.add(classCodeTemp);
                    }
                }
            } else {
                throw new Exception("result对象为null");
            }
            // 去重结束

            if (classCodeList.isEmpty()) {
                throw new Exception("没有获取到险类代码，请检查riskdtemplate表的配置！");
            } else {
                for (int i = 0; i < classCodeList.size(); i++) {
                    RiskDtemplateVo vo = new RiskDtemplateVo();
                    vo.setClassCode(classCodeList.get(i));
                    riskDtemplateVoList.add(vo);
                }
            }

            Code code = null;
            for (RiskDtemplateVo riskDtemplateVo : riskDtemplateVoList) {
                code = new Code();
                String classCode = riskDtemplateVo.getClassCode();
                code.setCode(classCode);
                if (RiskControlConst.JB.equals(classCode)) {
                    code.setName(RiskControlConst.JB_CNAME);
                } else if (RiskControlConst.ZH.equals(classCode)) {
                    code.setName(RiskControlConst.ZH_CNAME);
                } else if (RiskControlConst.YQ.equals(classCode)) {
                    code.setName(RiskControlConst.YQ_CNAME);
                } else if (RiskControlConst.ZD.equals(classCode)) {
                    code.setName(RiskControlConst.ZD_CNAME);
                } else if (RiskControlConst.JS.equals(classCode)) {
                    code.setName(RiskControlConst.JS_CNAME);
                }
                // RLIUBIN2014062702_11 风控增加险类“QT--财产险（其他险）” add by liangjianze
                // 2015-1-29
                else if (RiskControlConst.QT.equals(classCode)) {
                    code.setName(RiskControlConst.QT_CNAME);
                } else {
//                        PrpDclassVo prpDclass = dictService.getPrpDclass(classCode);
//                        if (prpDclass != null) {
//                            code.setName(prpDclass.getClassCName());
//                        }
                    // 以后去除
                    code.setName("qwe" + classCode);
                }
                codeList.add(code);
            }

        } catch (Exception e) {
            LOGGER.info("获取classCode险类代码异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("获取classCode险类代码异常:" + e);
        }
        return codeList;
    }

    /**
     * @return List<Code>
     * @throws
     * @功能：从RiskDtemplate表中获取riskCode险类代码
     * @author 马军亮
     * @日期 2017-10-23
     */
    public List<Code> queryRiskCode(String classCode, String comCode)
            throws Exception {

        List<Code> codeList = new ArrayList<Code>();
        // classCode为空时返回
        try {
            if ("".equals(classCode)) {
                return new ArrayList<Code>();
            }

            List<RiskDtemplateVo> riskDtemplateVoList = new ArrayList<RiskDtemplateVo>();
            // 用来装去重后的riskCode
            List<String> riskCodeList = new ArrayList<String>();

            Specification<RiskDtemplate> spec = new Specification<RiskDtemplate>() {
                @Override
                public Predicate toPredicate(Root<RiskDtemplate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                    Predicate classCodeEqual = criteriaBuilder.equal(root.get("id").get("classCode"), classCode);
                    Predicate validstatusEqual = criteriaBuilder.equal(root.get("validstatus"), "1");
                    return criteriaBuilder.and(classCodeEqual, validstatusEqual);
                }
            };

            List<RiskDtemplate> result = riskDtemplateRepository.findAll(spec);
            // 去重开始
            if (result != null && result.size() != 0) {
                for (RiskDtemplate rtemp : result) {
                    String riskCodeTemp = rtemp.getId().getRiskCode();
                    // 假设不重复
                    boolean flag = false;
                    for (String sTemp : riskCodeList) {
                        if (riskCodeTemp.equals(sTemp)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        riskCodeList.add(riskCodeTemp);
                    }
                }
            } else {
                throw new Exception("result对象为null");
            }
            // 去重结束

            if (riskCodeList.isEmpty()) {
                throw new Exception("没有获取到产品代码，请检查riskdtemplate表的配置！");
            } else {
                for (int i = 0; i < riskCodeList.size(); i++) {
                    RiskDtemplateVo vo = new RiskDtemplateVo();
                    vo.setRiskCode(riskCodeList.get(i));
                    riskDtemplateVoList.add(vo);
                }
            }
            Code code = null;
            for (RiskDtemplateVo riskDtemplateVo : riskDtemplateVoList) {
                String riskCode = riskDtemplateVo.getRiskCode();
                if (RiskControlConst.JBX.equals(riskCode.trim())) {
                    code = new Code();
                    code.setCode(riskCode.trim());
                    code.setName(RiskControlConst.JBX_CNAME);
                    codeList.add(code);
                } else if (RiskControlConst.ZHX.equals(riskCode.trim())) {
                    code = new Code();
                    code.setCode(riskCode.trim());
                    code.setName(RiskControlConst.ZHX_CNAME);
                    codeList.add(code);
                } else if (RiskControlConst.YQX.equals(riskCode.trim())) {
                    code = new Code();
                    code.setCode(riskCode.trim());
                    code.setName(RiskControlConst.YQX_CNAME);
                    codeList.add(code);
                } else if ("PUB".equals(riskCode.trim())) {
//                        List<PrpDrisk> prpDrisks = dictService.getrisk(classCode,
//                                "", comCode, "1", 0, 0).getData();
//                        for (PrpDrisk prpdRisk : prpDrisks) {
//                            code = new Code();
//                            code.setCode(prpdRisk.getRiskCode());
//                            code.setName(prpdRisk.getRiskCName());
//                            codeList.add(code);
//                        }
                } else {
//                        List<PrpDrisk> prpDrisks = dictService.getrisk("",
//                                riskCode, 0, 0).getData();
//                        for (PrpDrisk prpdRisk : prpDrisks) {
//                            code = new Code();
//                            code.setCode(riskCode.trim());
//                            code.setName(prpdRisk.getRiskCName());
//                            codeList.add(code);
//                        }
                }

            }
        } catch (Exception e) {
            LOGGER.info("获取riskCode险类代码异常：" + e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException("获取riskCode险类代码异常:" + e);
        }
        return codeList;
    }
}
