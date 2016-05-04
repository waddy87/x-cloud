/**
 * 
 */
package com.sugon.cloudview.cloudmanager.exception;

import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author zhangdapeng
 *
 */
public class ApiException extends JsonMappingException {

    private static final long serialVersionUID = 1L;

    public ApiException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    public ApiException(String msg, Throwable rootCause) {
        super(msg, rootCause);
    }

}
