package training.mansour.beautifullibya.FlickrAPI;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mansour on 29/01/2016.
 */

public class Requestor {
    public static JSONObject requestFlickrPhotosJSON(RequestQueue requestQueue, String FlickrPhtotoUrl) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                FlickrPhtotoUrl,
                (String)null, requestFuture, requestFuture);


        requestQueue.add(request);
        try {
            response = requestFuture.get(12, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Log.e("PHP", e.toString());
        } catch (ExecutionException e) {
            Log.e("PHP", e.toString());
        } catch (TimeoutException e) {
            Log.e("PHP", e.toString());
        }
        return response;
    }
}