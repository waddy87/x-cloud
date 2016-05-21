package org.waddys.xcloud.vijava.vm;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.Cmd;

public class MigrateVM {
    public static class MigrateVMCmd extends Cmd<MigrateVMAnswer> {
        private static final long serialVersionUID = -2732401039723481707L;
        /**
         * 要迁移的虚拟机的管理对象唯一标示
         */
        String vmId;
        /**
         * 虚拟机要运行的资源池，可不设置，如果设置了资源池和/或主机，则进行计算资源迁移
         */
        String poolId;
        /**
         * 虚拟机要运行的主机，可不设置
         */
        String hostId;

        public String getVmId() {
            return vmId;
        }

        public MigrateVMCmd setVmId(String vmId) {
            this.vmId = vmId;
            return this;
        }

        public String getPoolId() {
            return poolId;
        }

        public MigrateVMCmd setPoolId(String poolId) {
            this.poolId = poolId;
            return this;
        }

        public String getHostId() {
            return hostId;
        }

        public MigrateVMCmd setHostId(String hostId) {
            this.hostId = hostId;
            return this;
        }
        

		public String getToken() {
			return token;
		}

		public MigrateVMCmd setToken(String token) {
			this.token = token;
			return this;
		}

		

        @Override
        public String toString() {
            return "MigrateVMCmd [vmId=" + vmId + ", poolId=" + poolId + ", hostId=" + hostId + "]";
        }

    }

    public static class MigrateVMAnswer extends Answer {
        private static final long serialVersionUID = 5896692212535040536L;

        private boolean success;

        private String errMsg;

        public Boolean getSuccess() {
            return success;
        }

        public MigrateVMAnswer setSuccess(Boolean success) {
            this.success = success;
            return this;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public MigrateVMAnswer setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        @Override
        public String toString() {
            return "MigrateVMAnswer [success=" + success + ", errMsg=" + errMsg + "]";
        }

    }
}
