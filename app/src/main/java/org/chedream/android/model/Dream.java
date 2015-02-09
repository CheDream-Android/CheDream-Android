package org.chedream.android.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Dream {

    private String id;

    private String title;

    private String description;

    @SerializedName("rejected_description")
    private String rejectedDescription;

    @SerializedName("implemented_description")
    private String implementedDescription;

    @SerializedName("completed_description")
    private String completedDescription;

    private String phone;

    private String slug;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("expired_date")
    private String expiredDate;

    @SerializedName("financial_completed")
    private int financialCompleted;

    @SerializedName("work_completed")
    private int workCompleted;

    @SerializedName("equipment_completed")
    private int equipmentCompleted;

    @SerializedName("users_who_favorites")
    private ArrayList<User> usersWhoFavorites;

    private User author;

    @SerializedName("current_status")
    private String currentStatus;

    @SerializedName("dream_financial_resources")
    private ArrayList<FinancialResource> dreamFinancialResources;

    @SerializedName("dream_equipment_resources")
    private ArrayList<EquipmentResource> dreamEquipmentResources;

    @SerializedName("dream_work_resources")
    private ArrayList<WorkResource> dreamWorkResources;

    @SerializedName("dream_financial_contributions")
    private ArrayList<FinancialContribution> dreamFinancialContributions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRejectedDescription() {
        return rejectedDescription;
    }

    public void setRejectedDescription(String rejectedDescription) {
        this.rejectedDescription = rejectedDescription;
    }

    public String getImplementedDescription() {
        return implementedDescription;
    }

    public void setImplementedDescription(String implementedDescription) {
        this.implementedDescription = implementedDescription;
    }

    public String getCompletedDescription() {
        return completedDescription;
    }

    public void setCompletedDescription(String completedDescription) {
        this.completedDescription = completedDescription;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public int getFinancialCompleted() {
        return financialCompleted;
    }

    public void setFinancialCompleted(int financialCompleted) {
        this.financialCompleted = financialCompleted;
    }

    public int getWorkCompleted() {
        return workCompleted;
    }

    public void setWorkCompleted(int workCompleted) {
        this.workCompleted = workCompleted;
    }

    public int getEquipmentCompleted() {
        return equipmentCompleted;
    }

    public void setEquipmentCompleted(int equipmentCompleted) {
        this.equipmentCompleted = equipmentCompleted;
    }

    public ArrayList<User> getUsersWhoFavorites() {
        return usersWhoFavorites;
    }

    public void setUsersWhoFavorites(ArrayList<User> usersWhoFavorites) {
        this.usersWhoFavorites = usersWhoFavorites;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public ArrayList<FinancialResource> getDreamFinancialResources() {
        return dreamFinancialResources;
    }

    public void setDreamFinancialResources(ArrayList<FinancialResource> dreamFinancialResources) {
        this.dreamFinancialResources = dreamFinancialResources;
    }

    public ArrayList<EquipmentResource> getDreamEquipmentResources() {
        return dreamEquipmentResources;
    }

    public void setDreamEquipmentResources(ArrayList<EquipmentResource> dreamEquipmentResources) {
        this.dreamEquipmentResources = dreamEquipmentResources;
    }

    public ArrayList<WorkResource> getDreamWorkResources() {
        return dreamWorkResources;
    }

    public void setDreamWorkResources(ArrayList<WorkResource> dreamWorkResources) {
        this.dreamWorkResources = dreamWorkResources;
    }

    public ArrayList<FinancialContribution> getDreamFinancialContributions() {
        return dreamFinancialContributions;
    }

    public void setDreamFinancialContributions(ArrayList<FinancialContribution> dreamFinancialContributions) {
        this.dreamFinancialContributions = dreamFinancialContributions;
    }
}
