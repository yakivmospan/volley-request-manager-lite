package com.ym.volley;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestParams {

    protected RequestQueue queue;

    protected int method;
    protected String url;
    protected JSONObject jsonObject;
    protected String appToken;
    protected String accessToken;
    protected Response.Listener<JSONObject> listenerJO;
    protected Response.Listener<JSONArray> listenerJA;
    protected Response.ErrorListener errorListener;

    public RequestParams queue(RequestQueue queue) {
        this.queue = queue;
        return this;
    }

    public RequestParams listenerJO(Response.Listener<JSONObject> listener) {
        listenerJO = listener;
        return this;
    }

    public RequestParams listenerJA(Response.Listener<JSONArray> listener) {
        listenerJA = listener;
        return this;
    }

    public RequestParams listener(Response.ErrorListener listener) {
        errorListener = listener;
        return this;
    }

    public RequestParams json(JSONObject JSONObject) {
        jsonObject = JSONObject;
        return this;
    }

    public RequestParams appToken(String appToken) {
        this.appToken = appToken;
        return this;
    }

    public RequestParams accessToken(String driverToken) {
        this.accessToken = driverToken;
        return this;
    }

    public RequestParams url(String url) {
        this.url = url;
        return this;
    }

    public boolean isAccessTokenValid() {
        return accessToken != null && !accessToken.isEmpty();
    }

    public boolean isAppTokenValid() {
        return appToken != null && !appToken.isEmpty();
    }
}
