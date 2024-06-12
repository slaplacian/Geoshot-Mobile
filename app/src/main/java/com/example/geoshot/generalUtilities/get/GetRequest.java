package com.example.geoshot.generalUtilities.get;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequest {
    static String get(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Erro na requisição: " + response.code();
            }
        } catch (IOException e) {
            return "Erro: " + e.getMessage();
        }
    }

}
