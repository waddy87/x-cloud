package com.sugon.cloudview.cloudmanager.org.api;

import javax.transaction.Transactional;

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

import com.sugon.cloudview.cloudmanager.exception.ApiException;
import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.org.service.OrganizationService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vnet.NetPoolService;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.RoleEnum;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.usermgmt.service.service.UserService;
import com.sugon.cloudview.cloudmanager.util.PageUtil;

import net.sf.json.JSONObject;


@RestController
@RequestMapping(path = "/api/organizations", produces = { "application/json;charset=UTF-8" })
public class OrgApi {
	
    private static final Logger logger = LoggerFactory.getLogger(OrgApi.class);

    @Autowired
	private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private NetPoolService netPoolService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Organization get(@PathVariable String id) throws ApiException {
        logger.info("完成查看组织详情请求：" + id);
        Organization result = null;
        try {
            result = organizationService.showById(id);
        } catch (Exception e) {
            logger.error("查询数据库出错！", e);
            throw new ApiException("查询数据库出错！", e);
        }
        logger.info("完成查看组织详情请求：" + id);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Organization> list(
@RequestParam(required = false) Organization search,
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer per_page)
                    throws ApiException {
        logger.info("收到查询组织请求: {search=" + search + ",page=" + page + ",per_page=" + per_page + "}");
        PageRequest pageRequest = PageUtil.buildPageRequest(page, per_page);
        Page<Organization> result = null;
        try {
            result = organizationService.pageAll(search, pageRequest);
        } catch (Exception e) {
            logger.error("查询数据库出错！", e);
            // return createResponse(false, "查询数据库出错！", e);
            throw new ApiException("查询数据库出错！", e);
        }
        logger.info("完成查询组织请求");
        return result;
	}

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public Organization add(Organization organization, User orgAdmin) throws ApiException {
        logger.info("收到创建组织请求：" + organization + ",orgAdmin=" + orgAdmin);
        Organization result = null;
        // 添加组织记录
        try {
            result = organizationService.add(organization);
        } catch (Exception e) {
            logger.error("添加组织失败！", e);
            throw new ApiException("添加组织失败！" + e.getMessage(), e);
        }
        // 添加组织管理员记录
        try {
            orgAdmin.setOrgId(result.getId());
            userService.save(orgAdmin, RoleEnum.ORG_MANAGER.getValue());
        } catch (Exception e) {
            logger.error("添加组织管理员失败！", e);
            // return createResponse(false, "创建组织失败！", e);
            throw new ApiException("添加组织管理员失败！" + e.getMessage(), e);
        }
        logger.info("完成创建组织请求：" + result);
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) throws ApiException {
        logger.info("收到删除组织请求：" + id);
        // 1.注销组织
        try {
            organizationService.deleteById(id);
        } catch (Exception e) {
            logger.error("删除组织失败！" + e.getMessage(), e);
            throw new ApiException("删除组织失败！" + e.getMessage(), e);
        }
        // 2.级联注销该组织下所有成员
        try {
            logger.debug("级联注销该组织下所有成员");
            userService.updateUserStatusByOrgId(id);
        } catch (Exception e) {
            logger.error("注销组织成员失败！", e);
            throw new ApiException("删除组织失败：注销组织成员失败！", e);
        }
        // 3.级联注销该组织下的网络
        try {
            logger.debug("级联注销该组织下的网络");
            netPoolService.recycleNetpoolByorgId(id);
        } catch (Exception e) {
            logger.error("同步注销网络失败！", e);
            throw new ApiException("删除组织失败：同步注销网络失败！", e);
        }
        logger.info("完成删除组织请求：" + id);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public void update(@PathVariable String id, @RequestBody Organization organization) throws ApiException {
        logger.info("收到更新组织请求：" + organization);
        Organization targetOrg = null;
        try {
            targetOrg = organizationService.showById(id);
        } catch (Exception e1) {
            logger.error("查询目标组织出错！", e1);
            throw new ApiException("查询目标组织出错！", e1);
        }
        if (targetOrg == null) {
            logger.error("目标组织不存在！");
            throw new ApiException("目标组织不存在！");
        }
        try {
            if (organization.getName() != null) {
                targetOrg.setName(organization.getName());
            }
            if (organization.getAddress() != null) {
                targetOrg.setAddress(organization.getAddress());
            }
            if (organization.getRemarks() != null) {
                targetOrg.setRemarks(organization.getRemarks());
            }
            organizationService.update(targetOrg);
        } catch (Exception e) {
            logger.error("更新组织出错！", e);
            throw new ApiException("更新组织失败！");
        }
        logger.info("完成更新组织请求：" + id);
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
