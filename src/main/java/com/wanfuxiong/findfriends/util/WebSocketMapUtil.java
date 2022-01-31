package com.wanfuxiong.findfriends.util;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// 保存在线客户端的Map操作类
public class WebSocketMapUtil {

    private static ConcurrentMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    // 加入连接
    public static void put (String key, Session session){
        sessionMap.put(key, session);
    }

    // 获取连接
    public static Session get (String key){
        return sessionMap.get(key);
    }

    // 移除连接
    public static void remove (String key){
        sessionMap.remove(key);
    }

    // 获取map所有值
    public static Collection getAllValues (){
        return sessionMap.values();
    }

    // 获取map所有的key
    public static List<String> getAllKey(){
        return new ArrayList<>(sessionMap.keySet());
    }
}
