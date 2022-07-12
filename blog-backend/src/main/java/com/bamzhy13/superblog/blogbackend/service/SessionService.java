package com.bamzhy13.superblog.blogbackend.service;

import com.bamzhy13.superblog.blogbackend.entity.Session;
import com.bamzhy13.superblog.blogbackend.mapper.SessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    SessionMapper sessionMapper;

    public Session getSessionByUserUuid(String userUuid) {
        Session session = sessionMapper.getSessionByUserUuid(userUuid);
        return session;
    }

    public void insert(String userUuid) {
        sessionMapper.insert(userUuid);
    }

    public void flushExpireTime(String userUuid) {
        sessionMapper.flushExpireTime(userUuid);
    }
}
