package com.sanzhi.appupdatedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    private Button button,buttonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        buttonDialog = (Button) findViewById(R.id.buttonDialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }
        });
        buttonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MessageDialog(MainActivity.this).setTitle("首页").build().show();
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
        new AppUpdate(this)
                .setTitle("标题")
                .setTitleSize(17)
                .setContentColor(0xffff0000)
                .setAppId("nfy7qt3618gpuhv2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .update();

    }
}
