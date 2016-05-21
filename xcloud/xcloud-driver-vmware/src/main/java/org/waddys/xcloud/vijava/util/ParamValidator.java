package org.waddys.xcloud.vijava.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class ParamValidator {
    /**
     * 默认构造方法
     */
    public ParamValidator() {

    }

    /**
     * validate param
     * 
     * @param param
     *            only one param
     * @return when Map,Collection,Arrays,String is null or size is 0 ,return
     *         false; when objects is null,return false;
     */
    private static boolean validatorParamNotEmpty(Object param) {

        if (null == param) {
            return false;
        }

        /**
         * validate String
         */
        if (String.class.isInstance(param)) {
            if (0 == ((String) param).trim().length()) {
                return false;
            }
        }

        /**
         * validate Collection
         */
        if (Collection.class.isInstance(param)) {
            if (0 == ((Collection<?>) param).size()) {
                return false;
            }
        }

        /**
         * validate Map
         */
        if (param instanceof Map<?, ?>) {
            if (0 == ((Map<?, ?>) param).size()) {
                return false;
            }
        }

        /**
         * validate Arrays
         */
        if (param.getClass().isArray()) {
            if (0 == Array.getLength(param)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 全部不为空即返回true<br/>
     * this function validate Map,Collection,Arrays,String,and other objects
     * except Primitive types is not empty
     * 
     * @param params
     *            variable params,accept objects which extends Object
     * @return when Map,Collection,Arrays,String is null or size is 0, when
     *         objects is null,return false;
     */
    public static boolean validatorParamsNotEmpty(Object... params) {
        if (null == params) {
            return false;
        }
        for (Object obj : params) {
            if (!ParamValidator.validatorParamNotEmpty(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 有一个为空即返回true
     * 
     * @param params
     *            variable params,accept objects which extends Object
     * @return return true when Map,Collection,Arrays,String is null or size is
     *         0 and objects is null;
     */
    public static boolean validatorParamsIsEmpty(Object... params) {
        if (null == params) {
            return true;
        }
        for (Object obj : params) {
            if (!ParamValidator.validatorParamNotEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 全部为空时返回true
     * 
     * @param params
     * @return
     */
    public static boolean validatorParamsIsEmptyAll(Object... params) {
        if (null == params) {
            return true;
        }
        for (Object obj : params) {
            if (ParamValidator.validatorParamNotEmpty(obj)) {
                return false;
            }
        }
        return true;
    }

}
