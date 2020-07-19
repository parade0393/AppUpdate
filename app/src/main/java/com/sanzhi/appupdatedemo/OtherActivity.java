package com.sanzhi.appupdatedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sanzhi.appupdate.MessageDialog;

public class OtherActivity extends AppCompatActivity {

    private Button button;
    private MessageDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        button = (Button) findViewById(R.id.button);

        dialog =new MessageDialog(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.build().show();
            }
        });
    }
}