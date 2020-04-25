package com.example.administrator.daoyunapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

public class ActivityMemberDetail extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        Intent intent =getIntent();
        User u = (User) intent.getSerializableExtra("user");
        Classes c = (Classes) intent.getSerializableExtra("classes");
        TextView textNick = (TextView)findViewById(R.id.student);
        textNick.setText(u.getName());
        TextView textSchool = (TextView)findViewById(R.id.text_school);
        textSchool.setText(u.getCollege());
        TextView textClass = (TextView)findViewById(R.id.text_class_name);
        textClass.setText(c.getNewsClassName());
        TextView experience = (TextView)findViewById(R.id.experience);
        experience.setText(Integer.toString(u.getEmpiricalValue()));
        LinearLayout llActuall = (LinearLayout)findViewById(R.id.LL_actual);
//        llActuall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(ActivityMemberDetail.this,ActivityExperienceDetail.class);
//                startActivity(intent);
//            }
//        });
    }
}