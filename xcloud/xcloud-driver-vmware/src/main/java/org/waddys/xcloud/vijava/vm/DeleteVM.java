package org.waddys.xcloud.vijava.vm;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.Cmd;

public class DeleteVM {

    public static class DeleteVMCmd extends Cmd<DeleteVMAnswer> {

        private static final long serialVersionUID = 6536259739545694517L;
        /**
         * 要删除的虚拟机管理对象唯一标识
         */
        private String vmId;

        public String getVmId() {
            return vmId;
        }

        public DeleteVMCmd setVmId(String vmId) {
            this.vmId = vmId;
            return this;
        }

        public String getToken() {
        	return token;
        }
        
        public DeleteVMCmd setToken(String token) {
        	this.token = token;
        	return this;
        }
        
        @Override
        public String toString() {
            return "DeleteVMCmd [vmId=" + vmId + "]";
        }

    }

    public static class DeleteVMAnswer extends Answer {

        private static final long serialVersionUID = -513468421130130432L;
        private boolean success;
        private String errMsg;
        private String taskId;

        public boolean isSuccess() {
            return success;
        }

        public DeleteVMAnswer setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public DeleteVMAnswer setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        
        public String getTaskId() {
			return taskId;
		}

		public DeleteVMAnswer setTaskId(String taskId) {
			this.taskId = taskId;
			return this;
		}

		@Override
        public String toString() {
            return "DeleteVMAnswer [success=" + success + ", errMsg=" + errMsg + "]";
        }

    }

}
