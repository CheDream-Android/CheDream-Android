package org.chedream.android.database;

import com.google.gson.annotations.SerializedName;

import org.chedream.android.model.Picture;

import io.realm.RealmObject;

/**
 * Created by Dante Allteran on 3/29/2015.
 * Class were created only for correct work of Realm Database
 */
public class RealmPicture extends RealmObject {
    private int id;

    private String name;

    @SerializedName("provider_reference")
    private String providerReference;

    private int width;

    private int height;

    public RealmPicture() {

    }

    public RealmPicture (Picture picture) {
        id = picture.getId();
        name = picture.getName();
        providerReference = picture.getProviderReference();
        width = picture.getWidth();
        height = picture.getHeight();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderReference() {
        return providerReference;
    }

    public void setProviderReference(String providerReference) {
        this.providerReference = providerReference;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
