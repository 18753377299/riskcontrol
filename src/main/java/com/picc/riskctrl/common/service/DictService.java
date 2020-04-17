package com.picc.riskctrl.common.service;

import com.picc.riskctrl.common.dao.RiskUserInfoMobileRepository;
import com.picc.riskctrl.common.po.RiskUserInfoMobile;
import com.picc.riskctrl.common.proxy.CPZXService;
import com.picc.riskctrl.common.proxy.vo.PrpDclassVo;
import com.picc.riskctrl.common.proxy.vo.PrpDriskVo;
import com.picc.riskctrl.common.proxy.vo.RequestBody;
import com.picc.riskctrl.common.utils.BoCopyUtil;
import com.picc.riskctrl.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class DictService {

    @Autowired
    private RiskUserInfoMobileRepository riskUserInfoMobileRepository;

    @Autowired
    private CPZXService cpzxService;

    /**
     * @param riskUserInfoMobile
     * @param userCode
     * @param isPC
     * @throws
     * @Description:
     * @author: QuLingjie
     * @data: 2020/1/17
     * @return:java.lang.String
     */
    @Transactional
    public String saveRiskUserInfoMobile(RiskUserInfoMobile riskUserInfoMobile, String userCode, String isPC) {
        @SuppressWarnings("unused")
        String message = "";
        try {
            Specification<RiskUserInfoMobile> spec = (Specification<RiskUserInfoMobile>) (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicateList = new ArrayList<>();
                if (StringUtils.isNotBlank(userCode)) {
                    predicateList.add(criteriaBuilder.equal(root.get("userCode"), userCode.trim()));
                }
                Predicate[] arr = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(arr));
            };
            Optional<RiskUserInfoMobile> riskUserInfoMobileTemp = riskUserInfoMobileRepository.findOne(spec);

            //查询结果转换为对象
            RiskUserInfoMobile riskUserInfoMobileT = riskUserInfoMobileTemp.orElse(null);
            riskUserInfoMobile.setUserCode(userCode);
            if (riskUserInfoMobileT == null) {
                riskUserInfoMobileRepository.save(riskUserInfoMobile);
            } else {
                //业务数据进行保存
                String lngAndLat = riskUserInfoMobileT.getLngAndLat();
                if ("0".equals(isPC)) {
                    if (StringUtils.isNotBlank(lngAndLat)) {
                        String[] result = lngAndLat.split(",");
                        if (result.length < 5) {
                            lngAndLat = lngAndLat.trim() + "," + riskUserInfoMobile.getLngAndLat();
                            riskUserInfoMobile.setLngAndLat(lngAndLat);
                        } else {
                            String newResult = "";
                            for (int i = 0; i < result.length; i++) {
                                if (i != 0) {
                                    newResult += result[i] + ",";
                                }
                            }
                            newResult = newResult + riskUserInfoMobile.getLngAndLat();
                            riskUserInfoMobile.setLngAndLat(newResult);
                        }
                    }
                    if (StringUtils.isNotBlank(riskUserInfoMobileT.getUrl())) {
                        riskUserInfoMobile.setUrl(riskUserInfoMobileT.getUrl());
                    }
                    //pm.mergeObject(riskUserInfoMobile, riskUserInfoMobilenew, true);
                    //更新对象.（新数据对象更新到旧数据对象(target)
                    BoCopyUtil.convert(riskUserInfoMobile, riskUserInfoMobileT, null, null, null);

                }
            }
            message = "1";
        } catch (Exception e) {
            log.error("数据字典接口异常：" + e.getMessage(), e);
            throw new ServiceException("数据字典接口异常", e);
        }
        return message;
    }

    public PrpDclassVo getPrpDclass(String classCode) {
        Assert.hasText(classCode, "险类代码不能为空");
        RequestBody requestBody = new RequestBody();
        requestBody.setClassCode(classCode);
        requestBody.setPageNo(0);
        requestBody.setPageSize(0);
        return cpzxService.getClass(requestBody).stream().findFirst().orElse(null);
    }

    public PrpDriskVo getPrpDrisk(String riskCode) {
        Assert.hasText(riskCode, "险种代码不能为空");
        RequestBody requestBody = new RequestBody();
        requestBody.setRiskCode(riskCode);
        requestBody.setValidStatus("1");
        requestBody.setPageNo(0);
        requestBody.setPageSize(0);
        return cpzxService.getRisk(requestBody);
    }
}
