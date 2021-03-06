package training.mansour.beautifullibya;

import android.app.Application;
import android.content.Context;

/**
 * Created by Mansour on 26/01/2016.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    public static final String FLICKR_API_KEY = "";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static String getFlickrApiKey () { return FLICKR_API_KEY; }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }
}
