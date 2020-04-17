package com.picc.riskctrl.base;

import com.picc.riskctrl.common.po.UserInfo;
import com.picc.riskctrl.common.utils.ServletSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller和sevice的一些公用方法
 *
 * @author wangwenjie
 * @date 2020-01-20
 */
public interface CommonSupport {
    /**
     * 获取当前请求用户信息
     *
     * @author wangwenjie
     */
    default UserInfo getUserInfo() {
        String userCode = getHeader("userCode");
        String comCode = getHeader("comCode");
//        String userName = getHeader("userName");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserCode(userCode);
        userInfo.setComCode(comCode);
        userInfo.setUserName("风控全国");
        return userInfo;
    }

    /**
     * 获取请求头信息
     *
     * @author wangwenjie
     * @param headerName
     * @return java.lang.String
     */
    default String getHeader(String headerName) {
        return ServletSupport.getRequestHeader(headerName);
    }

    /**
     * 设置响应头
     *
     * @author wangwenjie
     * @param headerName
     * @param headerValue
     * @return void
     */
    default void setHeader(String headerName, String headerValue) {
        ServletSupport.setResponseHeader(headerName, headerValue);
    }

    /**
     * 获取请求
     *
     * @author wangwenjie 
     * @return javax.servlet.http.HttpServletRequest
     */
    default HttpServletRequest getRequest() {
        return ServletSupport.getRequest();
    }

    /**
     * 获取响应
     *
     * @author wangwenjie
     * @return javax.servlet.http.HttpServletResponse
     */
    default HttpServletResponse getResponse() {
        return ServletSupport.getResponse();
    }
}
