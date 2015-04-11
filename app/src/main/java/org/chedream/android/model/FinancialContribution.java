package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class FinancialContribution implements Parcelable {

    private int id;

    private long quantity;

    @SerializedName("hidden_contributor")
    private boolean hiddenContributor;

    private User user;

    @SerializedName("financial_resource")
    private FinancialResource financialResource;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isHiddenContributor() {
        return hiddenContributor;
    }

    public void setHiddenContributor(boolean hiddenContributor) {
        this.hiddenContributor = hiddenContributor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FinancialResource getFinancialResourcse() {
        return financialResource;
    }

    public void setFinancialResourcse(FinancialResource financialResourcse) {
        this.financialResource = financialResourcse;
    }

    protected FinancialContribution(Parcel in) {
        id = in.readInt();
        quantity = in.readLong();
        hiddenContributor = in.readByte() != 0x00;
        user = (User) in.readValue(User.class.getClassLoader());
        financialResource = (FinancialResource) in.readValue(FinancialResource.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(quantity);
        dest.writeByte((byte) (hiddenContributor ? 0x01 : 0x00));
        dest.writeValue(user);
        dest.writeValue(financialResource);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FinancialContribution> CREATOR = new Parcelable.Creator<FinancialContribution>() {
        @Override
        public FinancialContribution createFromParcel(Parcel in) {
            return new FinancialContribution(in);
        }

        @Override
        public FinancialContribution[] newArray(int size) {
            return new FinancialContribution[size];
        }
    };
}
