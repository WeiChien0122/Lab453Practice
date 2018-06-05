package com.nclab.cycu.lab453practice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mWoodTextView;
    private TextView mFoodTextView;
    private TextView mGoldTextView;
    private TextView mStoneTextView;
    private TextView mPopTextView;

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
        findViewById(R.id.woodButton).setOnClickListener(this);
        findViewById(R.id.foodButton).setOnClickListener(this);
        findViewById(R.id.goldButton).setOnClickListener(this);
        findViewById(R.id.stoneButton).setOnClickListener(this);
        findViewById(R.id.popButton).setOnClickListener(this);
        findViewById(R.id.maxPopButton).setOnClickListener(this);
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
            case R.id.woodButton:
                mPlayer.setWood(mPlayer.getWood() + 5); //為mPlayer增加5個木材
                break;
            case R.id.foodButton:
                mPlayer.setFood(mPlayer.getFood() + 5); //為mPlayer增加5個食物
                break;
            case R.id.goldButton:
                mPlayer.setGold(mPlayer.getGold() + 5); //為mPlayer增加5個黃金
                break;
            case R.id.stoneButton:
                mPlayer.setStone(mPlayer.getStone() + 5); //為mPlayer增加5個石頭
                break;
            case R.id.popButton:
                mPlayer.setPopulation(mPlayer.getPopulation() + 1); //為mPlayer增加1個人口
                break;
            case R.id.maxPopButton:
                mPlayer.setMaxPopulation(mPlayer.getMaxPopulation() + 5); //為mPlayer增加5個人口上限
                break;
        }
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
