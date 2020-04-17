package com.picc.riskctrl.common.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UtiBackRuleConfigId implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    private String ruleCode;

    private String comCode;

    @Column(name = "RULECODE", nullable = false)
    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    @Column(name = "COMCODE", nullable = false)
    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comCode == null) ? 0 : comCode.hashCode());
        result = prime * result + ((ruleCode == null) ? 0 : ruleCode.hashCode());
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
        UtiBackRuleConfigId other = (UtiBackRuleConfigId) obj;
        if (comCode == null) {
            if (other.comCode != null) {
                return false;
            }
        } else if (!comCode.equals(other.comCode)) {
            return false;
        }
        if (ruleCode == null) {
            if (other.ruleCode != null) {
                return false;
            }
        } else if (!ruleCode.equals(other.ruleCode)) {
            return false;
        }
        return true;
    }
}
