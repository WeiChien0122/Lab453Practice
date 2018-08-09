package com.nclab.cycu.lab453practice.members;

import com.nclab.cycu.lab453practice.MemberView;
import com.nclab.cycu.lab453practice.Player;

/**
 * Member的工廠類別
 */
public class MemberFactory {
    /**
     * 成員的類別(村民、民兵、裝甲步兵...)
     */
    public enum Type {
        VILLAGER,
        MILITIA,
        MAN_AT_ARMS
    }

    /**
     * 靜態方法，也就是說MemberFactory不需要實例化，就可以直接使用，產生並回傳一個Member
     *
     * @param type       成員的類別(Type.Villager, Type.MILITIA, ...)
     * @param player     擁有這個Member的Player
     * @param memberView 和這個Member對應的圖示
     * @return
     */
    public static Member newInstance(Type type, Player player, MemberView memberView) {
        //宣告Member
        Member member;

        //建構Member物件
        switch (type) {
            case VILLAGER:
                member = new Villager(player, memberView);
                break;
            case MILITIA:
                member = new Militia(player, memberView);
                break;
            case MAN_AT_ARMS:
                member = new ManAtArms(player, memberView);
                break;

            //防呆: 在enum裡面放了新類別，可是忘記在這裡實作
            default:
                throw new RuntimeException("輸入的Type尚未實作");
        }

        //回傳Member物件
        return member;
    }
}
