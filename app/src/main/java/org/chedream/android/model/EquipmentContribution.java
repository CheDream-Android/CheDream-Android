package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class EquipmentContribution implements Parcelable {

    @SerializedName("created_at")
    private String createdAt;

    private int quantity;

    @SerializedName("hidden_contributor")
    private boolean hiddenContributor;

    private User user;

    private String id;

    @SerializedName("equipment_resource")
    private EquipmentResource equipmentResource;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

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

    public EquipmentResource getEquipmentResource() {
        return equipmentResource;
    }

    public void setEquipmentResource(EquipmentResource equipmentResource) {
        this.equipmentResource = equipmentResource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createdAt);
        dest.writeInt(this.quantity);
        dest.writeByte(hiddenContributor ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.id);
        dest.writeParcelable(this.equipmentResource, flags);
    }

    public EquipmentContribution() {
    }

    private EquipmentContribution(Parcel in) {
        this.createdAt = in.readString();
        this.quantity = in.readInt();
        this.hiddenContributor = in.readByte() != 0;
        this.user = in.readParcelable(User.class.getClassLoader());
        this.id = in.readString();
        this.equipmentResource = in.readParcelable(EquipmentResource.class.getClassLoader());
    }

    public static final Creator<EquipmentContribution> CREATOR = new Creator<EquipmentContribution>() {
        public EquipmentContribution createFromParcel(Parcel source) {
            return new EquipmentContribution(source);
        }

        public EquipmentContribution[] newArray(int size) {
            return new EquipmentContribution[size];
        }
    };
}
