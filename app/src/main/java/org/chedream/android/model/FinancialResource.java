package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FinancialResource implements Parcelable {

    private int quantity;

    private Dream dream;

    private String title;

    private String id;

    @SerializedName("financial_contributes")
    private ArrayList<FinancialContribution> financialContributes;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<FinancialContribution> getFinancialContributes() {
        return financialContributes;
    }

    public void setFinancialContributes(ArrayList<FinancialContribution> financialContributes) {
        this.financialContributes = financialContributes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quantity);
        dest.writeParcelable(this.dream, 0);
        dest.writeString(this.title);
        dest.writeString(this.id);
        dest.writeSerializable(this.financialContributes);
    }

    public FinancialResource() {
    }

    private FinancialResource(Parcel in) {
        this.quantity = in.readInt();
        this.dream = in.readParcelable(Dream.class.getClassLoader());
        this.title = in.readString();
        this.id = in.readString();
        this.financialContributes = (ArrayList<FinancialContribution>) in.readSerializable();
    }

    public static final Creator<FinancialResource> CREATOR = new Creator<FinancialResource>() {
        public FinancialResource createFromParcel(Parcel source) {
            return new FinancialResource(source);
        }

        public FinancialResource[] newArray(int size) {
            return new FinancialResource[size];
        }
    };
}
