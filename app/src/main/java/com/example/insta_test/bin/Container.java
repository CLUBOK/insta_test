package com.example.insta_test.bin;

import android.content.Context;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.insta_test.models.Cell;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class Container extends FrameLayout
{
    public Context context;
    public AppCompatActivity activity;
    public SuperContainer superContainer;
    public Container rootContainer;
    public Container previousContainer;
    //OBSERVABLE
    public CompositeDisposable compositeDisposable;


    public Container(@NonNull Context context, SuperContainer superContainer)
    {
        super(context);
        initData(context, superContainer);
    }

    public Container(@NonNull Context context, Container container)
    {
        super(context);
        initData(context, container);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        onBuild();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        clearData();
        onDispose();
    }


    // DATA

    private void initData(@NonNull Context context, SuperContainer superContainer)
    {
        this.context = context;
        this.activity = superContainer;
        this.superContainer = superContainer;
        this.rootContainer = this;
        this.previousContainer = null;
        //OBSERVABLE
        this.compositeDisposable = new CompositeDisposable();
    }

    private void initData(@NonNull Context context, Container container)
    {
        this.context = context;
        this.activity = container.activity;
        this.superContainer = container.superContainer;
        this.rootContainer = container.rootContainer;
        this.previousContainer = container;
        //OBSERVABLE
        this.compositeDisposable = new CompositeDisposable();
    }

    private void clearData()
    {
        clearDisposable();
    }


    // BOOT

    public abstract void onCreate();

    public abstract void onBuild();

    public abstract void onDispose();



    // RX

    public void addConsumer(Observable observable, Consumer observer)
    {
        if (compositeDisposable.isDisposed() || observable == null) return;
        compositeDisposable.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribe(observer));
    }

    public void addDisposable(Disposable disposable)
    {
        if (compositeDisposable.isDisposed()) return;
        compositeDisposable.add(disposable);
    }

    public void clearDisposable()
    {
        compositeDisposable.clear();
    }

}
