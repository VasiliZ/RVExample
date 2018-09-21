package com.github.vasiliz.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class CustomView extends View {


    public void setmText(String mText) {
        this.mText = mText;
    }

    private int mColor, mLabelColor;
    private String mText;
    private Paint mCircle;

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mCircle = new Paint();
        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.customView, 0, 0);

        try {
            mText = arr.getString(R.styleable.customView_circleLabel);
            mColor = arr.getInteger(R.styleable.customView_circleColor, 0);
            mLabelColor = arr.getInteger(R.styleable.customView_labelColor, 0);
        } finally {
            arr.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int halfWidth = this.getMeasuredWidth() / 2;
        int halfHeight = this.getMeasuredHeight() / 2;
        int r = 0;
        if (halfWidth > halfHeight) {
            r = halfHeight - 10;
        } else {
            r = halfHeight - 10;
        }

        mCircle.setStyle(Paint.Style.FILL);
        mCircle.setAntiAlias(true);
        mCircle.setColor(mColor);

        canvas.drawCircle(halfWidth, halfHeight, r, mCircle);
        mCircle.setTextAlign(Paint.Align.CENTER);
        mCircle.setTextSize(50);
        mCircle.setColor(mLabelColor);
        canvas.drawText(mText, halfWidth, halfHeight, mCircle);
    }
}
