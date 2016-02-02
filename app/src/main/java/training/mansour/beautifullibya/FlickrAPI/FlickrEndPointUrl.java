package training.mansour.beautifullibya.FlickrAPI;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import training.mansour.beautifullibya.MyApplication;

/**
 * Created by Mansour on 26/01/2016.
 */
public class FlickrEndPointUrl {

    private static Uri.Builder builder;

    private static Uri.Builder getBaseFlickrPhotosURL(Integer Page) {

        String url = "https://api.flickr.com/services/rest/?";
        builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("api_key", MyApplication.getFlickrApiKey());
        builder.appendQueryParameter("per_page", "20");
        builder.appendQueryParameter("page", Page.toString ());
        builder.appendQueryParameter("extras", "date_taken,owner_name,description,geo");
        builder.appendQueryParameter("format", "json");
        builder.appendQueryParameter("nojsoncallback", "1");

        return  builder;
    }

    public static String getFlickrPhotosForSpecificGroupUrl(Integer Page) {
        Uri.Builder builder = Uri.parse(FlickrEndPointUrl.getBaseFlickrPhotosURL(Page).toString()).buildUpon();
        builder.appendQueryParameter("method", "flickr.groups.pools.getPhotos");
        builder.appendQueryParameter("group_id", "635414@N21");
        Log.e("PHP", "FLICKR JSON URL IS CALLED");
        return builder.toString();
    }

    public static String getFlickrFeaturedPhotosForSpecificGroupUrl(Integer Page) {
        Uri.Builder builder = Uri.parse(FlickrEndPointUrl.getBaseFlickrPhotosURL(Page).toString()).buildUpon();
        builder.appendQueryParameter("method", "flickr.groups.pools.getPhotos");
        builder.appendQueryParameter("group_id", "635414@N21");
        builder.appendQueryParameter ( "tags", "Featured" );
        Log.e("PHP", "FLICKR JSON Featured URL IS CALLED");
        return builder.toString();
    }

    public static String getFlickrPhotosByOwnerForSpecificGroupUrl(Integer Page) {
        Uri.Builder builder = Uri.parse(FlickrEndPointUrl.getBaseFlickrPhotosURL(Page).toString()).buildUpon();
        builder.appendQueryParameter("method", "flickr.groups.pools.getPhotos");
        builder.appendQueryParameter("group_id", "635414@N21");
        builder.appendQueryParameter ( "user_id", "" );
        Log.e("PHP", "FLICKR JSON Featured URL IS CALLED");
        return builder.toString();
    }

}


/*
    Arguments

    api_key (Required)
    Your API application key. See here for more details.
        group_id (Required)
        The id of the group who's pool you which to get the photo list for.
        tags (Optional)
        A tag to filter the pool with. At the moment only one tag at a time is supported.
        user_id (Optional)
        The nsid of a user. Specifying this parameter will retrieve for you only those photos that the user has contributed to the group pool.
        extras (Optional)
        A comma-delimited list of extra information to fetch for each returned record. Currently supported fields are:

        description, license, date_upload, date_taken, owner_name, icon_server, original_format, last_update, geo, tags,
        machine_tags, o_dims, views, media, path_alias, url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o

        per_page (Optional)
        Number of photos to return per page. If this argument is omitted, it defaults to 100. The maximum allowed value is 500.
        page (Optional)
        The page of results to return. If this argument is omitted, it defaults to 1.*/
