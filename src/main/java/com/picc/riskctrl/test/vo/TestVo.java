package com.picc.riskctrl.test.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * TestVo对象.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("TestVo对象")
public class TestVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 对应字段：id */
	@ApiModelProperty()
	private String id;
	/** 对应字段：name */
	@ApiModelProperty()
	private String name;
}
