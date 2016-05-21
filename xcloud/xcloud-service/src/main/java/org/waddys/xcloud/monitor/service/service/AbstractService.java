package org.waddys.xcloud.monitor.service.service;

import net.sf.json.JSONObject;

public interface AbstractService {

    public JSONObject getAllHostsPerformInfo();

    public JSONObject getAllStoragePerformInfo();

    public JSONObject getAllVMPerformInfo();

    public JSONObject getAllClusterPerformInfo();

}
