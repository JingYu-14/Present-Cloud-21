package com.example.administrator.daoyunapplication.SignActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.LoginActivity;
import com.example.administrator.daoyunapplication.MainActivity;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/4/18 0018.
 */

public class SignActivity extends AppCompatActivity {
    private Button reSet_bt;
    private Button start_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

//        reSet_bt = (Button)findViewById(R.id.reSet_bt);
//        start_bt = (Button)findViewById(R.id.start_bt);
//        reSet_bt.setOnClickListener(new SignButton());
//        start_bt.setOnClickListener(new SignButton());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SignButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {



//                case R.id.start_bt:
//
//                    break;
            }
        }
    }

}
