package training.mansour.beautifullibya.FlickrAPI;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import training.mansour.beautifullibya.MyApplication;

/**
 * Created by Mansour on 26/01/2016.
 */
public class FlickrEndPointUrl {

    private static Uri.Builder builder;

    public static Uri.Builder getBaseFlickrPhotosURL() {

        String url = "https://api.flickr.com/services/rest/?";
        builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("api_key", MyApplication.getFlickrApiKey());
        builder.appendQueryParameter("per_page", "10");
        builder.appendQueryParameter("page", "1");
        builder.appendQueryParameter("extras", "date_taken,owner_name,description");
        builder.appendQueryParameter("format", "json");
        builder.appendQueryParameter("nojsoncallback", "1");

        return  builder;
    }

}
