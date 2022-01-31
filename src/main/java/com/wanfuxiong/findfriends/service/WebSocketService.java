package com.wanfuxiong.findfriends.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wanfuxiong.findfriends.pojo.Message;
import com.wanfuxiong.findfriends.util.WebSocketMapUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketService {

    // 存离线消息
    private static ConcurrentMap<String, Map<String, List<Message>>> messageMap = new ConcurrentHashMap<>();

    private Session session;

    // 用户唯一id
    private String userId;

    // 在线人数计数
    private static int count;

    /*
     * userID和Session绑定
     * 给客户端发送消息需要session
     * */

    // 有连接时回调
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        WebSocketMapUtil.put(userId, session);
        count++;
        // 向所有用户发送当前在线用户数量
        // sendOnLine();
        // new Thread(new Runnable() {
        //     @SneakyThrows
        //     @Override
        //     public void run() {
        //         // 延迟一下，不然安卓fragment反应不过来
        //         Thread.sleep(500);
        //         sendOnLine();
        //     }
        // }).start();
        if (messageMap.get(String.valueOf(userId)) != null) {
            // 说明该用户在离线时有人曾给他发消息
            Map<String, List<Message>> map = messageMap.get(String.valueOf(userId));
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();//键
                List<Message> list = map.get(key);
                if (list != null) {
                    for (Message message : list) {
                        // JSONObject rs = new JSONObject();
                        // rs.put("toUserID", userId);
                        // rs.put("fromUserID", key);
                        // rs.put("messageData", message.getMessageData());
                        // rs.put("date", null);
                        // onMessage(rs.toString(),session);
                        sendMessage(message, session);
                    }
                }
                iterator.remove();
            }
        }
    }

    // 断开连接时回调
    @OnClose
    public void onClose(@PathParam("userId") String userId) {
        // 移除关闭的连接
        WebSocketMapUtil.remove(userId);
        count--;
        sendOnLine();
        // sendOnLine(userId, "out");
    }

    // 发生错误时回调
    @OnError
    public void onError(@PathParam("userId") String userId, Throwable error) {
        WebSocketMapUtil.remove(userId);
        // count--;
        sendOnLine();
        // error.printStackTrace();
    }

    // 收到客户端发过来的消息时回调
    @OnMessage
    public void onMessage(String messageJSON, Session fromSession) {
        Message message = JSONArray.parseObject(messageJSON, Message.class);

        // 接收者的Session
        Session toSession = WebSocketMapUtil.get(String.valueOf(message.getToUserID()));
        // 判断接收者是否在线
        if (toSession != null) {
            // 传达消息
            sendMessage(message, toSession);// 把消息传给收件人
            sendMessage(message, fromSession);//把消息回传给发件人
        } else {
            saveMessage(message);
            sendMessage(message, fromSession);//把消息回传给发件人
        }
    }

    private void saveMessage(Message message) {
        if (messageMap.get(message.getToUserID().toString()) == null) {
            // 从未有人给该用户发过离线消息
            Map<String, List<Message>> map = new HashMap<>();
            List<Message> messages = new ArrayList<>();//该用户发的离线消息的集合
            messages.add(message);
            map.put(String.valueOf(message.getFromUserID()), messages);
            messageMap.put(String.valueOf(message.getToUserID()), map);
        } else {
            // 别的用户不止一次给他发消息，但他目前仍然是离线状态
            Map<String, List<Message>> map = messageMap.get(message.getToUserID().toString());
            List<Message> messages = new ArrayList<>();
            if (map.get(message.getFromUserID().toString()) != null) {
                // 此用户不止一次给他发消息，但他目前仍然是离线状态
                messages = map.get(message.getFromUserID().toString());
                messages.add(message);
                map.put(String.valueOf(message.getFromUserID()), messages);
            } else {
                // 此用户未曾给他发消息
                messages.add(message);
                map.put(String.valueOf(message.getFromUserID()), messages);
            }
        }
    }

    // 发送消息
    public void sendMessage(Message message, Session session) {
        // session.getAsyncRemote().sendText(JSON.toJSONString(message));
        synchronized (session) {
            try {
                session.getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageAll(Message message) {
        for (Object object : WebSocketMapUtil.getAllValues()) {
            Session session_map = (Session) object;
            session_map.getAsyncRemote().sendText(JSON.toJSONString(message));
        }
    }

    public void sendOnLine() {
        Message message = new Message();
        message.setFromUserID(0);
        StringBuilder stringBuilder = new StringBuilder();
        List<String> userIdList = WebSocketMapUtil.getAllKey();
        for (String s : userIdList) {
            stringBuilder.append(s).append(";");
        }
        message.setMessageData(String.valueOf(count));
        sendMessageAll(message);
    }

}

