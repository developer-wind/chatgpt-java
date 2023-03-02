package com.wind.example;

import com.wind.Authentication;
import com.wind.TextChat;
import com.wind.common.HttpResponse;

import com.wind.serialize.TextChatSct;

import java.io.IOException;
import java.net.HttpURLConnection;

public class TextChatExample {
    public static void main(String[] args) {
        //如果有多个账号或者多个私钥可以都写进来，会轮训使用
        String[] pks = new String[]{
            "sk-g6NzxToPUm5qVGlek",
            "sk-ZhhBea34FjhFn"
        };
        Authentication authentication = new Authentication(pks);
        TextChat textChat = new TextChat(authentication);
        try {
            HttpResponse resp = textChat.setUser("uuid_123123").send("继续");
            if (resp.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                System.out.println("Pk失效了大哥");
                //pk失效了
                return;
            }

            TextChatSct textChatSct = new TextChatSct(resp);
            TextChatSct.TextChatResponse data = textChatSct.getData();
            if (data == null) {
                System.out.println("数据解析失败，http_code:"+resp.getCode());
                //数据解析失败，返回数据异常或服务端有错误
                return;
            }

            System.out.println(data.getChoices().get(0).getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
