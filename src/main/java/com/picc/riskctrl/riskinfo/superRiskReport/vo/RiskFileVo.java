package com.picc.riskctrl.riskinfo.superRiskReport.vo;

public class RiskFileVo {
    //风控报告名称
    private String riskFileName;
    //出具报告年度
    private String riskYear;
    //行业
    private String professions;
    //险种
    private String riskNames;
    //出具报告机构
    private String senders;
    //风控报告来源
    private String ascNames;
    //审核状态
    private String validStatus;

    public String getValidStatus() {
        return validStatus;
    }
    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }
    public String getRiskFileName() {
        return riskFileName;
    }
    public void setRiskFileName(String riskFileName) {
        this.riskFileName = riskFileName;
    }
    public String getRiskYear() {
        return riskYear;
    }
    public void setRiskYear(String riskYear) {
        this.riskYear = riskYear;
    }
    public String getProfessions() {
        return professions;
    }
    public void setProfessions(String professions) {
        this.professions = professions;
    }
    public String getRiskNames() {
        return riskNames;
    }
    public void setRiskNames(String riskNames) {
        this.riskNames = riskNames;
    }
    public String getSenders() {
        return senders;
    }
    public void setSenders(String senders) {
        this.senders = senders;
    }
    public String getAscNames() {
        return ascNames;
    }
    public void setAscNames(String ascNames) {
        this.ascNames = ascNames;
    }

}
