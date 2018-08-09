package com.nclab.cycu.lab453practice.members;


import com.nclab.cycu.lab453practice.MemberView;
import com.nclab.cycu.lab453practice.Player;

/**
 * 裝甲步兵
 */
public class ManAtArms extends Member {

    /**
     * @param player    擁有這個裝甲步兵的Player
     * @param imageView 這個裝甲步兵的視圖
     */
    public ManAtArms(Player player, MemberView imageView) {
        super(player, imageView, 45, 45, 6, 0, 0, 1);
    }

    /**
     * 攻擊Member
     *
     * @param targetMember 攻擊目標
     */
    private void attack(Member targetMember) {
        int damage = getAttack() - targetMember.getNearDefence();
        if (damage <= 0) {
            damage = 1;
        }
        targetMember.setHp(targetMember.getHp() - damage);
    }

    @Override
    protected void onLoop() {
        //尋找附近的單位
        Member member = getPlayer().findNearbyMember(this);
        //攻擊單位
        if (member != null) {
            attack(member);
        }
    }

}
