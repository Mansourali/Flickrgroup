package training.mansour.beautifullibya.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import training.mansour.beautifullibya.Extras.FadeInImageListener;
import training.mansour.beautifullibya.FlickrAPI.FlickrImage;
import training.mansour.beautifullibya.MyApplication;
import training.mansour.beautifullibya.Network.VolleySinglton;
import training.mansour.beautifullibya.R;

/**
 * Created by Mansour on 27/01/2016.
 */
public class FlickrPhotoAdapter extends RecyclerView.Adapter<FlickrPhotoAdapter.ViewHolderFlickrGroup> {

        private ArrayList<FlickrImage> flickrImages = new ArrayList<>();
        private LayoutInflater layoutInflater;
        private VolleySinglton volleySinglton;
        private ImageLoader imageLoader;

    public FlickrPhotoAdapter (Context context) {
            layoutInflater = LayoutInflater.from(context);
            volleySinglton = VolleySinglton.getInstance();
            imageLoader = volleySinglton.getImageLoader();
        }

        public void setFlickrImages (ArrayList<FlickrImage> flickrImages){
            this.flickrImages = flickrImages ;
            notifyItemRangeChanged(0, flickrImages.size());
        }

        @Override
        public ViewHolderFlickrGroup onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.list_item_normal_image, parent, false);
            ViewHolderFlickrGroup viewHolderFlickrGroup = new ViewHolderFlickrGroup(view);
            return viewHolderFlickrGroup;
        }

        @Override
        public void onBindViewHolder(final ViewHolderFlickrGroup holder, int position) {

             holder.dateTakenTextView.setText(flickrImages.get(position).getDateTaken ());
             holder.ownernameTextView.setText(flickrImages.get(position).getOwnerName());

            imageLoader.get(flickrImages.get(position).getImageUrl(),
                    new FadeInImageListener(holder.flcikrImageView, MyApplication.getAppContext()));

            imageLoader.get(flickrImages.get(position).getBuddyIcon(),
                    new FadeInImageListener(holder.buddyIconImage, MyApplication.getAppContext()));

        }

        @Override
        public int getItemCount() {
            return flickrImages.size();
        }

        static class ViewHolderFlickrGroup extends RecyclerView.ViewHolder {

            private ImageView flcikrImageView;
            private TextView dateTakenTextView;
            private TextView ownernameTextView;
            private ImageView buddyIconImage;

            public ViewHolderFlickrGroup(View itemView) {
                super(itemView);
                flcikrImageView = (ImageView) itemView.findViewById(R.id.list_view_image);
                dateTakenTextView = (TextView) itemView.findViewById(R.id.item_image_date);
                ownernameTextView = (TextView) itemView.findViewById(R.id.item_image_author);
                buddyIconImage = (ImageView) itemView.findViewById(R.id.owner_buddy_icon);
            }
        }
}

