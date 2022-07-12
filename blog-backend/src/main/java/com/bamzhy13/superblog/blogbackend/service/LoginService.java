package com.bamzhy13.superblog.blogbackend.service;

import com.bamzhy13.superblog.blogbackend.entity.UserInfo;
import com.bamzhy13.superblog.blogbackend.mapper.UserInfoMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserInfoMapper userInfoMapper;

    public UserInfo getUserInfo(String userInfoStr) {
        JsonObject jsonObject = JsonParser.parseString(userInfoStr).getAsJsonObject();

        String username = jsonObject.get("username").getAsString();

        String password = jsonObject.get("password").getAsString();

        UserInfo userInfo = userInfoMapper.getUserInfo(username, password);

        if (userInfo != null) {
            userInfo.setPassword("");
        }

        return userInfo;

    }
    public UserInfo getUserInfoByName(String username) {

        UserInfo userInfo = userInfoMapper.getUserInfoByName(username);

        if (userInfo != null) {
            userInfo.setPassword("");
        }

        return userInfo;

    }


}
