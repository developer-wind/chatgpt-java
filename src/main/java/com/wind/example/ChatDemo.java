package com.wind.example;

import com.wind.Chat;
import com.wind.Completions;
import com.wind.common.HttpResponse;
import com.wind.serialize.ChatRespObj;
import com.wind.serialize.CompletionsRespObj;
import com.wind.struct.ChatMessage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatDemo {
    public static void main(String[] args) {
        try {
            Chat c = new Chat("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");
            c.setUser("user_id123");
            ChatMessage[] messages = new ChatMessage[] {
                    new ChatMessage("user", "你好"),
            };
            HttpResponse resp = c.create(messages);
            if (resp.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                System.out.println("Pk失效了大哥");
                //pk失效了
                return;
            }

            ChatRespObj respObj = new ChatRespObj(resp);
            ChatRespObj.Response data = respObj.getData();
            if (data == null) {
                System.out.println("数据解析失败，http_code:"+resp.getCode());
                //数据解析失败，返回数据异常或服务端有错误
                return;
            }

            System.out.println(data.getChoices().get(0).getMessage().getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
