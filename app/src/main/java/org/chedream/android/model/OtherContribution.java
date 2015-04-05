package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class OtherContribution implements Parcelable {

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


    protected OtherContribution(Parcel in) {
        quantity = in.readInt();
        if (in.readByte() == 0x01) {
            dream = new ArrayList<Dream>();
            in.readList(dream, Dream.class.getClassLoader());
        } else {
            dream = null;
        }
        user = (User) in.readValue(User.class.getClassLoader());
        id = in.readString();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        if (dream == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(dream);
        }
        dest.writeValue(user);
        dest.writeString(id);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OtherContribution> CREATOR = new Parcelable.Creator<OtherContribution>() {
        @Override
        public OtherContribution createFromParcel(Parcel in) {
            return new OtherContribution(in);
        }

        @Override
        public OtherContribution[] newArray(int size) {
            return new OtherContribution[size];
        }
    };
}