package com.sanzhi.appupdatedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;

import com.sanzhi.appupdate.AppUpdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                .setAppId("nfy7qt3618gpuhv2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .update();

    }
}
