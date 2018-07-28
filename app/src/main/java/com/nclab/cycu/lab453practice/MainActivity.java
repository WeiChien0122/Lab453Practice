package com.nclab.cycu.lab453practice;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
                newVillage();
                break;
            case R.id.newMilitiaButton:
                //新增一個民兵
                newMilitia();
                break;
        }
    }

    private void newVillage() {

        //創建ImageView，並覆寫onTouchEvent()，讓我們可以拖著照片移動
        ImageView villageImageView = new android.support.v7.widget.AppCompatImageView(this) {
            float thumbX0;
            float thumbY0;
            float viewX0;
            float viewY0;
            boolean hasMoved;

            //Android要求，只要有覆寫onTouchEvent()，就必須覆寫performClick()
            @Override
            public boolean performClick() {
                return super.performClick();
            }

            //覆寫onTouchEvent，處理觸碰事件，我們要拖著照片移動
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: //手指碰到螢幕
                        //紀錄手指碰到螢幕的位置
                        thumbX0 = event.getRawX();
                        thumbY0 = event.getRawY();
                        //紀錄元件的初始位置
                        viewX0 = getX();
                        viewY0 = getY();
                        //紀錄在這次的觸碰事件中，從開始到結束，是否有移動過，如果沒有，會在手指離開螢幕的時候觸發點擊事件
                        hasMoved = false;
                        break;

                    case MotionEvent.ACTION_MOVE: //手指在螢幕上移動
                        //紀錄在這次的觸碰事件中，從開始到結束，是否有移動過，如果沒有，會在手指離開螢幕的時候觸發點擊事件
                        hasMoved = true;
                        //取得移動後的手指位置
                        float x = event.getRawX();
                        float y = event.getRawY();
                        //更新元件的位置
                        setX(viewX0 + (x - thumbX0));
                        setY(viewY0 + (y - thumbY0));
                        //寫在MainActivity的方法，處理當村民移動時要做的事情
                        onVillageMoved(this, getX(), getY());
                        break;
                    case MotionEvent.ACTION_UP: //手指離開螢幕
                        //如果手指不曾移動，觸發點擊事件
                        if (!hasMoved) performClick();
                        return false;
                }
                return true;
            }
        };

        //設定元件圖片
        villageImageView.setImageDrawable(getResources().getDrawable(Villager.DRAWABLE_RESOURCE));

        //取得Layout
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        //把ImageView放入Layout，長寬各為200pixels
        constraintLayout.addView(villageImageView, 200, 200);

        //創建村民
        mPlayer.newVillage(villageImageView);
    }

    private void newMilitia() {

        //創建ImageView，並覆寫onTouchEvent()，讓我們可以拖著照片移動
        ImageView militiaImageView = new android.support.v7.widget.AppCompatImageView(this) {
            float thumbX0;
            float thumbY0;
            float viewX0;
            float viewY0;
            boolean hasMoved;

            //Android要求，只要有覆寫onTouchEvent()，就必須覆寫performClick()
            @Override
            public boolean performClick() {
                return super.performClick();
            }

            //覆寫onTouchEvent，處理觸碰事件，我們要拖著照片移動
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: //手指碰到螢幕
                        //紀錄手指碰到螢幕的位置
                        thumbX0 = event.getRawX();
                        thumbY0 = event.getRawY();
                        //紀錄元件的初始位置
                        viewX0 = getX();
                        viewY0 = getY();
                        //紀錄在這次的觸碰事件中，從開始到結束，是否有移動過，如果沒有，會在手指離開螢幕的時候觸發點擊事件
                        hasMoved = false;
                        break;

                    case MotionEvent.ACTION_MOVE: //手指在螢幕上移動
                        //紀錄在這次的觸碰事件中，從開始到結束，是否有移動過，如果沒有，會在手指離開螢幕的時候觸發點擊事件
                        hasMoved = true;
                        //取得移動後的手指位置
                        float x = event.getRawX();
                        float y = event.getRawY();
                        //更新元件的位置
                        setX(viewX0 + (x - thumbX0));
                        setY(viewY0 + (y - thumbY0));
                        break;
                    case MotionEvent.ACTION_UP: //手指離開螢幕
                        //如果手指不曾移動，觸發點擊事件
                        if (!hasMoved) performClick();
                        return false;
                }
                return true;
            }
        };

        //設定元件圖片
        militiaImageView.setImageDrawable(getResources().getDrawable(Militia.DRAWABLE_RESOURCE));

        //取得Layout
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        //把ImageView放入Layout，長寬各為200pixels
        constraintLayout.addView(militiaImageView, 200, 200);

        //創建民兵
        mPlayer.newMilitia(militiaImageView);
    }

    private void onVillageMoved(ImageView view, float x, float y) {
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
        float view1Right = view1Left+view1.getWidth();
        float view1Bottom = view1Top + view1.getHeight();
        float view2Left = view2.getX();
        float view2Top = view2.getY();
        float view2Right = view2Left+view2.getWidth();
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
