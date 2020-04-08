package com.example.administrator.daoyunapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView imageView=(ImageView)findViewById(R.id.cy_xq);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailActivity.this,MemberActivity.class);
                startActivity(intent);
            }
        });
        ImageView hdImage=(ImageView)findViewById(R.id.hd_xq);
        hdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hdintent=new Intent(DetailActivity.this,ActivityActivity.class);
                startActivity(hdintent);
            }
        });
        ImageView detailImage=(ImageView)findViewById(R.id.zy_xq);
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent=new Intent(DetailActivity.this,ClassActivity.class);
                startActivity(detailIntent);
            }
        });
    }
}
