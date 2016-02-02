package training.mansour.beautifullibya.Services;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;
import training.mansour.beautifullibya.CallBacks.FlickrPhotosLoadedListener;
import training.mansour.beautifullibya.FlickrAPI.FlickrEndPointUrl;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.FlickrAPI.FlickrJSONparser;
import training.mansour.beautifullibya.MyApplication;
import training.mansour.beautifullibya.Network.VolleySinglton;
import training.mansour.beautifullibya.Task.TaskLoadFlickrImages;

/**
 * Created by Mansour on 28/01/2016.
 */

public class MyService extends JobService implements FlickrPhotosLoadedListener{
    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters params) {
        new TaskLoadFlickrImages(this).execute(1);
        jobParameters = params;
        Log.i("PHP", "MY-SERVICE JOB STARTED");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    @Override
    public void onFlickrPhotosLoaded(ArrayList<FlickrImage> flickrImages) {
        jobFinished(jobParameters, false);
    }

}
