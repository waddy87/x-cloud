package com.sugon.cloudview.cloudmanager.vijava.vm;


import java.util.List;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;
import com.sugon.cloudview.cloudmanager.vijava.data.VMDiskInfo;
import com.sugon.cloudview.cloudmanager.vijava.data.VMNicInfo;

public class CreateVM {

    public static class CreateVMCmd extends Cmd<CreateVMAnswer> {

        private static final long serialVersionUID = 2440134494725606627L;

        /**
         * 虚拟机名称，必须设置
         */
        private String name;

        private String passwd;
        
        /**
         * 虚拟机cpu个数，如不设置，则默认使用模板的cpu个数
         */
        private Integer cpuNum;

        /**
         * 虚拟机内存大小，单位为MB，如不设置，则默认使用模板的内存大小
         */
        private Long memSizeMB;

        /**
         * 创建虚拟机的模板对象唯一标识，必须设置
         */
        private String templateId;

        /**
         * 虚拟机将要运行的资源池对象唯一标识，如不设置，则自动选择一个资源池
         */
        private String poolId;

        /**
         * 虚拟机关联的存储对象唯一标识，如不设置，则默认使用模板关联的存储对象
         */
        private String datastoreId;

        public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}

		/**
         * 需新增的网卡信息
         */
        private List<VMNicInfo> nicInfos;

        /**
         * 需新增的磁盘信息
         */
        private List<VMDiskInfo> diskInfos;

        private String userName;

        private String pwd;

        public String getName() {
            return name;
        }

        public CreateVMCmd setName(String name) {
            this.name = name;
            return this;
        }

        public Integer getCpuNum() {
            return cpuNum;
        }

        public CreateVMCmd setCpuNum(Integer cpuNum) {
            this.cpuNum = cpuNum;
            return this;
        }

        public Long getMemSizeMB() {
            return memSizeMB;
        }

        public CreateVMCmd setMemSizeMB(Long memSizeMB) {
            this.memSizeMB = memSizeMB;
            return this;
        }

        public String getTemplateId() {
            return templateId;
        }

        public CreateVMCmd setTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public String getPoolId() {
            return poolId;
        }

        public CreateVMCmd setPoolId(String poolId) {
            this.poolId = poolId;
            return this;
        }


        public String getDatastoreId() {
            return datastoreId;
        }

        public CreateVMCmd setDatastoreId(String datastoreId) {
            this.datastoreId = datastoreId;
            return this;
        }

        public List<VMNicInfo> getNicInfos() {
            return nicInfos;
        }

        public CreateVMCmd setNicInfos(List<VMNicInfo> nicInfos) {
            this.nicInfos = nicInfos;
            return this;
        }

        public List<VMDiskInfo> getDiskInfos() {
            return diskInfos;
        }

        public CreateVMCmd setDiskInfos(List<VMDiskInfo> diskInfos) {
            this.diskInfos = diskInfos;
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

		public CreateVMCmd setToken(String token) {
			this.token = token;
			return this;
		}

		
        
        @Override
		public String toString() {
			return "CreateVMCmd [name=" + name + ", passwd=" + passwd + ", cpuNum=" + cpuNum + ", memSizeMB="
					+ memSizeMB + ", templateId=" + templateId + ", poolId=" + poolId + ", datastoreId=" + datastoreId
					+ ", nicInfos=" + nicInfos + ", diskInfos=" + diskInfos 
					+ ", userName=" + userName + ", pwd=" + pwd + "]";
		}


    }

    public static class CreateVMAnswer extends Answer {

        private static final long serialVersionUID = -5028635694711875492L;

        /**
         * 虚拟机的管理对象唯一标识
         */
        private String vmId;

        /**
         * 虚拟机的名称
         */
        private String name;
        
        private String passwd;

        private String taskId;
        
        /**
         * 操作成功
         */
        private boolean success;
        
        /**
         * 需新增的网卡信息
         */
        private List<VMNicInfo> nicInfos;

        /**
         * 需新增的磁盘信息
         */
        private List<VMDiskInfo> diskInfos;

        public String getVmId() {
            return vmId;
        }

        public CreateVMAnswer setVmId(String vmId) {
            this.vmId = vmId;
            return this;
        }

        public String getName() {
            return name;
        }

        public CreateVMAnswer setName(String name) {
            this.name = name;
            return this;
        }

        public String getTaskId() {
            return taskId;
        }

        public CreateVMAnswer setTaskId(String taskId) {
            this.taskId = taskId;
            return this;
        }
        

        public boolean isSuccess() {
			return success;
		}

		public CreateVMAnswer setSuccess(boolean success) {
			this.success = success;
			return this;
		}

		public String getPasswd() {
			return passwd;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
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
			return "CreateVMAnswer [vmId=" + vmId + ", name=" + name + ", passwd=" + passwd + ", taskId=" + taskId
					+ ", success=" + success + ", nicInfos=" + nicInfos + ", diskInfos=" + diskInfos + "]";
		}
    }
}
