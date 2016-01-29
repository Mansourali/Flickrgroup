package training.mansour.beautifullibya;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import training.mansour.beautifullibya.Database.ImagesDatabase;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;

/**
 * Created by Mansour on 26/01/2016.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static ImagesDatabase FlickrImageDB ;
    public static final String FLICKR_API_KEY = "ee91e6f9cedcf5a3f9951d69671117c0";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getInstance() { return mInstance; }

    public synchronized static ImagesDatabase getWritableDatabase(){
        if (FlickrImageDB == null )
            FlickrImageDB = new ImagesDatabase(getAppContext());
        Log.e("PHP", "My application database created");
        return FlickrImageDB;
    }

    public static String getFlickrApiKey () { return FLICKR_API_KEY; }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }
}
