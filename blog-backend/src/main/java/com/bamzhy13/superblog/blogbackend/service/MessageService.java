package com.bamzhy13.superblog.blogbackend.service;

import com.bamzhy13.superblog.blogbackend.entity.UserInfo;
import com.bamzhy13.superblog.blogbackend.mapper.MessageMapper;
import com.bamzhy13.superblog.blogbackend.tools.MailSender;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Properties;

@Service
public class MessageService {
    @Autowired
    LoginService loginService;

    @Autowired
    MessageMapper messageMapper;

    public String generateCode() {

        String str = String.valueOf(System.currentTimeMillis());
        String code = str.substring(str.length() - 4);
        return code;
    }

    public void saveAndSendCode(String contact, String code) throws MessagingException {
        JsonObject jsonObject = JsonParser.parseString(contact).getAsJsonObject();
        String emailAddress = jsonObject.get("address").getAsString();
        String username = jsonObject.get("username").getAsString();

        UserInfo userInfo = loginService.getUserInfoByName(username);

        messageMapper.insert(userInfo.getUuid(), code);

        MailSender.sendMail("Verification Code", emailAddress, "Your verification code: <b style=\\\"color:blue;\\\">" + code + "</b>");

    }

    public boolean checkCode(String info){

        JsonObject jsonObject = JsonParser.parseString(info).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        String code = jsonObject.get("code").getAsString();

        UserInfo userInfo = loginService.getUserInfoByName(username);

        String codeInDB = messageMapper.getRecentCode(userInfo.getUuid());

        return codeInDB.equals(code);

    }
}
