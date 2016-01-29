package training.mansour.beautifullibya.Extras;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import training.mansour.beautifullibya.FlickrAPI.FlickrEndPointUrl;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.FlickrAPI.FlickrJSONparser;
import training.mansour.beautifullibya.FlickrAPI.Requestor;
import training.mansour.beautifullibya.MyApplication;

/**
 * Created by Mansour on 29/01/2016.
 */
public class PhotosUtils {
    public static ArrayList<FlickrImage> LoadFlickrPhotos(RequestQueue requestQueue) throws JSONException {
        JSONObject response = Requestor.requestFlickrPhotosJSON(requestQueue, FlickrEndPointUrl.getFlickrPhotosForSpecificGroupUrl());
        ArrayList<FlickrImage> flickrImages = FlickrJSONparser.parseFlickrImageResponse(response);
        MyApplication.getWritableDatabase().InsertFlickrImage(flickrImages, true);
        return flickrImages;
    }
}
