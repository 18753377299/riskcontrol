package com.picc.riskctrl.riskcheck.template.newBuilder;

/**
 * @Description: 完整版构造者
 * @Author: wangwenjie
 * @Create: 2019-04-30
 */
public class NewIntactTemplateBuilder extends NewTemplateBuilder{

    @Override
    protected NewTemplateBuilder buildWordReport() {
        templateInfo.setWordReportTemplate("/riskCheckReport.docx");
        return this;
    }

    //完整版pdf
    @Override
    protected NewTemplateBuilder buildPdfReport() {
        templateInfo.setDefaultPdfTemplate("/riskCheckReport_pdf.docx");
        return this;
    }
}
