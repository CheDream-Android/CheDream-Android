package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FAQ implements Parcelable {

    private String title;

    private String question;

    private String answer;

    private String slug;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.question);
        dest.writeString(this.answer);
        dest.writeString(this.slug);
    }

    public FAQ() {
    }

    private FAQ(Parcel in) {
        this.title = in.readString();
        this.question = in.readString();
        this.answer = in.readString();
        this.slug = in.readString();
    }

    public static final Creator<FAQ> CREATOR = new Creator<FAQ>() {
        public FAQ createFromParcel(Parcel source) {
            return new FAQ(source);
        }

        public FAQ[] newArray(int size) {
            return new FAQ[size];
        }
    };
}
