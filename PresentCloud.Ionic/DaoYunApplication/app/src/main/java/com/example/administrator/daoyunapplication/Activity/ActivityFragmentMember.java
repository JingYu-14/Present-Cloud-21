package com.example.administrator.daoyunapplication.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentActivityAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentMemberAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListMemberAdapter;
import com.example.administrator.daoyunapplication.Adapter.ListClassAdapter;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yinzhang on 2020/3/10.
 */

public class ActivityFragmentMember extends ListFragment//android.support.v4.app.Fragment{
{
    List<User> mUserList;
    private static Classes c;
    private static User user;
    public ActivityFragmentMember(){}
    @SuppressLint("ValidFragment")
    public ActivityFragmentMember(Classes c,User user){
        this.c=c;
        this.user=user;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //这边做数据的初始化，从服务器获取数据
    private void initClass(){
        //注意，第一个数据不显示，后面自己补一个初始数据，后台获取的接到初始数据中
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
//        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
//        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
//        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
//        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
//        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
//        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
//        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
//        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        getStudentesData();
    }

    private View viewContent;
    private int mType = 0;//上选项卡的名称 编号
    private String mTitle;//上选项卡的名称


    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

//    private View viewContent;
//    private ViewPager vp_user;//用户内容
//    private ViewPager vp_list;//列表内容
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (viewContent == null) {
            viewContent=inflater.inflate(R.layout.fragment_activity_member, null);

        }
        mUserList = new ArrayList<>();
        initClass();
        Log.e("dafasf",c.toString());
//        ActivityListMemberAdapter adapter = new ActivityListMemberAdapter(getContext(), R.layout.activity_member_list_item, mUserList);
//        this.setListAdapter(adapter);

        return viewContent;

    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        User c= (User)l.getItemAtPosition(position);
        Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        //这边写跳转到任务的详细页面，提交任务页面
//        Intent intent = new Intent(getActivity(), ActivityHome.class);
//        intent.putExtra("classes", c);
//        startActivity(intent);
    }
    private void getStudentesData(){
        final OkHttpClient client = new OkHttpClient();
        String path="http://3r1005r723.wicp.vip/daoyunapi/public/index.php/";

        path = path + "students";


        int pagenum=1;
        int pagesize=100;
        String format = String.format(path+"?id="+c.getNewsClassId()+"&pagenum="+pagenum+"&pagesize="+pagesize);
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
        final Request build1 = new Request.Builder().url(format).build();

        client.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String info = response.body().string();//获取服务器返回的json格式数据

                Log.e("TAG", "onResponse: "+info );
                JsonObject jsonObject = new JsonParser().parse(info).getAsJsonObject();
                JsonObject jsonObjectMeta =jsonObject.get("meta").getAsJsonObject();
                int code = jsonObjectMeta.get("status").getAsInt();
                String msg="";
                msg=jsonObjectMeta.get("msg").getAsString();

                if (200==code)//如果code等于200，则说明存在该用户，而且服务器还返回了该用户的信息
                {
                    //这边要注意，获取的是用户对象getAsString();不行.getAsJsonObject();用这个
                    JsonObject result = jsonObject.get("data").getAsJsonObject();
                    JsonArray studentsArray = result.get("students").getAsJsonArray();//取出用户信息
//
                    for(int i=0;i<studentsArray.size();i++){
                        JsonObject re=studentsArray.get(i).getAsJsonObject();
                        User c = new User(
                                re.get("id").getAsInt(),
                                re.get("account").getAsString(),
                                re.get("name").getAsString(),
                                re.get("exp").getAsInt(),
                                re.get("state").getAsBoolean());
                        mUserList.add(c);
                    }
//                            Log.e("ada",mClassList.toString());
                    //ui更新必须用这个
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityListMemberAdapter adapter = new ActivityListMemberAdapter(getContext(), R.layout.activity_member_list_item, mUserList,user,c);
                            setListAdapter(adapter);
                        }});
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }


            }
        });


    }
}
