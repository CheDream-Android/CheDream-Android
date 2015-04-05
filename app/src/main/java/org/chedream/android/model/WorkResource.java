package org.chedream.android.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WorkResource {

    private int quantity;

    private ArrayList<Dream> dream; //???

    private String title;

    private String id;

    @SerializedName("work_contributions")
    private ArrayList<WorkContribution> workContributions;

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

    public ArrayList<WorkContribution> getWorkContributions() {
        return workContributions;
    }

    public void setWorkContributions(ArrayList<WorkContribution> workContributions) {
        this.workContributions = workContributions;
    }

}
