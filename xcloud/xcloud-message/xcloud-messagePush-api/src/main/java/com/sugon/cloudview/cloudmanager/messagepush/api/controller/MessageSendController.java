/**
 * 
 */
package com.sugon.cloudview.cloudmanager.messagepush.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sugon.cloudview.cloudmanager.messagepush.serviceImpl.serviceImpl.BaseMessageManageQueryServiceImpl;

import net.sf.json.JSONObject;

/**
 * @author 张浩然
 * @version 创建时间： 2016年3月30日
 */
@Controller
@RequestMapping("/messagePush")
public class MessageSendController {

    @Autowired
    private BaseMessageManageQueryServiceImpl baseMessageManageQueryServiceImpl;

    // 前台请求路径
    // @RequestMapping(method = RequestMethod.GET, value = "/unMandateIndex")
    @RequestMapping(method = RequestMethod.GET, value = "/messageIndex")
    public ModelAndView manDateIndex() {
        // 返回的jsp页面路径
        // return new ModelAndView("/managedVMManagement/unMandateIndex");
        return new ModelAndView("/alert/messageIndex");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list", produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    @Transactional(readOnly = true)
    public String getMessageSend(@RequestParam(value = "name", required = false) String name, @RequestParam int page,
            @RequestParam int rows) {

        if (name == null) {
            name = "";
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("total", Long.valueOf(baseMessageManageQueryServiceImpl.countAll()));
        jsonObject.put("rows", baseMessageManageQueryServiceImpl.ListMailObjectMessage(name, page, rows));

        return jsonObject + "";
    }
}
