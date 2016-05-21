/**
 * @Title: JsonResult.java
 * @project Cloudview V1.8.0
 *
 * Copyright (c) 2014 公司: 曙光云计算有限公司
 * SUGON PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package org.waddys.xcloud.common.base.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import net.sf.json.JSONObject;

/**
 * <b>非线程安全对象</b><br/>
 * 本实体类是根据Cloudview产品开发约定进行封装，将异常对象转换为String对象，用于在UI层的前后台数据交换。
 * <p>
 * 参数说明：
 * <ul>
 * <li>flag 标识服务是否正常结束。</li>
 * <li>result 标识接口的返回值，可以为Object、List、Map等类型的JSON格式数据。</li>
 * <li>message 异常的信息：param为占位符的参数（字符串数组），desc为异常描述，solution为解决方案</li>
 * </ul>
 * <p>
 * 场景举例：<br>
 * 1、接口异常<br>
 * {flag:false,message:{code:"abc_123",params:["参数1","参数2"],desc:"可选描述信息",
 * solution:"解决方案"}} <br>
 * 2、接口正常，返回值为Map对象<br>
 * {flag:true,result:"{total:5,rows:[{"id":"1","name":"张三"},{"id":"2","name":"李四
 * "}]}"} <br>
 * 3、接口正常，返回值为Object对象<br>
 * {flag:true,result:"{"id":"1","name":"张三"}"} <br>
 * 4、接口正常，返回值为List对象<br>
 * {flag:true,result:"[{"id":"1","name":"张三"},{"id":"2","name":"李四"}]"} <br>
 * 5、接口正常，无返回值<br>
 * {flag:true}
 * 
 * @author lishch
 */
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -1096442426984574943L;

    /**
     * 结果状态标识<br />
     */
    private boolean flag = true;

    /**
     * 结果值<br />
     * 可以为任何类型, 一般性为结果信息与结果返回值, 前者表示返回的操作信息, 后者表示返回值
     */
    private Object result = null;

    /**
     * 提示信息
     */
    private JsonMessage message = new JsonMessage();

    /**
     * 空构造函数
     */
    public JsonResult() {
    }

    public boolean getFlag() {
        return flag;
    }

    public JsonResult failed() {
        this.flag = false;
        return this;
    }

    public JsonResult success() {
        this.flag = true;
        return this;
    }

    public Object getResult() {
        return result;
    }

    /**
     * 返回值为单个对象
     * 
     * @param result
     * @return
     */
    public JsonResult setResult(Object result) {
        this.result = result;
        return this;
    }

    /**
     * 返回值为List类型
     * 
     * @param result
     * @return
     */
    @SuppressWarnings("unchecked")
    public JsonResult addResultObject(Object object) {
        if (this.result == null || this.result instanceof List<?>) {
            this.result = new LinkedList<Object>();
        }
        ((List<Object>) result).add(result);
        return this;
    }

    /**
     * 返回值为List类型
     * 
     * @param result
     * @return
     */
    @SuppressWarnings("unchecked")
    public JsonResult addResultObjects(List<?> objects) {
        if (this.result == null || this.result instanceof List<?>) {
            this.result = new LinkedList<Object>();
        }
        ((List<Object>) result).addAll(objects);
        return this;
    }

    /**
     * 返回值为Map类型
     * 
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public JsonResult putResultObject(String key, Object value) {
        if (this.result == null || this.result instanceof Map<?, ?>) {
            this.result = new HashMap<String, Object>();
        }
        ((Map<String, Object>) result).put(key, value);
        return this;
    }

    /**
     * 返回值为Map类型
     * 
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public JsonResult putResultObjects(Map<? extends String, ?> objects) {
        if (this.result == null || this.result instanceof Map<?, ?>) {
            this.result = new HashMap<String, Object>();
        }
        ((Map<String, Object>) result).putAll(objects);
        return this;
    }

    public JsonResult setError(Exception e) {
        CloudviewException error = null;
        CloudviewExceptionCode code = null;
        if (e instanceof CloudviewException) {
            // 系统内部已定义的异常
        	
        	if(  (code = (error = (CloudviewException) e).getCode()) != null){
        		this.message.setCode(code.getCode()).setParams(error.getParams()).setDesc(code.getDesc())
                    .setSolution(code.getSolution());
        	}
        	else{
        		//TODO 此处暂时放过没有异常码的情况  以后系统定义异常码后恢复历史版本
        		this.message.setDesc(e.getMessage());
        	}
        		
        } else {
            // 外部异常或无异常code的异常
            this.message.setCode(CloudviewCommonExceptionCode.EP001_S001_M001_L1_C00001.getCode())
                    .setDesc(CloudviewCommonExceptionCode.EP001_S001_M001_L1_C00001.getDesc())
                    .setSolution(CloudviewCommonExceptionCode.EP001_S001_M001_L1_C00001.getSolution());
        }
        return this;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag", this.flag);
        if (flag && this.result != null) {
            jsonObject.put("result", this.result);
        }
        if (!flag && this.message != null) {
            jsonObject.put("message", this.message);
        }
        return jsonObject.toString();
    }

    public String toString(Logger logger) {
        String str = this.toString();
        if (logger != null) {
            logger.debug("JsonResult: " + str);
        }
        return str;
    }

    public static void main(String[] args) {
        String str = new JsonResult()
                .setError(new CloudviewException(CloudviewCommonExceptionCode.EP001_S001_M001_L1_C00001)).toString();
        System.out.println(str);
    }
}