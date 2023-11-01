package com.example.stable_dif_web.controller;

import com.example.stable_dif_web.models.ImgToImg;
import com.example.stable_dif_web.models.TxtToImg;
import com.example.stable_dif_web.tools.DevTools;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static com.example.stable_dif_web.tools.DevTools.convertImageToBase64;

@RestController
public class Controller {

    private final RestTemplate restTemplate;
    ModelAndView modelAndView = new ModelAndView();

    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/TxtToImg")
    public ModelAndView getDataFromApiTxtGet(){
        modelAndView.setViewName("TxtToImg"); // this is the name of your html file

        return modelAndView;
    }

    @PostMapping ("/TxtToImg")
    public ModelAndView getDataFromApiTxtPost(@RequestParam String param){

        try {
            TxtToImg requestData = new TxtToImg();
            requestData.setPrompt(param);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String apiUrl = "http://127.0.0.1:7860/sdapi/v1/txt2img";

            HttpEntity<TxtToImg> entity = new HttpEntity<>(requestData, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, entity, String.class);

            String result = responseEntity.getBody();
            System.out.println(result);   // debugging purpose
            JSONObject resultJson = new JSONObject(result);
            String image = (String) resultJson.getJSONArray("images").get(0);

            modelAndView.setViewName("TxtToImg");
            modelAndView.addObject("imgSrc", image);
        } catch(JSONException e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("/ImgToImg")
    public ModelAndView getDataFromApiImgGet(){
        modelAndView.setViewName("ImgToImg"); // this is the name of your html file

        return modelAndView;
    }

    @PostMapping ("/ImgToImg")
    public ModelAndView getDataFromApiImgPost(@RequestParam("fileInput") MultipartFile file, @RequestParam("param") String param){

        try {
            ImgToImg requestData = new ImgToImg();
            requestData.setPrompt(param);
            requestData.setInit_images(convertImageToBase64(file));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String apiUrl = "http://127.0.0.1:7860/sdapi/v1/img2img";

            HttpEntity<ImgToImg> entity = new HttpEntity<>(requestData, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, entity, String.class);

            String result = responseEntity.getBody();
            System.out.println(result);   // debugging purpose
            JSONObject resultJson = new JSONObject(result);
            String image = (String) resultJson.getJSONArray("images").get(0);

            modelAndView.setViewName("ImgToImg");
            modelAndView.addObject("imgSrc", image);
        } catch(JSONException e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return modelAndView;
    }
}
