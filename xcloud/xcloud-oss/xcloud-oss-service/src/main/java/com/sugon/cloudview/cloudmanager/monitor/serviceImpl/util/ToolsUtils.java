package com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.ClusterBo;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.ComputeSystemBo;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.HostBo;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.StorageBo;
import com.sugon.cloudview.cloudmanager.monitor.service.bo.VMBo;
import com.sugon.vim25.HostSystemConnectionState;
import com.sugon.vim25.HostSystemPowerState;
import com.sugon.vim25.ManagedEntityStatus;
import com.sugon.vim25.PerfCounterInfo;
import com.sugon.vim25.VirtualMachinePowerState;

@Service("monitor-toolsutils")
public class ToolsUtils {
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public double CpuHzToGHz(double cpuHz) {
        return Double.parseDouble(decimalFormat.format(cpuHz / 1000 / 1000 / 1000.0));
    }

    public double CpuHzToMHz(double cpuHz) {
        return Double.parseDouble(decimalFormat.format(cpuHz / 1000 / 1000.0));
    }

    public double CpuMHzToGHz(double cpuMHz) {
        return Double.parseDouble(decimalFormat.format(cpuMHz / 1000.0));
    }

    public double MembyteToGB(double membyte) {
        return Double.parseDouble(decimalFormat.format(membyte / 1024 / 1024 / 1024.0));
    }

    public double MembyteToMB(double membyte) {
        return Double.parseDouble(decimalFormat.format(membyte / 1024 / 1024.0));
    }

    public double MemMBToGB(double memMB) {
        return Double.parseDouble(decimalFormat.format(memMB / 1024.0));
    }

    public double StorebyteToTB(double storebyte) {
        return Double.parseDouble(decimalFormat.format(storebyte / 1024 / 1024 / 1024 / 1024.0));
    }

    public double StoreMBToTB(double storeMB) {
        return Double.parseDouble(decimalFormat.format(storeMB / 1024 / 1024.0));
    }

    public String getPercentage(double part, double total) {
        if (total == 0) {
            return "0";
        }
        String result = decimalFormat.format((part / total) * 100);
        return result;
    }

    public String getAvg(double sum, double num) {
        if (num == 0) {
            return "0";
        }
        String result = decimalFormat.format((sum / num));
        return result;
    }

