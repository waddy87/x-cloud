package org.waddys.xcloud.vijava.vm;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.Cmd;

public class DetachDisk {

    public class DetachDiskAnswer extends Answer {

        private static final long serialVersionUID = -6640755163415130481L;

    }

    public class DetachDiskCmd extends Cmd<DetachDiskAnswer> {

        private static final long serialVersionUID = 5807637906928665009L;


		public String getToken() {
			return token;
		}

		public DetachDiskCmd setToken(String token) {
			this.token = token;
			return this;
		}

		
    }

}
