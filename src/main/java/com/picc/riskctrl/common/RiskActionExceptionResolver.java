package com.picc.riskctrl.common;


import com.picc.riskctrl.base.BaseController;
import com.picc.riskctrl.common.vo.RiskDnaturalResponseVo;
import com.picc.riskctrl.common.vo.RiskSwitchControlResponseVo;
import com.picc.riskctrl.exception.*;
import com.picc.riskctrl.riskins.vo.RiskInsResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pdfc.framework.web.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 *
 * 控制层统一处理异常类
 *
 */

@ControllerAdvice
@Slf4j
public class RiskActionExceptionResolver<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public ApiResponse<T> exceptionHandler(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        String uuid = UUID.randomUUID().toString();
        System.out.println("ex-uuid:[" + uuid + "]");
        ex.printStackTrace();
        LOGGER.info("ex-uuid:[" + uuid + "]", ex);
        response.addIntHeader("exflag", 0);
        String err;
        try {
            err = URLEncoder.encode(ex.getLocalizedMessage(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            err = "An internal server error has occurred on the server. The requested operation failed.";
        }
        response.addHeader("excontent", "uuid:[" + uuid + "]" + err);
        return BaseController.error(null, ex.getMessage());
    }

    /**
     *
     * 取出栈中全部异常信息
     *
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    /**
     * 处理单号查询异常
     *
     * @author wangwenjie
     * @param ex
     * @return pdfc.framework.web.ApiResponse<Object>
     */
    @ExceptionHandler(RiskNoCanNotFoundException.class)
    @ResponseBody
    public ApiResponse<Object> riskNoCanNotFoundExceptionHandler(RiskNoCanNotFoundException ex) {
        logExceptionClassName(ex);
        log.error("==> {}: {}", ex.getMessage(), ex.getErrorData());
        return BaseController.error("异常单号：" + ex.getErrorData(),
                ApiResponse.FAIL_TEXT + ": " + ex.getMessage());
    }

    /**
     * 流程结束异常
     *
     * @author wangwenjie
     * @param ex
     * @return pdfc.framework.web.ApiResponse<Object>
     */
    @ExceptionHandler(ProcessInstanceControlException.class)
    @ResponseBody
    public ApiResponse<Object> processInstanceControlExceptionHandler(ProcessInstanceControlException ex) {
        logExceptionClassName(ex);
        log.error("==> {}: {}", ex.getMessage(), ex.getErrorData());
        return BaseController.error(null, ex.getMessage());
    }

    /**
     * service基础异常
     *
     * @author wangwenjie
     * @param ex
     * @return pdfc.framework.web.ApiResponse<java.lang.Object>
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ApiResponse<Object> serviceExceptionHandler(ServiceException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof RiskNoCanNotFoundException) {
            return this.riskNoCanNotFoundExceptionHandler((RiskNoCanNotFoundException) cause);
        } else if (cause instanceof ProcessInstanceControlException) {
            return this.processInstanceControlExceptionHandler((ProcessInstanceControlException) cause);
        }
        logExceptionClassName(ex);
        log.error("==> Error Log = {}", ex.getMessage());
        return BaseController.error(null, ex.getMessage());
    }

    /**
     * riskdnatural service异常处理
     *
     * @author wangwenjie
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskDnaturalResponseVo>
     */
    @ExceptionHandler(RiskDnaturalServiceException.class)
    @ResponseBody
    public ApiResponse<RiskDnaturalResponseVo> riskDnaturalServiceExceptionHandler(RiskDnaturalServiceException ex) {
        logExceptionClassName(ex);
        RiskDnaturalResponseVo riskDnaturalResponseVo = new RiskDnaturalResponseVo();
        riskDnaturalResponseVo.setMessage("error");
        riskDnaturalResponseVo.setQCexception(ex.getMessage());
        return BaseController.error(riskDnaturalResponseVo);
    }

    /**
     * 打印捕获异常类名称
     *
     * @author wangwenjie
     * @param ex
     * @return void
     */
    private void logExceptionClassName(ApplicationException ex) {
        log.error("==> {} Catch Exception ...", ex.getClass().getName());
    }

    /**
     * riskSwitchControl service 异常处理
     *
     * @author wangwenjie
     * @param ex
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskSwitchControlResponseVo>
     */
    @ExceptionHandler(RiskSwitchControlServiceException.class)
    @ResponseBody
    public ApiResponse<RiskSwitchControlResponseVo> riskSwitchControlServiceExceptionHandler(RiskSwitchControlServiceException ex) {
        logExceptionClassName(ex);
        RiskSwitchControlResponseVo vo = new RiskSwitchControlResponseVo();
        vo.setQCexception(ex.getMessage());
        vo.setMessage("error");
        return BaseController.error(vo);
    }

    /**
     * RiskInsUtiWeightEditService 异常处理
     *
     * @author wangwenjie
     * @param ex
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.common.vo.RiskSwitchControlResponseVo>
     */
    @ExceptionHandler(RiskInsUtiWeightEditServiceException.class)
    @ResponseBody
    public ApiResponse<String> riskSwitchControlServiceExceptionHandler(RiskInsUtiWeightEditServiceException ex) {
        logExceptionClassName(ex);
        return BaseController.error("error", ex.getMessage());
    }

    /**
     * RiskInsSaleServiceException 异常处理
     *
     * @author wangwenjie
     * @param ex
     * @return pdfc.framework.web.ApiResponse<com.picc.riskctrl.riskins.vo.RiskInsResponseVo>
     */
    @ExceptionHandler(RiskInsSaleServiceException.class)
    @ResponseBody
    public ApiResponse<RiskInsResponseVo> riskInsSaleServiceExceptionHandler(RiskInsSaleServiceException ex) {
        logExceptionClassName(ex);
        RiskInsResponseVo vo = new RiskInsResponseVo();
        vo.setStatus(ApiResponse.FAIL);
        vo.setMessage(ex.getMessage());
        //起始异常堆栈
        vo.setQCexception(getStackTrace(ex.getCause()));
        return BaseController.error(vo);
    }
}
