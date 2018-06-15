package com.nclab.cycu.lab453practice;

public class Villager {
    public static final String JOB_NONE = "閒置村民";
    public static final String JOB_LUMBERJACK = "伐木工";
    public static final String JOB_FARMER = "農夫";
    public static final String JOB_GOLD_MINER = "金礦工";
    public static final String JOB_STONE_MINER = "石礦工";
    private int mMaxHp;
    private int mAttack;
    private int mAttackRange;
    private int mNearDefence;
    private int mFarDefence;
    private String mJob;

    public Villager() {
        mMaxHp = 25;
        mAttack = 3;
        mAttackRange = 0;
        mNearDefence = 0;
        mFarDefence = 0;
        mJob = JOB_NONE;
    }

    /**
     * 設定村民的工作
     * @param job JOB_NONE, JOB_LUMBERJACK, JOB_FARMER, JOB_GOLD_MINER, JOB_STONE_MINER
     */
    public void setJob(String job) {
        mJob = job;
    }

    public String getJob() {
        return mJob;
    }
}
