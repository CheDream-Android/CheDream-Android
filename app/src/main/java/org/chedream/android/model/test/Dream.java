package org.chedream.android.model.test;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.chedream.android.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dream implements Serializable {
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

    public boolean isMoney() {
        return moneyMax != 0;
    }

    public boolean isPeople() {
        return peopleMax != 0;
    }

    public boolean isTools() {
        return toolsMax != 0;
    }

    public Dream setTitle(String title) {
        this.title = title;
        return this;
    }

    public Dream setDescription(String description) {
        this.description = description;
        return this;
    }

    public Dream setImage(String image) {
        this.image = image;
        return this;
    }

    public Dream setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public Dream setMoneyMax(int moneyMax) {
        this.moneyMax = moneyMax;
        return this;
    }

    public Dream setMoneyCurrent(int moneyCurrent) {
        this.moneyCurrent = moneyCurrent;
        return this;
    }

    public Dream setToolsMax(int toolsMax) {
        this.toolsMax = toolsMax;
        return this;
    }

    public Dream setToolsCurrent(int toolsCurrent) {
        this.toolsCurrent = toolsCurrent;
        return this;
    }

    public Dream setPeopleMax(int peopleMax) {
        this.peopleMax = peopleMax;
        return this;
    }

    public Dream setPeopleCurrent(int peopleCurrent) {
        this.peopleCurrent = peopleCurrent;
        return this;
    }

    public static List<Dream> getDreams(Context context) {
        List<Dream> dreams = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            dreams.add(getRandomDream(context, i));
        }
        return dreams;
    }

    public static Dream getRandomDream(Context context, int i) {
        Resources res = context.getResources();
        Random rand = new Random();
        String description = titles[i];
        String title = titles[i];
        String image = images[i];
        Log.d("lalka", " i = " + i + "; + link = " + image);
        int likes = rand.nextInt(15);

        int money = rand.nextInt(101);
        int people = rand.nextInt(101);
        int tools = rand.nextInt(101);

        int moneyMax = 100;
        int peopleMax = 100;
        int toolsMax = 100;

        if (rand.nextInt(15) == 0) {
            moneyMax = 0;
        }

        if (rand.nextInt(15) == 0) {
            peopleMax = 0;
        }

        return new Dream()
                .setTitle(title)
                .setDescription(description)
                .setImage(image)
                .setLikes(likes)
                .setMoneyMax(moneyMax)
                .setMoneyCurrent(money)
                .setPeopleMax(peopleMax)
                .setPeopleCurrent(people)
                .setToolsMax(toolsMax)
                .setToolsCurrent(tools);
    }

    private static final String[] titles = new String[] {
            "Стіна справжніх бажань \"Доки я живий\"",
            "Черкаський фестиваль кіно",
            "Створення парашутного спорт-клубу в Черкасах",
            "Максі-сингл \"21 грам\" гурту \"Сонце в кишені\"",
            "Електронний підручник!",
            "2 лавки для майбутнього скверу",
            "Сайт фестиваля WindFestival",
            "Виклик.com",
            "Казкова алея",
            "Проектор для творчої молоді",
            "Покраска подъезда и прилегающей территории",
    };

    private static final String[] images = new String[] {
            "http://chedream.org/upload/media/poster/0001/01/dde3e992fe23eb8ff88ff4ff07ae5ca63ad34d9e.jpeg",
            "http://chedream.org/upload/media/poster/0001/01/2521fefae8bb8d5a49a83d6b92f52060445d2285.jpeg",
            "http://chedream.org/upload/media/poster/0001/01/610086ae4448f903ca787813d35a33467bb6bc8d.jpeg",
            "http://chedream.org/upload/media/poster/0001/01/4f78cd20a92f4f88c9a0bce0bfd1242a5a91f46b.jpeg",
            "http://chedream.org/upload/media/poster/0001/01/3efd7733f202450bab46d1d9b118961464edcec0.jpeg",
            "http://chedream.org/upload/media/poster/0001/01/33b2ee6dedf46baf7e2cd1aceae5727370fa7fdd.jpeg",
            "http://chedream.org/upload/media/poster/0001/01/be7a7879b26f8d4b6d52e102bd5d9bf361eab38c.jpeg",
            "http://chedream.org/upload/media/poster/0001/01/8c80cbea3542d18ac76b55ef2bc607840343be9d.png",
            "http://chedream.org/upload/media/poster/0001/01/0a48e7ea5f1e4eea9110aec6888f15bc6cdeac31.jpeg",
            "http://chedream.org/upload/media/poster/0001/01/babe18cb3fed3d2252d10f3ab9cef3092db95691.jpeg",
            "http://chedream.org/upload/media/poster/0001/01/41ca398e8289191c574e61663d3c5ca2f13399ee.jpeg",
    };
}