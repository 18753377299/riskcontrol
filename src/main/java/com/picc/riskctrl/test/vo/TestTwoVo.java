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
 * TestTwoVo对象.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("TestTwoVo对象")
public class TestTwoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 对应字段：id */
	@ApiModelProperty()
	private Integer id;
	/** 对应字段：name */
	@ApiModelProperty()
	private String name;
	/** 对应字段：password */
	@ApiModelProperty()
	private String password;
}
