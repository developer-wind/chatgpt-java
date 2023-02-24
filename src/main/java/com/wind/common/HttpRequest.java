package com.wind.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    HttpURLConnection con;

    public HttpRequest(String url, String pk) throws IOException {
        URL obj = new URL(url);
        con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
//        con.setRequestProperty("OpenAI-Organization", "org-BLvjuHK8stK27Syz18FrvTaA");
        con.setRequestProperty("Authorization", "Bearer " + pk);
        con.setRequestProperty("Accept-Charset", "utf-8");
        //如不需要使用中文可外部覆盖
        con.setRequestProperty("Accept-Language", "zh-CN");
    }

    public interface ParamsHandler {
        public byte[] getParams();
    }

    public HttpRequest addHeader(String key, String val) {
        con.setRequestProperty(key, val);
        return this;
    }

    public String done(ParamsHandler ph) throws IOException {
        OutputStream os = con.getOutputStream();
        os.write(ph.getParams());
        os.flush();
        os.close();
        this.con.disconnect();
        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("http_code: "+responseCode);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();

        String inputLine;
        while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
