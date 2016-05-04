package com.sugon.cloudview.cloudmanager.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//暂时无用
public class BaseFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger logger = LoggerFactory
            .getLogger(BaseFormAuthenticationFilter.class);

    /**
     * 即是否允许访问，返回true表示允许；返回false，将到onAccessDenied进行处理
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
            ServletResponse response, Object mappedValue) {
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request,
            ServletResponse response) throws Exception {
        logger.debug("======onAccessDenied=========拒绝访问自己处理============");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        System.out.println("==FormAuthenticationFilter===onAccessDenied===="
                + SecurityUtils.getSubject().getPrincipal());
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            // 判断session里是否有用户信息
            if (httpServletRequest.getHeader("x-requested-with") != null
                    && httpServletRequest.getHeader("x-requested-with")
                            .equalsIgnoreCase("XMLHttpRequest")) {
                // 如果是ajax请求响应头会有，x-requested-with
                httpServletResponse.setHeader("sessionstatus", "timeoutajax");// 在响应头设置session状态
            }
        }
        return true;

    }
}
