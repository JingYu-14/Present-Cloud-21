package com.example.administrator.daoyunapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.daoyunapplication.Home.HomeActivity;
import com.example.administrator.daoyunapplication.Home.TestFragment1;
import com.example.administrator.daoyunapplication.Home.TestFragment2;
import com.example.administrator.daoyunapplication.Home.TestFragment3;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/4/11 0011.
 */

public class ActivityHome extends AppCompatActivity {
    private List<ActivityHome.TabItem> mTableItemList;
    private Classes c;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_home);
        initTabData();
        initTabHost();
        // 首先获取到意图对象
        Intent intent = getIntent();
        // 获取到传递过来的姓名,文件路径
        c= (Classes) intent.getSerializableExtra("classes");
        user= (User) intent.getSerializableExtra("user");
        //将信息传给listFragment
        ArrayList<ListFragment>   fragments = new ArrayList<>();
        fragments.add(new ActivityFragmentMember(c,user));
    }


    //初始化Tab数据
    private void initTabData() {
        mTableItemList = new ArrayList<>();
        //添加tab,5个对于5个页面，底部5个按钮
        mTableItemList.add(new ActivityHome.TabItem(R.drawable.zy_normal,R.drawable.zy_press,R.string.main_home_activity_resource, ActivityFragmentResource.class));
        mTableItemList.add(new ActivityHome.TabItem(R.drawable.cy_normal,R.drawable.cy_press,R.string.main_home_activity_member, ActivityFragmentMember.class));
        mTableItemList.add(new ActivityHome.TabItem(R.drawable.hd_normal,R.drawable.hd_press,R.string.main_home_activity_activity, ActivityFragmentActivity.class));
        mTableItemList.add(new ActivityHome.TabItem(R.drawable.xx_normal,R.drawable.xx_press,R.string.main_home_activity_message, ActivityFragmentMessage.class));
        mTableItemList.add(new ActivityHome.TabItem(R.drawable.xq_normal,R.drawable.xq_press,R.string.main_home_activity_detail, ActivityFragmentDetail.class));

    }

    //初始化选项卡视图
    private void initTabHost() {
        //实例化FragmentTabHost对象
        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i<mTableItemList.size(); i++) {
           ActivityHome.TabItem tabItem = mTableItemList.get(i);
            //实例化一个TabSpec,设置tab的名称和视图
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitleString()).setIndicator(tabItem.getView());
            fragmentTabHost.addTab(tabSpec,tabItem.getFragmentClass(),null);

            //给Tab按钮设置背景
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.activity_main_bottom_bg));

            //默认选中第1个tab
            if(i == 0) {
                tabItem.setChecked(true);
            }
        }

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //重置Tab样式
                for (int i = 0; i< mTableItemList.size(); i++) {
                    ActivityHome.TabItem tabitem = mTableItemList.get(i);
                    if (tabId.equals(tabitem.getTitleString())) {
                        tabitem.setChecked(true);
                    }else {
                        tabitem.setChecked(false);
                    }
                    if(i==2){
                        //将信息传给listFragment
                        ArrayList<ListFragment>   fragments = new ArrayList<>();
                        fragments.add(new ActivityFragmentMember(c,user));
                    }

                }


            }
        });
    }

    ////////Tab 选项卡 底部当成为一个页面////////////
    class TabItem {
        //正常情况下显示的图片
        private int imageNormal;
        //选中情况下显示的图片
        private int imagePress;
        //tab的名字。编码
        private int title;
        private String titleString;

        //tab对应的fragment
        public java.lang.Class<? extends Fragment> fragmentClass;

        public View view;
        public ImageView imageView;
        public TextView textView;

        public TabItem(int imageNormal, int imagePress, int title, java.lang.Class<? extends Fragment> fragmentClass) {
            this.imageNormal = imageNormal;
            this.imagePress = imagePress;
            this.title = title;
            this.fragmentClass =fragmentClass;

        }

        public java.lang.Class<? extends  Fragment> getFragmentClass() {
            return fragmentClass;
        }
        public int getImageNormal() {
            return imageNormal;
        }

        public int getImagePress() {
            return imagePress;
        }

        public int getTitle() {
            return  title;
        }

        public String getTitleString() {
            if (title == 0) {
                return "";
            }
            if(TextUtils.isEmpty(titleString)) {
                titleString = getString(title);
            }
            return titleString;
        }

        public View getView() {
            if(this.view == null) {
                this.view = getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
                this.imageView = (ImageView) this.view.findViewById(R.id.tab_iv_image);
                this.textView = (TextView) this.view.findViewById(R.id.tab_tv_text);
                if(this.title == 0) {
                    this.textView.setVisibility(View.GONE);
                } else {
                    this.textView.setVisibility(View.VISIBLE);
                    this.textView.setText(getTitleString());
                }
                this.imageView.setImageResource(imageNormal);
            }
            return this.view;
        }

        //切换tab的方法
        public void setChecked(boolean isChecked) {
            if(imageView != null) {
                if(isChecked) {
                    imageView.setImageResource(imagePress);
                }else {
                    imageView.setImageResource(imageNormal);
                }
            }
            if(textView != null && title != 0) {
                if(isChecked) {
                    textView.setTextColor(getResources().getColor(R.color.main_botton_text_select));
                } else {
                    textView.setTextColor(getResources().getColor(R.color.main_bottom_text_normal));
                }
            }
        }
    }

}
