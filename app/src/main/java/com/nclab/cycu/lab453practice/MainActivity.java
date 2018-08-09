package com.nclab.cycu.lab453practice;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nclab.cycu.lab453practice.members.Member;
import com.nclab.cycu.lab453practice.members.MemberFactory;
import com.nclab.cycu.lab453practice.members.Villager;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mWoodTextView;
    private TextView mFoodTextView;
    private TextView mGoldTextView;
    private TextView mStoneTextView;
    private TextView mPopTextView;
    private ImageView mForestImageView;
    private ImageView mFarmlandImageView;
    private ImageView mGoldImageView;
    private ImageView mStoneImageView;
    private Handler mHandler = new Handler();

    //依照Player這個class建構出一個物件，然後放到mPlayer變數中
    private Player mPlayer = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI(); //初始化UI

        //用Handler搭配Runnable實現循環執行
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!isDestroyed()) {
                    updateView();
                    mHandler.postDelayed(this, 100);
                }
            }
        };
        mHandler.postDelayed(runnable, 100);
    }

    private void initializeUI() {
        mWoodTextView = findViewById(R.id.woodTextView);
        mFoodTextView = findViewById(R.id.foodTextView);
        mGoldTextView = findViewById(R.id.goldTextView);
        mStoneTextView = findViewById(R.id.stoneTextView);
        mPopTextView = findViewById(R.id.popTextView);
        findViewById(R.id.newVillagerButton).setOnClickListener(this);
        findViewById(R.id.newMilitiaButton).setOnClickListener(this);
        findViewById(R.id.newManAtArmsButton).setOnClickListener(this);
        mForestImageView = findViewById(R.id.forestImageView);
        mFarmlandImageView = findViewById(R.id.farmlandImageView);
        mGoldImageView = findViewById(R.id.goldImageView);
        mStoneImageView = findViewById(R.id.stoneImageView);
    }

    /**
     * 在initializeUI()中，我們為各個Button設置了Listener，傳入的參數都是"this"，
     * 意思是傳入MainActivity自己，而MainActivity必須實作View.OnClickListener，
     * 當Button被按時，Button會把我們設置的MainActivity當成是OnClickListener，
     * 然後這裡的onClick()就會被呼叫。
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newVillagerButton:
                //新增一個村民
                MemberView villagerView = newMemberView(R.drawable.villager);
                villagerView.setOnMoveListener(new MemberView.OnMoveListener() {
                    @Override
                    public void onMove(MemberView memberView) {
                        onVillageMoved(memberView);
                    }
                });

                Member villager = MemberFactory.newInstance(MemberFactory.Type.VILLAGER, mPlayer, villagerView);
                mPlayer.addMember(villager);
                break;

            case R.id.newMilitiaButton:
                //新增一個民兵
                MemberView militiaView = newMemberView(R.drawable.militia);

                Member militia = MemberFactory.newInstance(MemberFactory.Type.MILITIA, mPlayer, militiaView);
                mPlayer.addMember(militia);
                break;

            case R.id.newManAtArmsButton:
                MemberView manAtArmsView = newMemberView(R.drawable.man_at_arms);

                Member manAtArms = MemberFactory.newInstance(MemberFactory.Type.MAN_AT_ARMS, mPlayer, manAtArmsView);
                mPlayer.addMember(manAtArms);
                break;
        }
    }

    /**
     * 創建MemberView，並將MemberView放入Layout中
     * @param drawable 圖片資源
     * @return MemberView
     */
    private MemberView newMemberView(int drawable) {
        //創建ImageView，並覆寫onTouchEvent()，讓我們可以拖著照片移動
        MemberView memberView = new MemberView(this);

        //設定元件圖片
        memberView.setImageDrawable(getResources().getDrawable(drawable));

        //取得Layout
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        //把ImageView放入Layout，長寬各為200pixels
        constraintLayout.addView(memberView, 200, 200);

        return memberView;
    };

    private void onVillageMoved(ImageView view) {
        //取得擁有此ImageView的村民
        Villager villager = (Villager) mPlayer.findMemberByView(view);

        //判斷村民位置，設定工作
        if (isOverlapping(view, mForestImageView)) {
            villager.setJob(Villager.JOB_LUMBERJACK);

        } else if (isOverlapping(view, mFarmlandImageView)) {
            villager.setJob(Villager.JOB_FARMER);

        } else if (isOverlapping(view, mGoldImageView)) {
            villager.setJob(Villager.JOB_GOLD_MINER);

        } else if (isOverlapping(view, mStoneImageView)) {
            villager.setJob(Villager.JOB_STONE_MINER);

        } else {
            villager.setJob(Villager.JOB_NONE);

        }
    }

    /**
     * 判斷兩個元件是否重疊
     */
    public static boolean isOverlapping(View view1, View view2) {
        float view1Left = view1.getX();
        float view1Top = view1.getY();
        float view1Right = view1Left + view1.getWidth();
        float view1Bottom = view1Top + view1.getHeight();
        float view2Left = view2.getX();
        float view2Top = view2.getY();
        float view2Right = view2Left + view2.getWidth();
        float view2Bottom = view2Top + view2.getHeight();
        return !(view1Left > view2Right) && !(view1Top > view2Bottom) && !(view1Right < view2Left) && !(view1Bottom < view2Top);
    }

    /**
     * 更新頁面
     */
    private void updateView() {
        //取得mPlayer的資源數量，然後轉成字串，設置到資源的TextView中
        mWoodTextView.setText(String.valueOf(mPlayer.getWood()));
        mFoodTextView.setText(String.valueOf(mPlayer.getFood()));
        mGoldTextView.setText(String.valueOf(mPlayer.getGold()));
        mStoneTextView.setText(String.valueOf(mPlayer.getStone()));
        //String.format也是轉換字串的一種方法，用法有點像C語言中<stdio.h>的printf()
        mPopTextView.setText(String.format(Locale.getDefault(), "%d/%d", mPlayer.getPopulation(), mPlayer.getMaxPopulation()));

        Member deadMember = mPlayer.findDeadMember();
        if (deadMember != null) {
            deadMember.getImageView().setVisibility(View.GONE);
            mPlayer.removeMember(deadMember);
        }
    }

}
