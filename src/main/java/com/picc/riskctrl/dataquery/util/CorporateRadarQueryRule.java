package com.picc.riskctrl.dataquery.util;


/**
 * @Description: 企业失信信息查询规则
 * @Create: 2019-04-24
 */
public class CorporateRadarQueryRule {
    //企业关键字
    private String keyword;
    //产品代码
    private String productCode;
    private String pageIndex;
    private String pageSize;

    //详情id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
