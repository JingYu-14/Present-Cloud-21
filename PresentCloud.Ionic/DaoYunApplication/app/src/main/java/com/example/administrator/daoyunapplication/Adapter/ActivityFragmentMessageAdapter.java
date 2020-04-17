package com.example.administrator.daoyunapplication.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.administrator.daoyunapplication.Activity.ActivityContentFragment;
import com.example.administrator.daoyunapplication.Activity.MessageContentFragment;
import com.example.administrator.daoyunapplication.Home.ContentFragment;

import java.util.List;

//继承FragmentStatePagerAdapter，应该是Fragment的适配器
public class ActivityFragmentMessageAdapter extends FragmentStatePagerAdapter {
    public static final String TAB_TAG = "@dream@";

    private List<String> mTitles;

    public ActivityFragmentMessageAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        //初始化Fragment数据
        // Log.e("position:",position+" ,");
        //在FragmentAdapter中将ContentFragment中间内容嵌入到Tab页面中
        //将FragmentAdapter整个放入ContentFragment中
        MessageContentFragment fragment = new MessageContentFragment();
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
