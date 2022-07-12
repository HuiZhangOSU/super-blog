package com.bamzhy13.superblog.blogbackend.controller;

import com.bamzhy13.superblog.blogbackend.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SubscribeController {

    @Autowired
    SubscribeService subscribeService;

    @PostMapping("/subscribe")
    @ResponseBody
    public HashMap<String, String> subscribe(@RequestBody String info) {
        HashMap<String, String> result = new HashMap<>();
        try {
            subscribeService.add(info);
            result.put("status", "success");
            result.put("info", "subscribe successfully");
        } catch (Exception e) {
            result.put("status", "failed");
            result.put("info", e.toString());
        }
        return result;

    }

    @PostMapping("/unsubscribe")
    @ResponseBody
    public HashMap<String, String> unsubscribe(@RequestBody String info) {
        HashMap<String, String> result = new HashMap<>();
        try {
            subscribeService.delete(info);
            result.put("status", "success");
            result.put("info", "unsubscribe successfully");
        } catch (Exception e) {
            result.put("status", "failed");
            result.put("info", e.toString());
        }
        return result;
    }
}
