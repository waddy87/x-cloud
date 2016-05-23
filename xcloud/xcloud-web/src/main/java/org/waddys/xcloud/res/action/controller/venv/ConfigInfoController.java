package org.waddys.xcloud.res.action.controller.venv;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.waddys.xcloud.common.base.exception.CloudviewException;
import org.waddys.xcloud.log.api.Log;
import org.waddys.xcloud.log.impl.LogObject;
import org.waddys.xcloud.log.impl.LogUitls;
import org.waddys.xcloud.log.type.LogConst;
import org.waddys.xcloud.res.action.common.DateJsonValueProcessor;
import org.waddys.xcloud.res.bo.venv.VenvConfig;
import org.waddys.xcloud.res.exception.venv.VenvException;
import org.waddys.xcloud.res.service.service.vdc.ComputingPoolService;
import org.waddys.xcloud.res.service.service.venv.VenvConfigService;
import org.waddys.xcloud.res.service.service.vnet.NetPoolService;
import org.waddys.xcloud.vijava.base.CloudviewExecutor;
import org.waddys.xcloud.vijava.environment.ConnectCloudVM.ConnectCloudVMAnswer;
import org.waddys.xcloud.vijava.environment.ConnectCloudVM.ConnectCloudVMCmd;
import org.waddys.xcloud.vijava.environment.TestConnectInfo.TestConnectInfoAnswer;
import org.waddys.xcloud.vijava.environment.TestConnectInfo.TestConnectInfoCmd;
import org.waddys.xcloud.vijava.exception.VirtException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/venv")
public class ConfigInfoController {
	@Autowired
	private VenvConfigService venvConfigService;
	@Autowired
	private ComputingPoolService computingPoolService;
	@Autowired
	private CloudviewExecutor cloudviewExecutor;
	@Autowired
	private NetPoolService netPoolService;
	private static Logger logger = LoggerFactory.getLogger(ConfigInfoController.class);

