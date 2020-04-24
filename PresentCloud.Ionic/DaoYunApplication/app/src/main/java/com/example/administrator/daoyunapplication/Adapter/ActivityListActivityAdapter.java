package com.example.administrator.daoyunapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.Disscuss;
import com.example.administrator.daoyunapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class ActivityListActivityAdapter extends ArrayAdapter<Disscuss> {

    private int resourceId;
    private Context context;

    public ActivityListActivityAdapter(@NonNull Context context, int resource, @NonNull List<Disscuss> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
    }
    public void setResourceId(int resource){
        this.resourceId = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("Listposition:",position +" ");

        //获取对应的Class班级对象
        Disscuss aDisscuss = getItem(position);
        View view = LayoutInflater.from(context).inflate(resourceId,null);
        ImageView imageView_headpic=(ImageView)view.findViewById(R.id.imageView_headpic);
        TextView textView_disscuss_name = (TextView)view.findViewById(R.id.textView_disscuss_name);

        imageView_headpic.setImageResource(R.drawable.ic_launcher);
        textView_disscuss_name.setText(aDisscuss.getDisscussName());

        return view;
    }
}

