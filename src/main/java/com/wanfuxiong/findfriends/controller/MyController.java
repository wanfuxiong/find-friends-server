package com.wanfuxiong.findfriends.controller;

import com.wanfuxiong.findfriends.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.math.BigDecimal;

@Controller
public class MyController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/")
    public String home(Model model) {
        // model.addAttribute("userAmount", userMapper.countUsers());
        // long fileLength = new File("/home/Find Friends V1.0.0.apk").length();
        // model.addAttribute("apkSizeLong", fileLength);
        // model.addAttribute("apkSize", BigDecimal.valueOf((float) fileLength / 1000000).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + " MB");
        // model.addAttribute("apkSize", "Android 6.0+ "+BigDecimal.valueOf((float) fileLength / 1048576).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + " MB");
        return "index";
    }

}
