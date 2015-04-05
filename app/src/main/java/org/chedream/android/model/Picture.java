package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.chedream.android.database.RealmPicture;

public class Picture implements Parcelable {

    private int id;

    private String name;

    @SerializedName("provider_reference")
    private String providerReference;

    private int width;

    private int height;

    public Picture (RealmPicture realmPicture) {
        this.id = realmPicture.getId();
        this.name = realmPicture.getName();
        this.providerReference = realmPicture.getProviderReference();
        this.width = realmPicture.getWidth();
        this.height = realmPicture.getHeight();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public void setProviderReference(String providerReference) {
        this.providerReference = providerReference;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.providerReference);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    public Picture() {
    }

    private Picture(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.providerReference = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
