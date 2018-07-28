package com.nclab.cycu.lab453practice;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.ArrayList;

public class Player {
    private int mWood;
    private int mFood;
    private int mGold;
    private int mStone;
    private int mPopulation;
    private int mMaxPopulation;
    //存放成員的「容器」，Villager和Militia都繼承Member，所以可以一起放在這個容器裡
    private ArrayList<Member> mMemberList;

    public Player() {
        //初始化變數
        mWood = 0;
        mFood = 0;
        mGold = 0;
        mStone = 0;
        mPopulation = 0;
        mMaxPopulation = 0;
        //創建「成員」的容器
        mMemberList = new ArrayList<>();
    }

    //setters
    synchronized public void setWood(int wood) {
        this.mWood = wood;
    }

    synchronized public void setFood(int food) {
        this.mFood = food;
    }

    synchronized public void setGold(int gold) {
        this.mGold = gold;
    }

    synchronized public void setStone(int stone) {
        this.mStone = stone;
    }

    synchronized public void setPopulation(int population) {
        this.mPopulation = population;
    }

    synchronized public void setMaxPopulation(int maxPopulation) {
        this.mMaxPopulation = maxPopulation;
    }

    //getters
    public int getWood() {
        return mWood;
    }

    public int getFood() {
        return mFood;
    }

    public int getGold() {
        return mGold;
    }

    public int getStone() {
        return mStone;
    }

    public int getPopulation() {
        mPopulation = mMemberList.size();
        return mPopulation;
    }

    public int getMaxPopulation() {
        return mMaxPopulation;
    }

    public void newVillage(@NonNull ImageView villageImageView) {
        //建立一個村民
        Villager villager = new Villager(this, villageImageView);
        //放到「成員」容器裡
        mMemberList.add(villager);
    }

    public void newMilitia(@NonNull ImageView militiaImageView) {
        //建立一個民兵
        Militia militia = new Militia(this, militiaImageView);
        //放到「成員」容器裡
        mMemberList.add(militia);
    }

    /**
     * 尋找附近的成員
     * @param memberFrom 基準點
     * @return 附近的成員
     */
    public Member findNearbyMember(Member memberFrom) {
        for (Member memberTo : mMemberList) {
            if (memberFrom != memberTo &&
                    MainActivity.isOverlapping(memberFrom.getImageView(), memberTo.getImageView())) {
                return memberTo;
            }
        }
        return null;
    }

    /**
     * 尋找擁有此ImageView的成員
     * @param view ImageView
     * @return 擁有此ImageView的成員 or null
     */
    public Member findMemberByView(ImageView view) {
        for (Member member : mMemberList) {
            if (member.getImageView() == view) {
                return member;
            }
        }
        return null;
    }

    /**
     * 尋找已經死亡的成員，可能是村民和民兵，但都是Member
     * @return 死亡的成員 or null
     */
    public Member findDeadMember() {
        for (Member member : mMemberList) {
            if (!member.isAlive()) {
                return member;
            }
        }
        return null;
    }

    /**
     * 從list中移除成員，可能是村民和民兵，但都是Member
     * @param member
     */
    public void removeMember(Member member) {
        mMemberList.remove(member);
    }
}
