package training.mansour.beautifullibya.Services;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;
import training.mansour.beautifullibya.FlickrAPI.FlickrEndPointUrl;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.FlickrAPI.FlickrJSONparser;
import training.mansour.beautifullibya.MyApplication;
import training.mansour.beautifullibya.Network.VolleySinglton;
import training.mansour.beautifullibya.R;

/**
 * Created by Mansour on 28/01/2016.
 */
public class MyService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        new myTask(this).execute(params);
        Log.e("PHP", "MYservice JOB STARTED");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private static class myTask extends AsyncTask<JobParameters, Void, JobParameters> {
        MyService myService;
        private VolleySinglton volleySinglton;
        private RequestQueue requestQueue;


        myTask(MyService myService) {
            volleySinglton = VolleySinglton.getInstance();
            requestQueue = volleySinglton.getRequestQueue();
            this.myService = myService;
            Log.e("PHP", "MyAsyncTask is constructed");

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JobParameters doInBackground(JobParameters... jobParameterses) {

            Log.e("PHP", "Do in background method called");
            ArrayList<FlickrImage> flickrImageArrayList = new ArrayList<>();

            try {
                flickrImageArrayList = FlickrJSONparser.parseFlickrImageResponse(sendJSONrequest());
                Log.e("PHP", "Parsing Flickr photo in background");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MyApplication.getWritableDatabase().InsertFlickrImage(flickrImageArrayList, true);
            return jobParameterses[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            super.onPostExecute(jobParameters);
            myService.jobFinished(jobParameters, false);
        }

        private String getFlickrPhotosUrl() {
            Uri.Builder builder = Uri.parse(FlickrEndPointUrl.getBaseFlickrPhotosURL().toString()).buildUpon();
            builder.appendQueryParameter("method", "flickr.groups.pools.getPhotos");
            builder.appendQueryParameter("group_id", "635414@N21");
            Log.e("PHP", "FLICKR JSON URL IS CALLED");
            return builder.toString();
        }

        private JSONObject sendJSONrequest() {
            JSONObject response = null;
            RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    getFlickrPhotosUrl(),
                    (String) null,
                    requestFuture,
                    requestFuture);

            requestQueue.add(jsonObjectRequest);

            Log.e("PHP", "SEND JSON REQUEST IS CALLED");

            try {
                response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e("PHP", "InterruptedException");
            } catch (ExecutionException e) {
                Log.e("PHP", "ExecutionException");
                e.printStackTrace();
            } catch (TimeoutException e) {
                Log.e("PHP", "TimeoutException");
                e.printStackTrace();
            }
            return response;
        }
    }
}
