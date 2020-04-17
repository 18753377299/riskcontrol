package com.picc.riskctrl.riskins.po;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "riskreport_construct")
public class RiskReportConstruct implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /** 风控档案编号 */
    private String riskFileNo;
    /**
     * 是否存在地下资产 A 存在地下资产 B 不存在地下资产
     */
    private String underAssetsFlag;
    /**
     * 地下保险资产浸水致损性质 A 浸水全损 B 部分致损 C 不受影响
     */
    private String underAssetsProperty;
    /**
     * 地下保险可水损资产占比 A <10% B 10%-80% C >80%
     */
    private String underAssetsRatio;
    /**
     * 排水管情况（专职） A 非内置 B 内置无破损或锈蚀 C 内置有破损或锈蚀
     */
    private String pipeline;
    /**
     * 排水管情况（兼职） A 是 B 否
     */
    private String pipelineFlag;
    /**
     * 建筑物内建筑防火分隔设施类型 A 防火门 B 防火墙 C 防火卷帘
     */
    private String fireproofingType;
    /**
     * 防火分隔是否有效 A 是 B 否
     */
    private String fireproofingFlag;
    /** 隐患部位和风险隐患描述 */
    private String hiddenDescription;
    /**
     * 是否安装避雷设施 A 是 B 否
     */
    private String lightningPro;
    /**
     * 是否为高耸/孤立/突出建筑（专职） A 100米范围内是 B 500米范围内是 C 否
     */
    private String buildOneFiveHFlag;
    /**
     * 是否为高耸/孤立/突出建筑 A 是 B 否
     */
    private String highBuildFlag;
    /** 补充说明信息 */
    private String addMessage;
    /** 插入时间 */
    private Date insertTimeForHis;
    /** 更新时间 */
    private Date operateTimeForHis;
    /** 风控主表 */
    private RiskReportMain riskReportMain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RISKFILENO", nullable = false, insertable = false, updatable = false)
    public RiskReportMain getRiskReportMain() {
        return riskReportMain;
    }

    public void setRiskReportMain(RiskReportMain riskReportMain) {
        this.riskReportMain = riskReportMain;
    }

    @Id
    @Column(name = "RISKFILENO", nullable = false)
    public String getRiskFileNo() {
        return riskFileNo;
    }

    public void setRiskFileNo(String riskFileNo) {
        this.riskFileNo = riskFileNo;
    }

    @Column(name = "UNDERASSETSFLAG")
    public String getUnderAssetsFlag() {
        return underAssetsFlag;
    }

    public void setUnderAssetsFlag(String underAssetsFlag) {
        this.underAssetsFlag = underAssetsFlag;
    }

    @Column(name = "UNDERASSETSPROPERTY")
    public String getUnderAssetsProperty() {
        return underAssetsProperty;
    }

    public void setUnderAssetsProperty(String underAssetsProperty) {
        this.underAssetsProperty = underAssetsProperty;
    }

    @Column(name = "UNDERASSETSRATIO")
    public String getUnderAssetsRatio() {
        return underAssetsRatio;
    }

    public void setUnderAssetsRatio(String underAssetsRatio) {
        this.underAssetsRatio = underAssetsRatio;
    }

    @Column(name = "PIPELINE")
    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    @Column(name = "PIPELINEFLAG")
    public String getPipelineFlag() {
        return pipelineFlag;
    }

    public void setPipelineFlag(String pipelineFlag) {
        this.pipelineFlag = pipelineFlag;
    }

    @Column(name = "FIREPROOFINGTYPE")
    public String getFireproofingType() {
        return fireproofingType;
    }

    public void setFireproofingType(String fireproofingType) {
        this.fireproofingType = fireproofingType;
    }

    @Column(name = "FIREPROOFINGFLAG")
    public String getFireproofingFlag() {
        return fireproofingFlag;
    }

    public void setFireproofingFlag(String fireproofingFlag) {
        this.fireproofingFlag = fireproofingFlag;
    }

    @Column(name = "HIDDENDESCRIPTION")
    public String getHiddenDescription() {
        return hiddenDescription;
    }

    public void setHiddenDescription(String hiddenDescription) {
        this.hiddenDescription = hiddenDescription;
    }

    @Column(name = "LIGHTNINGPRO")
    public String getLightningPro() {
        return lightningPro;
    }

    public void setLightningPro(String lightningPro) {
        this.lightningPro = lightningPro;
    }

    @Column(name = "HIGHBUILDFLAG")
    public String getHighBuildFlag() {
        return highBuildFlag;
    }

    public void setHighBuildFlag(String highBuildFlag) {
        this.highBuildFlag = highBuildFlag;
    }

    @Column(name = "BUILDONEFIVEHFLAG")
    public String getBuildOneFiveHFlag() {
        return buildOneFiveHFlag;
    }

    public void setBuildOneFiveHFlag(String buildOneFiveHFlag) {
        this.buildOneFiveHFlag = buildOneFiveHFlag;
    }

    @Column(name = "ADDMESSAGE")
    public String getAddMessage() {
        return addMessage;
    }

    public void setAddMessage(String addMessage) {
        this.addMessage = addMessage;
    }

    // @Column(name = "INSERTTIMEFORHIS",insertable=false, updatable = false)
    @CreatedDate
    @Column(name = "INSERTTIMEFORHIS", updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    // @Column(name = "OPERATETIMEFORHIS",insertable=false)
    @LastModifiedDate
    @Column(name = "OPERATETIMEFORHIS")
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }
}