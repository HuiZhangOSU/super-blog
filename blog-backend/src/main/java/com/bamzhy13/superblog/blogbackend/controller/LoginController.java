package com.bamzhy13.superblog.blogbackend.controller;

import com.bamzhy13.superblog.blogbackend.entity.Session;
import com.bamzhy13.superblog.blogbackend.entity.UserInfo;
import com.bamzhy13.superblog.blogbackend.service.LoginService;
import com.bamzhy13.superblog.blogbackend.service.SessionService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;


@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    SessionService sessionService;

    @PostMapping("/login")
    @ResponseBody
    public HashMap<String, String> login(@RequestBody String userInfoStr) {

        HashMap<String, String> result = new HashMap<>();
        UserInfo userInfo = loginService.getUserInfo(userInfoStr);

        Session session;

        if (userInfo != null) {
            result.put("loginStatus", "success");
            session = sessionService.getSessionByUserUuid(userInfo.getUuid());
            if (session != null) {
                Timestamp expireTime = session.getExpireTime();
                if (expireTime.getTime() - System.currentTimeMillis() > 0) {
                    result.put("needValidate", "false");
                } else {
                    sessionService.flushExpireTime(userInfo.getUuid());
                    result.put("needValidate", "true");
                }
            } else {
                sessionService.insert(userInfo.getUuid());
                result.put("needValidate", "true");
            }
        } else {
            result.put("loginStatus", "failed");
            result.put("needValidate", "false");
        }

        return result;
    }

}
