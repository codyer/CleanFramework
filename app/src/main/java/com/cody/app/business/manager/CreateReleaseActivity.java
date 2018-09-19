/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.business.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityCreateReleaseBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.app.business.adapter.ImagePickerAdapter;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.cody.handler.framework.presenter.Presenter;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.handler.framework.viewmodel.WithHeaderViewModel;

import java.io.File;
import java.util.ArrayList;

/**
 * 创建发布内容页面
 * create by chy date 2016/8/19
 */

public class CreateReleaseActivity extends
        WithHeaderActivity<Presenter<WithHeaderViewModel>, WithHeaderViewModel,
                                ActivityCreateReleaseBinding> implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数

    public final static int CAMERA = 1;
    public final static int PHOTOS = 2;

    private int type;
    private File creamFile;

    private ImagePicker imagePicker = ImagePicker.getInstance();


    @NonNull
    @Override
    protected WithHeaderViewModel buildViewModel(Bundle savedInstanceState) {
        WithHeaderViewModel viewModel = new WithHeaderViewModel();
        viewModel.setId(1);
        return new WithHeaderViewModel();
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.edit_title));
        header.setLeft(true);
        header.setRightIsText(true);
        header.setRight(true);
        header.setRightText("发布");
    }

    @NonNull
    @Override
    protected Presenter buildPresenter() {
        return new Presenter();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_create_release;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null) {
            type = getIntent().getExtras().getInt("type");
        }
        super.onCreate(savedInstanceState);
        initViewsAndEvents();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.headerRightBtn:
                break;
        }
    }

    protected void initViewsAndEvents() {
        initWidget();
        if (CAMERA == type) {
            creamFile = ImagePicker.getInstance().getTakeImageFile();
            imagePicker.takePicture(CreateReleaseActivity.this, CAMERA);
        } else {
            ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, PHOTOS);
        }
    }


    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.create_release_recycler);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                imagePicker.setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            if (requestCode == PHOTOS) {
                finish();
            } else if (resultCode == RESULT_OK) {
                ImagePicker.galleryAddPic(this, imagePicker.getTakeImageFile());
                ImageItem imageItem = new ImageItem();
                imageItem.path = imagePicker.getTakeImageFile().getAbsolutePath();
                imagePicker.clearSelectedImages();
                imagePicker.addSelectedImageItem(0, imageItem, true);
                ArrayList<ImageItem> imageItems = imagePicker.getSelectedImages();
                selImageList.addAll(imageItems);
                adapter.setImages(selImageList);
            } else {
                return;
            }
        }

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (requestCode == REQUEST_CODE_SELECT || requestCode == CAMERA || requestCode == PHOTOS) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }

    }
}
