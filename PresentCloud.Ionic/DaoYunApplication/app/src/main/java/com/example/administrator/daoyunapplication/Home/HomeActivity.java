package com.example.administrator.daoyunapplication.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/3/10 0010.
 */

public class HomeActivity extends AppCompatActivity {

    private List<TabItem> mTableItemList;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 首先获取到意图对象
        Intent intent = getIntent();
        user=(User)intent.getSerializableExtra("user");
        initTabData();
        initTabHost();

        ArrayList<Fragment>   fragments = new ArrayList<>();
        fragments.add(new TestFragment1(user));

    }


    //初始化Tab数据
    private void initTabData() {
        mTableItemList = new ArrayList<>();
        //添加tab,三个对应三个页面，底部三个按钮
        mTableItemList.add(new TabItem(R.drawable.main_bottom_home_normal,R.drawable.main_bottom_home_press,R.string.main_home_text, TestFragment1.class));
        mTableItemList.add(new TabItem(R.drawable.main_bottom_mine_normal,R.drawable.main_bottom_mine_press,R.string.main_mine_text, TestFragment2.class));
        mTableItemList.add(new TabItem(R.drawable.main_bottom_attention_normal,R.drawable.main_bottom_attention_press,R.string.main_attention_text, TestFragment3.class));


    }

    //初始化选项卡视图
    private void initTabHost() {
        //实例化FragmentTabHost对象
        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i<mTableItemList.size(); i++) {
            TabItem tabItem = mTableItemList.get(i);
            //实例化一个TabSpec,设置tab的名称和视图
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitleString()).setIndicator(tabItem.getView());
            fragmentTabHost.addTab(tabSpec,tabItem.getFragmentClass(),null);

            //给Tab按钮设置背景
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.main_bottom_bg));

            //默认选中第一个tab
            if(i == 0) {
                tabItem.setChecked(true);
            }
        }

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //重置Tab样式
                for (int i = 0; i< mTableItemList.size(); i++) {
                    TabItem tabitem = mTableItemList.get(i);
                    if (tabId.equals(tabitem.getTitleString())) {
                        tabitem.setChecked(true);
                    }else {
                        tabitem.setChecked(false);
                    }
                    if(i==0){
                        ArrayList<Fragment>   fragments = new ArrayList<>();
                        fragments.add(new TestFragment1(user));
                    }
                    if(i==1){//点击第二个页面时，传参数
                        if(user.getAvatar()==""){
                            ArrayList<Fragment>   fragments = new ArrayList<>();
                            fragments.add(new TestFragment2(user));
                        }else{
                            //seachImage();
                            ArrayList<Fragment>   fragments = new ArrayList<>();
                            fragments.add(new TestFragment2(user));
                        }
                    }
                    if(i==2){//点击第三个页面时，传参数
                        if(user.getAvatar()==""){
                            ArrayList<Fragment>   fragments = new ArrayList<>();
                            fragments.add(new TestFragment3(user));
                        }else{
                            //seachImage();
                            ArrayList<Fragment>   fragments = new ArrayList<>();
                            fragments.add(new TestFragment3(user));
                        }

                        //应该在点第三个Tab时，初始化fragment
//                        ArrayList<ContentFragment3>   fragments = new ArrayList<>();
//                        fragments.add(new ContentFragment3(user));
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
        public Class<? extends Fragment> fragmentClass;

        public View view;
        public ImageView imageView;
        public TextView textView;

        public TabItem(int imageNormal, int imagePress, int title,Class<? extends Fragment> fragmentClass) {
            this.imageNormal = imageNormal;
            this.imagePress = imagePress;
            this.title = title;
            this.fragmentClass =fragmentClass;
        }

        public Class<? extends  Fragment> getFragmentClass() {
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
