package com.example.stable_dif_web.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TxtToImg {

    @JsonProperty("prompt")
    private String prompt;
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
