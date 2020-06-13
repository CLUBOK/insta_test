package com.example.insta_test;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.insta_test.bin.SuperContainer;
import com.example.insta_test.pages.DefaultPage_;
import com.example.insta_test.pages.HomePage_;
import com.example.insta_test.utilities.Utilities;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends SuperContainer implements View.OnClickListener
{


    @AfterInject
    public void AfterInject()
    {

    }



    @AfterViews
    void AfterViews()
    {
        List<View> pages = new ArrayList<>();
        pages.add(HomePage_.build(MainActivity.this, this));
        pages.add(DefaultPage_.build(MainActivity.this, this, R.drawable.ic_search));
        pages.add(DefaultPage_.build(MainActivity.this, this, R.drawable.ic_add));
        pages.add(DefaultPage_.build(MainActivity.this, this, R.drawable.ic_favorite));
        pages.add(DefaultPage_.build(MainActivity.this, this, R.drawable.ic_account));


        VPAdapter vpAdapter = new VPAdapter(pages);
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                Utilities.log(" > onPageSelected " + position);
                selectedBottomBar(position);
            }
        });

        selectedBottomBar(0);


//        PageAdapter adapter = new PageAdapter();
//        viewPager.setAdapter(adapter);

        // git config --global user.email "clubokstudio@gmail.com"
        // git config --global user.name "CLUBOK"







        btn_camera.setOnClickListener(this);
        btn_send.setOnClickListener(this);

        btn_home.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_favorite.setOnClickListener(this);
        btn_account.setOnClickListener(this);
    }

    @ViewById
    ViewPager viewPager;
