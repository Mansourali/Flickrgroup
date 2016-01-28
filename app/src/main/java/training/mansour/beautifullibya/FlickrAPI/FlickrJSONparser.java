package training.mansour.beautifullibya.FlickrAPI;

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
        if(response.has("photos")) {
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

                    String imageUrl = "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_t.jpg";

                    JSONObject descriptionContent = jsonObj.getJSONObject("description");

                    mImages.add(new FlickrImage(jsonObj.getString("title"),
                            imageUrl,
                            jsonObj.getString("datetaken"),
                            jsonObj.getString("ownername"),
                            descriptionContent.getString("_content")));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mImages;
    }

    //TODO
    private boolean contains (JSONObject parsrObject, String key){
        return (parsrObject != null && parsrObject.has(key) && !parsrObject.isNull(key)) ? true : false ;
    }


}
