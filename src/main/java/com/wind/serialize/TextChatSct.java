package com.wind.serialize;

import com.alibaba.fastjson.JSONObject;
import com.wind.common.HttpResponse;

import java.net.HttpURLConnection;
import java.util.List;

public class TextChatSct {
    private HttpResponse resp;

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

    private TextChatResponse data;

    public TextChatResponse getData() {
        return data;
    }

    public TextChatSct(HttpResponse resp) {
        this.resp = resp;
        if (this.resp.getCode() == HttpURLConnection.HTTP_OK) {
            JSONObject jsonObject = JSONObject.parseObject(resp.getData());
            data = JSONObject.toJavaObject(jsonObject, TextChatResponse.class);
        }
    }
}
