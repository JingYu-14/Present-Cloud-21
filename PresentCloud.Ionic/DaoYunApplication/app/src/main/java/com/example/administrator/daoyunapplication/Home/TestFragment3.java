package com.example.administrator.daoyunapplication.Home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.Test3FragmentAdapter;
import com.example.administrator.daoyunapplication.Adapter.TestFragmentAdapter;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.Arrays;

/**
 * Created by yinzhang on 2020/3/10.
 */

public class TestFragment3 extends android.support.v4.app.Fragment{
    private View viewContent;
    private TabLayout tab_essence;//上导航栏
    private ViewPager vp_essence;//上内容
    private static String msg="";
    private static User user;
    public TestFragment3(){}
    @SuppressLint("ValidFragment")
    public TestFragment3(String msg){
        this.msg=msg;

    }
    @SuppressLint("ValidFragment")
    public TestFragment3(User user){
        this.user=user;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (viewContent == null) {
            viewContent = inflater.inflate(R.layout.fragment_test_3, container, false);
//            Bundle bundle = getArguments();
//            String userName="";
//            //判断非空
//            if(bundle != null){
//                userName = bundle.getString("name");
//            }

            initConentView(viewContent);
            initData();

        }
        // 缓存的viewiew需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个view已经有parent的错误。
        ViewGroup parent = (ViewGroup) viewContent.getParent();
        if (parent != null) {
            parent.removeView(viewContent);
        }
        //   viewContent = inflater.inflate(R.layout.fragment_test_1,container,false);


        return viewContent;
    }
    public void initConentView(View viewContent) {
        this.tab_essence = (TabLayout) viewContent.findViewById(R.id.tab_essence_own);
        this.vp_essence = (ViewPager) viewContent.findViewById(R.id.vp_essence_own);
    }

    public void initData() {
        //获取标签数据
        String[] titles = getResources().getStringArray(R.array.own_video_tab);

        //创建一个viewpager的adapter
        Test3FragmentAdapter adapter = new Test3FragmentAdapter(getFragmentManager(), Arrays.asList(titles),this.user);
        this.vp_essence.setAdapter(adapter);

        //将TabLayout和ViewPager关联起来
        this.tab_essence.setupWithViewPager(this.vp_essence);
    }
//    public TestFragment3(){}
//    @SuppressLint("ValidFragment")
//    public TestFragment3(String str){
//        String userName = str;
//        Log.e("fffffffffffg:",str);
//    }

}
