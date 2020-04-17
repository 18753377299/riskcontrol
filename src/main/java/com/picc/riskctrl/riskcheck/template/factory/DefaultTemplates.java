package com.picc.riskctrl.riskcheck.template.factory;


/**
 * @Description: 默认模板类，可直接使用或根据具体业务扩展使用
 * @Author: wangwenjie
 * @Create: 2019-04-12
 */
public class DefaultTemplates {

    private String defaultWordTemplate;
    private String defaultPdfTemplate;

    public String getDefaultWordTemplate() {
        return defaultWordTemplate;
    }

    public void setDefaultWordTemplate(String defaultWordTemplate) {
        this.defaultWordTemplate = defaultWordTemplate;
    }

    public String getDefaultPdfTemplate() {
        return defaultPdfTemplate;
    }

    public void setDefaultPdfTemplate(String defaultPdfTemplate) {
        this.defaultPdfTemplate = defaultPdfTemplate;
    }
}