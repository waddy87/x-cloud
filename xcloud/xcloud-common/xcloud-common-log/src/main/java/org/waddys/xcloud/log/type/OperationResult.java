/**
 * Created on 2016年4月8日
 */
package org.waddys.xcloud.log.type;

/**
 * 功能名: 请填写功能名
 *
 * 功能描述: 请简要描述功能的要点
 *
 * Copyright: Copyright (c) 2016
 *
 * 公司: 曙光云计算技术有限公司
 *
 * @author 曾兵
 * @version 2.0.0 sp1
 */
public enum OperationResult {

	SUCCESS("成功"),

	FAIL("失败");

	private String value;

	OperationResult(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}
