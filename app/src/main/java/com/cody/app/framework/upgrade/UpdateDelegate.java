package com.cody.app.framework.upgrade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.cody.app.R;
import com.cody.handler.framework.viewmodel.UpdateViewModel;
import com.cody.xf.utils.CommonUtil;
import com.cody.xf.utils.LogUtil;
import com.cody.xf.utils.ResourceUtil;
import com.cody.xf.utils.ToastUtil;
import com.cody.xf.widget.dialog.AlertDialog;

/**
 * 版本更新
 */
public class UpdateDelegate {
    private final Activity mActivity;
    private final UpdateViewModel mUpdateViewModel;
    private ProgressDialog mProgressDialog;
    private OnUpdateListener mOnUpdateListener;
    private boolean isBindService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
            DownloadService downloadService = binder.getService();
            if (downloadService.isDownloaded()) {
                stopDownLoadService();
                mOnUpdateListener.onUpdateFinish();
                mProgressDialog.dismiss();
            }

            //接口回调，下载进度
            downloadService.setOnProgressListener(new DownloadService.OnDownloadListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onProgress(int size, int total) {
                    String progress = String.format(ResourceUtil.getString(R.string.download_progress_format), CommonUtil.formatSize(size), CommonUtil.formatSize(total));
                    LogUtil.d("download", progress);
                    mProgressDialog.setMax(total);
                    mProgressDialog.setProgress(size);
                    mProgressDialog.setMessage(progress);
                }

                @Override
                public void onFinish() {
                    ToastUtil.showToast(ResourceUtil.getString(R.string.download_finished));
                    stopDownLoadService();
                    mProgressDialog.dismiss();
                    mOnUpdateListener.onUpdateFinish();
                }

                @Override
                public void onFailure() {
                    ToastUtil.showToast("更新失败！");
                    stopDownLoadService();
                    mProgressDialog.dismiss();
                    mOnUpdateListener.onUpdateFinish();
                }
            });

            isBindService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBindService = false;
        }
    };

    private UpdateDelegate(Activity activity, UpdateViewModel viewModel, OnUpdateListener onUpdateListener) {
        mActivity = activity;
        mUpdateViewModel = viewModel;
        mOnUpdateListener = onUpdateListener;

        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setTitle(ResourceUtil.getString(R.string.version_update));
        mProgressDialog.setMessage(ResourceUtil.getString(R.string.update_progress));
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        if (mUpdateViewModel.isForceUpdate()) {
            forceUpdate();
        } else if (mUpdateViewModel.isOptionalUpdate()) {
            optionalUpdate();
        } else {
//            ToastUtil.showToast(ResourceUtil.getString(R.string.the_best_version));
            mOnUpdateListener.onUpdateFinish();
        }
    }

    public static void delegate(Activity activity, UpdateViewModel viewModel, OnUpdateListener onUpdateListener) {
        new UpdateDelegate(activity, viewModel, onUpdateListener);
    }

    /**
     * 选择更新
     */
    private void optionalUpdate() {
        new AlertDialog(mActivity).builder()
                .setTitle(mActivity.getString(R.string.upgrade_title))
                .setMsg(mUpdateViewModel.getUpdateInfo())
                .setCancelable(false)
                .setPositiveButton(ResourceUtil.getString(R.string.update_now), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bindDownLoadService();
                    }
                })
                .setNegativeButton(ResourceUtil.getString(R.string.not_now), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnUpdateListener.onUpdateFinish();
                    }
                }).show();
    }

    /**
     * 强制更新
     */
    private void forceUpdate() {
        final AlertDialog alertDialog = new AlertDialog(mActivity).builder()
                .setTitle(mActivity.getString(R.string.upgrade_title))
                .setMsg(mUpdateViewModel.getUpdateInfo())
                .setCancelable(false)
                .setPositiveButton(ResourceUtil.getString(R.string.update_now), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bindDownLoadService();
                    }
                });
        alertDialog.show();
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    ((Activity) alertDialog.getContext()).finish();
                }
                return false;
            }
        });
    }

    /**
     * DownloadManager是否可用
     *
     * @param context
     * @return
     */
    private boolean isDownLoadMangerEnable(Context context) {
        int state = context.getApplicationContext().getPackageManager()
                .getApplicationEnabledSetting("com.android.providers.downloads");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                    state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED);
        } else {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                    state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER);
        }
    }

    private void bindDownLoadService() {
        if (isDownLoadMangerEnable(mActivity)) {
            Intent intent = new Intent(mActivity, DownloadService.class);
            intent.putExtra(DownloadService.DOWNLOAD, mUpdateViewModel.getApkUrl());
            intent.putExtra(DownloadService.APK_NAME, mUpdateViewModel.getApkName());
            intent.putExtra(DownloadService.IS_FORCE, mUpdateViewModel.isForceUpdate());
            mActivity.startService(intent);
            mProgressDialog.show();
            mActivity.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        } else {//下载管理器被禁用时跳转浏览器下载
            try {
                if (!TextUtils.isEmpty(mUpdateViewModel.getApkUrl()) && mActivity != null) {
                    Intent intent = new Intent();
                    Uri uri = Uri.parse(mUpdateViewModel.getApkUrl());
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                        mActivity.startActivity(Intent.createChooser(intent, "请选择浏览器进行下载更新"));
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stopDownLoadService() {
        try {
            Intent intent = new Intent(mActivity, DownloadService.class);
            if (isBindService) {
                mActivity.unbindService(mServiceConnection);
                isBindService = false;
            }
            mActivity.stopService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnUpdateListener {
        void onUpdateFinish();
    }
}