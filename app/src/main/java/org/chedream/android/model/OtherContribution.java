package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OtherContribution implements Parcelable {

    private int quantity;

    private Dream dream;

    private User user;

    private String id;

    private String title;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quantity);
        dest.writeParcelable(this.dream, 0);
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.id);
        dest.writeString(this.title);
    }

    public OtherContribution() {
    }

    private OtherContribution(Parcel in) {
        this.quantity = in.readInt();
        this.dream = in.readParcelable(Dream.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
        this.id = in.readString();
        this.title = in.readString();
    }

    public static final Creator<OtherContribution> CREATOR = new Creator<OtherContribution>() {
        public OtherContribution createFromParcel(Parcel source) {
            return new OtherContribution(source);
        }

        public OtherContribution[] newArray(int size) {
            return new OtherContribution[size];
        }
    };
}
