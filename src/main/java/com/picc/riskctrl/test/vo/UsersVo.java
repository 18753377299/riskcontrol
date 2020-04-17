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
 * UsersVo对象.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("UsersVo对象")
public class UsersVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 对应字段：id */
	@ApiModelProperty()
	private Integer id;
	/** 对应字段：name */
	@ApiModelProperty()
	private String name;
	/** 对应字段：age */
	@ApiModelProperty()
	private Integer age;
	/** 对应字段：address */
	@ApiModelProperty()
	private String address;
}
