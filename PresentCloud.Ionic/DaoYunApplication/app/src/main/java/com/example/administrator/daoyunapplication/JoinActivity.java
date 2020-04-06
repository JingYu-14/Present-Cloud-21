package com.example.administrator.daoyunapplication;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class JoinActivity extends AppCompatActivity {
    private EditText input_join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        input_join =(EditText) findViewById(R.id.edit_join);
        input_join.setFocusable(true);
        input_join.setFocusableInTouchMode(true);
        input_join.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        input_join.requestFocus();
//        Timer timer=new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                InputMethodManager inputManager=(InputMethodManager)input_join.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(input_join,0);
//            }
//        },500);
    }
}
