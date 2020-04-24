package com.example.administrator.daoyunapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Activity.ActivityFragmentMember;
import com.example.administrator.daoyunapplication.Activity.ActivityHome;
import com.example.administrator.daoyunapplication.LoginActivity;
import com.example.administrator.daoyunapplication.MainActivity;
import com.example.administrator.daoyunapplication.Model.Detail;
import com.example.administrator.daoyunapplication.Model.Disscuss;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.example.administrator.daoyunapplication.SignActivity.SignActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class ActivityListDetailAdapter extends ArrayAdapter<Detail> {

    private int resourceId;
    private Context context;

    public ActivityListDetailAdapter(@NonNull Context context, int resource, @NonNull List<Detail> objects) {
        super(context, resource, objects);
        this.context = context;
        //这边要变resourceId，布局对应的item的格式
        this.resourceId = resource;
    }
    public void setResourceId(int resource){
        this.resourceId = resource;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("Listposition:",position +" ");
        int itemViewType=getItemViewType(position);
        if(itemViewType==0){
            setResourceId( R.layout.activity_detail_list_item_1);
            View view = LayoutInflater.from(context).inflate(resourceId, null);
            ImageView detail_image = (ImageView)view.findViewById(R.id.detail_image);
            TextView major = (TextView) view.findViewById(R.id.major);
            TextView className = (TextView) view.findViewById(R.id.class_name);
            TextView teacherName = (TextView) view.findViewById(R.id.teacher_name);
            TextView semester = (TextView) view.findViewById(R.id.semester);
            detail_image.setImageResource(R.drawable.class_icon);
            major.setText("2019级软件工程专业");
            className.setText("工程实践");
            teacherName.setText("池芝标");
            semester.setText("2019-2020-2");
            return view;
        }else {
            setResourceId( R.layout.activity_detail_list_item);
            //获取对应的Class班级对象
            Detail detail = getItem(position);
            View view = LayoutInflater.from(context).inflate(resourceId, null);
            TextView textView_detail_title = (TextView) view.findViewById(R.id.textView_detail_title);
            TextView textView_detail_content = (TextView)view.findViewById(R.id.textView_detail_content);
            textView_detail_title.setText(detail.getTitle());
            textView_detail_content.setText(detail.getContent());
            return view;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 0;
        }else{
            return 1;
        }

    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

}

