package com.example.administrator.daoyunapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button signin;
    private Button enroll;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin = (Button)findViewById(R.id.signin);
        enroll = (Button)findViewById(R.id.enroll);
        signin.setOnClickListener(onClickListener);
        enroll.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v){
            switch (v.getId()) {
                case R.id.signin:
                    intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                   // intent.putExtra("pathFile", wPath);
                    startActivity(intent);
                    break;
                case R.id.enroll:
                    intent = new Intent();
                    intent.setClass(MainActivity.this, ReignActivity.class);
                    // intent.putExtra("pathFile", wPath);
                    startActivity(intent);
                    break;

            }

        }
    };
}
