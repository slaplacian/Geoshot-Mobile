package com.example.geoshot.generalUtilities.get;

public class APIClient0 {
    protected String host = "172.15.2.239:8080";
    protected String endpoint;

    protected APIClient0(String endpoint) {
        this.endpoint = endpoint;
    }

    public String PrepareStringRequest() {
        String req = "http://" + this.host + this.endpoint;
        return req;
    }

    public String PrepareStringBody(String... keys) {
        String body = "{\n";
        for(int i=0;i< keys.length;i++) {
            body += "\""+keys[i]+"\":\"%s\"";
            if(i != keys.length - 1) body += ",";
        }
        body += "}";
        return body;
    }
}