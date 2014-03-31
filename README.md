### Volley Request Manager - Lite

[You can find article here][0]

```java
// init component for request processing
RequestManager.initializeWith(getApplicationContext());

// create request
Request request = new JsonObjectRequest(
        Request.Method.GET,
        "request url here",
        null,
        mListener,
        mErrorListener);
        
// process request with default queue      
RequestManager.queue().add(request);
```

```java
// init component that for image loading
ImageManager.initializeWith(getApplicationContext());

// load image with default ImageLoader
ImageManager.loader().get(
        "http://farm6.staticflickr.com/5475/10375875123_75ce3080c6_b.jpg",
        mImageListener);
        
// load image with NetworkImageView
NetworkImageView view = new NetworkImageView(context);
  
view.view.setImageUrl(
        "http://farm6.staticflickr.com/5475/10375875123_75ce3080c6_b.jpg",
        ImageManager.loader()); // to use default ImageLoader
```

[0]: https://github.com/yakivmospan/yakivmospan/blob/master/articles/android/http/Volley%20Request%20Manager%20-%20Lite.md
