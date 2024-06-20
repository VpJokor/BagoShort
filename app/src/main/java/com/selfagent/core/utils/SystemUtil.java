package com.selfagent.core.utils;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import java.io.File;

public class SystemUtil {
    //https://gitee.com/lanyangyangzzz/custom-view-project

    private static final String TAG = "SystemUtil";

    //文件下载
    public static void startDownLoad(Context context,String fileUtl) {

        //下载链接 这里下载手机B站为示例
//        String downloadUrl = "https://dl.hdslb.com/mobile/latest/iBiliPlayer-html5_app_bili.apk";
        String downloadUrl = fileUtl;

        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1);
        //这里下载到指定的目录，我们存在公共目录下的download文件夹下
        Uri fileUri = Uri.fromFile(
              new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        System.currentTimeMillis() + "-" + fileName
                )
        );
        //开始构建 DownloadRequest 对象
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        //构建通知栏样式
        request.setTitle("测试下载标题");
        request.setDescription("测试下载的内容文本");

        //下载或下载完成的时候显示通知栏
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE or DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //指定下载的文件类型为APK
        request.setMimeType("application/vnd.android.package-archive");
//            request.addRequestHeader()   //还能加入请求头
//            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)   //能指定下载的网络

        //指定下载到本地的路径(可以指定URI)
        request.setDestinationUri(fileUri);

        //开始构建 DownloadManager 对象
        DownloadManager downloadManager = (DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);

        //加入Request到系统下载队列，在条件满足时会自动开始下载。返回的为下载任务的唯一ID
        long requestID = downloadManager.enqueue(request);

        //注册下载任务完成的监听
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //已经完成
                if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                    //获取下载ID
                    long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    Uri uri = downloadManager.getUriForDownloadedFile(id);
                    Log.e(TAG, "onReceive: 下载完成了- uri: " + uri.toString() );
                    installApk(context,uri);

                } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                    //如果还未完成下载，跳转到下载中心
                    Log.e(TAG, "onReceive: 跳转到下载中心"  );
                    Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                    viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    context.startActivity(viewDownloadIntent);
                }
            }
        }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //调用系统组件浏览图片
    public static void viewImg(Context context,Uri uri,String type){
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse("http://test.com/img111.jpg"), "image/*")
        intent.setData(uri);
        intent.setType(type);
        context.startActivity(intent);
    }

    //<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
    public static void installApk(Context context , Uri uri ) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    //跳转到APP设置页面
    public static void toAppSetting(Context context,String pkgName){
//        context.getApplicationContext().getPackageName()
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + pkgName));
        context.startActivity(intent);
    }

    // 判断是否有通知权限
    public static boolean isNotifyOpen(Context context){
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }
}
