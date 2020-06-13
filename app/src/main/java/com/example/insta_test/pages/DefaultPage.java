package com.example.insta_test.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.example.insta_test.R;
import com.example.insta_test.bin.Container;
import com.example.insta_test.bin.SuperContainer;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@SuppressLint("ViewConstructor")
@EViewGroup(R.layout.page_default)
public class DefaultPage extends Container
{
    public DefaultPage(@NonNull Context context, SuperContainer superContainer, int res)
    {
        super(context, superContainer);
        drawableRes = res;
    }

    public DefaultPage(@NonNull Context context, Container container, int res)
    {
        super(context, container);
        drawableRes = res;
    }

    @AfterViews
    public void onCreate()
    {
        setIcon(drawableRes);
    }

    @Override
    public void onBuild()
    {

    }

    @Override
    public void onDispose()
    {

    }



    private int drawableRes;
    @ViewById
    ImageView iv_pageIcon;


    public void setIcon(@DrawableRes int drawableRes)
    {
        iv_pageIcon.setImageResource(drawableRes);
    }




}
