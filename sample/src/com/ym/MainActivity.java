package com.ym;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.defaultproject.R;
import com.ym.volley.RequestManager;
import com.ym.volley.RequestParams;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity
        extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main);

        RequestManager.initializeWith(getApplicationContext());

        RequestParams requestParams = new RequestParams().url(HttpFactory.createTestUrl());
        RequestManager.queue().doJORequest(requestParams);

//        //Queue use custom listener
//        RequestManager.queue()
//                .useBackgroundQueue()
//                .addRequest(new TestJsonRequest(), mRequestCallback)
//                .start();
//
//        //Queue use default volley Response and Error listener
//        RequestManager
//                .queue()
//                .useBackgroundQueue()
//                .addRequest(new TestJsonRequest(), mListener, mErrorListener)
//                .start();
//
//        RequestManager
//                .loader()
//                .useDefaultLoader()
//                .obtain()
//                .get(
//                        "http://farm6.staticflickr.com/5475/10375875123_75ce3080c6_b.jpg",
//                        new ImageLoader.ImageListener() {
//                            @Override
//                            public void onResponse(ImageLoader.ImageContainer response,
//                                    boolean isImmediate) {
//
//                                Toast.makeText(getApplicationContext(), "Bitmap loaded", Toast.LENGTH_SHORT).show();
//
//                                BitmapDrawable bitmapDrawable = new BitmapDrawable(
//                                        response.getBitmap());
//                                findViewById(R.id.txtHello).setBackgroundDrawable(bitmapDrawable);
//                            }
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                L.e(error.toString());
//                            }
//                        }
//                );
    }


    private Response.Listener mListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject o) {

        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    };
}
