package com.picc.riskctrl.common.schema;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UtiKeyId implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 表名 */
	private String tableName;
	/** 字段代码 */
	private String fieldName;

	public UtiKeyId() {
	}

	@Column(name = "TABLENAME")
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "FIELDNAME")
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
    public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof UtiKeyId)) {
			return false;
		}
		UtiKeyId castOther = (UtiKeyId) other;

		return ((this.getTableName() == castOther.getTableName()) || (this
				.getTableName() != null
				&& castOther.getTableName() != null && this.getTableName()
				.equals(castOther.getTableName())))
				&& ((this.getFieldName() == castOther.getFieldName()) || (this
						.getFieldName() != null
						&& castOther.getFieldName() != null && this
						.getFieldName().equals(castOther.getFieldName())));
	}

	@Override
    public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTableName() == null ? 0 : this.getTableName().hashCode());
		result = 37 * result
				+ (getFieldName() == null ? 0 : this.getFieldName().hashCode());
		return result;
	}

}
