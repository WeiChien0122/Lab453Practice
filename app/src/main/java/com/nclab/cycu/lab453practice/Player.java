package com.nclab.cycu.lab453practice;

import java.util.ArrayList;

public class Player {
    private int wood;
    private int food;
    private int gold;
    private int stone;
    private int population;
    private int maxPopulation;
    //存放村民的「容器」
    private ArrayList<Villager> mVillagerList;

    public Player() {
        //初始化變數
        wood = 0;
        food = 0;
        gold = 0;
        stone = 0;
        population = 0;
        maxPopulation = 0;
        //創建容器
        mVillagerList = new ArrayList<>();
    }

    //setters
    public void setWood(int wood) {
        this.wood = wood;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    //getters
    public int getWood() {
        return wood;
    }

    public int getFood() {
        return food;
    }

    public int getGold() {
        return gold;
    }

    public int getStone() {
        return stone;
    }

    public int getPopulation() {
        //目前我們的單位只有村民，所以人口就是村民的數量
        population = mVillagerList.size();
        return population;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public Villager newVillage() {
        //建立一個村民
        Villager villager = new Villager();
        //放到容器裡
        mVillagerList.add(villager);
        //回傳這個村民，讓其他程式得到它的參考。
        return villager;
    }
}
