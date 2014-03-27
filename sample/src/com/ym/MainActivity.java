package com.ym;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.defaultproject.R;
import com.ym.http.ImageManager;
import com.ym.http.RequestManager;
import com.ym.utils.L;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity
        extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main);

        RequestManager.initializeWith(getApplicationContext());

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                HttpFactory.createTestUrl(),
                null,
                mListener,
                mErrorListener);
        RequestManager.queue().doRequest(request);

        ImageManager.loader().doLoad(
                "http://farm6.staticflickr.com/5475/10375875123_75ce3080c6_b.jpg",
                mImageListener);
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

    private ImageLoader.ImageListener mImageListener = new ImageLoader.ImageListener() {
        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            Toast.makeText(getApplicationContext(), "Bitmap loaded", Toast.LENGTH_SHORT).show();
            BitmapDrawable bitmapDrawable = new BitmapDrawable(response.getBitmap());
            findViewById(R.id.txtHello).setBackgroundDrawable(bitmapDrawable);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            L.e(error.toString());
        }
    };
}
