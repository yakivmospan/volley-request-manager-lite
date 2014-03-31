package com.ym.http;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.content.Context;

public class RequestManager {

    private static RequestManager mInstance;
    private RequestQueue mQueue;

    private RequestManager(Context context) {
        Context applicationContext = context.getApplicationContext();
        mQueue = Volley.newRequestQueue(applicationContext);
    }

    public static synchronized void initializeWith(Context context) {
        if (mInstance == null) {
            mInstance = new RequestManager(context);
        }
    }

    public static synchronized RequestQueue queue() {
        if (mInstance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        return mInstance.getQueue();
    }

    public RequestQueue getQueue() {
        return mQueue;
    }
}
