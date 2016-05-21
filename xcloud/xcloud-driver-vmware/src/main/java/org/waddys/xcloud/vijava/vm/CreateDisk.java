package org.waddys.xcloud.vijava.vm;

import org.waddys.xcloud.vijava.base.Answer;
import org.waddys.xcloud.vijava.base.Cmd;

public class CreateDisk {

    public class CreateDiskAnswer extends Answer {

        private static final long serialVersionUID = 4663529556823594599L;

    }

    public class CreateDiskCmd extends Cmd<CreateDiskAnswer> {

        private static final long serialVersionUID = 2596235660328021961L;


		public String getToken() {
			return token;
		}

		public CreateDiskCmd setToken(String token) {
			this.token = token;
			return this;
		}

		
        
    }
}
