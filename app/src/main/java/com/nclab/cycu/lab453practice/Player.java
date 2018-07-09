package com.nclab.cycu.lab453practice;

import android.support.annotation.NonNull;
import android.widget.ImageView;

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
    //存放民兵的容器
    private ArrayList<Militia> mMilitiaList;

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
        mMilitiaList = new ArrayList<>();
    }

    //setters
    synchronized public void setWood(int wood) {
        this.wood = wood;
    }

    synchronized public void setFood(int food) {
        this.food = food;
    }

    synchronized public void setGold(int gold) {
        this.gold = gold;
    }

    synchronized public void setStone(int stone) {
        this.stone = stone;
    }

    synchronized public void setPopulation(int population) {
        this.population = population;
    }

    synchronized public void setMaxPopulation(int maxPopulation) {
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
        //目前我們的單位只有村民與民兵，所以人口就是村民與民兵的數量
        population = mVillagerList.size() + mMilitiaList.size();
        return population;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void newVillage(@NonNull ImageView villageImageView) {
        //建立一個村民
        Villager villager = new Villager(this, villageImageView);
        //放到容器裡
        mVillagerList.add(villager);
    }

    public void newMilitia(@NonNull ImageView militiaImageView) {
        //建立一個民兵
        Militia militia = new Militia(this, militiaImageView);
        //放到容器裡
        mMilitiaList.add(militia);
    }

    /**
     * 尋找附近的村民
     * @param militia 參考點(民兵)
     * @return 村民，or null if it can't be found.
     */
    public Villager findNearbyVillager(Militia militia) {
        for (Villager villager : mVillagerList) {
            if (MainActivity.isOverlapping(militia.getImageView(), villager.getImageView())) {
                return villager;
            }
        }
        return null;
    }

    /**
     * 尋找附近的民兵
     * @param militia 參考點(民兵)
     * @return 民兵 or null
     */
    public Militia findNearbyMilitia(Militia militia) {
        for (Militia m : mMilitiaList) {
            if (MainActivity.isOverlapping(m.getImageView(), militia.getImageView())) {
                if (m != militia) {
                    return m;
                }
            }
        }
        return null;
    }

    /**
     * 尋找擁有此ImageView的Villager
     * @param view ImageView
     * @return 擁有此ImageView的Villager或是null if it can't be found.
     */
    public Villager findVillagerByView(ImageView view) {
        for (Villager villager : mVillagerList) {
            if (villager.getImageView() == view) {
                return villager;
            }
        }
        return null;
    }

    /**
     * 尋找已經死亡的村民
     * @return 死亡的村民 or null
     */
    public Villager findDeadVillager() {
        for (Villager villager : mVillagerList) {
            if (!villager.isAlive()) {
                return villager;
            }
        }
        return null;
    }

    /**
     * 尋找已經死亡的民兵
     * @return 死亡的民兵 or null
     */
    public Militia findDeadMilitia() {
        for (Militia militia : mMilitiaList) {
            if (!militia.isAlive()) {
                return militia;
            }
        }
        return null;
    }

    /**
     * 從list中移除村民
     * @param villager 村民
     */
    public void removeVillager(@NonNull Villager villager) {
        mVillagerList.remove(villager);
    }

    /**
     * 從list中移除民兵
     * @param militia 村民
     */
    public void removeMilitia(@NonNull Militia militia) {
        mMilitiaList.remove(militia);
    }
}
