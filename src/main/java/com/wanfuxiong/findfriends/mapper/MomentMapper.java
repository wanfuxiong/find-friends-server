package com.wanfuxiong.findfriends.mapper;

import com.wanfuxiong.findfriends.pojo.Moment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MomentMapper {

    List<Moment> selectMomentByID(int id);

    int insertMoment(Moment moment);
}