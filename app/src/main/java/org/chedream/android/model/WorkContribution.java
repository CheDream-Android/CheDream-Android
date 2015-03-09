package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quantity);
        dest.writeByte(hiddenContributor ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.id);
        dest.writeParcelable(this.workResource, flags);
    }

    public WorkContribution() {
    }

    private WorkContribution(Parcel in) {
        this.quantity = in.readInt();
        this.hiddenContributor = in.readByte() != 0;
        this.user = in.readParcelable(User.class.getClassLoader());
        this.id = in.readString();
        this.workResource = in.readParcelable(WorkResource.class.getClassLoader());
    }

    public static final Creator<WorkContribution> CREATOR = new Creator<WorkContribution>() {
        public WorkContribution createFromParcel(Parcel source) {
            return new WorkContribution(source);
        }

        public WorkContribution[] newArray(int size) {
            return new WorkContribution[size];
        }
    };
}
