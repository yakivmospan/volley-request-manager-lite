package com.ym.http;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.ym.http.utils.RequestHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageManager {

    private static ImageManager mInstance;
    private ImageLoaderController mImageLoaderController;


    private ImageManager(Context context) {
        Context applicationContext = context.getApplicationContext();
        mImageLoaderController = new ImageLoaderController(applicationContext);
    }

    public static synchronized void initializeWith(Context context) {
        if (mInstance == null) {
            mInstance = new ImageManager(context);
        }
    }

    public static synchronized ImageLoaderController loader() {
        if (mInstance == null) {
            throw new IllegalStateException(ImageManager.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        return mInstance.getImageLoaderController();
    }

    private ImageLoaderController getImageLoaderController() {
        return mImageLoaderController;
    }

    public class ImageLoaderController {

        private ImageLoader mImageLoader;

        public ImageLoaderController(Context context) {
            mImageLoader = new ImageLoader(
                    Volley.newRequestQueue(context.getApplicationContext()),
                    new Cache(RequestHelper.DEFAULT_DISK_USAGE_BYTES));
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

    public static class Cache
            extends LruCache<String, Bitmap>
            implements ImageLoader.ImageCache {

        public Cache() {
            this(getDefaultLruCacheSize());
        }

        public Cache(int sizeInKiloBytes) {
            super(sizeInKiloBytes);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }

        public static int getDefaultLruCacheSize() {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            return cacheSize;
        }
    }
}
