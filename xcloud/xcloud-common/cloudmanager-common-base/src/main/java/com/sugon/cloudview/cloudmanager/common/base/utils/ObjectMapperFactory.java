package com.sugon.cloudview.cloudmanager.common.base.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugon.cloudview.cloudmanager.common.base.exception.CloudviewRuntimeException;

public class ObjectMapperFactory {

    static Logger log = LoggerFactory.getLogger(ObjectMapperFactory.class);
    public static final String DefaultListResult = "[]";
    public static final String DefaultMapResult = "{\"value\":0}";
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(format);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return objectMapper;
    }

    public static String writeValueAsString(Object resultObj) {
        try {
            return objectMapper.writeValueAsString(resultObj);
        } catch (Exception e) {
            return null;
        }
    }

    public static String writeValueAsString(Object resultObj, Logger logger) {
        if (logger == null) {
            logger = log;
        }
        try {
            String tmp = objectMapper.writeValueAsString(resultObj);
            logger.debug("writeValueAsString: " + tmp);
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    public static String writeValueAsString(List<String> resultObj, Logger logger) {
        if (logger == null) {
            logger = log;
        }
        try {
            String tmp = objectMapper.writeValueAsString(new LinkedList<Object>(resultObj));
            logger.debug("writeValueAsString: " + tmp);
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T getObjectFromStringWithNull(String message, Class<T> clazz, Logger logger) {
        if (logger == null) {
            logger = log;
        }
        try {
            logger.debug("step into getObjectFromString: " + message);
            return objectMapper.readValue(message, clazz);
        } catch (Exception e) {
            logger.error("", e);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObjectFromString(String message, Class<T> clazz, Logger logger)
            throws CloudviewRuntimeException {
        if (logger == null) {
            logger = log;
        }
        try {
            logger.debug("step into getObjectFromString: " + message);
            return objectMapper.readValue(message, clazz);
        } catch (Exception e) {
            logger.error("对象转换异常", e);
            throw new CloudviewRuntimeException("转换异常");
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromStringWithoutNull(String message, Logger logger) {
        if (logger == null) {
            logger = log;
        }
        Map<String, Object> map = null;
        try {
            logger.debug("step into getMapFromString: " + message);
            map = objectMapper.readValue(message, HashMap.class);
            logger.debug("getMapFromString map: " + map);
        } catch (Exception e) {
            logger.error("解析String失败", e);
        }
        if (map == null) {
            return new HashMap<String, Object>();
        } else {
            return map;
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromString(String message, Logger logger) throws CloudviewRuntimeException {
        if (logger == null) {
            logger = log;
        }
        Map<String, Object> map = null;
        try {
            logger.debug("step into getMapFromString: " + message);
            map = objectMapper.readValue(message, HashMap.class);
            logger.debug("getMapFromString map: " + map);
        } catch (Exception e) {
            logger.error("解析String失败", e);
        }
        if (map == null) {
            throw new CloudviewRuntimeException("参数解析失败");
        } else {
            return map;
        }
    }

    public static List<?> getListFromStringWithNull(String message, Logger logger) {
        if (logger == null) {
            logger = log;
        }
        if (message == null) {
            return null;
        }
        List<?> list = null;
        try {
            logger.debug("step into getListFromStringWithoutNull: " + message);
            list = objectMapper.readValue(message, List.class);
            logger.debug("getMapFromString map: " + list);
        } catch (Exception e) {
            logger.error("解析String失败", e);
        }
        return list;
    }

    public static List<?> getListFromString(String message, Logger logger) throws CloudviewRuntimeException {
        if (logger == null) {
            logger = log;
        }
        List<?> list = null;
        try {
            logger.debug("step into getListFromString: " + message);
            list = objectMapper.readValue(message, List.class);
            logger.debug("getListFromString list: " + list);
        } catch (Exception e) {
            logger.error("解析String失败", e);
        }
        if (list == null) {
            throw new CloudviewRuntimeException("参数解析失败");
        } else {
            return list;
        }
    }

    public static class TestVo {
        private String name;
        private String sex;

        public String getName() {
            return name;
        }

        public TestVo setName(String name) {
            this.name = name;
            return this;
        }

        public String getSex() {
            return sex;
        }

        public TestVo setSex(String sex) {
            this.sex = sex;
            return this;
        }

        @Override
        public String toString() {
            return "TestVo [name=" + name + ", sex=" + sex + "]";
        }
    }

    public static void main(String[] args) {
        TestVo testVo = new TestVo().setName("waret");
        String str = ObjectMapperFactory.writeValueAsString(testVo);
        // {"name":"waret","sex":null}
        // 有属性为空时，转成的String中存在key-value对，且value为null
        System.out.println(str);
        String str1 = "{\"name\":\"waret\",\"age\":12}";
        testVo = ObjectMapperFactory.getObjectFromStringWithNull(str1, TestVo.class, null);
        // TestVo [name=waret, sex=null]
        // String中存在其它key-value时，不影响解析
        System.out.println(testVo);
    }
}
