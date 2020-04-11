package com.example.administrator.daoyunapplication.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.daoyunapplication.Adapter.ListUserAdapter;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//底部选项卡对应的Fragment
public class ContentFragment3 extends ListFragment//extends Fragment
{
    List<User> mUserList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void initUser(){
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //布局文件中只有一个居中的ListView
        viewContent = inflater.inflate(R.layout.fragment_content3,container,false);

//        TextView textView = (TextView) viewContent.findViewById(R.id.tv_content);
//        textView.setText(this.mTitle);

        Log.e("mType:",mType+" ,");
        mUserList = new ArrayList<>();
        if( mType==0) {
            initUser();
        }
        ListUserAdapter adapter = new ListUserAdapter(getContext(), R.layout.list_item_user, mUserList);
        this.setListAdapter(adapter);
        return viewContent;
    }
}
