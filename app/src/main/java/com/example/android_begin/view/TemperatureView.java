package com.example.android_begin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.android_begin.R;

public class TemperatureView extends View {

    private Rect rect = new Rect();
    private Paint paint;
    private int width = 0;
    private int height = 0;
    private int value = 0;
    private float aspectx;
    private float aspecty;

    public TemperatureView(Context context) {
        super(context);
        init();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttr(context, attrs);
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttr(context, attrs);
    }


    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttr(context, attrs);
    }

    public void setValue(int value) {
        this.value = value;
        invalidate();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TemperatureView, 0, 0);
        value = typedArray.getInteger(R.styleable.TemperatureView_value, 0);
        typedArray.recycle();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        float tw = w - getPaddingLeft() - getPaddingRight() - 5;
        aspectx = tw / w;
        height = h;
        float th = h - getPaddingTop() - getPaddingBottom() - 5;
        aspecty = th / h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //маштабируем и сдвигаем холст если есть падинги
        canvas.scale(aspectx, aspecty);
        canvas.translate(getPaddingRight(), getPaddingTop());
        //столбик
        rect.set(width / 2 + width / 4, 0, width, height);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, paint);
        //значение
        rect.set(width / 2 + width / 4 + 10, (int) (height - height / 100.0f * (50 + value)), width - 10, height);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, paint);
        //линейка
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.RIGHT);

        for (int i = 0; i <= 100; i++) {
            if (i % 5 == 0) {//короткие палочки
                paint.setColor(Color.BLUE);
                float y = height / 100.f * i;
                canvas.drawLine(width / 2.f + width / 8.f, y, width / 2.f + width / 4.f, y, paint);
            }
            if (i % 10 == 0) {//длинные палочки + цифры
                float y = height / 100.f * i;
                paint.setColor(Color.BLUE);
                canvas.drawLine(width / 2.f, y, width / 2.f + width / 4.f, y, paint);
                paint.setColor(Color.BLACK);
                String text = String.valueOf(50 - i);
                canvas.drawText(text, width / 4.f + width / 8.f, y, paint);
            }
        }
    }
}

