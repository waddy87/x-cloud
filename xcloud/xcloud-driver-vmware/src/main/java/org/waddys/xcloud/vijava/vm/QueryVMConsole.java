package org.waddys.xcloud.vijava.vm;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.Cmd;

public class QueryVMConsole {
	
    public static class QueryVMConsoleCmd extends Cmd<QueryVMConsoleAnswer> {

        private static final long serialVersionUID = -5513493795371953704L;

        /**
         * 虚拟机唯一标识
         */
        private String vmId;
        
        /**
         * 虚拟机名称
         */
        private String vmName;

		public String getVmId() {
			return vmId;
		}

		public void setVmId(String vmId) {
			this.vmId = vmId;
		}

		public String getVmName() {
			return vmName;
		}

		public void setVmName(String vmName) {
			this.vmName = vmName;
		}

		@Override
		public String toString() {
			return "QueryVMConsoleCmd [vmId=" + vmId + ", vmName=" + vmName + "]";
		}

		public String getToken() {
			return token;
		}

		public QueryVMConsoleCmd setToken(String token) {
			this.token = token;
			return this;
		}
    }

    
    public static class QueryVMConsoleAnswer extends Answer {

        private static final long serialVersionUID = 3747107941941104025L;
        private boolean success;
        private String errMsg;
        private String cookie;
        private String consoleURL;

        public String getCookie() {
			return cookie;
		}

		public QueryVMConsoleAnswer setCookie(String cookie) {
			this.cookie = cookie;
			return this;
		}

		public boolean isSuccess() {
            return success;
        }

        public QueryVMConsoleAnswer setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public QueryVMConsoleAnswer setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

		public String getConsoleURL() {
			return consoleURL;
		}

		public QueryVMConsoleAnswer setConsoleURL(String consoleURL) {
			this.consoleURL = consoleURL;
			return this;
		}

		@Override
		public String toString() {
			return "QueryVMConsoleAnswer [success=" + success 
					+ ", errMsg=" + errMsg + ", cookie=" + cookie
					+ ", consoleURL=" + consoleURL + "]";
		}
    }
}
