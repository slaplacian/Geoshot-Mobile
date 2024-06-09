package com.example.geoshot;

import android.util.Log;

import okhttp3.*;
import java.io.IOException;

public class APIClient {
    private static PostStrategy strategy;

    public void setPostStrategy(PostStrategy strategy) {
        APIClient.strategy = strategy;
    }

    public String postRequest() {

        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        String json = strategy.json();

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(strategy.url())
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            } else {
                Log.d("APIClientError", "Erro: " + response.code());

                throw new IOException("Erro: " + response.code());
            }
        }
        catch (IOException e ) {
            Log.d("APIClientError", "Erro: RuntimeException");
            throw new RuntimeException(e);
        }
    }
}