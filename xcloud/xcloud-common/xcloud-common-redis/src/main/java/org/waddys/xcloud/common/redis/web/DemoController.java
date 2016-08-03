package org.waddys.xcloud.common.redis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.waddys.xcloud.common.redis.entity.Address;
import org.waddys.xcloud.common.redis.entity.User;
import org.waddys.xcloud.common.redis.service.DemoService;

/**
 * Created by wisely on 2015/5/25.
 */

@Controller
public class DemoController {

    @Autowired
    DemoService demoService;

    @RequestMapping("/test")
    @ResponseBody
    public String putCache(){
        demoService.findUser(1l,"wang","yunfei");
        demoService.findAddress(1l,"anhui","hefei");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return "ok";
    }
    @RequestMapping("/test2")
    @ResponseBody
    public String testCache(){
        User user = demoService.findUser(1l,"wang","yunfei");
        Address address =demoService.findAddress(1l,"anhui","hefei");
        System.out.println("我这里没执行查询");
        System.out.println("user:"+"/"+user.getFirstName()+"/"+user.getLastName());
        System.out.println("address:"+"/"+address.getProvince()+"/"+address.getCity());
        return "ok";
    }
}
