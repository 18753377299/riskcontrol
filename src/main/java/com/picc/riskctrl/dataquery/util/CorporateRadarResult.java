package com.picc.riskctrl.dataquery.util;

/**
 * @Description: 企业失信信息结果集
 */
public class CorporateRadarResult {
    private Object data;
    private Long total;

    public CorporateRadarResult(){}

    public CorporateRadarResult(Object data,Long total){
        this.data = data;
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}