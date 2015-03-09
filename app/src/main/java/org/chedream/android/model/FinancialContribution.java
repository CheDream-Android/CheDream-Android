package org.chedream.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FinancialContribution implements Parcelable {

    private int quantity;

    @SerializedName("hidden_contributor")
    private boolean hiddenContributor;

    private User user;

    @SerializedName("financial_resource")
    private FinancialResource financialResourcse;

    public int getQuantity() {
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
        return financialResourcse;
    }

    public void setFinancialResourcse(FinancialResource financialResourcse) {
        this.financialResourcse = financialResourcse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quantity);
        dest.writeByte(hiddenContributor ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.user, 0);
        dest.writeParcelable(this.financialResourcse, flags);
    }

    public FinancialContribution() {
    }

    private FinancialContribution(Parcel in) {
        this.quantity = in.readInt();
        this.hiddenContributor = in.readByte() != 0;
        this.user = in.readParcelable(User.class.getClassLoader());
        this.financialResourcse = in.readParcelable(FinancialResource.class.getClassLoader());
    }

    public static final Creator<FinancialContribution> CREATOR = new Creator<FinancialContribution>() {
        public FinancialContribution createFromParcel(Parcel source) {
            return new FinancialContribution(source);
        }

        public FinancialContribution[] newArray(int size) {
            return new FinancialContribution[size];
        }
    };
}
