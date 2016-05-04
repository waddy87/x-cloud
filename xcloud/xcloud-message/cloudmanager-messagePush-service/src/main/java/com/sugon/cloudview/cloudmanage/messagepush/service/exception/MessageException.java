/**
 * 
 */
package com.sugon.cloudview.cloudmanage.messagepush.service.exception;

import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewException;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月24日
 */
public class MessageException extends CloudviewException {

    private static final long serialVersionUID = 5420682359274653370L;

    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 错误信息
     */
    private final String errorMsg;

    /**
     * 当错误产生时 <br>
     * 部分业务逻辑需要返回业务数据，将这些业务数据放到这里。<br>
     * 数据需为JSON对象的字符串，内容参见业务接口定义文档
     */
    private final String failureData;

    /**
     * @param errorCode
     * @param errorMsg
     * @param failureData
     */
    public MessageException(String errorCode, String errorMsg, String failureData) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.failureData = failureData;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @return the failureData
     */
    public String getFailureData() {
        return failureData;
    }

}
