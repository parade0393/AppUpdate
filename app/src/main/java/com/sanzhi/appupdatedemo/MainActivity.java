package com.sanzhi.appupdatedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sanzhi.appupdate.AppUpdate;
import com.sanzhi.appupdate.CommonDialog;
import com.sanzhi.appupdate.MessageDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog.getInstance(MainActivity.this).build()
                        .setOnPositionBtnClickListener(new MessageDialog.OnPositionBtnClickListener() {
                            @Override
                            public void onPositionBtnClick(CommonDialog commonDialog, View view) {
                                commonDialog.dismiss();
                            }
                        })
                        .setOnNegativeBtnClickListener(new MessageDialog.OnNegativeBtnClickListener() {
                            @Override
                            public void onNegativeBtnClick(CommonDialog commonDialog, View view) {
                                commonDialog.dismiss();
                                Toast.makeText(MainActivity.this,"文本",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });


        checkUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("parade", "onResume: checkUpdate");
        checkUpdate();
    }

    private void checkUpdate() {
        Log.d("updateDemo","onResume");
        AppUpdate.getInstance(this)
                .setTitle("标题")
                .setTitleSize(17)
                .setContentColor(0xffff0000)
                .setAppId("nfy7qt3618gpuhv2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .update();

    }
}
