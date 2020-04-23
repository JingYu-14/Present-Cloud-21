package com.example.administrator.daoyunapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.daoyunapplication.R;

public class ActivityMemberDetail extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        LinearLayout llActuall = (LinearLayout)findViewById(R.id.LL_actual);
        llActuall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityMemberDetail.this,ActivityExperienceDetail.class);
                startActivity(intent);
            }
        });
    }
}