	/*
	 * 获得虚拟化环境信息
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getConfigInfo")
	public String getConfigInfo(ModelMap model) {
		logger.debug("setp into method is getvenvConfigInfo()");
		List<VenvConfig> venvconfigInfos = venvConfigService.findConfigInfos();
		if (venvconfigInfos != null && venvconfigInfos.size() > 0) {
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
			model.put("configInfo1", JSONArray.fromObject(venvconfigInfos, config));
			model.addAttribute("configInfo", venvconfigInfos.get(0));
		} else {
			model.addAttribute("configInfo", null);
		}
		return "venv/venvIndex";
	}

	/**
	 * 添加虚拟环境
	 * 
	 * @throws CloudviewException
	 * @throws VirtException
	 */
	/**
	 * 返回值0：成功；1：同步模板失败；2:同步利旧虚拟机失败;-1：同步数据失败
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addvenvConfigInfo")
	@ResponseBody
	@Log(message = "接入虚拟化环境{0}", businessType = "虚拟化环境", moduleType = LogConst.M_VENV_CONFIG, resourceType = "虚拟化环境", operationType = "接入")
	public String addvenvConfigInfo(@RequestParam(value = "configname") String configname,
			@RequestParam(value = "ip") String ip, @RequestParam(value = "username") String username,
			@RequestParam(value = "pwd") String pwd) throws VirtException, CloudviewException {
		logger.debug("step into method addvenvConfigInfo(),params:configname=" + configname);
		JSONObject jo = new JSONObject();
		LogObject logObject = new LogObject();
		Object ary[] = new Object[1];
		ary[0] = ip;
		logObject.setObjects(ary);
		try {
			ConnectCloudVMCmd cmd = new ConnectCloudVMCmd();
			cmd.setIp(ip);
			cmd.setUsername(username);
			cmd.setUserpwd(pwd);
			logger.debug("start call ConnectCloudVMAnswer method" + System.currentTimeMillis() + ".........");
			ConnectCloudVMAnswer answer = cloudviewExecutor.execute(cmd);
			logger.debug("finish call ConnectCloudVMAnswer method" + System.currentTimeMillis() + ".........");
			if (answer != null && answer.isSuccess()) {
				VenvConfig venvConfig = new VenvConfig();
				venvConfig.setConfigName(configname);
				venvConfig.setiP(ip);
				venvConfig.setUserName(username);
				venvConfig.setPassword(pwd);
				venvConfig.setCreateDate(new Date());
				venvConfig.setVersion(answer.getVersion());
				venvConfig.setStatus("1");// 1代表vcenter正常
				long start = System.currentTimeMillis();
				logger.debug("调用sysDataFromCloudvm()方法，开始同步数据");
				String falg=venvConfigService.synDataVenvConfig(venvConfig);
				long end = System.currentTimeMillis();
				logger.debug("开始同步数据操作完成,所耗时间" + (end - start));
				if("0".equals(falg)){
					jo.put("flag", "0");
					jo.put("message", "接入虚拟化环境成功");
				}
				if("1".equals(falg)){
					jo.put("flag", "1");
                    jo.put("message", "接入虚拟化环境成功,同步模板数据失败,请到模板模块同步数据");
				}
				if("2".equals(falg)){
					jo.put("flag", "2");
                    jo.put("message", "接入虚拟化环境成功,同步利旧虚拟机数据失败,请到利旧虚拟机模块同步数据");
				}
				
			}
		} catch (VenvException e) {
			logger.debug("接入虚拟化环境失败");
			jo.put("flag", "-1");
			jo.put("message", "接入虚拟化环境失败");
            logObject.setOperationResult("1");
		}
        LogUitls.putArgs(logObject);
		return jo + "";
	}

	/**
	 * 恢复虚拟化连接环境
	 */
	@PostConstruct
	public void recoverConfigInfo() {
		logger.debug("开始进入恢复虚拟化连接环境方法......................");
		try {
			List<VenvConfig> lst = venvConfigService.findConfigInfos();
			if(lst == null || lst.size() <= 0){
				logger.debug("无可恢复的虚拟化连接......................");
				return ;
			}
			for (VenvConfig venvConfig : lst) {
				ConnectCloudVMCmd cmd = new ConnectCloudVMCmd();
				cmd.setIp(venvConfig.getiP());
				cmd.setUsername(venvConfig.getUserName());
				cmd.setUserpwd(venvConfig.getPassword());
				logger.debug("start call ConnectCloudVMAnswer method" + System.currentTimeMillis() + ".........");
				ConnectCloudVMAnswer answer = cloudviewExecutor.execute(cmd);
				logger.debug("finish call ConnectCloudVMAnswer method" + System.currentTimeMillis() + ".........");
				logger.debug("返回结果" + answer);
				if (answer != null && answer.isSuccess()) {
					logger.debug("恢复虚拟化连接环境操作成功");
				} else {
					logger.debug("恢复虚拟化连接环境操作失败");
				}
			}
		} catch (VirtException e) {
			logger.debug("恢复虚拟化连接环境失败", e);
			e.printStackTrace();
		} catch (Exception e1) {
			logger.debug("恢复虚拟化连接环境失败", e1);
		}
	}

