/**
 * 
 */
package com.sugon.cloudview.cloudmanager.vm.action;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.RollbackException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sugon.cloudview.cloudmanager.common.DateJsonValueProcessor;
import com.sugon.cloudview.cloudmanager.common.base.utils.CIDRUtils;
import com.sugon.cloudview.cloudmanager.log.api.Log;
import com.sugon.cloudview.cloudmanager.log.impl.LogObject;
import com.sugon.cloudview.cloudmanager.log.impl.LogUitls;
import com.sugon.cloudview.cloudmanager.log.type.LogConst;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.VMBo;
import com.sugon.cloudview.cloudmanager.monitor.service.service.VMService;
import com.sugon.cloudview.cloudmanager.org.api.OrgApi;
import com.sugon.cloudview.cloudmanager.org.bo.Organization;
import com.sugon.cloudview.cloudmanager.org.constant.OrgStatus;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProvideVDCStoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProviderVDC;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vnet.NetPool;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ProviderVDCService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.StoragePoolService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vnet.NetPoolService;
import com.sugon.cloudview.cloudmanager.taskMgmt.service.bo.TaskInfo;
import com.sugon.cloudview.cloudmanager.templet.service.VMTempletService;
import com.sugon.cloudview.cloudmanager.templet.service.entity.VMTempletE;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.Role;
import com.sugon.cloudview.cloudmanager.usermgmt.service.bo.User;
import com.sugon.cloudview.cloudmanager.usermgmt.service.service.UserService;
import com.sugon.cloudview.cloudmanager.util.JsonUtil;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsole.QueryVMConsoleAnswer;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsole.QueryVMConsoleCmd;
import com.sugon.cloudview.cloudmanager.vijava.vm.QueryVMConsoleTask;
import com.sugon.cloudview.cloudmanager.vm.api.VmApi;
import com.sugon.cloudview.cloudmanager.vm.bo.VmTask;
import com.sugon.cloudview.cloudmanager.vm.bo.VmConfig;
import com.sugon.cloudview.cloudmanager.vm.bo.VmDisk;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.bo.VmNet;
import com.sugon.cloudview.cloudmanager.vm.constant.ActionType;
import com.sugon.cloudview.cloudmanager.vm.constant.RunStatus;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;


/**
 * @author James
 * 
 */
@Controller
@RequestMapping(value = "/action/vm")
public class VmManagementController  {
	private static final Logger logger = LoggerFactory
			.getLogger(VmManagementController.class);

