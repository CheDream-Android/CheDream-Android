package org.chedream.android.database;

import com.google.gson.annotations.SerializedName;

import org.chedream.android.model.User;

import io.realm.RealmObject;

/**
 * Created by Dante Allteran on 3/28/2015.
 * Class were created only for correct work of Realm Database
 */
public class RealmUser extends RealmObject {
    private int id;

    private String username;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public RealmUser() {
    }

    public RealmUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        if (user.getAvatar().getProviderReference() != null) {
            this.avatar = user.getAvatar().getProviderReference();
        }
    }
}