package evl.io.dto;

import java.util.ArrayList;
import java.util.List;

public class DeepSeekStreamRequest {
    private String model = "deepseek-chat";
    private List<Message> messages;
    private boolean stream = true;
    private Double temperature = 0.7;
    private Integer max_tokens = 2048;
    private Double frequency_penalty = 0.0;
    private Double presence_penalty = 0.0;

    public static DeepSeekStreamRequest create(String userMessage) {
        DeepSeekStreamRequest request = new DeepSeekStreamRequest();
        request.messages = new ArrayList<>();
        request.messages.add(new Message("user", userMessage));
        return request;
    }

    public DeepSeekStreamRequest addMessage(String role, String content) {
        this.messages.add(new Message(role, content));
        return this;
    }

    // getters and setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(Integer max_tokens) {
        this.max_tokens = max_tokens;
    }

    public Double getFrequency_penalty() {
        return frequency_penalty;
    }

    public void setFrequency_penalty(Double frequency_penalty) {
        this.frequency_penalty = frequency_penalty;
    }

    public Double getPresence_penalty() {
        return presence_penalty;
    }

    public void setPresence_penalty(Double presence_penalty) {
        this.presence_penalty = presence_penalty;
    }

    public static class Message {
        private String role;
        private String content;

        public Message() {
        }

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
