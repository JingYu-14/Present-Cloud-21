//package com.example.administrator.daoyunapplication.Activity;
//
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentActivityAdapter;
//import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentMessageAdapter;
//import com.example.administrator.daoyunapplication.R;
//
//import java.util.Arrays;
//
///**
// * Created by yinzhang on 2020/3/10.
// */
//
//public class ActivityFragmentMessage extends android.support.v4.app.Fragment{
//    private View viewContent;
//    private TabLayout tab_essence;//上导航栏
//    private ViewPager vp_essence;//上内容
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (viewContent == null) {
//            viewContent = inflater.inflate(R.layout.fragment_activity_message, container, false);
//            initConentView(viewContent);
//            initData();
//
//        }
//        // 缓存的viewiew需要判断是否已经被加过parent，
//        // 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
//        ViewGroup parent = (ViewGroup) viewContent.getParent();
//        if (parent != null) {
//            parent.removeView(viewContent);
//        }
//        return viewContent;
//
//    }
//
//    public void initConentView(View viewContent) {
//        this.tab_essence = (TabLayout) viewContent.findViewById(R.id.tab_essence_message);
//        this.vp_essence = (ViewPager) viewContent.findViewById(R.id.vp_essence_message);
//    }
//
//    public void initData() {
//        //获取标签数据
//        String[] titles = getResources().getStringArray(R.array.message_home_video_tab);
//
//        //创建一个viewpager的adapter
//        ActivityFragmentMessageAdapter adapter = new ActivityFragmentMessageAdapter(getFragmentManager(), Arrays.asList(titles));
//        this.vp_essence.setAdapter(adapter);
//
//        //将TabLayout和ViewPager关联起来
//        this.tab_essence.setupWithViewPager(this.vp_essence);
//    }
//}



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
import com.example.administrator.daoyunapplication.Adapter.ActivityListMessageAdapter;
import com.example.administrator.daoyunapplication.Model.Message;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yinzhang on 2020/3/10.
 */

public class ActivityFragmentMessage extends ListFragment//android.support.v4.app.Fragment{
{
    List<Message> mMessageList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //这边做数据的初始化，从服务器获取数据
    private void initClass(){
        mMessageList.add(new Message(1,"小蓝消息","出勤率在班课中遥遥领先"));
        mMessageList.add(new Message(0,"班课通知","暂无通知"));
        mMessageList.add(new Message(1,"小蓝消息","出勤率在班课中遥遥领先"));
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
            viewContent=inflater.inflate(R.layout.fragment_activity_message, null);

        }
        mMessageList = new ArrayList<>();
        initClass();

        ActivityListMessageAdapter adapter = new ActivityListMessageAdapter(getContext(), R.layout.activity_message_list_item, mMessageList);
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

