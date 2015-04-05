package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import io.realm.RealmObject;

public class Dream implements Parcelable {
    private int id;

    private String title;

    private String description;

    private String phone;

    private String slug;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("deleted_at")
    private String deletedAt;

    private User author;

    @SerializedName("current_status")
    private String currentStatus;

    @SerializedName("media_pictures")
    private ArrayList<Picture> mediaPictures;

    @SerializedName("media_completed_pictures")
    private ArrayList<Picture> mediaCompletedPictures;

    @SerializedName("media_poster")
    private Picture mediaPoster;

    @SerializedName("dream_financial_resources")
    private ArrayList<FinancialResource> dreamFinancialResources;

    @SerializedName("dream_equipment_resources")
    private ArrayList<EquipmentResource> dreamEquipmentResources;

    @SerializedName("dream_work_resources")
    private ArrayList<WorkResource> dreamWorkResources;

    @SerializedName("dream_financial_contributions")
    private ArrayList<FinancialContribution> dreamFinancialContributions;

    @SerializedName("dream_equipment_contributions")
    private ArrayList<EquipmentContribution> dreamEquipmentContributions;

    @SerializedName("dream_work_contributions")
    private ArrayList<WorkContribution> dreamWorkContributions;

    @SerializedName("dream_other_contributions")
    private ArrayList<OtherContribution> dreamOtherContributions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
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

    public ArrayList<Picture> getMediaPictures() {
        return mediaPictures;
    }

    public void setMediaPictures(ArrayList<Picture> mediaPictures) {
        this.mediaPictures = mediaPictures;
    }

    public ArrayList<Picture> getMediaCompletedPictures() {
        return mediaCompletedPictures;
    }

    public void setMediaCompletedPictures(ArrayList<Picture> mediaCompletedPictures) {
        this.mediaCompletedPictures = mediaCompletedPictures;
    }

    public Picture getMediaPoster() {
        return mediaPoster;
    }

    public void setMediaPoster(Picture mediaPoster) {
        this.mediaPoster = mediaPoster;
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

    public ArrayList<EquipmentContribution> getDreamEquipmentContributions() {
        return dreamEquipmentContributions;
    }

    public void setDreamEquipmentContributions(ArrayList<EquipmentContribution> dreamEquipmentContributions) {
        this.dreamEquipmentContributions = dreamEquipmentContributions;
    }

    public ArrayList<WorkContribution> getDreamWorkContributions() {
        return dreamWorkContributions;
    }

    public void setDreamWorkContributions(ArrayList<WorkContribution> dreamWorkContributions) {
        this.dreamWorkContributions = dreamWorkContributions;
    }

    public ArrayList<OtherContribution> getDreamOtherContributions() {
        return dreamOtherContributions;
    }

    public void setDreamOtherContributions(ArrayList<OtherContribution> dreamOtherContributions) {
        this.dreamOtherContributions = dreamOtherContributions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.phone);
        dest.writeString(this.slug);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.deletedAt);
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.currentStatus);
        dest.writeSerializable(this.mediaPictures);
        dest.writeSerializable(this.mediaCompletedPictures);
        dest.writeParcelable(this.mediaPoster, flags);
        dest.writeSerializable(this.dreamFinancialResources);
        dest.writeSerializable(this.dreamEquipmentResources);
        dest.writeSerializable(this.dreamWorkResources);
        dest.writeSerializable(this.dreamFinancialContributions);
        dest.writeSerializable(this.dreamEquipmentContributions);
        dest.writeSerializable(this.dreamWorkContributions);
        dest.writeSerializable(this.dreamOtherContributions);
    }

    public Dream() {
    }

    private Dream(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.phone = in.readString();
        this.slug = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.deletedAt = in.readString();
        this.author = in.readParcelable(User.class.getClassLoader());
        this.currentStatus = in.readString();
        this.mediaPictures = (ArrayList<Picture>) in.readSerializable();
        this.mediaCompletedPictures = (ArrayList<Picture>) in.readSerializable();
        this.mediaPoster = in.readParcelable(Picture.class.getClassLoader());
        this.dreamFinancialResources = (ArrayList<FinancialResource>) in.readSerializable();
        this.dreamEquipmentResources = (ArrayList<EquipmentResource>) in.readSerializable();
        this.dreamWorkResources = (ArrayList<WorkResource>) in.readSerializable();
        this.dreamFinancialContributions = (ArrayList<FinancialContribution>) in.readSerializable();
        this.dreamEquipmentContributions = (ArrayList<EquipmentContribution>) in.readSerializable();
        this.dreamWorkContributions = (ArrayList<WorkContribution>) in.readSerializable();
        this.dreamOtherContributions = (ArrayList<OtherContribution>) in.readSerializable();
    }

    public static final Parcelable.Creator<Dream> CREATOR = new Parcelable.Creator<Dream>() {
        public Dream createFromParcel(Parcel source) {
            return new Dream(source);
        }

        public Dream[] newArray(int size) {
            return new Dream[size];
        }
    };
}
