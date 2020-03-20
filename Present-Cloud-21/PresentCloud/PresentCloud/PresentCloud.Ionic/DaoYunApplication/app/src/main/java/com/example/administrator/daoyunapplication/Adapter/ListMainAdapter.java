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

import com.example.administrator.daoyunapplication.R;
import com.example.administrator.daoyunapplication.Model.Food;

import java.util.List;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class ListMainAdapter extends ArrayAdapter<Food> {

    private int resourceId;
    private Context context;

    public ListMainAdapter(@NonNull Context context, int resource, @NonNull List<Food> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      //  Log.e("Listposition:",position +" ");

        //获取food对象
        Food food = getItem(position);
        View view = LayoutInflater.from(context).inflate(resourceId,null);
        ImageView imageView_headpic=(ImageView)view.findViewById(R.id.imageView_headpic);
        TextView textView_course_name = (TextView)view.findViewById(R.id.textView_course_name);
        TextView textView_teacher_name = (TextView)view.findViewById(R.id.textView_teacher_name);
        TextView textView_class_name = (TextView)view.findViewById(R.id.textView_class_name);
      //  textView.setText(food.getName());
        imageView_headpic.setImageResource(R.drawable.ic_launcher);
        textView_course_name.setText(food.getNewsCourseName());
        textView_teacher_name.setText(food.getNewsTeacherName());
        textView_class_name.setText(food.getNewsClassName());
        return view;
    }
}

