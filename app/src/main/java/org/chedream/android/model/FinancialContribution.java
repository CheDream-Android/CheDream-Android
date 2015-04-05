package org.chedream.android.model;

import com.google.gson.annotations.SerializedName;


public class FinancialContribution {

    private int id;

    private int quantity;

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
        return financialResource;
    }

    public void setFinancialResourcse(FinancialResource financialResourcse) {
        this.financialResource = financialResourcse;
    }
}
