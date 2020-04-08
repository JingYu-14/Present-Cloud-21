package com.example.administrator.daoyunapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


public class ActivityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        ImageView imageView=(ImageView)findViewById(R.id.cy_hd);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityActivity.this,MemberActivity.class);
                startActivity(intent);
            }
        });
        ImageView hdImage=(ImageView)findViewById(R.id.zy_hd);
        hdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hdintent=new Intent(ActivityActivity.this,ClassActivity.class);
                startActivity(hdintent);
            }
        });
        ImageView detailImage=(ImageView)findViewById(R.id.xq_hd);
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent=new Intent(ActivityActivity.this,DetailActivity.class);
                startActivity(detailIntent);
            }
        });
    }
}