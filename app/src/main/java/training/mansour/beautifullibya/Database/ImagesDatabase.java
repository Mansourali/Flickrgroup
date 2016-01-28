package training.mansour.beautifullibya.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

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

        String sql = "INSERT INTO " + mImageHelper.TABLE_GROUP_IMAGES + " VALUES (?,?,?,?,?;)";
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
            statement.execute();
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    public ArrayList<FlickrImage> getAllFlickrImages () {
        ArrayList<FlickrImage> FlickrImagesLists = new ArrayList<>();
        String[] columns = {mImageHelper.COLUMN_TITLE, mImageHelper.COLUMN_IMAGE_URL,
        mImageHelper.COLUMN_DATA_TAKEN, mImageHelper.COLUMN_OWNER_NAME, mImageHelper.COLUMN_DESCRIPTION};


    }

    private void deleteALL() {

    }

    private class ImageHelper extends SQLiteOpenHelper {
        private Context context;
        private final static String DATA_BASE_NAME = "imagesDB";
        private final static int DATA_BASE_VERSION = 1;
        public final static String TABLE_GROUP_IMAGES = "TABLE_GROUP";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_IMAGE_URL = "imageUrl";
        public final static String COLUMN_DATA_TAKEN = "dateTaken";
        public final static String COLUMN_OWNER_NAME = "ownerName";
        public final static String COLUMN_DESCRIPTION = "description";
        private final static String CREATE_TABLE_FLICKR_GROUP = "CREATE TABLE " + TABLE_GROUP_IMAGES + " (" +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_IMAGE_URL + " TEXT, " +
                COLUMN_DATA_TAKEN + " TEXT, " +
                COLUMN_OWNER_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                ");" ;

        public ImageHelper(Context context) {
            super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION );
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_FLICKR_GROUP);
                Toast.makeText(context, "Database created", Toast.LENGTH_LONG).show();
            }catch (SQLiteException exception){
                Toast.makeText(context, exception.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL("DROP TABLE" + TABLE_GROUP_IMAGES + "IF EXIST;");
                onCreate(sqLiteDatabase);
                Toast.makeText(context, "Database updated", Toast.LENGTH_LONG).show();
            }catch (SQLiteException exception){
                Toast.makeText(context, exception.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
