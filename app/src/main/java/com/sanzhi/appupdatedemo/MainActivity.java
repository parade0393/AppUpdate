package com.sanzhi.appupdatedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sanzhi.appupdate.AppUpdate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        checkUpdate();
        super.onResume();
    }

    private void checkUpdate() {

    }
}
