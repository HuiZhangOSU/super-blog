package com.bamzhy13.superblog.blogbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MessageMapper {
    void insert(@Param("userUuid") String userUuid, @Param("code") String code);

    String getRecentCode(@Param("userUuid") String userUuid);

}
