package org.waddys.xcloud.vijava.base;

import java.io.Serializable;

public class Cmd<T extends Answer> implements Serializable {

	public String cmdDesc = this.getClass().getName();
	protected String token = "";
    private static final long serialVersionUID = 6431211130802148010L;


	@SuppressWarnings("rawtypes")
	public static void main(String args[]){
    	
    	Cmd cmd = new Cmd();
    	System.out.println(cmd.cmdDesc);
    }


	@Override
	public String toString() {
		return "Cmd [cmdDesc=" + cmdDesc + ", token=" + token + "]";
	}
	
}
