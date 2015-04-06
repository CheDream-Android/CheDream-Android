package org.chedream.android.database;

import com.google.gson.annotations.SerializedName;

import org.chedream.android.model.Dream;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Dante Allteran on 3/28/2015.
 * Class were created only for correct work of Realm Database
 */
@RealmClass
public class RealmDream extends RealmObject {

    @PrimaryKey
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

    @SerializedName("users_who_favorites")
    private RealmList<RealmUser> usersWhoFavorites;

    private RealmUser author;

    @SerializedName("current_status")
    private String currentStatus;

    @SerializedName("media_pictures")
    private RealmList<RealmPicture> mediaPictures;

    @SerializedName("media_completed_pictures")
    private RealmList<RealmPicture> mediaCompletedPictures;

    @SerializedName("media_poster")
    private RealmPicture mediaPoster;

    private int finResQuantity;
    private int finContribQuantity;

    private int equipResQuantity;
    private int equipContribQuantity;

    private int workResQuantity;
    private int workContribQuantity;

    public int getFinResQuantity() {
        return finResQuantity;
    }

    public void setFinResQuantity(int finResQuantity) {
        this.finResQuantity = finResQuantity;
    }

    public int getFinContribQuantity() {
        return finContribQuantity;
    }

    public void setFinContribQuantity(int finContribQuantity) {
        this.finContribQuantity = finContribQuantity;
    }

    public int getEquipResQuantity() {
        return equipResQuantity;
    }

    public void setEquipResQuantity(int equipResQuantity) {
        this.equipResQuantity = equipResQuantity;
    }

    public int getEquipContribQuantity() {
        return equipContribQuantity;
    }

    public void setEquipContribQuantity(int equipContribQuantity) {
        this.equipContribQuantity = equipContribQuantity;
    }

    public int getWorkResQuantity() {
        return workResQuantity;
    }

    public void setWorkResQuantity(int workResQuantity) {
        this.workResQuantity = workResQuantity;
    }

    public int getWorkContribQuantity() {
        return workContribQuantity;
    }

    public void setWorkContribQuantity(int workContribQuantity) {
        this.workContribQuantity = workContribQuantity;
    }

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

    public RealmList<RealmUser> getUsersWhoFavorites() {
        return usersWhoFavorites;
    }

    public void setUsersWhoFavorites(RealmList<RealmUser> usersWhoFavorites) {
        this.usersWhoFavorites = usersWhoFavorites;
    }

    public RealmUser getAuthor() {
        return author;
    }

    public void setAuthor(RealmUser author) {
        this.author = author;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public RealmList<RealmPicture> getMediaPictures() {
        return mediaPictures;
    }

    public void setMediaPictures(RealmList<RealmPicture> mediaPictures) {
        this.mediaPictures = mediaPictures;
    }

    public RealmList<RealmPicture> getMediaCompletedPictures() {
        return mediaCompletedPictures;
    }

    public void setMediaCompletedPictures(RealmList<RealmPicture> mediaCompletedPictures) {
        this.mediaCompletedPictures = mediaCompletedPictures;
    }

    public RealmPicture getMediaPoster() {
        return mediaPoster;
    }

    public void setMediaPoster(RealmPicture mediaPoster) {
        this.mediaPoster = mediaPoster;
    }


    public RealmDream() {

    }

    public RealmDream(Dream dream, RealmUser authorRealm, RealmList<RealmUser> usersWhoFavoritesRealm,
                      RealmPicture mediaPoster, RealmList<RealmPicture> mediaPictures,
                      RealmList<RealmPicture> mediaCompletedPictures) {
        this.id = dream.getId();
        this.title = dream.getTitle();
        this.description = dream.getDescription();
        this.phone = dream.getPhone();
        this.slug = dream.getSlug();
        this.createdAt = dream.getCreatedAt();
        this.updatedAt = dream.getUpdatedAt();
        this.deletedAt = dream.getDeletedAt();
        this.currentStatus = dream.getCurrentStatus();

        this.author = authorRealm;
        this.usersWhoFavorites = usersWhoFavoritesRealm;
        this.mediaPoster = mediaPoster;
        this.mediaPictures = mediaPictures;
        this.mediaCompletedPictures = mediaCompletedPictures;
    }
}
