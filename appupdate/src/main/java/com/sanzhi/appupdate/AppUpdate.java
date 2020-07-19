package com.sanzhi.appupdate;

import android.content.Context;
import android.content.res.Resources;
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
public class AppUpdate implements CommonDialog.OnAllItemClickListener {

    private static AppUpdate appUpdate;
    private Context context;
    private CommonDialog dialog;
    private String apk_url;
    private TextView title;
    private TextView content;
    private TextView negativeBtn;
    private TextView positiveBtn;
    /**
     * 通知栏的图标 资源路径
     */
    private int smallIcon = -1;
    private String url;
    /** 弹窗标题 */
    private String titleText = "温馨提示";
    /** 标题文字大小 */
    private float titleSize = 17;
    private int titleColor = 0xff000000;
    /** 弹窗内容 */
    private String contentText = "有新版本,请更新";
    /** 内容文字大小 */
    private float contentSize = 13;
    private int contentColor = 0xff9c9c9c;
    /** 确定按钮背景资源 */
    private int positionBtnResId = R.drawable.btn_bac;
    /** 确定按钮背景颜色 */
    private int positionBtnBgColor;
    /** 确定按钮文本 */
    private String positionBtnText = "更新";
    /** 确定按钮文字颜色 */
    private int positionBtnTextColor = 0xffffffff;
    /** 确定按钮文字大小 */
    private float positionBtnTextSize = 13;
    /** 取消按钮背景资源 */
    private int negativeBtnResId = R.drawable.btn_bac_gray;
    /** 取消按钮背景颜色 */
    private int negativeBtnBgColor;
    /** 取消按钮文本 */
    private String negativeBtnText = "取消";
    /** 取消按钮文字颜色 */
    private int negativeBtnTextColor = 0xff9c9c9c;
    /** 取消按钮文字大小 */
    private float negativeBtnTextSize = 13;

    public AppUpdate(Context context){
        this.context = context.getApplicationContext();
        dialog = new CommonDialog(this.context, R.layout.dialog_update)
                .setListenItem(new int[]{R.id.btnNegativeUpdate,R.id.btnPositiveUpdate})
                .setListener(this);
        title = dialog.findViewById(R.id.tvTitleUpdate);
        content = dialog.findViewById(R.id.tvContentUpdate);
        negativeBtn = dialog.findViewById(R.id.btnNegativeUpdate);
        positiveBtn = dialog.findViewById(R.id.btnPositiveUpdate);
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
        this.titleText = title;
        return this;
    }

    public AppUpdate setTitleSize(float titleSize){
        this.titleSize = titleSize;
        return this;
    }

    public AppUpdate setTitleColor(int color){
        this.titleColor = color;
        return this;
    }

    public AppUpdate setContent(String content){
        this.contentText = content;
        return this;
    }

    public AppUpdate setContentSize(float contentSize){
        this.contentSize = contentSize;
        return this;
    }

    public AppUpdate setContentColor(int color){
        this.contentColor = color;
        return this;
    }

    public AppUpdate setPositionBtnResId(@IdRes int resId){
        this.positionBtnResId = resId;
        return this;
    }

    public AppUpdate setPositionBtnBgColor(int positionBtnBgColor){
        this.positionBtnBgColor = positionBtnBgColor;
        return this;
    }

    public AppUpdate setPositionText(String positionBtnText){
        this.positionBtnText = positionBtnText;
        return this;
    }

    public AppUpdate setPositionBtnTextColor(int positionBtnTextColor){
        this.positionBtnBgColor = positionBtnTextColor;
        return this;
    }

    public AppUpdate setPositionBtnTextSize(float spValue){
        this.positionBtnTextSize = spValue;
        return this;
    }

    public AppUpdate setNegativeBtnResId(@IdRes int negativeBtnResId){
        this.negativeBtnResId = negativeBtnResId;
        return this;
    }

    public AppUpdate setNegativeBtnBgColor(int negativeBtnBgColor){
        this.negativeBtnBgColor = negativeBtnBgColor;
        return this;
    }

    public AppUpdate setNegativeBtnTextColor(int negativeBtnTextColor){
        this.negativeBtnTextColor = negativeBtnTextColor;
        return this;
    }

    public AppUpdate setNegativeBtnText(String text){
        this.negativeBtnText = text;
        return this;
    }

    public AppUpdate setNegativeBtnTextSize(float spValue){
        this.negativeBtnTextSize = spValue;
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
                        //标题设置
                        title.setText(titleText);
                        title.setTextColor(titleColor);
                        title.setTextSize(TypedValue.COMPLEX_UNIT_PX,sp2px(titleSize));

                        //内容设置

                        content.setText(contentText);
                        content.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(contentSize));
                        content.setTextColor(contentColor);

                        //取消按钮设置

                        negativeBtn.setText(negativeBtnText);
                        negativeBtn.setTextSize(negativeBtnTextSize);
                        negativeBtn.setTextColor(negativeBtnTextColor);
                        if (negativeBtnBgColor != 0){
                            //如果设置了取消按钮的背景颜色
                            negativeBtn.setBackgroundColor(negativeBtnBgColor);
                        }else {
                            negativeBtn.setBackgroundResource(negativeBtnResId);
                        }

                        //确定按钮
                        positiveBtn.setText(positionBtnText);
                        positiveBtn.setTextColor(positionBtnTextColor);
                        positiveBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(positionBtnTextSize));
                        if (positionBtnBgColor != 0){
                            //如果设置了确定按钮的背景颜色
                            positiveBtn.setBackgroundColor(positionBtnBgColor);
                        }else {
                            positiveBtn.setBackgroundResource(positionBtnResId);
                        }
                        if (!dialog.isShowing()){
                            //没有在显示才去显示
                            dialog.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private int sp2px(final float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    public void handleClick(CommonDialog commonDialog, View view) {
        int id = view.getId();
        if (id == R.id.btnNegativeUpdate) {
            if (commonDialog.isShowing()){
                commonDialog.dismiss();
            }
        } else if (id == R.id.btnPositiveUpdate) {
            if (commonDialog.isShowing()){
                commonDialog.dismiss();
            }
            DownloadManager.getInstance(context)
                    .setApkName(AuxiliaryUtil.getFileName(apk_url))
                    .setApkUrl("http://version-server.sanzhisoft.com/" + apk_url)
                    .setSmallIcon(smallIcon)
                    .download();
        }
    }
}
