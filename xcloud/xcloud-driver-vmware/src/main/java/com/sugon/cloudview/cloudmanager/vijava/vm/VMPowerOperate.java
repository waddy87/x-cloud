package com.sugon.cloudview.cloudmanager.vijava.vm;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;

public class VMPowerOperate {
	
	public static enum PowerOPType{
		powerOn,
		powerOff,
		restart,
		suspend;
	}



    public static class VMPowerOpAnswer extends Answer {

        private static final long serialVersionUID = -5720299620344294595L;

        /**
         * 操作结果，true为成功，false为失败
         */
        private boolean success;

        /**
         * 操作结果为失败时，失败的原因
         */
        private String errMsg;

        private String taskId;

        public boolean isSuccess() {
            return success;
        }

        public VMPowerOpAnswer setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public VMPowerOpAnswer setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        public String getTaskId() {
            return taskId;
        }

        public VMPowerOpAnswer setTaskId(String taskId) {
            this.taskId = taskId;
            return this;
        }

        @Override
        public String toString() {
            return "VMPowerOpAnswer [success=" + success + ", errMsg=" + errMsg + ", taskId=" + taskId + "]";
        }


    }

    public static class VMPowerOpCmd extends Cmd<VMPowerOpAnswer> {

        private static final long serialVersionUID = -6769564556817773574L;
        /**
         * 要操作的虚拟机管理对象唯一标识
         */
        private String vmId;

        /**
         * 操作类型，合法值有： powerOn、powerOff、restart、suspend
         */
        private PowerOPType opType;

        public String getVmId() {
            return vmId;
        }

        public VMPowerOpCmd setVmId(String vmId) {
            this.vmId = vmId;
            return this;
        }

        public PowerOPType getOpType() {
            return opType;
        }

        public VMPowerOpCmd setOpType(PowerOPType opType) {
            this.opType = opType;
            return this;
        }
        

		public String getToken() {
			return token;
		}

		public VMPowerOpCmd setToken(String token) {
			this.token = token;
			return this;
		}

		

        @Override
        public String toString() {
            return "VMPowerOpCmd [vmId=" + vmId + ", opType=" + opType + "]";
        }


    }
}
