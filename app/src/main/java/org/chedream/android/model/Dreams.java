package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Dreams implements Parcelable {

    @SerializedName("self_page")
    private String selfPage;

    @SerializedName("next_page")
    private String nextPage;

    @SerializedName("prev_page")
    private String prevPage;

    @SerializedName("first_page")
    private String firstPage;

    @SerializedName("last_page")
    private String lastPage;

    private ArrayList<Dream> dreams;

    public String getSelfPage() {
        return selfPage;
    }

    public void setSelfPage(String selfPage) {
        this.selfPage = selfPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(String prevPage) {
        this.prevPage = prevPage;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public ArrayList<Dream> getDreams() {
        return dreams;
    }

    public void setDreams(ArrayList<Dream> dreams) {
        this.dreams = dreams;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.selfPage);
        dest.writeString(this.nextPage);
        dest.writeString(this.prevPage);
        dest.writeString(this.firstPage);
        dest.writeString(this.lastPage);
        dest.writeSerializable(this.dreams);
    }

    public Dreams() {
    }

    private Dreams(Parcel in) {
        this.selfPage = in.readString();
        this.nextPage = in.readString();
        this.prevPage = in.readString();
        this.firstPage = in.readString();
        this.lastPage = in.readString();
        this.dreams = (ArrayList<Dream>) in.readSerializable();
    }

    public static final Parcelable.Creator<Dreams> CREATOR = new Parcelable.Creator<Dreams>() {
        public Dreams createFromParcel(Parcel source) {
            return new Dreams(source);
        }

        public Dreams[] newArray(int size) {
            return new Dreams[size];
        }
    };
}
