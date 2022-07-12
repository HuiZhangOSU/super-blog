package com.bamzhy13.superblog.blogbackend.controller;

import com.bamzhy13.superblog.blogbackend.entity.UserInfo;
import com.bamzhy13.superblog.blogbackend.service.LoginService;
import com.bamzhy13.superblog.blogbackend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MessageController {

    @Autowired
    LoginService loginService;

    @Autowired
    MessageService messageService;

    @PostMapping("/sendCode")
    @ResponseBody
    public HashMap<String, String> sendCode(@RequestBody String contact) {

        HashMap<String, String> result = new HashMap<>();

        try{
            String code = messageService.generateCode();
            messageService.saveAndSendCode(contact, code);
            result.put("status","success");
            result.put("info","sent successfully");
        }catch (Exception e) {
            result.put("status","failed");
            result.put("info",e.toString());
        }

        return result;
    }

    @PostMapping("/checkCode")
    @ResponseBody
    public HashMap<String, String> checkCode(@RequestBody String info) {

        HashMap<String, String> result = new HashMap<>();

        try{
            if(messageService.checkCode(info)){
                result.put("status","success");
                result.put("info","verified");
            }else{
                result.put("status","failed");
                result.put("info","wrong ");
            }

        }catch (Exception e) {
            result.put("status","failed");
            result.put("info",e.toString());
        }

        return result;
    }

}
