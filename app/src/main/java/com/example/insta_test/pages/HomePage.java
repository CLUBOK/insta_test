package com.example.insta_test.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.insta_test.R;
import com.example.insta_test.bin.Container;
import com.example.insta_test.bin.SuperContainer;
import com.example.insta_test.bin.wdgets.PageIndicator;
import com.example.insta_test.models.Cell;
import com.example.insta_test.models.Picture;
import com.example.insta_test.utilities.Utilities;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


@SuppressLint("ViewConstructor")
@EViewGroup(R.layout.page_home)
public class HomePage extends Container
{
    public HomePage(@NonNull Context context, SuperContainer superContainer)
    {
        super(context, superContainer);
    }

    public HomePage(@NonNull Context context, Container container)
    {
        super(context, container);
    }

    @AfterViews
    public void onCreate()
    {
        cellAdapter = new CellAdapter();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
        recycler.setAdapter(cellAdapter);
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(null);
        recycler.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onBuild()
    {
        loadCells();
    }

    @Override
    public void onDispose()
    {

    }

    private void loadCells()
    {
        Observable<Long> observable = Observable.fromCallable(new Callable<Long>()
        {
            @Override
            public Long call()
            {
                try
                {
                    clearCellList();
                    TimeUnit.MILLISECONDS.sleep(1300);
                    String jsonStr = Utilities.getJsonFromAssets(context, "cells.json");
                    Utilities.log(jsonStr);
                    ObjectMapper Obj = new ObjectMapper();
                    assert jsonStr != null;
                    List<Cell> cells = Obj.readValue(jsonStr, new TypeReference<List<Cell>>() {});
                    setCellList(cells);
                }
                catch (Exception e)
                {
                    Utilities.catchError(e);
                }
                return 0L;
            }
        })
                .doOnSubscribe(new Consumer<Disposable>()
                {
                    @Override
                    public void accept(Disposable disposable)
                    {
                        showView(progressBar);
                    }
                })
                .doFinally(new Action()
                {
                    @Override
                    public void run()
                    {
                        hideView(progressBar);
                    }
                })
                .subscribeOn(Schedulers.single());
        addDisposable(observable.subscribe());
    }

    public void loadAvatar(final Context context, final String fileName, final ImageView imageView, final int reqWidth, final int reqHeight)
    {
        Observable<Long> observable = Observable.fromCallable(new Callable<Long>()
        {
            @Override
            public Long call()
            {
                try
                {
                    Bitmap bitmap = Utilities.getBitmapFromAssets(context, fileName, reqWidth, reqHeight);
                    setBitmap(bitmap, imageView);
                }
                catch (Exception e)
                {
                    Utilities.catchError(e);
                }
                return 0L;
            }
        })
                .doOnSubscribe(new Consumer<Disposable>()
                {
                    @Override
                    public void accept(Disposable disposable)
                    {
                        showView(null);
                    }
                })
                .doFinally(new Action()
                {
                    @Override
                    public void run()
                    {
                        hideView(null);
                    }
                })
                .subscribeOn(Schedulers.single());
        addDisposable(observable.subscribe());
    }

    public void loadImage(final Context context, final String fileName, final ProgressBar progressBar, final ImageView imageView, final int reqWidth, final int reqHeight)
    {
        Observable<Long> observable = Observable.fromCallable(new Callable<Long>()
        {
            @Override
            public Long call()
            {
                try
                {
                    TimeUnit.MILLISECONDS.sleep(500);
                    Bitmap bitmap = Utilities.getBitmapFromAssets(context, fileName, reqWidth, reqHeight);
                    setBitmap(bitmap, imageView);
                }
                catch (Exception e)
                {
                    Utilities.catchError(e);
                }
                return 0L;
            }
        })
                .doOnSubscribe(new Consumer<Disposable>()
                {
                    @Override
                    public void accept(Disposable disposable)
                    {
                        showView(progressBar);
                    }
                })
                .doFinally(new Action()
                {
                    @Override
                    public void run()
                    {
                        hideView(progressBar);
                    }
                })
                .subscribeOn(Schedulers.single());
        addDisposable(observable.subscribe());
    }

    @UiThread
    public void setCellList(List<Cell> cells)
    {
        cellAdapter.setCellList(cells);
    }

    @UiThread
    public void clearCellList()
    {
        cellAdapter.clear();
    }

    @UiThread
    public void setBitmap(Bitmap bitmap, ImageView imageView)
    {
        imageView.setImageBitmap(bitmap);
    }

    @UiThread
    public void showView(View view)
    {
        if (view != null) view.setVisibility(VISIBLE);
    }

    @UiThread
    public void hideView(View view)
    {
        if (view != null) view.setVisibility(GONE);
    }



    private CellAdapter cellAdapter;
    @ViewById
    RecyclerView recycler;
    @ViewById
    ProgressBar progressBar;



    // ADAPTER

    private class CellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        List<Cell> itemList;

        public CellAdapter()
        {
            this.itemList = new ArrayList<>();
        }

        private void setCellList(List<Cell> cellList)
        {
            itemList.addAll(cellList);
            notifyDataSetChanged();
        }

        public void clear()
        {
            if (!itemList.isEmpty())
            {
                itemList.clear();
                notifyDataSetChanged();
            }
        }

        @Override
        public int getItemCount()
        {
            return itemList.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            return 0;
        }

        @NonNull
        @Override
        public CellHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_cell, parent, false);
            return new CellHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
        {
            CellHolder cellHolder = (CellHolder) holder;
            cellHolder.setCell(itemList.get(position));
        }
    }

    private class PictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<Picture> itemList;

        public PictureAdapter()
        {
            this.itemList = new ArrayList<>();
        }

        private void setPictures(List<Picture> pictures)
        {
            itemList.clear();
            itemList.addAll(pictures);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount()
        {
            return itemList.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            return 0;
        }

        @NonNull
        @Override
        public PictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_picture, parent, false);
            return new PictureHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
        {
            PictureHolder pictureHolder = (PictureHolder) holder;
            pictureHolder.setPicture(itemList.get(position));
        }
    }

    // HOLDER

    private class CellHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Context context;
        Cell cell;

        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_subTitle;
        TextView tv_likesCount;
        ImageView iv_menu;
        View btn_subTitle;

        PictureAdapter pictureAdapter;
        ViewPager2 viewPager;
        PageIndicator pageIndicator;

        ImageView iv_like;
        ImageView iv_comment;
        ImageView iv_send;
        ImageView iv_turned;



        private CellHolder(View view)
        {
            super(view);

            context = view.getContext();

            iv_avatar = view.findViewById(R.id.iv_avatar);
            tv_name = view.findViewById(R.id.tv_name);
            tv_subTitle = view.findViewById(R.id.tv_subTitle);
            tv_likesCount = view.findViewById(R.id.tv_likesCount);
            btn_subTitle = view.findViewById(R.id.btn_subTitle);
            iv_menu = view.findViewById(R.id.iv_menu);

            viewPager = view.findViewById(R.id.viewPager);
            pageIndicator = view.findViewById(R.id.pageIndicator);

            iv_like = view.findViewById(R.id.iv_like);
            iv_comment = view.findViewById(R.id.iv_comment);
            iv_send = view.findViewById(R.id.iv_send);
            iv_turned = view.findViewById(R.id.iv_turned);



            iv_avatar.setClipToOutline(true);
            setupViewPager();
            setupOnClick();
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_subTitle:
                    Utilities.toast(context, "Sub Title", Toast.LENGTH_SHORT);
                    break;
                case R.id.iv_menu:
                    Utilities.toast(context, "Menu", Toast.LENGTH_SHORT);
                    break;

                case R.id.iv_like:
                    tv_likesCount.setText("Likes " + cell.likesCount++);
                    break;
                case R.id.iv_comment:
                    Utilities.toast(context, "Comment", Toast.LENGTH_SHORT);
                    break;
                case R.id.iv_send:
                    Utilities.toast(context, "Send", Toast.LENGTH_SHORT);
                    break;
                case R.id.iv_turned:
                    Utilities.toast(context, "FLAG", Toast.LENGTH_SHORT);
                    break;
            }
        }

        private void setupViewPager()
        {
            pictureAdapter = new PictureAdapter();
            viewPager.setAdapter(pictureAdapter);
            pageIndicator.setViewPager(viewPager);
        }

        private void setupOnClick()
        {
            btn_subTitle.setOnClickListener(this);
            iv_menu.setOnClickListener(this);
            iv_like.setOnClickListener(this);
            iv_comment.setOnClickListener(this);
            iv_send.setOnClickListener(this);
            iv_turned.setOnClickListener(this);
        }

        public void setCell(Cell cell)
        {
            this.cell = cell;
            String avatarUrl = cell.avatarUrl;
            iv_avatar.setImageBitmap(null);
            loadAvatar(context, avatarUrl, iv_avatar, 40, 40);


            tv_name.setText(cell.name);
            tv_subTitle.setText(cell.subTitle);
            tv_likesCount.setText("Likes " + cell.likesCount);
            pictureAdapter.setPictures(cell.pictures);
        }
    }

    private class PictureHolder extends RecyclerView.ViewHolder
    {
        Context context;
        ImageView iv_picture;
        ProgressBar progressBar;

        PictureHolder(View view)
        {
            super(view);
            context = view.getContext();
            iv_picture = view.findViewById(R.id.iv_picture);
            progressBar = view.findViewById(R.id.progressBar);
        }

        private void setPicture(Picture picture)
        {
            iv_picture.setImageBitmap(null);
            String pictureUrl = picture.pictureUrl;
//            Bitmap bitmap = Utilities.getBitmapFromAssets(context, pictureUrl, 200, 200);
//            iv_picture.setImageBitmap(bitmap);
            loadImage(context, pictureUrl, progressBar, iv_picture, 500, 500);
        }
    }

}
