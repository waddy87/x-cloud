/**
 * Created on 2016年4月22日
 */
package com.sugon.cloudview.cloudmanager.monitor.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.monitor.api.bo.ClusterTopo;
import com.sugon.cloudview.cloudmanager.monitor.api.bo.HostTopo;
import com.sugon.cloudview.cloudmanager.monitor.api.bo.VMTopo;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.HostBo;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.VMBo;
import com.sugon.cloudview.cloudmanager.monitor.service.service.HostService;
import com.sugon.cloudview.cloudmanager.monitor.service.service.VMService;

/**
 * 功能名: 请填写功能名
 * 功能描述: 请简要描述功能的要点
 * Copyright: Copyright (c) 2016
 * 公司: 曙光云计算技术有限公司
 *
 * @author yangkun
 * @version 2.0.0 sp1
 */

@Controller("monitor-TopoController")
@RequestMapping(path = "/monitor")
public class TopoController {
	
	    @Qualifier("monitor-hostServiceImpl")
	    @Autowired
	    private HostService hostService;
	   
	    @Qualifier("monitor-vmServiceImpl")
	    @Autowired
	    private VMService vmService;
	
	
		
		@RequestMapping(path="/getTopo" ,method=RequestMethod.GET)
		@ResponseBody
		public JSONObject getTopo(HttpServletRequest request, Model model){
			
		    JSONObject result = new JSONObject();
	        List<HostBo> hostBos = hostService.getHosts();
	        List<ClusterTopo> clusters=new ArrayList<ClusterTopo>();
	        List<HostTopo> hostTopos=new ArrayList<HostTopo>();
	        if(hostBos==null || hostBos.isEmpty() || hostBos.size()==0){
	        	return result;
	        }
	        for(HostBo hostbo:hostBos){
	        	//将独立的host放入hostTopos列表中
	        	if(hostbo.isbStandalone()==true || hostbo.getClusterName()==null){
	        		HostTopo hostTopo=builderHostTopoByHostBo(hostbo);
	        		hostTopos.add(hostTopo);
	        	}else{  //将属于集群的host放入 clusters中
	        		String clusterString=hostbo.getClusterName();
	        		if(clusters.size()>0 && !clusters.isEmpty()){
	        			boolean inCluster=false;
	        			ClusterTopo inClusterTopo=null;
	        			for(ClusterTopo clusterTopo:clusters){
		        			if(clusterTopo.getClusterName().equals(clusterString)){
		        				inCluster=true;
		        				inClusterTopo=clusterTopo;
		        			}
		        		}
	        			//如果在集群列表中，则在集群列表的主机列表中添加该主机
	        			if(inCluster==true){
	        				HostTopo hostTopo=builderHostTopoByHostBo(hostbo);
	        				inClusterTopo.getHostsTopo().add(hostTopo);
	        			}else {  //如果不在集群列表中，则新建列表，并将host放到集群里面
	        				ClusterTopo cTopo=new ClusterTopo();
		        			List <HostTopo> hostTopos2=new ArrayList<HostTopo>();
		        			cTopo.setClusterId(clusterString);
		        			cTopo.setClusterName(clusterString);
		        			cTopo.setHostsTopo(hostTopos2);
		        			HostTopo hostTopo=builderHostTopoByHostBo(hostbo);
		        			cTopo.getHostsTopo().add(hostTopo);
		        			clusters.add(cTopo);
						}
	        			
	        		}else{  //初始化，如果没有对应的集群，先创建一个集群，并将host放到集群里面
	        			ClusterTopo cTopo=new ClusterTopo();
	        			List <HostTopo> hostTopos2=new ArrayList<HostTopo>();
	        			cTopo.setClusterId(clusterString);
	        			cTopo.setClusterName(clusterString);
	        			cTopo.setHostsTopo(hostTopos2);
	        			HostTopo hostTopo=builderHostTopoByHostBo(hostbo);
	        			cTopo.getHostsTopo().add(hostTopo);
	        			clusters.add(cTopo);
	        		}
	        	}
	        }
	        result.put("clusters", clusters);
	        result.put("hosts", hostTopos);
	        return result;
		}
		
		/*
		 * 构建hostTopo对象
		 */
		public HostTopo	builderHostTopoByHostBo(HostBo hostbo){
			HostTopo hostTopo=new HostTopo();
			hostTopo.setHostId(hostbo.getId());
			hostTopo.setHostName(hostbo.getName());
			hostTopo.setPowerStatus(hostbo.getPowerStatus());
			hostTopo.setStatus(hostbo.getStatus());
			hostTopo.setIpAddr(hostbo.getIpAddr());
			
		    List<VMBo>vmBos=	hostbo.getVmList();
		    List<VMTopo> vmTopos=new ArrayList<VMTopo>();
			if(vmBos!=null && !vmBos.isEmpty() && vmBos.size()>0){
				for(VMBo vmbo:vmBos){
					  VMTopo vm= new VMTopo();
	    		      vm.setVmId(vmbo.getId());
	    		      vm.setVmName(vmbo.getName());
	    		      vm.setPowerStatus(vmbo.getPowerStatus());
	    		      vm.setStatus(vmbo.getStatus());
	    		      vm.setIpAddr(vmbo.getIpAddr());
	    		      vmTopos.add(vm);
	    		}
			}
			hostTopo.setVmsTopo(vmTopos);
			return hostTopo;
		}
}
