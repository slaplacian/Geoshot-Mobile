package com.example.geoshot.generalUtilities.put;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PutRequest {
    public static String put(String url, String body) {
        OkHttpClient client = new OkHttpClient();
        RequestBody jsonBody = RequestBody.create(body, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(url)
                .put(jsonBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "error";
            }
        } catch (IOException e) {
            return "error";
        }
    }
}
