package com.wind;

import com.alibaba.fastjson.JSONObject;
import com.wind.common.HttpRequest;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.List;

public class TextChat {
    Authentication auth;

    /**
     * model 语言模型
     * text-davinci-003 收费贵，速度慢，精准
     * text-curie-001 收费便宜，速度快，次精准
     */
    String model = "text-curie-001";

    /**
     * maxToken 控制返回答案的长度，最大4096
     */
    int maxToken = 512;

    /**
     * temperature 答案的随机性
     * 取值0-2，高随机，低固定
     */
    float temperature = 1;

    /**
     * top_p
     */
    int top_p = 1;

    /**
     * n 为每一个问题生成多种答案
     * 多种答案的token总数不超过maxToken
     * 建议为1，性价比高
     */
    int n = 1;

    /**
     * 回显问题
     */
    boolean echo = false;

    /**
     * 唯一用户标识，可以用于追溯
     */
    String user;

    private static final String urlPath = "https://api.openai.com/v1/completions";

    public TextChat(Authentication a) {
        this.auth = a;
    }

    public TextChat setModel(String m) {
        model = m;
        return this;
    }

    public TextChat setMaxToken(int t) {
        maxToken = t;
        return this;
    }

    public TextChat setTemperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    public TextChat setTop_p(int top_p) {
        this.top_p = top_p;
        return this;
    }

    public TextChat setN(int n) {
        this.n = n;
        return this;
    }

    public TextChat setEcho(boolean echo) {
        this.echo = echo;
        return this;
    }

    public TextChat setUser(String user) {
        this.user = user;
        return this;
    }

    static public class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
        public void setPrompt_tokens(int prompt_tokens) {
            this.prompt_tokens = prompt_tokens;
        }
        public int getPrompt_tokens() {
            return prompt_tokens;
        }

        public void setCompletion_tokens(int completion_tokens) {
            this.completion_tokens = completion_tokens;
        }
        public int getCompletion_tokens() {
            return completion_tokens;
        }

        public void setTotal_tokens(int total_tokens) {
            this.total_tokens = total_tokens;
        }
        public int getTotal_tokens() {
            return total_tokens;
        }
    }

    static public class Choices {
        private String text; //回复的内容
        private int index;
        private String logprobs;
        private String finish_reason;
        public void setText(String text) {
            this.text = text;
        }
        public String getText() {
            return text;
        }

        public void setIndex(int index) {
            this.index = index;
        }
        public int getIndex() {
            return index;
        }

        public void setLogprobs(String logprobs) {
            this.logprobs = logprobs;
        }
        public String getLogprobs() {
            return logprobs;
        }

        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }
        public String getFinish_reason() {
            return finish_reason;
        }
    }

    static public class TextChatResponse {
        private String id;
        private String object; //对话类型
        private long created; //创建时间
        private String model; //语言模型
        private List<Choices> choices; //包含回复的具体数据
        private Usage usage; //token统计相关

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setObject(String object) {
            this.object = object;
        }
        public String getObject() {
            return object;
        }

        public void setCreated(long created) {
            this.created = created;
        }
        public long getCreated() {
            return created;
        }

        public void setModel(String model) {
            this.model = model;
        }
        public String getModel() {
            return model;
        }

        public void setChoices(List<Choices> choices) {
            this.choices = choices;
        }
        public List<Choices> getChoices() {
            return choices;
        }

        public void setUsage(Usage usage) {
            this.usage = usage;
        }
        public Usage getUsage() {
            return usage;
        }
    }

    /**
     * send 发送对话内容
     * @param prompt 对话内容
     * @return TextChatResponse 对方的回复
     * @throws IOException
     */
    public TextChatResponse send(String prompt) throws IOException, HTTPException {
        HttpRequest httpRequest = new HttpRequest(urlPath, auth.getKey(user));
        String respJson = httpRequest.done(() -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("model", model);
                jsonObject.put("max_tokens", maxToken);
                jsonObject.put("echo", echo);
                jsonObject.put("user", user);
                jsonObject.put("n", n);
                jsonObject.put("top_p", top_p);
                jsonObject.put("temperature", temperature);
                jsonObject.put("prompt", prompt);
                return jsonObject.toJSONString().getBytes();
        });
        JSONObject jsonObject = JSONObject.parseObject(respJson);
        return JSONObject.toJavaObject(jsonObject, TextChatResponse.class);
    }
}