//    @ViewById
//    ViewPager2 viewPager;

    @ViewById
    ImageView btn_camera;
    @ViewById
    ImageView btn_send;

    @ViewById
    ViewGroup bottomBar;
    @ViewById
    ImageView btn_home;
    @ViewById
    ImageView btn_search;
    @ViewById
    ImageView btn_add;
    @ViewById
    ImageView btn_favorite;
    @ViewById
    ImageView btn_account;

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_camera:
                Utilities.toast(MainActivity.this, "Camera", Toast.LENGTH_SHORT);
                break;
            case R.id.btn_send:
                Utilities.toast(MainActivity.this, "Send", Toast.LENGTH_SHORT);
                break;


            case R.id.btn_home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.btn_search:
                viewPager.setCurrentItem(1);
                break;
            case R.id.btn_add:
                viewPager.setCurrentItem(2);
                break;
            case R.id.btn_favorite:
                viewPager.setCurrentItem(3);
                break;
            case R.id.btn_account:
                viewPager.setCurrentItem(4);
                break;
        }
    }

    private void selectedBottomBar(int activePosition)
    {
        for (int i = 0; i < bottomBar.getChildCount(); i++)
        {
            View child = bottomBar.getChildAt(i);
            child.setActivated(i == activePosition);
        }
    }





























    // ADAPTER

    // VIEW_PAGER ADAPTER

    private static class VPAdapter extends PagerAdapter
    {
        List<View> pages;

        public VPAdapter(List<View> pages)
        {
            this.pages = pages;
        }

        @Override
        public Object instantiateItem(@NonNull ViewGroup collection, int position)
        {
            View view = pages.get(position);
            collection.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view)
        {
            container.removeView((View) view);
        }

        @Override
        public int getCount()
        {
            return pages.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
        {
//            return false;
//            return true;
            return view == object;
        }
    }


//    // VIEW_PAGER_2 ADAPTER
//
//    private static class PageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
//    {
//        public PageAdapter()
//        {
//
//        }
//
//        @Override
//        public int getItemCount()
//        {
//            return 5;
//        }
//
//        @Override
//        public int getItemViewType(int position)
//        {
//            return position;
//        }
//
//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//        {
//            View v  = null;
//            RecyclerView.ViewHolder holder = null;
//            switch (viewType)
//            {
//                case 0:
//                    v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_home, parent, false);
//                    holder = new HomePageHolder(v);
//                    break;
//
//                default:
//                    v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_default, parent, false);
//                    holder = new DefaultPageHolder(v);
//                    break;
//            }
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
//        {
//            switch (position)
//            {
//                case 0:
////                    ((HomePageHolder) holder).loadCells();
//                    break;
//                case 1:
//                    ((DefaultPageHolder) holder).setIcon(R.drawable.ic_search);
//                    break;
//                case 2:
//                    ((DefaultPageHolder) holder).setIcon(R.drawable.ic_add);
//                    break;
//                case 3:
//                    ((DefaultPageHolder) holder).setIcon(R.drawable.ic_favorite);
//                    break;
//                case 4:
//                    ((DefaultPageHolder) holder).setIcon(R.drawable.ic_account);
//                    break;
//            }
//        }
//    }
//
//    // HOLDER
//
//    private static class HomePageHolder extends RecyclerView.ViewHolder
//    {
//        private Context context;
//        private CellAdapter cellAdapter;
//        RecyclerView recycler;
//        ProgressBar progressBar;
//
//
//        public HomePageHolder(View view)
//        {
//            super(view);
//            context = view.getContext();
//
//            recycler = view.findViewById(R.id.recycler);
//            progressBar = view.findViewById(R.id.progressBar);
//
//            cellAdapter = new CellAdapter();
//            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
//            recycler.setAdapter(cellAdapter);
//            recycler.setLayoutManager(layoutManager);
//            recycler.setItemAnimator(null);
//            recycler.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
//
//
//            loadCells();
//        }
//
//        private void loadCells()
//        {
//            try
//            {
//                cellAdapter.clear();
//                String jsonStr = Utilities.getJsonFromAssets(context, "cells.json");
//                Utilities.log(jsonStr);
//                ObjectMapper Obj = new ObjectMapper();
//                assert jsonStr != null;
//                List<Cell> cells = Obj.readValue(jsonStr, new TypeReference<List<Cell>>() {});
////                for (Cell cell : cells)
////                {
////                    Utilities.log(cell.toString());
////                }
//
//
//                cellAdapter.setCellList(cells);
//            }
//            catch (Exception e)
//            {
//                Utilities.catchError(e);
//            }
//        }
//
//
//
//
//
//
//
//
//        // ADAPTER
//
//        private static class CellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
//        {
//            List<Cell> itemList;
//
//            public CellAdapter()
//            {
//                this.itemList = new ArrayList<>();
//            }
//
//            private void setCellList(List<Cell> cellList)
//            {
//                itemList.addAll(cellList);
//                notifyDataSetChanged();
//            }
//
//            public void clear()
//            {
//                if (!itemList.isEmpty())
//                {
//                    itemList.clear();
//                    notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public int getItemCount()
//            {
//                return itemList.size();
//            }
//
//            @Override
//            public int getItemViewType(int position)
//            {
//                return 0;
//            }
//
//            @NonNull
//            @Override
//            public CellHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//            {
//                View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_cell, parent, false);
//                return new CellHolder(v);
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
//            {
//                CellHolder cellHolder = (CellHolder) holder;
//                cellHolder.setCell(itemList.get(position));
//            }
//        }
//
//        private static class PictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
//        {
//            private List<Picture> itemList;
//
//            public PictureAdapter()
//            {
//                this.itemList = new ArrayList<>();
//            }
//
//            private void setPictures(List<Picture> pictures)
//            {
//                itemList.clear();
//                itemList.addAll(pictures);
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public int getItemCount()
//            {
//                return itemList.size();
//            }
//
//            @Override
//            public int getItemViewType(int position)
//            {
//                return 0;
//            }
//
//            @NonNull
//            @Override
//            public PictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//            {
//                View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_picture, parent, false);
//                return new PictureHolder(v);
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
//            {
//                PictureHolder pictureHolder = (PictureHolder) holder;
//                pictureHolder.setPicture(itemList.get(position));
//            }
//        }
//
//        // HOLDER
//
//        private static class CellHolder extends RecyclerView.ViewHolder implements View.OnClickListener
//        {
//            Context context;
//
//            ImageView iv_avatar;
//            TextView tv_name;
//            TextView tv_subTitle;
//            TextView tv_likesCount;
//            ImageView iv_menu;
//            View btn_subTitle;
//
//            PictureAdapter pictureAdapter;
//            ViewPager2 viewPager;
//            PageIndicator pageIndicator;
//
//            ImageView iv_like;
//            ImageView iv_comment;
//            ImageView iv_send;
//            ImageView iv_turned;
//
//
//
//            private CellHolder(View view)
//            {
//                super(view);
//
//                context = view.getContext();
//
//                iv_avatar = view.findViewById(R.id.iv_avatar);
//                tv_name = view.findViewById(R.id.tv_name);
//                tv_subTitle = view.findViewById(R.id.tv_subTitle);
//                tv_likesCount = view.findViewById(R.id.tv_likesCount);
//                btn_subTitle = view.findViewById(R.id.btn_subTitle);
//                iv_menu = view.findViewById(R.id.iv_menu);
//
//                viewPager = view.findViewById(R.id.viewPager);
//                pageIndicator = view.findViewById(R.id.pageIndicator);
//
//                iv_like = view.findViewById(R.id.iv_like);
//                iv_comment = view.findViewById(R.id.iv_comment);
//                iv_send = view.findViewById(R.id.iv_send);
//                iv_turned = view.findViewById(R.id.iv_turned);
//
//
//
//
//                setupViewPager();
//                setupOnClick();
//            }
//
//            @Override
//            public void onClick(View v)
//            {
//                switch (v.getId())
//                {
//                    case R.id.btn_subTitle:
//
//                        break;
//                    case R.id.iv_menu:
//
//                        break;
//
//                    case R.id.iv_like:
//
//                        break;
//                    case R.id.iv_comment:
//
//                        break;
//                    case R.id.iv_send:
//
//                        break;
//                    case R.id.iv_turned:
//
//                        break;
//                }
//            }
//
//            private void setupViewPager()
//            {
//                pictureAdapter = new PictureAdapter();
//                viewPager.setAdapter(pictureAdapter);
//                pageIndicator.setViewPager(viewPager);
//            }
//
//            private void setupOnClick()
//            {
//                btn_subTitle.setOnClickListener(this);
//                iv_menu.setOnClickListener(this);
//                iv_like.setOnClickListener(this);
//                iv_comment.setOnClickListener(this);
//                iv_send.setOnClickListener(this);
//                iv_turned.setOnClickListener(this);
//            }
//
//            public void setCell(Cell cell)
//            {
////                Utilities.log(" > setCell");
////                Utilities.log(" > likesCount: " + cell.likesCount);
////                Utilities.log(" > avatarUrl:  " + cell.avatarUrl);
////                Utilities.log(cell.toString());
//
//
//                String avatarUrl = cell.avatarUrl;
//                Bitmap bitmap = Utilities.getBitmapFromAssets(context, avatarUrl);
//                iv_avatar.setImageBitmap(bitmap);
//                iv_avatar.setClipToOutline(true);
//
//
//                tv_name.setText(cell.name);
//                tv_subTitle.setText(cell.subTitle);
//                tv_likesCount.setText("Likes " + cell.likesCount);
//                pictureAdapter.setPictures(cell.pictures);
//            }
//        }
//
//        private static class PictureHolder extends RecyclerView.ViewHolder
//        {
//            Context context;
//            ImageView iv_picture;
//            ProgressBar progressBar;
//
//            private PictureHolder(View view)
//            {
//                super(view);
//                context = view.getContext();
//                iv_picture = view.findViewById(R.id.iv_picture);
//                progressBar = view.findViewById(R.id.progressBar);
//            }
//
//            private void setPicture(Picture picture)
//            {
//                String pictureUrl = picture.pictureUrl;
//                Bitmap bitmap = Utilities.getBitmapFromAssets(context, pictureUrl);
//                iv_picture.setImageBitmap(bitmap);
//            }
//        }
//    }
//
//    private static class DefaultPageHolder extends RecyclerView.ViewHolder
//    {
//        ImageView iv_pageIcon;
//
//        public DefaultPageHolder(View view)
//        {
//            super(view);
//            iv_pageIcon = view.findViewById(R.id.iv_pageIcon);
//        }
//
//        private void setIcon(@DrawableRes int drawableRes)
//        {
//            iv_pageIcon.setImageResource(drawableRes);
//        }
//    }

}
