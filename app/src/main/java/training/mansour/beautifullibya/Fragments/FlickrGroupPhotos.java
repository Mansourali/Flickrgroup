package training.mansour.beautifullibya.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import training.mansour.beautifullibya.Activity.MainActivity;
import training.mansour.beautifullibya.Adapter.FlickrPhotoAdapter;
import training.mansour.beautifullibya.CallBacks.FlickrPhotosLoadedListener;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.MyApplication;
import training.mansour.beautifullibya.Network.CheckNetwork;
import training.mansour.beautifullibya.R;
import training.mansour.beautifullibya.Task.TaskLoadFlickrImages;


public class FlickrGroupPhotos extends Fragment implements
        FlickrPhotosLoadedListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String STATE_FLICKE = "state_flickr";
    private ArrayList<FlickrImage> mFlickrPhotoList;
    private RecyclerView recyclerView;
    private FlickrPhotoAdapter flickrPhotoAdapter;
    private TextView volleyError;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Integer CurrentPage = 1;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        mFlickrPhotoList = new ArrayList<> ( );
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState ( outState );
        outState.putParcelableArrayList ( STATE_FLICKE, mFlickrPhotoList );
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View layout = inflater.inflate ( R.layout.fragment_flickr_group, container, false );

        recyclerView = (RecyclerView) layout.findViewById ( R.id.fragment_recycler_view );
        volleyError = (TextView) layout.findViewById ( R.id.fragment_volley_error );
        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_for_more);
        swipeRefreshLayout.setOnRefreshListener(this);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new GridLayoutManager (getActivity (), 2));

        flickrPhotoAdapter = new FlickrPhotoAdapter(getActivity());
        recyclerView.setAdapter(flickrPhotoAdapter);

        if (savedInstanceState != null) {
            mFlickrPhotoList = savedInstanceState.getParcelableArrayList(STATE_FLICKE);
        } else {
            if (new CheckNetwork(getActivity()).isNetworkAvailable()) {
                new TaskLoadFlickrImages(this).execute(CurrentPage);
                Toast.makeText(getActivity(), "Get photos from Flickr", Toast.LENGTH_SHORT).show();
            }
            else {
                mFlickrPhotoList = MyApplication.getWritableDatabase().getAllFlickrImages();
                Toast.makeText(getActivity(), "Get photos from Database", Toast.LENGTH_SHORT).show();
            }
        }

        flickrPhotoAdapter.setFlickrImages ( mFlickrPhotoList );

        return layout;
    }

    private void loadMoreData(Integer currentPage) {
        new TaskLoadFlickrImages(this).execute(currentPage);
    }

    @Override
    public void onFlickrPhotosLoaded (ArrayList<FlickrImage> flickrImages) {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        flickrPhotoAdapter.setFlickrImages(flickrImages);
    }

    @Override
    public void onRefresh() {
        if (!new CheckNetwork(getActivity()).isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No internet!!", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
        else
        new TaskLoadFlickrImages(this).execute(CurrentPage);
    }

}
