package org.chedream.android.model;

import com.google.gson.annotations.SerializedName;

public class EquipmentContribution {

    @SerializedName("created_at")
    private String createdAt;

    private int quantity;

    @SerializedName("hidden_contributor")
    private boolean hiddenContributor;

    private User user;

    private String id;

    @SerializedName("hidden_contributor")
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
}
