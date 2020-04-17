package com.picc.riskctrl.riskins.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author zrw
 * @date 2020/01/13
 */
@Data
public class RiskReportSaleMainQueryVo {

    /** 照片档案号 */
    private String archivesNo;
    /** 企业名称 */
    private String companyName;
    /** 查勘机构 */
    private String exploreComcode;
    /** 查勘员 */
    private String explorer;
    /** 查勘员 */
    private String explorerCode;
    /** 审核人 */
    private String checkUpCode;
    /** 审核状态 */
    private String checkUpFlag;
    /** 查勘日期起期 */
    private Date exploreDateBegin;
    /** 查勘日期止期 */
    private Date exploreDateEnd;
    /** 移动端查询标志位 */
    private Boolean mobFlag = false;

}
