package com.sanzhi.appupdate;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;


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
public class AppUpdate {

    private Context context;
    /**
     * 通知栏的图标 资源路径
     */
    private int smallIcon = -1;
    private String url;

    public AppUpdate() {
    }

    public AppUpdate(Context context) {
        this.context = context;
    }

    public AppUpdate setAppId(String appId) {
        this.url = "http://version-server.sanzhisoft.com/api/app/info/" + appId + "/version";
        return this;
    }

    public AppUpdate setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
        return this;
    }

    public void update() {
        checkUpdate();
    }

    private void checkUpdate() {
        Log.d("updateDemo","checkUpdate:thread:::"+Thread.currentThread().getName());
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
                    final String apk_url = jsonObject.getString("apk_url");
                    if (VersionUtil.checkNewVersion(context,newest_version)){
                        //需要更新
                        CommonDialog dialog = new CommonDialog(context, R.layout.dialog_update)
                                .setListenItem(new int[]{R.id.btn_cancel,R.id.btn_confirm})
                                .setListener(new CommonDialog.OnAllItemClickListener() {
                                    @Override
                                    public void handleClick(CommonDialog commonDialog, View view) {
                                        int id = view.getId();
                                        if (id == R.id.btn_cancel) {
                                            commonDialog.dismiss();
                                        } else if (id == R.id.btn_confirm) {
                                            commonDialog.dismiss();
                                            DownloadManager.getInstance(context)
                                                    .setApkName(AuxiliaryUtil.getFileName(apk_url))
                                                    .setApkUrl("http://version-server.sanzhisoft.com/" + apk_url)
                                                    .setSmallIcon(smallIcon)
                                                    .download();
                                        }
                                    }
                                });
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
