package org.waddys.xcloud.res.serviceImpl.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.waddys.xcloud.res.bo.venv.VenvConfig;
import org.waddys.xcloud.res.bo.vnet.NetPool;
import org.waddys.xcloud.res.po.entity.venv.VenvConfigE;
import org.waddys.xcloud.res.po.entity.vnet.NetPoolE;

public class Converters {

    private static Logger Logger = LoggerFactory.getLogger(Converters.class);

    public static Converter<VenvConfigE, VenvConfig> converter = new Converter<VenvConfigE, VenvConfig>() {
        @Override
        public VenvConfig convert(VenvConfigE source) {
            VenvConfig venvConfig = new VenvConfig();
            try {
                BeanUtils.copyProperties(venvConfig, source);
            } catch (IllegalAccessException | InvocationTargetException e) {
                Logger.error("error", e);
            }
            return venvConfig;
        }
    };
    
    public static Converter<VenvConfig, VenvConfigE> venvConvertDB = new Converter<VenvConfig, VenvConfigE>() {
    	@Override
    	public VenvConfigE convert(VenvConfig source) {
    		VenvConfigE venvConfigE = new VenvConfigE();
    		try {
    			BeanUtils.copyProperties(venvConfigE, source);
    		} catch (IllegalAccessException | InvocationTargetException e) {
    			Logger.error("error", e);
    		}
    		return venvConfigE;
    	}
    };
    
    public static Converter<VenvConfigE, VenvConfig> venvConvertBusness = new Converter<VenvConfigE, VenvConfig>() {
        @Override
        public VenvConfig convert(VenvConfigE source) {
            VenvConfig venvConfig = new VenvConfig();
            try {
                BeanUtils.copyProperties(venvConfig, source);
            } catch (IllegalAccessException | InvocationTargetException e) {
                Logger.error("error", e);
            }
            return venvConfig;
        }
    };
    
    public static Converter<NetPoolE, NetPool> vnetConvertBusness = new Converter<NetPoolE, NetPool>(){

		@Override
		public NetPool convert(NetPoolE source) {
			NetPool netPool = new NetPool();
			try {
				BeanUtils.copyProperties(netPool, source);
			} catch (IllegalAccessException e) {
				Logger.error("convert For busness obj IllegalAccessException :" + e.getMessage(), e);
			} catch (InvocationTargetException e) {
				Logger.error("convert For busness obj InvocationTargetException :" + e.getMessage(), e);
			}
			return netPool;
		}
    	
    };
    
    public static Converter<NetPool, NetPoolE> vnetConvertDataBase = new Converter<NetPool, NetPoolE>(){
    	
    	@Override
    	public NetPoolE convert(NetPool source) {
    		NetPoolE netPoolE = new NetPoolE();
    		try {
    			BeanUtils.copyProperties(netPoolE, source);
    		} catch (IllegalAccessException e) {
    			Logger.error("convert For DB obj IllegalAccessException :" + e.getMessage(), e);
    		} catch (InvocationTargetException e) {
    			Logger.error("convert For DB obj InvocationTargetException :" + e.getMessage(), e);
    		}
    		return netPoolE;
    	}
    	
    };
    
}
