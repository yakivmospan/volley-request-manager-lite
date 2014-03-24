package com.ym.volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import android.content.Context;

public class RequestManager {

    private static RequestManager instance;

    private RequestController mRequestController;

    private ImageLoaderController mImageLoaderController;

    private RequestManager(Context context) {
        Context applicationContext = context.getApplicationContext();
        mRequestController = new RequestController(applicationContext);
        mImageLoaderController = new ImageLoaderController(applicationContext);
    }

    public static synchronized void initializeWith(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
    }

    public static synchronized RequestController queue() {
        if (instance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        return instance.getRequestController();
    }

    public static synchronized ImageLoaderController loader() {
        if (instance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        return instance.getImageLoaderController();
    }

    private RequestController getRequestController() {
        return mRequestController;
    }

    private ImageLoaderController getImageLoaderController() {
        return mImageLoaderController;
    }

    public class RequestController {

        private RequestQueue mQueue;

        public RequestController(Context context) {
            RequestQueue requestQueue = RequestFactory.newDefaultQueue(context);
        }

        public void doJORequest(RequestParams params) {
            Request request = new JsonObjectRequest(
                    params.method,
                    params.url,
                    params.jsonObject,
                    params.listenerJO,
                    params.errorListener);

            getQueue(params).add(request);
        }

        public void doJARequest(RequestParams params) {
            Request request = new JsonArrayRequest(
                    params.url,
                    params.listenerJA,
                    params.errorListener);

            getQueue(params).add(request);
        }

        private RequestQueue getQueue(RequestParams params) {
            return params.queue == null ? mQueue : params.queue;
        }
    }

    class ImageLoaderController {

        private ImageLoader mImageLoader;

        public ImageLoaderController(Context context) {
            mImageLoader = RequestFactory.newDefaultLoader(context);
        }

        public void load() {

        }
    }
}
