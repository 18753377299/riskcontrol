package ins.platform.common.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@ApiModel("Code对象")
public class Code implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String code;// NOPMD
	private String name;
	private String className;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
