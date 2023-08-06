package com.example.allergendetector;

public class ChatGPTRequest {
    private String prompt;

    public ChatGPTRequest(String prompt) {
        this.prompt = prompt;
    }

    // Create getters and setters if needed
    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}

