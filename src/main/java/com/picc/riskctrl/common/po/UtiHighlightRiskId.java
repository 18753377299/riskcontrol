package com.picc.riskctrl.common.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UtiHighlightRiskId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**风控模板号*/
	private String riskModel;
	/**存储表名*/
	private String fromTable;
	/**存储字段名*/
	private String fromColumn;
	/**取值*/
	private String riskValue;
	@Column(name = "riskmodel", nullable = false)
	public String getRiskModel() {
		return riskModel;
	}
	public void setRiskModel(String riskModel) {
		this.riskModel = riskModel;
	}
	@Column(name = "fromtable", nullable = false)
	public String getFromTable() {
		return fromTable;
	}
	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}
	@Column(name = "fromcolumn", nullable = false)
	public String getFromColumn() {
		return fromColumn;
	}
	public void setFromColumn(String fromColumn) {
		this.fromColumn = fromColumn;
	}
	@Column(name = "riskvalue", nullable = false)
	public String getRiskValue() {
		return riskValue;
	}
	public void setRiskValue(String riskValue) {
		this.riskValue = riskValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromColumn == null) ? 0 : fromColumn.hashCode());
		result = prime * result + ((fromTable == null) ? 0 : fromTable.hashCode());
		result = prime * result + ((riskModel == null) ? 0 : riskModel.hashCode());
		result = prime * result + ((riskValue == null) ? 0 : riskValue.hashCode());
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
		UtiHighlightRiskId other = (UtiHighlightRiskId) obj;
		if (fromColumn == null) {
			if (other.fromColumn != null) {
                return false;
            }
		} else if (!fromColumn.equals(other.fromColumn)) {
            return false;
        }
		if (fromTable == null) {
			if (other.fromTable != null) {
                return false;
            }
		} else if (!fromTable.equals(other.fromTable)) {
            return false;
        }
		if (riskModel == null) {
			if (other.riskModel != null) {
                return false;
            }
		} else if (!riskModel.equals(other.riskModel)) {
            return false;
        }
		if (riskValue == null) {
			if (other.riskValue != null) {
                return false;
            }
		} else if (!riskValue.equals(other.riskValue)) {
            return false;
        }
		return true;
	}
	
	
	
}
