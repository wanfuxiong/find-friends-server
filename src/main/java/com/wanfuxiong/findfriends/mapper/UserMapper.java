package com.wanfuxiong.findfriends.mapper;

import com.wanfuxiong.findfriends.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    int countUsers();

    // List<User> selectRandomUsers(int count);
    List<User> selectRandomUsers();

    User selectRandomUser(int id);

    List<User> selectUserByInterestAndID(String interest, int id);

    User selectUserByIDAndPassword(int id, String password);

    User selectUserByUsername(String username);

    User selectUserByID(Integer id);

    int insertUser(int id, String username, String password, int sex, String interest);

    int updateUserByID(int id, String username, String signature, int sex, String interest, String phoneNumber, String qq, byte[] profile);

    int updatePasswordByID(int id, String originPassword, String newPassword);

    int deleteUserByID(int id);

    // 新版数据库用户头像是路径
    int changeProfileOkHttp(int id, String profile);
}