	@Autowired
	public VmApi vc;
	@Autowired
	private VMService vmService;
	@Autowired
	public VMTempletService vts;
	public VMTempletService getVts() {
		return vts;
	}
	public void setVts(VMTempletService vts) {
		this.vts = vts;
	}
	@Autowired
	public ProviderVDCService pvc;
	@Autowired
	public StoragePoolService sps;
	@Autowired
	public NetPoolService nps;
	@Autowired
	public OrgApi oc;
	@Autowired
	public UserService us;

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/vmIndex", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView vmIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		JSONObject jsonObj = new JSONObject();
		String result = null;
		try {

		} catch (Exception e) {

		}
		return new ModelAndView("vmManagement/index");
	}






	@RequestMapping(value = "/list", produces = {
	"application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public String queryVmList(
			@RequestParam(value = "page", required = true) Integer pageSize,
			@RequestParam(value = "rows", required = true) Integer pageNum, 
			@RequestParam(value = "name", required = false) String  name,
			@RequestParam(value = "orgId", required = false) String  orgId,
			@RequestParam(value = "proId", required = false) String  proId,
			HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		VmHost vm=new VmHost();
		vm.setName(name);
		JSONObject jo =null;
		JSONObject jo1=new JSONObject();
		String result=null;
		VmHost vh=new VmHost();
		vh.setName("%"+name+"%");
		vh.setStatus("A");
		HttpSession hs=request.getSession();
		Page<VmHost> list=null;
		try{
			logger.info("查询接口");
			User user=(User)hs.getAttribute("currentUser");	
			List<Role> roles=user.getRoles();
			for(Role r:roles){
				if(r.getRoleCode().equals("org_manager")){
					orgId=user.getOrgId();
					vh.setOrgId(orgId);
				}
				else if(r.getRoleCode().equals("org_user")){
					vh.setOwnerId(user.getId());
				}
			}

			/*Page<VmHost> list=vc.list(name,proId,orgId, pageSize, pageNum);*/

			list=vc.list(vh, pageSize, pageNum);


			String content=JsonUtil.toJson(list);      
			jo=JSONObject.fromObject(content);
			jo1.put("total", jo.get("totalElements"));
			jo1.put("rows", jo.get("content"));
			result=jo1.toString();
		}
		catch(Exception e){}
		return result;
	}

	@RequestMapping(value = "/netcardlist", produces = {
	"application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public String queryVmnetcardList(
			@RequestParam(value = "page", required = true) Integer pageSize,
			@RequestParam(value = "rows", required = true) Integer pageNum, 
			@RequestParam(value = "name", required = false) String  name,
			@RequestParam(value = "vmId", required = true) String  vmId,
			HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		VmHost vm=new VmHost();
		vm.setName(name);
		JSONObject jo =null;
		JSONObject jo1=new JSONObject();
		String result=null;
		try{
			logger.info("查询接口");
			Page<VmNet> list= vc.listNet(vmId); 
			String content=JsonUtil.toJson(list);      
			jo=JSONObject.fromObject(content);
			jo1.put("total", jo.get("totalElements"));
			jo1.put("rows", jo.get("content"));
			result=jo1.toString();
		}
		catch(Exception e){
			String a=e.getMessage();			
		}
		return result;
	}

	@RequestMapping(value = "/orgUserList", produces = {
	"application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public String queryUserList(
			@RequestParam(value = "page", required = true) Integer pageSize,
			@RequestParam(value = "rows", required = true) Integer pageNum, 
			@RequestParam(value = "name", required = false) String  name,
			HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		VmHost vm=new VmHost();
		vm.setName(name);
		JSONObject jo =null;
		JSONObject jo1=new JSONObject();
		String result=null;
		HttpSession hs=request.getSession();
		try{
			User user=(User)hs.getAttribute("currentUser");	
			logger.info("查询接口");
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			List<User> list=us.findOrgUserByOrgId(user.getOrgId());
			JSONArray ja=JSONArray.fromObject(list,config); 
			jo1.put("total", ja.size());
			jo1.put("rows",ja);
			result=jo1.toString();
		}
		catch(Exception e){
			String a=e.getMessage();			
		}
		return result;
	}
	@RequestMapping(value = "/disklist", produces = {
	"application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public String queryVmDiskList(
			@RequestParam(value = "page", required = true) Integer pageSize,
			@RequestParam(value = "rows", required = true) Integer pageNum, 
			@RequestParam(value = "name", required = false) String  name,
			@RequestParam(value = "vmId", required = true) String  vmId,
			HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		VmHost vm=new VmHost();
		vm.setName(name);
		JSONObject jo =null;
		JSONObject jo1=new JSONObject();
		String result=null;
		try{
			logger.info("查询接口");
			Page<VmDisk> list= vc.listDisk(vmId);			 
			String content=JsonUtil.toJson(list);      
			jo=JSONObject.fromObject(content);
			jo1.put("total", jo.get("totalElements"));
			jo1.put("rows", jo.get("content"));
			result=jo1.toString();
		}
		catch(Exception e){
			String a=e.getMessage();			
		}
		return result;
	}

	@RequestMapping(value = "/queryNetsByOrg", produces = {
	"application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public String queryNetsByOrg(
			@RequestParam(value = "orgId", required = false) String  orgId,
			HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		String result=null;
		JSONArray netarr=null;
		try{
			List<NetPool> netPoolList= nps.QueryNetpoolByorgId(orgId);
			if(netPoolList.size()!=0){
				netarr=JSONArray.fromObject(netPoolList);
				result=netarr.toString();
			}
		}
		catch(Exception e){}
		return result;
	}

	@RequestMapping(value = "/queryStorByProVDC", produces = {
	"application/json;charset=UTF-8" }, method = RequestMethod.GET)
	@ResponseBody
	public String queryStorByProVDC(
			@RequestParam(value = "proVDCId", required = false) String  proVDCId,
			HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		String result=null;
		JSONArray netarr=null;
		try{
			List proVDCSpList= pvc.findStoragePools(proVDCId);
			if(proVDCSpList.size()!=0){
				netarr=JSONArray.fromObject(proVDCSpList);
				result=netarr.toString();
			}
		}
		catch(Exception e){}
		return result;
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toAddVm", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView toAddVm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		ModelAndView mav=new ModelAndView();
		VMTempletE vt=new VMTempletE();
		vt.setStatus("1");
		List<NetPool> netPoolList=null;
		JSONArray netarr=null;
		mav.setViewName("vmManagement/add");
		try {
			Organization og=new Organization();
			og.setStatus(OrgStatus.NORMAL);
			/*			Page<Organization> orglist=oc.list(null, 1, 40000);*/
			Page<Organization> orglist=oc.list(og, 1, 40000);
			String content=JsonUtil.toJson(orglist);      
			JSONObject jo=JSONObject.fromObject(content); 
			JSONArray orgArr= JSONArray.fromObject(jo.get("content"));	      
			if(orgArr.size()!=0){
				JSONObject firstOrg=JSONObject.fromObject(orgArr.get(0));
				netPoolList= nps.QueryNetpoolByorgId(firstOrg.get("id").toString());
				netarr=JSONArray.fromObject(netPoolList);
			}
			mav.addObject("orgList", orgArr);
			if(netarr!=null){
				mav.addObject("netPoolList",JSONArray.fromObject(netPoolList));
			}
		}
		catch(Exception e){

		}
		try{
			Page<VMTempletE>  list=vts.findAllVMTemplet(vt, 1, 40000);
			mav.addObject("vmTempList",list.getContent());
		}
		catch(Exception e){
			String a=e.getMessage();
			System.out.println(e);
		}
		try{	
			List<ProviderVDC> proList=pvc.findAll();	
			if(proList.size()!=0){
				List proVDCSpList= pvc.findStoragePools(proList.get(0).getpVDCId());
				mav.addObject("proVDCSpList", JSONArray.fromObject(proVDCSpList));
			}
			mav.addObject("proVDCList",JSONArray.fromObject(proList));
		}
		catch (Exception e) {

		}
		return mav;
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/goToDetail", method = RequestMethod.GET)
	public ModelAndView goToDetail(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "vmId", required = true) String vmId,
			ModelMap model) {
		ModelAndView mav=new ModelAndView();
		VmHost search=new VmHost ();
		search.setId(vmId);
		//OrgController oc=new OrgController();
		/*	String result1=vc.get(id);*/
		try {

			/*Page<VmHost> list=vc.list(search, null, null);*/
			search=vc.show(vmId);
			ProviderVDC PV=pvc.findProviderVDC(search.getVdcId());	
			mav.addObject("provdcName", PV.getName());		
			String a=JsonUtil.toJson(search);
			/*			String content=JsonUt il.toJson(list);      
			JSONObject jo=JSONObject.fromObject(content);
			JSONArray ja=JSONArray.fromObject(jo.get("content"));*/
			JSONObject jo=JSONObject.fromObject(a);
			JSONObject jo2=vmService.getHistory(vmId);			
			mav.setViewName("vmManagement/detail");
			mav.addObject("vmDetail", jo);
			mav.addObject("vmMonitor", jo2);

		} catch (Exception e) {

		}
		/* return "protected/resourcepool/pool_add";*/
		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toAddNetcard", method = RequestMethod.GET)
	public ModelAndView toAddNetcard(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "orgId", required = true) String orgId,
			@RequestParam(value = "vmId", required = true) String vmId,
			ModelMap model) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("vmManagement/addNet");
		List<NetPool> netPoolList=null;
		JSONArray netarr=null;
		try {
			netPoolList= nps.QueryNetpoolByorgId(orgId);
			netarr=JSONArray.fromObject(netPoolList);
			Page<VmNet> list= vc.listNet(vmId); 
			List<VmNet> netlist=list.getContent();
			List<Integer> a=new <Integer>ArrayList();
			if(netlist!=null&&netlist.size()!=0){
				
				for(int i=0;i<netPoolList.size();i++){
					for(int j=0;j<netlist.size();j++){
						String id=netlist.get(j).getNet().getNetPoolId();
						if(netPoolList.get(i).getNetPoolId().equals(id)){
							a.add(i);
						}
					}	
				}
				for(int b:a){
					netPoolList.remove(b);
				}
/*				for(NetPool np:netPoolList){
				for(VmNet vn:netlist){
					String id=vn.getNet().getNetPoolId();				
						if(np.getNetPoolId().equals(id)){
							netPoolList.remove(np);
						}
					}
				}*/
			}
			if(netarr!=null){
				mav.addObject("netPoolList",JSONArray.fromObject(netPoolList));
			}    
		} catch (Exception e) {

		}
		/* return "protected/resourcepool/pool_add";*/
		return mav;
	}


	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toVNCPage", method = RequestMethod.GET)
	public ModelAndView toVNCPage(HttpServletRequest request,
			@RequestParam(value = "internalId", required = true) String  internalId
			) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("vmManagement/vnc");
		try {
			QueryVMConsoleCmd queryVMConsoleCmd = new QueryVMConsoleCmd();
			queryVMConsoleCmd.setVmId(internalId);
			QueryVMConsoleTask task = new QueryVMConsoleTask(queryVMConsoleCmd);
			QueryVMConsoleAnswer answer = task.execute();
			System.out.println(answer);
			System.out.println("=================");
			System.out.println(answer.getConsoleURL());
			mav.addObject("url",answer.getConsoleURL());
		} catch (Exception e) {

		}
		/* return "protected/resourcepool/pool_add";*/
		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toConfigVm", method = RequestMethod.GET)
	public ModelAndView toConfigVm(HttpServletRequest request,
			@RequestParam(value = "proVdcId", required = true) String proVdcId,
			@RequestParam(value = "vmId", required = true) String vmId,
			HttpServletResponse response,
			ModelMap model) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("vmManagement/configVm");
		try {   
			VmHost search=vc.show(vmId);
			if(search!=null){
				mav.addObject("cpuNum",search.getvCpuNumer());
				mav.addObject("memory",Long.toString(search.getvMemCapacity()/1024));
				ProviderVDC pd=pvc.findProviderVDC(proVdcId);
				mav.addObject("cpuNumVdc",pd.getvCpuOverplus());				
				mav.addObject("memoryVdc",Long.toString(pd.getMemoryOverplus()/1024));		
			}


		} catch (Exception e) {

		}
		/* return "protected/resourcepool/pool_add";*/
		return mav;
	}


	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toUpdateVmName", method = RequestMethod.GET)
	public ModelAndView toUpdateVmName(HttpServletRequest request,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "vmId", required = true) String vmId,
			HttpServletResponse response,
			ModelMap model) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("vmManagement/updateVm");
		try {   
			VmHost search=vc.show(vmId);
			if(search!=null){
				mav.addObject("vmName",search.getName());				
				mav.addObject("description",search.getRemarks());		
			}		
		} catch (Exception e) {

		}
		/* return "protected/resourcepool/pool_add";*/
		return mav;
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toDistributeVm", method = RequestMethod.GET)
	public ModelAndView toDistributeVm(HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("vmManagement/distributeVm");
		List<NetPool> netPoolList=null;
		JSONArray netarr=null;
		HttpSession hs=request.getSession();
		try {
			User user=(User)hs.getAttribute("currentUser");	
			User usa=new User();
			usa.setOrgId(user.getOrgId());
			Map<String,Object> list=us.findUsers(usa, 1, 10000);
			mav.addObject("userList",list);
		} catch (Exception e) {

		}
		/* return "protected/resourcepool/pool_add";*/
		return mav;
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/toAddDisk", method = RequestMethod.GET)
	public ModelAndView toAddDisk(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "proVdcId", required = true) String proVdcId,

			ModelMap model) {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("vmManagement/addDisk");
		try {
			List<ProvideVDCStoragePool> list=pvc.findStoragePools(proVdcId);
			mav.addObject("storPools",JSONArray.fromObject(list));

		} catch (Exception e) {

		}
		/* return "protected/resourcepool/pool_add";*/
		return mav;
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/addVm", method = RequestMethod.POST)
	@Log(message="创建虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_ADD,moduleType="虚拟机",isTaskType=true)
	public @ResponseBody String addVm(@RequestBody List<VmHost> vmHostlist,
			HttpServletRequest request
			) {
		//OrgController oc=new OrgController();
		/*	String result1=vc.get(id);*/
		logger.info("传入的虚拟机参数为"+vmHostlist);
		JSONArray ja=new JSONArray();
		Object a [] =new Object [2];
		LogObject logobject = new LogObject();
		a[0]=vmHostlist.get(0).getName();
		a[1]="成功";
		for(VmHost vmHost:vmHostlist){
			JSONObject res=new JSONObject();
			TaskInfo tin=new TaskInfo();
			try {
				HttpSession hs=request.getSession();													
				logobject.setObjects(a);	
				User user=(User)hs.getAttribute("currentUser");
				vmHost.setCreaterId(user.getId());
				VmHost result  = vc.create(vmHost);
				VmTask ti=result.getTaskInfo();
				
				logobject.setObjects(a);
				tin.setResourceId(result.getId());
				tin.setResourceName(result.getName());
				if(ti!=null){
					tin.setTaskinfoId(ti.getTaskId());
					tin.setCreateDate(ti.getCreateTime());
					tin.setTaskinfoName(ti.getTaskName());			
				}
//				logobject.getTaskInfoList().add(tin);
				res.put("flag", true);
				res.put("message", "创建虚拟机"+vmHost.getName()+"成功");
				ja.add(res);
			} 
			catch (Exception e) {
				a[1]="失败";
				res.put("flag", false);
				res.put("message", e.getMessage());
				ja.add(res);
				logobject.setObjects(a);
//				logobject.getTaskInfoList().add(tin);
/*				logobject.setOperationResult("1");*/
				LogUitls.putArgs(logobject);
			}
		}
		LogUitls.putArgs(logobject);
		return ja.toString();
		/* return "protected/resourcepool/pool_add";*/

	}


	@SuppressWarnings("static-access")
	@RequestMapping(value = "/addOneVm", method = RequestMethod.POST, produces = {
	"application/json;charset=UTF-8" })
	@Log(message="创建虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_ADD,moduleType="虚拟机",isTaskType=true)
	public @ResponseBody String addOneVm(@RequestBody JSONObject vmHost,
			HttpServletRequest request
			) {
		//OrgController oc=new OrgController();
		/*	String result1=vc.get(id);*/
		logger.info("传入的虚拟机参数为"+vmHost);
		JSONObject jo=new JSONObject();
		HttpSession hs=request.getSession();
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		/*a[0]=vmHost.getName();*/
		VmHost vv=(VmHost)vmHost.get("vmHost");
		try {
			a[1]="成功";
			logobject.setObjects(a);	
			User user=(User)hs.getAttribute("currentUser");
			/*	vmHost.setCreaterId(user.getId());*/
			VmHost result  = vc.create(vv);
			VmTask ti=result.getTaskInfo();
			logobject.setObjects(a);
			logobject.setResourceId(result.getId());
			logobject.setResourceName(result.getName());
			if(ti!=null){
				logobject.setTaskId(ti.getTaskId());
				logobject.setTaskCreateTime(ti.getCreateTime());
				logobject.setTaskInfoName(ti.getTaskName());	
			}
			LogUitls.putArgs(logobject);
			return JsonUtil.success("创建虚拟机成功");
			/*			JSONObject jo=JSONObject.fromObject(result1);
			mav.addObject("orgDetail", jo);*/
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			logobject.setOperationResult("1");
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
		/* return "protected/resourcepool/pool_add";*/

	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/startVm", method = RequestMethod.POST)
	@Log(message="启动虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_START,moduleType="虚拟机",isTaskType=true)
	public @ResponseBody String startVm(@RequestBody VmHost vmHost) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vmHost.getName();
		try {
			a[1]="成功";	
			VmHost result=vc.action(vmHost.getId(),ActionType.START,null);
			logobject.setObjects(a);
			VmTask ti=result.getTaskInfo();
			logobject.setObjects(a);
			logobject.setResourceId(result.getId());
			logobject.setResourceName(result.getName());
			logobject.setResourceType("虚拟机");
			if(ti!=null){
				logobject.setTaskInfoName(ti.getTaskName());
				logobject.setTaskId(ti.getTaskId());
				logobject.setTaskCreateTime(ti.getCreateTime());
			}
			LogUitls.putArgs(logobject);
			return JsonUtil.success("开始启动虚拟机");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setOperationResult("1");
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}

	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/stopVm", method = RequestMethod.POST)
	@Log(message="停止虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_STOP,moduleType="虚拟机",isTaskType=true)
	public @ResponseBody String stopVm(@RequestBody VmHost vmHost) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vmHost.getName();
		try {
			a[1]="成功";	
			VmHost result=vc.action(vmHost.getId(),ActionType.STOP,null);
			logobject.setObjects(a);
			VmTask ti=result.getTaskInfo();
			logobject.setObjects(a);
			logobject.setResourceId(result.getId());
			logobject.setTaskCreateTime(ti.getCreateTime());
			if(ti!=null){
				logobject.setResourceName(result.getName());
				logobject.setTaskId(ti.getTaskId());
				logobject.setTaskInfoName(ti.getTaskName());	
			}
			LogUitls.putArgs(logobject);
			return JsonUtil.success("开始停止虚拟机");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setOperationResult("1");
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/refreshVm", method = RequestMethod.POST)
	@Log(message="刷新虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_STOP,moduleType="虚拟机")
	public @ResponseBody String refreshVm(@RequestBody VmHost vmHost) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vmHost.getName();
		try {
			a[1]="成功";	
			VmHost vh=vc.action(vmHost.getId(),ActionType.REFRESH,null);
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JSONObject.fromObject(vh).toString();
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/deleteVm", method = RequestMethod.POST)
	@Log(message="删除虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_DELETE,moduleType="虚拟机",isTaskType=true)
	public @ResponseBody String deleteVm(@RequestBody VmHost vmHost) {
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vmHost.getName();
		try {
			a[1]="成功";
			VmHost result=vc.action(vmHost.getId(),ActionType.DESTROY,null);
			logobject.setObjects(a);
			VmTask ti=result.getTaskInfo();
			logobject.setObjects(a);
			logobject.setResourceId(result.getId());
			if(ti!=null){
				logobject.setTaskCreateTime(ti.getCreateTime());
				logobject.setResourceName(result.getName());
				logobject.setTaskId(ti.getTaskId());
				logobject.setTaskInfoName(ti.getTaskName());	
			}
			LogUitls.putArgs(logobject);
			return JsonUtil.success("删除虚拟机成功");        
		} catch (Exception e) {
			a[1]="失败";
			logobject.setOperationResult("1");
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/releaseVm", method = RequestMethod.POST)
	@Log(message="删除虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_DELETE,moduleType="虚拟机")
	public @ResponseBody String releaseVm(@RequestBody VmHost vmHost) {
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vmHost.getName();
		try {
			a[1]="成功";
			vc.action(vmHost.getId(),ActionType.RELEASE,null);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("删除虚拟机成功");        
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/deleteVmNetcard", method = RequestMethod.POST)
	@Log(message="删除虚拟机网络{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_DELETE,moduleType="虚拟机")
	public @ResponseBody String deleteVmNetcard(@RequestBody VmNet vn) {
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vn.getNetId();
		try {
			a[0]="";
			a[1]="成功";
			vc.removeNet(vn.getVmId(), vn.getId());  
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.success("删除虚拟机网络成功");        
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}



	@SuppressWarnings("static-access")
	@RequestMapping(value = "/recycleVm", method = RequestMethod.POST)
	@Log(message="回收虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_STOP,moduleType="虚拟机")
	public @ResponseBody String recycleVm(@RequestBody VmHost vmHost) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vmHost.getName();
		try {
			a[1]="成功";	
			vc.action(vmHost.getId(),ActionType.REVOKE,null);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("回收虚拟机成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/configVm", method = RequestMethod.POST)
	@Log(message="配置虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_STOP,moduleType="虚拟机",isTaskType=true)
	public @ResponseBody String configVm(@RequestBody VmConfig vmHost) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vmHost.getName();
		try {
			a[1]="成功";	
			VmTask result=vc.config(vmHost.getVmId(), vmHost);
			logobject.setObjects(a);
			logobject.setResourceName(vmHost.getName());
			if(result!=null){
				logobject.setResourceId(result.getResourceId());
				logobject.setTaskInfoName(result.getTaskName());
				logobject.setTaskId(result.getTaskId());
				logobject.setTaskCreateTime(result.getCreateTime());
			}
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.success("配置虚拟机成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/updateVm", method = RequestMethod.POST)
	@Log(message="修改虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_UPDATE,moduleType="虚拟机")
	public @ResponseBody String updateVm(@RequestBody VmHost vmHost) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vmHost.getName();
		try {
			a[1]="成功";	
			vc.update(vmHost.getId(), vmHost);
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.success("修改虚拟机成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/distributeVm", method = RequestMethod.POST)
	@Log(message="分配虚拟机{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_DISTRIBUTION,moduleType="虚拟机",isTaskType=true)
	public @ResponseBody String distributeVm(@RequestBody VmHost vmHost) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vmHost.getName();
		try {
			a[1]="成功";	
			vc.action(vmHost.getId(), ActionType.ASSIGN, vmHost);
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.success("分配虚拟机成功");
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/deleteVmDisk", method = RequestMethod.POST)
	@Log(message="删除虚拟机磁盘{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_DELETE,moduleType="虚拟机")
	public @ResponseBody String deleteVmDisk(@RequestBody VmDisk vd) {
		LogObject logobject = new LogObject();
		Object a [] =new Object [2];
		a[0]=vd.getsPoolId();
		try {
			a[0]="";
			a[1]="成功";
			vc.removeDisk(vd.getVmId(), vd.getId());
			logobject.setObjects(a);	
			LogUitls.putArgs(logobject);
			return JsonUtil.success("删除虚拟机磁盘成功");        
		} catch (Exception e) {
			a[1]="失败";
			logobject.setObjects(a);
			LogUitls.putArgs(logobject);
			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/addVmNetcard", method = RequestMethod.POST)
	@Log(message="添加虚拟机网卡{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_CONFIG,moduleType="虚拟机",isTaskType=true)
	public @ResponseBody String addVmNetcard(@RequestBody List<VmNet> vn) {
		JSONArray ja=new JSONArray();
		for(VmNet o:vn){
			JSONObject res=new JSONObject();
			Object a [] =new Object [2];
			LogObject logobject = new LogObject();
			try{
				a[0]="";
				a[1]="成功";
				VmNet result=vc.addNet(o.getVmId(),o);	
				VmTask ti=result.getTaskInfo();
				logobject.setObjects(a);
				logobject.setResourceId(result.getId());
				if(ti!=null){
					logobject.setTaskId(ti.getTaskId());
					logobject.setTaskCreateTime(ti.getCreateTime());
					logobject.setTaskInfoName(ti.getTaskName());	
				}
				logobject.setObjects(a);	
				LogUitls.putArgs(logobject);
				res.put("flag", true);
				res.put("message", "添加虚拟机网络成功");
				ja.add(res);
			}
			catch(Exception e){
				a[1]="失败";
				res.put("flag", false);
				res.put("message", e.getMessage());
				ja.add(res);
				logobject.setObjects(a);
				logobject.setOperationResult("1");
				LogUitls.putArgs(logobject);
			}

		}
		return ja.toString();	
	}


	@SuppressWarnings("static-access")
	@RequestMapping(value = "/addVmDisk", method = RequestMethod.POST)
	@Log(message="添加虚拟机磁盘{0}{1}",resourceType=LogConst.RESOURCE_VM,operationType=LogConst.OPERATIONTYPE_CONFIG,moduleType="虚拟机",isTaskType=true)
	public @ResponseBody String addVmDisk(@RequestBody List<VmDisk> vn) {
		JSONArray ja=new JSONArray();
		for(VmDisk o:vn){
			JSONObject res=new JSONObject();
			Object a [] =new Object [2];
			LogObject logobject = new LogObject();
			try{
				a[0]="";
				a[1]="成功";
				VmDisk result=vc.addDisk(o.getVmId(), o);
				VmTask ti=result.getTaskInfo();
				logobject.setObjects(a);
				logobject.setResourceId(result.getId());
				if(ti!=null){
					logobject.setTaskCreateTime(ti.getCreateTime());
					logobject.setTaskInfoName(ti.getTaskName());	
					logobject.setTaskId(ti.getTaskId());
				}
				logobject.setObjects(a);	
				LogUitls.putArgs(logobject);
				res.put("flag", true);
				res.put("message", "添加虚拟机磁盘成功");
				ja.add(res);
			}
			catch(Exception e){
				a[1]="失败";
				res.put("flag", false);
				res.put("message", e.getMessage());
				ja.add(res);
				logobject.setObjects(a);
				logobject.setOperationResult("1");
				LogUitls.putArgs(logobject);
			}

		}
		return ja.toString();
	}
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/validIp", method = RequestMethod.POST)
	public @ResponseBody String validIp(
			@RequestParam(value = "ip", required = true) String ip,
			@RequestParam(value = "subnet", required = true) String subnet

			) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/

		try {
			CIDRUtils cidrUtils = new CIDRUtils(subnet);
			boolean isInRange = cidrUtils.isInRange(ip);
			if(!isInRange){
				return "false";

			}
			else{
				return "true";
			}
		} catch (Exception e) {

			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/validVmName", method = RequestMethod.POST)
	public @ResponseBody String validVmName(
			@RequestParam(value = "name", required = true) String name

			) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/

		try {
			VmHost vh=new VmHost();
			vh.setName(name);
			Page<VmHost> list=vc.list(vh, 1, 8);
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
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/queryVmHistory", method = RequestMethod.POST)
	public @ResponseBody String queryVmHistory(
			@RequestBody JSONObject params 
			) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		JSONObject jo=new JSONObject();
		String internalId=params.getString("internalId");
		String vmId=params.getString("vmId");
		try {
			if(internalId!=null){
				jo=vmService.getHistory(internalId);		        
			}
			else{
				VmHost search=vc.show(vmId);
				if(search.getInternalId()!=null){
					jo=vmService.getHistory(search.getInternalId());    
				}
			}
			return jo.toString();
		} catch (Exception e) {

			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/queryVNCUrl", method = RequestMethod.POST)
	public @ResponseBody String queryVNCUrl(
			@RequestBody JSONObject params 
			) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/
		JSONObject jo=new JSONObject();
		String internalId=params.getString("internalId");
		try {
			QueryVMConsoleCmd queryVMConsoleCmd = new QueryVMConsoleCmd();
			queryVMConsoleCmd.setVmId(internalId);
			QueryVMConsoleTask task = new QueryVMConsoleTask(queryVMConsoleCmd);
			QueryVMConsoleAnswer answer = task.execute();
			System.out.println(answer);
			System.out.println("=================");
			System.out.println(answer.getConsoleURL());
			jo.put("url", answer.getConsoleURL());
			return jo.toString();
		} catch (Exception e) {

			return JsonUtil.error(e.getMessage(), e);
		}
	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/queryVmTaskStatus", method = RequestMethod.POST)
	public @ResponseBody String queryVmTaskStatus(
			@RequestBody VmHost search		
			) {
		/*		logger.info("传入的虚拟机参数为"+vmId);*/

		try {
			search=vc.show(search.getId());
			RunStatus status=search.getRunStatus();
			if(status.toString().equals("NONE")){
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
