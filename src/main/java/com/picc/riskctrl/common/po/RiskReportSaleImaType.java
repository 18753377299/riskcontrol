package com.picc.riskctrl.common.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "RiskReport_saleImaType")
public class RiskReportSaleImaType implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /** 复合主键 */
    private RiskReportSaleImaTypeId id;
    /** 所属类别名称 */
    private String typeCName;
    /** 存在风险照片数量 */
    private Integer imageRepulseSum;
    /** 照片数量 */
    private Integer imageSum;
    /** 备注 */
    private String remark;
    /** 插入时间 */
    private Date insertTimeForHis;
    /** 更新时间 */
    private Date operateTimeForHis;
    /** 风控主表 */
    @JSONField(serialize = false)
    private RiskReportSaleMain riskReportSaleMain;
    /** 风控销售员版照片 */
    private List<RiskReportSaleImage> riskReportSaleImageList = new ArrayList<RiskReportSaleImage>(0);
    /** 该类型中首张照片的路径 */
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARCHIVESNO", referencedColumnName = "ARCHIVESNO", nullable = false, insertable = false, updatable = false)
    public RiskReportSaleMain getRiskReportSaleMain() {
        return riskReportSaleMain;
    }

    public void setRiskReportSaleMain(RiskReportSaleMain riskReportSaleMain) {
        this.riskReportSaleMain = riskReportSaleMain;
    }

    /**
     * 联合主键
     */
    @EmbeddedId
    @AttributeOverrides({@AttributeOverride(name = "archivesNo", column = @Column(name = "ARCHIVESNO")),
            @AttributeOverride(name = "imageType", column = @Column(name = "IMAGETYPE"))})
    public RiskReportSaleImaTypeId getId() {
        return id;
    }

    public void setId(RiskReportSaleImaTypeId id) {
        this.id = id;
    }

    @Column(name = "IMAGEREPULSESUM")
    public Integer getImageRepulseSum() {
        return imageRepulseSum;
    }

    public void setImageRepulseSum(Integer imageRepulseSum) {
        this.imageRepulseSum = imageRepulseSum;
    }

    @Column(name = "IMAGESUM")
    public Integer getImageSum() {
        return imageSum;
    }

    public void setImageSum(Integer imageSum) {
        this.imageSum = imageSum;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "INSERTTIMEFORHIS", insertable = false, updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    @Column(name = "OPERATETIMEFORHIS", insertable = false)
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }

    @Column(name = "TYPECNAME")
    public String getTypeCName() {
        return typeCName;
    }

    public void setTypeCName(String typeCName) {
        this.typeCName = typeCName;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "riskReportSaleImaType")
    public List<RiskReportSaleImage> getRiskReportSaleImageList() {
        return riskReportSaleImageList;
    }

    public void setRiskReportSaleImageList(List<RiskReportSaleImage> riskReportSaleImageList) {
        this.riskReportSaleImageList = riskReportSaleImageList;
    }

    @Transient
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}