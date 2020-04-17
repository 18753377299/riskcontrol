package com.picc.riskctrl.riskcheck.template.newBuilder;


import com.picc.riskctrl.riskcheck.template.factory.DefaultTemplates;

/**
 * @Description: 巡检报告下载路径
 * @Author: wangwenjie
 * @Create: 2019-04-29
 */
public class NewTemplateInfo extends DefaultTemplates {

    /**
     * word通知书，word报告，pdf
     */
    private String wordNoticeTemplate;
    private String wordReportTemplate;

    public String getWordNoticeTemplate() {
        return wordNoticeTemplate;
    }

    public void setWordNoticeTemplate(String wordNoticeTemplate) {
        this.wordNoticeTemplate = wordNoticeTemplate;
    }

    public String getWordReportTemplate() {
        return wordReportTemplate;
    }

    public void setWordReportTemplate(String wordReportTemplate) {
        this.wordReportTemplate = wordReportTemplate;
    }
}