	/**
	 * 测试连接
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/testvenvConfigInfo")
	@ResponseBody
    @Log(message = "测试虚拟化环境{0}连接", businessType = "虚拟化环境", moduleType = LogConst.M_VENV_CONFIG, resourceType = "虚拟化环境", operationType = "测试连接")
	public String testvenvConfigInfo(@RequestParam(value = "id") String id,
			@RequestParam(value = "configname") String configname, @RequestParam(value = "ip") String ip,
			@RequestParam(value = "username") String username, @RequestParam(value = "pwd") String pwd) {
		logger.debug("step into method testvenvConfigInfo(),params:configname=" + configname + ",ip=" + ip
				+ ",username=" + username + ",pwd=" + pwd);
		JSONObject jo = new JSONObject();
		LogObject logObject = new LogObject();
		Object ary[] = new Object[1];
		ary[0] = ip;
		logObject.setObjects(ary);
		TestConnectInfoAnswer answer;
		try {
			answer = testConnectInfo(ip, username, pwd);
			logger.debug("cloudviewExecutor.execute(),return answer" + answer);
			if (answer != null && answer.isSuccess()) {
				if (id != null && id != "") {
					VenvConfig venvConfig = venvConfigService.findVenvConfigByconfigId(id);
					venvConfig.setStatus("1");
					venvConfigService.updateVenvConfig(venvConfig);
				}
				jo.put("flag", true);
			} else {
				if (id != null && id != "") {
					VenvConfig venvConfig = venvConfigService.findVenvConfigByconfigId(id);
					venvConfig.setStatus("2");
					venvConfigService.updateVenvConfig(venvConfig);
				}
                logObject.setOperationResult("1");
				jo.put("flag", false);
			}
		} catch (VenvException e1) {
			logger.debug("failure addvenvConfigInfo()");
			jo.put("flag", false);
			jo.put("message", e1.getMessage());
            logObject.setOperationResult("1");
		}
		logger.debug("method testvenvConfigInfo(),return jo=" + jo.toString());
        LogUitls.putArgs(logObject);
		return jo + "";
	}

	public TestConnectInfoAnswer testConnectInfo(String ip, String username, String pwd) throws VenvException {
		logger.debug("step into method testConnectInfo(),ip=" + ip + ",username=" + username + ",pwd=" + pwd);
		TestConnectInfoAnswer answer;
		try {
			TestConnectInfoCmd cmd = new TestConnectInfoCmd();
			cmd.setIp(ip);
			cmd.setUsername(username);
			cmd.setUserpwd(pwd);
			answer = cloudviewExecutor.execute(cmd);
			logger.debug("method testConnectInfo(),return answer=" + answer);
		} catch (VirtException e) {
			logger.debug("method testConnectInfo()," + e);
			throw new VenvException(e.getMessage());
		} catch (Exception e1) {
			logger.debug("method testConnectInfo()," + e1);
			throw new VenvException(e1.getMessage());
		}
		return answer;
	}

	/**
	 * 添加虚拟环境
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/addvenvConfigInfoPage")
	public String addvenvConfigInfoPage() throws VenvException {
		return "venv/addvenv";
	}

	/**
	 * 修改虚拟环境
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/editByvenvconfigId")
	@ResponseBody
	@Log(message = "修改虚拟化环境{0}", businessType = "虚拟化环境", moduleType = LogConst.M_VENV_CONFIG, resourceType = "虚拟化环境", operationType = "修改")
	public String editByvenvconfigId(@RequestParam(value = "id") String id,
			@RequestParam(value = "configname") String configname, @RequestParam(value = "ip") String ip,
			@RequestParam(value = "username") String username, @RequestParam(value = "pwd") String pwd)
					throws VenvException {
		logger.debug("step into method addvenvConfigInfo(),params:configname=" + configname);
		JSONObject jo = new JSONObject();
		LogObject logObject = new LogObject();
		Object ary[] = new Object[1];
		ary[0] = ip;
		logObject.setObjects(ary);
		try {
			VenvConfig venvconfig = venvConfigService.findVenvConfigByconfigId(id);
			venvconfig.setConfigName(configname);
			venvconfig.setiP(ip);
			venvconfig.setUserName(username);
			venvconfig.setPassword(pwd);
			venvconfig.setStatus("1");// 1代表vcenter正常
			venvConfigService.addVenvConfig(venvconfig);
			jo.put("flag", true);
		} catch (Exception e) {
			jo.put("flag", false);
			jo.put("messeage", e.getMessage());
            logObject.setOperationResult("1");
		}
        LogUitls.putArgs(logObject);
		return jo + "";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/editByvenvconfigIdPage")
	public String editByvenvconfigIdPage(@RequestParam(value = "id") String id, ModelMap model) throws VenvException {
		VenvConfig venvconfig = venvConfigService.findVenvConfigByconfigId(id);
		model.addAttribute("configInfo", venvconfig);
		return "venv/detailvenv";
	}

	/**
	 * 删除虚拟环境
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/delByconfigId")
	@ResponseBody
	@Log(message = "删除虚拟化环境", businessType = "虚拟化环境", moduleType = LogConst.M_VENV_CONFIG, resourceType = "虚拟化环境", operationType = "删除")
	public String delByconfigId(@RequestParam(value = "id") String id) throws VenvException {
		logger.debug("step into method delByconfigId(),params:configid=" + id);
		JSONObject jo = new JSONObject();
        LogObject logObject = new LogObject();
		try {
			VenvConfig venvconfig = venvConfigService.findVenvConfigByconfigId(id);
			if (venvconfig != null) {
				String configId = venvconfig.getConfigId();
				netPoolService.delNetPoolByConfigId(configId);
				logger.debug("success delNetPoolByConfigId");
				computingPoolService.deleteByConfigId(configId);
				logger.debug("success delComputingPool and delStoragePool success！");
				venvConfigService.delVenvConfig(configId);
				logger.debug("success delVenvConfig");
				// TODO 未删除模板与利旧虚拟机数据
			}
			logger.debug("success delete data from cloudvm");
			jo.put("flag", true);
		} catch (Exception e) {
			logger.debug("failure delete data from cloudvm");
			jo.put("flag", false);
            logObject.setOperationResult("1");
		}
        LogUitls.putArgs(new LogObject());
		return jo + "";
	}

	/**
	 * 同步虚拟化环境数据
	 */
	/**
	 * 返回值0：成功；1：同步模板失败；2:同步利旧虚拟机失败;-1：同步数据失败
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/sysDataFromCloudvmPage")
	@ResponseBody
	@Log(message = "同步虚拟化环境", businessType = "虚拟化环境", moduleType = LogConst.M_VENV_CONFIG, resourceType = "虚拟化环境", operationType = "同步")
	public String sysDataFromCloudvmPage() {
		logger.debug("step into method sysDataFromCloudvmPage()");
		JSONObject jo = new JSONObject();
        LogObject logObject = new LogObject();
		try {
			List<VenvConfig> venvConfigs = venvConfigService.findConfigInfos();
			if (venvConfigs != null && venvConfigs.size() > 0) {
				for (VenvConfig venvConfig : venvConfigs) {
					logger.debug("同步虚拟化环境：" + venvConfig.toString() + "数据");
					logger.debug("开始调用方法synDataFromCloudvmCompare()执行数据同步操作!");
					long start = System.currentTimeMillis();
					String falg=venvConfigService.synDataVenvConfigCompare(venvConfig.getConfigId(), venvConfig.getiP());
					long end = System.currentTimeMillis();
					logger.debug("执行数据同步操作完成,所耗时间:" + (end - start));
					if("0".equals(falg)){
						jo.put("flag", "0");
						logger.debug("执行数据同步操作完成,所耗时间:" + (end - start));
						jo.put("msg", "同步虚拟化环境数据成功");
					}
					if("1".equals(falg)){
						logger.debug("同步模板数据失败");
						jo.put("flag", "1");
						jo.put("msg", "同步模板数据失败");
                        logObject.setOperationResult("1");
					}
					if("2".equals(falg)){
						logger.debug("同步利旧虚拟机数据失败");
						jo.put("flag", "2");
						jo.put("msg", "同步利旧虚拟机数据失败");
                        logObject.setOperationResult("1");
					}
				}
			}
		} catch (VenvException e) {
			logger.debug("同步虚拟化环境网络或资源池数据" + e.getMessage());
			jo.put("flag", "-1");
            jo.put("msg", "同步虚拟化环境网络或资源池数据");
            logObject.setOperationResult("1");
		}
        LogUitls.putArgs(logObject);
		return jo + "";
	}
}
