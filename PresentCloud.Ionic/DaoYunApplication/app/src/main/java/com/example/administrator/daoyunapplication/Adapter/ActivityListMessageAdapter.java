package com.example.administrator.daoyunapplication.Adapter;

import android.content.Context;
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
import com.example.administrator.daoyunapplication.Model.Message;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class ActivityListMessageAdapter extends ArrayAdapter<Message> {

    private int resourceId;
    private Context context;

    public ActivityListMessageAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
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

        int itemViewType=getItemViewType(position);
        if(itemViewType==0){
            setResourceId( R.layout.activity_message_list_item_1);
            View view = LayoutInflater.from(context).inflate(resourceId, null);
            Log.e("Listposition:",0 +" ");
            return view;
        }else {
            setResourceId( R.layout.activity_message_list_item);
            Message aMessage = getItem(position);
            View view = LayoutInflater.from(context).inflate(resourceId, null);
            ImageView imageView_headpic = (ImageView) view.findViewById(R.id.imageView_headpic);
            TextView textView_message_name = (TextView) view.findViewById(R.id.textView_message_name);
            TextView textContent = (TextView)view.findViewById(R.id.textView_message_content);
            imageView_headpic.setImageResource(R.drawable.ic_launcher);
            textView_message_name.setText(aMessage.getMessageName());
            textContent.setText(aMessage.getContent());
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

