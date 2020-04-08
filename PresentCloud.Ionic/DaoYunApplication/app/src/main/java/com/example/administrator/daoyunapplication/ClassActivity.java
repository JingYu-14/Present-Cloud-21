package com.example.administrator.daoyunapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ClassActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        ImageView imageView=(ImageView)findViewById(R.id.cy_zy);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClassActivity.this,MemberActivity.class);
                startActivity(intent);
            }
        });
        ImageView hdImage=(ImageView)findViewById(R.id.hd_zy);
        hdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hdintent=new Intent(ClassActivity.this,ActivityActivity.class);
                startActivity(hdintent);
            }
        });
        ImageView detailImage=(ImageView)findViewById(R.id.xq_zy);
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent=new Intent(ClassActivity.this,DetailActivity.class);
                startActivity(detailIntent);
            }
        });
    }
}
