package com.sugon.cloudview.cloudmanager.user.api.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.org.service.OrganizationService;
import com.sugon.cloudview.cloudmanager.user.api.common.DateJsonValueProcessor;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.RoleEnum;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.usermgmt.service.exception.UserMgmtException;
import com.sugon.cloudview.cloudmanager.usermgmt.service.service.RoleService;
import com.sugon.cloudview.cloudmanager.usermgmt.service.service.UserService;

@Controller
@RequestMapping("/userMgmt")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private SessionDAO sessionDAO;

    private static Logger logger = LoggerFactory
            .getLogger(UserController.class);

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public @ResponseBody String createUser(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "realname", required = false) String realname,
            @RequestParam(value = "telephone", required = false) String telephone,
            @RequestParam(value = "email", required = false) String email) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            User userTmp = userService.findByUsername(username);
            if (null != userTmp) {
                resultObject.put("success", false);
                resultObject.put("message", "用户名重复！");
            } else {
                User userSave = new User();
                userSave.setUsername(username);
                userSave.setCreateDate(new Date());
                userSave.setEmail(email);
                userSave.setLocked(false);
                userSave.setOrgId(user.getOrgId());
                userSave.setPassword(password);
                userSave.setRealname(realname);
                userSave.setTelephone(telephone);
                userService.save(userSave, RoleEnum.ORG_USER.getValue());
            }
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/openUserCreatePage", method = RequestMethod.GET)
    public String openCreateUserPage(ModelMap model) {
        return "user/createUser";
    }

    @RequestMapping(value = "/changeAccountStatus", method = RequestMethod.POST)
    public @ResponseBody String changeAccountStatus(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "locked", required = false) Boolean locked) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            User user = new User();
            user.setId(userId);
            user.setLocked(locked);
            userService.update(user);
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/updateUserPassword", method = RequestMethod.POST)
    public @ResponseBody String updateUserPassword(
            @RequestParam(value = "oldPassword", required = false) String oldPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            userService.changePassword(user.getId(), oldPassword, newPassword);
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/openUpdateUserPasswordPage", method = RequestMethod.GET)
    public String openUpdateUserPasswordPage(ModelMap model) {
        return "user/password";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public @ResponseBody String resetPassword(
            @RequestParam(value = "userId", required = false) String userId) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            userService.resetPassword(userId, "111111");
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public @ResponseBody String updateUser(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "realname", required = false) String realname,
            @RequestParam(value = "telephone", required = false) String telephone,
            @RequestParam(value = "email", required = false) String email,
            ModelMap model) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        try {
            User user = new User();
            user.setId(userId);
            user.setRealname(realname);
            user.setTelephone(telephone);
            user.setEmail(email);
            userService.update(user);
        } catch (Exception e) {
            resultObject.put("success", false);
            resultObject.put("message", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return resultObject + "";
    }

    @RequestMapping(value = "/openUpdateUserPage", method = RequestMethod.GET)
    public String openUpdateUserPage(
            @RequestParam(value = "id", required = false) String id,
            ModelMap model) {
        try {
            User user = userService.findUser(id);
            model.put("user", user);
        } catch (UserMgmtException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "user/updateUser";
    }

    @RequestMapping(value = "/queryUserTable", method = RequestMethod.POST)
    public @ResponseBody String queryUserTable(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "rows", required = false) int rows) {
        JSONObject json = new JSONObject();
        try {
            Subject subject = SecurityUtils.getSubject();
            User current = (User) subject.getPrincipal();
            String orgId = current.getOrgId();
            User userSearch = new User();
            userSearch.setUsername(name);
            if (StringUtils.isNotEmpty(orgId)) {// 组织管理员
                userSearch.setOrgId(orgId);
            }

            JsonConfig config = new JsonConfig();
            config.setIgnoreDefaultExcludes(false);
            config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            config.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            Map<String, Object> map = userService.findUsers(userSearch, page,
                    rows);
            @SuppressWarnings("unchecked")
            List<User> list = (List<User>) map.get("userList");
            Collection<Session> sessions = sessionDAO.getActiveSessions();
            for (User userFor : list) {
                Organization org = organizationService.showById(userFor
                        .getOrgId());
                if (null != org) {
                    userFor.setOrgName(org.getName());
                }
                for (Session session : sessions) {
                    PrincipalCollection p = (PrincipalCollection) session
                            .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                    if (p != null) {
                        if (userFor.getUsername().equals(
                                ((User) p.getPrimaryPrincipal()).getUsername())) {
                            userFor.setIsOnline(true);
                        }
                    }
                }
            }
            json.put("total", map.get("total"));
            json.put("rows", JSONArray.fromObject(list, config));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return json + "";
    }

    @RequestMapping(value = "/userIndex", method = RequestMethod.GET)
    public String userIndex(ModelMap model) {
        // try {
        // userService.updateUserStatusByOrgId("1");
        // } catch (UserMgmtException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // try {
        // User user = new User();
        // user.setUsername("sunht01");
        // user.setEmail("sht@123.com");
        // user.setCreateDate(new Date());
        // user.setLocked(false);
        // user.setOrgId("orgId");
        // user.setOrgName("中科睿光");
        // user.setPassword("admin");
        // user.setRealname("大兵哥");
        // user.setTelephone("1234567");
        // userService.save(user, "1");
        // } catch (Exception e) {
        // logger.error(e.getMessage(), e);
        // e.printStackTrace();
        // }
        return "user/userIndex";
    }

}
