package com.picc.riskctrl.riskinfo.expert.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @功能：评论表主键
 * @author liqiankun
 * @throws Exception 
 * @日期 20200109
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskInfoDiscussId implements Serializable{

	private static final long serialVersionUID = 1L;
	/**专家编号*/
	private Integer expertNo;
	/**序号*/
	private Integer serialNo;
	
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
		RiskInfoDiscussId other = (RiskInfoDiscussId) obj;
		if (expertNo == null) {
			if (other.expertNo != null) {
                return false;
            }
		} else if (!expertNo.equals(other.expertNo)) {
            return false;
        }
		if (serialNo == null) {
			if (other.serialNo != null) {
                return false;
            }
		} else if (!serialNo.equals(other.serialNo)) {
            return false;
        }
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expertNo == null) ? 0 : expertNo.hashCode());
		result = prime * result + ((serialNo == null) ? 0 : serialNo.hashCode());
		return result;
	}
	
}
