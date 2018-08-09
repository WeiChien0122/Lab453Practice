package com.nclab.cycu.lab453practice.members;

import android.widget.ImageView;

import com.nclab.cycu.lab453practice.MemberView;
import com.nclab.cycu.lab453practice.Player;

public abstract class Member {
    private Player mPlayer;
    private MemberView mMemberView;
    private MemberThread mMilitiaThread;
    private boolean mIsAlive;
    private int mHp;
    private int mMaxHp;
    private int mAttack;
    private int mAttackRange;
    private int mNearDefence;
    private int mFarDefence;

    public Member(Player player, MemberView memberView, int hp, int maxHp, int mAttack, int attackRange, int nearDefence, int farDefence) {
        setAlive(true);
        setPlayer(player);
        setMemberView(memberView);
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

    public void setMemberView(MemberView memberView) {
        mMemberView = memberView;
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
        return mMemberView;
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
                //更新圖示上的血條
                mMemberView.setHp(mHp, mMaxHp);

                //呼叫onLoop()，繼承Member的類別可以覆寫onLoop()，處理週期性的事情
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
