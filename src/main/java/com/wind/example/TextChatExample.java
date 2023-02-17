package com.wind.example;

import com.wind.Authentication;
import com.wind.TextChat;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;

public class TextChatExample {
    public static void main(String[] args) {
        //如果有多个账号或者多个私钥可以都写进来，会轮训使用
        String[] pks = new String[]{
//            "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",
//            "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",
                "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"
        };
        Authentication authentication = new Authentication(pks);
        TextChat textChat = new TextChat(authentication);
        try {
            TextChat.TextChatResponse resp = textChat.setUser("uuid_123123").send("继续");
            System.out.println(resp.getChoices().get(0).getText());
        } catch (IOException | HTTPException e) {
            throw new RuntimeException(e);
        }
    }
}
