package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EquipmentResource {

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

}
