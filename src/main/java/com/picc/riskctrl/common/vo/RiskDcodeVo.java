package com.picc.riskctrl.common.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskDcodeVo对象")
public class RiskDcodeVo   implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	/** 代码类型 */
	private String codeType;
	/** 行业代码/工程类别代码 */
	private String codeCode;
	
	private String codeCname;
	
	private String [] validStatus;

}
