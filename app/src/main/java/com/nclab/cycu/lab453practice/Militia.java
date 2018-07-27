package com.nclab.cycu.lab453practice;

import android.util.Log;
import android.widget.ImageView;

public class Militia {
    public static final int DRAWABLE_RESOURCE = R.drawable.militia;

    private Player mPlayer;
    private ImageView mImageView;
    private MilitiaThread mMilitiaThread;
    private boolean mIsAlive;
    private int mHp;
    private int mMaxHp;
    private int mAttack;
    private int mAttackRange;
    private int mNearDefence;
    private int mFarDefence;

    /**
     * @param player           擁有這個村民的Player
     * @param militiaImageView 顯示在畫面上的ImageView
     */
    public Militia(Player player, ImageView militiaImageView) {
        mPlayer = player; //把擁有這個村民的player記錄起來，供以後使用
        mImageView = militiaImageView;
        mIsAlive = true;
        mHp = 40;
        mMaxHp = 40;
        mAttack = 4;
        mAttackRange = 0;
        mNearDefence = 0;
        mFarDefence = 1;

        mMilitiaThread = new MilitiaThread();
        mMilitiaThread.start();
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public boolean isAlive() {
        return mIsAlive;
    }

    synchronized void setHp(int hp) {
        mHp = hp;
        if (mHp <= 0) {
            mIsAlive = false;
        }
    }

    public int getHp() {
        return mHp;
    }

    public int getNearDefence() {
        return mNearDefence;
    }

    /**
     * 攻擊村民
     *
     * @param villager 目標
     */
    private void attack(Villager villager) {
        int damage = mAttack > villager.getNearDefence() ? mAttack - villager.getNearDefence() : 0;
        villager.setHp(villager.getHp() - damage);
        Log.d(getClass().getSimpleName(), "attack villager, hp:" + villager.getHp());
    }

    /**
     * 攻擊民兵
     *
     * @param militia 目標
     */
    private void attack(Militia militia) {
        int damage = mAttack > militia.getNearDefence() ? mAttack - militia.getNearDefence() : 0;
        militia.setHp(militia.getHp() - damage);

        Log.d(getClass().getSimpleName(), "attack militia, hp:" + militia.getHp());
    }


    /**
     * 民兵的執行序
     */
    private class MilitiaThread extends Thread {
        @Override
        public void run() {
            while (mIsAlive) {
                //尋找附近的單位
                Villager villager = mPlayer.findNearbyVillager(Militia.this);
                Militia militia = mPlayer.findNearbyMilitia(Militia.this);
                //攻擊單位
                if (villager != null) {
                    attack(villager);
                } else if (militia != null) {
                    attack(militia);
                }

                //每秒一次
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
