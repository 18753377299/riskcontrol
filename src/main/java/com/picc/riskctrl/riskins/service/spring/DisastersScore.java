package com.picc.riskctrl.riskins.service.spring;

import com.picc.riskctrl.common.po.UtiFormula;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.math.BigDecimal;
import java.util.Map;


public class DisastersScore {
	/**
	 * @author 周东旭
	 * @throws Exception 
	 * @功能     水灾评分*/
	
	public BigDecimal partialWaterScore(String[] str,UtiFormula utiFormula) throws Exception {
		BigDecimal sumScore=BigDecimal.ZERO;
//		int id=Integer.parseInt(utiFormula.getId().getDangerType());
		String dengerScore=str[4];
		if(0<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<3) {
			sumScore=getResultByFormula("5.00");
		}else if(3<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<5) {
			sumScore=getResultByFormula("4.00");
		}else if(5<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<8) {
			sumScore=getResultByFormula("2.00");
		} else {
			sumScore=getResultByFormula("0.00");
		}
		return sumScore;
	}
	public BigDecimal partialWindScore(String[] str,UtiFormula utiFormula) throws Exception{
		BigDecimal sumScore=BigDecimal.ZERO;
//		int id=Integer.parseInt(utiFormula.getId().getDangerType());
		String dengerScore=str[5];
		if(0<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<3) {
			sumScore=getResultByFormula("22.00");
		}else if(3<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<5) {
			sumScore=getResultByFormula("15.00");
		}else if(5<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<8) {
			sumScore=getResultByFormula("8.00");
		} else {
			sumScore=getResultByFormula("0.00");
		}
		return sumScore;
	}
	public BigDecimal partialThunderScore(String[] str,UtiFormula utiFormula) throws Exception{
		BigDecimal sumScore=BigDecimal.ZERO;
//		int id=Integer.parseInt(utiFormula.getId().getDangerType());
		String dengerScore=str[1];
		if(0<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<3) {
			sumScore=getResultByFormula("18.00");
		}else if(3<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<6) {
			sumScore=getResultByFormula("12.00");
		}else if(6<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<9) {
			sumScore=getResultByFormula("6.00");
		} else {
			sumScore=getResultByFormula("3.00");
		}
		return sumScore;
	}
	public BigDecimal partialSnowScore(String[] str,UtiFormula utiFormula) throws Exception{
		BigDecimal sumScore=BigDecimal.ZERO;
//		int id=Integer.parseInt(utiFormula.getId().getDangerType());
		String dengerScore=str[2];
		if(1<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<3) {
			sumScore=getResultByFormula("15.00");
		}else if(3<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<5) {
			sumScore=getResultByFormula("10.00");
		}else if(5<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<8) {
			sumScore=getResultByFormula("5.00");
		} else if(8<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<=10){
			sumScore=getResultByFormula("0.00");
		}else {
			sumScore=getResultByFormula("20.00");
		}
		return sumScore;
	}
	public BigDecimal partialEQScore(String[] str,UtiFormula utiFormula) throws Exception{
		BigDecimal sumScore=BigDecimal.ZERO;
//		int id=Integer.parseInt(utiFormula.getId().getDangerType());
		String dengerScore=str[6];
		if(Double.parseDouble(dengerScore)==0.00) {
			sumScore=getResultByFormula("100.00");
		}else if(Double.parseDouble(dengerScore)==0.05) {
			sumScore=getResultByFormula("80.00");
		}else if(Double.parseDouble(dengerScore)==0.10) {
			sumScore=getResultByFormula("60.00");
		} else if(Double.parseDouble(dengerScore)==0.15){
			sumScore=getResultByFormula("45.00");
		}else if(Double.parseDouble(dengerScore)==0.20){
			sumScore=getResultByFormula("30.00");
		}else if(Double.parseDouble(dengerScore)==0.30) {
			sumScore=getResultByFormula("15.00");
		}else {
			sumScore=getResultByFormula("0.00");
		}
		return sumScore;
	}
	public BigDecimal partialGeologicalScore(String[] str,UtiFormula utiFormula) throws Exception{
		BigDecimal sumScore=BigDecimal.ZERO;
//		int id=Integer.parseInt(utiFormula.getId().getDangerType());
		String dengerScore=str[7];
		if(1<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<3) {
			sumScore=getResultByFormula("80.00");
		}else if(3<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<5) {
			sumScore=getResultByFormula("60.00");
		}else if(5<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<8) {
			sumScore=getResultByFormula("40.00");
		} else if(8<=Double.parseDouble(dengerScore)&&Double.parseDouble(dengerScore)<=10){
			sumScore=getResultByFormula("20.00");
		}else {
			sumScore=getResultByFormula("100.00");
		}
		return sumScore;
	}
	/**
	 * @功能：消防设施分值获取
	 * @author liqiankun
	 * @param HttpServletRequest
	 * @param RiskInsRiskReportVo
	 * @return BigDecimal @日期：20190717
	 * @throws Exception 
	 */
	public BigDecimal obtainFireFacilityScore(Map<String, String> objectMap,UtiFormula utiFormula) throws Exception{
		BigDecimal sumScore=BigDecimal.ZERO;
		// 获取选中的值
		String factorYValue = objectMap.get("RiskReportFireDanger.fireFacility");
		factorYValue = factorYValue.replace("[", "").replace("]", "").replace("\"", "");
		if(factorYValue.length()>0){
			if(factorYValue.indexOf("A")>-1&&factorYValue.indexOf("B")>-1){
				sumScore=getResultByFormula("61.00");
			}else if(factorYValue.indexOf("A")>-1){
				sumScore=getResultByFormula("40.00");
			}else if(factorYValue.indexOf("C")>-1){
				sumScore=getResultByFormula("20.00");
			}else if(factorYValue.indexOf("E")>-1){
				sumScore=getResultByFormula("0.00");
			}else {
				// 选择“B/D”的时候为10分
				sumScore=getResultByFormula("10.00");
			}
		}
		
		return sumScore;
	}
	/**
	 * @功能：消防水源供应,分值获取
	 * @author liqiankun
	 * @param HttpServletRequest
	 * @param RiskInsRiskReportVo
	 * @return BigDecimal @日期：20190717
	 * @throws Exception 
	 */
	public BigDecimal obtainFireWaterSupplyScore(Map<String, String> objectMap,UtiFormula utiFormula) throws Exception{
		BigDecimal sumScore=BigDecimal.ZERO;
		// 获取选中的值
		String factorYValue = objectMap.get("RiskReportFireDanger.fireWaterSupply");
		factorYValue = factorYValue.replace("[", "").replace("]", "").replace("\"", "");
		if(factorYValue.length()>0){
			if(factorYValue.indexOf("B")>-1){
				sumScore=getResultByFormula("24.4");
			}else if(factorYValue.indexOf("A")>-1&&factorYValue.indexOf("C")>-1){
				sumScore=getResultByFormula("23.00");
			}else if(factorYValue.indexOf("A")>-1){
				sumScore=getResultByFormula("15.00");
			}else if(factorYValue.indexOf("C")>-1){
				sumScore=getResultByFormula("8.00");
			}else {
				// 选择“E/D”的时候为0分
				sumScore=getResultByFormula("0.00");
			}
		}
		
		return sumScore;
	}
	
