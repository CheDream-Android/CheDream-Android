package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class WorkContribution implements Parcelable {

    private int quantity;

    @SerializedName("hidden_contributor")
    private boolean hiddenContributor;

    private User user;

    private String id;

    @SerializedName("work_resource")
    private WorkResource workResource;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isHiddenContributor() {
        return hiddenContributor;
    }

    public void setHiddenContributor(boolean hiddenContributor) {
        this.hiddenContributor = hiddenContributor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WorkResource getWorkResource() {
        return workResource;
    }

    public void setWorkResource(WorkResource workResource) {
        this.workResource = workResource;
    }



    protected WorkContribution(Parcel in) {
        quantity = in.readInt();
        hiddenContributor = in.readByte() != 0x00;
        user = (User) in.readValue(User.class.getClassLoader());
        id = in.readString();
        workResource = (WorkResource) in.readValue(WorkResource.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeByte((byte) (hiddenContributor ? 0x01 : 0x00));
        dest.writeValue(user);
        dest.writeString(id);
        dest.writeValue(workResource);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WorkContribution> CREATOR = new Parcelable.Creator<WorkContribution>() {
        @Override
        public WorkContribution createFromParcel(Parcel in) {
            return new WorkContribution(in);
        }

        @Override
        public WorkContribution[] newArray(int size) {
            return new WorkContribution[size];
        }
    };
}