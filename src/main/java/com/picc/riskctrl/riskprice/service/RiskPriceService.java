package com.picc.riskctrl.riskprice.service;

import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.service.DataSourcesService;
import com.picc.riskctrl.riskprice.dao.PricingTreatmentDao;
import com.picc.riskctrl.riskprice.dao.RiskReportPricingDao;
import com.picc.riskctrl.riskprice.po.Extendinsure;
import com.picc.riskctrl.riskprice.po.PricingTreatment;
import com.picc.riskctrl.riskprice.po.RiskReportPricing;
import com.picc.riskctrl.riskprice.vo.CommonPricingVo;
import com.picc.riskctrl.riskprice.vo.ExtendinsureVo;
import com.picc.riskctrl.riskprice.vo.PricingTreatmentVo;
import com.picc.riskctrl.riskprice.vo.RiskReportPricingVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdfc.framework.web.ApiResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RiskPriceService {
	@Autowired
	PricingTreatmentDao pricingTreatmentDao;
	@Autowired
	RiskReportPricingDao riskReportPricingDao;
	@Autowired
    private DataSourcesService dataSourcesService;
	/**
	 * @author 周东旭
	 * @param riskfileNo
	 * @return PricingTreatmentVo*/
	public PricingTreatmentVo queryRiskFileNo(String riskFileNo) {
		PricingTreatmentVo vo = new PricingTreatmentVo();
		PricingTreatment pricingTreatment=pricingTreatmentDao.getOne(riskFileNo);
		if(pricingTreatment!=null){
			BeanUtils.copyProperties(pricingTreatment, vo);
			vo.setEffectiveDate(pricingTreatment.getEffectiveDate());
			vo.setExpirationDate(pricingTreatment.getExpirationDate());
			vo.setBusinessNature(pricingTreatment.getBusinessNature().trim());
		}
		return vo;
	}
	/**@功能：保存定价信息
	 * @author 周东旭
	 * @时间： 20200210*/
	public String saveRiskPrice(CommonPricingVo comPricVo) {
		PricingTreatment pricingTreatment = new PricingTreatment();
		RiskReportPricing pric = new RiskReportPricing();
		String  message = "";
		List<Extendinsure> extendinsureList = new ArrayList<Extendinsure>(0);
		try {
			BeanUtils.copyProperties(comPricVo.getPricingTreatmentVo(), pricingTreatment);
			BeanUtils.copyProperties(comPricVo.getPricingVo(), pric);
			if(comPricVo.getExtendInsureList()!=null) {
				for(int i=0;i<comPricVo.getExtendInsureList().size();i++) {
					Extendinsure extendinsure = new Extendinsure();
					extendinsure.setRiskFileNo(comPricVo.getPricingTreatmentVo().getRiskFileNo());
					extendinsure.setInsuranceCode(comPricVo.getExtendInsureList().get(i).getINSURANCECODE()==null?"":comPricVo.getExtendInsureList().get(i).getINSURANCECODE());
					extendinsure.setInsuranceFee(comPricVo.getExtendInsureList().get(i).getINSURANCEFEE()==null?null:new BigDecimal(comPricVo.getExtendInsureList().get(i).getINSURANCEFEE()));
					extendinsure.setInsuranceRate(comPricVo.getExtendInsureList().get(i).getINSURANCERATE()==null?null:new BigDecimal(comPricVo.getExtendInsureList().get(i).getINSURANCERATE()));
					extendinsure.setInsuranceType(comPricVo.getExtendInsureList().get(i).getINSURANCETYPE()==null?"":comPricVo.getExtendInsureList().get(i).getINSURANCETYPE());
					extendinsureList.add(extendinsure);
				}
				pric.setExtendinsureList(extendinsureList);
			}
			if("1".equals(comPricVo.getPricingTreatmentVo().getFlag())) {
				this.pricingTreatmentDao.save(pricingTreatment);
				this.riskReportPricingDao.save(pric);
			}else {
//				// 获取持久对象
//				QueryRule queryRule = QueryRule.getInstance();
				PricingTreatment source = null;
				RiskReportPricing source1 = null;
				source = this.pricingTreatmentDao.getOne(comPricVo.getPricingTreatmentVo().getRiskFileNo().trim());
				source1 = this.riskReportPricingDao.getOne(comPricVo.getPricingTreatmentVo().getRiskFileNo().trim());
				BeanUtils.copyProperties(source, pricingTreatment);
				BeanUtils.copyProperties(source1, pric);
				this.pricingTreatmentDao.save(pricingTreatment);
				this.riskReportPricingDao.save(pric);
			}
			message = "success";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存定价信息异常:" + e);
		}
		return message;
	}
	/**
	 * @功能：查询风控报告信息
	 * @author 王坤龙
	 * @param riskFileNo风控编号
	 * @throws @日期2019-07-06
	 */
	public ApiResponse reShowPricing(String riskFileNo, UserInfo userInfo) throws Exception {
		String userCodeInfo = userInfo.getUserCode();
		String comCode = userInfo.getComCode();
		ApiResponse ajax = new ApiResponse();
		Map<String, Object> datas = new HashMap<String, Object>();
		List<ExtendinsureVo> extendinsureVoList= new ArrayList<ExtendinsureVo>();
		RiskReportPricingVo pv = new RiskReportPricingVo();
		try {
			if (StringUtils.isNotBlank(riskFileNo)) {
//				queryRule.addEqual("riskFileNo", riskFileNo.trim());
//				RiskReportPricing pric = databaseDao.findUnique(RiskReportPricing.class, queryRule);
				RiskReportPricing pric = this.riskReportPricingDao.getOne(riskFileNo);
				if(pric!=null){
					BeanUtils.copyProperties(pric, pv);
					// 投保险种翻译
					if (StringUtils.isNotBlank(pv.getRiskCode())) {
						pv.setRiskCodeName(dataSourcesService.queryRiskCodeName(pv.getRiskCode(), comCode));
					}
					if (StringUtils.isNotBlank(pv.getComCode())) {
						pv.setComCodeName(dataSourcesService.queryComCodeCName(pv.getComCode()));
					}
					datas.put("pricing", pv);
					ajax.setStatus(1);
					List<Extendinsure> extendinsureList = pric.getExtendinsureList();
					if(extendinsureList.size()>0){
						for(Extendinsure ex:extendinsureList){
							ExtendinsureVo exVo = new ExtendinsureVo();
							BeanUtils.copyProperties(ex, exVo);
							extendinsureVoList.add(exVo);
						}
					}
					datas.put("extendinsureVoList", extendinsureVoList);
					ajax.setData(datas);
				}else{
					ajax.setData(pv);
					ajax.setStatus(0);
				}
			} else {
				throw new RuntimeException("风险编号不能为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询风控报告信息异常:" + e);
		}
		return ajax;
	}

}
