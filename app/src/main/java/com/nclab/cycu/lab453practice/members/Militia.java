package com.nclab.cycu.lab453practice.members;

import com.nclab.cycu.lab453practice.MemberView;
import com.nclab.cycu.lab453practice.Player;
import com.nclab.cycu.lab453practice.R;

public class Militia extends Member {
    public static final int DRAWABLE_RESOURCE = R.drawable.militia;

    /**
     * @param player           擁有這個村民的Player
     * @param militiaImageView 顯示在畫面上的ImageView
     */
    public Militia(Player player, MemberView militiaImageView) {
        super(player, militiaImageView, 40, 40, 4, 0, 0, 1);
    }

    /**
     * 攻擊Member，Villager和Militia都繼承Member，可以一起使用這個method
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
