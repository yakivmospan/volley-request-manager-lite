package com.ym.http;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.content.Context;

public class RequestManager {

    private static RequestManager mInstance;
    private RequestController mRequestController;

    private RequestManager(Context context) {
        Context applicationContext = context.getApplicationContext();
        mRequestController = new RequestController(applicationContext);
    }

    public static synchronized void initializeWith(Context context) {
        if (mInstance == null) {
            mInstance = new RequestManager(context);
        }
    }

    public static synchronized RequestController queue() {
        if (mInstance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        return mInstance.getRequestController();
    }

    private RequestController getRequestController() {
        return mRequestController;
    }

    public class RequestController {

        private RequestQueue mQueue;

        public RequestController(Context context) {
            mQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        public RequestController doRequest(Request request) {
            mQueue.add(request);
            return this;
        }

        public RequestController doRequest(Request request, RequestQueue queue) {
            queue.add(request);
            return this;
        }

        public RequestQueue instance() {
            return mQueue;
        }
    }
}
