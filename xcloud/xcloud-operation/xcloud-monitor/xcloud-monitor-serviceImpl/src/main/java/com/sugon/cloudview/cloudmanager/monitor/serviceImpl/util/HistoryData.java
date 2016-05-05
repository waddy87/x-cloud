package com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sugon.cloudview.cloudmanager.monitor.service.bo.MetricValue;
import com.sugon.vim25.ManagedObjectReference;
import com.sugon.vim25.PerfCounterInfo;
import com.sugon.vim25.PerfEntityMetric;
import com.sugon.vim25.PerfEntityMetricBase;
import com.sugon.vim25.PerfFormat;
import com.sugon.vim25.PerfMetricId;
import com.sugon.vim25.PerfMetricIntSeries;
import com.sugon.vim25.PerfMetricSeries;
import com.sugon.vim25.PerfQuerySpec;
import com.sugon.vim25.PerfSampleInfo;
import com.sugon.vim25.mo.ManagedEntity;
import com.sugon.vim25.mo.PerformanceManager;
import com.sugon.vim25.mo.ServiceInstance;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("monitor-history")
public class HistoryData {
    @Qualifier("monitor-connection")
    @Autowired
    private Connection connection;

    /**
     * 功能: 资源历史数据接口
     *
     * @param resourceId
     *            资源Id
     * @param resourceType
     *            资源类型，可以是物理机、虚拟机、集群、存储；详见PerfConstants类ENTITY_CLUSTER、
     *            ENTITY_Host ENTITY_VM、ENTITY_DS
     * @param historyDataType
     *            历史数据类型，1天、1周、1月、1年；详见PerfConstants类HISTORY_前缀
     * @return
     */
    public JSONObject getJsonHistoryData(String resourceId, String resourceType, String historyDataType) {
        JSONObject resultJson = new JSONObject();
        Map<String, List<MetricValue>> metricListMap = getHistoryPerformData(resourceId, resourceType, historyDataType);

        JSONArray cpuTime = new JSONArray();
        JSONArray cpuValue = new JSONArray();

        JSONArray memTime = new JSONArray();
        JSONArray memValue = new JSONArray();

        JSONArray diskTime = new JSONArray();
        JSONArray diskValue = new JSONArray();

        JSONArray netTime = new JSONArray();
        JSONArray netValue = new JSONArray();

        JSONArray storeTime = new JSONArray();
        JSONArray storeValue = new JSONArray();

        if (metricListMap != null && metricListMap.isEmpty() == false) {
            for (String key : metricListMap.keySet()) {
                for (MetricValue mv : metricListMap.get(key)) {
                    if (key.equals("CPU_RATE")) {
                        cpuTime.add(mv.getCollectTime());
                        cpuValue.add(mv.getMetricValue());
                    } else if (key.equals("MEM_RATE")) {
                        memTime.add(mv.getCollectTime());
                        memValue.add(mv.getMetricValue());
                    } else if (key.equals("DSK_USAGE")) {
                        diskTime.add(mv.getCollectTime());
                        diskValue.add(mv.getMetricValue());
                    } else if (key.equals("NIC_AVG")) {
                        netTime.add(mv.getCollectTime());
                        netValue.add(mv.getMetricValue());
                    } else if (key.equals("STORE_USAGE")) {
                        storeTime.add(mv.getCollectTime());
                        storeValue.add(mv.getMetricValue());
                    }
                }
            }
        }

        // 组装单个指标值的json对象
        JSONObject mapCpu = new JSONObject();
        mapCpu.put("collectTime", cpuTime);
        mapCpu.put("values", cpuValue);

        JSONObject mapMem = new JSONObject();
        mapMem.put("collectTime", memTime);
        mapMem.put("values", memValue);

        JSONObject mapDisk = new JSONObject();
        mapDisk.put("collectTime", diskTime);
        mapDisk.put("values", diskValue);

        JSONObject mapNet = new JSONObject();
        mapNet.put("collectTime", netTime);
        mapNet.put("values", netValue);
        
        JSONObject mapStore = new JSONObject();
        mapStore.put("collectTime", storeTime);
        mapStore.put("values", storeValue);
        
        // JSONObject mapStore = new JSONObject();
        // for(int i=0; i<storageTime.size(); i++){
        // Object obj = diskUsedValue.get(i);
        // long used = (long) obj;
        // long capacity = Long.valueOf(diskCapacityValue.get(i).toString());
        // double usage = 0;
        // if (Math.abs(capacity - 0) > 0.0001) {
        // BigDecimal diskUsageBd = new BigDecimal((used / capacity) * 100);
        // usage = diskUsageBd.setScale(2,
        // BigDecimal.ROUND_HALF_UP).doubleValue();
        // }
        // mapStore.put("collectTime", storageTime);
        // mapStore.put("values", usage);
        // }

        // 组装所有指标值json对象
        JSONObject mapAllMertic = new JSONObject();
        mapAllMertic.put("cpuUsage", mapCpu);
        mapAllMertic.put("memUsage", mapMem);
        mapAllMertic.put("diskTps", mapDisk);
        mapAllMertic.put("netTps", mapNet);
        mapAllMertic.put("storeUsage", mapStore);

        resultJson.put("perf24", mapAllMertic);
        return resultJson;
    }

