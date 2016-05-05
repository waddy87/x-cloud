package com.sugon.cloudview.cloudmanager.vijava.vm;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;

public class AttachDisk {

    public static class AttachDiskCmd extends Cmd<AttachDiskAnswer> {

        private static final long serialVersionUID = 3247903389842123798L;
        

		public String getToken() {
			return token;
		}

		public AttachDiskCmd setToken(String token) {
			this.token = token;
			return this;
		}

		
    }

    public static class AttachDiskAnswer extends Answer {

        private static final long serialVersionUID = 7522631344731908050L;

    }
}
