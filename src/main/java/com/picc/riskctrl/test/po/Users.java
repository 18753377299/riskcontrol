package com.picc.riskctrl.test.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * 通过ins-framework-mybatis-generator工具自动生成，请勿手工修改。表users的PO对象<br/>
 * 对应表名：users
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 对应字段：id */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/** 对应字段：name */
	@Column(name = "name")
	private String name;
	/** 对应字段：age */
	@Column(name = "age")
	private Integer age;
	/** 对应字段：address */
	@Column(name = "address")
	private String address;
}
