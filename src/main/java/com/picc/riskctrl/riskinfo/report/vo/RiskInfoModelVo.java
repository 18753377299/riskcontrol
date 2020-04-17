package com.picc.riskctrl.riskinfo.report.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("RiskInfoModelVo对象")
public class RiskInfoModelVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="序号")
	private Integer id;
	/**  父节点 */
	private String parentId;
	/**  子节点 */
	private String chilId;
	/**  节点类型 1-非叶节点  0-叶节点 */
	private String nodeType;
	/**  菜单名称  */
	private String nodeCName;
	/**  风控模板路径 */
	private String url;
	/**  有效标志位 */
	private String validStatus;
	/**  同级显示顺序  */
	private String orderNo;
	/**  子风控模板菜单项  */
	private List<RiskInfoModelVo> childModel;
	/**  提供父级放置将最后一级图片url的路径  */
	private List<String> urls;
	
}
