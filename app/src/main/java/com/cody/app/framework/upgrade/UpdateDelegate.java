package com.cody.app.framework.upgrade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
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
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
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
                if (keyCode==KeyEvent.KEYCODE_BACK){
                    ((Activity)alertDialog.getContext()).finish();
                }
                return false;
            }
        });
    }

    private void bindDownLoadService() {
        Intent intent = new Intent(mActivity, DownloadService.class);
        intent.putExtra(DownloadService.DOWNLOAD, mUpdateViewModel.getApkUrl());
        intent.putExtra(DownloadService.APK_NAME, mUpdateViewModel.getApkName());
        intent.putExtra(DownloadService.IS_FORCE, mUpdateViewModel.isForceUpdate());
        mActivity.startService(intent);
        mProgressDialog.show();
        isBindService = mActivity.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void stopDownLoadService() {
        Intent intent = new Intent(mActivity, DownloadService.class);
        if (isBindService) {
            mActivity.unbindService(mServiceConnection);
            isBindService = false;
        }
        mActivity.stopService(intent);
    }

    public interface OnUpdateListener {
        void onUpdateFinish();
    }
}