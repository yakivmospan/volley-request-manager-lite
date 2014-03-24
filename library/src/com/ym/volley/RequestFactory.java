package com.ym.volley;

import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import java.io.File;
import java.util.concurrent.Executors;

public class RequestFactory {

    public static interface Options {

        public static final String REQUEST_CACHE_PATH = "volley/request";
        public static final String IMAGE_CACHE_PATH = "volley/image";
        public static final int DEFAULT_POOL_SIZE = 4;

        public static final int DEFAULT_DISK_USAGE_BYTES = 50 * 1024 * 1024;
    }

    public static ImageLoader newDefaultLoader(Context context) {
        return newLoader(newDefaultImageQueue(context), new BitmapLruCache());
    }

    public static ImageLoader newLoader(RequestQueue queue, BitmapLruCache cache) {
        return new ImageLoader(queue, cache);
    }

    public static ImageLoader newLoader(Context context, int cacheSize) {
        return newLoader(RequestFactory.newDefaultImageQueue(context),
                new BitmapLruCache(cacheSize));
    }

    public static ImageLoader newLoader(Context context, BitmapLruCache cache) {
        return newLoader(RequestFactory.newDefaultImageQueue(context), cache);
    }

    public static RequestQueue newDefaultQueue(Context context) {
        return Volley.newRequestQueue(context.getApplicationContext());
    }

    public static RequestQueue newDefaultImageQueue(Context context) {
        return newImageQueue(context.getApplicationContext(), null,
                Options.DEFAULT_POOL_SIZE);
    }

    public static RequestQueue newBackgroundQueue(Context context) {
        return newBackgroundQueue(context, null, Options.DEFAULT_POOL_SIZE);
    }

    public static RequestQueue newBackgroundQueue(Context context, HttpStack stack,
            int threadPoolSize) {
        File cacheDir = new File(context.getCacheDir(), Options.REQUEST_CACHE_PATH);

        if (stack == null) {
            stack = createStack(context);
        }

        // important part
//        int threadPoolSize = 10; // number of network dispatcher threads to create
        // pass Executor to constructor of ResponseDelivery object
        ResponseDelivery delivery = new ExecutorDelivery(
                Executors.newFixedThreadPool(threadPoolSize));

        Network network = new BasicNetwork(stack);

        // pass ResponseDelivery object as a 4th parameter for RequestQueue constructor
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network, threadPoolSize,
                delivery);
        queue.start();

        return queue;
    }

    public static RequestQueue newImageQueue(Context context, HttpStack stack, int threadPoolSize) {
        // define cache folder
        File rootCache = context.getExternalCacheDir();
        if (rootCache == null) {
            rootCache = context.getCacheDir();
        }

        File cacheDir = new File(rootCache, Options.IMAGE_CACHE_PATH);
        cacheDir.mkdirs();

        if (stack == null) {
            stack = createStack(context);
        }

        BasicNetwork network = new BasicNetwork(stack);
        DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir,
                Options.DEFAULT_DISK_USAGE_BYTES);

        RequestQueue queue = new RequestQueue(diskBasedCache, network, threadPoolSize);
        queue.start();

        return queue;
    }

    public static HttpStack createStack(Context context) {
        HttpStack result = null;

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        if (Build.VERSION.SDK_INT >= 9) {
            result = new RedirectHurlStack();
        } else {
            // Prior to Gingerbread, HttpUrlConnection was unreliable.
            // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
            result = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
        }

        return result;
    }
}
