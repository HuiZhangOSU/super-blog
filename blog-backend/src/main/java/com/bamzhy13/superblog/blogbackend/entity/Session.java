package com.bamzhy13.superblog.blogbackend.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Session {
    String sessionKey;
    String userUuid;
    Timestamp expireTime;
    Timestamp createTime;
    Timestamp updateTime;
}
