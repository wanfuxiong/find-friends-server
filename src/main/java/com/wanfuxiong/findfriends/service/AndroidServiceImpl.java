package com.wanfuxiong.findfriends.service;

import com.wanfuxiong.findfriends.mapper.IDMapper;
import com.wanfuxiong.findfriends.mapper.MessageMapper;
import com.wanfuxiong.findfriends.mapper.MomentMapper;
import com.wanfuxiong.findfriends.mapper.UserMapper;
import com.wanfuxiong.findfriends.pojo.ID;
import com.wanfuxiong.findfriends.pojo.Moment;
import com.wanfuxiong.findfriends.pojo.User;
import com.wanfuxiong.findfriends.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class AndroidServiceImpl implements AndroidService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    IDMapper idMapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    MomentMapper momentMapper;

    @Override
    public Map<String, Object> login(int id, String password) {
        Map<String, Object> results = new HashMap<>();
        User user = userMapper.selectUserByIDAndPassword(id, password);
        if (user == null) {
            results.put("code", 0);
            results.put("user", null);
        } else {
            results.put("code", 1);
            results.put("user", user);
            // List<Moment> moments = momentMapper.selectMomentByID(id);
            // results.put("moments", moments);
        }

        return results;
    }

    @Override
    public Map<String, Object> register(String username, String password, int sex, String interest) {
        Map<String, Object> results = new HashMap<>();
        int code;
        User user;
        List<ID> ids = idMapper.getAvailableIDList();
        if (ids.size() == 0) {
            code = 2;
        } else {
            int id = ids.get(new Random().nextInt(ids.size())).getUserID();
            code = userMapper.insertUser(id, username, password, sex, interest);
            if (code == 1) {
                idMapper.setIDUsed(id);// 注册成功后将用户id设为已使用
                user = userMapper.selectUserByIDAndPassword(id, password);
                results.put("id", id);
                results.put("user", user);
            }
        }
        results.put("code", code);
        results.put("desc", "0表示操作数据库失败，1表示成功，2表示无可用id");
        return results;
    }

    @Override
    public Map<String, Object> getUserInfo(int id) {
        Map<String, Object> results = new HashMap<>();
        User user = userMapper.selectUserByID(id);
        results.put("user", user);
        return results;
    }

    @Override
    public Map<String, Object> saveUserInfo(User user) {
        Map<String, Object> results = new HashMap<>();
        int code = userMapper.updateUserByID(
                user.getUserID(),
                user.getUserUsername(),
                user.getUserSignature(),
                user.getUserSex(),
                user.getUserInterest(),
                user.getUserPhoneNumber(),
                user.getUserQQ(),
                user.getUserProfile()
        );
        results.put("code", code);
        return results;
    }

    @Override
    public Map<String, Object> getUserListByInterestAndID(String interest, int id) {
        Map<String, Object> results = new HashMap<>();
        List<User> users = userMapper.selectUserByInterestAndID(interest, id);
        results.put("users", users);
        return results;
    }

    @Override
    public Map<String, Object> getSimpleUserList(int count) {
        Map<String, Object> results = new HashMap<>();
        List<User> users = userMapper.selectRandomUsers();
        results.put("users", users);
        return results;
    }

    @Override
    public Map<String, Object> getRandomUser(int id, int sex, String interest) {
        Map<String, Object> results = new HashMap<>();
        List<User> users = userMapper.selectRandomUsers();
        for (User user : users) {
            if (MyUtils.calculateSimilarity(interest, user.getUserInterest()) > 90) {
                if (sex == 2) {// 不分性别
                    results.put("user", user);
                    break;
                } else if (sex == 0 && user.getUserSex() == 0) {// 想找女
                    results.put("user", user);
                    break;
                } else if (sex == 1 && user.getUserSex() == 1) {// 想找男
                    results.put("user", user);
                    break;
                }
            }
        }
        if (results.size() == 0) {
            results.put("user", null);
        }
        return results;
    }

    @Override
    public Map<String, Object> searchUserByID(int searchID, int currentID) {
        Map<String, Object> results = new HashMap<>();
        if (currentID != searchID) {
            User user = userMapper.selectUserByID(searchID);
            if (user != null) {
                results.put("user", user);
                results.put("code", 1);
            } else {
                results.put("code", 0);
            }
        } else {
            results.put("code", 2);
        }
        return results;
    }

    @Override
    public Map<String, Object> changePassword(int id, String originPassword, String newPassword) {
        Map<String, Object> results = new HashMap<>();
        results.put("code", userMapper.updatePasswordByID(id, originPassword, newPassword));
        return results;
    }

    @Override
    public Map<String, Object> closeAccount(int id) {
        Map<String, Object> results = new HashMap<>();
        // if (idMapper.setIDUnused(id) == 1 && userMapper.deleteUserByID(id) == 1) {
        //     results.put("code", 1);
        // } else {
        //     results.put("code", 0);
        // }
        results.put("code", userMapper.deleteUserByID(id));
        return results;
    }

    @Override
    public Map<String, Object> getMomentsByID(int id) {
        Map<String, Object> results = new HashMap<>();
        List<Moment> moments = momentMapper.selectMomentByID(id);
        results.put("moments", moments);
        return results;
    }

    @Override
    public Map<String, Object> getMoments(int id) {
        return null;
    }

    @Override
    public Map<String, Object> sendMoment(Moment moment) {
        Map<String, Object> results = new HashMap<>();
        int code = momentMapper.insertMoment(moment);
        results.put("code", code);
        return results;
    }

}
