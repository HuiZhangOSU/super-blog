package com.bamzhy13.superblog.blogbackend.mapper;

import com.bamzhy13.superblog.blogbackend.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserInfoMapper {
    UserInfo getUserInfo(@Param("username") String username, @Param("password") String password);

    UserInfo getUserInfoByName(@Param("username")String username);
}
