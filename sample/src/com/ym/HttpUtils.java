package com.ym;

import android.net.Uri;

/**
 * Created by Yakiv M. on 25.03.14
 */

public class HttpUtils {

    public static String createTestUrl() {
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("http");
        uri.authority("httpbin.org");
        uri.path("get");
        uri.appendQueryParameter("name", "Jon Doe");
        uri.appendQueryParameter("age", "21");

        return uri.build().toString();
    }
}
