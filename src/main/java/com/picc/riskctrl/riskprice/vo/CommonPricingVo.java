package com.picc.riskctrl.riskprice.vo;

import com.picc.riskctrl.riskprice.ResponseModel.ExtendInsureRes;

import java.util.List;

public class CommonPricingVo {
	private PricingTreatmentVo pricingTreatmentVo;
	private RiskReportPricingVo pricingVo;
	private List<ExtendInsureRes> ExtendInsureList;
	public PricingTreatmentVo getPricingTreatmentVo() {
		return pricingTreatmentVo;
	}
	public void setPricingTreatmentVo(PricingTreatmentVo pricingTreatmentVo) {
		this.pricingTreatmentVo = pricingTreatmentVo;
	}
	
	public RiskReportPricingVo getPricingVo() {
		return pricingVo;
	}
	public void setPricingVo(RiskReportPricingVo pricingVo) {
		this.pricingVo = pricingVo;
	}
	public List<ExtendInsureRes> getExtendInsureList() {
		return ExtendInsureList;
	}
	public void setExtendInsureList(List<ExtendInsureRes> extendInsureList) {
		ExtendInsureList = extendInsureList;
	}
	
}
