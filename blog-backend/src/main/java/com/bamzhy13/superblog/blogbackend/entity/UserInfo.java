package com.bamzhy13.superblog.blogbackend.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserInfo {
    String uuid;
    String username;
    String password;
    Timestamp lastLoginTime;
    Timestamp createTime;
    Timestamp updateTime;
}
