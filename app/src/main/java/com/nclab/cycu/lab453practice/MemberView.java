package com.nclab.cycu.lab453practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class MemberView extends android.support.v7.widget.AppCompatImageView {
    private OnMoveListener mOnMoveListener;

    private Paint mPaint = new Paint();

    private float thumbX0;
    private float thumbY0;
    private float viewX0;
    private float viewY0;
    private boolean hasMoved;

    private int mHp, mMaxHp;

    public MemberView(Context context) {
        super(context);
    }

    public void setOnMoveListener(OnMoveListener onMoveListener) {
        mOnMoveListener = onMoveListener;
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
                onViewMoved(this);
                break;
            case MotionEvent.ACTION_UP: //手指離開螢幕
                //如果手指不曾移動，觸發點擊事件
                if (!hasMoved) performClick();
                return false;
        }
        return true;
    }

    //Android要求，只要有覆寫onTouchEvent()，就必須覆寫performClick()
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    //當圖片被移動時，通知有註冊監聽的地方
    private void onViewMoved(MemberView memberView) {
        //若mOnMoveListener有參考到物件，則透過這個物件(OnMoveListener)呼叫"onMove(MemberImageView)"來通知「移動事件」的發生
        if (mOnMoveListener != null) {
            mOnMoveListener.onMove(this);
        }
    }

    public void setHp(int hp, int maxHp) {
        mHp = hp;
        mMaxHp = maxHp;
        //重畫一張圖
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //畫紅底
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(getLeft()
                , (float) (getTop() + (getBottom() - getTop()) * 0.95)
                , getRight()
                , getBottom()
                , mPaint);
        //畫綠血
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(getLeft()
                , (float) (getTop() + (getBottom() - getTop()) * 0.95)
                , getLeft() + (getRight() - getLeft()) * ((float) mHp / mMaxHp)
                , getBottom()
                , mPaint);
    }

    public interface OnMoveListener {
        void onMove(MemberView memberView);
    }
}
