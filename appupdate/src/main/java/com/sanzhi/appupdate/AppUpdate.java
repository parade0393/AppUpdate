package com.sanzhi.appupdate;

import android.content.Context;

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

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

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
        /*if (Build.VERSION.SDK_INT >= 23) {
            int permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                checkUpdate();
            }
        } else {
            checkUpdate();
        }*/
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
                    StringBuilder response = new StringBuilder();
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
        });
    }

    private void showResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String newest_version = jsonObject.getString("newest_version");
            String apk_url = jsonObject.getString("apk_url");
            if (VersionUtil.checkNewVersion(context,newest_version)){

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
