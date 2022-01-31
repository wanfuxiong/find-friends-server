package com.wanfuxiong.findfriends.service;

import com.wanfuxiong.findfriends.pojo.Moment;
import com.wanfuxiong.findfriends.pojo.User;

import java.util.Map;

public interface AndroidService {

    // 登录
    Map<String, Object> login(int id, String password);

    // 注册
    Map<String, Object> register(String username, String password, int sex, String interest);

    // 获取用户信息（不带密码）
    Map<String, Object> getUserInfo(int id);

    // 保存用户信息
    Map<String, Object> saveUserInfo(User user);

    // 获取指定兴趣的用户（排除自己）
    Map<String, Object> getUserListByInterestAndID(String interest, int id);

    // 随机获取50个用户，只带有id，username，sex，interest这几个属性
    Map<String, Object> getSimpleUserList(int count);

    // 随机获取一个用户
    Map<String, Object> getRandomUser(int id, int sex, String interest);

    // 通过ID搜索用户（排除自己）
    Map<String, Object> searchUserByID(int searchID, int currentID);

    // 修改密码
    Map<String, Object> changePassword(int id, String originPassword, String newPassword);

    // 注销账号（通过ID）
    Map<String, Object> closeAccount(int id);

    // 获取自己的动态
    Map<String, Object> getMomentsByID(int id);

    // 获取别人的动态
    Map<String, Object> getMoments(int id);

    // 发布动态
    Map<String, Object> sendMoment(Moment moment);
}
