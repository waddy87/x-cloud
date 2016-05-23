package org.waddys.xcloud.project.api;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.waddys.xcloud.exception.ApiException;
import org.waddys.xcloud.project.bo.Project;
import org.waddys.xcloud.project.service.ProjectService;
import org.waddys.xcloud.user.bo.User;
import org.waddys.xcloud.user.service.service.UserService;
import org.waddys.xcloud.util.PageUtil;
import org.waddys.xcloud.vm.bo.VmHost;
import org.waddys.xcloud.vm.service.VmService;

import net.sf.json.JSONObject;


@RestController
@RequestMapping(path = "/api/projects", produces = { "application/json;charset=UTF-8" })
public class ProjectApi {
	
    private static final Logger logger = LoggerFactory.getLogger(ProjectApi.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private VmService vmService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public Set<User> listUser(@PathVariable String id) throws ApiException {
        logger.info("接收到客户端请求: {id=" + id + "}");
        Set<User> users = projectService.listUser(id);
        return users;
    }

    @RequestMapping(value = "/{id}/vms", method = RequestMethod.GET)
    public Page<VmHost> listVM(@PathVariable String id, @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer per_page) throws ApiException {
        logger.info("接收到客户端请求: {id=" + id + "}");

        PageRequest pageRequest = PageUtil.buildPageRequest(page, per_page);
        VmHost search = new VmHost("");
        Page<VmHost> result = null;

        try {
            result = vmService.pageByPID(id, search, pageRequest);
        } catch (Exception e) {
            throw new ApiException("解析请求失败！", e.getCause());
        }

        return result;
    }

    @RequestMapping(value = "/{id}/users/{user_id}", method = RequestMethod.POST)
    public void addUser(@PathVariable String id, @PathVariable("user_id") String userId) throws ApiException {
        logger.info("收到项目关联用户请求: {id=" + id + ",userId=" + userId + "}");
        try {
            User user = userService.findUser(userId);
            projectService.addUser(id, user);
        } catch (Exception e) {
            logger.error("项目关联用户失败！", e);
            throw new ApiException("项目关联用户失败！" + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}/users/{user_id}", method = RequestMethod.DELETE)
    public void removeUser(@PathVariable String id, @PathVariable("user_id") String userId) throws ApiException {
        logger.info("收到项目移出用户请求: {id=" + id + ",userId=" + userId + "}");
        try {
            User user = userService.findUser(userId);
            projectService.removeUser(id, user);
        } catch (Exception e) {
            logger.error("项目移出用户失败！", e);
            throw new ApiException("项目移出用户失败！" + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}/vms/{vm_id}", method = RequestMethod.POST)
    public void addVM(@PathVariable String id, @PathVariable("vm_id") String vmId) throws ApiException {
        logger.info("接收到客户端请求: {id=" + id + ",vmId=" + vmId + "}");
        try {
            projectService.addVm(id, vmId);
        } catch (Exception e) {
            logger.error("关联虚机失败！", e);
            throw new ApiException("关联虚机失败！", e);
        }
    }

    @RequestMapping(value = "/{id}/vms/{vm_id}", method = RequestMethod.DELETE)
    public void removeVM(@PathVariable String id, @PathVariable("vm_id") String vmId) throws ApiException {
        logger.info("接收到客户端请求: {id=" + id + ",vmId=" + vmId + "}");
        try {
            projectService.removeVm(id, vmId);
        } catch (Exception e) {
            logger.error("移除虚机失败！", e);
            throw new ApiException("移除虚机失败！", e);
        }
    }

    // -------------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Project show(@PathVariable String id) throws ApiException {
        Project result = projectService.show(id);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Project> list(@RequestParam(required = false) Project search,
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer per_page)
                    throws ApiException {
        logger.info("收到查询项目请求: {search=" + search + ",page=" + page + ",per_page=" + per_page + "}");
        PageRequest pageRequest = PageUtil.buildPageRequest(page, per_page);
        Page<Project> result = null;
        try {
            result = projectService.pageByBO(search, pageRequest);
        } catch (Exception e) {
            logger.error("查询数据库出错！", e);
            throw new ApiException("查询数据库出错！", e);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Project add(@RequestBody Project project) throws ApiException {
        logger.info("收到创建项目请求: " + project);
        Project result = null;
        try {
            project.setStatus("A");
            project.setCreateTime(new Date());
            result = projectService.add(project);
        } catch (Exception e) {
            logger.error("创建项目失败！", e);
            throw new ApiException("创建项目失败！", e);
        }
        logger.info("完成创建项目请求: " + result);
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) throws ApiException {
        logger.info("收到删除项目请求: " + id);
        try {
            projectService.deleteById(id);
        } catch (Exception e) {
            logger.error("删除项目失败！" + e.getMessage(), e);
            // jsonString = createResponse(false, "删除项目失败！", e);
            throw new ApiException("删除项目失败！" + e.getMessage(), e);
        }
        logger.info("完成删除项目请求: " + id);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public void update(@PathVariable String id, @RequestBody Project project) throws ApiException {
        logger.info("收到更新项目请求：" + project);
        Project targetProj = null;
        try {
            targetProj = projectService.show(id);
        } catch (Exception e) {
            logger.error("查询目标项目出错！", e);
            // return createResponse(false, "更新项目失败！", e);
            throw new ApiException("查询目标项目出错！", e);
        }
        if (targetProj == null)
            throw new ApiException("目标项目不存在！");
        try {
            if (project.getName() != null) {
                targetProj.setName(project.getName());
            }
            if (project.getDescription() != null) {
                targetProj.setDescription(project.getDescription());
            }
            projectService.update(targetProj);
        } catch (Exception e) {
            logger.error("更新项目出错！", e);
            // return createResponse(false, "更新项目失败！", e);
            throw new ApiException("更新项目出错！", e);
        }
        logger.info("完成更新项目请求: " + id);
	}

    private String createResponse(boolean flag, String message) {
        return "{\"flag\":" + flag + ",\"message\":\"" + message + "\"}";
    }

    private String createResponse(boolean flag, String message, Exception e) {
        JSONObject resp = new JSONObject();
        resp.put("flag", flag);
        resp.put("message", message);
        resp.put("details", e.getLocalizedMessage());
        return resp.toString();
    }

}
