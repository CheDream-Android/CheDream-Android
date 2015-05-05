package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DreamsContainer implements Parcelable {

    private Dream dream;

    public Dream getDream() {
        return dream;
    }

    public void setDream(Dream dream) {
        this.dream = dream;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.dream, 0);
    }

    public DreamsContainer() {
    }

    private DreamsContainer(Parcel in) {
        this.dream = in.readParcelable(Dream.class.getClassLoader());
    }

    public static final Parcelable.Creator<DreamsContainer> CREATOR = new Parcelable.Creator<DreamsContainer>() {
        public DreamsContainer createFromParcel(Parcel source) {
            return new DreamsContainer(source);
        }

        public DreamsContainer[] newArray(int size) {
            return new DreamsContainer[size];
        }
    };
}
