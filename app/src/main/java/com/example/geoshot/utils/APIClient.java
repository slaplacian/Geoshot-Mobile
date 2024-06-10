package com.example.geoshot.utils;

import android.util.Log;

import com.example.geoshot.R;

import okhttp3.*;
import java.io.IOException;

public class APIClient {
    private static PostStrategy strategy;
    private static final OkHttpClient client = new OkHttpClient();

    public void setPostStrategy(PostStrategy strategy) {
        APIClient.strategy = strategy;
    }

    public String postRequest() {
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
                Log.d("Depurando", "APIClientError -> postRequest -> responseisNotSuccessful: " + response.code());

                throw new IOException("Erro: " + response.code());
            }
        }
        catch (IOException e ) {
            Log.d("Depurando", "APIClientError -> postRequest -> RuntimeException");
            throw new RuntimeException(e);
        }
    }

    public String getRequest(String username) {

        String url = "http://"+ R.string.IP_SERVER_ADDRESS + ":8080/api/initial-page?username=" + username;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            return response.body().string();
        } catch (Exception e ) {
            return e.toString();
        }
    }
}