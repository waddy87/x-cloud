package com.sugon.cloudview.cloudmanager.vijava.vm;


import java.util.List;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.data.VMDiskInfo;
import com.sugon.cloudview.cloudmanager.vijava.data.VMNetworkInfo;
import com.sugon.cloudview.cloudmanager.vijava.data.VMNicInfo;

public class ReconfigVM {

    public static class ReconfigVMCmd extends Cmd<ReconfigVMAnswer> {

        private static final long serialVersionUID = 2440134494725606627L;

        /**
         * 虚拟机名称，必须设置
         */
        private String name;

        /**
         * 虚拟机cpu个数，如不设置，则默认使用模板的cpu个数
         */
        private Integer cpuNum;

        /**
         * 虚拟机内存大小，单位为MB，如不设置，则默认使用模板的内存大小
         */
        private Long memSizeMB;

        /**
         * 创建虚拟机对象的唯一标识，必须设置
         */
        private String vmId;

        /**
         * 虚拟机将要运行的资源池对象唯一标识，如不设置，则自动选择一个资源池
         */
        private String poolId;

        /**
         * 虚拟机关联的存储对象唯一标识，如不设置，则默认使用模板关联的存储对象
         */
        private String datastoreId;
        
        
        /**
         * 初始化密码
         */
        private String currentPassword;
        
        /**
         * 新密码
         */
        private String newPassword;

        /**
         * 需新增的网卡信息
         */
        private List<VMNicInfo> nicInfos;

        /**
         * 需新增的磁盘信息
         */
        private List<VMDiskInfo> diskInfos;

        /**
         * ip信息
         */
        private List<VMNetworkInfo> networkInfos;

        private String userName;

        private String pwd;

        public String getName() {
            return name;
        }

        public ReconfigVMCmd setName(String name) {
            this.name = name;
            return this;
        }

        public Integer getCpuNum() {
            return cpuNum;
        }

        public ReconfigVMCmd setCpuNum(Integer cpuNum) {
            this.cpuNum = cpuNum;
            return this;
        }

        public Long getMemSizeMB() {
            return memSizeMB;
        }

        public ReconfigVMCmd setMemSizeMB(Long memSizeMB) {
            this.memSizeMB = memSizeMB;
            return this;
        }

        public String getVmId() {
			return vmId;
		}

		public void setVmId(String vmId) {
			this.vmId = vmId;
		}

		public String getPoolId() {
            return poolId;
        }

        public ReconfigVMCmd setPoolId(String poolId) {
            this.poolId = poolId;
            return this;
        }


        public String getDatastoreId() {
            return datastoreId;
        }

        public ReconfigVMCmd setDatastoreId(String datastoreId) {
            this.datastoreId = datastoreId;
            return this;
        }

        public List<VMNicInfo> getNicInfos() {
            return nicInfos;
        }

        public ReconfigVMCmd setNicInfos(List<VMNicInfo> nicInfos) {
            this.nicInfos = nicInfos;
            return this;
        }

        public List<VMDiskInfo> getDiskInfos() {
            return diskInfos;
        }

        public ReconfigVMCmd setDiskInfos(List<VMDiskInfo> diskInfos) {
            this.diskInfos = diskInfos;
            return this;
        }

        public List<VMNetworkInfo> getNetworkInfos() {
            return networkInfos;
        }

        public ReconfigVMCmd setNetworkInfos(List<VMNetworkInfo> networkInfos) {
            this.networkInfos = networkInfos;
            return this;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }


		public String getToken() {
			return token;
		}

		public ReconfigVMCmd setToken(String token) {
			this.token = token;
			return this;
		}
		
        public String getCurrentPassword() {
			return currentPassword;
		}

		public void setCurrentPassword(String currentPassword) {
			this.currentPassword = currentPassword;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}

		@Override
		public String toString() {
			return "ReconfigVMCmd [name=" + name + ", cpuNum=" + cpuNum + ", memSizeMB=" + memSizeMB + ", vmId=" + vmId
					+ ", poolId=" + poolId + ", datastoreId=" + datastoreId + ", currentPassword=" + currentPassword
					+ ", newPassword=" + newPassword + ", nicInfos=" + nicInfos + ", diskInfos=" + diskInfos
					+ ", networkInfos=" + networkInfos + ", userName=" + userName + ", pwd=" + pwd + "]";
		}


    }

    public static class ReconfigVMAnswer extends Answer {

        private static final long serialVersionUID = -5028635694711875492L;

        /**
         * 虚拟机的管理对象唯一标识
         */
        private String vmId;
        
        /**
         * 虚拟机新密码
         */
        private String newPasswd;

        /**
         * 虚拟机的名称
         */
        private String name;
        /**
         * 操作成功
         */
        private boolean success;

        public String getVmId() {
            return vmId;
        }
        
        
        public String getNewPasswd() {
			return newPasswd;
		}

		public ReconfigVMAnswer setNewPasswd(String newPasswd) {
			this.newPasswd = newPasswd;
			return this;
		}

		/**
         * 需新增的网卡信息
         */
        private List<VMNicInfo> nicInfos;

        /**
         * 需新增的磁盘信息
         */
        private List<VMDiskInfo> diskInfos;

        public ReconfigVMAnswer setVmId(String vmId) {
            this.vmId = vmId;
            return this;
        }

        public String getName() {
            return name;
        }

        public ReconfigVMAnswer setName(String name) {
            this.name = name;
            return this;
        }

        public boolean isSuccess() {
			return success;
		}

		public ReconfigVMAnswer setSuccess(boolean success) {
			this.success = success;
			return this;
		}

		public List<VMNicInfo> getNicInfos() {
			return nicInfos;
		}

		public void setNicInfos(List<VMNicInfo> nicInfos) {
			this.nicInfos = nicInfos;
		}

		public List<VMDiskInfo> getDiskInfos() {
			return diskInfos;
		}

		public void setDiskInfos(List<VMDiskInfo> diskInfos) {
			this.diskInfos = diskInfos;
		}

		@Override
		public String toString() {
			return "ReconfigVMAnswer [vmId=" + vmId + ", name=" + name 
					+ ", taskId=" + this.getTaskId() +", taskName=" + this.getTaskName() + ", success=" + success
					+ ", nicInfos=" + nicInfos + ", diskInfos=" + diskInfos + "]";
		}


    }

}
