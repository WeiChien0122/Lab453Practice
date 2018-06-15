package com.nclab.cycu.lab453practice;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
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
    private HashMap<ImageView, Villager> mViewVillagerHashMap = new HashMap<>();

    //依照Player這個class建構出一個物件，然後放到mPlayer變數中
    private Player mPlayer = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI(); //初始化UI
        updateView(); //更新頁面
    }

    /**
     *
     */
    private void initializeUI() {
        mWoodTextView = findViewById(R.id.woodTextView);
        mFoodTextView = findViewById(R.id.foodTextView);
        mGoldTextView = findViewById(R.id.goldTextView);
        mStoneTextView = findViewById(R.id.stoneTextView);
        mPopTextView = findViewById(R.id.popTextView);
        findViewById(R.id.popButton).setOnClickListener(this);
        findViewById(R.id.maxPopButton).setOnClickListener(this);
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
            case R.id.popButton:
                //新增一個村民
                newVillage();
                break;
            case R.id.maxPopButton:
                mPlayer.setMaxPopulation(mPlayer.getMaxPopulation() + 5); //為mPlayer增加5個人口上限
                break;
        }
        updateView();
    }

    private void newVillage() {
        //叫mPlayer創建一個村民
        Villager newVillage = mPlayer.newVillage();

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
        villageImageView.setImageDrawable(getResources().getDrawable(R.drawable.village));

        //用HashMapur將村民和ImageView對應
        mViewVillagerHashMap.put(villageImageView, newVillage);

        //取得Layout
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        //把ImageView放入Layout，長寬各為200pixels
        constraintLayout.addView(villageImageView, 200, 200);
    }

    private void onVillageMoved(ImageView view, float x, float y) {
        //從HashMap取得ImageView對應的村民
        Villager villager = mViewVillagerHashMap.get(view);
        //判斷村民位置，設定工作
        if (x >= mForestImageView.getLeft() && x <= mForestImageView.getRight() && y >= mForestImageView.getTop() && y <= mForestImageView.getBottom()) {
            villager.setJob(Villager.JOB_LUMBERJACK);
            mPlayer.setWood(mPlayer.getWood() + 1);

        } else if (x >= mFarmlandImageView.getLeft() && x <= mFarmlandImageView.getRight() && y >= mFarmlandImageView.getTop() && y <= mFarmlandImageView.getBottom()) {
            villager.setJob(Villager.JOB_FARMER);
            mPlayer.setFood(mPlayer.getFood() + 1);

        } else if (x >= mGoldImageView.getLeft() && x <= mGoldImageView.getRight() && y >= mGoldImageView.getTop() && y <= mGoldImageView.getBottom()) {
            villager.setJob(Villager.JOB_GOLD_MINER);
            mPlayer.setGold(mPlayer.getGold() + 1);

        } else if (x >= mStoneImageView.getLeft() && x <= mStoneImageView.getRight() && y >= mStoneImageView.getTop() && y <= mStoneImageView.getBottom()) {
            villager.setJob(Villager.JOB_STONE_MINER);
            mPlayer.setStone(mPlayer.getStone() + 1);

        } else {
            villager.setJob(Villager.JOB_NONE);

        }
        //更新頁面
        updateView();
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
    }
}
