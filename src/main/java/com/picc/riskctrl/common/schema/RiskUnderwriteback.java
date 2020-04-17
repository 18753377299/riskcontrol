package com.picc.riskctrl.common.schema;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "RISKUNDERWRITEBACK")
public class RiskUnderwriteback implements Serializable {

    private static final long serialVersionUID = -7429296090795331459L;

    /** 序号 */
    private RiskUnderwritebackId id;

    /** 打回原因 */
    private String repulsesugggest;
    /** 操作员代码 */
    private String operatorCode ;

    public RiskUnderwriteback() {
    }

    /**
     * 序号
     */
    @EmbeddedId
    @AttributeOverrides( {
            @AttributeOverride(name = "executionId", column = @Column(name = "EXECUTIONID")),
            @AttributeOverride(name = "updateType", column = @Column(name = "UPDATETYPE")),
            @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
    public RiskUnderwritebackId getId() {
        return this.id;
    }

    public void setId(RiskUnderwritebackId id) {
        this.id = id;
    }

    /**
     * 操作员代码
     */

    @Column(name = "OPERATORCODE")
    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }


    /** 插入时间 */
    private Date insertTimeForHis;
    /** 更新时间 */
    private Date operateTimeForHis;

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
    /**
     * 打回原因
     * @return
     */
    @Column(name = "REPULSESUGGGEST")
    public String getRepulsesugggest() {
        return repulsesugggest;
    }

    public void setRepulsesugggest(String repulsesugggest) {
        this.repulsesugggest = repulsesugggest;
    }
}
