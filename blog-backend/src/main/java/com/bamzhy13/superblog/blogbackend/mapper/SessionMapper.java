package com.bamzhy13.superblog.blogbackend.mapper;

import com.bamzhy13.superblog.blogbackend.entity.Session;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SessionMapper {
    Session getSessionByUserUuid(String userUuid);

    void insert(String userUuid);

    void flushExpireTime(String userUuid);
}
