package org.waddys.xcloud.common.redis.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.common.redis.entity.Address;
import org.waddys.xcloud.common.redis.entity.User;

/**
 * Created by wisely on 2015/5/25.
 */
@Service
public class DemoService {
	
    @Cacheable(value = "usercache",keyGenerator = "sampleKeyGenerator")
    public User findUser(Long id,String firstName,String lastName){
        System.out.println("无缓存的时候调用这里");
        return new User(id,firstName,lastName);
    }
    
    @Cacheable(value = "addresscache",keyGenerator = "sampleKeyGenerator")
    public Address findAddress(Long id,String province,String city){
        System.out.println("无缓存的时候调用这里");
        return new Address(id,province,city);
    }
}
