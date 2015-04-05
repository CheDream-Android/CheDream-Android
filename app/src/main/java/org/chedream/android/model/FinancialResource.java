package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FinancialResource implements Parcelable {

    private int quantity;

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


    protected FinancialResource(Parcel in) {
        quantity = in.readInt();
        if (in.readByte() == 0x01) {
            dream = new ArrayList<Dream>();
            in.readList(dream, Dream.class.getClassLoader());
        } else {
            dream = null;
        }
        title = in.readString();
        id = in.readString();
        if (in.readByte() == 0x01) {
            financialContributes = new ArrayList<FinancialContribution>();
            in.readList(financialContributes, FinancialContribution.class.getClassLoader());
        } else {
            financialContributes = null;
        }
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
        dest.writeString(title);
        dest.writeString(id);
        if (financialContributes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(financialContributes);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FinancialResource> CREATOR = new Parcelable.Creator<FinancialResource>() {
        @Override
        public FinancialResource createFromParcel(Parcel in) {
            return new FinancialResource(in);
        }

        @Override
        public FinancialResource[] newArray(int size) {
            return new FinancialResource[size];
        }
    };
}
