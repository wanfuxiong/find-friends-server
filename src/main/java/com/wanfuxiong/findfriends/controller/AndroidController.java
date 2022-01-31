package com.wanfuxiong.findfriends.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wanfuxiong.findfriends.pojo.Moment;
import com.wanfuxiong.findfriends.pojo.User;
import com.wanfuxiong.findfriends.service.AndroidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class AndroidController {

    @Autowired
    AndroidService androidService;

    @PostMapping(value = "/login")
    public Map<String, Object> login(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String password = request.getParameter("password");
        return androidService.login(id, password);
    }

    @PostMapping(value = "/register")
    public Map<String, Object> register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int sex = Integer.parseInt(request.getParameter("sex"));
        String interest = request.getParameter("interest");
        return androidService.register(username, password, sex, interest);
    }

    @PostMapping(value = "/getUserInfo")
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        return androidService.getUserInfo(id);
    }

    @PostMapping(value = "/saveUserInfo", produces = "application/json;charset=UTF-8")
    public Map<String, Object> saveUserInfo(@RequestBody JSONObject jsonObject) {
        User user = JSON.toJavaObject(jsonObject, User.class);
        return androidService.saveUserInfo(user);
    }

    @PostMapping(value = "/getUserListByInterestAndID")
    public Map<String, Object> getUserListByInterestAndID(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String interest = request.getParameter("interest");
        return androidService.getUserListByInterestAndID(interest, id);
    }

    @PostMapping(value = "/getSimpleUserList")
    public Map<String, Object> getSimpleUserList(HttpServletRequest request) {
        return androidService.getSimpleUserList(50);
    }

    @PostMapping(value = "/getRandomUser")
    public Map<String, Object> getRandomUser(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int sex = Integer.parseInt(request.getParameter("sex"));
        String interest = request.getParameter("interest");
        return androidService.getRandomUser(id, sex, interest);
    }

    @PostMapping(value = "/searchUserByID")
    public Map<String, Object> searchUserByID(HttpServletRequest request) {
        int searchID = Integer.parseInt(request.getParameter("search_id"));
        int currentID = Integer.parseInt(request.getParameter("current_id"));
        return androidService.searchUserByID(searchID, currentID);
    }

    @PostMapping(value = "/changePassword")
    public Map<String, Object> changePassword(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        String originPassword = request.getParameter("original_password");
        String newPassword = request.getParameter("new_password");
        return androidService.changePassword(id, originPassword, newPassword);
    }

    @PostMapping(value = "/closeAccount")
    public Map<String, Object> closeAccount(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        return androidService.closeAccount(id);
    }

    @PostMapping(value = "/getMomentsByID")
    public Map<String, Object> getSelfMoment(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        return androidService.getMomentsByID(id);
    }

    @PostMapping(value = "/sendMoment", produces = "application/json;charset=UTF-8")
    public Map<String, Object> sendMoment(@RequestBody JSONObject jsonObject) {
        Moment moment = JSON.toJavaObject(jsonObject, Moment.class);
        return androidService.sendMoment(moment);
    }
}
