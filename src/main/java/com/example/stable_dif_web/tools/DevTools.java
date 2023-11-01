package com.example.stable_dif_web.tools;


import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.*;
import java.util.Base64;

public class DevTools {
    public static String convertImageToBase64(MultipartFile imageFile) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            try (InputStream imageInputStream = imageFile.getInputStream()) {
                while ((bytesRead = imageInputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
            }

            byte[] imageData = byteArrayOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageData);
        } catch (IOException e) {
            throw e;
        }
    }
}
