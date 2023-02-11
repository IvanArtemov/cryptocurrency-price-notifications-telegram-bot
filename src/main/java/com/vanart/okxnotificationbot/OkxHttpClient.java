package com.vanart.okxnotificationbot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanart.okxnotificationbot.dto.OkxResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class OkxHttpClient {

    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${okx.url}")
    private String okxPath;

    public <T> List<T> sendGetRequest(String path, T outputObject) {
        InputStream inputStream;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(okxPath + path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int status = conn.getResponseCode();
            if (status >= 300) {
                inputStream = conn.getErrorStream();
            } else {
                inputStream = conn.getInputStream();
            }
            var response = objectMapper.readValue(inputStream, new TypeReference<OkxResponse<T>>() {
            });
            return objectMapper.convertValue(response.getData(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, outputObject.getClass()));
        } catch (IOException e) {
            throw new RuntimeException("Error when executing the GET request", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
