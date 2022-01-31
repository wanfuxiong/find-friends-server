package com.wanfuxiong.findfriends;

import com.wanfuxiong.findfriends.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindfriendsApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void hello() {
        System.out.println(userMapper.selectUserByID(111111));
    }

}
