package com.bamzhy13.superblog.blogbackend.service;

import com.bamzhy13.superblog.blogbackend.mapper.SubscribeMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {

    @Autowired
    SubscribeMapper subscribeMapper;

    public void add(String info) {
        JsonObject jsonObject = JsonParser.parseString(info).getAsJsonObject();
        String email = jsonObject.get("email").getAsString();
        subscribeMapper.insert(email);
    }

    public void delete(String info) {
        JsonObject jsonObject = JsonParser.parseString(info).getAsJsonObject();
        String email = jsonObject.get("email").getAsString();
        subscribeMapper.delete(email);
    }

}
