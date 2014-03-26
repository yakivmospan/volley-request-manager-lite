package com.ym.volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

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
            RequestQueue requestQueue = RequestQueueFactory.newDefaultQueue(context);
        }

        public RequestController doRequest(Request request) {
            mQueue.add(request);
            return this;
        }

        public RequestController doRequest(Request request, RequestQueue queue) {
            queue.add(request);
            return this;
        }

        public RequestQueue getQueue() {
            return mQueue;
        }
    }

    public class ImageLoaderController {

        private ImageLoader mImageLoader;

        public ImageLoaderController(Context context) {
            mImageLoader = RequestQueueFactory.newDefaultLoader(context);
        }

        public ImageLoaderController doLoad(String url, NetworkImageView view) {
            view.setImageUrl(url, mImageLoader);
            return this;
        }

        public ImageLoaderController doLoad(String url, NetworkImageView view,
                ImageLoader imageLoader) {
            view.setImageUrl(url, mImageLoader);
            return this;
        }

        public ImageLoaderController doLoad(String url, ImageLoader.ImageListener imageListener) {
            mImageLoader.get(url, imageListener);
            return this;
        }

        public ImageLoaderController doLoad(String url, ImageLoader.ImageListener imageListener,
                ImageLoader imageLoader) {
            imageLoader.get(url, imageListener);
            return this;
        }

        public ImageLoader getImageLoader() {
            return mImageLoader;
        }
    }
}
