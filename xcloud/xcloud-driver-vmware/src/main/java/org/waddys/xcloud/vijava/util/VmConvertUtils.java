package org.waddys.xcloud.vijava.util;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;
import org.waddys.xcloud.vijava.exception.VirtException;

public class VmConvertUtils {

    private static final String EMPTYSTRING = "";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date == null ? System.currentTimeMillis() : date.getTime());
    }

    /**
     * 功能:日期转换 string to date
     * 
     * @param str
     * @param format
     * @return 如果解析失败则返回空
     */
    public static Date convertStringToDate(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 功能:日期转换 date to string
     * 
     * @param str
     * @param format
     * @return
     * @throws ParseException
     */
    public static String convertDateToString(Date date) {
        if (date != null) {
            return simpleDateFormat.format(date);
        } else {
            return getCurrentDateString();
        }
    }

    public static String getCurrentDateString() {
        return convertDateToString(new Date());
    }

    public static String convertDateToString(Date date,String format){
    	SimpleDateFormat dateFormate = new SimpleDateFormat(format);
    	if(date == null)
    		date = new Date();
    	String dateString = dateFormate.format(date); 
    	 return dateString;
    }
    /**
     * 功能：获得x分钟之前的时间
     * 
     * @param date
     * @param x
     * @return
     */
    public static Date xMinutesBefore(Date date, int x) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MINUTE, -x);
        return ca.getTime();
    }

    /**
     * 功能：获得x小时之前的时间
     * 
     * @param date
     * @param x
     * @return
     * @throws Exception
     */
    public static Date xHoursBefore(Date date, int x) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR, -x);
        return ca.getTime();
    }
    
    /**
     * this method used for change the pieres B to GB.
     * @param param
     * @return
     */
     
    public static long B2GB(long param){
    	return param/1024/1024/1024;
    }

    /**
     * this method used for change the pieres B to MB.
     * @param param
     * @return
     */
     
    public static long B2MB(long param){
    	return param/1024/1024;
    }

    /**
     * this method used for change the pieres HZ to MHZ.
     * @param param
     * @return
     */
    public static long HZ2MHZ(long param){
    	return param/1024/1024;
    }

    /**
     * 功能: 获得x小时之后的时间
     * 
     * @param date
     * @param x
     * @return
     */
    public static Date xHourAfter(Date date, int x) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR, +x);
        return ca.getTime();
    }

    /**
     * 功能：获得x天之前的时间
     * 
     * @param date
     * @param x
     * @return
     * @throws Exception
     */
    public static Date xDaysBefore(Date date, int x) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, -x);
        return ca.getTime();
    }

    /**
     * 功能：获得x月之前的时间
     * 
     * @param date
     * @param x
     * @return
     */
    public static Date xMonthsBefore(Date date, int x) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, -x);
        return ca.getTime();
    }

    /**
     * 防止进度条出现小于0或大于100的值出现，出现将导致进度条出问题
     * 
     * @param targetValue
     * @return
     */
    public static double formatProgressValue(double targetValue) {
        double value = targetValue;
        if (targetValue > 100) {
            value = 100;
        } else if (value < 0) {
            value = 0;
        }
        return value;
    }

    /**
     * 四舍五入2位小数后转换为String
     * 
     * @param data
     *            double型数据
     * @return String数据
     */
    public static Double doubleFormatter(Object data) {
        Double d = getDoubleWithNull(data);
        if (d == null) {
            return 0D;
        } else {
            return doubleTakingOut0(new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }

    /**
     * 功能: 保留两位小数，如果不足两位小数则补齐 （1）图内的小数点后1位小数的默认补一个零，例如：21.30；
     * （2）图内数字为整数的，默认补2个零，例如：21.00； （3）图内数字是0的，默认就是一个0，例如：0。
     * 
     * @param data
     * @return
     */
    public static String getDoubleFormat(Object data) {
        Double d = getDoubleWithNull(data);
        if (d == null) {
            d = 0D;
        }

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);

    }

    /**
     * 方法名称: doubleTrans <br/>
     * 描述: 例如double值为100.0，则转化为100
     * 
     * @param value
     * @return
     */
    private static Double doubleTakingOut0(Double value) {
        if (value == null) {
            return null;
        }
        if (Math.round(value) - value == 0) {
            return Double.valueOf(value.longValue());
        } else {
            return value;
        }
    }

    /**
     * 将任意类型转换为 Long，否则返回 null
     * 
     * @param str
     * @return
     */
    public static Long getLong(Object str) {
        Long l = getLongWithNull(str);
        if (l == null) {
            return 0L;
        } else {
            return l;
        }
    }

    /**
     * 将任意类型转换为 Long，否则返回 null
     * 
     * @param str
     * @return
     */
    public static Long getLongWithNull(Object str) {
        try {
            if (str == null) {
                return null;
            } else if (str instanceof String) {
                return Long.valueOf((String) str);
            } else if (str instanceof Integer) {
                return ((Integer) str).longValue();
            } else if (str instanceof Long) {
                return (Long) str;
            } else if (str instanceof Double) {
                return ((Double) str).longValue();
            } else if (str instanceof Float) {
                return ((Float) str).longValue();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将任意类型转换为 Integer，否则返回 0
     * 
     * @param str
     * @return
     */
    public static Integer getInteger(Object str) {
        Integer i = getIntegerWithNull(str);
        if (i == null) {
            return 0;
        } else {
            return i;
        }
    }

    /**
     * 将任意类型转换为 Integer，否则返回 null
     * 
     * @param str
     * @return
     */
    public static Integer getIntegerWithNull(Object str) {
        try {
            if (str == null) {
                return null;
            } else if (str instanceof Integer) {
                return (Integer) str;
            } else if (str instanceof String) {
                return Integer.valueOf((String) str);
            } else if (str instanceof Long) {
                return ((Long) str).intValue();
            } else if (str instanceof Float) {
                return ((Float) str).intValue();
            } else if (str instanceof Double) {
                return ((Double) str).intValue();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Double getDouble(Object str) {
        Double d = getDoubleWithNull(str);
        if (d == null) {
            return 0D;
        } else {
            return d;
        }
    }

    public static Double getDoubleWithNull(Object str) {
        try {
            if (str == null) {
                return null;
            } else if (str instanceof Double) {
                return (Double) str;
            } else if (str instanceof String) {
                return Double.valueOf((String) str);
            } else if (str instanceof Integer) {
                return ((Integer) str).doubleValue();
            } else if (str instanceof Long) {
                return ((Long) str).doubleValue();
            } else if (str instanceof Float) {
                return ((Float) str).doubleValue();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean getBooleanWithNull(Object str) {
        if (str == null) {
            return null;
        } else if (str instanceof Boolean) {
            return (Boolean) str;
        } else if (str instanceof String) {
            return Boolean.valueOf((String) str);
        } else {
            return null;
        }
    }

    public static String getString(Object str) {
        String s = getStringWithNull(str);
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }

    public static String getStringWithNull(Object str) {
        if (str == null) {
            return null;
        } else if (str instanceof String) {
            return (String) str;
        } else if (str.getClass().isEnum()) {
            return ((Enum<?>) str).name();
        } else {
            return str.toString();
        }
    }

    public static List<?> getList(Object o) {
        return convert(o, List.class, new ArrayList<Object>());
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getListByType(Object o) {
        return convert(o, List.class, new ArrayList<T>());
    }

    public static Map<?, ?> getMap(Object o) {
        return convert(o, Map.class, new HashMap<Object, Object>());
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getMapByType(Object o) {
        return convert(o, Map.class, new HashMap<K, V>());
    }

    public static List<?> getListWithNull(Object o) {
        return convert(o, List.class);
    }

    public static Map<?, ?> getMapWithNull(Object o) {
        return convert(o, Map.class);
    }

    public static String getFilePath(String pathName) {
        if (pathName == null || pathName.trim().length() == 0) {
            return EMPTYSTRING;
        }
        pathName = pathName.trim();
        int index = pathName.trim().lastIndexOf(File.separator);
        if (index < 0) {
            return EMPTYSTRING;
        } else {
            return pathName.substring(0, index);
        }
    }

    public static String getFileName(String pathName) {
        if (pathName == null || pathName.trim().length() == 0) {
            return EMPTYSTRING;
        }
        pathName = pathName.trim();
        int index = pathName.lastIndexOf(File.separator);
        if (index < 0) {
            return pathName;
        } else {
            return pathName.substring(index + 1);
        }
    }

    public static String getFileNameWithoutSuffix(String pathName) {
        String fileName = getFileName(pathName);
        if (ParamValidator.validatorParamsNotEmpty(fileName)) {
            return fileName.substring(0, fileName.lastIndexOf('.'));
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(getFileNameWithoutSuffix("fafsa.vhd"));
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Object tmpObject, Class<T> clazz) {
        if (tmpObject == null) {
            return null;
        } else if (Integer.class == clazz) {
            return (T) getIntegerWithNull(tmpObject);
        } else if (Long.class == clazz) {
            return (T) getLongWithNull(tmpObject);
        } else if (Double.class == clazz) {
            return (T) getDoubleWithNull(tmpObject);
        } else if (String.class == clazz) {
            return (T) getStringWithNull(tmpObject);
        } else {
            Assert.isInstanceOf(clazz, tmpObject);
            return (T) tmpObject;
        }
    }

    public static <T> T convert(Object tmpObject, Class<T> clazz, T defautlValue) {
        T retValue = convert(tmpObject, clazz);
        if (retValue == null) {
            retValue = defautlValue;
        }
        return retValue;
    }

    // public static CaseInsensitiveMap toLowerCaseKey(Map<?, ?> param) {
    // if (param != null && param.size() > 0) {
    // return new CaseInsensitiveMap(param);
    // } else {
    // return new CaseInsensitiveMap();
    // }
    // }

    @SuppressWarnings("unchecked")
    public static <T> T convertWithException(Object tmpObject, Class<T> clazz) throws VirtException {
        if (tmpObject == null) {
            throw new VirtException("参数类型不正确");
        }

        Assert.isInstanceOf(clazz, tmpObject);
        return (T) tmpObject;
    }

    public static String[] convertList2Array(List<String> params) {
        if (params != null) {
            return params.toArray(new String[] {});
        } else {
            return new String[] {};
        }
    }

    public static List<String> convertArray2List(String[] params) {
        if (params != null) {
            return new ArrayList<String>(Arrays.asList(params));
        } else {
            return new ArrayList<String>();
        }
    }
}
