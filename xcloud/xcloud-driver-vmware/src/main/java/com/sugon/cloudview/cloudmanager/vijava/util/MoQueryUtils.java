package com.sugon.cloudview.cloudmanager.vijava.util;

import java.rmi.RemoteException;

import com.sugon.vim25.KeyAnyValue;
import com.sugon.vim25.LicenseAssignmentManagerLicenseAssignment;
import com.sugon.vim25.RuntimeFault;
import com.sugon.vim25.mo.LicenseAssignmentManager;
import com.sugon.vim25.mo.LicenseManager;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.ServiceInstance;

public class MoQueryUtils {
	/**
	 * 管理对象目录结构中，根据类型取最近的祖先节点
	 * 
	 * @param mo
	 *            当前结点
	 * @return moType 要获取的最近祖先节点的类型
	 */
	public static ManagedEntity getNearestAncesterByType(ManagedEntity mo,
			String moType) {
		// 采用递归的方式 返回最近的祖先节点
		if (mo == null || moType == null || "".equals(moType))
			return null;
		else if (!moType.equals(mo.getMOR().getType()))
			return getNearestAncesterByType(mo.getParent(), moType);
		else
			return mo;
	}

	/**
	 * 查询vsphere版本信息 因一个链接中可能有多个版本的主机 本操作需底层保证host license版本一致
	 * 本工具方法的目的是返回其中一个主机的license
	 * 
	 * @param ServiceInstance
	 *            当前连接si实例
	 * @return String 连接中主机的版本信息
	 */
	public static String getVsphereVersion(ServiceInstance si)
			throws RuntimeFault, RemoteException {
		//
		String version = "";

		// 获得licenseManager
		LicenseManager lm = si.getLicenseManager();

		// 获得已用license管理类
		LicenseAssignmentManager lam = si.getLicenseManager()
				.getLicenseAssignmentManager();

		// 获得已用license信息
		LicenseAssignmentManagerLicenseAssignment[] llsm = lam
				.queryAssignedLicenses(null);

		// 遍历已用license看是否有主机license 如果有 则返回license名称
		for (int i = 0; i < llsm.length; i++) {

			String productName = "";
			KeyAnyValue[] prop = llsm[i].getProperties();
			// 获取license应用的实体名字 如果是VMware ESX Server 说明该license是vsphere license
			for (KeyAnyValue kv : prop) {
				if ("ProductName".equals(kv.getKey())) {
					productName = kv.getValue().toString();
					break;
				}
			}

			if ("VMware ESX Server".equals(productName)) {
				version += llsm[i].getAssignedLicense().getName();
				break;
			}
		}
		return version;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
