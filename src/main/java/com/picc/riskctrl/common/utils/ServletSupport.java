package com.picc.riskctrl.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * servlet支持
 *
 * @author wangwenjie
 * @date 2020-01-07
 */
@Slf4j
public class ServletSupport {

    private static Optional<HttpServletRequest> getCurrentRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> requestAttributes instanceof ServletRequestAttributes)
                .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
                .map(ServletRequestAttributes::getRequest);
    }

    private static Optional<HttpServletResponse> getCurrentResponse() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> requestAttributes instanceof ServletRequestAttributes)
                .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
                .map(ServletRequestAttributes::getResponse);
    }

    /**
     * 获取当前请求
     *
     * @author wangwenjie
     */
    public static HttpServletRequest getRequest() {
        return getCurrentRequest()
                .orElseThrow(() -> new RuntimeException("get request failed"));
    }

    /**
     * 获取当前响应
     *
     * @author wangwenjie
     */
    public static HttpServletResponse getResponse() {
        return getCurrentResponse()
                .orElseThrow(() -> new RuntimeException("get response failed"));
    }


    /**
     * 获取请求属性
     *
     * @author wangwenjie
     */
    public static Object getRequestAttribute(String attrName) {
        return getCurrentRequest()
                .map(request -> request.getAttribute(attrName))
                .orElseThrow(() -> new IllegalArgumentException("unknown request attribute"));
    }

    /**
     * 获取请求头信息
     *
     * @author wangwenjie
     * @param headerName
     * @return java.lang.Object
     */
    public static String getRequestHeader(String headerName) {
        return getCurrentRequest()
                .map(request -> request.getHeader(headerName))
                .orElseThrow(() -> new IllegalArgumentException("unknown request header name"));
    }

    /**
     * 为当前响应 头 设置属性
     *
     * @author wangwenjie
     * @param headerKey
     * @param headerValue
     * @return void
     */
    public static void setResponseHeader(String headerKey, String headerValue) {
        getResponse().setHeader(headerKey, headerValue);
    }
}
