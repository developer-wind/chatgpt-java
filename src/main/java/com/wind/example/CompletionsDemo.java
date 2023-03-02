package com.wind.example;

import com.wind.Completions;
import com.wind.common.HttpResponse;

import com.wind.serialize.CompletionsRespObj;

import java.io.IOException;
import java.net.HttpURLConnection;

public class CompletionsDemo {
    public static void main(String[] args) {
        String pk = "pk-sa1sBFsb23";
        try {
            Completions c = new Completions(pk);
            c.setUser("uuid_123123");
            HttpResponse resp = c.create("你好!");
            if (resp.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                System.out.println("Pk失效了大哥");
                //pk失效了
                return;
            }

            CompletionsRespObj respObj = new CompletionsRespObj(resp);
            CompletionsRespObj.Response data = respObj.getData();
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
