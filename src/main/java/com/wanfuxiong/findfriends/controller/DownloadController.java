package com.wanfuxiong.findfriends.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

@RestController
public class DownloadController {

    //下载文件的方法
    @RequestMapping("/downloadFile")
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取文件的绝对路径
        File directory = new File("/home/Find Friends V1.0.0.apk");//设定为当前文件夹
        // File directory = new File("/Users/fx_wan/Find Friends V1.0.0.apk");//设定为当前文件夹
        String parentDir = directory.getAbsolutePath().substring(0, directory.getAbsolutePath().lastIndexOf("/"));

        // System.out.println("parentDir:" + parentDir);
        //获取输入流对象（用于读文件）
        FileInputStream fis = new FileInputStream(new File(parentDir, fileName));
        //获取文件后缀
        String extendFileName = fileName.substring(fileName.lastIndexOf('.'));
        //动态设置响应类型，根据前台传递文件类型设置响应类型
        response.setContentType(request.getSession().getServletContext().getMimeType(extendFileName));
        //设置响应头,attachment表示以附件的形式下载，inline表示在线打开
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        //获取输出流对象（用于写文件）
        ServletOutputStream os = response.getOutputStream();
        //下载文件,使用spring框架中的FileCopyUtils工具
        FileCopyUtils.copy(fis, os);
    }


}
