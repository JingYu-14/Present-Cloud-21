package com.example.administrator.daoyunapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.daoyunapplication.R;


public class MemberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ImageView imageView=(ImageView)findViewById(R.id.zy_cy);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MemberActivity.this,ClassActivity.class);
                startActivity(intent);
            }
        });
        ImageView hdImage=(ImageView)findViewById(R.id.hd_cy);
        hdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hdintent=new Intent(MemberActivity.this,ActivityActivity.class);
                startActivity(hdintent);
            }
        });
        ImageView detailImage=(ImageView)findViewById(R.id.xq_cy);
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent=new Intent(MemberActivity.this,DetailActivity.class);
                startActivity(detailIntent);
            }
        });
    }
}