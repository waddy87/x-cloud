package com.sugon.cloudview.cloudmanager.vijava.vm;

import com.sugon.cloudview.cloudmanager.vijava.base.Answer;
import com.sugon.cloudview.cloudmanager.vijava.base.Cmd;

public class ConfigureNetwork {

    public class ConfigureNetworkAnswer extends Answer {

        private static final long serialVersionUID = -4311872018442697528L;

    }

    public class ConfigureNetworkCmd extends Cmd<ConfigureNetworkAnswer> {

        private static final long serialVersionUID = -9060780127024180055L;


		public String getToken() {
			return token;
		}

		public ConfigureNetworkCmd setToken(String token) {
			this.token = token;
			return this;
		}

		
    }
}
