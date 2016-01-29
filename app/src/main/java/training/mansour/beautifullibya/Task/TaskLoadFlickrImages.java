package training.mansour.beautifullibya.Task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import org.json.JSONException;

import java.util.ArrayList;

import training.mansour.beautifullibya.CallBacks.FlickrPhotosLoadedListener;
import training.mansour.beautifullibya.Extras.PhotosUtils;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.Network.VolleySinglton;

/**
 * Created by Mansour on 29/01/2016.
 */
public class TaskLoadFlickrImages extends AsyncTask<Void, Void, ArrayList<FlickrImage>>{

    private FlickrPhotosLoadedListener photosLoadedListener ;
    private VolleySinglton volleySinglton;
    private RequestQueue requestQueue;

    public TaskLoadFlickrImages (FlickrPhotosLoadedListener photosLoadedListener){
        this.photosLoadedListener = photosLoadedListener;
        volleySinglton = VolleySinglton.getInstance();
        requestQueue = volleySinglton.getRequestQueue();
    }

    @Override
    protected ArrayList<FlickrImage> doInBackground(Void... voids) {
        ArrayList<FlickrImage> flickrImages = new ArrayList<>();
        try {
            flickrImages = PhotosUtils.LoadFlickrPhotos(requestQueue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flickrImages ;
    }

    @Override
    protected void onPostExecute(ArrayList<FlickrImage> flickrImages) {
        if (photosLoadedListener != null)
            photosLoadedListener.onFlickrPhotosLoaded(flickrImages);
    }
}
