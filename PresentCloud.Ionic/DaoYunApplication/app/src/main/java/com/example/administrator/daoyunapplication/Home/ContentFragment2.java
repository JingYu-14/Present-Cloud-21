package com.example.administrator.daoyunapplication.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Activity.ActivityHome;
import com.example.administrator.daoyunapplication.Adapter.ListClassAdapter;
import com.example.administrator.daoyunapplication.Adapter.createClassAdapter;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//底部选项卡对应的Fragment
public class ContentFragment2 extends ListFragment//extends Fragment
{   private  User user;
    private static String type;
    List<Classes>  mClassList;
    private boolean isFirst=false;
    private boolean isOne=false;
    private boolean isSecond=false;
    private boolean isThress=false;
    public ContentFragment2(){}
    @SuppressLint("ValidFragment")
    public ContentFragment2(User user){
        this.user = user;
    }
    @SuppressLint("ValidFragment")
    public ContentFragment2(User user, String type){
        this.user = user;
        this.type=type;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private View viewContent;
    private int mType = 0;//上选项卡的名称 编号
    private String mTitle;//上选项卡的名称

    public int getmType(){
        return this.mType;
    }
    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //布局文件中只有一个居中的ListView
        viewContent = inflater.inflate(R.layout.fragment_content,container,false);
        isFirst=true;
        Log.e("mType:",mType+" ,");
        if (mType == 0) {//+号

            mClassList = new ArrayList<>();
            mClassList.add(new Classes(6,"333333","工程实践",1,"池老标5","2019级专硕","hv"));
            createClassAdapter adapter = new createClassAdapter(getContext(), R.layout.creater_class_button, mClassList,user);
            this.setListAdapter(adapter);


        }

        return viewContent;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();

        Classes c= (Classes)l.getItemAtPosition(position);

    }


}
