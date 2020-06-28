package com.example.administrator.daoyunapplication.Home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.daoyunapplication.Adapter.Test2FragmentAdapter;
import com.example.administrator.daoyunapplication.Adapter.TestFragmentAdapter;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.Arrays;

/**
 * Created by yinzhang on 2020/3/10.
 */

public class TestFragment2 extends android.support.v4.app.Fragment{

    private View viewContent;
    private TabLayout tab_essence;//上导航栏
    private ViewPager vp_essence;//上内容
    private static User user;

    public TestFragment2(){}
    @SuppressLint("ValidFragment")
    public TestFragment2(User user){
        this.user=user;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (viewContent == null) {
            viewContent = inflater.inflate(R.layout.fragment_test_2, container, false);
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
        this.tab_essence = (TabLayout) viewContent.findViewById(R.id.tab_essence);
        this.vp_essence = (ViewPager) viewContent.findViewById(R.id.vp_essence);
    }

    public void initData() {
        //获取标签数据
        String[] titles = getResources().getStringArray(R.array.two_plus);

        //创建一个viewpager的adapter
        Test2FragmentAdapter adapter = new Test2FragmentAdapter(getChildFragmentManager(), Arrays.asList(titles),user);
     //   this.vp_essence.setOffscreenPageLimit(3);
        this.vp_essence.setAdapter(adapter);

        //将TabLayout和ViewPager关联起来
        this.tab_essence.setupWithViewPager(this.vp_essence);

    }

}
