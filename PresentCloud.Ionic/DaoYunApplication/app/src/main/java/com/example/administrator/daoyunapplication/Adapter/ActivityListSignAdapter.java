package com.example.administrator.daoyunapplication.Adapter;

/**
 * Created by 666666 on 2020/6/23.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.daoyunapplication.Model.SignResult;
import com.example.administrator.daoyunapplication.Model.StudentSign;
import com.example.administrator.daoyunapplication.R;

import java.util.List;

public class ActivityListSignAdapter extends ArrayAdapter<StudentSign> {
    private int resourceId;
    private Context context;
//    private int studentsNum;
    //    private Classes c;
//    private  User user;
//    private SignResult signResult;
    public ActivityListSignAdapter(@NonNull Context context, int resource, @NonNull List<StudentSign> objects) {
        super(context, resource, objects);
        this.context = context;
        //这边要变resourceId，布局对应的item的格式
        this.resourceId = resource;

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
        StudentSign studentSign = getItem(position);
        View view = LayoutInflater.from(context).inflate(resourceId,null);
        TextView textView=(TextView)view.findViewById(R.id.textView_sign_name);
        TextView tt=(TextView)view.findViewById(R.id.text_sign_content);
        if(studentSign!=null){
            String time= studentSign.getTime().replaceAll("-",":");
            String title=studentSign.getDate()+"  "+time+context.getString(R.string.sign);
            String content;
            if (studentSign.getState()){
                content=context.getString(R.string.have_sign);
                tt.setText(content);
            }else {
                content=context.getString(R.string.not_sign);
                tt.setText(content);
                tt.setTextColor(0xffff0000);
            }
            textView.setText(title);
        }

        return view;
    }


}

