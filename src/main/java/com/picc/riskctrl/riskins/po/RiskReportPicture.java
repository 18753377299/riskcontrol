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
@Table(name = "riskreport_picture")
public class RiskReportPicture implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /** 复合主键 */
    private RiskReportPictureId id;
    /** 原照片路径 */
    private String imageUrl;
    /** 压缩照片路径 */
    private String thumUrl;
    /** 用户代码 */
    private String userCode;
    /** 影像id */
    private String imageID;
    /** 备注 */
    private String remark;
    /** 扩展字段一 */
    private String ext1;
    /** 扩展字段二 */
    private String ext2;
    /** 扩展字段三 */
    private String ext3;
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

    /**
     * 联合主键
     */
    @EmbeddedId
    @AttributeOverrides({@AttributeOverride(name = "riskFileNo", column = @Column(name = "RISKFILENO")),
        @AttributeOverride(name = "imageName", column = @Column(name = "IMAGENAME")),
        @AttributeOverride(name = "childPath", column = @Column(name = "CHILDPATH"))})

    public RiskReportPictureId getId() {
        return id;
    }

    public void setId(RiskReportPictureId id) {
        this.id = id;
    }

    @Column(name = "IMAGEURL")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(name = "THUMURL")
    public String getThumUrl() {
        return thumUrl;
    }

    public void setThumUrl(String thumUrl) {
        this.thumUrl = thumUrl;
    }

    @Column(name = "USERCODE")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Column(name = "IMAGEID")
    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "EXT1")
    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    @Column(name = "EXT2")
    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    @Column(name = "EXT3")
    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    // @Column(name = "INSERTTIMEFORHIS", insertable = false, updatable = false)
    @CreatedDate
    @Column(name = "INSERTTIMEFORHIS", updatable = false)
    public Date getInsertTimeForHis() {
        return insertTimeForHis;
    }

    public void setInsertTimeForHis(Date insertTimeForHis) {
        this.insertTimeForHis = insertTimeForHis;
    }

    // @Column(name = "OPERATETIMEFORHIS", insertable = false)
    @LastModifiedDate
    @Column(name = "OPERATETIMEFORHIS")
    public Date getOperateTimeForHis() {
        return operateTimeForHis;
    }

    public void setOperateTimeForHis(Date operateTimeForHis) {
        this.operateTimeForHis = operateTimeForHis;
    }
}