/*
 * Copyright (c)  Created by Cody.yi on 2016/8/26.
 */

package com.cody.app.business.personal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.cody.app.R;
import com.cody.app.databinding.ActivityPersonalInformationBinding;
import com.cody.app.framework.activity.WithHeaderActivity;
import com.cody.handler.business.presenter.PersonalInformationPresenter;
import com.cody.app.business.manager.CreateQrCodeActivity;
import com.cody.handler.business.viewmodel.PersonalInformationViewModel;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageCropActivity;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.cody.handler.framework.viewmodel.HeaderViewModel;
import com.cody.repository.business.database.UserInfoManager;
import com.cody.repository.business.local.LocalKey;
import com.cody.repository.framework.Repository;
import com.cody.xf.utils.ActivityUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.dialog.ActionSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by cody.yi on 2016/8/9.
 * 个人信息
 */
public class PersonalInformationActivity extends WithHeaderActivity<PersonalInformationPresenter,
        PersonalInformationViewModel, ActivityPersonalInformationBinding> {

    private ImagePicker imagePicker;
    private String mImgPath;
    public final static int CAMERA = 1;
    public final static int PHOTOS = 2;
    public final static int CROP = 3;
    public final static int NICK_NAME = 4;
    public final static int TEL_PHONE = 5;
    public final static int SELF_INTRODUCTION = 6;
    public static final int REQUEST_PERMISSION_STORAGE = 0x01;
    public static final int REQUEST_PERMISSION_CAMERA = 0x02;

    @NonNull
    @Override
    protected PersonalInformationPresenter buildPresenter() {
        return new PersonalInformationPresenter();
    }

    @NonNull
    @Override
    protected PersonalInformationViewModel buildViewModel(Bundle savedInstanceState) {
        PersonalInformationViewModel viewModel = new PersonalInformationViewModel();
        viewModel.setId(1);
        return viewModel;
    }

    @Override
    protected void initHeader(HeaderViewModel header) {
        header.setTitle(getString(R.string.personal_information));
        header.setLeft(true);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImagePicker();
        getPresenter().getUserInfo(TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headerLeftBtn:
                finish();
                break;
            case R.id.portraitItem:
                clickPortrait();
                break;
            case R.id.realNameItem:
                break;
            case R.id.nickNameItem:
//                ActivityUtil.navigateToForResult(ChangeNickNameActivity.class, NICK_NAME);
                break;
            case R.id.telPhoneItem:
//                ActivityUtil.navigateToForResult(ChangePhoneNumberActivity.class, TEL_PHONE);
                break;
            case R.id.selfIntroductionItem:
                ActivityUtil.navigateToForResult(SelfIntroductionActivity.class, SELF_INTRODUCTION);
                break;
            case R.id.personalQrCodeItem:
                //个人主页二维码
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("jumpType", "1");
                    jsonObject.put("pramKey1", Repository.getLocalValue(LocalKey.OPEN_ID));
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("Content", jsonObject.toString());
                    ActivityUtil.navigateTo(CreateQrCodeActivity.class, bundle1);
//                    ActivityUtil.navigateTo(PersonalHomePageQrCodeActivity.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void clickPortrait() {
        if (TextUtils.isEmpty(UserInfoManager.getImId()) || UserInfoManager.getImId().startsWith("10"))
            return;//当用户是客服时，不可进行头像修改

        new ActionSheetDialog(PersonalInformationActivity.this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Red,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (!checkPermission(Manifest.permission.CAMERA)) {
                                    ActivityCompat.requestPermissions(PersonalInformationActivity.this, new
                                            String[]{Manifest.permission.CAMERA}, ImageGridActivity
                                            .REQUEST_PERMISSION_CAMERA);
                                } else {
                                    imagePicker.takePicture(PersonalInformationActivity.this, CAMERA);
                                }

                                LogUtil.d(TAG, "拍照 which=" + which);
                            }
                        })
                .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                ImagePicker.getInstance().setSelectLimit(1);
                                Intent intent = new Intent(PersonalInformationActivity.this, ImageGridActivity.class);
                                startActivityForResult(intent, PHOTOS);
                                LogUtil.d(TAG, "从相册选择 which=" + which);
                            }
                        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, "requestCode=" + requestCode);
        LogUtil.d(TAG, "resultCode=" + resultCode);
        if (resultCode == RESULT_CANCELED) return;
        switch (requestCode) {
            case CAMERA:
                //发送广播通知图片增加了
                if (imagePicker.getTakeImageFile() == null) return;
                ImagePicker.galleryAddPic(this, imagePicker.getTakeImageFile());
                ImageItem imageItem = new ImageItem();
                imageItem.path = imagePicker.getTakeImageFile().getAbsolutePath();
                imagePicker.clearSelectedImages();
                imagePicker.addSelectedImageItem(0, imageItem, true);
                Intent intent = new Intent(PersonalInformationActivity.this, ImageCropActivity.class);
                startActivityForResult(intent, CROP);  //单选需要裁剪，进入裁剪界面
                break;
            case PHOTOS:
                // is the same
            case CROP:
                if (data != null) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                            .EXTRA_RESULT_ITEMS);
                    if (images != null && images.size() > 0) {
                        LogUtil.d(TAG, "image path = " + images.get(0).path);
                        mImgPath = images.get(0).path;
                        Repository.setLocalValue(LocalKey.PICTURE_URL, mImgPath);
                        getViewModel().getImageUrl().set(mImgPath);
                        getPresenter().upLoadUserHeadImage(TAG, images.get(0).name, images.get(0).path);
                    }
                }
                break;
            case NICK_NAME:
                getViewModel().getNickName().set(Repository.getLocalValue(LocalKey.NICK_NAME));
                break;
            case TEL_PHONE:
                getViewModel().getTelPhone().set(Repository.getLocalValue(LocalKey.MOBILE_PHONE));
                break;
            case SELF_INTRODUCTION:
                getViewModel().getSelfIntroduction().set(Repository.getLocalValue(LocalKey.INTRODUCTION));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                new ImageDataSource(this, null, this);
            } else {
                ToastUtil.showToast("权限被禁止，无法选择本地图片");
            }
        } else if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imagePicker.takePicture(this, ImagePicker.REQUEST_CODE_TAKE);
            } else {
                ToastUtil.showToast("权限被禁止，无法打开相机");
            }
        }
    }

    private boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void initImagePicker() {
        int SIZE = getResources().getDimensionPixelSize(R.dimen.dimens_40dp);
        imagePicker = ImagePicker.getInstance();

        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setMultiMode(false);                     //单选
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(SIZE);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(SIZE);                         //保存文件的高度。单位像素
    }
}
