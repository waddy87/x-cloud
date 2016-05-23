/**
 * 
 */
package org.waddys.xcloud.org.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.waddys.xcloud.log.api.Log;
import org.waddys.xcloud.log.impl.LogObject;
import org.waddys.xcloud.log.impl.LogUitls;
import org.waddys.xcloud.log.type.LogConst;
import org.waddys.xcloud.org.api.OrgApi;
import org.waddys.xcloud.org.bo.Organization;
import org.waddys.xcloud.org.constant.OrgStatus;
import org.waddys.xcloud.org.service.OrganizationService;
import org.waddys.xcloud.user.bo.User;
import org.waddys.xcloud.user.service.service.UserService;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.vm.bo.VmHost;

import net.sf.json.JSONObject;

/**
 * @author James
 * 
 */
@Controller
@RequestMapping(value = "/orgs")
public class OrgMagementController  {
	private static final Logger logger = LoggerFactory
			.getLogger(OrgMagementController.class);

	@Autowired
	public OrgApi oc;
	@Autowired
	public OrganizationService occ;
	@Autowired
	public UserService us;

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/orgIndex", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView orgIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		try {

		} catch (Exception e) {

		}
		return new ModelAndView("orgManagement/index");
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/deleteOrg", method = RequestMethod.POST)
	@Log(message="删除组织{0}{1}",resourceType="组织",operationType=LogConst.OPERATIONTYPE_DELETE,moduleType="组织管理")
	public @ResponseBody
	String deletOrg(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "orgId", required = true) String orgId,
			@RequestParam(value = "orgName", required = false) String orgName
			) {
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=orgName;
		try {
			a[1]="成功";
			oc.delete(orgId);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("删除组织成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/addOrg", method = RequestMethod.POST)
	@Log(message="创建组织{0}{1}",resourceType="组织",operationType=LogConst.OPERATIONTYPE_ADD,moduleType="组织管理")
	public @ResponseBody String addOrg(@RequestBody JSONObject jo,
			HttpServletRequest request
			) {
		//OrgController oc=new OrgController();
		/*	String result1=vc.get(id);*/
		logger.info("传入的参数为"+jo);
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		try {
			a[1]="成功";
			Organization organization = (Organization) JSONObject.toBean(jo.getJSONObject("organization"),Organization.class);
			a[0]=organization.getName();
			User owner = (User) JSONObject.toBean(jo.getJSONObject("user"), User.class);
			oc.add(organization, owner);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("创建组织成功");
			/*JSONObject jo=JSONObject.fromObject(result1);
			mav.addObject("orgDetail", jo);*/
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
		/* return "protected/resourcepool/pool_add";*/

	}


	@SuppressWarnings("static-access")
	@RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
	@Log(message="更新组织{0}{1}",resourceType="组织",operationType="更新",moduleType="组织")
	public @ResponseBody
	String updateOrg(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestBody Organization organization
			) {
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=organization.getName();
		try {
			a[1]="成功";
			oc.update(organization.getId(),organization);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("更新组织成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}


	}
	@RequestMapping(value = "/list", produces = {
	"application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public String queryOrgList(
			@RequestParam(value = "page", required = true) Integer pageSize,
			@RequestParam(value = "rows", required = true) Integer pageNum, 
			@RequestParam(value = "name", required = true) String  name,
			HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		JSONObject jo =null;
		JSONObject jo1=new JSONObject();
		String result=null;
		Organization og=new Organization();
		og.setName("%"+name+"%");
		og.setStatus(OrgStatus.NORMAL);
		try{
			/*jo=JSONObject.fromObject(oc.list(name, pageSize, pageNum));*/
			Page<Organization> list=oc.list(og, pageSize, pageNum);
/*			PageRequest pageRequest=new PageRequest(pageNum,pageSize);
			Organization og=new Organization();
			og.setStatus(OrgStatus.NORMAL);
			list=occ.pageAll(og, pageRequest);*/
			String content=JsonUtil.toJson(list);      
			jo=JSONObject.fromObject(content);
			jo1.put("total", jo.get("totalElements"));
			jo1.put("rows", jo.get("content"));
			result=jo1.toString();
		}
		catch(Exception e){}
		return result;
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toAddOrg", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView toAddOrg(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		try {

		} catch (Exception e) {

		}
		return new ModelAndView("orgManagement/add");
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/goToDetail", method = RequestMethod.GET)
	public ModelAndView queryOrgList(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "_", required = false) String aa,
			ModelMap model) {
		ModelAndView mav=new ModelAndView();
		//OrgController oc=new OrgController();
		try {
			User orguser=us.findOrgManagerByOrgId(id);
			JSONObject jo2=new JSONObject();
			jo2.put("email", orguser.getEmail());
			jo2.put("telephone", orguser.getTelephone());
			jo2.put("realname", orguser.getRealname());
			String result1 = JsonUtil.toJson(oc.get(id));
			mav.setViewName("orgManagement/detail");
			JSONObject jo = JSONObject.fromObject(result1);
			mav.addObject("orgDetail", jo);
			mav.addObject("orgUser", jo2);
		} catch (Exception e) {
			mav.addObject(JsonUtil.error(e.getMessage(), e));
		}
		/* return "protected/resourcepool/pool_add";*/
		return mav;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/validAccount", method = RequestMethod.POST)
	public @ResponseBody String validAccount(
			@RequestParam(value = "account", required = true) String account
			
			) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/

		try {
           User uss=us.findByUsername(account);           
			if(uss==null){
				return "true";			
			}
			else{
				return "false";
			}
		} catch (Exception e) {

			return JsonUtil.error(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/validOrgName", method = RequestMethod.POST)
	public @ResponseBody String validOrgName(
			@RequestParam(value = "name", required = true) String name
			
			) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/

		try {
			Organization og=new Organization();
			og.setName(name);
			Page<Organization> list=oc.list(og, 1, 8);  
          Long t=list.getTotalElements();
			if(t==0){
				return "true";			
			}
			else{
				return "false";
			}
		} catch (Exception e) {

			return JsonUtil.error(e.getMessage(), e);
		}
	}
}
