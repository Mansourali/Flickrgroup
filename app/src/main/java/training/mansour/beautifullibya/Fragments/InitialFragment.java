package training.mansour.beautifullibya.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;

import com.android.volley.DefaultRetryPolicy;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import training.mansour.beautifullibya.MyApplication;
import training.mansour.beautifullibya.Network.FadeInImageListener;
import training.mansour.beautifullibya.Network.VolleySinglton;
import training.mansour.beautifullibya.R;

public class InitialFragment extends Fragment {

    private static final String FLICKR_REQUEST_TAG = "FLICKR_LIBYA_BEST_PHOTO_GROUP";


    /*private  VolleySinglton volleySinglton;
    private ImageLoader imageLoader ;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private ProgressDialog mProgress;
    private List<DataModel> mDataList;
    private EfficientAdapter mAdapter;
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volleySinglton = VolleySinglton.getInstance();
        requestQueue = volleySinglton.getRequestQueue();
        makeSampleHttpRequest();

    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.initial_fragment, container, false);

        //RequestQueue requestQueue = VolleySinglton.getInstance().getRequestQueue();

/*        mDataList = new ArrayList<DataModel>();

        ListView mListView = (ListView) layout.findViewById(R.id.image_list);

        mAdapter = new EfficientAdapter(mActivity);

        mListView.setAdapter(mAdapter);

                showProgress();
                makeSampleHttpRequest();*/

        return layout;
    }

    /*

    public void onDestroy() {
        super.onDestroy();
    }

    public void onStop() {
        super.onStop();
        if(mProgress != null)
            mProgress.dismiss();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
    }

    private void makeSampleHttpRequest() {

        String url = "https://api.flickr.com/services/rest/?";
        Uri.Builder builder = Uri.parse(url).buildUpon();
		builder.appendQueryParameter("api_key", MyApplication.getFlickrApiKey());
		builder.appendQueryParameter("method", "flickr.groups.pools.getPhotos");
        builder.appendQueryParameter("group_id","635414@N21");
		builder.appendQueryParameter("format", "json");
		builder.appendQueryParameter("nojsoncallback", "1");

         jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, builder.toString(), (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseFlickrImageResponse(response);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("JSON parse error");
                }
                stopProgress();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                // For AuthFailure, you can re login with user credentials.
                // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                // In this case you can check how client is forming the api and debug accordingly.
                // For ServerError 5xx, you can do retry or handle accordingly.
                if( error instanceof NetworkError) {
                } else if( error instanceof ClientError) {
                } else if( error instanceof ServerError) {
                } else if( error instanceof AuthFailureError) {
                } else if( error instanceof ParseError) {
                } else if( error instanceof NoConnectionError) {
                } else if( error instanceof TimeoutError) {
                }

                stopProgress();
                showToast(error.getMessage());
            }
        });

        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setTag(FLICKR_REQUEST_TAG);
        requestQueue.add(jsonObjectRequest);
    }


    private void showProgress() {
        mProgress = ProgressDialog.show(getActivity(), "", "Loading...");
    }

    private void stopProgress() {
        mProgress.cancel();
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    private void parseFlickrImageResponse(JSONObject response) throws JSONException {

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
                    DataModel model = new DataModel();
                    model.setImageUrl(imageUrl);
                    model.setTitle(jsonObj.getString("title"));
                    mDataList.add(model);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class DataModel {
        private String mImageUrl;
        private String mTitle;

        public String getImageUrl() {
            return mImageUrl;
        }
        public void setImageUrl(String mImageUrl) {
            this.mImageUrl = mImageUrl;
        }
        public String getTitle() {
            return mTitle;
        }
        public void setTitle(String mTitle) {
            this.mTitle = mTitle;
        }

    }

    private  class EfficientAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public EfficientAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return mDataList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.title.setText(mDataList.get(position).getTitle());
            System.out.println("###### Image Url ###### "+mDataList.get(position).getImageUrl());
            imageLoader.get(mDataList.get(position).getImageUrl(), new FadeInImageListener(holder.image,mActivity));

*/
/*            mImageLoader.get(mDataList.get(position).getImageUrl(),
            							ImageLoader.getImageListener(holder.image, R.drawable.flickr, android.R.drawable.ic_dialog_alert),
            							//You can specify width & height of the bitmap to be scaled down when the image is downloaded.
            							50,50); *//*

            return convertView;
        }

        class ViewHolder {
            TextView title;
            ImageView image;
        }

    }
*/


}
