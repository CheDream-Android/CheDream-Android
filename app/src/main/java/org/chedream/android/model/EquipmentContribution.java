package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EquipmentContribution  implements Parcelable {

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

    protected EquipmentContribution(Parcel in) {
        createdAt = in.readString();
        quantity = in.readInt();
        hiddenContributor = in.readByte() != 0x00;
        user = (User) in.readValue(User.class.getClassLoader());
        id = in.readString();
        equipmentResource = (EquipmentResource) in.readValue(EquipmentResource.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdAt);
        dest.writeInt(quantity);
        dest.writeByte((byte) (hiddenContributor ? 0x01 : 0x00));
        dest.writeValue(user);
        dest.writeString(id);
        dest.writeValue(equipmentResource);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<EquipmentContribution> CREATOR = new Parcelable.Creator<EquipmentContribution>() {
        @Override
        public EquipmentContribution createFromParcel(Parcel in) {
            return new EquipmentContribution(in);
        }

        @Override
        public EquipmentContribution[] newArray(int size) {
            return new EquipmentContribution[size];
        }
    };
}