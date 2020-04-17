package com.example.administrator.daoyunapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.daoyunapplication.Model.Class;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class ListUserAdapter extends ArrayAdapter<User> {

    private int resourceId;
    private Context context;

    public ListUserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      //  Log.e("Listposition:",position +" ");

        //获取User对象
        User aUser = getItem(position);
        View view = LayoutInflater.from(context).inflate(resourceId,null);
        ImageView imageView_headpic=(ImageView)view.findViewById(R.id.imageView_headpic);
        TextView textView_user_name = (TextView)view.findViewById(R.id.textView_user_name);
        TextView textView_empirical_value = (TextView)view.findViewById(R.id.textView_empirical_value);

        imageView_headpic.setImageResource(R.drawable.ic_launcher);
        textView_user_name.setText(aUser.getName());
        textView_empirical_value.setText("经验值："+aUser.getEmpiricalValue());
        return view;
    }
}

