package com.nclab.cycu.lab453practice;

public class Player {
    private int wood;
    private int food;
    private int gold;
    private int stone;
    private int population;
    private int maxPopulation;

    public Player() {
        //初始化變數
        wood = 0;
        food = 0;
        gold = 0;
        stone = 0;
        population = 0;
        maxPopulation = 0;
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
        return population;
    }


    public int getMaxPopulation() {
        return maxPopulation;
    }

}
