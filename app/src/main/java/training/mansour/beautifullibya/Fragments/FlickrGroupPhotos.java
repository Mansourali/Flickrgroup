package training.mansour.beautifullibya.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import training.mansour.beautifullibya.Adapter.FlickrPhotoAdapter;
import training.mansour.beautifullibya.CallBacks.FlickrPhotosLoadedListener;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.MyApplication;
import training.mansour.beautifullibya.R;
import training.mansour.beautifullibya.Task.TaskLoadFlickrImages;


public class FlickrGroupPhotos extends Fragment implements FlickrPhotosLoadedListener{

    private static final String STATE_FLICKE = "state_flickr";
    private ArrayList<FlickrImage> mFlickrPhotoList;
    private RecyclerView recyclerView;
    private FlickrPhotoAdapter flickrPhotoAdapter;
    private TextView volleyError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFlickrPhotoList = new ArrayList<>();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_FLICKE, mFlickrPhotoList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_flickr_group, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.fragment_recycler_view);
        volleyError = (TextView) layout.findViewById(R.id.fragment_volley_error);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        flickrPhotoAdapter = new FlickrPhotoAdapter(getActivity());
        recyclerView.setAdapter(flickrPhotoAdapter);

        if (savedInstanceState != null){
            mFlickrPhotoList = savedInstanceState.getParcelableArrayList(STATE_FLICKE);
            Toast.makeText(getActivity(), "comes from a rotation", Toast.LENGTH_LONG).show();
        } else {
            mFlickrPhotoList = MyApplication.getWritableDatabase().getAllFlickrImages();
            Toast.makeText(getActivity(), "First time data", Toast.LENGTH_LONG).show();
            if (mFlickrPhotoList.isEmpty()){
                new TaskLoadFlickrImages(this).execute();
            }
        }
        flickrPhotoAdapter.setFlickrImages(mFlickrPhotoList);
        return layout;
    }

    @Override
    public void onFlickrPhotosLoaded(ArrayList<FlickrImage> flickrImages) {
        flickrPhotoAdapter.setFlickrImages(flickrImages);
    }
}
