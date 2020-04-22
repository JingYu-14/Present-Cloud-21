package com.example.administrator.daoyunapplication.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentActivityAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentMemberAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListMemberAdapter;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yinzhang on 2020/3/10.
 */

public class ActivityFragmentDetail extends ListFragment//android.support.v4.app.Fragment{
{
    List<User> mUserList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //这边做数据的初始化，从服务器获取数据
    private void initClass(){
        //注意，第一个数据不显示，后面自己补一个初始数据，后台获取的接到初始数据中
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));

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

        ActivityListMemberAdapter adapter = new ActivityListMemberAdapter(getContext(), R.layout.activity_member_list_item, mUserList);
        this.setListAdapter(adapter);

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

}
