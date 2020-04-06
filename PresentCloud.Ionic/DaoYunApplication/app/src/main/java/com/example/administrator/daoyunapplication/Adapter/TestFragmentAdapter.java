package com.example.administrator.daoyunapplication.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.administrator.daoyunapplication.Home.ContentFragment;

import java.util.List;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//继承FragmentStatePagerAdapter，应该是Fragment的适配器
public class TestFragmentAdapter extends FragmentStatePagerAdapter {
    public static final String TAB_TAG = "@dream@";

    private List<String> mTitles;

    public TestFragmentAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        //初始化Fragment数据
        Log.e("position:",position+" ,");
        ContentFragment fragment = new ContentFragment();
        String[] title = mTitles.get(position).split(TAB_TAG);
        Log.e("title:",title[0]+" ,"+title[1]);
        fragment.setType(Integer.parseInt(title[1]));
        fragment.setTitle(title[0]);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split(TAB_TAG)[0];
    }


}
