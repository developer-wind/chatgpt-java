package com.wind;

import com.alibaba.fastjson.JSONObject;
import com.wind.common.HttpRequest;
import com.wind.common.HttpResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Completions {
    /**
     * model 语言模型
     */
    String model = "text-curie-001";

    /**
     * frequencyPenalty 选填参数，是一个浮点数，用于惩罚模型生成重复的文本。默认值为 0.0。
     */
    float frequencyPenalty = 0;

    /**
     * presencePenalty 选填参数，是一个浮点数，用于惩罚模型生成缺少指定文本内容的文本。默认值为 0.0。
     */
    float presencePenalty = 0;

    /**
     * maxToken 选填参数，是一个整数，表示要生成的最大标记数。默认值为 2048。
     */
    int maxToken = 1024;

    /**
     * temperature 选填参数，是一个浮点数，表示生成文本的创新程度。较高的温度会产生更具创新性的文本，而较低的温度会产生更具可预测性的文本。默认值为 1。
     */
    float temperature = 1;

    /**
     * top_p 选填参数，是一个浮点数，表示使用的 Nucleus Sampling 的概率质量分布范围。默认值为 1.0。
     */
    float topP = 1;

    /**
     * n 选填参数，是一个整数，表示要生成的文本的数量。默认值为 1。
     */
    int n = 1;

    /**
     * stream 选填参数，是一个布尔值，表示是否将生成的文本作为流返回。默认值为 false，这意味着我会将生成的所有文本作为单个响应返回。如果您将此参数设置为 true，则我将生成的文本作为流返回，这可以在生成大量文本时提高性能。
     */
    boolean stream = false;

    /**
     * echo 选填参数，是一个布尔值，表示是否将输入 prompt 包含在生成的文本中。默认值为 true。
     */
    boolean echo = true;

    /**
     * stop 选填参数，是一个字符串或字符串列表，表示生成文本的停止标记。默认情况下，我会在生成达到 max_tokens 标记数或遇到空字符串时停止生成文本。如果您指定了 stop 参数，则我会在遇到停止标记时停止生成文本。
     */
    String[] stop;

    /**
     * 唯一用户标识，可以用于追溯
     */
    String user;

    public void setModel(String model) {
        this.model = model;
    }

    public void setFrequencyPenalty(float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public void setPresencePenalty(float presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public void setMaxToken(int maxToken) {
        this.maxToken = maxToken;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setTopP(float topP) {
        this.topP = topP;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public void setEcho(boolean echo) {
        this.echo = echo;
    }

    public void setStop(String[] stop) {
        this.stop = stop;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String url = "https://api.openai.com/v1/completions";
    HttpRequest httpRequest;

    public Completions(String pk, HashMap<String, String> header) throws IOException {
        httpRequest = new HttpRequest(url, pk);
        if (header != null)
            header.forEach(httpRequest::addHeader);
    }

    public Completions(String pk) throws IOException {
        httpRequest = new HttpRequest(url, pk);
    }

    public HttpResponse create(String prompt) throws IOException {
        return httpRequest.send(() -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("model", model);
            jsonObject.put("max_tokens", maxToken);
            jsonObject.put("echo", echo);
            jsonObject.put("user", user);
            jsonObject.put("n", n);
            jsonObject.put("top_p", topP);
            jsonObject.put("temperature", temperature);
            jsonObject.put("prompt", prompt);
            jsonObject.put("stop", stop);
            jsonObject.put("frequency_penalty", frequencyPenalty);
            jsonObject.put("presence_penalty", presencePenalty);
            jsonObject.put("stream", stream);
            return jsonObject.toJSONString().getBytes();
        });
    }
}
