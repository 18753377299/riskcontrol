package com.picc.riskctrl.common.vo;

//基本信息维护

public class RiskBasicInfoTendVo {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    /**行业*/
    private String[] profession;
    /**出险原因*/
    private String[] claimReason;
    /**出具报告机构*/
    private String[] sender;

    public String[] getProfession() {
        return profession;
    }
    public void setProfession(String[] profession) {
        this.profession = profession;
    }
    public String[] getClaimReason() {
        return claimReason;
    }
    public void setClaimReason(String[] claimReason) {
        this.claimReason = claimReason;
    }
    public String[] getSender() {
        return sender;
    }
    public void setSender(String[] sender) {
        this.sender = sender;
    }
}
