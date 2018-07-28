package com.nclab.cycu.lab453practice;

import android.widget.ImageView;

public class Member {
    private Player mPlayer;
    private ImageView mImageView;
    private MemberThread mMilitiaThread;
    private boolean mIsAlive;
    private int mHp;
    private int mMaxHp;
    private int mAttack;
    private int mAttackRange;
    private int mNearDefence;
    private int mFarDefence;

    public Member(Player player, ImageView imageView, int hp, int maxHp, int mAttack, int attackRange, int nearDefence, int farDefence) {
        setAlive(true);
        mPlayer = player;
        mImageView = imageView;
        setHp(hp);
        setMaxHp(maxHp);
        setAttack(mAttack);
        setAttackRange(attackRange);
        setNearDefence(nearDefence);
        setFarDefence(farDefence);
        mMilitiaThread = new MemberThread();
        mMilitiaThread.start();
    }

    //setters
    public void setPlayer(Player player) {
        mPlayer = player;
    }

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    public void setAlive(boolean alive) {
        mIsAlive = alive;
    }

    synchronized public void setHp(int hp) {
        if (isAlive()) {
            if (hp > 0) {
                mHp = hp;
            } else {
                mHp = 0;
                setAlive(false);
            }
        }
    }

    public void setMaxHp(int maxHp) {
        mMaxHp = maxHp;
    }

    public void setAttack(int attack) {
        mAttack = attack;
    }

    public void setAttackRange(int attackRange) {
        mAttackRange = attackRange;
    }

    public void setNearDefence(int nearDefence) {
        mNearDefence = nearDefence;
    }

    public void setFarDefence(int farDefence) {
        mFarDefence = farDefence;
    }

    //getters
    public Player getPlayer() {
        return mPlayer;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public boolean isAlive() {
        return mIsAlive;
    }

    public int getHp() {
        return mHp;
    }

    public int getMaxHp() {
        return mMaxHp;
    }

    public int getAttack() {
        return mAttack;
    }

    public int getAttackRange() {
        return mAttackRange;
    }

    public int getNearDefence() {
        return mNearDefence;
    }

    public int getFarDefence() {
        return mFarDefence;
    }

    //about Thread

    /**
     * 每秒執行一次
     */
    protected void onLoop() {
    }

    private class MemberThread extends Thread {
        @Override
        public void run() {
            while (Member.this.isAlive()) {
                onLoop();

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
