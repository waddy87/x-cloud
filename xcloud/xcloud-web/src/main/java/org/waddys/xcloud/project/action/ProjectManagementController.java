/**
 * 
 */
package org.waddys.xcloud.project.action;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.waddys.xcloud.common.DateJsonValueProcessor;
import org.waddys.xcloud.log.api.Log;
import org.waddys.xcloud.log.impl.LogObject;
import org.waddys.xcloud.log.impl.LogUitls;
import org.waddys.xcloud.log.type.LogConst;
import org.waddys.xcloud.org.bo.Organization;
import org.waddys.xcloud.project.api.ProjectApi;
import org.waddys.xcloud.project.bo.Project;
import org.waddys.xcloud.user.bo.Role;
import org.waddys.xcloud.user.bo.User;
import org.waddys.xcloud.user.service.service.UserService;
import org.waddys.xcloud.util.JsonUtil;
import org.waddys.xcloud.vm.api.VmApi;
import org.waddys.xcloud.vm.bo.VmHost;



/**
 * @author James
 * 
 */
@Controller
@RequestMapping(value = "/action/project")
public class ProjectManagementController  {
	private static final Logger logger = LoggerFactory
			.getLogger(ProjectManagementController.class);

	@Autowired
	public ProjectApi pa;
	@Autowired
	public VmApi vc;
	@Autowired
	public UserService us;

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/projectIndex", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView projectIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		ModelAndView mav=new ModelAndView();
		List<Project> proList=null;
		HttpSession hs=request.getSession();
		Project pr=new Project();
		String role="";
		pr.setStatus("A");
		try {
			User user=(User)hs.getAttribute("currentUser");	
			List<Role> roles=user.getRoles();
			for(Role r:roles){
				if(r.getRoleCode().equals("org_manager")){
					String orgId=user.getOrgId();
					pr.setOrgId(orgId);
				}
				else if(r.getRoleCode().equals("org_user")){
					String orgId=user.getOrgId();
					pr.setOrgId(orgId);
					 proList=user.getProjects();
					 role="org_user";
				}
			}
			Page<Project> list=pa.list(pr, 1, 8);

			/*String content=JsonUtil.toJson(list);    */  
			/*jo=JSONObject.fromObject(content);*/
	        JsonConfig config = new JsonConfig();
	        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
	        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
	        if(role.equals("org_user")){
	        	mav.addObject("proList",JSONArray.fromObject(proList,config));
	        }
	        else{
	        	mav.addObject("proList",JSONArray.fromObject(list.getContent(),config));
	        }		
			mav.setViewName("projectManagement/index");
			mav.addObject("orgId",user.getOrgId());
		} catch (Exception e) {
			String a=e.getMessage();
		}
		return mav;
	}
	@RequestMapping(value = "/list", produces = {
	"application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public String queryProjectList(
			@RequestParam(value = "page", required = true) Integer pageSize,
			@RequestParam(value = "rows", required = true) Integer pageNum, 
			@RequestParam(value = "name", required = true) String  name,
			HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		logger.info("传入的参数:分页大小"+pageSize+"页码号为"+pageNum+"查询名称为"+name);
		JSONObject jo = new JSONObject();
		try{
			/*			jo=JSONObject.fromObject(pa.list(name,"","", pageSize, pageNum));
			JSONObject jo2=JSONObject.fromObject(jo.get("rows"));
			jo.put("rows", jo2.get("content"));*/

		}
		catch(Exception e){}


		return jo.toString();
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toAddProject", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView toAddProject(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "orgId", required = true) String orgId
			) {
		ModelAndView mav=new ModelAndView();
		try {
			mav.setViewName("projectManagement/add");
			mav.addObject("orgId",orgId);
		} catch (Exception e) {

		}
		return mav;
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/goToDetail", method = RequestMethod.GET)
	public ModelAndView goToDetail(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "_", required = true) String aa,
			ModelMap model) {
		ModelAndView mav=new ModelAndView();
		try {
			mav.setViewName("orgManagement/detail");

		} catch (Exception e) {

		}
		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toAddVm", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView toAddVm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "orgId", required = true) String orgId,
			@RequestParam(value = "proId", required = true) String proId
			) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("projectManagement/addVm");
		try {
			mav.addObject("orgId",orgId);
			mav.addObject("proId",proId);
		} catch (Exception e) {

		}
		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toAddUser", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView toAddUser(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "orgId", required = true) String orgId,
			@RequestParam(value = "proId", required = true) String proId
			) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("projectManagement/addUser");
		try {
			mav.addObject("orgId",orgId);
			mav.addObject("proId",proId);
		} catch (Exception e) {

		}
		return mav;
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/queryAddingVmlist", produces = {
	"application/json;charset=UTF-8" },  method = RequestMethod.GET)
	public @ResponseBody
	String queryAddingVmlist(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "orgId", required = true) String  orgId
			) {
		JSONObject jo1 = new JSONObject();
		JSONObject jo2=null;
		JSONObject jo3=new JSONObject();
		String result = null;
		JSONArray ja=new JSONArray();
		VmHost vh=new VmHost();
		vh.setIsAssigned(false);
		vh.setOrgId(orgId);
		vh.setStatus("A");
		try {
			Page<VmHost> list=vc.list(vh, 1, 40000);
			String content=JsonUtil.toJson(list);      
			JSONObject jo=JSONObject.fromObject(content);
			jo1.put("total", jo.get("totalElements"));
			jo1.put("rows", jo.get("content"));
			for(Object o:JSONArray.fromObject(jo.get("content"))){
				jo2= JSONObject.fromObject(o);
				jo3.put("value", jo2.get("id"));
				jo3.put("label", jo2.get("name"));
				ja.add(jo3.toString());
			}
			result=ja.toString();

		} catch (Exception e) {

		}
		return result;
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/queryAddingUserlist", produces = {
	"application/json;charset=UTF-8" },  method = RequestMethod.GET)
	public @ResponseBody
	String queryAddingUserlist(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "orgId", required = true) String  orgId,
			@RequestParam(value = "proId", required = true) String  proId
			) {
		JSONObject jo2=null;
		JSONObject jo3=new JSONObject();
		String result = null;
		VmHost vh=new VmHost();
		JSONArray ja=new JSONArray();
		vh.setIsAssigned(false);
		vh.setOrgId(orgId);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		try {
			List<User> list=us.findOrgUserByOrgId(orgId);    

			for(User o:list){
				jo3.put("value", o.getId());
				jo3.put("label", o.getRealname());
				ja.add(jo3.toString());
			}
			result=ja.toString();

		} catch (Exception e) {

		}
		return result;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/queryVmlist", produces = {
	"application/json;charset=UTF-8" },  method = RequestMethod.GET)
	public @ResponseBody
	String queryVmlistByProject(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "page", required = true) Integer pageSize,
			@RequestParam(value = "rows", required = true) Integer pageNum, 
			@RequestParam(value = "name", required = false) String  name,
			@RequestParam(value = "orgId", required = false) String  orgId,
			@RequestParam(value = "proId", required = false) String  proId
			) {
		JSONObject jo1 = new JSONObject();
		String result = null;
		JSONArray ja=new JSONArray();
		VmHost vh=new VmHost();
		vh.setIsAssigned(false);
		vh.setOrgId(orgId);
		try {
			Page<VmHost> list=pa.listVM(proId, pageSize, pageNum);
			String content=JsonUtil.toJson(list);      
			JSONObject jo=JSONObject.fromObject(content);
			jo1.put("total", jo.get("totalElements"));
			jo1.put("rows", jo.get("content"));
			result=jo1.toString();
		} catch (Exception e) {

		}
		return result;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/queryUserlist", produces = {
	"application/json;charset=UTF-8" },  method = RequestMethod.GET)
	public @ResponseBody
	String queryUserlistByProject(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "page", required = true) Integer pageSize,
			@RequestParam(value = "rows", required = true) Integer pageNum, 
			@RequestParam(value = "name", required = false) String  name,
			@RequestParam(value = "orgId", required = false) String  orgId,
			@RequestParam(value = "proId", required = false) String  proId
			) {
		JSONObject jo1 = new JSONObject();
		String result = null;
		JSONArray ja=new JSONArray();
		try {
			Set<User> list=pa.listUser(proId);     
			ja=JSONArray.fromObject(list);
			jo1.put("total", list.size());
			jo1.put("rows", ja.toString());
			result=ja.toString();
		} catch (Exception e) {

		}
		return result;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	@Log(message="创建项目{0}{1}",resourceType="项目",operationType="创建",moduleType="项目")
	public @ResponseBody String addProject(@RequestBody Project pro,
			HttpServletRequest request
			) {
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=pro.getName();
		try {
			a[1]="成功";				
			pa.add(pro);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("创建项目成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/addVm", method = RequestMethod.POST,produces = {
	"application/json;charset=UTF-8" })
	@Log(message="为项目{0}添加虚拟机{1}",resourceType="项目",operationType=LogConst.OPERATIONTYPE_DISTRIBUTION,moduleType="项目管理")
	public @ResponseBody String addVm(@RequestBody Map<String,Object> params 
			) {
		String proId=params.get("proId").toString();
		String vmId=params.get("vmId").toString();
		String proName=params.get("proName").toString();
		String vmIds []=vmId.split(";");
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=proName;
		try {
			a[1]="成功";	
			for(String vmid:vmIds){
				pa.addVM(proId, vmid);
			}
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("添加虚拟机资源成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/addUser", method = RequestMethod.POST,produces = {
	"application/json;charset=UTF-8" })
	@Log(message="为项目{0}添加人员{1}",resourceType="项目",operationType=LogConst.OPERATIONTYPE_DISTRIBUTION,moduleType="项目管理")
	public @ResponseBody String addUser(@RequestBody Map<String,Object> params 
			) {
		String proId=params.get("proId").toString();
		String userId=params.get("userId").toString();
		String proName=params.get("proName").toString();
		String userIds []=userId.split(";");
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=proName;
		try {
			a[1]="成功";	
			for(String userid:userIds){
				pa.addUser(proId, userid);
			}
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("添加人员成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	/*@SuppressWarnings("static-access")
	@RequestMapping(value = "/toRemoveVm", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView toRemoveVm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "orgId", required = true) String orgId,
			@RequestParam(value = "proId", required = true) String proId
			) {
		JSONObject jsonObj = new JSONObject();
		String result = null;
		 ModelAndView mav=new ModelAndView();
		 mav.setViewName("projectManagement/removeVm");
		try {
         mav.addObject("orgId",orgId);
         mav.addObject("proId",proId);
		} catch (Exception e) {

		}
		return mav;
	}*/

	/*@SuppressWarnings("static-access")
	@RequestMapping(value = "/queryRemovingVmlist", produces = {
	"application/json;charset=UTF-8" },  method = RequestMethod.GET)
	public @ResponseBody
	String queryRemovingingVmlist(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestParam(value = "orgId", required = true) String  orgId,
			@RequestParam(value = "proId", required = true) String  proId
			) {
		JSONObject jo1 = new JSONObject();
		JSONObject jo2=null;
		JSONObject jo3=new JSONObject();
		String result = null;
		JSONArray ja=new JSONArray();
		try {
			Page<VmHost> list=vc.list("",proId,orgId, 1, 40000);
			String content=JsonUtil.toJson(list);      
			JSONObject jo=JSONObject.fromObject(content);
			jo1.put("total", jo.get("totalElements"));
			jo1.put("rows", jo.get("content"));
			for(Object o:JSONArray.fromObject(jo.get("content"))){
				jo2= JSONObject.fromObject(o);
				jo3.put("value", jo2.get("id"));
				jo3.put("label", jo2.get("name"));
				ja.add(jo3.toString());
			}
			result=ja.toString();

		} catch (Exception e) {

		}
		return result;
	}*/

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/removeVm", method = RequestMethod.POST,produces = {
	"application/json;charset=UTF-8" })
	@Log(message="为项目{0}移除虚拟机{1}",resourceType="项目",operationType=LogConst.OPERATIONTYPE_DISTRIBUTION,moduleType="项目管理")
	public @ResponseBody String removeVm(@RequestBody Map<String,Object> params 
			) {

		String proId=params.get("proId").toString();
		String vmId=params.get("vmId").toString();
		String proName=params.get("proName").toString();
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=proName;
		try {
			a[1]="成功";	
			pa.removeVM(proId, vmId);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("移除虚拟机资源成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/removeUser", method = RequestMethod.POST,produces = {
	"application/json;charset=UTF-8" })
	@Log(message="为项目{0}移除用户{1}",resourceType="项目",operationType=LogConst.OPERATIONTYPE_DISTRIBUTION,moduleType="项目管理")
	public @ResponseBody String removeUser(@RequestBody Map<String,Object> params 
			) {
		String proId=params.get("proId").toString();
		String userId=params.get("userId").toString();
		String proName=params.get("proName").toString();
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=proName;
		try {
			a[1]="成功";	
			pa.removeUser(proId, userId);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("移除组织成员成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/deletePro", method = RequestMethod.POST)
	@Log(message="删除项目{0}{1}",resourceType="项目",operationType=LogConst.OPERATIONTYPE_DELETE,moduleType="项目管理")
	public @ResponseBody String deletePro(@RequestBody Project pro) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=pro.getName();
		try {
			a[1]="成功";	
			pa.delete(pro.getId());   
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("删除项目成功");        
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/updatePro", method = RequestMethod.POST)
	@Log(message="更新项目{0}{1}",resourceType="项目",operationType=LogConst.OPERATIONTYPE_UPDATE,moduleType="项目管理")
	public @ResponseBody
	String updatePro(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,
			@RequestBody Project project
			) {
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=project.getName();
		try {
			a[1]="成功";	
			pa.update(project.getId(),project);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("更新项目成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

}
