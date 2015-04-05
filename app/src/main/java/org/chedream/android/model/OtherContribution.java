package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import io.realm.RealmObject;

public class OtherContribution {

    private int quantity;

    private ArrayList<Dream> dream; //WTF??!

    private User user;

    private String id;

    private String title;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<Dream> getDream() {
        return dream;
    }

    public void setDream(ArrayList<Dream> dream) {
        this.dream = dream;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
