package com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sugon.cloudview.cloudmanager.managedvm.service.exception.OldVirtualMachineException;
import com.sugon.cloudview.cloudmanager.managedvm.serviceImpl.dao.entity.OldVirtualMachineE;

public class Validate {

	private static Logger logger = LoggerFactory.getLogger(Validate.class);

	public static void isExistsAndIsDeleted(OldVirtualMachineE oldVirtualMachineE) throws OldVirtualMachineException {

		if (oldVirtualMachineE == null) {
			logger.debug("OldVirtualMachineException：该利旧虚拟机不存在！");
			throw new OldVirtualMachineException("该利旧虚拟机不存在！");
		}

		if (oldVirtualMachineE.getIsDeleted() == 1) {
			logger.debug("OldVirtualMachineException：不能对孤立的利旧虚拟机进行操作！");
			throw new OldVirtualMachineException("利旧虚拟机为孤立的，只能进行删除动作！");
		}
	}

	public static void isExistsAndIsDeleted(List<OldVirtualMachineE> oldVirtualMachineEs)
	        throws OldVirtualMachineException {

		for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
			if (oldVirtualMachineE == null) {
				logger.debug("OldVirtualMachineException：包含不存在的利旧虚拟机！");
				throw new OldVirtualMachineException("包含不存在的利旧虚拟机！");
			}

			if (oldVirtualMachineE.getIsDeleted() == 1) {
				logger.debug("OldVirtualMachineException：不能对孤立的利旧虚拟机进行操作！");
				throw new OldVirtualMachineException("利旧虚拟机为孤立的，只能进行删除动作！");
			}
		}
	}

	public static List<OldVirtualMachineE> isExistsAndIsDeleted(List<OldVirtualMachineE> oldVirtualMachineEs,
	        String property, int flag,
	        String log)
 throws OldVirtualMachineException, NoSuchMethodException, SecurityException,
	                IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		OldVirtualMachineE oldVM = new OldVirtualMachineE();
		Class clazz = oldVM.getClass();
		Method methodGet = clazz.getDeclaredMethod("get" + property);
		Method methodSet = clazz.getDeclaredMethod("set" + property);
		
		for (OldVirtualMachineE oldVirtualMachineE : oldVirtualMachineEs) {
			if (oldVirtualMachineE == null) {
				logger.debug("OldVirtualMachineException：包含不存在的利旧虚拟机！");
				throw new OldVirtualMachineException("包含不存在的利旧虚拟机！");
			}

			if (oldVirtualMachineE.getIsDeleted() == 1) {
				logger.debug("OldVirtualMachineException：不能对孤立的利旧虚拟机进行操作！");
				throw new OldVirtualMachineException("利旧虚拟机为孤立的，只能进行删除动作！");
			}
			
			if ((int) methodGet.invoke(oldVirtualMachineE) == flag) {
				logger.debug("OldVirtualMachineException：" + log + "！");
				throw new OldVirtualMachineException(log + "！");
			}
			
			methodSet.invoke(oldVirtualMachineE, flag);
		}

		return oldVirtualMachineEs;
	}
}
