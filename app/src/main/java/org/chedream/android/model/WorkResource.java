package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkResource implements Parcelable {

    private int quantity;

    private ArrayList<Dream> dream; //???

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

    public ArrayList<Dream> getDream() {
        return dream;
    }

    public void setDream(ArrayList<Dream> dream) {
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


    protected WorkResource(Parcel in) {
        quantity = in.readInt();
        if (in.readByte() == 0x01) {
            dream = new ArrayList<Dream>();
            in.readList(dream, Dream.class.getClassLoader());
        } else {
            dream = null;
        }
        title = in.readString();
        id = in.readString();
        if (in.readByte() == 0x01) {
            workContributions = new ArrayList<WorkContribution>();
            in.readList(workContributions, WorkContribution.class.getClassLoader());
        } else {
            workContributions = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        if (dream == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(dream);
        }
        dest.writeString(title);
        dest.writeString(id);
        if (workContributions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(workContributions);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WorkResource> CREATOR = new Parcelable.Creator<WorkResource>() {
        @Override
        public WorkResource createFromParcel(Parcel in) {
            return new WorkResource(in);
        }

        @Override
        public WorkResource[] newArray(int size) {
            return new WorkResource[size];
        }
    };
}