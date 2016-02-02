package training.mansour.beautifullibya.Task;

import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONException;

import java.util.ArrayList;

import training.mansour.beautifullibya.CallBacks.FlickrPhotosLoadedListener;
import training.mansour.beautifullibya.Extras.PhotosUtils;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.MyApplication;
import training.mansour.beautifullibya.Network.VolleySinglton;

/**
 * Created by Mansour on 29/01/2016.
 */
public class TaskLoadFlickrImages extends AsyncTask<Integer, Void, ArrayList<FlickrImage>>{

    private FlickrPhotosLoadedListener photosLoadedListener ;
    private VolleySinglton volleySinglton;
    private RequestQueue requestQueue;

    public TaskLoadFlickrImages (FlickrPhotosLoadedListener photosLoadedListener){
        this.photosLoadedListener = photosLoadedListener;
        volleySinglton = VolleySinglton.getInstance();
        requestQueue = volleySinglton.getRequestQueue();
    }

    @Override
    protected ArrayList<FlickrImage> doInBackground(Integer... Page) {
        ArrayList<FlickrImage> flickrImages = new ArrayList<>();
        Integer page = Page[0];
        try {
            flickrImages = PhotosUtils.LoadFlickrPhotos(requestQueue, page);
        } catch (JSONException e) {
            Toast.makeText(MyApplication.getAppContext(), "Error when connecting to Flickr", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return flickrImages;
    }

    @Override
    protected void onPostExecute(ArrayList<FlickrImage> flickrImages) {
        if (photosLoadedListener != null)
            photosLoadedListener.onFlickrPhotosLoaded(flickrImages);
    }
}
