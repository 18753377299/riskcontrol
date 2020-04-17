package com.picc.riskctrl.common.schema;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class RiskUnderwritebackId implements Serializable {

    private static final long serialVersionUID = -2917228955388418615L;

    private String executionId;

    private String updateType;

    private short serialNo;

    public RiskUnderwritebackId() {
    }

    @Column(name = "EXECUTIONID")
    public String getExecutionId() {
        return executionId;
    }


    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }


    @Column(name = "UPDATETYPE")
    public String getUpdateType() {
        return this.updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    @Column(name = "SERIALNO")
    public short getSerialNo() {
        return this.serialNo;
    }

    public void setSerialNo(short serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((executionId == null) ? 0 : executionId.hashCode());
        result = prime * result + serialNo;
        result = prime * result + ((updateType == null) ? 0 : updateType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RiskUnderwritebackId other = (RiskUnderwritebackId) obj;
        if (executionId == null) {
            if (other.executionId != null) {
                return false;
            }
        } else if (!executionId.equals(other.executionId)) {
            return false;
        }
        if (serialNo != other.serialNo) {
            return false;
        }
        if (updateType == null) {
            if (other.updateType != null) {
                return false;
            }
        } else if (!updateType.equals(other.updateType)) {
            return false;
        }
        return true;
    }

}
