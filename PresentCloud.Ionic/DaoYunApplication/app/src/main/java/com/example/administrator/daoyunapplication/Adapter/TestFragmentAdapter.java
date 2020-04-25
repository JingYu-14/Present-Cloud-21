package com.example.administrator.daoyunapplication.Adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.example.administrator.daoyunapplication.Home.ContentFragment;
import com.example.administrator.daoyunapplication.Model.User;

import java.util.List;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//继承FragmentStatePagerAdapter，应该是Fragment的适配器
public class TestFragmentAdapter extends FragmentPagerAdapter {
    public static final String TAB_TAG = "@dream@";
    private FragmentManager mFragmentManager;
    private List<String> mTitles;
    private static User user;
    public TestFragmentAdapter(FragmentManager fm, List<String> titles,User user) {
        super(fm);
        mFragmentManager=fm;
        mTitles = titles;
        this.user=user;
    }
    @Override
    public Parcelable saveState() {
        // Do Nothing
        return null;
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        //初始化Fragment数据
        Log.e("position:",position+" ,");
        //在FragmentAdapter中将ContentFragment中间内容嵌入到Tab页面中
        //将FragmentAdapter整个放入ContentFragment中
        ContentFragment fragment = new ContentFragment(user);
        String[] title = mTitles.get(position).split(TAB_TAG);
        Log.e("title:",title[0]+" ,"+title[1]);
        fragment.setType(Integer.parseInt(title[1]));
        fragment.setTitle(title[0]);

        return fragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        //初始化Fragment数据
        Log.e("setPrimaryItem:",position+" ,");
        //在FragmentAdapter中将ContentFragment中间内容嵌入到Tab页面中
        //将FragmentAdapter整个放入ContentFragment中

        String[] title = mTitles.get(position).split(TAB_TAG);
        ContentFragment fragment = new ContentFragment(user,title[1]);
        Log.e("title:",title[0]+" ,"+title[1]);
        fragment.setType(Integer.parseInt(title[1]));
        fragment.setTitle(title[0]);
        super.setPrimaryItem(container, position, object);
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
