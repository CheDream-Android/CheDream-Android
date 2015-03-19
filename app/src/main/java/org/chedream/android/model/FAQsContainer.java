package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FAQsContainer implements Parcelable {

    public FAQsContainer() {
    }

    private List<FAQ> faqs;

    public List<FAQ> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<FAQ> faqs) {
        this.faqs = faqs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(faqs);
    }

    private FAQsContainer(Parcel in) {
        in.readTypedList(faqs, FAQ.CREATOR);
    }

    public static final Creator<FAQsContainer> CREATOR = new Creator<FAQsContainer>() {
        public FAQsContainer createFromParcel(Parcel source) {
            return new FAQsContainer(source);
        }

        public FAQsContainer[] newArray(int size) {
            return new FAQsContainer[size];
        }
    };
}
