package com.example.administrator.daoyunapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.Disscuss;
import com.example.administrator.daoyunapplication.Model.SignResult;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.example.administrator.daoyunapplication.SignActivity.SignActivity;

import java.util.List;

public class ActivityListResourceAdapter extends ArrayAdapter<SignResult> {
    private int resourceId;
    private Context context;
    private int studentsNum;
//    private Classes c;
//    private  User user;
//    private SignResult signResult;
    public ActivityListResourceAdapter(@NonNull Context context, int resource, @NonNull List<SignResult> objects) {
        super(context, resource, objects);
        this.context = context;
        //这边要变resourceId，布局对应的item的格式
        this.resourceId = resource;

    }
    public ActivityListResourceAdapter(@NonNull Context context, int resource, @NonNull List<SignResult> objects,int sNum) {
        super(context, resource, objects);
        this.context = context;
        //这边要变resourceId，布局对应的item的格式
        this.resourceId = resource;
        this.studentsNum=sNum;
    }
//    public ActivityListResourceAdapter(@NonNull Context context, int resource, @NonNull List<SignResult> objects) {
//        super(context, resource, objects);
//        this.context = context;
       // 这边要变resourceId，布局对应的item的格式
//        this.resourceId = resource;
//        this.signResult=
//        this.user=user;
//        this.c=c;
//    }
    public void setResourceId(int resource){
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("Listposition:",position +" ");

        //获取对应的Class班级对象
        SignResult sResult = getItem(position);
        View view = LayoutInflater.from(context).inflate(resourceId,null);
        TextView textView=(TextView)view.findViewById(R.id.textView_sign_name);
        TextView tt=(TextView)view.findViewById(R.id.text_sign_content);
        if(sResult!=null){
            String time= sResult.getTime().replaceAll("-",":");
            String title=sResult.getDate()+"  "+time+context.getString(R.string.sign);
            String content = sResult.getSignNum()+"/"+studentsNum;
            textView.setText(title);
            tt.setText(content);
        }

        return view;
    }


}
