package com.sugon.cloudview.cloudmanager.web.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sugon.cloudview.cloudmanager.resource.api.common.DateJsonValueProcessor;
import com.sugon.cloudview.cloudmanager.shiro.RetryLimitHashedCredentialsMatcher;
import com.sugon.cloudview.cloudmanager.taskMgmt.service.bo.TaskInfo;
import com.sugon.cloudview.cloudmanager.taskMgmt.service.exception.TaskinfoException;
import com.sugon.cloudview.cloudmanager.taskMgmt.service.service.TaskInfoService;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.web.validatecode.ValidateCode;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory
            .getLogger(LoginController.class);

    @Autowired
    private RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher;
    @Autowired
    private TaskInfoService taskInfoService;

    @RequestMapping(value = "/checkValidateCode", method = RequestMethod.POST)
    public @ResponseBody String checkValidateCode(
            @RequestParam(value = "validateCode", required = false) String validateCode,
            HttpServletRequest request) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", false);
        resultObject.put("message", "验证码错误！");
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String code = (String) session.getAttribute("validateCode");
        String submitCode = WebUtils.getCleanParam(request, "validateCode");
        if (null != submitCode
                && StringUtils.equals(code, submitCode.toLowerCase())) {
            resultObject.put("success", true);
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/validateCode")
    public void validateCode(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        String verifyCode = ValidateCode.generateTextCode(
                ValidateCode.TYPE_NUM_LOWER, 4, null);
        request.getSession().setAttribute("validateCode", verifyCode);
        response.setContentType("image/jpeg");
        BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 36,
                3, true, new Color(238, 238, 238), new Color(153, 153, 153),
                null);
        ImageIO.write(bim, "JPEG", response.getOutputStream());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView first() {
        return new ModelAndView("redirect:/index");
    }

    @RequestMapping(value = "/userlogin", method = RequestMethod.GET)
    public ModelAndView userLogin() {
        return new ModelAndView("userlogin");
    }

    // // // 解决405问题
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginGet(
            @RequestParam(value = "path", required = false) String path) {
        Subject currentUser = SecurityUtils.getSubject();
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("userlogin");
        if (currentUser.isAuthenticated()) {
            modelView.addObject("path", path);
            modelView.setViewName("index");
        }
        return modelView;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("userlogin");
        return modelView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password,
            HttpServletRequest request) {
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("userlogin");
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String code = (String) session.getAttribute("validateCode");
        String submitCode = WebUtils.getCleanParam(request, "validateCode");
        if (!StringUtils.equals(code, submitCode.toLowerCase())) {
            modelView.addObject("message", "验证码错误！");
        } else {
            UsernamePasswordToken token = new UsernamePasswordToken(username,
                    password);
            String errorMessage = "登录成功！";
            // token.setRememberMe(true);
            try {
                subject.login(token);
                modelView.setViewName("index");
            } catch (UnknownAccountException uae) {
                errorMessage = "用户不存在！";
                modelView.addObject("message", errorMessage);
                uae.printStackTrace();
                logger.error(uae.getMessage(), uae);
            } catch (IncorrectCredentialsException ice) {
                int i = retryLimitHashedCredentialsMatcher.getRety(username);
                errorMessage = "用户名或密码不正确！剩余次数" + (5 - i);
                modelView.addObject("message", errorMessage);
                modelView.addObject("username", username);
                ice.printStackTrace();
                logger.error(ice.getMessage(), ice);
            } catch (LockedAccountException lae) {
                errorMessage = "用户冻结！";
                modelView.addObject("message", errorMessage);
                lae.printStackTrace();
                logger.error(lae.getMessage(), lae);
            } catch (DisabledAccountException dispa) {
                errorMessage = "用户禁用！";
                modelView.addObject("message", errorMessage);
                dispa.printStackTrace();
                logger.error(dispa.getMessage(), dispa);
            } catch (ExcessiveAttemptsException e) {
                errorMessage = "密码输入错误次数超过5次，账号锁定10分钟！";
                modelView.addObject("message", errorMessage);
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            } catch (AuthenticationException e) {
                errorMessage = "登录失败！";
                modelView.addObject("message", errorMessage);
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
            if (subject.isAuthenticated()) {
                User user = (User) subject.getPrincipal();
                session.setAttribute("currentUser", user);
                modelView.addObject("currentUser", user);
                /*
                 * gaohj 读取最近任务
                 */
                try {
                    logger.debug("开始获取最近运行中的任务..........");
                    List<TaskInfo> taskInfos = taskInfoService
                            .findAllRecentTask();
                    JsonConfig config = new JsonConfig();
                    config.registerJsonValueProcessor(Date.class,
                            new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
                    modelView.addObject("taskInfos",
                            JSONArray.fromObject(taskInfos, config));
                    logger.debug("最近运行中的任务有：" + taskInfos.size() + ","
                            + taskInfos);
                } catch (TaskinfoException e1) {
                    logger.error("读取最近任务失败", e1);
                }
            } else {
                token.clear();
            }
        }
        return modelView;
    }

    /**
     * 任务定时
     */
    @RequestMapping(value = "/getTask", method = RequestMethod.POST)
    public String getTask(ModelMap model) {
        try {
            logger.debug("开始获取最近运行中的任务..........");
            List<TaskInfo> taskInfos = taskInfoService.findAllRecentTask();
            logger.debug("最近运行中的任务有：" + taskInfos.size());
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            model.put("taskInfos", JSONArray.fromObject(taskInfos, config));
            logger.debug("最近运行中的任务有：" + taskInfos.size() + "," + taskInfos);
        } catch (TaskinfoException e1) {
            logger.error("读取最近任务失败", e1);
        }
        return "taskRecent";
    }

    /**
     * 用户登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        SecurityUtils.getSubject().logout();
        return "userlogin";
    }

}
