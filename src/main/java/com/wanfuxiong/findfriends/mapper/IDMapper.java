package com.wanfuxiong.findfriends.mapper;

import com.wanfuxiong.findfriends.pojo.ID;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IDMapper {

    List<ID> getAvailableIDList();

    int setIDUsed(int id);

    int setIDUnused(int id);
}

