package com.example.administrator.daoyunapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;

public class DetailTeacherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_teacher);
        CheckBox checkBox=(CheckBox)findViewById(R.id.checkbox);
//        checkBox.setClickable(false);
        checkBox.setEnabled(false);
    }
}
