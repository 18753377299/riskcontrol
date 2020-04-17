package com.picc.riskctrl.common.utils;

import com.picc.riskctrl.riskinfo.superRiskReport.vo.SuperReportResponseVo;
import io.jsonwebtoken.lang.Assert;
import org.springframework.data.domain.Page;
import pdfc.framework.common.ResultPage;

/**
 * @author liqiankun
 * @功能:用于包装返回信息
 * @return List<RiskReportDataTotal>
 * @日期：20200107
 */
public class ResultPageUtils {
    /**
     * @return ResultPage
     * @功能:用于包装返回信息
     * @author liqiankun
     * @日期：20200107
     */
    @SuppressWarnings("unchecked")
    public static ResultPage returnPage(Page page) {
        ResultPage resultPage = new ResultPage();
        if (page != null) {
            resultPage.setData(page.getContent());
            resultPage.setPageNo(page.getPageable().getPageNumber() + 1);
            resultPage.setPerPage(page.getPageable().getPageSize());
            resultPage.setTotalCount(page.getTotalElements());
        }
        return resultPage;
    }

    /**
     * Page对象转换优秀风控报告结果集
     *
     * @param superReportResponseVo
     * @param page
     * @return com.picc.riskctrl.riskinfo.superRiskReport.vo.SuperReportResponseVo
     * @author wangwenjie
     */
    public static SuperReportResponseVo covertSuperReportResponseVo(SuperReportResponseVo superReportResponseVo, Page page) {
        Assert.notNull(superReportResponseVo, "object can not be null");
        Assert.notNull(page, "page can not be null");
        superReportResponseVo.setDataList(page.getContent());
        superReportResponseVo.setTotalCount(page.getTotalElements());
        superReportResponseVo.setTotalPage(page.getTotalPages());
        return superReportResponseVo;
    }

}
