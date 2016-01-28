package training.mansour.beautifullibya.Network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import training.mansour.beautifullibya.MyApplication;

/**
 * Created by Mansour on 26/01/2016.
 */
public class VolleySinglton {

    private static VolleySinglton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader ;

    private VolleySinglton (){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> imageCache = new LruCache<>((int)Runtime.getRuntime().maxMemory()/1024/8);
            @Override
            public Bitmap getBitmap(String s) {
                return imageCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                imageCache.put(s, bitmap);
            }
        });
    }

    public static VolleySinglton getInstance(){
        if (mInstance == null){
            mInstance = new VolleySinglton();
        }
        return  mInstance;
    }

    public RequestQueue getRequestQueue (){
        return mRequestQueue;
    }
    public ImageLoader getImageLoader () { return imageLoader; }
}
