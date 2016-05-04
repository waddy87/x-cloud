package com.sugon.cloudview.cloudmanager.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;

public class ShiroUserFilter extends UserFilter {

    public final static String X_R = "X-Requested-With";
    public final static String X_R_VALUE = "XMLHttpRequest";

    /**
     * 加入ajax查询参数，以便跳转至超时登录页面。
     * 
     * @param request
     * @param response
     * @throws IOException
     * @see org.apache.shiro.web.filter.AccessControlFilter#redirectToLogin(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse)
     */
    @Override
    protected void redirectToLogin(ServletRequest request,
            ServletResponse response) throws IOException {
        // HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // HttpServletResponse httpServletResponse = (HttpServletResponse)
        // response;
        // String xrv = httpServletRequest.getHeader(X_R);
        //
        // if (xrv != null && xrv.equalsIgnoreCase(X_R_VALUE)) {
        // httpServletResponse.setHeader("sessionstatus", "timeoutajax");
        // } else {
        // super.redirectToLogin(request, response);
        // }
        super.redirectToLogin(request, response);
    }
}
