package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EquipmentResource implements Parcelable {

    @SerializedName("created_at")
    private String createdAt;

    private int quantity;

    private Dream dream;

    private String title;

    private String id;

    @SerializedName("quantity_type")
    private String quantityType;

    @SerializedName("equipment_contributes")
    private ArrayList<EquipmentContribution> equipmentContributes;

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

    public String getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }

    public ArrayList<EquipmentContribution> getEquipmentContributes() {
        return equipmentContributes;
    }

    public void setEquipmentContributes(ArrayList<EquipmentContribution> equipmentContributes) {
        this.equipmentContributes = equipmentContributes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createdAt);
        dest.writeInt(this.quantity);
        dest.writeParcelable(this.dream, 0);
        dest.writeString(this.title);
        dest.writeString(this.id);
        dest.writeString(this.quantityType);
        dest.writeSerializable(this.equipmentContributes);
    }

    public EquipmentResource() {
    }

    private EquipmentResource(Parcel in) {
        this.createdAt = in.readString();
        this.quantity = in.readInt();
        this.dream = in.readParcelable(Dream.class.getClassLoader());
        this.title = in.readString();
        this.id = in.readString();
        this.quantityType = in.readString();
        this.equipmentContributes = (ArrayList<EquipmentContribution>) in.readSerializable();
    }

    public static final Creator<EquipmentResource> CREATOR = new Creator<EquipmentResource>() {
        public EquipmentResource createFromParcel(Parcel source) {
            return new EquipmentResource(source);
        }

        public EquipmentResource[] newArray(int size) {
            return new EquipmentResource[size];
        }
    };
}
