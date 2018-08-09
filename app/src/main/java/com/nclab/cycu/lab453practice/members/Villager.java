package com.nclab.cycu.lab453practice.members;

import com.nclab.cycu.lab453practice.MemberView;
import com.nclab.cycu.lab453practice.Player;
import com.nclab.cycu.lab453practice.R;

public class Villager extends Member {
    public static final int DRAWABLE_RESOURCE = R.drawable.villager;

    public static final String JOB_NONE = "閒置村民";
    public static final String JOB_LUMBERJACK = "伐木工";
    public static final String JOB_FARMER = "農夫";
    public static final String JOB_GOLD_MINER = "金礦工";
    public static final String JOB_STONE_MINER = "石礦工";
    private String mJob;

    /**
     * @param player 擁有這個村民的Player
     * @param villageImageView 顯示在畫面上的ImageView
     */
    public Villager(Player player, MemberView villageImageView) {
        super(player, villageImageView, 25, 25, 3, 0, 0, 0);
        mJob = JOB_NONE;
    }

    /**
     * 設定村民的工作
     * @param job JOB_NONE, JOB_LUMBERJACK, JOB_FARMER, JOB_GOLD_MINER, JOB_STONE_MINER
     */
    public void setJob(String job) {
        mJob = job;
    }

    @Override
    protected void onLoop() {
        //根據村民的工作做對應的事情
        switch (mJob) {
            case JOB_NONE:
                //Do nothing.
                break;

            case JOB_LUMBERJACK:
                //玩家的木材+1
                getPlayer().setWood(getPlayer().getWood() + 1);
                break;

            case JOB_FARMER:
                //玩家的食物+1
                getPlayer().setFood(getPlayer().getFood() + 1);
                break;

            case JOB_GOLD_MINER:
                //玩家的金礦+1
                getPlayer().setGold(getPlayer().getGold() + 1);
                break;

            case JOB_STONE_MINER:
                //玩家的石頭+1
                getPlayer().setStone(getPlayer().getStone() + 1);
                break;
        }
    }
}
