package com.example.stable_dif_web.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ImgToImg {
    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("init_images")
    private List<String> init_images = new ArrayList<>();
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    public void setInit_images(String imageInBase64) {
        this.init_images.add(imageInBase64);
    }
}
