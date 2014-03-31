package com.ym;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.defaultproject.R;
import com.ym.http.ImageManager;
import com.ym.http.RequestManager;

import org.json.JSONObject;

/**
 * Created by Yakiv M. on 25.03.14
 */

public class HttpFactory {

    public static void createTestRequest(Response.Listener<JSONObject> listener,
            Response.ErrorListener errorListener) {
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                HttpUtils.createTestUrl(),
                null,
                listener,
                errorListener);
        RequestManager.queue().doRequest(request);
    }

    public static void loadImageWithDefaultStub(String url, NetworkImageView view) {
        view.setDefaultImageResId(R.drawable.ic_launcher);
        ImageManager.loader().doLoad(
                url,
                view);
    }
}
