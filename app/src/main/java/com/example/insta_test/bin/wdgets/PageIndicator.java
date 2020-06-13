package com.example.insta_test.bin.wdgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.insta_test.utilities.Utilities;

import java.util.Objects;

public class PageIndicator extends View
{
    public PageIndicator(Context context)
    {
        super(context);
        init(context);
    }

    public PageIndicator(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public PageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public PageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }




    Context context;
    int pageCount;
    int currentPosition;
    int indicatorSize;
    int width;
    int height;
    Paint paintActive;
    Paint paintInactive;
    int colorActive;
    int colorInactive;


    private void init(Context context)
    {
        this.context = context;
        colorActive = Utilities.getColorFromAndroidAttr(context, android.R.attr.colorAccent);
        colorInactive = Utilities.getColorFromAndroidAttr(context, android.R.attr.colorControlHighlight);
        paintActive = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintInactive = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintActive.setColor(colorActive);
        paintInactive.setColor(colorInactive);
    }

    private void initBounds()
    {
//        pageCount = 5;
        indicatorSize = (int) Utilities.convertDpToPx(context, 6f);
        width = (int) (indicatorSize * pageCount * 1.5);
        height = indicatorSize;
        invalidate();
    }

    public void setColorActive(int colorActive)
    {
        this.colorActive = colorActive;
        paintActive.setColor(colorActive);
        invalidate();
    }

    public void setColorInactive(int colorInactive)
    {
        this.colorInactive = colorInactive;
        paintInactive.setColor(colorInactive);
        invalidate();
    }

    public void setViewPager(ViewPager2 viewPager)
    {
        final RecyclerView.Adapter adapter = viewPager.getAdapter();
        pageCount = Objects.requireNonNull(adapter).getItemCount();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver()
        {
            @Override
            public void onChanged()
            {
                pageCount = Objects.requireNonNull(adapter).getItemCount();
                initBounds();
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                setActivePosition(position);
            }
        });




    }

    public void setActivePosition(int position)
    {
        this.currentPosition = position;
        invalidate();
    }

    private void drawIndicator(Canvas canvas)
    {
        float cx0 = (float) width / (pageCount * 2);
        float cx = 0f;
        float cy = (float) height / 2;
        for (int i = 0; i < pageCount; i++)
        {
            cx = cx0 + (cx0 * i * 2);
            Paint paint = currentPosition == i ? paintActive : paintInactive;
            canvas.drawCircle(cx, cy, (float)indicatorSize / 2, paint);
        }
    }








    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        drawIndicator(canvas);
    }



















}
