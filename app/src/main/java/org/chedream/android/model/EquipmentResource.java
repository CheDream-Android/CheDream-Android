package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class EquipmentResource implements Parcelable {

    @SerializedName("created_at")
    private String createdAt;

    private int quantity;

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


    protected EquipmentResource(Parcel in) {
        createdAt = in.readString();
        quantity = in.readInt();
        title = in.readString();
        id = in.readString();
        quantityType = in.readString();
        if (in.readByte() == 0x01) {
            equipmentContributes = new ArrayList<EquipmentContribution>();
            in.readList(equipmentContributes, EquipmentContribution.class.getClassLoader());
        } else {
            equipmentContributes = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdAt);
        dest.writeInt(quantity);
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(quantityType);
        if (equipmentContributes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(equipmentContributes);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<EquipmentResource> CREATOR = new Parcelable.Creator<EquipmentResource>() {
        @Override
        public EquipmentResource createFromParcel(Parcel in) {
            return new EquipmentResource(in);
        }

        @Override
        public EquipmentResource[] newArray(int size) {
            return new EquipmentResource[size];
        }
    };
}
