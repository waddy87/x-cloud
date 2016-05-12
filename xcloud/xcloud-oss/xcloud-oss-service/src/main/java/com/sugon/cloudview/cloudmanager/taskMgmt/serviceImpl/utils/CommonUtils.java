package com.sugon.cloudview.cloudmanager.taskMgmt.serviceImpl.utils;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class CommonUtils {
    public static void converterMethod(Object dst, Object src) {
        try {
            BeanUtils.copyProperties(dst, src);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static String converterDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        return strDate;

    }

    public static List<String> getSamesection(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<String>();
        for (String str : list2) {// 遍历list1
            if (list1.contains(str)) {// 如果存在这个数
                result.add(str);// 放进一个list里面，这个list就是交集
            }
        }
        return result;
    }
}
