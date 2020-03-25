package com.example.administrator.daoyunapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 2020/3/10 0010.
 */

public class ReignActivity extends AppCompatActivity {
    private Button btn_enroll;
    private EditText account_input;
    private EditText password_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reign);
        btn_enroll = (Button)findViewById(R.id.btn_enroll);
        account_input = (EditText)findViewById(R.id.account_input);
        password_input = (EditText)findViewById(R.id.password_input);

    }

}
