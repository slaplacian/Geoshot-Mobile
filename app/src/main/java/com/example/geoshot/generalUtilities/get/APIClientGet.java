package com.example.geoshot.generalUtilities.get;

import com.example.geoshot.generalUtilities.APIClient;

public class APIClientGet extends APIClient {

    public APIClientGet(String endpoint) {
        super(endpoint);
    }
    public String PrepareStringRequest(String... params) {
        String req = "http://" + this.host + this.endpoint + "?";
        for(int i=0;i<params.length;i++) {
            req += params[i];
            req += "=%s";
            if(i != params.length - 1) req += "&";
        }

        return req;

    }
}
