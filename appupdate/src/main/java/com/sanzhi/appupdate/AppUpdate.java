package com.sanzhi.appupdate;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


import androidx.annotation.IdRes;

import com.azhon.appupdate.manager.DownloadManager;
import com.sanzhi.appupdate.util.AuxiliaryUtil;
import com.sanzhi.appupdate.util.VersionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author: parade岁月
 * @date: 2020/6/1 11:32
 * @description app更新
 */
public class AppUpdate implements MessageDialog.OnPositionBtnClickListener, MessageDialog.OnNegativeBtnClickListener {

    private static AppUpdate appUpdate;
    private Context context;
    private String apk_url;
    private MessageDialog messageDialog;
    /**
     * 通知栏的图标 资源路径
     */
    private int smallIcon = -1;
    private String url;


    private AppUpdate(Context context){
        this.context = context.getApplicationContext();
        messageDialog = MessageDialog.getInstance(context)
                .setOnPositionBtnClickListener(this)
                .setOnNegativeBtnClickListener(this)
                .build();
    }

    public static AppUpdate getInstance(Context context){
        if (appUpdate == null){
            appUpdate = new AppUpdate(context);
        }
        return appUpdate;
    }

    public AppUpdate setAppId(String appId) {
        this.url = "http://version-server.sanzhisoft.com/api/app/info/" + appId + "/version";
        return this;
    }

    public AppUpdate setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
        return this;
    }

    public AppUpdate setTitle(String title){
      messageDialog.setTitle(title);
        return this;
    }

    public AppUpdate setTitleSize(float titleSize){
        messageDialog.setTitleSize(titleSize);
        return this;
    }

    public AppUpdate setTitleColor(int color){
        messageDialog.setTitleColor(color);
        return this;
    }

    public AppUpdate setContent(String content){
        messageDialog.setContent(content);
        return this;
    }

    public AppUpdate setContentSize(float contentSize){
        messageDialog.setContentSize(contentSize);
        return this;
    }

    public AppUpdate setContentColor(int color){
        messageDialog.setContentColor(color);
        return this;
    }

    public AppUpdate setPositionBtnResId(@IdRes int resId){
        messageDialog.setPositionBtnResId(resId);
        return this;
    }

    public AppUpdate setPositionBtnBgColor(int positionBtnBgColor){
        messageDialog.setPositionBtnBgColor(positionBtnBgColor);
        return this;
    }

    public AppUpdate setPositionText(String positionBtnText){
        messageDialog.setPositionText(positionBtnText);
        return this;
    }

    public AppUpdate setPositionBtnTextColor(int positionBtnTextColor){
        messageDialog.setPositionBtnTextColor(positionBtnTextColor);
        return this;
    }

    public AppUpdate setPositionBtnTextSize(float spValue){
        messageDialog.setPositionBtnTextSize(spValue);
        return this;
    }

    public AppUpdate setNegativeBtnResId(@IdRes int negativeBtnResId){
        messageDialog.setNegativeBtnResId(negativeBtnResId);
        return this;
    }

    public AppUpdate setNegativeBtnBgColor(int negativeBtnBgColor){
        messageDialog.setNegativeBtnBgColor(negativeBtnBgColor);
        return this;
    }

    public AppUpdate setNegativeBtnTextColor(int negativeBtnTextColor){
        messageDialog.setNegativeBtnTextColor(negativeBtnTextColor);
        return this;
    }

    public AppUpdate setNegativeBtnText(String text){
        messageDialog.setNegativeBtnText(text);
        return this;
    }

    public AppUpdate setNegativeBtnTextSize(float spValue){
        messageDialog.setNegativeBtnTextSize(spValue);
        return this;
    }


    public void update() {
        checkUpdate();
    }

    private void checkUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(AppUpdate.this.url);
                    connection = (HttpURLConnection) url.openConnection();//建立了一个与服务器的tcp连接，并没有实际发送http请求
                    connection.setRequestMethod("GET");//请求方式
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(10000);
                    InputStream inputStream = connection.getInputStream();//真正发起http请求
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    final StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        showResponse(response.toString());

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                    if (reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String response) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String newest_version = jsonObject.getString("newest_version");
                    apk_url = jsonObject.getString("apk_url");
                    if (VersionUtil.checkNewVersion(context,newest_version)){
                        messageDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onPositionBtnClick(CommonDialog commonDialog, View view) {
        messageDialog.dismiss();
        DownloadManager.getInstance(context)
                .setApkName(AuxiliaryUtil.getFileName(apk_url))
                .setApkUrl("http://version-server.sanzhisoft.com/" + apk_url)
                .setSmallIcon(smallIcon)
                .download();
    }

    @Override
    public void onNegativeBtnClick(CommonDialog commonDialog, View view) {
        messageDialog.dismiss();
    }
}
