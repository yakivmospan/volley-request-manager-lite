### Volley Request Manager - Lite

[You can find arcticle here][0]

```java
// init component that for request processing
RequestManager.initializeWith(getApplicationContext());

// create request
Request request = new JsonObjectRequest(
        Request.Method.GET,
        "request url here",
        null,
        mListener,
        mErrorListener);
        
// process request with default queue      
RequestManager.queue().doRequest(request);

// process request with custom queue      
RequestManager.queue().doRequest(request, customQueue);
```

```java
// init component that for image loading
ImageManager.initializeWith(getApplicationContext());

// load image with defaul ImageLoader
ImageManager.loader().doLoad(
        "http://farm6.staticflickr.com/5475/10375875123_75ce3080c6_b.jpg",
        mImageListener);
        
// load image with cusmot ImageLoader
ImageManager.loader().doLoad(
        "http://farm6.staticflickr.com/5475/10375875123_75ce3080c6_b.jpg",
        mImageListener,
        customImageLoader);
        
// load image with NetworkImageView
NetworkImageView view = new NetworkImageView(context);

ImageManager.loader().doLoad(
        "http://farm6.staticflickr.com/5475/10375875123_75ce3080c6_b.jpg",
        view);        
  
 view.view.setImageUrl(
        "http://farm6.staticflickr.com/5475/10375875123_75ce3080c6_b.jpg",
        ImageManager.loader.instance()); // to use default ImageLoader       
```

[0]: https://github.com/yakivmospan/yakivmospan/blob/master/articles/android/http/Volley%20Request%20Manager%20-%20Lite.md
