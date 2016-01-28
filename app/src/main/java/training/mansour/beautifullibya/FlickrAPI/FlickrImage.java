package training.mansour.beautifullibya.FlickrAPI;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Holds the data for Flickr photo that is used to display Flickr Images in ListViews.
 * 
 * @author Mani Selvaraj
 *
 */
public class FlickrImage implements  Parcelable{

	private String title;
	private String imageUrl;
	private String dateTaken;
    private String ownerName;
    private String description;

    public FlickrImage (String title, String imageUrl, String dateTaken, String ownerName, String description){
        setTitle(title);
        setImageUrl(imageUrl);
        setDateTaken(dateTaken);
        setOwnerName(ownerName);
        setDescription(description);
    }

    protected FlickrImage(Parcel in) {
        title = in.readString();
        imageUrl = in.readString();
        dateTaken = in.readString();
        ownerName = in.readString();
        description = in.readString();
    }

    public static final Creator<FlickrImage> CREATOR = new Creator<FlickrImage>() {
        @Override
        public FlickrImage createFromParcel(Parcel in) {
            return new FlickrImage(in);
        }

        @Override
        public FlickrImage[] newArray(int size) {
            return new FlickrImage[size];
        }
    };

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public String getImageUrl() { return imageUrl;  }

    public void setImageUrl(String imageUrl) {  this.imageUrl = imageUrl;  }

    public String getDateTaken() {  return dateTaken;  }

    public void setDateTaken(String dateTaken) {  this.dateTaken = dateTaken;   }

    public String getOwnerName() {  return ownerName;   }

    public void setOwnerName(String ownerName) {   this.ownerName = ownerName;    }

    public String getDescription() {  return description;   }

    public void setDescription(String description) {  this.description = description;    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(imageUrl);
        parcel.writeString(dateTaken);
        parcel.writeString(ownerName);
        parcel.writeString(description);
    }

}
