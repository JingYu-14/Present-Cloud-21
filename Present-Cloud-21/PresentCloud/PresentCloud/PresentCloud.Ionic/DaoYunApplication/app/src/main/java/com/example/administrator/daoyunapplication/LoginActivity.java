package com.example.administrator.daoyunapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 2020/3/10 0010.
 */

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private Button btn_forget_pass;
    private EditText account_input;
    private EditText password_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_forget_pass = (Button)findViewById(R.id.btn_forget_pass);
        account_input = (EditText)findViewById(R.id.account_input);
        password_input = (EditText)findViewById(R.id.password_input);

    }

}
