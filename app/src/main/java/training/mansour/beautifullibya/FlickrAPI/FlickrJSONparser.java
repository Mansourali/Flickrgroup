package training.mansour.beautifullibya.FlickrAPI;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mansour on 27/01/2016.
 */
public class FlickrJSONparser {

    private static ArrayList<FlickrImage> mDataList = new ArrayList<>();

    public static ArrayList<FlickrImage> parseFlickrImageResponse(JSONObject response) throws JSONException {

        ArrayList<FlickrImage> mImages = new ArrayList<>();

        if(contains(response,"photos")) {

            try {
                JSONObject photos = response.getJSONObject("photos");
                JSONArray items = photos.getJSONArray("photo");

                mDataList.clear();

                for(int index = 0 ; index < items.length(); index++) {

                    JSONObject jsonObj = items.getJSONObject(index);

                    String farm = jsonObj.getString("farm");
                    String id = jsonObj.getString("id");
                    String secret = jsonObj.getString("secret");
                    String server = jsonObj.getString("server");

                    String imageUrl = "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_c.jpg";

                    String userNSID = jsonObj.getString ( "owner" );
                    String buddyIcon = "http://flickr.com/buddyicons/" + userNSID + ".jpg";

                    String dateString = jsonObj.getString("datetaken");
                    String datetaken  = dateString.split(" ")[0];

                    JSONObject descriptionContent = jsonObj.getJSONObject("description");

                    mImages.add(new FlickrImage(jsonObj.getString("title"),
                            imageUrl,
                            datetaken,
                            jsonObj.getString("ownername"),
                            descriptionContent.getString("_content"),
                            buddyIcon,
                            jsonObj.getString("latitude"),
                            jsonObj.getString("longitude"))
                    );
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.i ( "PHP", "Method Flickr JSON parser with error: " + e.toString () );
            }
        }
        return mImages;
    }

    //TODO
    private static boolean contains (JSONObject parsrObject, String key){
        return (parsrObject != null && parsrObject.has(key) && !parsrObject.isNull(key)) ? true : false ;
    }

}
