package com.picc.riskctrl.common.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: Code
 * @Author: 张日炜
 * @Date: 2020-01-15 09:54
 **/
@Data
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
