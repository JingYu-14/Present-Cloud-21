package com.example.administrator.daoyunapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.administrator.daoyunapplication.Model.Classes;
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

public class ActivityListMemberAdapter extends ArrayAdapter<User> {

    private int resourceId;
    private Context context;
    private  Classes c;
    private  User user;
    public ActivityListMemberAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        //这边要变resourceId，布局对应的item的格式
        this.resourceId = resource;

    }
    public ActivityListMemberAdapter(@NonNull Context context, int resource, @NonNull List<User> objects,User user,Classes c) {
        super(context, resource, objects);
        this.context = context;
        //这边要变resourceId，布局对应的item的格式
        this.resourceId = resource;
        this.user=user;
        this.c=c;
    }
    public void setResourceId(int resource){
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("Listposition:",position +" ");
        int itemViewType=getItemViewType(position);
        if(itemViewType==0){
            setResourceId( R.layout.activity_member_list_item_1);
            View view = LayoutInflater.from(context).inflate(resourceId, null);
            TextView member_detail = (TextView) view.findViewById(R.id.member_detail);
            Button design_bt = (Button) view.findViewById(R.id.sign_bt);
            design_bt.setOnClickListener(new ActivityListMemberAdapter.RegisterButton(position));
            if(user.getRole()==2)//教师
            {
                member_detail.setText("");
            }else if (user.getRole()==1)//学生
            {
                member_detail.setText("当前获得经验值："+user.getEmpiricalValue());
            }

            return view;
        }else {
            setResourceId( R.layout.activity_member_list_item);
            //获取对应的Class班级对象
            User aUser = getItem(position);
            View view = LayoutInflater.from(context).inflate(resourceId, null);
            ImageView imageView_headpic = (ImageView) view.findViewById(R.id.imageView_headpic);
            TextView textView_member_name = (TextView) view.findViewById(R.id.textView_member_name);

            imageView_headpic.setImageResource(R.drawable.ic_launcher);
            textView_member_name.setText(aUser.getName());
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

    public class RegisterButton implements View.OnClickListener {
        int mPosition;
        public RegisterButton(int inPosition){
            mPosition= inPosition;
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                //签到按钮
                case R.id.sign_bt:
                    Context currentActivity=v.getContext();
                    //跳到签到页面
                    Intent intent = new Intent(currentActivity, SignActivity.class);
                    intent.putExtra("user",user);
                    intent.putExtra("classes",c);
                    currentActivity.startActivity(intent);
                    // currentActivity.startActivityForResult(intent, 0);
                    // Toast.makeText(v.getContext(), mPosition+"dfd", Toast.LENGTH_SHORT).show();

                    break;

            }
        }
    }

}