	/**
	 * @功能：消防设施分值获取-简化版
	 * @author liqiankun
	 * @param HttpServletRequest
	 * @param RiskInsRiskReportVo
	 * @return BigDecimal @日期：20190717
	 * @throws Exception 
	 */
	public BigDecimal getFireFacilityScoreSimple(Map<String, String> objectMap,UtiFormula utiFormula) throws Exception{
		BigDecimal sumScore=BigDecimal.ZERO;
		// 获取选中的值
		String factorYValue = objectMap.get("RiskReportFireDanger.fireFacility");
		factorYValue = factorYValue.replace("[", "").replace("]", "").replace("\"", "");
		if(factorYValue.length()>0){
			if(factorYValue.indexOf("A")>-1&&factorYValue.indexOf("B")>-1){
				sumScore=getResultByFormula("83.30");
			}else if(factorYValue.indexOf("A")>-1){
				sumScore=getResultByFormula("60.00");
			}else if(factorYValue.indexOf("C")>-1){
				sumScore=getResultByFormula("40.00");
			}else if(factorYValue.indexOf("E")>-1){
				sumScore=getResultByFormula("0.00");
			}else {
				// 选择“B/D”的时候为20分
				sumScore=getResultByFormula("20.00");
			}
		}
		
		return sumScore;
	}
	
	/**
	 * @功能：将字符串计算公式计算出结果
	 * @param riskReportUtiFactor
	 *            因子
	 * @param value
	 *            因子值
	 * @return BigDecimal 分数
	 * @author 梁尚
	 * @throws Exception
	 * 			@时间：2017-11-20 @修改记录：
	 */
	private BigDecimal getResultByFormula(String formula) throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		BigDecimal result = BigDecimal.ZERO;
		result = new BigDecimal(engine.eval(formula).toString()).setScale(4, BigDecimal.ROUND_HALF_UP);
		return result;
	}
}
