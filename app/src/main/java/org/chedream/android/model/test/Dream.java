package org.chedream.android.model.test;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.chedream.android.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.RealmObject;

public class Dream extends RealmObject implements Serializable {
    private String title;
    private String description;
    private String image;

    private int likes;

    private int moneyMax;
    private int moneyCurrent;

    private int toolsMax;
    private int toolsCurrent;

    private int peopleMax;
    private int peopleCurrent;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setMoneyMax(int moneyMax) {
        this.moneyMax = moneyMax;
    }

    public void setMoneyCurrent(int moneyCurrent) {
        this.moneyCurrent = moneyCurrent;
    }

    public void setToolsMax(int toolsMax) {
        this.toolsMax = toolsMax;
    }

    public void setToolsCurrent(int toolsCurrent) {
        this.toolsCurrent = toolsCurrent;
    }

    public void setPeopleMax(int peopleMax) {
        this.peopleMax = peopleMax;
    }

    public void setPeopleCurrent(int peopleCurrent) {
        this.peopleCurrent = peopleCurrent;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public int getLikes() {
        return likes;
    }

    public int getMoneyMax() {
        return moneyMax;
    }

    public int getMoneyCurrent() {
        return moneyCurrent;
    }

    public int getToolsMax() {
        return toolsMax;
    }

    public int getToolsCurrent() {
        return toolsCurrent;
    }

    public int getPeopleMax() {
        return peopleMax;
    }

    public int getPeopleCurrent() {
        return peopleCurrent;
    }

//    public boolean isMoney() {
//        return moneyMax != 0;
//    }
//
//    public boolean isPeople() {
//        return peopleMax != 0;
//    }
//
//    public boolean isTools() {
//        return toolsMax != 0;
//    }

//    public Dream setTitle(String title) {
//        this.title = title;
//        return this;
//    }
//
//    public Dream setDescription(String description) {
//        this.description = description;
//        return this;
//    }
//
//    public Dream setImage(String image) {
//        this.image = image;
//        return this;
//    }
//
//    public Dream setLikes(int likes) {
//        this.likes = likes;
//        return this;
//    }
//
//    public Dream setMoneyMax(int moneyMax) {
//        this.moneyMax = moneyMax;
//        return this;
//    }
//
//    public Dream setMoneyCurrent(int moneyCurrent) {
//        this.moneyCurrent = moneyCurrent;
//        return this;
//    }
//
//    public Dream setToolsMax(int toolsMax) {
//        this.toolsMax = toolsMax;
//        return this;
//    }
//
//    public Dream setToolsCurrent(int toolsCurrent) {
//        this.toolsCurrent = toolsCurrent;
//        return this;
//    }
//
//    public Dream setPeopleMax(int peopleMax) {
//        this.peopleMax = peopleMax;
//        return this;
//    }
//
//    public Dream setPeopleCurrent(int peopleCurrent) {
//        this.peopleCurrent = peopleCurrent;
//        return this;
//    }

}