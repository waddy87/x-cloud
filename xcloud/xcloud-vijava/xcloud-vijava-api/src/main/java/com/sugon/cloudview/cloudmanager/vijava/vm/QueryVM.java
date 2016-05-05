package com.sugon.cloudview.cloudmanager.vijava.vm;

import java.util.HashMap;
import java.util.List;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.data.VMachine;

public class QueryVM {
	
	public static enum VMTag{
		
		cloudview;
		
	}
	
    public static enum PowerState {
        poweredOn, poweredOff, suspended
    }

    public static class QueryVMCmd extends Cmd<QueryVMAnswer> {

        /**
         * 请描述变量的功能
         */
        private static final long serialVersionUID = -5513493785370953704L;

        /**
         * 虚拟机唯一标识
         */
        private String vmId;
        /**
         * 虚拟机名称
         */
        private String vmName;
        /**
         * 电源状态，合法状态包括：poweredOff、poweredOn、suspended
         * 
         */
        private PowerState powerStatus;
        /**
         * 操作系统id
         */
        private String guestOSId;
        /**
         * ip地址
         */
        private String ip;
        /**
         * 数据存储名称
         */
        private String datastoreName;
        /**
         * 网络名称
         */
        private String networkName;
        /**
         * 资源池名称
         */
        private String poolName;
        /**
         * 是否是模板，true为模板，false为虚拟机，null则为虚拟机和模板
         */
        private Boolean isTemplate;
        /**
         * 是否是例旧虚拟机
         */
        private Boolean isCloudviewVM;
        

        private String tag;

        
        public Boolean getIsCloudviewVM() {
			return isCloudviewVM;
		}

		public void setIsCloudviewVM(Boolean isCloudviewVM) {
			this.isCloudviewVM = isCloudviewVM;
			if(isCloudviewVM){
				tag = VMTag.cloudview.toString();
			}
			else{
				tag = null;
			}
		}

		public String getTag() {
			return tag;
		}

        public QueryVMCmd setTag(String tag) {
			this.tag = tag;
            return this;
		}

		public String getVmId() {
            return vmId;
        }

        public QueryVMCmd setVmId(String vmId) {
            this.vmId = vmId;
            return this;
        }

        public String getVmName() {
            return vmName;
        }

        public QueryVMCmd setVmName(String vmName) {
            this.vmName = vmName;
            return this;
        }

        public PowerState getPowerStatus() {
            return powerStatus;
        }

        public QueryVMCmd setPowerStatus(PowerState powerStatus) {
            this.powerStatus = powerStatus;
            return this;
        }

        public String getGuestOSId() {
            return guestOSId;
        }

        public QueryVMCmd setGuestOSId(String guestOSId) {
            this.guestOSId = guestOSId;
            return this;
        }

        public String getIp() {
            return ip;
        }

        public QueryVMCmd setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public String getDatastoreName() {
            return datastoreName;
        }

        public QueryVMCmd setDatastoreName(String datastoreName) {
            this.datastoreName = datastoreName;
            return this;
        }

        public String getNetworkName() {
            return networkName;
        }

        public QueryVMCmd setNetworkName(String networkName) {
            this.networkName = networkName;
            return this;
        }

        public String getPoolName() {
            return poolName;
        }

        public QueryVMCmd setPoolName(String poolName) {
            this.poolName = poolName;
            return this;
        }

        public Boolean getIsTemplate() {
            return isTemplate;
        }

        public QueryVMCmd setIsTemplate(Boolean isTemplate) {
            this.isTemplate = isTemplate;
            return this;
        }


		public String getToken() {
			return token;
		}

		public QueryVMCmd setToken(String token) {
			this.token = token;
			return this;
		}

		
        @Override
        public String toString() {
            return "QueryVMCmd [vmId=" + vmId + ", vmName=" + vmName + ", powerStatus=" + powerStatus + ", guestOSId="
                    + guestOSId + ", ip=" + ip + ", datastoreName=" + datastoreName + ", networkName=" + networkName
                    + ", poolName=" + poolName + ", isTemplate=" + isTemplate + "]";
        }


    }

    public static class QueryVMAnswer extends Answer {

        /**
         * 请描述变量的功能
         */
        private static final long serialVersionUID = 3747107941949704025L;
        private boolean success;
        private String errMsg;
        private List<VMachine> vmList;

        
        public boolean isSuccess() {
            return success;
        }

        public QueryVMAnswer setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public QueryVMAnswer setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        public List<VMachine> getVmList() {
            return vmList;
        }

        public QueryVMAnswer setVmList(List<VMachine> vmList) {
            this.vmList = vmList;
            return this;
        }

        @Override
        public String toString() {
            return "QueryVMAnswer [success=" + success + ", errMsg=" + errMsg + ", vmList=" + vmList + "]";
        }


    }
}
