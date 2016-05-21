package org.waddys.xcloud.log.demo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.waddys.xcloud.log.api.Log;

@Controller  
public class OperatorLogDemo{  
	
	private final static Logger logger = LoggerFactory.getLogger(OperatorLogDemo.class);
	
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    @Log(message="log message", operationType ="startVM")
    public String logCase() throws Exception {
    	
        return getData();
    }  
    
    public String getData(){
    	String s = "Proccess UserController; The Date is  " +  new Date();
        logger.error(s);
        return s;
    }
}  

