package org.waddys.xcloud.alert.serviceImpl.service.utils;

import java.util.Calendar;
import java.util.Map;

import org.waddys.xcloud.alert.service.bo.AlertSenderUI;
import org.waddys.xcloud.alert.serviceImpl.entity.AlertSender;

import com.vmware.vim25.HostSystemConnectionState;
import com.vmware.vim25.HostSystemPowerState;
import com.vmware.vim25.ManagedEntityStatus;
import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.VirtualMachinePowerState;

/**
 * @author yangkun
 *
 */

public class ToolsUtils {
	
	public static String MakeMetricFullName(PerfCounterInfo pcinfo){
		String counterGroup = pcinfo.getGroupInfo().getKey();
		String counterName = pcinfo.getNameInfo().getKey();
		String counterRollupType = pcinfo.getRollupType().toString().toUpperCase();
		String fullCounterName = counterGroup + "." + counterName + "." + counterRollupType;
		return fullCounterName;
	}
		
	public static void converBoToPo(AlertSenderUI alertSenderUI, AlertSender alertSender){
		alertSender.setAlertLevel(alertSenderUI.getAlertLevel());
		alertSender.setAlertSendLevel(alertSenderUI.getAlertLevel());
		alertSender.setEnable(alertSenderUI.isEnable());
		alertSender.setMessage(alertSenderUI.getMessage());
		alertSender.setName(alertSenderUI.getName());
		alertSender.setReceiver(alertSenderUI.getReceiver());
		alertSender.setResId(alertSenderUI.getResId());
		alertSender.setResName(alertSenderUI.getResName());
		alertSender.setResType(alertSenderUI.getResType());
		alertSender.setSendType(alertSenderUI.getSendType());
		alertSender.setTriggerDetail(alertSenderUI.getTriggerDetail());
		alertSender.setTriggerId(alertSenderUI.getTriggerId());
		alertSender.setTriggerName(alertSenderUI.getTriggerName());
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
