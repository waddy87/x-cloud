package org.waddys.xcloud.vijava.vm;

import java.util.Arrays;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.Cmd;

import com.sugon.vim25.VmEvent;

public class QueryVMEvent {

    public static class QueryVMEventCmd extends Cmd<QueryVMEventAnswer> {
    	
        private static final long serialVersionUID = 653625973945794517L;
        
        private int eventSentinel;

		public int getEventSentinel() {
			return eventSentinel;
		}

		public void setEventSentinel(int eventSentinel) {
			this.eventSentinel = eventSentinel;
		}
		

		public String getToken() {
			return token;
		}

		public QueryVMEventCmd setToken(String token) {
			this.token = token;
			return this;
		}

		

		@Override
		public String toString() {
			return "QueryVMEventCmd [eventSentinel=" + eventSentinel + "]";
		}

    }

    public static class QueryVMEventAnswer extends Answer {

        private static final long serialVersionUID = -513468422130230432L;
        
        private boolean success;
        private String errMsg;
        private VmEvent[] events;
        private int eventSentinel;

        public int getEventSentinel() {
			return eventSentinel;
		}

		public void setEventSentinel(int eventSentinel) {
			this.eventSentinel = eventSentinel;
		}

		public boolean isSuccess() {
            return success;
        }

        public QueryVMEventAnswer setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public QueryVMEventAnswer setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }
        
        public VmEvent[] getEvents() {
			return events;
		}

		public void setEvents(VmEvent[] events) {
			this.events = events;
		}

		@Override
		public String toString() {
			return "QueryVMEventAnswer [success=" + success + ", errMsg=" + errMsg + ", events="
					+ Arrays.toString(events) + ", eventSentinel=" + eventSentinel + "]";
		}
    }
}
