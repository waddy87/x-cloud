package org.waddys.xcloud.log.api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.waddys.xcloud.log.type.LogLevel;

@Documented
@Target({ METHOD })
@Retention(RUNTIME)
public @interface Log {

	/**
	 * 日志信息
	 */
	String message() default "";

	/**
	 * 业务类型
	 */
	String businessType() default "";

	/**
	 * 业务操作类型
	 */
	String operationType() default "";
	
	/**
	 * 模块类型
	 */
	String moduleType() default "";
	
	/**
	 * 资源类型
	 */
	String resourceType() default "";

	/**
	 * 
	 * 功能: 是否任务类型操作，默认不是
	 *
	 * @return
	 */
	boolean isTaskType() default false;

	/**
	 * 日志记录等级
	 */
	LogLevel level() default LogLevel.INFO;

}
