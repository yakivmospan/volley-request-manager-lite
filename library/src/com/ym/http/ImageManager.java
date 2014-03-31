package com.ym.http;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageManager {

    private static ImageManager mInstance;
    private ImageLoader mImageLoader;


    private ImageManager(Context context) {
        Context applicationContext = context.getApplicationContext();
        mImageLoader = new ImageLoader(
                Volley.newRequestQueue(applicationContext),
                new Cache(50 * 1024 * 1024));
    }

    public static synchronized void initializeWith(Context context) {
        if (mInstance == null) {
            mInstance = new ImageManager(context);
        }
    }

    public static synchronized ImageLoader loader() {
        if (mInstance == null) {
            throw new IllegalStateException(ImageManager.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        return mInstance.getImageLoader();
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
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
