package org.waddys.xcloud.api;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Parameter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import net.sf.json.JSONObject;

/**
 * 展示 Rest API 列表
 * 
 * @author zhangdapeng
 *
 */
@RestController
@RequestMapping(path = "/api", produces = { "application/json;charset=UTF-8" })
public class RestInfo {
    private static final Logger logger = LoggerFactory.getLogger(RestInfo.class);

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private NumberFormat fmtI = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ENGLISH));
    private NumberFormat fmtD = new DecimalFormat("###,##0.000", new DecimalFormatSymbols(Locale.ENGLISH));

    private String summary() {
        StringBuffer titleBuffer = new StringBuffer("");
        titleBuffer.append("\n===== Rest App Summary =====\n ");
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        titleBuffer.append("Uptime: " + toDuration(runtime.getUptime()) + "\n");
        titleBuffer.append(" Team: zhangdapeng@sugon.com wangchch@sugon.com fanxiao@sugon.com\n\n");
//        titleBuffer.append("Event Statitcs:\n");
//        titleBuffer.append("接收事件总数：" + VmEventListener.allCount + "\n");
//        titleBuffer.append("丢弃事件数量：" + VmEventListener.discardCount + "\n");
//        titleBuffer.append("处理成功数量：" + VmEventListener.successCount + "\n");
//        titleBuffer.append("处理失败数量：" + VmEventListener.failedCount + "\n");
        titleBuffer.append("\n\n");
        return titleBuffer.toString();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listApi(HttpServletRequest request) {
        StringBuffer sBuffer = new StringBuffer("");
        try {
            // WebApplicationContext wc = WebApplicationContextUtils
            // .getRequiredWebApplicationContext(request.getSession().getServletContext());
            // Map<String, Object> beanMap =
            // wc.getBeansWithAnnotation(RestController.class);
            // RequestMappingHandlerMapping rmhp =
            // wc.getBean(RequestMappingHandlerMapping.class);
            // Map<RequestMappingInfo, HandlerMethod> map =
            // rmhp.getHandlerMethods();
            Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
            int index = 0;
            for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
                RequestMappingInfo info = m.getKey();
                // sBuffer.append(JsonUtil.toJson(info) + "\n");
                // sBuffer.append(info.getConsumesCondition());
                // sBuffer.append(" - ");
                // sBuffer.append(info.getCustomCondition());
                // sBuffer.append(" - ");
                // sBuffer.append(info.getHeadersCondition());
                // sBuffer.append(" - ");
                String action = info.getMethodsCondition().toString();
                String url = info.getPatternsCondition().toString();
                if (!url.startsWith("[/api"))
                    continue;
                index++;
                sBuffer.append(action);
                sBuffer.append(" - \t");
                sBuffer.append(url);
                // sBuffer.append(" - ");
                // sBuffer.append(info.getParamsCondition());
                // sBuffer.append(" - ");
                // sBuffer.append(info.getProducesCondition());
                // sBuffer.append(" \n ");

                HandlerMethod method = m.getValue();
                // sBuffer.append(method.getMethod().getName());
                // sBuffer.append(" - ");
                // sBuffer.append(method.getMethod().getDeclaringClass());
                // Class<?>[] types = method.getMethod().getParameterTypes();
                Parameter[] params = method.getMethod().getParameters();
                if (params != null) {
                    int paramIndex = 0;
                    for (Parameter param : params) {
                        if (param.getAnnotation(PathVariable.class) != null)
                            continue;
                        paramIndex++;
                        sBuffer.append(" \n ");
                        try {
                            sBuffer.append("param" + paramIndex + ": " + param.getType().getSimpleName());
                            Object object = param.getType().newInstance();
                            sBuffer.append(" : " + JSONObject.fromObject(object).toString());
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }
                sBuffer.append(" \n ");
                try {
                    sBuffer.append("return: " + method.getMethod().getReturnType().getSimpleName());
                    Object object = method.getMethod().getReturnType().newInstance();
                    // sBuffer.append(" : " +
                    // JSONObject.fromObject(object).toString());
                } catch (Exception e) {
                    // TODO: handle exception
                }
                sBuffer.append(" \n\n ");
                // System.out.println();
            }
            StringBuffer titleBuffer = new StringBuffer("");
            titleBuffer.append("\n===== 共 " + index + " 个API =====\n ");
            titleBuffer.append("Auto Generate: " + format.format(new Date()) + "\n\n");
            sBuffer.insert(0, titleBuffer);
            sBuffer.insert(0, summary());
        } catch (Exception e) {
            logger.error("" + e.getMessage());
        }

        return sBuffer.toString();
    }

    protected String toDuration(double uptime) {
        uptime /= 1000;
        if (uptime < 60) {
            return fmtD.format(uptime) + " seconds";
        }
        uptime /= 60;
        if (uptime < 60) {
            long minutes = (long) uptime;
            String s = fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
            return s;
        }
        uptime /= 60;
        if (uptime < 24) {
            long hours = (long) uptime;
            long minutes = (long) ((uptime - hours) * 60);
            String s = fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
            if (minutes != 0) {
                s += " " + fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
            }
            return s;
        }
        uptime /= 24;
        long days = (long) uptime;
        long hours = (long) ((uptime - days) * 24);
        String s = fmtI.format(days) + (days > 1 ? " days" : " day");
        if (hours != 0) {
            s += " " + fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
        }
        return s;
    }

}
