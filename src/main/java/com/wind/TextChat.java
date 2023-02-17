package com.wind;

import com.alibaba.fastjson.JSONObject;
import com.wind.common.HttpRequest;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TextChat {
    Authentication auth;

    HashMap<String, String> header;

    /**
     * model 语言模型
     * text-davinci-003 收费贵，速度慢，精准
     * text-curie-001 收费便宜，速度快，次精准
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
    int maxToken = 512;

    /**
     * temperature 选填参数，是一个浮点数，表示生成文本的创新程度。较高的温度会产生更具创新性的文本，而较低的温度会产生更具可预测性的文本。默认值为 1。
     */
    float temperature = 1;

    /**
     * top_p 选填参数，是一个浮点数，表示使用的 Nucleus Sampling 的概率质量分布范围。默认值为 1.0。
     */
    int topP = 1;

    /**
     * n 选填参数，是一个整数，表示要生成的文本的数量。默认值为 1。
     */
    int n = 1;

    /**
     * stream 选填参数，是一个布尔值，表示是否将生成的文本作为流返回。默认值为 false，这意味着我会将生成的所有文本作为单个响应返回。如果您将此参数设置为 true，则我将生成的文本作为流返回，这可以在生成大量文本时提高性能。
     */
    boolean stream = false;

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public void setStop(String[] stop) {
        this.stop = stop;
    }

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

    public TextChat setFrequencyPenalty(float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
        return this;
    }

    public TextChat setPresencePenalty(float presencePenalty) {
        this.presencePenalty = presencePenalty;
        return this;
    }

    public TextChat setTopP(int topP) {
        this.topP = topP;
        return this;
    }

    private static final String urlPath = "https://api.openai.com/v1/completions";

    public TextChat(Authentication a) {
        this.auth = a;
    }

    public TextChat setModel(String m) {
        model = m;
        return this;
    }

    public TextChat setHeaders(HashMap<String, String> headers) {
        header = headers;
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
    public TextChatResponse send(String prompt) throws IOException {
        HttpRequest httpRequest = new HttpRequest(urlPath, auth.getKey(user));
        if (header != null)
            header.forEach(httpRequest::addHeader);
        String respJson = httpRequest.done(() -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("model", model);
                jsonObject.put("max_tokens", maxToken);
                jsonObject.put("echo", echo);
                jsonObject.put("user", user);
                jsonObject.put("n", n);
                jsonObject.put("top_p", topP);
                jsonObject.put("temperature", temperature);
                jsonObject.put("prompt", prompt);
                if (stop != null)
                    jsonObject.put("stop", stop);
                jsonObject.put("frequency_penalty", frequencyPenalty);
                jsonObject.put("presence_penalty", presencePenalty);
                jsonObject.put("stream", stream);
                return jsonObject.toJSONString().getBytes();
        });
        JSONObject jsonObject = JSONObject.parseObject(respJson);
        return JSONObject.toJavaObject(jsonObject, TextChatResponse.class);
    }
}
