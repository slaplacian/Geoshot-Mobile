package com.example.geoshot.generalUtilities;

import android.util.Log;

import com.example.geoshot.generalUtilities.post.PostStrategy;

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
                String resposta = response.body().string();
                return resposta;
            } else {
                Log.d("Depurando", "APIClientError -> postRequest -> responseisNotSuccessful: " + response.code());

                throw new IOException("Erro: " + response.code());
            }
        }
        catch (IOException e ) {
            Log.d("Depurando", "APIClientError -> postRequest -> IOException");
            throw new RuntimeException(e);
        }
    }

    public String getRequest(String username) {

        String url = "http://"+ AuxiliarGeral.getIPServerAddress() + ":8080/api/initial-page?username=" + username;

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            String resposta = response.body().string();
            Log.d("Depurando", "APIClient -> getRequest -> Olha o response: " + resposta);
            return resposta;
        } catch (Exception e ) {
            Log.d("Depurando", "APIClient -> getRequest  -> Erro no response " + e.toString());
            return e.toString();
        }
    }
}