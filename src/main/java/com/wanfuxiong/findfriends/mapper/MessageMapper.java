package com.wanfuxiong.findfriends.mapper;

import com.wanfuxiong.findfriends.pojo.ID;
import com.wanfuxiong.findfriends.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    List<Message> selectMessages(int toUserID);

    int insertMessage(Message message);

}