    public Map<String, List<MetricValue>> getHistoryPerformData(String resourceId, String resourceType,
            String historyDataType) {

        PerformanceManager perfMgr = null;
        // 最终返回的的所有指标的所有采样值的列表
        Map<String, List<MetricValue>> returnMapList = new HashMap<String, List<MetricValue>>();
        HashMap<Integer, String> keyMap = new HashMap<Integer, String>();

        // 判断资源类型,并获取性能数据
        PerfEntityMetric perfData = null;
        if (PerfConstants.ENTITY_CLUSTER.equals(resourceType) || 
                PerfConstants.ENTITY_HOST.equals(resourceType)|| 
                PerfConstants.ENTITY_VM.equals(resourceType) ||
                PerfConstants.ENTITY_DS.equals(resourceType)) {
            ManagedEntity managedEntity = null;
            try {
                ManagedObjectReference mor = new ManagedObjectReference();
                mor.setType(resourceType);
                mor.setVal(resourceId);
                managedEntity = new ManagedEntity(connection.getSi().getServerConnection(), mor);
            } catch (Exception e) {
                return null;
            }


            try {
                ServiceInstance si = connection.getSi();
                perfMgr = si.getPerformanceManager();
                perfData = getPerformanceContent(si, perfMgr, managedEntity, keyMap, historyDataType);
            } catch (Exception e) {
                System.err.println("----getHistory--resourceId:" + resourceId + "--查询失败");
                e.printStackTrace();
                return null;
            }
        }

        if (perfData == null) {
            return null;
        }

        PerfMetricSeries[] perfDataValues = perfData.value;
        PerfSampleInfo[] listinfo = perfData.sampleInfo;

        List<Store> dUsedList = new ArrayList<Store>();
        List<Store> dCapacityList = new ArrayList<Store>();
        // 获取设备的所有指标的采样值
        for (int i = 0; i < perfDataValues.length; i++) {

            // 具体某个指标的所有采样列表
            List<MetricValue> metricList = new ArrayList<MetricValue>();
            PerfMetricSeries perfDataValue = perfDataValues[i];
            if (perfDataValue instanceof PerfMetricIntSeries) {
                long[] values = ((PerfMetricIntSeries) perfDataValue).value;

                // 获取所有指标的所有采样值
                for (int j = 0; j < values.length; j++) {

                    int id = perfDataValue.id.counterId;
                    String value = String.valueOf(values[j]);
                    String keyString = keyMap.get(id);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
                    String time1 = sdf1.format(listinfo[j].timestamp.getTime());

                    // CPU信息
                    if ("cpu_usage".equals(keyString)) {
                        /* if (instance == null || instance.equals("")) */ {
                            // 单个采样值数据结构
                            MetricValue mV = new MetricValue();
                            // 设置时间和值
                            mV.setCollectTime(time1);
                            // cpu利用率需要除以100，坑啊
                            DecimalFormat df2 = new DecimalFormat("###0.00");
                            mV.setMetricValue(df2.format(Double.valueOf(value) / 100.0));
                            metricList.add(mV);
                            returnMapList.put("CPU_RATE", metricList);
                        }
                    }
                    // 内存信息
                    else if ("mem_usage".equals(keyString)) {
                        // 单个采样值数据结构
                        MetricValue mV = new MetricValue();
                        // 设置时间和值
                        mV.setCollectTime(time1);
                        // mem利用率需要除以100
                        DecimalFormat df2 = new DecimalFormat("###0.00");
                        mV.setMetricValue(df2.format(Double.valueOf(value) / 100.0));
                        metricList.add(mV);
                        returnMapList.put("MEM_RATE", metricList);
                    }
                    // 网卡信息
                    else if ("net_usage".equals(keyString)) {
                        // 单个采样值数据结构
                        MetricValue mV = new MetricValue();
                        // 设置时间和值
                        mV.setCollectTime(time1);
                        mV.setMetricValue(value);
                        metricList.add(mV);
                        returnMapList.put("NIC_AVG", metricList);

                    }
                    // 存储利用率信息
                    else if ("disk_usage".equals(keyString)) {
                        // 单个采样值数据结构
                        MetricValue mV = new MetricValue();
                        // 设置时间和值
                        mV.setCollectTime(time1);
                        mV.setMetricValue(value);
                        metricList.add(mV);
                        returnMapList.put("DSK_USAGE", metricList);
                    }
                    // 存储已用
                    else if ("disk_used".equals(keyString)) {
                        Store used = new Store();
                        used.time = time1;
                        used.store = value;
                        dUsedList.add(used);
                    }
                    // 存储容量
                    else if ("disk_capacity".equals(keyString)) {
                        Store capacity = new Store();
                        capacity.time = time1;
                        capacity.store = value;
                        dCapacityList.add(capacity);
                    }
                }
            }
        }

        if (PerfConstants.ENTITY_DS.equals(resourceType)) {
            // 具体某个指标的所有采样列表
            List<MetricValue> metricList = new ArrayList<MetricValue>();
            for (int i = 0; i < dUsedList.size(); i++) {
                String usedTime = dUsedList.get(i).getTime();
                String capacityTime = dCapacityList.get(i).getTime();
                if (usedTime.equals(capacityTime)) {
                    // 单个采样值数据结构
                    MetricValue mV = new MetricValue();
                    // 设置时间和值
                    mV.setCollectTime(usedTime);

                    String sdf = dUsedList.get(i).getStore();
                    double disk_used = Double.valueOf(dUsedList.get(i).getStore());
                    double disk_capacity = Double.valueOf(dCapacityList.get(i).getStore());
                    DecimalFormat df2 = new DecimalFormat("###0.00");
                    double usage = 0;
                    if (Math.abs(disk_capacity - 0) > 0.0001) {
                        BigDecimal diskUsageBd = new BigDecimal(disk_used / disk_capacity);
                        usage = diskUsageBd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    mV.setMetricValue(df2.format(Double.valueOf(usage)));
                    metricList.add(mV);
                    returnMapList.put("STORE_USAGE", metricList);
                }
            }
        }

        return returnMapList;
    }

    class Store {
        private String time;
        private String store;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }
    }

