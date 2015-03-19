package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WorkResource implements Parcelable {

    private int quantity;

    private Dream dream;

    private String title;

    private String id;

    @SerializedName("work_contributions")
    private ArrayList<WorkContribution> workContributions;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Dream getDream() {
        return dream;
    }

    public void setDream(Dream dream) {
        this.dream = dream;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<WorkContribution> getWorkContributions() {
        return workContributions;
    }

    public void setWorkContributions(ArrayList<WorkContribution> workContributions) {
        this.workContributions = workContributions;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quantity);
        dest.writeParcelable(this.dream, 0);
        dest.writeString(this.title);
        dest.writeString(this.id);
        dest.writeSerializable(this.workContributions);
    }

    public WorkResource() {
    }

    private WorkResource(Parcel in) {
        this.quantity = in.readInt();
        this.dream = in.readParcelable(Dream.class.getClassLoader());
        this.title = in.readString();
        this.id = in.readString();
        this.workContributions = (ArrayList<WorkContribution>) in.readSerializable();
    }

    public static final Creator<WorkResource> CREATOR = new Creator<WorkResource>() {
        public WorkResource createFromParcel(Parcel source) {
            return new WorkResource(source);
        }

        public WorkResource[] newArray(int size) {
            return new WorkResource[size];
        }
    };
}
