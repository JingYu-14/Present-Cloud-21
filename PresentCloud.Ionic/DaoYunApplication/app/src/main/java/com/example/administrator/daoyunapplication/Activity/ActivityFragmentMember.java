package com.example.administrator.daoyunapplication.Activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentActivityAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityFragmentMemberAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListMemberAdapter;
import com.example.administrator.daoyunapplication.R;

import java.util.Arrays;

/**
 * Created by yinzhang on 2020/3/10.
 */

public class ActivityFragmentMember extends android.support.v4.app.Fragment{
    private View viewContent;
    private ViewPager vp_user;//用户内容
    private ViewPager vp_list;//列表内容
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (viewContent == null) {
            viewContent=inflater.inflate(R.layout.fragment_activity_member, null);

        }


        return viewContent;

    }


}
