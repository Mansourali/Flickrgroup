package training.mansour.beautifullibya.Extras;

import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import training.mansour.beautifullibya.FlickrAPI.FlickrEndPointUrl;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.FlickrAPI.FlickrJSONparser;
import training.mansour.beautifullibya.FlickrAPI.Requester;
import training.mansour.beautifullibya.MyApplication;

/**
 * Created by Mansour on 29/01/2016.
 */
public class PhotosUtils {
    public static ArrayList<FlickrImage> LoadFlickrPhotos(RequestQueue requestQueue,Integer Page) throws JSONException {
        ArrayList<FlickrImage> flickrImages = new ArrayList<>();
        JSONObject response = Requester.requestFlickrPhotosJSON(requestQueue, FlickrEndPointUrl.getFlickrPhotosForSpecificGroupUrl(Page));
        if (response != null) {
            flickrImages = FlickrJSONparser.parseFlickrImageResponse(response);
            MyApplication.getWritableDatabase().InsertFlickrImage(flickrImages, true);
        }
        //else //Can't create handler inside thread that has not called Looper.prepare()
          //  Toast.makeText(MyApplication.getAppContext(), "Error getting Flickr photos", Toast.LENGTH_SHORT).show();
        return flickrImages;
    }
}
