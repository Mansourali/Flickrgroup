package training.mansour.beautifullibya.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import training.mansour.beautifullibya.Adapter.FlickrPhotoAdapter;
import training.mansour.beautifullibya.FlickrAPI.FlickrEndPointUrl;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.FlickrAPI.FlickrJSONparser;
import training.mansour.beautifullibya.Network.VolleySinglton;
import training.mansour.beautifullibya.R;


public class FlickrGroupPhotos extends Fragment {

    private static final String STATE_FLICKE = "state_flickr";
    private VolleySinglton volleySinglton;
    private RequestQueue requestQueue;
    private ArrayList<FlickrImage> mDataList;
    private RecyclerView recyclerView;
    private FlickrPhotoAdapter flickrPhotoAdapter;
    private TextView volleyError;

    private String getFlickrPhotosUrl () {
        Uri.Builder builder = Uri.parse(FlickrEndPointUrl.getBaseFlickrPhotosURL().toString()).buildUpon();
        builder.appendQueryParameter("method", "flickr.groups.pools.getPhotos");
        builder.appendQueryParameter("group_id","635414@N21");
        return builder.toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataList = new ArrayList<>();
        volleySinglton = VolleySinglton.getInstance();
        requestQueue = volleySinglton.getRequestQueue();
        sendJSONrequest();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_FLICKE, mDataList);
    }

    private void sendJSONrequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getFlickrPhotosUrl(), (String)null,
            new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyError.setVisibility(View.GONE);
                try {
                    mDataList = FlickrJSONparser.parseFlickrImageResponse(response);
                    flickrPhotoAdapter.setFlickrImages(mDataList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"JSON parse error", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                // For AuthFailure, you can re login with user credentials.
                // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                // In this case you can check how client is forming the api and debug accordingly.
                // For ServerError 5xx, you can do retry or handle accordingly.
                volleyError.setVisibility(View.VISIBLE);
                if( error instanceof NetworkError) {
                    volleyError.setText(R.string.network_error);
                } else if( error instanceof ServerError) {
                    volleyError.setText(R.string.server_error);
                } else if( error instanceof AuthFailureError) {
                    volleyError.setText(R.string.auth_failure_error);
                } else if( error instanceof ParseError) {
                    volleyError.setText(R.string.parse_error);
                } else if( error instanceof NoConnectionError || error instanceof TimeoutError) {
                    volleyError.setText(R.string.error_timeout);
                }
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_flickr_group, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.fragment_recycler_view);
        volleyError = (TextView) layout.findViewById(R.id.fragment_volley_error);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        flickrPhotoAdapter = new FlickrPhotoAdapter(getActivity());
        recyclerView.setAdapter(flickrPhotoAdapter);

        if (savedInstanceState != null){
            mDataList = savedInstanceState.getParcelableArrayList(STATE_FLICKE);
            flickrPhotoAdapter.setFlickrImages(mDataList);
            Toast.makeText(getActivity(), "comes from a rotation", Toast.LENGTH_LONG).show();
        } else {
            sendJSONrequest();
            Toast.makeText(getActivity(), "First time data", Toast.LENGTH_LONG).show();
        }
        return layout;
    }

}
