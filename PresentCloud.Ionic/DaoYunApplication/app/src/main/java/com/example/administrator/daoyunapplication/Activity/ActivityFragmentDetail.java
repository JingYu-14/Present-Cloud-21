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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentActivityAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentMemberAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListDetailAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListMemberAdapter;
import com.example.administrator.daoyunapplication.Home.ContentFragment;
import com.example.administrator.daoyunapplication.Home.HomeActivity;
import com.example.administrator.daoyunapplication.Model.Class;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.Detail;
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

public class ActivityFragmentDetail extends ListFragment//android.support.v4.app.Fragment{
{
    List<Detail> mDetailList;
    private static Classes c;
    private static User u;
    public ActivityFragmentDetail(){}
    @SuppressLint("ValidFragment")
    public ActivityFragmentDetail(Classes c,User u){
        this.c=c;
        this.u=u;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //这边做数据的初始化，从服务器获取数据
    private void initClass(){
//        Log.e("detail",c.getNewsClassName());
//        Log.e("dd",c.getNewsCourseName());
//        Log.e("ddd",c.getNewsIconURl());
//        Log.e("dddd",c.getNewsTeacherName());
//        Log.e("5",c.getTno());
        //注意，第一个数据不显示，后面自己补一个初始数据，后台获取的接到初始数据中
        mDetailList.add(new Detail("教材","计算机原理与应用"));
        mDetailList.add(new Detail("教材","暂无"));
        mDetailList.add(new Detail("学校院系","未知"));
        mDetailList.add(new Detail("",""));
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
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (viewContent == null) {
            viewContent=inflater.inflate(R.layout.fragment_activity_detail, null);
            if(u.getRole()==2){//老师结束班课
                Button button=(Button)viewContent.findViewById(R.id.out);
                if(u.getUserId()==c.tno){//看这个老师是不是这门课的授课老师
                    button.setText("结束班课");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            stopClass();
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.putExtra("user",u);
                            startActivity(intent);
                        }
                    });
                }else{//不是授课老师的话也是学生角色
                    button.setText("退出班课");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dropout();
                            Intent intent = new Intent(getActivity(),HomeActivity.class);
                            intent.putExtra("user",u);
                            startActivity(intent);
                        }
                    });
                }

            }else if(u.getRole()==1){//学生退出班课
                Button button=(Button)viewContent.findViewById(R.id.out);
                button.setText("退出班课");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dropout();
                        Intent intent = new Intent(getActivity(),HomeActivity.class);
                        intent.putExtra("user",u);
                        startActivity(intent);
                    }
                });

            }
        }
        mDetailList = new ArrayList<>();
        initClass();

        ActivityListDetailAdapter adapter = new ActivityListDetailAdapter(getContext(), R.layout.activity_detail_list_item, mDetailList,c);
        this.setListAdapter(adapter);

        return viewContent;

    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        Detail c= (Detail) l.getItemAtPosition(position);
        Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        //这边写跳转到任务的详细页面，提交任务页面
//        Intent intent = new Intent(getActivity(), ActivityHome.class);
//        intent.putExtra("classes", c);
//        startActivity(intent);
    }
    public void dropout(){
        String url = "http://3r1005r723.wicp.vip/daoyunapi/public/index.php//teacherStudents?id="+c.getNewsClassId()+"&uid="+u.getUserId();
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)//请求的url
                .addHeader("content-type", "application/json;charset:utf-8")
                .header("Authorization",u.getToken())
                .delete()
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
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
                    //   JsonObject result = jsonObject.get("data").getAsJsonObject();//取出用户信息

                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列

                }else{
                    Looper.prepare();
                    Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }


            }
        });
    }

    public void stopClass(){
        String url = "http://3r1005r723.wicp.vip/daoyunapi/public/index.php//teacherClasses?id="+c.getNewsClassId();
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)//请求的url
                .header("Authorization",u.getToken())
                .addHeader("content-type", "application/json;charset:utf-8")
                .delete()
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
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
                    //   JsonObject result = jsonObject.get("data").getAsJsonObject();//取出用户信息

                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列

                }else{
                    Looper.prepare();
                    Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();// 进入loop中的循环，查看消息队列
                }


            }
        });
    }
}