    /**
     * 功能: 根据ob1类的属性名称，将ob1对象属性值赋予ob2同名属性（必须满足命名规则，set、get方法首字母大写）；
     *      支持list属性的赋值
     *
     * @param ob1
     * @param ob2
     */
    public void convert(Object ob1, Object ob2) {
        Class<?> parent = ob1.getClass().getSuperclass();
        Class<?> classType1 = ob1.getClass();
        Class<?> classType2 = ob2.getClass();

        Field[] parentFields = parent.getDeclaredFields();
        // 获得对象的所有成员变量
        Field[] fields = classType1.getDeclaredFields();

        Field[] f = (Field[]) ArrayUtils.addAll(parentFields, fields);
        for (Field field : f) {
            // if ( field.getGenericType() instanceof ParameterizedType ) {
            // System.out.println((( ParameterizedType ) field.getGenericType()
            // ).getActualTypeArguments()[0]);
            // }else{
            // System.out.println(field.getType().getSimpleName());
            // }
            // 如果类型是list
            if (field.getGenericType() instanceof ParameterizedType) {
                Class<?> ob2Generic = null;
                // 获取成员变量的名字
                String name = field.getName();

                // 获取get和set方法的名字
                String firstLetter = name.substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + name.substring(1);
                String setMethodName = "set" + firstLetter + name.substring(1);
                Method getMethod;
                try {
                    getMethod = classType1.getMethod(getMethodName, new Class[] {});
                    Method setMethod = classType2.getMethod(setMethodName, new Class[] { field.getType() });// 注意set方法需要传入参数类型
                    Type[] t = setMethod.getGenericParameterTypes();
                    Pattern pattern = Pattern.compile("<(.*)>");
                    Matcher matcher = pattern.matcher(t[0].toString());
                    String className = "";
                    while (matcher.find()) {
                        className = matcher.group(1);
                    }
                    ob2Generic = Class.forName(className);
                    List<?> value1 = (List<?>) getMethod.invoke(ob1, new Object[] {});

                    List<Object> value2 = new ArrayList<Object>();
                    if (value1 != null && value1.size() > 0) {
                        for (int i = 0; i < value1.size(); i++) {
                            Object object1 = value1.get(i);
                            Object object2 = ob2Generic.newInstance();
                            convert(object1, object2);
                            value2.add(object2);
                        }
                        setMethod.invoke(ob2, new Object[] { value2 });
                    }

                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                // 获取成员变量的名字
                String name = field.getName();

                // 获取get和set方法的名字
                String firstLetter = name.substring(0, 1).toUpperCase(); // 将属性的首字母转换为大写
                String getMethodName = "get" + firstLetter + name.substring(1);
                String setMethodName = "set" + firstLetter + name.substring(1);

                // 获取方法对象
                Method getMethod;
                try {
                    getMethod = classType1.getMethod(getMethodName, new Class[] {});
                    Method setMethod = classType2.getMethod(setMethodName, new Class[] { field.getType() });// 注意set方法需要传入参数类型

                    Object value = getMethod.invoke(ob1, new Object[] {});
                    setMethod.invoke(ob2, new Object[] { value });
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    System.err.println("----name:" + name + " 属性转换失败！");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 功能: 得到某类资源list按某类型指标排序的topn
     *
     * @param computeSystemBos
     *            ComputeSystemBo子类
     * @param topn
     *            返回top多少
     * @param type
     *            指标类型，详见下面的方法：CreateSortUtils
     * @return
     */
    public List getResourceMetricTopN(List<?> computeSystemBos, int topn, String type) {
        List<ComputeSystemBo> topnResource = new ArrayList<ComputeSystemBo>();
        List<SortUtils> resourceSort = new ArrayList<SortUtils>();
        Iterator<?> resourceIter = computeSystemBos.iterator();
        int index = 0;
        while (resourceIter.hasNext()) {
            Object computeSystemBo = resourceIter.next();
            // 断开连接的主机或虚拟机不参与topn排序
            if (computeSystemBo instanceof HostBo) {
                if (PerfConstants.HOST_CONN_STATUS_DISCONNECTED
                        .equals(((HostBo) computeSystemBo).getConnectionStatus())) {
                    continue;
                }
            } else if (computeSystemBo instanceof HostBo) {
                if (PerfConstants.HOST_CONN_STATUS_DISCONNECTED
                        .equals(((VMBo) computeSystemBo).getConnectionStatus())) {
                    continue;
                }
            }
            SortUtils su = CreateSortUtils((ComputeSystemBo) computeSystemBo, index, type);
            index++;
            resourceSort.add(su);
        }

        Collections.sort(resourceSort);
        index = 0;

        Iterator<SortUtils> iter = resourceSort.iterator();
        while (iter.hasNext()) {
            if (index >= topn) {
                break;
            }
            SortUtils su = iter.next();
            topnResource.add((ComputeSystemBo) computeSystemBos.get(su.getIndex()));
            index++;
        }

        return topnResource;
    }

    /**
     * 功能: 根据输入的type类型，从ComputeSystemBo对象中取出相应的指标值，生成排序集合
     *
     * @param computeSystemBo
     * @param index
     * @param type
     * @return
     */
    private SortUtils CreateSortUtils(ComputeSystemBo computeSystemBo, int index, String type) {

        double value = 0.0;

        switch (type) {
        case PerfConstants.CPU_TOTAL_ID:
            value = getDoubleFromString(computeSystemBo.getCpuMHZTotal());
            break;

        case PerfConstants.CPU_USAGE_ID:
            value = getDoubleFromString(computeSystemBo.getCpuUsage());
            break;

        case PerfConstants.CPU_USED_ID:
            value = getDoubleFromString(computeSystemBo.getCpuMHZUsed());
            break;

        case PerfConstants.MEM_TOTAL_ID:
            value = getDoubleFromString(computeSystemBo.getMemoryTotal());
            break;

        case PerfConstants.MEM_USAGE_ID:
            value = getDoubleFromString(computeSystemBo.getMemoryUsage());
            break;

        case PerfConstants.MEM_USED_ID:
            value = getDoubleFromString(computeSystemBo.getMemoryUsed());
            break;

        case PerfConstants.DISK_TOTAL_ID:
            value = getDoubleFromString(computeSystemBo.getDiskTotal());
            break;

        case PerfConstants.DISK_USED_ID:
            value = getDoubleFromString(computeSystemBo.getDiskUsed());
            break;

        case PerfConstants.DISK_USAGE_ID:
            value = getDoubleFromString(computeSystemBo.getDiskUsage());
            break;

        case PerfConstants.DISK_IO_SPEED_ID:
            value = Double.valueOf(computeSystemBo.getDiskIOSpeed());
            break;

        case PerfConstants.DISK_IOPS_ID:
            value = getDoubleFromString(computeSystemBo.getDiskIops());
            break;

        case PerfConstants.NET_IO_SPEED_ID:
            value = getDoubleFromString(computeSystemBo.getNetworkTransmitSpeed());
            break;

        case PerfConstants.NET_RX_ID:
            value = getDoubleFromString(computeSystemBo.getNetworkReceiveSpeed());
            break;

        case PerfConstants.NET_TX_ID:
            value = getDoubleFromString(computeSystemBo.getNetworkSendSpeed());
            break;

        case PerfConstants.VM_NUMS_ID:
            if (computeSystemBo instanceof HostBo) {
                value = Double.valueOf(((HostBo) computeSystemBo).getVmNum());
            } else if (computeSystemBo instanceof ClusterBo) {
                value = Double.valueOf(((ClusterBo) computeSystemBo).getVmNum());
            } else if (computeSystemBo instanceof StorageBo) {
                value = Double.valueOf(((StorageBo) computeSystemBo).getVmNum());
            } else {
                value = 0.0;
            }

            break;

        case PerfConstants.Host_NUMS_ID:
            if (computeSystemBo instanceof ClusterBo) {
                value = Double.valueOf(((ClusterBo) computeSystemBo).getHostNumber());
            } else if (computeSystemBo instanceof StorageBo) {
                value = Double.valueOf(((StorageBo) computeSystemBo).getHostNum());
            }

            break;

        default:
            value = 0.0;
            break;
        }

        return new SortUtils(index, value);
    }

    public double getDoubleFromString(String value) {
        double result = 0.0;
        if (value == null) {
            return result;
        }
        try {
            result = Double.parseDouble(value);
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static List getPage(List<?> resourceList, int pageNum, int pageSize) {
        List<ComputeSystemBo> computeSystemBos = new ArrayList<ComputeSystemBo>();
        int total = resourceList.size();
        int start=(pageNum-1)*pageSize;
        int end=pageNum*pageSize;
        if (start>=total) {
            return computeSystemBos;
        }
        for(int i=start;i<end;i++){
            if (i < total) {
                computeSystemBos.add((ComputeSystemBo) resourceList.get(i));
            }
        }
        return computeSystemBos;
    }
    // 以上为在Cloudview3.0基础上新增代码

	public static String MakeMetricFullName(PerfCounterInfo pcinfo){
		String counterGroup = pcinfo.getGroupInfo().getKey();
		String counterName = pcinfo.getNameInfo().getKey();
		String counterRollupType = pcinfo.getRollupType().toString().toUpperCase();
		String fullCounterName = counterGroup + "." + counterName + "." + counterRollupType;
		return fullCounterName;
	}
		
	
	public static String convertManageObjectStatusToString(ManagedEntityStatus mes){
		String status = PerfConstants.OBJECT_STATUS_GRAY;
		switch(mes){
		case green:
			status = PerfConstants.OBJECT_STATUS_GREEN;
			break;
		case yellow:
			status = PerfConstants.OBJECT_STATUS_YELLOW;
			break;
		case red:
			status = PerfConstants.OBJECT_STATUS_RED;
			break;
		default:
			break;
		}
		return status;
	}
	public static String convertHostConnectionStatusToString(HostSystemConnectionState hscs){
		String status = PerfConstants.HOST_CONN_STATUS_CONNECTED;
		switch(hscs){
		case connected:
			status = PerfConstants.HOST_CONN_STATUS_CONNECTED;
			break;
		case disconnected:
			status = PerfConstants.HOST_CONN_STATUS_DISCONNECTED;
			break;
		case notResponding:
			status = PerfConstants.HOST_CONN_STATUS_NORES;
			break;
		default:
			break;
		}
		return status;
	}
	public static String convertHostPowerStatusToString(HostSystemPowerState hsps){
		String status = PerfConstants.OBJECT_POWER_STATUS_UNKNOWN;
		switch(hsps){
		case poweredOn:
			status = PerfConstants.OBJECT_POWER_STATUS_ON;
			break;
		case poweredOff:
			status = PerfConstants.OBJECT_POWER_STATUS_OFF;
			break;
		case standBy:
			status = PerfConstants.OBJECT_POWER_STATUS_STANDBY;
			break;
		default:
			break;
		}
		return status;
	}
	public static String convertVmPowerStatusToString(VirtualMachinePowerState vmps){
		String status = PerfConstants.OBJECT_POWER_STATUS_ON;
		switch(vmps){
		case poweredOn:
			status = PerfConstants.OBJECT_POWER_STATUS_ON;
			break;
		case poweredOff:
			status = PerfConstants.OBJECT_POWER_STATUS_OFF;
			break;
		case suspended:
			status = PerfConstants.OBJECT_POWER_STATUS_SUSPEND;
			break;
		default:
			break;
		}
		return status;
	}
	
	public static String makeHostKeyName(String name){
		return PerfConstants.HOST_PREFIX_KEY + name;
	}
	
	public static String GetMapValue(Map<String, String> map, String key){
		String value = map.get(key);
		return (value == null) ? PerfConstants.PERF_DEFAULT_VALUE:value;
	}
	
	public static long GetMapValueLong(Map<String, Long> map, String key){
		Long value = map.get(key);
		return (value == null) ? PerfConstants.PERF_DEFAULT_LONG_VALUE:value;
	}
	public static double GetMapValueDouble(Map<String, Double> map, String key){
		Double value = map.get(key);
		return (value == null) ? PerfConstants.PERF_DEFAULT_DOUBLE_VALUE:value;
	}
	
	
	
	public static String getCalendarAsString(Calendar calendar, int nDateTimeType)
	{
		if(calendar == null){
			return "1970-01-01 00:00:00";
		}
		String sYear = String.valueOf(calendar.get(Calendar.YEAR));
        String sMonth = (calendar.get(Calendar.MONTH) + 1) > 9
					? String.valueOf((calendar.get(Calendar.MONTH)+1))
					: "0" +(calendar.get(Calendar.MONTH)+1);
        String sDay = calendar.get(Calendar.DAY_OF_MONTH) > 9
					? String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))
					: "0" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String sHour = calendar.get(Calendar.HOUR_OF_DAY)>9
					? String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))
					: "0" + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		String sMin = calendar.get(Calendar.MINUTE)>9
					? String.valueOf(calendar.get(Calendar.MINUTE))
					: "0" + String.valueOf(calendar.get(Calendar.MINUTE));
		String sSecond = calendar.get(Calendar.SECOND)>9
					? String.valueOf(calendar.get(Calendar.SECOND))
					: "0" + String.valueOf(calendar.get(Calendar.SECOND));

		String sReturnDateTime = sYear + "-" + sMonth + "-" + sDay + " "
				+ sHour + ":" + sMin + ":" + sSecond;

		if(nDateTimeType == 0)
		{
			sReturnDateTime = sYear + "-" + sMonth + "-" + sDay;
		}
		if(nDateTimeType == 1)
		{
			sReturnDateTime = sHour + ":" + sMin + ":" + sSecond;
		}

		return sReturnDateTime;
	}
}