    private PerfEntityMetric getPerformanceContent(ServiceInstance si, PerformanceManager perfMgr, ManagedEntity entity,
            Map<Integer, String> valueKey, String historyType) throws Exception {
        int perfInterval;
        int dayNumFromNow;
        switch (historyType) {
        case PerfConstants.HISTORY_ONEDAY:
            perfInterval = 1800;
            dayNumFromNow = 1;
            break;
        case PerfConstants.HISTORY_SEVENDAY:
            perfInterval = 7200;
            dayNumFromNow = 7;
            break;
        case PerfConstants.HISTORY_ONEMONTH:
            perfInterval = 86400;
            dayNumFromNow = 30;
            break;
        case PerfConstants.HISTORY_ONEYEAR:
            perfInterval = 86400;
            dayNumFromNow = 365;
            break;
        default:
            perfInterval = 1800;
            dayNumFromNow = 1;
            break;
        }

        try {
            PerfMetricId[] perfMetricIds = perfMgr.queryAvailablePerfMetric(entity, null, null, perfInterval);

            PerfQuerySpec qSpec = new PerfQuerySpec();
            qSpec.setEntity(entity.getMOR());// 观察对象

            qSpec.setMetricId(perfMetricIds);

            // by duan
            Calendar curTime = si.currentTime();
            Calendar startTime = (Calendar) curTime.clone();
            // startTime.roll(Calendar.DATE, -1); //这是个坑，2月1号减1，变为2月31号
            startTime.set(Calendar.DATE, startTime.get(Calendar.DATE) - dayNumFromNow);
            qSpec.setStartTime(startTime);


            // 设置了这个参数不是20，setMaxSample都不管用了
            // 超出了建立虚拟机的时间，不返回-1，直接空着
            qSpec.setIntervalId(perfInterval);// 20 300 1800 7200 86400
            // qSpec.setMaxSample(1);// 最大样品数 =1，只取一个最新的
            qSpec.setFormat(PerfFormat.normal.toString());

            PerfQuerySpec[] qSpecs = new PerfQuerySpec[1];
            qSpecs[0] = qSpec;
            PerfEntityMetricBase[] values = perfMgr.queryPerf(qSpecs);

            if (values == null || values.length == 0) {
                throw new Exception("Get Realtime Proformance Data fault");
            }

            int[] idsGroup = new int[perfMetricIds.length];
            for (int i = 0; i < perfMetricIds.length; i++) {
                idsGroup[i] = perfMetricIds[i].getCounterId();
            }

            PerfCounterInfo[] pcinfolist = perfMgr.queryPerfCounter(idsGroup);
            for (int i = 0; i != pcinfolist.length; ++i) {
                int perfConterInfoKey = pcinfolist[i].key;
                String groupInfoKey = pcinfolist[i].groupInfo.key;
                String nameInfoKey = pcinfolist[i].nameInfo.key;
                valueKey.put(perfConterInfoKey, groupInfoKey + "_" + nameInfoKey);
            }
            return (PerfEntityMetric) values[0];

        } catch (Exception e) {
            throw new Exception("Get Realtime Proformance Data fault", e);
        }

    }
}
