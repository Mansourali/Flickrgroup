package training.mansour.beautifullibya.CallBacks;

import java.util.ArrayList;

import training.mansour.beautifullibya.FlickrAPI.FlickrImage;

/**
 * Created by Mansour on 29/01/2016.
 */
public interface FlickrPhotosLoadedListener {
    public void onFlickrPhotosLoaded (ArrayList<FlickrImage> flickrImages);
}
