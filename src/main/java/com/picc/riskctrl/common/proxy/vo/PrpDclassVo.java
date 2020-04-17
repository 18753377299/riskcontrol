package com.picc.riskctrl.common.proxy.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PrpDclassVo implements Serializable {
    /** 险种分类代码 */
    private String classCode;
    /** 旧险种分类代码 */
    private String oldClassCode;
    /** 险种分类简体中文全称 */
    private String classCName;
    /** 险种分类简体中文简称 */
    private String classSCName;
    /** 险种分类繁体中文名称 */
    private String classTName;
    /** 险种分类英文全称 */
    private String classEName;
    /** 险种分类英文简称 */
    private String classSEName;
    /** 创建人 */
    private String creatorCode;
    /** 创建时间 */
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date insertTimeForHis;
    /** 最后修改人 */
    private String upDateCode;
    /** 最后修改时间 */
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date operateTimeForHis;
    /** 生效日期 */
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date validDate;
    /** 失效日期 */
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date invalidDate;
    /** 审核标志 */
    private String auditFlag;
    /** 有效标志 */
    private String validStatus;
    /** 备注 */
    private String remark;

    // private CPZX_DaccountInfo cpzx_DaccountInfo;
}
