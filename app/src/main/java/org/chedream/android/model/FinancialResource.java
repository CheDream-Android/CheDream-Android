package org.chedream.android.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FinancialResource {

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
}