package training.mansour.beautifullibya.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import training.mansour.beautifullibya.FlickrAPI.FlickrImage;

/**
 * Created by Mansour on 28/01/2016.
 */

public class ImagesDatabase {
    private ImageHelper mImageHelper;
    private SQLiteDatabase mSQLiteDatabase ;

    public ImagesDatabase (Context context){
        mImageHelper = new ImageHelper(context);
        mSQLiteDatabase = mImageHelper.getWritableDatabase();
    }

    public void InsertFlickrImage (ArrayList<FlickrImage> mFlickrImageList, boolean clearPrevious){

        if (clearPrevious) deleteALL();

        Log.e("PHP", "on insert photos to table is called");
        String sql = "INSERT INTO " + mImageHelper.TABLE_GROUP_IMAGES + " VALUES (?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = mSQLiteDatabase.compileStatement(sql);
        mSQLiteDatabase.beginTransaction();
        for (int i = 0; i < mFlickrImageList.size(); i++){
            FlickrImage currentFlickrImage = mFlickrImageList.get(i);
            statement.clearBindings();
            statement.bindString(1, currentFlickrImage.getTitle());
            statement.bindString(2, currentFlickrImage.getImageUrl());
            statement.bindString(3, currentFlickrImage.getDateTaken());
            statement.bindString(4, currentFlickrImage.getOwnerName());
            statement.bindString(5, currentFlickrImage.getDescription());
            statement.bindString(6, currentFlickrImage.getBuddyIcon());
            statement.bindString(7, currentFlickrImage.getLatitude());
            statement.bindString(8, currentFlickrImage.getLongitude());
            statement.execute();
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    public ArrayList<FlickrImage> getAllFlickrImages () {

        ArrayList<FlickrImage> FlickrImagesLists = new ArrayList<>();
        String[] columns = {
                mImageHelper.COLUMN_TITLE,
                mImageHelper.COLUMN_IMAGE_URL,
                mImageHelper.COLUMN_DATA_TAKEN,
                mImageHelper.COLUMN_OWNER_NAME,
                mImageHelper.COLUMN_DESCRIPTION,
                mImageHelper.COLUMN_BUDDY_ICON,
                mImageHelper.COLUMN_LATITUDE,
                mImageHelper.COLUMN_LONGITUDE};

        Cursor cursor = mSQLiteDatabase.query(mImageHelper.TABLE_GROUP_IMAGES, columns, null,null,null,null,null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                FlickrImagesLists.add( new FlickrImage(
                                cursor.getString(cursor.getColumnIndex(ImageHelper.COLUMN_TITLE)),
                                cursor.getString(cursor.getColumnIndex(ImageHelper.COLUMN_IMAGE_URL)),
                                cursor.getString(cursor.getColumnIndex(ImageHelper.COLUMN_DATA_TAKEN)),
                                cursor.getString(cursor.getColumnIndex(ImageHelper.COLUMN_OWNER_NAME)),
                                cursor.getString(cursor.getColumnIndex(ImageHelper.COLUMN_DESCRIPTION)),
                                cursor.getString(cursor.getColumnIndex(ImageHelper.COLUMN_BUDDY_ICON)),
                                cursor.getString(cursor.getColumnIndex(ImageHelper.COLUMN_LATITUDE)),
                                cursor.getString(cursor.getColumnIndex(ImageHelper.COLUMN_LONGITUDE))
                    )
                );
            }
            while (cursor.moveToNext());
        }
        return FlickrImagesLists;
    }

    private void deleteALL() {
        Log.e("PHP", "clear previous columns is called");
        mSQLiteDatabase.delete(mImageHelper.TABLE_GROUP_IMAGES, null,null);
    }

    private class ImageHelper extends SQLiteOpenHelper {

        private Context context;
        private final static String DATA_BASE_NAME = "imageDatabase";
        private final static int DATA_BASE_VERSION = 1;

        public final static String TABLE_GROUP_IMAGES = "TABLE_GROUP";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_IMAGE_URL = "imageUrl";
        public final static String COLUMN_DATA_TAKEN = "dateTaken";
        public final static String COLUMN_OWNER_NAME = "ownerName";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_BUDDY_ICON = "buddyIcon";
        public final static String COLUMN_LATITUDE = "latitude";
        public final static String COLUMN_LONGITUDE = "longitude";

        private final static String CREATE_TABLE_FLICKR_GROUP = "CREATE TABLE " + TABLE_GROUP_IMAGES + " (" +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_DATA_TAKEN + " TEXT, " +
                COLUMN_OWNER_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_BUDDY_ICON + " TEXT, " +
                COLUMN_LATITUDE + " TEXT, " +
                COLUMN_LONGITUDE + " TEXT " +
                ");" ;

        public ImageHelper(Context context) {
            super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION );
            this.context = context;

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_FLICKR_GROUP);
                Log.i("PHP", "Database created");
            }catch (SQLiteException exception){
                Log.e("PHP", exception.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            try {
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + TABLE_GROUP_IMAGES + ";");
                onCreate(sqLiteDatabase);
                Log.e("PHP", "Table " + TABLE_GROUP_IMAGES + " Dropped");
            } catch (SQLiteException exception){
                Log.e("PHP", exception.toString());
            }
        }
    }
}
