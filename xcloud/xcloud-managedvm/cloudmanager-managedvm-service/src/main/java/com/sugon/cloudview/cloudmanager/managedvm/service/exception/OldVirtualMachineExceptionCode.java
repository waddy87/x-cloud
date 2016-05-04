package com.sugon.cloudview.cloudmanager.managedvm.service.exception;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewExceptionCode;

public enum OldVirtualMachineExceptionCode implements CloudviewExceptionCode {

	MANAGEDVM001_001_001("该利旧虚拟机已被托管", "请选择未被托管的利旧虚拟机进行操作"),

	MANAGEDVM001_001_002("EP001_001_002 的描述信息，{0} 对象存在异常", "解决方案"),

	MANAGEDVM001_001_003("EP001_001_002 的描述信息，{0} 对象存在异常", "解决方案"),

	MANAGEDVM001_001_004("EP001_001_002 的描述信息，{0} 对象存在异常", "解决方案"),

	MANAGEDVM001_001_005("EP001_001_002 的描述信息，{0} 对象存在异常", "解决方案"),

	MANAGEDVM001_001_006("EP001_001_002 的描述信息，{0} 对象存在异常", "解决方案"),

	MANAGEDVM001_001_007("EP001_001_002 的描述信息，{0} 对象存在异常", "解决方案"),

	MANAGEDVM001_001_008("EP001_001_002 的描述信息，{0} 对象存在异常", "解决方案"),

	MANAGEDVM001_001_009("EP001_001_002 的描述信息，{0} 对象存在异常", "解决方案"),

	MANAGEDVM001_001_010("EP001_001_002 的描述信息，{0} 对象存在异常", "解决方案");

	private String desc;

	private String solution;

	private OldVirtualMachineExceptionCode(String desc, String solution) {
		this.desc = desc;
		this.solution = solution;
	}

	@Override
	public String getCode() {
		return this.name();
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

	@Override
	public String getSolution() {
		return this.solution;
	}

}
