package org.waddys.xcloud.web.controller.host;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HostController {

    private static final Logger logger = LoggerFactory.getLogger(HostController.class);

    @RequestMapping(value = "/resources/hosthello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        logger.debug("host controller");
        logger.info("host controller");
        return String.valueOf(new Random().nextDouble());
    }

    @RequestMapping(value = "/resources/hostListByStatus", produces = {
            "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public String hostListByStatus(@RequestParam(value = "status", required = true) String status,
            @RequestParam(value = "pageSize", required = true) String pageSize,
            @RequestParam(value = "pageNum", required = true) String pageNum, HttpServletRequest request,
            HttpSession session, HttpServletResponse response) {
        System.out.println("vmStatus为" + status);
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        for (int i = 0; i < Integer.parseInt(pageSize); i++) {
            Integer j = i + (Integer.parseInt(pageNum) - 1) * Integer.parseInt(pageSize);
            jo.put("hostId", j);
            jo.put("index", j);
            jo.put("name", "主机" + j);
            jo.put("author", "Mr.Fan");
            jo.put("pubTime", "2015-01-02");
            ja.put(jo);
        }
        jo = new JSONObject();
        jo.put("hostList", ja);
        jo.put("totalNum", 100);
        logger.debug("xxx");
        return jo.toString();
    }

    @RequestMapping(value = "/resources/getHostTypeList", produces = {
            "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    @ResponseBody
    public String getHostTypeList(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        String[] titles = { "100019", "100020 ", "100021", "100022", "100023" };
        for (int i = 0; i < titles.length; i++) {
            jo.put("title", titles[i]);
            jo.put("status", i);
            ja.put(jo);
        }
        return ja.toString();
    }

    @RequestMapping(value = "/resources/getCreaterList", produces = {
            "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public String getCreaterList(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        String[] titles = { "Mr.Fan", "Mr.Zeng", "Mr.Sun", "Miss.Ci", "Mr.Pan", "Miss.Lv" };
        for (int i = 0; i < titles.length; i++) {
            jo.put("name", titles[i]);
            jo.put("id", i);
            ja.put(jo);
        }
        return ja.toString();
    }

    @RequestMapping(value = "/resources/addHost", produces = {
            "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public String addHost(HttpServletRequest request, HttpSession session, HttpServletResponse response,
            @RequestParam(value = "hostName", required = true) String hostName,
            @RequestParam(value = "maxVmNum", required = true) Integer maxVmNum,
            @RequestParam(value = "imigration", required = true) Boolean imigration,
            @RequestParam(value = "creator", required = true) String creator) {
        JSONObject jo = new JSONObject();
        if (imigration) {
            jo.put("flag", false);
            jo.put("message", "系统暂时不支持自动迁移");
        } else {
            jo.put("flag", true);
        }
        return jo.toString();
    }

    @RequestMapping(value = "/resources/updateHost", produces = {
            "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public String updateHost(HttpServletRequest request, HttpSession session, HttpServletResponse response,
            @RequestParam(value = "hostName", required = true) String hostName,
            @RequestParam(value = "maxVmNum", required = true) Integer maxVmNum,
            @RequestParam(value = "imigration", required = true) Boolean imigration,
            @RequestParam(value = "creator", required = true) String creator) {
        JSONObject jo = new JSONObject();
        if (imigration) {
            jo.put("flag", false);
            jo.put("message", "系统暂时不支持自动迁移");
        } else {
            jo.put("flag", true);
        }
        return jo.toString();
    }

    @RequestMapping(value = "/resources/deleteHost", produces = {
            "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public String deleteHost(HttpServletRequest request, HttpSession session, HttpServletResponse response,
            @RequestParam(value = "hostId", required = true) Integer hostId

    ) {
        JSONObject jo = new JSONObject();
        jo.put("flag", true);
        return jo.toString();
    }

    @RequestMapping(value = "/resources/getHostDetailById", produces = {
            "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    @ResponseBody
    public String getHostDetailById(HttpServletRequest request, HttpSession session, HttpServletResponse response,
            @RequestParam(value = "hostId", required = true) Integer hostId

    ) {
        JSONObject jo = new JSONObject();
        jo.put("hostName", "主机1");
        jo.put("creater", 2);
        jo.put("maxVmNum", 20);
        jo.put("imigation", true);
        return jo.toString();
    }
}
