package com.picc.riskctrl.dataquery.service;

import com.picc.riskctrl.riskinfo.expert.dao.RiskInfoExpertRepository;
import com.picc.riskctrl.riskinfo.expert.po.RiskInfoExpert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RiskInfoService {

    public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    @Autowired
    private RiskInfoExpertRepository riskInfoExpertRepository;
    /**
     * @功能：保存
     * @作者：liangshang
     * @日期：20180525
     * @修改记录：张日炜20200220
     */
    public void saveRiskInfoExpert(RiskInfoExpert riskInfoExpert)throws Exception{
        try {
            if(null!=riskInfoExpert){
                riskInfoExpert.setValidStatus("1");
                riskInfoExpertRepository.save(riskInfoExpert);
            }
        } catch (Exception e) {
            LOGGER.info("保存异常：" + e.getMessage() ,e);
            e.printStackTrace();
            throw new RuntimeException("保存异常:"+e);
        }
    }
}
