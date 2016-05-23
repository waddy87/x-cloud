/**
 * Created on 2016年4月19日
 */
package org.waddys.xcloud.monitor.action.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.waddys.xcloud.monitor.bo.VMBo;
import org.waddys.xcloud.monitor.service.service.VMService;
import org.waddys.xcloud.monitor.serviceImpl.util.PerfConstants;
import org.waddys.xcloud.monitor.serviceImpl.util.ToolsUtils;
import org.waddys.xcloud.user.bo.Role;
import org.waddys.xcloud.user.bo.RoleEnum;
import org.waddys.xcloud.user.bo.User;
import org.waddys.xcloud.vm.bo.VmHost;
import org.waddys.xcloud.vm.service.VmService;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */
@Controller("monitor-orgMonitorController")
@RequestMapping(path = "/monitor")
public class OrgMonitorController {
	
	   
	   @Qualifier("monitor-vmServiceImpl")
	    @Autowired
	    private VMService vmService;

	    @Qualifier("monitor-toolsutils")
	    @Autowired
	    private ToolsUtils toolsUtils;
	    
	   @Autowired
	   private VmService vms;

	    @RequestMapping("/orgMonitor/vmTopN")
	    @ResponseBody
	    public JSONObject getAllVMs(HttpSession session) {
	    	//通过当前用户的session获取oid
	    	User user= 	(User)session.getAttribute("currentUser");
	    	String oid=	user.getOrgId();
	    	String uid=	user.getId();
	    	List<Role> roles =user.getRoles();
	    	List<String> vmidStrings=new ArrayList<String>();
	    	
	    	for(Role role:roles){
	    		if(role.getRoleCode().equals(RoleEnum.OPERATION_MANAGER.getCode())){
	    			//运营管理员单独页面处理
	    		}else if(role.getRoleCode().equals(RoleEnum.ORG_MANAGER.getCode())){
	    			//通过oid获取虚拟机列表信息
	    			Page<VmHost> page=vms.pageByOID(oid, null, null);
	    	    	if(page!=null && page.getTotalElements() >0L){
	    	    		List<VmHost> vmHostList=page.getContent();
	    	    		for(VmHost vmhost: vmHostList){
	    	    			vmidStrings.add(vmhost.getInternalId());
	    	    		}
	    	    	}
	    		}else if(role.getRoleCode().equals(RoleEnum.ORG_USER.getCode())){
	    			//通过oid获取虚拟机列表信息
	    			Page<VmHost> page=vms.pageByUID(uid, null, null);;
	    	    	if(page!=null && page.getTotalElements() >0L){
	    	    		List<VmHost> vmHostList=page.getContent();
	    	    		for(VmHost vmhost: vmHostList){
	    	    			vmidStrings.add(vmhost.getInternalId());
	    	    		}
	    	    	}
	    		}
	    	}	    		
	    	//获取虚拟机top5信息
	        JSONObject result = new JSONObject();
	        List<VMBo> vmBos = vmService.getVMsByIds(vmidStrings);
	        int topN = 5;
	        result.put("vmList", vmBos);
	        result.put("topnCpuUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.CPU_USAGE_ID));
	        result.put("topnCpuUsed", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.CPU_USED_ID));
	        result.put("topnDiskIO", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_IO_SPEED_ID));
	        result.put("topnMemUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.MEM_USAGE_ID));
	        result.put("topnMemUsed", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.MEM_USED_ID));
	        result.put("topnNetIO", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.NET_IO_SPEED_ID));
	        result.put("topnDiskUsage", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_USAGE_ID));
	        result.put("topnDiskIOPS", toolsUtils.getResourceMetricTopN(vmBos, topN, PerfConstants.DISK_IOPS_ID));
	        
	        return result;
	    }
	
	@RequestMapping(path="/orgMonitor" ,method=RequestMethod.GET)
	public ModelAndView orgMonitor(HttpServletRequest request, Model model){
		return new ModelAndView("monitor/orgMonitor");
	}
	
	
}
